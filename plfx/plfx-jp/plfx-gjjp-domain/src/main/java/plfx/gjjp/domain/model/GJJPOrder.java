package plfx.gjjp.domain.model;

import hg.common.component.BaseModel;
import hg.common.util.DateUtil;
import hg.common.util.MoneyUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

import plfx.api.client.api.v1.gj.dto.GJPassengerBaseInfoDTO;
import plfx.api.client.api.v1.gj.request.ApplyCancelNoPayOrderGJCommand;
import plfx.api.client.api.v1.gj.request.CreateJPOrderGJCommand;
import plfx.api.client.api.v1.gj.request.PayToJPOrderGJCommand;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.jp.command.admin.gj.AddExceptionOrderCommand;
import plfx.jp.command.admin.gj.ApplyCancelNoPayGJJPOrderCommand;
import plfx.jp.command.api.gj.GJJPOrderAuditSuccessCommand;
import plfx.jp.command.api.gj.GJJPOrderAuditSuccessCommand.GJJPSupplierPolicy;
import plfx.jp.domain.model.J;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import plfx.jp.domain.model.supplier.Supplier;
import plfx.yxgjclient.pojo.param.AvailableJourney;
import plfx.yxgjclient.pojo.param.CreateOrderResult;
import plfx.yxgjclient.pojo.param.Flight;
import plfx.yxgjclient.pojo.param.OrderInfo;
import plfx.yxgjclient.pojo.param.PassengerInfo;
import plfx.yxgjclient.pojo.param.PriceDetailInfo;
import plfx.yxgjclient.pojo.param.QueryOrderDetailResult;
import plfx.yxgjclient.pojo.param.RewPolicyInfo;

/**
 * @类功能说明：国际机票订单
 * @类修改者：
 * @修改日期：2015-6-29下午4:59:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午4:59:25
 */
@Entity
@DynamicUpdate
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX_GJ + "JP_ORDER")
public class GJJPOrder extends BaseModel {

	/**
	 * 订单基础信息
	 */
	@Embedded
	private GJJPOrderBaseInfo baseInfo;

	/**
	 * 异常订单信息
	 */
	@Embedded
	private GJJPOrderExceptionInfo exceptionInfo;

	/**
	 * 航段信息
	 */
	@Embedded
	private GJJPOrderSegmentInfo segmentInfo;

	/**
	 * 各乘机人信息
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jpOrder", cascade = CascadeType.ALL)
	private List<GJPassenger> passengers;

	/**
	 * 联系人信息
	 */
	@Embedded
	private GJJPOrderContacterInfo contacterInfo;

	/**
	 * 支付信息
	 */
	@Embedded
	private GJJPOrderPayInfo payInfo;

	/**
	 * 订单状态
	 */
	@Embedded
	private GJJPOrderStatus status;

