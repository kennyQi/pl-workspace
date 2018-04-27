package hsl.domain.model.yxjp;

import hg.common.component.BaseModel;
import hg.common.util.MoneyUtil;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.company.Member;
import hsl.domain.model.user.User;
import hsl.pojo.command.yxjp.order.ApplyRefundYXJPOrderCommand;
import hsl.pojo.command.yxjp.order.CancelNoPayYXJPOrderCommand;
import hsl.pojo.command.yxjp.order.CreateYXJPOrderCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.yxjp.YXPassengerDTO;
import hsl.pojo.dto.yxjp.notify.ApplyRefundTicketNotify;
import hsl.pojo.dto.yxjp.notify.OutTicketRefuseNotify;
import hsl.pojo.dto.yxjp.notify.OutTicketSuccessNotify;
import hsl.pojo.dto.yxjp.notify.RefundTicketNotify;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.util.HSLConstants;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import plfx.api.client.api.v1.gn.response.JPAutoOrderGNResponse;
import plfx.api.client.api.v1.gn.response.RefundTicketGNResponse;

import javax.persistence.*;
import java.util.*;

/**
 * 易行机票订单
 *
 * @author zhurz
 * @since 1.7
 */
@Entity
@DynamicUpdate
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_YJ + "ORDER")
public class YXJPOrder extends BaseModel {

	/**
	 * 订单基本信息
	 */
	@Embedded
	private YXJPOrderBaseInfo baseInfo;

	/**
	 * 航班信息
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FLIGHT_ID")
	private YXJPOrderFlight flight;

	/**
	 * 支付信息
	 */
	@Embedded
	private YXJPOrderPayInfo payInfo;

	/**
	 * 下单用户信息
	 */
	@Embedded
	private YXJPOrderCreateUserInfo userInfo;

