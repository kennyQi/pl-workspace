package plfx.gjjp.app.service.local;

import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.redisson.Redisson;
import org.redisson.core.RAtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.api.client.api.v1.gj.dto.GJJPOrderDTO;
import plfx.api.client.api.v1.gj.request.ApplyCancelNoPayOrderGJCommand;
import plfx.api.client.api.v1.gj.request.ApplyCancelOrderGJCommand;
import plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand;
import plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand.RefundPassengerInfo;
import plfx.api.client.api.v1.gj.request.CreateJPOrderGJCommand;
import plfx.api.client.api.v1.gj.request.JPOrderGJQO;
import plfx.api.client.api.v1.gj.request.PayToJPOrderGJCommand;
import plfx.api.client.api.v1.gj.response.ApplyCancelNoPayOrderGJResponse;
import plfx.api.client.api.v1.gj.response.ApplyCancelOrderGJResponse;
import plfx.api.client.api.v1.gj.response.ApplyRefundTicketGJResponse;
import plfx.api.client.api.v1.gj.response.PayToJPOrderGJResponse;
import plfx.api.client.common.ApiResponse;
import plfx.gjjp.app.common.adapter.GJJPApiAdapter;
import plfx.gjjp.app.dao.GJJPOrderDao;
import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.gjjp.domain.model.GJPassenger;
import plfx.jp.app.common.PropertiesDict;
import plfx.jp.app.common.util.RemoteConfigUtils;
import plfx.jp.app.dao.dealer.DealerDAO;
import plfx.jp.app.dao.supplier.SupplierDAO;
import plfx.jp.app.service.local.policy.PolicySnapshotLocalService;
import plfx.jp.command.pub.gj.CreateGJJPOrderLogCommand;
import plfx.jp.common.ThreadTrackingTokenGenerator;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import plfx.jp.domain.model.supplier.Supplier;
import plfx.jp.pojo.system.SupplierCode;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.yxgjclient.pojo.param.ApplyCancelNoPayParams;
import plfx.yxgjclient.pojo.param.ApplyCancelParams;
import plfx.yxgjclient.pojo.param.ApplyRefundParams;
import plfx.yxgjclient.pojo.param.ApplyRefundResult;
import plfx.yxgjclient.pojo.param.AvailableJourney;
import plfx.yxgjclient.pojo.param.PassengerInfo;
import plfx.yxgjclient.pojo.param.PayAutoParams;
import plfx.yxgjclient.pojo.param.PerOperationIsSuc;
import plfx.yxgjclient.pojo.param.RewPolicyInfo;
import plfx.yxgjclient.pojo.request.ApplyCancelNoPayRQ;
import plfx.yxgjclient.pojo.request.ApplyCancelRQ;
import plfx.yxgjclient.pojo.request.ApplyRefundRQ;
import plfx.yxgjclient.pojo.request.PayAutoRQ;
import plfx.yxgjclient.pojo.response.ApplyCancelNoPayRS;
import plfx.yxgjclient.pojo.response.ApplyCancelRS;
import plfx.yxgjclient.pojo.response.ApplyRefundRS;
import plfx.yxgjclient.pojo.response.CreateOrderRS;
import plfx.yxgjclient.pojo.response.PayAutoRS;
import plfx.yxgjclient.service.YIGJService;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：国际机票处理API请求本地服务
 * @类修改者：
 * @修改日期：2015-7-20下午1:57:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-20下午1:57:06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GJJPOrderApiLocalService {

	@Autowired
	private GJJPOrderDao jpOrderDao;

	@Autowired
	private DealerDAO dealerDAO;
	@Autowired
	private SupplierDAO supplierDAO;

	@Autowired
	private PolicySnapshotLocalService policySnapshotLocalService;
	
	@Autowired
	private GJJPOrderLogLocalService orderLogLocalService;

	@Autowired
	private YIGJService yigjService;
	
	@Resource
	private Redisson redisson;
	
	public final static String GJJP_ORDER_SEQ = "PLFX:GJJP_ORDER_SEQ:";
	
	private final static String devName = "zhurz";
	private HgLogger getHgLogger() {
		return HgLogger.getInstance();
	}
	
	/**
	 * @方法功能说明：生成平台订单ID
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-17下午3:02:41
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String generatOrderId(CreateJPOrderGJCommand command) {
		// 根据规则生成ID
		// 订单编号 时间戳＋订单来源＋订单类型＋流水号，订单编号长度16位；
		// 时间戳年份按照A-Z取一位(2015=A)，月份取一位16进制数字，日期2位数字；时间精确到秒；
		// 订单来源0来自移动端，1来自pc端；
		// 订单类型0机票，1门票，2线路，3酒店；最后是流水号；
		// 分销平台订单编号 分销商id＋订单编号
		Calendar now = Calendar.getInstance();
		// 默认来源为PC端
		int fromClientType = command.getFromClientType() == null ? 1 : command.getFromClientType();
		String year = String.format("%c", 'A' + (now.get(Calendar.YEAR) - 2015));
		String month = String.format("%x", now.get(Calendar.MONTH) + 1);
		String date = DateUtil.formatDateTime(now.getTime(), "MMddHHmmss");
		String dealerCode = command.getFromDealerCode();
		String orderNoPre = String.format("%s%s%s%s%d4", dealerCode, year, month, date, fromClientType).toUpperCase();
		RAtomicLong atomicLong = redisson.getAtomicLong(GJJP_ORDER_SEQ + orderNoPre);
		long no = atomicLong.incrementAndGet();
		atomicLong.expire(10, TimeUnit.SECONDS);
		return String.format("%s%02d", orderNoPre, no);
	}
	
	
	/**
	 * @方法功能说明：检查经销商订单ID是否重复
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-29下午3:57:15
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean dealerOrderIdIsExist(String dealerId, String dealerOrderId) {
		GJJPOrderQo qo = new GJJPOrderQo();
		qo.setDealerOrderIds(Arrays.asList(new String[] { dealerOrderId }));
		DealerQO dealerQO = new DealerQO();
		dealerQO.setId(dealerId);
		qo.setFromDealerQo(dealerQO);
		return jpOrderDao.queryCount(qo) > 0 ? true : false;
	}

	/**
	 * @方法功能说明：创建平台订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午3:17:08
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:GJJPOrder
	 * @throws
	 */
	public GJJPOrder createPlatformOrder(CreateJPOrderGJCommand command,
			AvailableJourney availableJourney, RewPolicyInfo rewPolicyInfo) {

		// 检查经销商订单号是否重复
		if (dealerOrderIdIsExist(command.getFromDealerId(), command.getDealerOrderId()))
			return null;

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建平台订单->createPlatformOrder(%s, %s, %s)", trackingToken,
						JSON.toJSONString(command, true), JSON.toJSONString(availableJourney, true), 
						JSON.toJSONString(rewPolicyInfo, true)), trackingToken);
		
		GJJPOrder jpOrder = new GJJPOrder();
		String orderId = generatOrderId(command);
		JPPlatformPolicySnapshot policySnapshot = policySnapshotLocalService.getPolicySnapshot(command.getFromDealerId(), SupplierCode.YEEXING_GJ);

		SupplierQO supplierQO = new SupplierQO();
		supplierQO.setCode(SupplierCode.YEEXING_GJ);
		Supplier supplier = supplierDAO.queryUnique(supplierQO);
		Dealer dealer = dealerDAO.get(command.getFromDealerId());

		jpOrder.create(command, orderId, availableJourney, rewPolicyInfo, dealer, supplier, policySnapshot);

		jpOrderDao.save(jpOrder);
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建平台订单->创建成功(%s)", trackingToken,
						orderId), trackingToken);

		return jpOrder;
	}

	/**
	 * @方法功能说明：创建供应商订单结束
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:16:03
	 * @修改内容：
	 * @参数：@param orderId
	 * @参数：@param createOrderRS
	 * @参数：@return
	 * @return:JPOrderDTO
	 * @throws
	 */
	public GJJPOrderDTO createSupplierOrderOver(String orderId, CreateOrderRS createOrderRS) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建供应商订单结束->createSupplierOrderOver(%s, %s)", trackingToken,
						orderId, JSON.toJSONString(createOrderRS, true)), trackingToken);
		
		// 下单失败时删除平台订单
		GJJPOrder jpOrder = jpOrderDao.get(orderId);
		if (jpOrder == null)
			return null;
		
		// 删除失败订单
		if (!"T".equals(createOrderRS.getIsSuccess())) {
			jpOrderDao.delete(jpOrder);
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->创建供应商订单结束->删除失败订单(%s)", trackingToken,
							orderId), trackingToken);
			return null;
		}

		Integer supplierPayPlatform = Integer.valueOf(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_PAY_PLATFORM, "2"));
		jpOrder.createSupplierOrderOver(createOrderRS.getCreateOrderResult(), supplierPayPlatform);
		jpOrderDao.update(jpOrder);
		
		return GJJPApiAdapter.order.convertDTO(jpOrder);
	}

	/**
	 * @方法功能说明：为国际机票订单付款
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:38:57
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:PayToJPOrderGJResponse
	 * @throws
	 */
	@Transactional(noRollbackFor = Exception.class)
	public PayToJPOrderGJResponse payToJPOrder(PayToJPOrderGJCommand command) {
		
		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->为国际机票订单付款->payToJPOrder(%s)", trackingToken,
						JSON.toJSONString(command, true)), trackingToken);

		PayToJPOrderGJResponse response = new PayToJPOrderGJResponse();
		GJJPOrder jpOrder = jpOrderDao.get(command.getPlatformOrderId());

		// 检查订单是否存在
		if (jpOrder == null) {
			response.setResult(PayToJPOrderGJResponse.RESULT_NOT_EXIST);
			response.setMessage("订单不存在");
			return response;
		} else {
			response.setPlatformOrderId(jpOrder.getId());
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->为国际机票订单付款->检查订单是否存在通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 检查来源
		if (!jpOrder.getBaseInfo().getFromDealer().getId().equals(command.getFromDealerId())) {
			response.setResult(ApiResponse.RESULT_NO_AUTH);
			response.setMessage("无权操作");
			return response;
		} else {
			response.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->为国际机票订单付款->检查来源通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 非待支付状态
		if (!GJJPConstants.ORDER_STATUS_PAY_WAIT.equals(jpOrder.getStatus().getCurrentValue())
				|| jpOrder.getPayInfo().getTotalPrice() == null
				|| jpOrder.getPayInfo().getSupplierTotalPrice() == null) {
			response.setResult(PayToJPOrderGJResponse.RESULT_CANNOT_PAY);
			response.setMessage("当前订单不能支付");
			return response;
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->为国际机票订单付款->检查支付状态通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 检查支付金额
		if (!jpOrder.getPayInfo().getTotalPrice().equals(command.getTotalPrice())) {
			response.setResult(PayToJPOrderGJResponse.RESULT_PRICE_ERROR);
			response.setMessage("价格错误");
			response.setTotalPrice(jpOrder.getPayInfo().getTotalPrice());
			return response;
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->为国际机票订单付款->检查支付金额通过(%.2f)", trackingToken,
						command.getTotalPrice()), trackingToken);

		// 装配供应商接口参数
		PayAutoRQ payAutoRQ = new PayAutoRQ();
		PayAutoParams payAutoParams = new PayAutoParams();
		payAutoRQ.setPayAutoParams(payAutoParams);
		payAutoParams.setOrderId(jpOrder.getBaseInfo().getSupplierOrderId());
		payAutoParams.setExtOrderId(jpOrder.getId());
		payAutoParams.setTotalPrice(String.format("%.2f", jpOrder.getPayInfo().getSupplierTotalPrice()));
		// 1—汇付2—支付宝(生产环境用支付宝,测试开发环境用汇付)
		payAutoParams.setPayPlatform(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_PAY_PLATFORM, "2"));
		payAutoParams.setPayNotifyUrl(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_NOTIFY_URL));
		payAutoParams.setOutNotifyUrl(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_NOTIFY_URL));

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->为国际机票订单付款->yigjService.payAuto(%s)", trackingToken,
						JSON.toJSONString(payAutoRQ, true)), trackingToken);

		// 调用供应商自动扣款
		PayAutoRS payAutoRS = yigjService.payAuto(payAutoRQ);
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->为国际机票订单付款->接口返回结果(%s)", trackingToken,
						JSON.toJSONString(payAutoRS, true)), trackingToken);

		// 供应商自动扣款成功
		if ("T".equals(payAutoRS.getIsSuccess())) {

			Integer supplierPayPlatform = Integer.valueOf(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_PAY_PLATFORM, "2"));
			jpOrder.payToSupplierSuccess(supplierPayPlatform, payAutoRS.getPayAutoResult()
					.getTradeNo(), jpOrder.getPayInfo().getSupplierTotalPrice());
			jpOrderDao.update(jpOrder);

			getHgLogger().info(getClass(), devName,
					String.format("(%s)->为国际机票订单付款->供应商自动扣款成功(%s)", trackingToken,
							jpOrder.getId()), trackingToken);
		} else {

			getHgLogger().info(getClass(), devName,
					String.format("(%s)->为国际机票订单付款->供应商自动扣款失败(%s)", trackingToken,
							jpOrder.getId()), trackingToken);

			// 记录操作日志
			orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
					.getId(), "为订单付款", command.getFromDealerCode(),
					GJJPConstants.LOG_WORKER_TYPE_DEALER, "供应商自动扣款失败"));

			response.setResult(ApiResponse.RESULT_FAILURE);
			response.setMessage("付款失败");
			
			return response;
		}

		response.setResult(ApiResponse.RESULT_SUCCESS);
		response.setTotalPrice(jpOrder.getPayInfo().getTotalPrice());

		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "为订单付款", command.getFromDealerCode(),
				GJJPConstants.LOG_WORKER_TYPE_DEALER, "为订单付款成功"));

		return response;
	}

	/**
	 * @方法功能说明：查询国际机票订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:40:38
	 * @修改内容：
	 * @参数：@param QO
	 * @参数：@return
	 * @return:JPOrderGJResponse
	 * @throws
	 */
	public List<GJJPOrderDTO> queryJPOrder(JPOrderGJQO QO) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询国际机票订单->queryJPOrder(%s)", trackingToken,
						JSON.toJSONString(QO, true)), trackingToken);

		List<GJJPOrderDTO> dtoList = new ArrayList<GJJPOrderDTO>();

		if ((QO.getDealerOrderIds() == null || QO.getDealerOrderIds().size() == 0)
			&& (QO.getPlatformOrderIds() == null || QO.getPlatformOrderIds().size() == 0))
			return dtoList;

		GJJPOrderQo jpOrderQo = new GJJPOrderQo();
		DealerQO dealerQO = new DealerQO();
		dealerQO.setId(QO.getFromDealerId());
		jpOrderQo.setInitAll(true);
		jpOrderQo.setFromDealerQo(dealerQO);
		jpOrderQo.setIds(QO.getPlatformOrderIds());
		jpOrderQo.setDealerOrderIds(QO.getDealerOrderIds());

		List<GJJPOrder> list = jpOrderDao.queryList(jpOrderQo);
		for (GJJPOrder jpOrder : list)
			dtoList.add(GJJPApiAdapter.order.convertDTO(jpOrder));

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询国际机票订单->查询结果(%s)", trackingToken,
						JSON.toJSONString(dtoList, true)), trackingToken);

		return dtoList;
	}

	/**
	 * @方法功能说明：申请取消未付款的订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:42:52
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApplyCancelNoPayOrderGJResponse
	 * @throws
	 */
	public ApplyCancelNoPayOrderGJResponse applyCancelNoPayOrder(ApplyCancelNoPayOrderGJCommand command) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->applyCancelNoPayOrder(%s)", trackingToken,
						JSON.toJSONString(command, true)), trackingToken);
		
		ApplyCancelNoPayOrderGJResponse response = new ApplyCancelNoPayOrderGJResponse();
		GJJPOrder jpOrder = jpOrderDao.get(command.getPlatformOrderId());

		// 检查订单是否存在
		if (jpOrder == null) {
			response.setResult(ApplyCancelNoPayOrderGJResponse.RESULT_NOT_EXIST);
			response.setMessage("订单不存在");
			return response;
		} else {
			response.setPlatformOrderId(jpOrder.getId());
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->检查订单是否存在通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 检查来源
		if (!jpOrder.getBaseInfo().getFromDealer().getId().equals(command.getFromDealerId())) {
			response.setResult(ApiResponse.RESULT_NO_AUTH);
			response.setMessage("无权操作");
			return response;
		} else {
			response.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->检查来源通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 状态检查
		Integer orderStatus = jpOrder.getStatus().getCurrentValue();
		if (GJJPConstants.ORDER_STATUS_CANCEL_ALREADY.equals(orderStatus)) {
			response.setResult(ApplyCancelNoPayOrderGJResponse.RESULT_CANCEL_ALREADY);
			response.setMessage("订单已经被取消");
			return response;
		}
		if (!(GJJPConstants.ORDER_STATUS_ORDER_TO_SUPPLIER.equals(orderStatus)
			|| GJJPConstants.ORDER_STATUS_PAY_WAIT.equals(orderStatus) 
			|| GJJPConstants.ORDER_STATUS_AUDIT_FAIL.equals(orderStatus))) {
			response.setResult(ApplyCancelNoPayOrderGJResponse.RESULT_PAID_OR_AUDIT);
			response.setMessage("订单已被支付过或正在审核");
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->状态检查通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 参数检查
		if (Integer.valueOf(6).equals(command.getCancelReasonType())
			&& StringUtils.isBlank(command.getCancelOtherReason())) {
			response.setResult(ApplyCancelNoPayOrderGJResponse.RESULT_NO_CANCEL_OTHER_REASON);
			response.setMessage("缺少其他原因具体内容");
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->参数检查通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		ApplyCancelNoPayRQ cancelNoPayRQ = new ApplyCancelNoPayRQ();
		ApplyCancelNoPayParams cancelNoPayParams = new ApplyCancelNoPayParams();
		cancelNoPayRQ.setApplyCancelNoPayParams(cancelNoPayParams);
		cancelNoPayParams.setOrderId(jpOrder.getBaseInfo().getSupplierOrderId());
		cancelNoPayParams.setExtOrderId(jpOrder.getId());
		cancelNoPayParams.setCancelReasonType(command.getCancelReasonType().toString());
		cancelNoPayParams.setCancelOtherReason(command.getCancelOtherReason());

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->yigjService.applyCancelOrderNoPay(%s)", trackingToken,
						JSON.toJSONString(cancelNoPayRQ, true)), trackingToken);
		
		ApplyCancelNoPayRS cancelNoPayRS = yigjService.applyCancelOrderNoPay(cancelNoPayRQ);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->接口返回结果(%s)", trackingToken,
						JSON.toJSONString(cancelNoPayRS, true)), trackingToken);

		// 取消失败
		if (!"T".equals(cancelNoPayRS.getIsSuccess())) {
			response.setResult(ApiResponse.RESULT_FAILURE);
			response.setMessage("申请取消失败");

			// 记录操作日志
			orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
					.getId(), "申请取消未付款的订单", command.getFromDealerCode(),
					GJJPConstants.LOG_WORKER_TYPE_DEALER, "申请取消未付款的订单失败"));
			
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->申请成功(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 更新订单和乘客机票状态
		jpOrder.apiCancelNoPayToSupplierAccept(command);
		jpOrderDao.update(jpOrder);

		response.setResult(ApiResponse.RESULT_SUCCESS);
		
		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "申请取消未付款的订单", command.getFromDealerCode(),
				GJJPConstants.LOG_WORKER_TYPE_DEALER, "申请取消未付款的订单成功"));

		return response;
	}

	/**
	 * @方法功能说明：申请取消已付款未出票的订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:43:09
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApplyCancelOrderGJResponse
	 * @throws
	 */
	public ApplyCancelOrderGJResponse applyCancelOrder(ApplyCancelOrderGJCommand command) {
		
		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消已付款未出票的订单->applyCancelOrder(%s)", trackingToken,
						JSON.toJSONString(command, true)), trackingToken);

		ApplyCancelOrderGJResponse response = new ApplyCancelOrderGJResponse();
		GJJPOrder jpOrder = jpOrderDao.get(command.getPlatformOrderId());

		// 检查订单是否存在
		if (jpOrder == null) {
			response.setResult(ApplyCancelOrderGJResponse.RESULT_NOT_EXIST);
			response.setMessage("订单不存在");
			return response;
		} else {
			response.setPlatformOrderId(jpOrder.getId());
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->检查订单是否存在通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 检查来源
		if (!jpOrder.getBaseInfo().getFromDealer().getId().equals(command.getFromDealerId())) {
			response.setResult(ApiResponse.RESULT_NO_AUTH);
			response.setMessage("无权操作");
			return response;
		} else {
			response.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->检查来源通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 状态检查
		// 平台订单状态
		Integer orderStatus = jpOrder.getStatus().getCurrentValue();
		// 非待出票状态下的订单不能取消
		if (!GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT.equals(orderStatus)) {
			response.setResult(ApplyCancelOrderGJResponse.RESULT_NOT_OUT_TICKET_WAIT);
			response.setMessage("非待出票的订单不能取消");
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->检查平台订单状态通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		ApplyCancelRQ cancelRQ = new ApplyCancelRQ();
		ApplyCancelParams cancelParams = new ApplyCancelParams();
		cancelRQ.setApplyCancelParams(cancelParams);
		cancelParams.setOrderId(jpOrder.getBaseInfo().getSupplierOrderId());
		cancelParams.setExtOrderId(jpOrder.getId());
		cancelParams.setCancelNotifyUrl(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_NOTIFY_URL));

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->yigjService.applyCancelOrder(%s)", trackingToken,
						JSON.toJSONString(cancelRQ)), trackingToken);
		
		ApplyCancelRS cancelRS = yigjService.applyCancelOrder(cancelRQ);
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->接口返回结果(%s)", trackingToken,
						JSON.toJSONString(cancelRS)), trackingToken);

		// 取消失败
		if (!"T".equals(cancelRS.getIsSuccess())) {
			response.setResult(ApiResponse.RESULT_FAILURE);
			response.setMessage("申请取消失败");

			// 记录操作日志
			orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
					.getId(), "申请取消已付款未出票的订单", command.getFromDealerCode(),
					GJJPConstants.LOG_WORKER_TYPE_DEALER, "申请取消已付款未出票的订单失败"));
			
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->申请取消成功(%s)", trackingToken,
						JSON.toJSONString(cancelRS)), trackingToken);
		
		// 更新订单状态
		jpOrder.cancelToSupplierAccept();
		jpOrderDao.update(jpOrder);

		response.setResult(ApiResponse.RESULT_SUCCESS);
		
		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "申请取消已付款未出票的订单", command.getFromDealerCode(),
				GJJPConstants.LOG_WORKER_TYPE_DEALER, "申请取消已付款未出票的订单成功"));

		return response;
	}

	/**
	 * @方法功能说明：申请退废票(成功出票时可调用)
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:43:18
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApplyRefundTicketGJResponse
	 * @throws
	 */
	public ApplyRefundTicketGJResponse applyRefundTicket(ApplyRefundTicketGJCommand command) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请退废票->applyRefundTicket(%s)", trackingToken,
						JSON.toJSONString(command, true)), trackingToken);

		ApplyRefundTicketGJResponse response = new ApplyRefundTicketGJResponse();
		GJJPOrder jpOrder = jpOrderDao.get(command.getPlatformOrderId());

		// 检查订单是否存在
		if (jpOrder == null) {
			response.setResult(ApplyRefundTicketGJResponse.RESULT_NOT_EXIST);
			response.setMessage("订单不存在");
			return response;
		} else {
			response.setPlatformOrderId(jpOrder.getId());
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->检查订单是否存在通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 检查来源
		if (!jpOrder.getBaseInfo().getFromDealer().getId().equals(command.getFromDealerId())) {
			response.setResult(ApiResponse.RESULT_NO_AUTH);
			response.setMessage("无权操作");
			return response;
		} else {
			response.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->检查来源通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 状态检查
		// 平台订单状态
		Integer orderStatus = jpOrder.getStatus().getCurrentValue();
		if (!(GJJPConstants.ORDER_STATUS_OUT_TICKET_ALREADY.equals(orderStatus)
			|| GJJPConstants.ORDER_STATUS_REFUND_WAIT.equals(orderStatus) 
			|| GJJPConstants.ORDER_STATUS_REFUND_SOME.equals(orderStatus))) {
			response.setResult(ApplyRefundTicketGJResponse.RESULT_CANNOT_REFUND);
			response.setMessage("订单不能退废票");
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->检查平台订单状态通过(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		// 检查是否可以当日退票
		if (Integer.valueOf(1).equals(command.getRefundType())) {
			Date now = new Date();
			Date bookTime = jpOrder.getBaseInfo().getBookTime();
			Date startTime = jpOrder.getSegmentInfo().getTakeoffFlights().get(0).getStartTime();
			// 废票条件：当天定的票且不是当天的航班
			if (!(DateUtils.isSameDay(bookTime, now) && DateUtils.truncatedCompareTo(startTime, bookTime, Calendar.DATE) > 0)) {
				response.setResult(ApplyRefundTicketGJResponse.RESULT_CANNOT_INVALID);
				response.setMessage("无法当日废票");
				return response;
			}

			getHgLogger().info(getClass(), devName,
					String.format("(%s)->申请取消未付款的订单->检查是否可以当日退票通过(%s)", trackingToken,
							jpOrder.getId()), trackingToken);
		}

		ApplyRefundRQ refundRQ = new ApplyRefundRQ();
		ApplyRefundParams refundParams = new ApplyRefundParams();
		refundRQ.setApplyRefundParams(refundParams);

		refundParams.setOrderId(jpOrder.getBaseInfo().getSupplierOrderId());
		refundParams.setExtOrderId(jpOrder.getId());
		refundParams.setRefundType(command.getRefundType().toString());
		refundParams.setRefundMemo(command.getRefundMemo());
		List<PassengerInfo> refundPassengerInfos = new ArrayList<PassengerInfo>();
		refundParams.setPassengerInfos(refundPassengerInfos);
		refundParams.setRefundNotifyUrl(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_NOTIFY_URL));

		Map<String, GJPassenger> passengerMap = new HashMap<String, GJPassenger>();
		for (GJPassenger passenger : jpOrder.getPassengers()) {
			String passengerKey = passenger.getBaseInfo().getIdType() + "|" + passenger.getBaseInfo().getIdNo();
			passengerMap.put(passengerKey, passenger);
		}

		// 申请成功的乘客
		List<RefundPassengerInfo> succPassengerInfos = new ArrayList<ApplyRefundTicketGJCommand.RefundPassengerInfo>();
		// 申请失败的乘客
		List<RefundPassengerInfo> failPassengerInfos = new ArrayList<ApplyRefundTicketGJCommand.RefundPassengerInfo>();
		// 发起退票的乘机人
		Map<String, GJPassenger> passengerNameMap = new HashMap<String, GJPassenger>();

		for (RefundPassengerInfo refundPassengerInfo : command.getRefundPassengerInfos()) {
			String refundPassengerKey = refundPassengerInfo.getIdType() + "|" + refundPassengerInfo.getIdNo();
			GJPassenger passenger = passengerMap.get(refundPassengerKey);
			if (passenger == null) {
				failPassengerInfos.add(refundPassengerInfo);
				continue;
			}
			PassengerInfo passengerInfo = new PassengerInfo();
			passengerInfo.setPassengerName(passenger.getBaseInfo().getPassengerName());
			// 多个票号之间用/分隔
			StringBuilder ordDetEticketNoBuf = new StringBuilder();
			for (int i = 0; i < passenger.getTickets().size(); i++) {
				if (i != 0)
					ordDetEticketNoBuf.append("/");
				ordDetEticketNoBuf.append(passenger.getTickets().get(i).getTicketNo());
			}
			passengerInfo.setOrdDetEticketNo(ordDetEticketNoBuf.toString());
			refundPassengerInfos.add(passengerInfo);
			passengerNameMap.put(passenger.getBaseInfo().getPassengerName(), passenger);
		}

		// 调用供应商退废票接口

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请退废票->yigjService.applyRefundTicket(%s)", trackingToken,
						JSON.toJSONString(refundRQ, true)), trackingToken);
		
		ApplyRefundRS refundRS = yigjService.applyRefundTicket(refundRQ);
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请退废票->接口返回结果(%s)", trackingToken,
						JSON.toJSONString(refundRS, true)), trackingToken);

		if (!"T".equals(refundRS.getIsSuccess())) {
			response.setResult(ApiResponse.RESULT_FAILURE);
			response.setMessage("退废票失败");

			// 记录操作日志
			orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
					.getId(), "申请退废票", command.getFromDealerCode(),
					GJJPConstants.LOG_WORKER_TYPE_DEALER, "申请退废票失败"));
			
			return response;
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->退废票申请成功(%s)", trackingToken,
						jpOrder.getId()), trackingToken);

		ApplyRefundResult refundResult = refundRS.getApplyRefundResult();
		for (PerOperationIsSuc perOperationIsSuc : refundResult.getPerPassengerIsSucs()) {
			GJPassenger passenger = passengerNameMap.get(perOperationIsSuc.getPassengerName());
			if (passenger == null)
				continue;
			RefundPassengerInfo passengerInfo = new RefundPassengerInfo();
			passengerInfo.setIdNo(passenger.getBaseInfo().getIdNo());
			passengerInfo.setIdType(passenger.getBaseInfo().getIdType());
			passengerInfo.setPassengerName(passenger.getBaseInfo().getPassengerName());
			if ("T".equals(perOperationIsSuc.getPerIsSuccess())) {
				succPassengerInfos.add(passengerInfo);
				passenger.apiRefundSupplierAccept(command);
			} else
				failPassengerInfos.add(passengerInfo);
		}

		// 更新订单和乘客机票状态
		jpOrderDao.update(jpOrder);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->申请取消未付款的订单->成功(%s) 失败(%s)", trackingToken,
						JSON.toJSONString(succPassengerInfos, true), 
						JSON.toJSONString(failPassengerInfos, true)), trackingToken);

		response.setResult(ApiResponse.RESULT_SUCCESS);
		response.setFailPassengerInfos(failPassengerInfos);
		response.setSuccPassengerInfos(succPassengerInfos);

		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "申请退废票", command.getFromDealerCode(),
				GJJPConstants.LOG_WORKER_TYPE_DEALER, "申请退废票成功"));

		return response;
	}

}
