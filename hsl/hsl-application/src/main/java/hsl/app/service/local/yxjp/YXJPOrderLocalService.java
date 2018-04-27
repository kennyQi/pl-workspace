package hsl.app.service.local.yxjp;

import com.alibaba.fastjson.JSON;
import hg.common.component.BaseServiceImpl;
import hg.common.util.DateUtil;
import hg.common.util.MoneyUtil;
import hg.common.util.SpringContextUtil;
import hg.log.util.HgLogger;
import hg.system.dao.StaffDao;
import hg.system.model.staff.Staff;
import hsl.app.common.util.AppInfoUtils;
import hsl.app.common.util.OrderUtil;
import hsl.app.component.config.SysProperties;
import hsl.app.dao.CityAirCodeDAO;
import hsl.app.dao.MemberDao;
import hsl.app.dao.UserDao;
import hsl.app.dao.yxjp.YXJPOrderDAO;
import hsl.app.dao.yxjp.YXJPOrderPassengerDAO;
import hsl.app.dao.yxjp.YXJPOrderPayBatchRefundRecordDAO;
import hsl.app.dao.yxjp.YXJPOrderPayRecordDAO;
import hsl.app.service.local.coupon.CouponLocalService;
import hsl.domain.model.company.Member;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.sys.CityAirCode;
import hsl.domain.model.user.User;
import hsl.domain.model.yxjp.*;
import hsl.payment.alipay.component.AliPayUtils;
import hsl.payment.alipay.config.HSLAlipayConfig;
import hsl.payment.alipay.pojo.*;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.command.yxjp.order.ApplyRefundYXJPOrderCommand;
import hsl.pojo.command.yxjp.order.CancelNoPayYXJPOrderCommand;
import hsl.pojo.command.yxjp.order.CreateYXJPOrderCommand;
import hsl.pojo.command.yxjp.order.PayToYXJPOrderCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.yxjp.YXFlightDTO;
import hsl.pojo.dto.yxjp.YXPassengerDTO;
import hsl.pojo.dto.yxjp.notify.ApplyRefundTicketNotify;
import hsl.pojo.dto.yxjp.notify.OutTicketRefuseNotify;
import hsl.pojo.dto.yxjp.notify.OutTicketSuccessNotify;
import hsl.pojo.dto.yxjp.notify.RefundTicketNotify;
import hsl.pojo.exception.CouponException;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.pojo.qo.yxjp.*;
import hsl.pojo.util.HSLConstants;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.qo.sys.CityAirCodeQO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plfx.api.client.api.v1.gn.response.JPAutoOrderGNResponse;
import plfx.api.client.api.v1.gn.response.RefundTicketGNResponse;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 易行机票订单服务
 *
 * @author zhurz
 * @since 1.7
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class YXJPOrderLocalService extends BaseServiceImpl<YXJPOrder, YXJPOrderQO, YXJPOrderDAO> {

	@Autowired
	private YXJPOrderDAO dao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private CityAirCodeDAO cityAirCodeDAO;
	@Autowired
	private YXJPOrderPayRecordDAO payRecordDAO;
	@Autowired
	private YXJPOrderPassengerDAO passengerDAO;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private YXJPOrderPayBatchRefundRecordDAO payBatchRefundRecordDAO;

	@Autowired
	private YXJPOrderOperateLogLocalService logService;
	@Autowired
	private CouponLocalService couponLocalService;

	@Autowired
	private CouponService couponService;
	@Autowired
	private YXFlightService yxFlightService;
	@Autowired
	private YXJPSmsService smsService;

	@Autowired
	private HSLAlipayConfig alipayConfig;

	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private RabbitTemplate mqTemplate;

	/**
	 * 订单业务线程池
	 */
	private ExecutorService exec = Executors.newFixedThreadPool(10);

	/**
	 * REDIS日期序列KEY前缀
	 */
	private final static String YXJP_ORDER_NO_DATE_SEQ = "HSL:YXJP_ORDER_NO_DATE_SEQ:";

	@Override
	protected YXJPOrderDAO getDao() {
		return dao;
	}

	/**
	 * <pre>
	 * 得到service在spring上下文中的对象
	 * 如果不使用此方法在当前service方法内调用自身方法则会直接绕过spring的事务处理
	 * </pre>
	 *
	 * @return
	 */
	protected YXJPOrderLocalService self() {
		return SpringContextUtil.getApplicationContext().getBean(YXJPOrderLocalService.class);
	}

	/**
	 * 获得日期的序列，当其超过9999时重置为0
	 *
	 * @param date 日期
	 * @return
	 */
	private synchronized Integer getDateSeq(Date date) {
		Long seq = 0L;
		Jedis jedis = jedisPool.getResource();
		try {
			String dateKey = YXJP_ORDER_NO_DATE_SEQ + DateUtil.formatDateTime(date, "yyyyMMdd");
			seq = jedis.incr(dateKey);
			if (seq > 9999) {
				seq = 0L;
				jedis.set(dateKey, "0");
			}
			jedis.expire(dateKey, (int) TimeUnit.DAYS.toSeconds(2));
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		return seq.intValue();
	}

	/**
	 * 创建订单
	 *
	 * @param command
	 * @return 订单ID
	 */
	public String createOrder(CreateYXJPOrderCommand command) {

		// 检查创建订单的command
		command.check();

		// 检查乘客是否可以下单，乘客不能下同天同航班的订单
		YXFlightDTO.BaseInfo flightInfo = command.getFlightInfo().getBaseInfo();
		for (YXPassengerDTO passengerDTO : command.getPassengers()) {
			YXJPOrderPassengerQO passengerQO = new YXJPOrderPassengerQO();
			YXJPOrderFlightQO flightQO = new YXJPOrderFlightQO();
			passengerQO.setFlightQO(flightQO);
			passengerQO.setIdNo(passengerDTO.getIdNo());
			passengerQO.setIdType(passengerDTO.getIdType());
			flightQO.setFlightNo(flightInfo.getFlightno());
			flightQO.setStartTimeBegin(DateUtils.truncate(flightInfo.getStartTime(), Calendar.DATE));
			flightQO.setStartTimeEnd(DateUtils.addSeconds(DateUtils.ceiling(flightInfo.getStartTime(), Calendar.DATE), -1));
			passengerQO.setStatuses(
					YXJPOrderPassengerTicket.STATUS_PAY_WAIT,
					YXJPOrderPassengerTicket.STATUS_TICKET_DEALING,
					YXJPOrderPassengerTicket.STATUS_TICKET_SUCC,
					YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_DEALING,
					YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_FAIL
			);
			if (passengerDAO.queryCount(passengerQO) > 0)
				throw new ShowMessageException(passengerDTO.getName() + "已经预定过当天" + flightInfo.getFlightno() + "的机票");
		}

		// 查询下单用户
		User orderUser = userDao.get(command.getFromUserId());

		// 查询企业员工
		Map<String, Member> memberMap = new HashMap<String, Member>();
		List<String> memberIds = new ArrayList<String>();
		for (YXPassengerDTO passengerDTO : command.getPassengers()) {
			if (StringUtils.isNotBlank(passengerDTO.getCompanyMemberId()))
				memberIds.add(passengerDTO.getCompanyMemberId());
		}
		if (memberIds.size() > 0) {
			HslMemberQO memberQO = new HslMemberQO();
			memberQO.setIds(memberIds);
			List<Member> members = memberDao.queryList(memberQO);
			for (Member member : members)
				memberMap.put(member.getId(), member);
		}

		// 根据城市三字码查询城市名称
		CityAirCodeQO cityAirCodeQO = new CityAirCodeQO();
		cityAirCodeQO.setCityAirCode(command.getFlightInfo().getBaseInfo().getOrgCity());
		CityAirCode orgCity = cityAirCodeDAO.queryUnique(cityAirCodeQO);
		cityAirCodeQO.setCityAirCode(command.getFlightInfo().getBaseInfo().getDstCity());
		CityAirCode dstCity = cityAirCodeDAO.queryUnique(cityAirCodeQO);
		String orgCityName = orgCity == null ? null : orgCity.getName();
		String dstCityName = dstCity == null ? null : dstCity.getName();

		// 生成订单号
		Date now = new Date();
		String orderNo = OrderUtil.createOrderNo(now, getDateSeq(now), 0, command.getFromType());

		// 创建并保存订单
		YXJPOrder order = new YXJPOrder();
		order.manager().createOrder(command, orderNo, orgCityName, dstCityName, orderUser, memberMap);
		getDao().save(order);

		// 记录订单操作日志
		logService.record(order.getId(), orderUser.getAuthInfo().getLoginName(), "用户下单",
				"下单命令参数：" + JSON.toJSONString(command));

		return order.getId();
	}

	/**
	 * 生成支付订单
	 *
	 * @param command 确认支付command
	 * @return 支付表单HTML代码
	 * @throws CouponException      卡券使用异常
	 * @throws ShowMessageException 异常消息
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String payToOrder(final PayToYXJPOrderCommand command) throws CouponException, ShowMessageException {

		// -- 每个步骤都是独立事务执行 --

		// 步骤1 生成支付表单HTML
		String payFormHtml = self().payToOrderStep1(command);

		// 步骤2 检查订单状态并向分销下单
		exec.execute(new Runnable() {
			@Override
			public void run() {
				try {
					YXJPOrderLocalService.this.self().outOrder(command.getOrderId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return payFormHtml;
	}

	/**
	 * 生成支付表单HTML（步骤1）
	 *
	 * @param command 确认支付command
	 * @return 支付表单HTML代码
	 * @throws CouponException      卡券使用异常
	 * @throws ShowMessageException 异常消息
	 */
	public String payToOrderStep1(PayToYXJPOrderCommand command) throws CouponException, ShowMessageException {

		// 查询订单
		YXJPOrder order = getDao().get(command.getOrderId());

		// 检查订单的下单用户是否和支付用户匹配
		if (order == null || !StringUtils.equalsIgnoreCase(command.getFromUserId(), order.getUserInfo().getUserId()))
			throw new ShowMessageException("非法操作");

		// 处理卡券占用
		List<CouponDTO> couponDTOs = null;
		if (command.getCouponIds() != null && command.getCouponIds().size() > 0) {

			// 订单支付价格
			Double orderTotalPay = MoneyUtil.round(order.getPayInfo().getTotalPrice() - order.getPayInfo().getTotalCancelPrice(), 2);

			// 检查卡券使用条件
			couponService.checkCouponUseCondition(command.getCouponIds(), command.getFromUserId(),
					ConsumeOrderSnapshot.ORDERTYPE_YXJP, order.getBaseInfo().getOrderNo(), orderTotalPay);

			// 占用卡券，保存订单快照
			BatchConsumeCouponCommand consumeCouponCommand = new BatchConsumeCouponCommand();
			consumeCouponCommand.setCouponIds(command.getCouponIds().toArray(new String[command.getCouponIds().size()]));
			consumeCouponCommand.setOrderId(order.getBaseInfo().getOrderNo());
			consumeCouponCommand.setPayPrice(orderTotalPay);
			consumeCouponCommand.setOrderType(ConsumeOrderSnapshot.ORDERTYPE_YXJP);
			couponDTOs = couponService.occupyCoupon(consumeCouponCommand);

		}

		// 生成支付订单
		YXJPOrderPayRecord payRecord = order.manager().generatePayRecord(command.getFromType(), couponDTOs, alipayConfig.getSellerEmail(), "");
		payRecordDAO.saveOrUpdate(payRecord);

		// 判断支付来源客户端类型
		if (HSLConstants.FromType.FROM_TYPE_PC.equals(command.getFromType())) {
			// 检查支付金额是否大于0，是就拼装支付表单HTML，否则直接支付成功
			if (payRecord.getPayAmount() > 0) {
				// 支付请求待补全
				String notifyUrl = SysProperties.getInstance().get("base_url_web") + "/gate/alipay/yxjp-notify";
				String returnUrl = SysProperties.getInstance().get("base_url_web") + "/gate/alipay/yxjp-return";
				PayRequest payRequest = new PayRequest(notifyUrl, returnUrl, payRecord.getPayOrderNo(), payRecord.getPayOrderName(),
						payRecord.getPayAmount(), AppInfoUtils.getAlipayRemarkPrefix(), null);
				return AliPayUtils.buildPayRequestFormHtml(payRequest, alipayConfig);
			} else {
				// 处理直接使用卡券支付成功的订单
				processUseCouponPaySuccess(payRecord);

				// 跳转成功页面的HTML
				return "<script>document.location.href='" + SysProperties.getInstance().get("base_url_web") + "/yxjp/success?orderId=" + order.getId() + "';</script>";
			}
		}

		return null;
	}

	/**
	 * 定单支付成功处理
	 *
	 * @param payNotify 支付宝通知
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void processPaySuccess(PayNotify payNotify) {

		// -- 每个步骤都是独立事务执行 --

		// 步骤1 支付成功处理
		final String orderId = self().processPaySuccessStep1(payNotify);

		// 如果orderId为null则不在继续处理
		if (orderId == null)
			return;

		// 步骤2 检查订单状态并向分销下单
		exec.execute(new Runnable() {
			@Override
			public void run() {
				try {
					YXJPOrderLocalService.this.self().outOrder(orderId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * 处理全部使用卡券支付成功的订单
	 *
	 * @param payRecord 支付记录
	 * @return 易行机票订单的ID
	 * @throws CouponException
	 */
	private String processUseCouponPaySuccess(YXJPOrderPayRecord payRecord) throws CouponException {

		// 检查是否存在支付记录
		if (payRecord == null)
			throw new RuntimeException("支付记录不存在");

		// 得到所属订单
		YXJPOrder order = payRecord.getFromOrder();
		// 支付成功处理
		order.manager().processPaySuccess(payRecord);
		// 消费卡券
		if (payRecord.getCouponAmount() > 0) {
			BatchConsumeCouponCommand confirmConsumeCouponCommand = new BatchConsumeCouponCommand();
			confirmConsumeCouponCommand.setCouponIds(payRecord.getUsedCouponIds().split(",", 0));
			couponService.confirmConsumeCoupon(confirmConsumeCouponCommand);
		}

		// 更新订单
		getDao().update(order);

		// 记录订单操作日志
		logService.record(order.getId(), order.getUserInfo().getLoginName(), "使用卡券支付成功",
				"卡券ID：" + payRecord.getUsedCouponIds());

		// 释放未使用的占用卡券
		refundCouponOnPaySuccess(order);

		return order.getId();
	}

	/**
	 * 定单支付成功处理（步骤1-更改订单状态）
	 *
	 * @param payNotify 支付宝通知
	 * @return 易行机票订单的ID
	 */
	public String processPaySuccessStep1(PayNotify payNotify) {

		// 查询支付记录
		YXJPOrderPayRecordQO payRecordQO = new YXJPOrderPayRecordQO();
		payRecordQO.setPayOrderNo(payNotify.getOutTradeNo());
		YXJPOrderPayRecord payRecord = payRecordDAO.queryUnique(payRecordQO);

		// 检查是否存在支付记录
		if (payRecord == null)
			throw new RuntimeException("支付记录不存在");

		// 检查金额是否一致
		if (!payRecord.getPayAmount().equals(payNotify.getTotalFee()))
			throw new RuntimeException("支付宝通知的支付金额与支付记录金额不一致");

		// 得到所属订单
		YXJPOrder order = payRecord.getFromOrder();
		boolean alreadyPaySuccess = payRecord.getPaySuccess();

		// 支付记录成功处理
		payRecord.manager().processPaySuccess(payNotify);
		getDao().update(payRecord);

		// 检查是否已经成功，如果已经成功则返回null
		if (alreadyPaySuccess)
			return null;

		// 记录订单操作日志
		logService.record(order.getId(), "支付宝通知", String.format("用户付款成功（付款金额:%.2f）", payNotify.getTotalFee()),
				JSON.toJSONString(payNotify));

		// 支付成功处理
		order.manager().processPaySuccess(payRecord);

		// 如果支付成功处理后状态都是失败待退款（支付成功前订单被定时任务取消的情况）
		if (order.manager().isOutTicketFailureRebackWait()) {
			try {
				// 失败处理
				payRefundOnOutTicketFailure(order.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// 消费卡券
		if (payRecord.getCouponAmount() > 0) {
			try {
				BatchConsumeCouponCommand confirmConsumeCouponCommand = new BatchConsumeCouponCommand();
				confirmConsumeCouponCommand.setCouponIds(payRecord.getUsedCouponIds().split(",", 0));
				couponService.confirmConsumeCoupon(confirmConsumeCouponCommand);
			} catch (CouponException e) {
				// 记录失败日志
				HgLogger.getInstance().error(YXJPOrderLocalService.class, "zhurz", "卡券消费失败", e);
				// 记录订单操作日志
				logService.record(order.getId(), "系统后台", "卡券消费失败", "异常信息：" + HgLogger.getStackTrace(e));
			}
		}

		// 更新订单
		getDao().update(order);

		// 释放未使用的占用卡券
		try {
			refundCouponOnPaySuccess(order);
		} catch (CouponException e) {
			// 记录失败日志
			HgLogger.getInstance().error(YXJPOrderLocalService.class, "zhurz", "卡券释放失败", e);
			// 记录订单操作日志
			logService.record(order.getId(), "系统后台", "释放未使用已占用的卡券失败", "异常信息：" + HgLogger.getStackTrace(e));
		}

		return order.getId();
	}

	/**
	 * 向MQ发送订单出票成功的消息
	 *
	 * @param yxjpOrderId 易行机票订单ID
	 */
	public void sendOutTicketSuccessMessage(String yxjpOrderId) {

		YXJPOrder order = getDao().get(yxjpOrderId);
		if (order == null) return;
		Double totalPayAmount = order.getPayInfo().getTotalPayAmount();

		// 检查实际付款数额
		if (totalPayAmount != null && totalPayAmount > 0d) {

			CreateCouponCommand command = new CreateCouponCommand();
			command.setSourceDetail("订单满送");
			command.setPayPrice(totalPayAmount);
			command.setMobile(order.getUserInfo().getMobile());
			command.setUserId(order.getUserInfo().getUserId());
			command.setLoginName(order.getUserInfo().getLoginName());

			CouponMessage couponMessage = new CouponMessage();
			couponMessage.setMessageContent(command);
			couponMessage.setType(Integer.valueOf(SysProperties.getInstance().get("issue_on_full", "2")));
			couponMessage.setSendDate(new Date());

			// 向MQ发送订单满送消息
			HgLogger.getInstance().info("zhurz", "发送消息到MQ->订单满送：" + JSON.toJSONString(couponMessage));
			mqTemplate.convertAndSend("hsl.order", couponMessage);
		}

	}

	/**
	 * 检查订单状态并向分销下单
	 *
	 * @param yxjpOrderId 易行机票订单ID
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void outOrder(String yxjpOrderId) throws Exception {

		// -- 每个步骤都是独立事务执行 --

		// 步骤1 检查订单状态并向分销下单
		boolean outOrderSuccess = self().outOrderStep1(yxjpOrderId);

		// 步骤2 检查是否成功，失败后立即退款
		if (!outOrderSuccess)
			self().payRefundOnOutTicketFailure(yxjpOrderId);

	}

	/**
	 * 检查订单状态并向分销下单（步骤1）
	 *
	 * @param yxjpOrderId 易行机票订单ID
	 * @return 是否向分销下单成功
	 */
	public boolean outOrderStep1(String yxjpOrderId) {

		// 获得订单
		YXJPOrder order = getDao().get(yxjpOrderId);

		if (order == null)
			throw new ShowMessageException("订单不存在");

		// 检查是否需要向分销下单
		if (!order.manager().needOutOrder())
			return true;

		// 向分销调用下单并自动扣钱的接口
		JPAutoOrderGNResponse response = yxFlightService.autoOrder(order);
		// 处理分销接口响应
		order.manager().processOutOrderResponse(response);
		// 更新订单状态
		getDao().update(order);

		// 成功
		if ("T".equals(response.getIs_success())) {
			// 短信通知
			smsService.sendSmsOnOutOrderSuccess(order);
			return true;
		}

		return false;
	}

	/**
	 * <pre>
	 * 取消未支付订单（管理员操作，必须附带fromAdminId）
	 * 当订单中的乘客只有取消未付款状态时，解除这个订单的卡券占用
	 * </pre>
	 *
	 * @param command 取消订单command
	 */
	public void cancelNoPayOrder(CancelNoPayYXJPOrderCommand command) {

		// 检查管理员
		Staff staff = staffDao.get(command.getFromAdminId());
		if (staff == null)
			throw new ShowMessageException("非法的操作用户");

		// 获取订单
		YXJPOrder order = getDao().get(command.getOrderId());

		// 检查订单是否存在
		if (order == null)
			throw new ShowMessageException("订单不存在");

		// 取消未支付乘客
		List<YXJPOrderPassenger> cancelPassengers = order.manager().cancelNoPayOrder(command);
		getDao().update(order);

		// 记录订单操作日志
		logService.record(order.getId(), staff.getAuthUser().getLoginName(),
				String.format("取消订单：%s", passengerNames(cancelPassengers)),
				JSON.toJSONString(command));

		// 释放卡券占用
		try {
			refundCouponOnCancel(order);
		} catch (CouponException e) {
			logService.record(order.getId(), "系统后台", "当乘客全部取消释放卡券时出现异常：" + e.getMessage(),
					"异常信息：" + HgLogger.getStackTrace(e));
		}
	}

	/**
	 * 申请退废票（管理员操作，必须附带fromAdminId）
	 *
	 * @param command 申请退废票command
	 */
	public void applyRefundOrder(ApplyRefundYXJPOrderCommand command) {

		// 检查管理员
		Staff staff = staffDao.get(command.getFromAdminId());
		if (staff == null)
			throw new ShowMessageException("非法的操作用户");

		// 获取订单
		YXJPOrder order = getDao().get(command.getOrderId());

		// 检查订单是否存在
		if (order == null)
			throw new ShowMessageException("订单不存在");

		// 创建退废票申请
		YXJPOrderRefundApply apply = order.manager().createRefundOrderApply(command, staff.getAuthUser().getLoginName());
		// 调用分销退废票申请
		RefundTicketGNResponse response = yxFlightService.applyRefundOrder(apply);
		// 处理退废票申请响应
		order.manager().processRefundOrderApplyResponse(apply, response);

		// 更新订单信息并保存退废票申请信息
		getDao().update(order);
		getDao().save(apply);

		// 短信通知
		smsService.sendSmsOnRefundApply(apply);

	}

	/**
	 * 处理出票成功通知
	 *
	 * @param notify 出票成功通知
	 */
	public void processOutTicketSuccessNotify(OutTicketSuccessNotify notify) {

		// 检查订单号
		if (StringUtils.isBlank(notify.getDealerOrderCode()))
			return;

		// 查询订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setOrderNo(notify.getDealerOrderCode());
		YXJPOrder order = getDao().queryUnique(qo);
		if (order == null)
			return;

		// 记录操作日志
		logService.record(order.getId(), "分销通知", "出票成功", "分销通知消息：" + JSON.toJSONString(notify));

		// 如果订单全部已出票或取消，则不继续往下执行
		if (order.manager().isAllOutTicketSuccessOrCancelNoPay())
			return;

		// 处理订单出票成功通知
		order.manager().processOutTicketSuccessNotify(notify);
		getDao().update(order);

		// 短信通知
		smsService.sendSmsOnOutTicketSuccess(order);

		// 向MQ发送订单满送消息
		sendOutTicketSuccessMessage(order.getId());
	}

	/**
	 * 处理拒绝出票通知
	 *
	 * @param notify 绝出票通知
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void processOutTicketRefuseNotify(OutTicketRefuseNotify notify) {

		// -- 每个步骤都是独立事务执行 --

		// 步骤1 处理拒绝出票通知
		String orderId = self().processOutTicketRefuseNotifyStep1(notify);

		// 步骤2 检查是否成功，失败后立即退款
		try {
			self().payRefundOnOutTicketFailure(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 处理拒绝出票通知（步骤1）
	 *
	 * @param notify 绝出票通知
	 * @return
	 */
	public String processOutTicketRefuseNotifyStep1(OutTicketRefuseNotify notify) {

		// 检查订单号
		if (StringUtils.isBlank(notify.getDealerOrderCode()))
			throw new RuntimeException("缺少订单号");

		// 查询订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setOrderNo(notify.getDealerOrderCode());
		YXJPOrder order = getDao().queryUnique(qo);
		if (order == null)
			throw new RuntimeException("订单不存在");

		// 处理订单拒绝出票通知
		order.manager().processOutTicketRefuseNotify(notify);
		getDao().update(order);

		// 记录操作日志
		logService.record(order.getId(), "分销通知", String.format("拒绝出票，理由：%s。", notify.getRefuseMemo()),
				"分销通知消息：" + JSON.toJSONString(notify));

		return order.getId();
	}

	/**
	 * 处理分销申请退废票通知
	 *
	 * @param notify 分销申请退废票通知
	 */
	public void processApplyRefundTicketNotify(ApplyRefundTicketNotify notify) {

		// 检查订单号
		if (StringUtils.isBlank(notify.getDealerOrderCode()))
			return;

		// 查询订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setOrderNo(notify.getDealerOrderCode());
		YXJPOrder order = getDao().queryUnique(qo);
		if (order == null)
			return;

		// 记录操作日志
		logService.record(order.getId(), "分销通知",
				String.format("申请退废票：%s|%s|%s", notify.passengerNames(),
						YXJPOrderRefundApply.REFUND_TYPE_MAP.get(notify.getRefundType()),
						notify.getRefundMemo()),
				JSON.toJSONString(notify));

		// 根据分销申请退废票通知创建退废票申请记录
		YXJPOrderRefundApply apply = order.manager().createOrderRefundApplyByNotify(notify);

		// 如果申请退废票的乘客已经改变状态则不继续执行
		if (apply.getPassengers().size() == 0)
			return;

		// 更新订单状态并保存退废票申请
		getDao().update(order);
		getDao().save(apply);

		// 短信通知
		smsService.sendSmsOnRefundApply(apply);
	}

	/**
	 * 处理退废票通知
	 *
	 * @param notify 退废票成功通知
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void processRefundTicketNotify(RefundTicketNotify notify) {

		// -- 每个步骤都是独立事务执行 --

		// 步骤1 处理退废票通知
		Map<String, List<String>> orderPassengerMap = self().processRefundTicketNotifyStep1(notify);

		// 步骤2 检查是否成功，退废票成功后立即退款
		if (orderPassengerMap != null) {
			for (Map.Entry<String, List<String>> entry : orderPassengerMap.entrySet()) {
				try {
					self().payRefundOnRefundSuccess(entry.getKey(), entry.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 处理退废票通知（步骤1）
	 *
	 * @param notify 退废票成功通知
	 * @return <订单ID, 退废票成功的乘客ID>
	 */
	public Map<String, List<String>> processRefundTicketNotifyStep1(RefundTicketNotify notify) {

		// 检查订单号
		if (StringUtils.isBlank(notify.getDealerOrderCode()))
			throw new RuntimeException("缺少订单号");

		// 查询订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setOrderNo(notify.getDealerOrderCode());
		YXJPOrder order = getDao().queryUnique(qo);
		if (order == null)
			throw new RuntimeException("订单不存在");

		// 处理订单退废票通知
		YXJPOrderRefundResult refundResult = order.manager().processRefundTicketNotify(notify);

		// 更新订单状态并保存退废票申请
		getDao().update(order);
		getDao().save(refundResult);

		// 记录操作日志
		logService.record(order.getId(), "分销通知",
				String.format("乘机人：%s，退废票%s。",
						passengerNames(refundResult.getPassengers()),
						refundResult.success() ? "成功" : "失败，原因：" + refundResult.getRefuseMemo()),
				"分销通知消息：" + JSON.toJSONString(notify));

		// 短信通知
		if (refundResult.success())
			smsService.sendSmsOnRefundSuccess(refundResult);
		else
			smsService.sendSmsOnRefundFailure(refundResult);

		// 记录退废票成功的乘客ID并返回
		if (refundResult.success()) {
			Map<String, List<String>> orderPassengerMap = new HashMap<String, List<String>>();
			orderPassengerMap.put(order.getId(), new ArrayList<String>());
			for (YXJPOrderPassenger passenger : refundResult.getPassengers())
				orderPassengerMap.get(order.getId()).add(passenger.getId());
			return orderPassengerMap;
		}

		return null;
	}

	/**
	 * <pre>
	 * 退款操作（场景：出票失败退款）
	 * 出票失败待退款是全退包括卡券，
	 * </pre>
	 *
	 * @param orderId 易行机票订单ID
	 */
	public void payRefundOnOutTicketFailure(String orderId) throws Exception {

		// 查询订单
		YXJPOrder order = getDao().get(orderId);
		if (order == null)
			return;

		// 检查是否符合状态
		if (!order.manager().isOutTicketFailureRebackWait())
			return;

		// 创建批量退款记录（场景：出票失败退款）
		YXJPOrderPayBatchRefundRecord batchRefundRecord = order.manager().createPayBatchRefundRecordOnOutTicketFailure(
				AppInfoUtils.getAlipayRemarkPrefix());

		// 退还卡券
		List<CouponDTO> couponDTOs = couponLocalService.orderRefund(new OrderRefundCommand(order.getBaseInfo().getOrderNo()));
		if (couponDTOs != null && couponDTOs.size() > 0) {
			StringBuilder couponInfo = new StringBuilder();
			for (CouponDTO couponDTO : couponDTOs) {
				if (couponInfo.length() > 0)
					couponInfo.append(",");
				couponInfo.append(couponDTO.getId());
			}
			// 记录操作日志
			logService.record(order.getId(), "系统后台", "出票失败，退还卡券。", "卡券ID：" + couponInfo);
		}

		// 更改“需要退款的金额为0”且“状态为出票失败待退款”的乘客状态为“出票失败已退款”
		order.manager().processNoPayRefundPassengerToRebackSuccess();

		// 更新订单
		getDao().update(order);

		// 检查是否需要发起退款操作
		if (batchRefundRecord.getBatchNum() > 0) {
			// 保存批量退款记录
			getDao().save(batchRefundRecord);
			// 支付宝退款通知地址
			String notifyUrl = SysProperties.getInstance().get("base_url_web") + "/gate/alipay/yxjp-refund-notify";
			PayRefundRequest payRefundRequest = batchRefundRecord.manager().convertToPayRefundRequest(notifyUrl);
			PayRefundResponse payRefundResponse = AliPayUtils.payRefund(payRefundRequest, alipayConfig);

			// 记录操作日志
			logService.recordOnNewTx(order.getId(), "系统后台", "出票失败，退款。", "支付宝退款请求：" + JSON.toJSONString(payRefundRequest));

			// 失败抛出异常
			if (!payRefundResponse.isSuccess()) {
				// 记录操作日志
				logService.recordOnNewTx(order.getId(), "系统后台", String.format("调用支付宝退款失败，错误代码：%s。", payRefundResponse.getError()),
						String.format("支付宝退款响应：%s。", payRefundResponse.getResponseXml()));
				throw new RuntimeException("退款请求失败，错误代码：" + payRefundResponse.getError());
			} else {
				// 通知分销已经退款
				final Double totalRefundAmount = batchRefundRecord.totalRefundAmount();
				exec.execute(new Runnable() {
					@Override
					public void run() {
						yxFlightService.updatePayBalances(totalRefundAmount);
					}
				});
			}
		}

		// 短信通知
		smsService.sendSmsOnOutTicketFailure(order);

	}

	/**
	 * <pre>
	 * 退款操作（场景：退废票成功退款）
	 * 退废成功待退款是用户实付-退废票手续费，如果手续费超过实付则不退款，卡券不退。
	 * </pre>
	 *
	 * @param orderId      易行机票订单ID
	 * @param passengerIds 乘客ID
	 */
	public void payRefundOnRefundSuccess(String orderId, List<String> passengerIds) throws Exception {

		// 查询订单
		YXJPOrder order = getDao().get(orderId);
		if (order == null)
			return;

		// 创建批量退款记录（场景：出票失败退款）
		YXJPOrderPayBatchRefundRecord batchRefundRecord = order.manager().createPayBatchRefundRecordOnRefundSuccess(
				passengerIds, AppInfoUtils.getAlipayRemarkPrefix());

		// 更新订单和保存批量退款记录
		getDao().update(order);

		// 检查是否需要发起退款操作
		if (batchRefundRecord.getBatchNum() > 0) {
			// 保存批量退款记录
			getDao().save(batchRefundRecord);
			// 支付宝退款通知地址
			String notifyUrl = SysProperties.getInstance().get("base_url_web") + "/gate/alipay/yxjp-refund-notify";
			PayRefundRequest payRefundRequest = batchRefundRecord.manager().convertToPayRefundRequest(notifyUrl);
			PayRefundResponse payRefundResponse = AliPayUtils.payRefund(payRefundRequest, alipayConfig);

			// 记录操作日志
			logService.recordOnNewTx(order.getId(), "系统后台", "退废票成功，退款。", "支付宝退款请求：" + JSON.toJSONString(payRefundRequest));

			// 失败抛出异常
			if (!payRefundResponse.isSuccess()) {
				// 记录操作日志
				logService.recordOnNewTx(order.getId(), "系统后台", String.format("调用支付宝退款失败，错误代码：%s。", payRefundResponse.getError()),
						String.format("支付宝退款响应：%s。", payRefundResponse.getResponseXml()));
				throw new RuntimeException("退款请求失败，错误代码：" + payRefundResponse.getError());
			} else {
				// 通知分销已经退款
				final Double totalRefundAmount = batchRefundRecord.totalRefundAmount();
				exec.execute(new Runnable() {
					@Override
					public void run() {
						yxFlightService.updatePayBalances(totalRefundAmount);
					}
				});
			}
		}

	}

	/**
	 * 处理支付宝退款通知
	 *
	 * @param notify 支付宝退款通知
	 */
	public void processPayRefundNotify(PayRefundNotify notify) {

		// 查询批量退款记录
		YXJPOrderPayBatchRefundRecordQO qo = new YXJPOrderPayBatchRefundRecordQO();
		qo.setBatchNo(notify.getBatchNo());
		YXJPOrderPayBatchRefundRecord batchRefundRecord = payBatchRefundRecordDAO.queryUnique(qo);
		if (batchRefundRecord == null)
			throw new RuntimeException("退款记录不存在");

		// 处理批量退款结果
		List<YXJPOrder> orders = batchRefundRecord.manager().processPayRefundNotify(notify);

		// 更新批量退款记录状态和机票订单的状态
		getDao().update(batchRefundRecord);
		getDao().updateList(orders);

		// 记录操作信息
		for (YXJPOrder order : orders) {
			logService.record(order.getId(), "支付宝通知", "支付宝退款成功", "通知信息：" + JSON.toJSONString(notify));
		}

	}

	/**
	 * <pre>
	 * 取消支付超时订单
	 * 当订单中的乘客只有取消未付款状态时，解除这个订单的卡券占用
	 * </pre>
	 *
	 * @param maxCount 最大订单处理数量
	 * @param timeout  超时时间（单位：分钟）
	 */
	public void cancelTimeoutOrder(int maxCount, int timeout) {
		YXJPOrderQO qo = new YXJPOrderQO();
		YXJPOrderPassengerQO passengerQO = new YXJPOrderPassengerQO();
		qo.setPassengerQO(passengerQO);
		passengerQO.setStatus(YXJPOrderPassengerTicket.STATUS_PAY_WAIT);
		qo.setCreateDateEnd(DateUtils.addMinutes(new Date(), -timeout));
		qo.setCreateDateOrder(1);
		List<YXJPOrder> orders = getDao().queryList(qo, 0, maxCount);
		// 取消超过20分钟未支付的订单
		for (YXJPOrder order : orders) {

			List<YXJPOrderPassenger> cancelPassengers = order.manager().cancelTimeoutNoPayOrder();

			// 记录操作日志
			logService.record(order.getId(), "系统后台", "定时取消超过" + timeout + "分钟未支付的订单，取消的乘客：" + passengerNames(cancelPassengers),
					"当前最大订单处理数量：" + maxCount);

			// 释放卡券占用
			try {
				refundCouponOnCancel(order);
			} catch (CouponException e) {
				logService.record(order.getId(), "系统后台", "当乘客全部取消释放卡券时出现异常：" + e.getMessage(),
						"异常信息：" + HgLogger.getStackTrace(e));
			}
		}

		// 更新订单
		getDao().updateList(orders);
	}

	/**
	 * 当订单取消时释放占用卡券
	 *
	 * @param order 易行机票订单
	 * @throws CouponException
	 */
	private void refundCouponOnCancel(YXJPOrder order) throws CouponException {

		// 检查是否全部取消未付款
		if (!order.manager().isAllCancelNoPay())
			return;

		// 退还卡券
		List<CouponDTO> couponDTOs = couponLocalService.orderRefund(new OrderRefundCommand(order.getBaseInfo().getOrderNo()));
		if (couponDTOs != null && couponDTOs.size() > 0) {
			StringBuilder couponInfo = new StringBuilder();
			for (CouponDTO couponDTO : couponDTOs) {
				if (couponInfo.length() > 0)
					couponInfo.append(",");
				couponInfo.append(couponDTO.getId());
			}
			// 记录操作日志
			logService.record(order.getId(), "系统后台", "订单全部取消，释放占用卡券。", "卡券ID：" + couponInfo);
		}

	}

	/**
	 * 当订单支付成功时释放未使用的占用卡券
	 *
	 * @param order 易行机票订单
	 * @throws CouponException
	 */
	private void refundCouponOnPaySuccess(YXJPOrder order) throws CouponException {

		// 检查是否全部取消未付款
		if (!order.manager().isAllTicketDealingOrCancelNoPay())
			return;

		// 退还卡券
		List<CouponDTO> couponDTOs = couponLocalService.orderRefund(new OrderRefundCommand(order.getBaseInfo().getOrderNo()), false);
		if (couponDTOs != null && couponDTOs.size() > 0) {
			StringBuilder couponInfo = new StringBuilder();
			for (CouponDTO couponDTO : couponDTOs) {
				if (couponInfo.length() > 0)
					couponInfo.append(",");
				couponInfo.append(couponDTO.getId());
			}
			// 记录操作日志
			logService.record(order.getId(), "系统后台", "订单支付完成，释放已占用未使用卡券。", "卡券ID：" + couponInfo);
		}

	}

	/**
	 * 检查退废票成功且不需要退款的订单改变状态为退废成功已退款
	 *
	 * @param maxCount 最大订单处理数量
	 */
	public void checkRefundSuccessRebackWaitPassengerToRebackSuccess(int maxCount) {
		YXJPOrderQO qo = new YXJPOrderQO();
		YXJPOrderPassengerQO passengerQO = new YXJPOrderPassengerQO();
		qo.setPassengerQO(passengerQO);
		qo.setCreateDateOrder(1);
		passengerQO.setStatus(YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_SUCC_REBACK_WAIT);

		List<YXJPOrder> orders = getDao().queryList(qo, 0, maxCount);

		for (YXJPOrder order : orders) {
			List<YXJPOrderPassenger> rebackSuccessPassengers = order.manager().processRefundSuccessRebackWaitPassengerToRebackSuccess();
			if (rebackSuccessPassengers.size() > 0)
				// 记录操作日志
				logService.record(order.getId(), "系统后台", "定时更改退废票成功且不需要退款的乘客状态为退废成功已退款，涉及的乘客：" + passengerNames(rebackSuccessPassengers),
						"当前最大订单处理数量：" + maxCount);
		}

		// 更新订单
		getDao().updateList(orders);
	}

	/**
	 * 得到乘客的姓名拼接字符串，顿号分隔
	 *
	 * @param passengers 乘客
	 * @return
	 */
	private String passengerNames(List<YXJPOrderPassenger> passengers) {
		StringBuilder passengerNames = new StringBuilder();
		for (YXJPOrderPassenger passenger : passengers) {
			if (passengerNames.length() > 0)
				passengerNames.append("、");
			passengerNames.append(passenger.getBaseInfo().getName());
		}
		return passengerNames.toString();
	}

	/**
	 * 查询公司成员信息
	 *
	 * @param memberId 成员ID
	 * @return
	 */
	public Member getMemberById(String memberId) {
		if (StringUtils.isBlank(memberId)) return null;
		HslMemberQO memberQO = new HslMemberQO();
		memberQO.setId(memberId);
		return memberDao.queryUnique(memberQO);
	}

}