	/**
	 * 乘客子订单
	 */
	@OrderBy("baseInfo.seq")
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "fromOrder", cascade = CascadeType.ALL)
	private List<YXJPOrderPassenger> passengers = new ArrayList<YXJPOrderPassenger>();

	/**
	 * 联系人
	 */
	@Embedded
	private YXJPOrderLinkman linkman;

	public YXJPOrderBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new YXJPOrderBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(YXJPOrderBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public YXJPOrderFlight getFlight() {
		return flight;
	}

	public void setFlight(YXJPOrderFlight flight) {
		this.flight = flight;
	}

	public YXJPOrderPayInfo getPayInfo() {
		if (payInfo == null)
			payInfo = new YXJPOrderPayInfo();
		return payInfo;
	}

	public void setPayInfo(YXJPOrderPayInfo payInfo) {
		this.payInfo = payInfo;
	}

	public YXJPOrderCreateUserInfo getUserInfo() {
		if (userInfo == null)
			userInfo = new YXJPOrderCreateUserInfo();
		return userInfo;
	}

	public void setUserInfo(YXJPOrderCreateUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<YXJPOrderPassenger> getPassengers() {
		if (passengers == null)
			passengers = new ArrayList<YXJPOrderPassenger>();
		return passengers;
	}

	public void setPassengers(List<YXJPOrderPassenger> passengers) {
		this.passengers = passengers;
	}

	public YXJPOrderLinkman getLinkman() {
		if (linkman == null)
			linkman = new YXJPOrderLinkman();
		return linkman;
	}

	public void setLinkman(YXJPOrderLinkman linkman) {
		this.linkman = linkman;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {

		/**
		 * 创建订单
		 *
		 * @param command      下单command
		 * @param orderNo      订单号
		 * @param orgCityName  出发城市名称
		 * @param destCityName 到达城市名称
		 * @param orderUser    下单用户
		 * @param memberMap    企业成员MAP(成员ID,成员)
		 */
		public void createOrder(CreateYXJPOrderCommand command, String orderNo,
								String orgCityName, String destCityName,
								User orderUser, Map<String, Member> memberMap) {
			setId(UUIDGenerator.getUUID());
			// 基本信息
			getBaseInfo().setOrderNo(orderNo);
			getBaseInfo().setFromType(command.getFromType());
			getBaseInfo().setCreateDate(new Date());
			// 航班信息
			setFlight(new YXJPOrderFlight().manager().create(command.getFlightInfo(), orgCityName, destCityName));
			// 联系人
			getLinkman().setLinkName(command.getLinkman().getLinkName());
			getLinkman().setLinkEmail(command.getLinkman().getLinkEmail());
			getLinkman().setLinkMobile(command.getLinkman().getLinkMobile());
			// 下单用户信息
			getUserInfo().setUserId(orderUser.getId());
			getUserInfo().setLoginName(orderUser.getAuthInfo().getLoginName());
			getUserInfo().setMobile(orderUser.getContactInfo().getMobile());
			// 乘客信息
			for (int i = 0; i < command.getPassengers().size(); i++) {
				YXPassengerDTO passengerDTO = command.getPassengers().get(i);
				Member member = null;
				if (StringUtils.isNotBlank(passengerDTO.getCompanyMemberId()))
					member = memberMap.get(passengerDTO.getCompanyMemberId());
				getPassengers().add(new YXJPOrderPassenger().manager().create(passengerDTO, YXJPOrder.this, member, getFlight(), i + 1));
			}
			// 支付信息
			getPayInfo().setTotalPrice(getFlight().getPolicyInfo().getPayPrice() * getPassengers().size());
		}

		/**
		 * 生成支付信息
		 *
		 * @param fromType  支付来源类型{@link HSLConstants.FromType}
		 * @param coupons   使用的卡券
		 * @param toAccount 收款帐号
		 * @param remark    备注
		 * @return
		 */
		public YXJPOrderPayRecord generatePayRecord(Integer fromType, List<CouponDTO> coupons, String toAccount, String remark) {

			// 带支付乘客
			List<YXJPOrderPassenger> waitPayPassengers = new ArrayList<YXJPOrderPassenger>();
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (HSLConstants.YXJPOrderPassengerTicket.STATUS_PAY_WAIT.equals(passenger.getTicket().getStatus())) {
					waitPayPassengers.add(passenger);
				}
			}

			if (waitPayPassengers.size() == 0)
				throw new ShowMessageException("订单无需支付");

			List<String> passengerIds = (List<String>) CollectionUtils.collect(waitPayPassengers, new BeanToPropertyValueTransformer("id"));
			List<String> couponIds = null;
			if (coupons != null) {
				couponIds = (List<String>) CollectionUtils.collect(coupons, new BeanToPropertyValueTransformer("id"));
			}

			// 生成支付订单号
			String payOrderNo = YXJPOrderPayRecord.generatePayOrderNo(fromType, getBaseInfo().getOrderNo(), passengerIds, couponIds);

			if (getPayInfo().getPayRecordMap().containsKey(payOrderNo)) {
				YXJPOrderPayRecord payRecord = getPayInfo().getPayRecordMap().get(payOrderNo);
				payRecord.setLastRequestDate(new Date());
				return payRecord;
			}

			// 创建支付记录，如果使用卡券后支付金额为0时，直接算支付成功。
			YXJPOrderPayRecord payRecord = new YXJPOrderPayRecord().manager().create(fromType, YXJPOrder.this, payOrderNo, toAccount, remark, waitPayPassengers, coupons);
			getPayInfo().getPayRecordMap().put(payOrderNo, payRecord);

			return payRecord;
		}

		/**
		 * 取消未支付订单
		 *
		 * @param command 取消订单command
		 * @return 申请取消的乘客
		 */
		public List<YXJPOrderPassenger> cancelNoPayOrder(CancelNoPayYXJPOrderCommand command) {

			// 取消的乘客
			List<YXJPOrderPassenger> cancelPassengers = new ArrayList<YXJPOrderPassenger>();

			// 总共取消的需要支付的价格
			Double orderTotalCancelPrice = 0d;
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (command.getPassengerIds().contains(passenger.getId())) {
					// 取消未支付乘客
					passenger.manager().cancelNoPayOrder();
					cancelPassengers.add(passenger);
				}
				// 计算总共取消的需要支付的价格
				if (YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY.equals(passenger.getTicket().getStatus())) {
					orderTotalCancelPrice = MoneyUtil.round(orderTotalCancelPrice + passenger.getPayInfo().getPrice(), 0);
				}
			}

			// 设置总共取消的需要支付的价格
			getPayInfo().setTotalCancelPrice(orderTotalCancelPrice);

			return cancelPassengers;
		}

		/**
		 * 取消超时未支付的乘客
		 *
		 * @return 取消的乘客
		 */
		public List<YXJPOrderPassenger> cancelTimeoutNoPayOrder() {

			// 取消的乘客
			List<YXJPOrderPassenger> cancelPassengers = new ArrayList<YXJPOrderPassenger>();

			// 总共取消的需要支付的价格
			Double orderTotalCancelPrice = 0d;
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (YXJPOrderPassengerTicket.STATUS_PAY_WAIT.equals(passenger.getTicket().getStatus())) {
					// 取消未支付乘客
					passenger.manager().cancelNoPayOrder();
					cancelPassengers.add(passenger);
				}
				// 计算总共取消的需要支付的价格
				if (YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY.equals(passenger.getTicket().getStatus())) {
					orderTotalCancelPrice = MoneyUtil.round(orderTotalCancelPrice + passenger.getPayInfo().getPrice(), 0);
				}
			}

			// 设置总共取消的需要支付的价格
			getPayInfo().setTotalCancelPrice(orderTotalCancelPrice);

			return cancelPassengers;
		}

		/**
		 * 支付成功处理
		 *
		 * @param payRecord 本地支付记录
		 */
		public void processPaySuccess(YXJPOrderPayRecord payRecord) {

			// -- 乘客支付成功处理 --
			// 累计已支付
			double totalAlreadyPay = 0d;
			// 全部乘客需要支付的金额
			double needTotalPay = payRecord.getTotalPayAmount();
			// 实际已支付金额
			double alreadyPay = payRecord.getPayAmount();
			// 已支付的乘客
			List<YXJPOrderPassenger> passengers = payRecord.getPassengers();
			for (int i = 0; i < passengers.size(); i++) {
				YXJPOrderPassenger passenger = passengers.get(i);
				// 乘客需要支付的金额
				double needPay = passenger.getPayInfo().getPrice();
				Double payAmount = Math.floor((needPay / needTotalPay) * alreadyPay);
				if (i == passengers.size() - 1) {
					// 最后一个乘客
					payAmount = MoneyUtil.round(alreadyPay - totalAlreadyPay, 2);
				} else {
					totalAlreadyPay += payAmount;
				}
				passenger.manager().processPaySuccess(payRecord, payAmount);
			}

			// -- 订单支付信息处理 --
			// 订单已支付总额
			Double orderTotalPayAmount = 0d;
			// 订单已使用的实际卡券优惠总额
			Double orderTotalCouponUsedAmount = 0d;
			// 订单已使用的卡券总额
			Double orderTotalCouponAmount = 0d;
			// 订单已取消的总价
			Double orderTotalCancelPrice = 0d;
			for (YXJPOrderPayRecord orderPayRecord : getPayInfo().getPayRecordMap().values()) {
				if (orderPayRecord.getPaySuccess()) {
					orderTotalPayAmount = MoneyUtil.round(orderTotalPayAmount + orderPayRecord.getPayAmount(), 2);
					orderTotalCouponUsedAmount = MoneyUtil.round(orderTotalCouponUsedAmount + orderPayRecord.getCouponUsedAmount(), 2);
					orderTotalCouponAmount = MoneyUtil.round(orderTotalCouponAmount + orderPayRecord.getCouponAmount(), 2);
				}
			}
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY.equals(passenger.getTicket().getStatus()))
					orderTotalCancelPrice = MoneyUtil.round(orderTotalCancelPrice + passenger.getPayInfo().getPrice(), 2);
			}
			getPayInfo().setTotalPayAmount(orderTotalPayAmount);
			getPayInfo().setTotalCouponUsedAmount(orderTotalCouponUsedAmount);
			getPayInfo().setTotalCouponAmount(orderTotalCouponAmount);
			getPayInfo().setTotalCancelPrice(orderTotalCancelPrice);
		}

		/**
		 * 创建退废票申请
		 *
		 * @param command  申请退废票command
		 * @param operator 操作人
		 * @return 退废票申请
		 */
		public YXJPOrderRefundApply createRefundOrderApply(ApplyRefundYXJPOrderCommand command, String operator) {
			// 申请退废票的乘客
			List<YXJPOrderPassenger> passengers = new ArrayList<YXJPOrderPassenger>();

			// 检查乘客状态是否可以退废票，状态只有出票成功或退废票失败的才可以申请
			List<Integer> needTicketStatus = Arrays.asList(
					YXJPOrderPassengerTicket.STATUS_TICKET_SUCC,
					YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_FAIL);

			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (command.getPassengerIds().contains(passenger.getId())) {
					if (!needTicketStatus.contains(passenger.getTicket().getStatus()))
						throw new ShowMessageException(String.format("乘客[%s]不满足申请退废票条件", passenger.getBaseInfo().getName()));
					passengers.add(passenger);
				}
			}

			return new YXJPOrderRefundApply().manager().create(command, YXJPOrder.this, passengers, operator);
		}

		/**
		 * 处理申请响应
		 *
		 * @param apply    退废票申请
		 * @param response 分销申请结果
		 */
		public void processRefundOrderApplyResponse(YXJPOrderRefundApply apply, RefundTicketGNResponse response) {
			// 处理退废票申请响应
			apply.manager().processResponse("T".equals(response.getIs_success()), response.getError());
			// 申请成功后的处理
			if (apply.getApplySuccess()) {
				for (YXJPOrderPassenger passenger : apply.getPassengers()) {
					// 退废票申请处理
					passenger.manager().processRefundOrderApply();
				}
			}
		}

		/**
		 * 处理向分销下单的响应
		 *
		 * @param outOrderResponse
		 */
		public void processOutOrderResponse(JPAutoOrderGNResponse outOrderResponse) {

			// 向分销下单时间
			if (outOrderResponse.getCreateTime() != null)
				getBaseInfo().setOutOrderDate(outOrderResponse.getCreateTime());
			else
				getBaseInfo().setOutOrderDate(new Date());

			// 下单失败处理
			if (!"T".equals(outOrderResponse.getIs_success())) {
				for (YXJPOrderPassenger passenger : getPassengers())
					passenger.manager().processOutOrderFailure();
			}

		}

		/**
		 * 检查是否需要向分销下单
		 *
		 * @return
		 */
		public boolean needOutOrder() {

			// 判断是否在分销下过单
			if (getBaseInfo().getOutOrderDate() != null)
				return false;

			// 检查乘客状态是否可以向分销下单
			List<Integer> needTicketStatus = Arrays.asList(
					YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY,
					YXJPOrderPassengerTicket.STATUS_TICKET_DEALING);
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (!needTicketStatus.contains(passenger.getTicket().getStatus()))
					return false;
			}

			return true;
		}

		/**
		 * 处理出票成功通知
		 *
		 * @param notify 出票成功通知
		 */
		public void processOutTicketSuccessNotify(OutTicketSuccessNotify notify) {
			getBaseInfo().setPnr(notify.getNewPnr());
			for (YXJPOrderPassenger passenger : getPassengers()) {
				int pidx = notify.getPassengerNames().indexOf(passenger.getBaseInfo().getName());
				if (pidx != -1)
					// 乘客出票成功处理
					passenger.manager().processOutTicketSuccess(notify.getAirIds().get(pidx));
			}
		}

		/**
		 * 处理拒绝出票通知
		 *
		 * @param notify 绝出票通知
		 */
		public void processOutTicketRefuseNotify(OutTicketRefuseNotify notify) {
			for (YXJPOrderPassenger passenger : getPassengers()) {
				// 乘客拒绝出票处理
				passenger.manager().processOutTicketRefuse();
			}
		}

		/**
		 * 处理退废票通知
		 *
		 * @param notify 退废票通知
		 */
		public YXJPOrderRefundResult processRefundTicketNotify(RefundTicketNotify notify) {

			// 创建退废票处理结果
			YXJPOrderRefundResult refundResult = new YXJPOrderRefundResult().manager().create(YXJPOrder.this, notify);

			// 成功处理
			if (Integer.valueOf(1).equals(notify.getRefundStatus())) {

				// -- 计算平摊在每个乘客的实退金额和退款手续费 --

				// 所有乘客需要支付的价格
				double totalNeedPay = 0d;
				for (YXJPOrderPassenger passenger : refundResult.getPassengers())
					totalNeedPay = MoneyUtil.round(totalNeedPay + passenger.getPayInfo().getPrice(), 2);

				// 供应商退款给分销的金额
				double refundAmount = refundResult.getRefundPrice();
				// 供应商退款给分销的手续费
				double proceduresAmount = refundResult.getProcedures();
				// 当前累计供应商退款给分销的金额
				double currentRefundAmount = 0d;
				// 当前累计供应商退款给分销的手续费
				double currentProceduresAmount = 0d;

				for (int i = 0; i < refundResult.getPassengers().size(); i++) {
					YXJPOrderPassenger passenger = refundResult.getPassengers().get(i);

					double needPay = passenger.getPayInfo().getPrice();
					// 计算乘客平摊的供应商实退金额
					double passengerRefundAmount = Math.floor((needPay / totalNeedPay) * refundAmount);
					// 计算乘客平摊的供应商退款给分销的手续费
					double passengerProceduresAmount = Math.floor((needPay / totalNeedPay) * proceduresAmount);

					if (i == refundResult.getPassengers().size() - 1) {
						// 计算最后一个乘客平摊的供应商实退金额
						passengerRefundAmount = MoneyUtil.round(refundAmount - currentRefundAmount, 2);
						// 计算最后一个乘客平摊的供应商退款给分销的手续费
						passengerProceduresAmount = MoneyUtil.round(proceduresAmount - currentProceduresAmount, 2);
					} else {
						currentRefundAmount += passengerRefundAmount;
						currentProceduresAmount += passengerProceduresAmount;
					}

					// 乘客退废票成功处理
					passenger.manager().processRefundTicketSuccess(refundResult, passengerRefundAmount, passengerProceduresAmount);

				}

				// -- 重新统计供应商退废票金额 --
				// 供应商已退款手续费总额
				Double totalSupplierProceduresAmount = 0d;
				// 供应商已退款总额
				Double totalSupplierRefundAmount = 0d;
				for (YXJPOrderPassenger passenger : getPassengers()) {
					totalSupplierProceduresAmount = MoneyUtil.round(totalSupplierProceduresAmount + passenger.getPayInfo().getSupplierProceduresAmount(), 2);
					totalSupplierRefundAmount = MoneyUtil.round(totalSupplierRefundAmount + passenger.getPayInfo().getSupplierRefundAmount(), 2);
				}
				getPayInfo().setTotalSupplierProceduresAmount(totalSupplierProceduresAmount);
				getPayInfo().setTotalSupplierRefundAmount(totalSupplierRefundAmount);
			}
			// 失败处理
			else {
				for (YXJPOrderPassenger passenger : refundResult.getPassengers())
					passenger.manager().processRefundTicketFailure();
			}

			return refundResult;
		}

		/**
		 * 根据分销申请退废票通知创建退废票申请记录
		 *
		 * @param notify 分销申请退废票通知
		 */
		public YXJPOrderRefundApply createOrderRefundApplyByNotify(ApplyRefundTicketNotify notify) {

			List<YXJPOrderPassenger> refundPassengers = new ArrayList<YXJPOrderPassenger>();

			for (YXJPOrderPassenger passenger : getPassengers()) {

				// 如果选择的乘客已经是退费处理中则跳过
				if (YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_DEALING.equals(passenger.getTicket().getStatus()))
					continue;

				if (notify.getAirIds().contains(passenger.getTicket().getTicketNo())) {
					passenger.manager().processRefundOrderApply();
					refundPassengers.add(passenger);
				}
			}

			return new YXJPOrderRefundApply().manager().createByNotify(notify, YXJPOrder.this, refundPassengers);
		}

		/**
		 * 创建批量退款记录（场景：出票失败退款）
		 *
		 * @param remarkPrefix 备注前缀，用来区分环境
		 * @return
		 */
		public YXJPOrderPayBatchRefundRecord createPayBatchRefundRecordOnOutTicketFailure(String remarkPrefix) {

			List<YXJPOrderPassenger> payRefundPassengers = new ArrayList<YXJPOrderPassenger>();
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (YXJPOrderPassengerTicket.STATUS_TICKET_FAIL_REBACK_WAIT.equals(passenger.getTicket().getStatus())) {
					payRefundPassengers.add(passenger);
				}
			}

			return new YXJPOrderPayBatchRefundRecord().manager().create(payRefundPassengers, (remarkPrefix == null ? "" : remarkPrefix) + "出票失败退款");
		}

		/**
		 * 创建批量退款记录（场景：退废票成功退款）
		 *
		 * @param passengerIds 退废票成功的乘客id
		 * @param remarkPrefix 备注前缀，用来区分环境
		 * @return
		 */
		public YXJPOrderPayBatchRefundRecord createPayBatchRefundRecordOnRefundSuccess(List<String> passengerIds, String remarkPrefix) {

			List<YXJPOrderPassenger> payRefundPassengers = new ArrayList<YXJPOrderPassenger>();
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (passengerIds.contains(passenger.getId())
						&& YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_SUCC_REBACK_WAIT.equals(passenger.getTicket().getStatus())) {
					payRefundPassengers.add(passenger);
				}
			}

			return new YXJPOrderPayBatchRefundRecord().manager().create(payRefundPassengers, (remarkPrefix == null ? "" : remarkPrefix) + "退废票成功退款");
		}

		/**
		 * 处理批量退款结果
		 *
		 * @param payRefundRecords 支付退款记录
		 */
		void processPayRefundResult(List<YXJPOrderPayRefundRecord> payRefundRecords) {

			// -- 乘客状态处理 --
			for (YXJPOrderPayRefundRecord payRefundRecord : payRefundRecords) {
				for (YXJPOrderPassenger passenger : payRefundRecord.getPassengers()) {
					// 乘客退款成功处理
					passenger.manager().processPayRefundSuccess(payRefundRecord);
				}
			}

			// -- 重新统计订单实际退款 --
			// 已退款总额
			Double totalRefundAmount = 0d;
			for (YXJPOrderPassenger passenger : getPassengers())
				totalRefundAmount = MoneyUtil.round(totalRefundAmount + passenger.getPayInfo().getRefundAmount(), 2);
			getPayInfo().setTotalRefundAmount(totalRefundAmount);

		}

		/**
		 * 订单状态是否是出票失败待退款或已取消未付款状态
		 *
		 * @return 是否是出票失败待退款或已取消未付款状态
		 */
		public boolean isOutTicketFailureRebackWait() {
			// 乘客必须具备的状态
			List<Integer> needStatus = Arrays.asList(YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY, YXJPOrderPassengerTicket.STATUS_TICKET_FAIL_REBACK_WAIT);
			// 如果订单中任何一个乘客不符合必须具备的状态则返回false
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (!needStatus.contains(passenger.getTicket().getStatus()))
					return false;
			}
			return true;
		}

		/**
		 * 处理出票失败待退款的且不需要退款的乘客状态为出票失败已退款
		 */
		public void processNoPayRefundPassengerToRebackSuccess() {
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (YXJPOrderPassengerTicket.STATUS_TICKET_FAIL_REBACK_WAIT.equals(passenger.getTicket().getStatus())
						&& passenger.getPayInfo().getNeedRefundAmount() == 0d) {
					passenger.manager().processPayRefundSuccess(null);
				}
			}
		}

		/**
		 * 处理退废票成功待退款且不需要退款的乘客状态为退废票成功已退款
		 *
		 * @return 处理的乘客
		 */
		public List<YXJPOrderPassenger> processRefundSuccessRebackWaitPassengerToRebackSuccess() {

			List<YXJPOrderPassenger> passengers = new ArrayList<YXJPOrderPassenger>();

			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_SUCC_REBACK_WAIT.equals(passenger.getTicket().getStatus())
						&& passenger.getPayInfo().getNeedRefundAmount() == 0d) {
					passenger.manager().processPayRefundSuccess(null);
					passengers.add(passenger);
				}
			}

			return passengers;
		}


		/**
		 * 是否全部取消未支付
		 *
		 * @return 是否全部取消未支付
		 */
		public boolean isAllCancelNoPay() {
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (!YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY.equals(passenger.getTicket().getStatus()))
					return false;
			}
			return true;
		}

		/**
		 * 是否全部出票处理中或取消未支付
		 *
		 * @return 是否全部取消未支付
		 */
		public boolean isAllTicketDealingOrCancelNoPay() {
			// 需要状态
			List<Integer> needStatus = Arrays.asList(
					YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY,
					YXJPOrderPassengerTicket.STATUS_TICKET_DEALING);
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (!needStatus.contains(passenger.getTicket().getStatus()))
					return false;
			}
			return true;
		}

		/**
		 * 是否全部出票成功或取消未支付
		 *
		 * @return 是否全部出票成功或取消未支付
		 */
		public boolean isAllOutTicketSuccessOrCancelNoPay() {
			// 需要状态
			List<Integer> needStatus = Arrays.asList(
					YXJPOrderPassengerTicket.STATUS_TICKET_SUCC,
					YXJPOrderPassengerTicket.STATUS_TICKET_CANCEL_NOPAY);
			for (YXJPOrderPassenger passenger : getPassengers()) {
				if (!needStatus.contains(passenger.getTicket().getStatus()))
					return false;
			}
			return true;
		}

	}

}