	/**
	 * @方法功能说明：创建平台订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:12:39
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param orderId
	 * @参数：@param availableJourney
	 * @参数：@param rewPolicyInfo
	 * @参数：@param dealer
	 * @参数：@param supplier
	 * @参数：@param policySnapshot
	 * @return:void
	 * @throws
	 */
	public void create(CreateJPOrderGJCommand command, String orderId,
			AvailableJourney availableJourney, RewPolicyInfo rewPolicyInfo,
			Dealer dealer, Supplier supplier,
			JPPlatformPolicySnapshot policySnapshot) {
		
		setId(orderId);
		// ------------ 基本信息 ------------
		getBaseInfo().setDealerOrderId(command.getDealerOrderId());
		getBaseInfo().setFromDealer(dealer);
		getBaseInfo().setSupplierPolicyId(rewPolicyInfo.getPolicyId());
		getBaseInfo().setFromSupplier(supplier);
		getBaseInfo().setPolicySnapshot(policySnapshot);
		getBaseInfo().setIsChangePnr("0".equals(rewPolicyInfo.getIsChangePnr()) ? false : true);
		getBaseInfo().setOutTicketType(Integer.valueOf(rewPolicyInfo.getOutTicketType()));
		getBaseInfo().setPassengerNumber(command.getPassengerInfos().size());
		getBaseInfo().setWorkTime(rewPolicyInfo.getOpenTime());
		getBaseInfo().setRefundTime(rewPolicyInfo.getRefundTime());
		getBaseInfo().setCreateDate(new Date());
		getBaseInfo().setRemark(command.getRemark());
		// ------------ 异常订单 ------------
		getExceptionInfo();
		// ------------ 航段信息 ------------
		getSegmentInfo().setLineType(availableJourney.getBackAvailJourney() == null ? GJJPOrderSegmentInfo.LINE_TYPE_SINGLE : GJJPOrderSegmentInfo.LINE_TYPE_GO_BACK);
		getSegmentInfo().setAirRules(rewPolicyInfo.getAirRules());
		List<Flight> flights = availableJourney.getTakeoffAvailJourney().getFlights();
		getSegmentInfo().setOrgCity(flights.get(0).getOrgCity());
		getSegmentInfo().setDstCity(flights.get(flights.size() - 1).getDstCity());

		availableJourney.getTakeoffAvailJourney().getTotalDuration();

		int duration1 = convertMinutes(availableJourney.getTakeoffAvailJourney().getTotalDuration());
		int transfer1 = convertMinutes(availableJourney.getTakeoffAvailJourney().getTransferTime());
		getSegmentInfo().setTakeoffTotalDuration(duration1 + transfer1);
		getSegmentInfo().setTakeoffFlights(new ArrayList<GJFlightCabin>());
		for (Flight flight : availableJourney.getTakeoffAvailJourney().getFlights()) {
			GJFlightCabin flightCabin = new GJFlightCabin();
			flightCabin.create(flight, GJFlightCabin.TYPE_TAKEOFF, this);
			getSegmentInfo().getTakeoffFlights().add(flightCabin);
		}
		if (availableJourney.getBackAvailJourney() != null) {
			int duration2 = convertMinutes(availableJourney.getBackAvailJourney().getTotalDuration());
			int transfer2 = convertMinutes(availableJourney.getBackAvailJourney().getTransferTime());
			getSegmentInfo().setBackTotalDuration(duration2 + transfer2);
			getSegmentInfo().setBackFlights(new ArrayList<GJFlightCabin>());
			for (Flight flight : availableJourney.getBackAvailJourney().getFlights()) {
				GJFlightCabin flightCabin = new GJFlightCabin();
				flightCabin.create(flight, GJFlightCabin.TYPE_BACK, this);
				getSegmentInfo().getBackFlights().add(flightCabin);
			}
		}
		// ------------ 各乘机人信息 ------------
		setPassengers(new ArrayList<GJPassenger>());
		for (GJPassengerBaseInfoDTO passengerInfoDTO : command.getPassengerInfos()) {
			GJPassenger passenger = new GJPassenger();
			passenger.create(passengerInfoDTO, this);
			getPassengers().add(passenger);
		}
		// ------------ 联系人信息 ------------
		getContacterInfo().setContactName(command.getContacterInfo().getContactName());
		getContacterInfo().setContactMobile(command.getContacterInfo().getContactMobile());
		getContacterInfo().setContactMail(command.getContacterInfo().getContactMail());
		getContacterInfo().setContactAddress(command.getContacterInfo().getContactAddress());
		// ------------ 支付信息 ------------
		// 基础返点
		double saleBasicDisc = Double.valueOf(rewPolicyInfo.getSaleBasicDisc()) / 100;
		// 奖励返点
		double saleExtraDisc = Double.valueOf(rewPolicyInfo.getSaleExtraDisc()) / 100;
		// 实际返点
		double supplierDisc = saleBasicDisc + ((1 - saleBasicDisc) * saleExtraDisc);
		getPayInfo().setSupplierDisc(supplierDisc);
		getPayInfo().setStatus(GJJPConstants.ORDER_PAY_CLOSED);
		// ------------ 订单状态 ------------
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_ORDER_TO_SUPPLIER);
	}

	/**
	 * @方法功能说明：创建供应商订单完毕
	 * @修改者名字：zhurz
	 * @修改时间：2015-8-6上午9:43:25
	 * @修改内容：
	 * @参数：@param supplierOrder
	 * @参数：@param supplierPayPlatform
	 * @return:void
	 * @throws
	 */
	public void createSupplierOrderOver(CreateOrderResult supplierOrder, Integer supplierPayPlatform) {

		// 待向供应商下单时才处理
		if (!GJJPConstants.ORDER_STATUS_ORDER_TO_SUPPLIER.equals(getStatus().getCurrentValue()))
			return;
		
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_AUDIT_WAIT);
		getStatus().setSupplierCurrentValue(Integer.valueOf(supplierOrder.getOrdState()));
		getPayInfo().setSupplierPayPlatform(supplierPayPlatform);
		getPayInfo().setSupplierTotalPrice(Double.valueOf(supplierOrder.getTotalPrice()));
		getBaseInfo().setBookTime(DateUtil.parseDateTime(supplierOrder.getBookTime(), "yyyy-MM-dd HH:mm:ss"));
		getBaseInfo().setBookRemainTime(DateUtil.parseDateTime(supplierOrder.getBookRemainTime(), "yyyy-MM-dd HH:mm:ss"));
		getBaseInfo().setWorkTime(supplierOrder.getWorkTime());
		getBaseInfo().setSupplierOrderId(supplierOrder.getOrderId());
		getBaseInfo().setRefundTime(supplierOrder.getRefundTime());

		double platformOrderTotalPrice = 0d;
		double platformOrderCommAmount = 0d;
		// 更新乘客
		for (GJPassenger passenger : getPassengers()) {
			for (PriceDetailInfo priceDetailInfo : supplierOrder.getPriceDetailInfos()) {

				if (!Integer.valueOf(priceDetailInfo.getPassengerType()).equals(passenger.getBaseInfo().getPassengerType()))
					continue;

				GJPassengerTicketPriceDetail priceDetail = passenger.getTicketPriceDetail();
				// 票面价
				double parValue = Double.valueOf(priceDetailInfo.getOrdDetAirportFee());
				// 税费
				double tax = Double.valueOf(priceDetailInfo.getOrdDetTax());
				// 开票费
				double outTicketMoney = Double.valueOf(priceDetailInfo.getOrdDetOutTickMoney());
				// 与供应商结算价
				double supplierTotalPrice = Double.valueOf(priceDetailInfo.getOrdDetPrice());

				priceDetail.setParValue(parValue);
				priceDetail.setSupplierBasicDisc(Double.valueOf(priceDetailInfo.getOrdBasicDisc()) / 100);
				priceDetail.setSupplierGDisc(Double.valueOf(priceDetailInfo.getOrdGDisc()) / 100);
				priceDetail.setSupplierTax(tax);
				priceDetail.setSupplierTotalPrice(supplierTotalPrice);
				priceDetail.setSupplierOutTickMoney(outTicketMoney);

				JPPlatformPolicySnapshot policySnapshot = getBaseInfo().getPolicySnapshot();

				// 平台价格叠加
				double platformPolicyAmount = 0d;
				// 有平台价格政策
				if (policySnapshot != null) {
					platformPolicyAmount = MoneyUtil.round(policySnapshot.getPercentPolicy() * priceDetail.getParValue() + policySnapshot.getPricePolicy(), 2);
				}
				priceDetail.setPlatformPolicy(platformPolicyAmount);
				// 平台机票价格
				double platformTotalPrice = MoneyUtil.round(parValue + platformPolicyAmount + outTicketMoney + tax, 2);
				// 平台总支付价格
				priceDetail.setPlatformTotalPrice(platformTotalPrice);
				// 佣金(差价)=平台总支付价格-与供应商结算价
				double commAmount = MoneyUtil.round(platformTotalPrice - supplierTotalPrice, 2);
				priceDetail.setCommAmount(commAmount);
				// 统计价格
				platformOrderTotalPrice += platformTotalPrice;
				platformOrderCommAmount += commAmount;
				break;
			}
		}

		getPayInfo().setTotalPrice(MoneyUtil.round(platformOrderTotalPrice, 2));
		getPayInfo().setCommAmount(MoneyUtil.round(platformOrderCommAmount, 2));
	}

	/**
	 * @方法功能说明：供应商审核通过
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:13:11
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void supplierAuditSuccess(GJJPOrderAuditSuccessCommand command) {

		// 待审核时才处理
		if (!GJJPConstants.ORDER_STATUS_AUDIT_WAIT.equals(getStatus().getCurrentValue()))
			return;
		
		double platformOrderTotalPrice = 0d;
		double platformOrderCommAmount = 0d;

		// 更新乘客
		for (GJPassenger passenger : getPassengers()) {

			GJJPSupplierPolicy supplierPolicy = command.getPolicyMap().get(passenger.getBaseInfo().getPassengerType());
			if (supplierPolicy == null)
				continue;

			GJPassengerTicketPriceDetail priceDetail = passenger.getTicketPriceDetail();
			// 票面价
			double parValue = supplierPolicy.getTicketPrice();
			// 税费
			double tax = supplierPolicy.getTax();
			// 开票费
			double outTicketMoney = supplierPolicy.getOutTktMoney();
			// 与供应商结算价
			double supplierTotalPrice = supplierPolicy.getOrdDetPrice();

			priceDetail.setParValue(parValue);
			priceDetail.setSupplierBasicDisc(supplierPolicy.getBasicDisc());
			priceDetail.setSupplierGDisc(supplierPolicy.getExtraDisc());
			priceDetail.setSupplierTax(tax);
			priceDetail.setSupplierTotalPrice(supplierTotalPrice);
			priceDetail.setSupplierOutTickMoney(outTicketMoney);

			JPPlatformPolicySnapshot policySnapshot = getBaseInfo().getPolicySnapshot();

			// 平台价格叠加
			double platformPolicyAmount = 0d;
			// 有平台价格政策
			if (policySnapshot != null) {
				platformPolicyAmount = MoneyUtil.round(policySnapshot.getPercentPolicy() * priceDetail.getParValue() + policySnapshot.getPricePolicy(), 2);
			}
			priceDetail.setPlatformPolicy(platformPolicyAmount);
			// 平台机票价格
			double platformTotalPrice = MoneyUtil.round(parValue + platformPolicyAmount + outTicketMoney + tax, 2);
			// 平台总支付价格
			priceDetail.setPlatformTotalPrice(platformTotalPrice);
			// 佣金(差价)=平台总支付价格-与供应商结算价
			double commAmount = MoneyUtil.round(platformTotalPrice - supplierTotalPrice, 2);
			priceDetail.setCommAmount(commAmount);
			// 统计价格
			platformOrderTotalPrice += platformTotalPrice;
			platformOrderCommAmount += commAmount;
			break;
		}

		getPayInfo().setTotalPrice(MoneyUtil.round(platformOrderTotalPrice, 2));
		getPayInfo().setCommAmount(MoneyUtil.round(platformOrderCommAmount, 2));
		getPayInfo().setSupplierTotalPrice(command.getSupplierOrderTotalPrice());
		getPayInfo().setStatus(GJJPConstants.ORDER_PAY_STATUS_WAIT);

		getStatus().setSupplierCurrentValue(command.getSupplierOrderStatus());
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_PAY_WAIT);

		for (GJPassenger passenger : getPassengers())
			passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_PAY_WAIT);
	}

	/**
	 * @方法功能说明：供应商审核失败
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:13:21
	 * @修改内容：
	 * @参数：@param supplierOrderStatus
	 * @return:void
	 * @throws
	 */
	public void supplierAuditFailure(Integer supplierOrderStatus) {
		getStatus().setSupplierCurrentValue(supplierOrderStatus);
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_AUDIT_FAIL);
		for (GJPassenger passenger : getPassengers())
			passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_AUDIT_FAIL);
	}

	/**
	 * @方法功能说明：取消订单申请成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:13:46
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void cancelToSupplierAccept() {
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_CANCEL_WAIT);
		for (GJPassenger passenger : getPassengers())
			passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_CANCEL_WAIT);
	}
	
	/**
	 * @方法功能说明：取消未支付的订单申请成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:51:56
	 * @修改内容：
	 * @参数：@param cancelReasonType
	 * @参数：@param cancelReason
	 * @return:void
	 * @throws
	 */
	private void cancelNoPayToSupplierAccept(Integer cancelReasonType, String cancelReason) {
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_CANCEL_ALREADY);
		switch (cancelReasonType) {
		case 1:
			getStatus().setCancelReason("扣率过低");
			break;
		case 2:
			getStatus().setCancelReason("舱位过高");
			break;
		case 3:
			getStatus().setCancelReason("价格不正确");
			break;
		case 4:
			getStatus().setCancelReason("客人证件不齐");
			break;
		case 5:
			getStatus().setCancelReason("行程未确定");
			break;
		case 6:
			getStatus().setCancelReason(cancelReason);
			break;
		}
		getPayInfo().setStatus(GJJPConstants.ORDER_PAY_CLOSED);
		for (GJPassenger passenger : getPassengers())
			passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_CANCEL_ALREADY);
	
	}

	/**
	 * @方法功能说明：API取消未支付的订单申请成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:13:46
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void apiCancelNoPayToSupplierAccept(ApplyCancelNoPayOrderGJCommand command) {
		cancelNoPayToSupplierAccept(command.getCancelReasonType(), command.getCancelOtherReason());
	}
	
	/**
	 * @方法功能说明：ADMIN后台取消未支付的订单申请成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:13:46
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void adminCancelNoPayToSupplierAccept(ApplyCancelNoPayGJJPOrderCommand command) {
		cancelNoPayToSupplierAccept(command.getCancelReasonType(), command.getCancelOtherReason());
	}

	/**
	 * @方法功能说明：取消订单成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:14:18
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void cancelToSupplierSuccess() {
		
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_CANCEL_ALREADY);
		
		if (GJJPConstants.ORDER_PAY_STATUS_ALREADY.equals(getPayInfo().getStatus()))
			getPayInfo().setStatus(GJJPConstants.ORDER_PAY_STATUS_REFUND_WAIT);
		
		for (GJPassenger passenger : getPassengers())
			passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_CANCEL_ALREADY);
	}

	/**
	 * @方法功能说明：取消订单失败
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:14:26
	 * @修改内容：
	 * @参数：@param cancleMemo
	 * @return:void
	 * @throws
	 */
	public void cancelToSupplierFailure(String cancleMemo) {
		getStatus().setRefuseCancelReason(cancleMemo);
		if (GJJPConstants.ORDER_PAY_STATUS_ALREADY.equals(getPayInfo().getStatus())) {
			getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT);
			for (GJPassenger passenger : getPassengers())
				passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_OUT_TICKET_WAIT);
		}
	}

	/**
	 * @方法功能说明：为订单付款成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:15:58
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void payToJPOrder(PayToJPOrderGJCommand command) {
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT);
		getPayInfo().setStatus(GJJPConstants.ORDER_PAY_STATUS_ALREADY);
		getPayInfo().setPayTime(new Date());
		for (GJPassenger passenger : getPassengers())
			passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_OUT_TICKET_WAIT);
	}

	/**
	 * @方法功能说明：付款给供应商成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:21:07
	 * @修改内容：
	 * @参数：@param supplierPayPlatform
	 * @参数：@param tradeNo
	 * @参数：@param totalPrice
	 * @return:void
	 * @throws
	 */
	public void payToSupplierSuccess(Integer supplierPayPlatform, String tradeNo, Double totalPrice) {

		// 待支付时处理
		if (!GJJPConstants.ORDER_STATUS_PAY_WAIT.equals(getStatus().getCurrentValue()))
			return;
		
		getPayInfo().setSupplierPayTradeNo(tradeNo);
		getPayInfo().setSupplierPayPlatform(supplierPayPlatform);
		getPayInfo().setSupplierTotalPrice(totalPrice);
		getPayInfo().setSupplierPayTime(new Date());
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT);
		getPayInfo().setStatus(GJJPConstants.ORDER_PAY_STATUS_ALREADY);
		for (GJPassenger passenger : getPassengers())
			passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_OUT_TICKET_WAIT);
	}

	/**
	 * @方法功能说明：供应商出票失败
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:21:31
	 * @修改内容：
	 * @参数：@param refuseMemo
	 * @return:void
	 * @throws
	 */
	public void supplierOutTicketFailure(String refuseMemo) {
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_OUT_TICKET_FAIL);
		getStatus().setRefuseOutTicketReason(refuseMemo);
		for (GJPassenger passenger : getPassengers()) {
			passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_OUT_TICKET_FAIL);
		}
	}

	/**
	 * @方法功能说明：供应商出票成功
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:21:48
	 * @修改内容：
	 * @参数：@param passengerTicketMap
	 * @return:void
	 * @throws
	 */
	public void supplierOutTicketSuccess(Map<String, List<String>> passengerTicketMap) {
		getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_OUT_TICKET_ALREADY);
		getBaseInfo().setOutTicketTime(new Date());
		List<GJFlightCabin> flightCabins = new ArrayList<GJFlightCabin>();
		flightCabins.addAll(getSegmentInfo().getTakeoffFlights());
		
		if (getSegmentInfo().getBackFlights() != null)
			flightCabins.addAll(getSegmentInfo().getBackFlights());
		
		// 设置乘机人票号
		for (Entry<String, List<String>> entry : passengerTicketMap.entrySet()) {
			for (GJPassenger passenger : getPassengers()) {
				if (passenger.getTickets() != null && passenger.getTickets().size() > 0)
					continue;
				if (passenger.getBaseInfo().getPassengerName().equals(entry.getKey())) {
					passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_OUT_TICKET_ALREADY);
					if (entry.getValue().size() == flightCabins.size()) {
						for (int i = 0; i < entry.getValue().size() && i < flightCabins.size(); i++) {
							GJJPTicket ticket = new GJJPTicket();
							ticket.create(entry.getValue().get(i), passenger, flightCabins.get(i));
							passenger.getTickets().add(ticket);
						}
					} else {
						GJJPTicket ticket = new GJJPTicket();
						ticket.create(entry.getValue().get(0),passenger, null);
						passenger.getTickets().add(ticket);
					}
					break;
				}
			}
		}
	}
	
	/**
	 * @方法功能说明：添加异常订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午5:22:27
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addExceptionOrder(AddExceptionOrderCommand command) {
		Double adjustAmount = getExceptionInfo().getAdjustAmount();
		adjustAmount = adjustAmount == null ? 0d : adjustAmount;
		getExceptionInfo().setAdjustAmount(MoneyUtil.round(adjustAmount + command.getAdjustAmount(), 2));
		getExceptionInfo().setAddDate(new Date());
		getExceptionInfo().setExceptionOrder(true);
		getExceptionInfo().setAdjustReason(command.getAdjustReason());
	}
	
	/**
	 * @方法功能说明：同步供应商订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-23下午4:42:16
	 * @修改内容：
	 * @参数：@param orderDetailResult
	 * @return:void
	 * @throws
	 */
	public void syncSupplierOrder(QueryOrderDetailResult orderDetailResult) {

		OrderInfo supplierOrderInfo = orderDetailResult.getOrderInfo();
		List<PassengerInfo> passengerInfos = new ArrayList<PassengerInfo>();
		Integer supplierOrderStatus = Integer.valueOf(supplierOrderInfo.getOrdState());

		// 平台订单待审核状态，供应商订单为待支付时，改变平台订单状态
		if (GJJPConstants.ORDER_STATUS_AUDIT_WAIT.equals(getStatus().getCurrentValue())
				&& Integer.valueOf(1).equals(supplierOrderStatus)) {
			getStatus().setCurrentValue(GJJPConstants.ORDER_STATUS_PAY_WAIT);
			getPayInfo().setStatus(GJJPConstants.ORDER_PAY_STATUS_WAIT);
			for (GJPassenger passenger : getPassengers())
				passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_PAY_WAIT);
		}
		
		getStatus().setSupplierCurrentValue(supplierOrderStatus);
		getBaseInfo().setSupplierOrderId(supplierOrderInfo.getOrderId());
		getBaseInfo().setPnr(supplierOrderInfo.getOrdPnr());
		getBaseInfo().setNewPnr(supplierOrderInfo.getOrdNewPnr());
		getBaseInfo().setBpnr(supplierOrderInfo.getOrdBPnr());
		getBaseInfo().setBookTime(DateUtil.parseDateTime(supplierOrderInfo.getOrdBookTime(), "yyyy-MM-dd HH:mm:ss"));
		getBaseInfo().setOutTicketTime(DateUtil.parseDateTime(supplierOrderInfo.getOrdGetTime(), "yyyy-MM-dd HH:mm:ss"));
		getBaseInfo().setWorkTime(supplierOrderInfo.getWorkTime());
		getBaseInfo().setRefundTime(supplierOrderInfo.getRefundTime());
		if (StringUtils.isNotBlank(supplierOrderInfo.getTotalPrice()))
			getPayInfo().setSupplierTotalPrice(Double.valueOf(supplierOrderInfo.getTotalPrice()));
		getPayInfo().setSupplierPayTradeNo(supplierOrderInfo.getTradeNo());
		getPayInfo().setSupplierPayTime(DateUtil.parseDateTime(supplierOrderInfo.getOrdPayTime(), "yyyy-MM-dd HH:mm:ss"));

		// 乘机人机票设置
		if (GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT.equals(getStatus().getCurrentValue())) {

			List<GJFlightCabin> flightCabins = new ArrayList<GJFlightCabin>();
			flightCabins.addAll(getSegmentInfo().getTakeoffFlights());

			if (getSegmentInfo().getBackFlights() != null)
				flightCabins.addAll(getSegmentInfo().getBackFlights());

			// 设置乘机人票号
			for (PassengerInfo passengerInfo : passengerInfos) {
				for (GJPassenger passenger : getPassengers()) {
					if (passenger.getTickets() != null && passenger.getTickets().size() > 0)
						continue;
					if (passenger.getBaseInfo().getIdType().equals(Integer.valueOf(passengerInfo.getIdType()))
							&& passenger.getBaseInfo().getIdNo().equals(passengerInfo.getIdNo())) {

						passenger.getStatus().setSupplierCurrentValue(Integer.valueOf(passengerInfo.getTicketType()));

						// 非供应商已出票的跳过
						if (!"2".equals(passengerInfo.getTicketType()))
							continue;

						passenger.getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_OUT_TICKET_ALREADY);
						String[] ticketNos = passengerInfo.getOrdDetEticketNo().split("\\^");
						if (ticketNos.length > 1) {
							for (int i = 0; i < ticketNos.length && i < flightCabins.size(); i++) {
								GJJPTicket ticket = new GJJPTicket();
								ticket.create(ticketNos[i], passenger, flightCabins.get(i));
								passenger.getTickets().add(ticket);
							}
						} else {
							GJJPTicket ticket = new GJJPTicket();
							ticket.create(ticketNos[0], passenger, null);
							passenger.getTickets().add(ticket);
						}
						break;
					}
				}
			}

			// 全部乘机人出票完成后设置平台订单状态为已出票
			boolean allOutTicket = true;
			for (GJPassenger passenger : getPassengers()) {
				if (!GJJPConstants.TICKET_STATUS_OUT_TICKET_ALREADY.equals(passenger.getStatus())) {
					allOutTicket = false;
					break;
				}
			}
			if (allOutTicket)
				getStatus().setCurrentValue(GJJPConstants.TICKET_STATUS_OUT_TICKET_ALREADY);
		}

	}

	static int convertMinutes(String timeStr) {
		try {
			String[] times = timeStr.trim().split(":");
			return Integer.valueOf(times[0]) * 60 + Integer.valueOf(times[1]);
		} catch (Exception e) {
			return 0;
		}
	}

	public GJJPOrderBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new GJJPOrderBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(GJJPOrderBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public GJJPOrderExceptionInfo getExceptionInfo() {
		if (exceptionInfo == null)
			exceptionInfo = new GJJPOrderExceptionInfo();
		return exceptionInfo;
	}

	public void setExceptionInfo(GJJPOrderExceptionInfo exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public GJJPOrderSegmentInfo getSegmentInfo() {
		if (segmentInfo == null)
			segmentInfo = new GJJPOrderSegmentInfo();
		return segmentInfo;
	}

	public void setSegmentInfo(GJJPOrderSegmentInfo segmentInfo) {
		this.segmentInfo = segmentInfo;
	}

	public List<GJPassenger> getPassengers() {
		if (passengers == null)
			passengers = new ArrayList<GJPassenger>();
		return passengers;
	}

	public void setPassengers(List<GJPassenger> passengers) {
		this.passengers = passengers;
	}

	public GJJPOrderContacterInfo getContacterInfo() {
		if (contacterInfo == null)
			contacterInfo = new GJJPOrderContacterInfo();
		return contacterInfo;
	}

	public void setContacterInfo(GJJPOrderContacterInfo contacterInfo) {
		this.contacterInfo = contacterInfo;
	}

	public GJJPOrderPayInfo getPayInfo() {
		if (payInfo == null)
			payInfo = new GJJPOrderPayInfo();
		return payInfo;
	}

	public void setPayInfo(GJJPOrderPayInfo payInfo) {
		this.payInfo = payInfo;
	}

	public GJJPOrderStatus getStatus() {
		if (status == null)
			status = new GJJPOrderStatus();
		return status;
	}

	public void setStatus(GJJPOrderStatus status) {
		this.status = status;
	}

}
