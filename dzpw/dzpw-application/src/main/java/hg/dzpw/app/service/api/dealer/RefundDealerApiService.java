package hg.dzpw.app.service.api.dealer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hg.common.component.RedisLock;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.common.adapter.DealerApiAdapter;
import hg.dzpw.app.pojo.qo.ApplyRefundRecordQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.service.local.ApplyRefundRecordLocalService;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.SingleTicketLocalService;
//import hg.dzpw.app.service.local.HJBTransferRecordLocalService;
import hg.dzpw.app.service.local.TicketOrderLocalService;
import hg.dzpw.dealer.client.api.v1.request.ApplyRefundCommand;
import hg.dzpw.dealer.client.api.v1.response.RefundResponse;
import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.common.ApiResponse;
import hg.dzpw.dealer.client.common.api.DealerApiAction;
import hg.dzpw.dealer.client.common.api.DealerApiService;
import hg.dzpw.dealer.client.common.exception.DZPWDealerApiException;
import hg.dzpw.dealer.client.dto.refund.GroupTicketRefundDTO;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.pay.ApplyRefundRecord;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：经销商申请退款
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-3-17下午5:36:44
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class RefundDealerApiService  implements DealerApiService{
	
	@Autowired
	private DealerLocalService dealerLocalService;
	
	@Autowired
	private TicketOrderLocalService ticketOrderService;
	
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	
	@Autowired
	private SingleTicketLocalService singleTicketLocalService;
	
	@Autowired
	private ApplyRefundRecordLocalService refundRecordLocalService;
	private String devName="guotx";
	private String appName="【经销商退款接口】";
	/**
	 * 
	 * @描述： 申请退款，从支付宝退至经销商签约账户
	 * @author: guotx 
	 * @version: 2016-3-17 下午1:33:54
	 */
	@DealerApiAction(DealerApiAction.ApplyRefund)
	public RefundResponse refundOrderFromAlipay(
			ApiRequest<ApplyRefundCommand> request) {

		ApplyRefundCommand refundCommand = request.getBody();
		RefundResponse resp = new RefundResponse();
		boolean hasLock = false;
		// 退款是否成功，遇到错误标记为false
		boolean isSuccess = true;
		RedisLock lock = null;
		try {
			// 查询对应的经销商ID
			String dealerId = DealerApiAdapter.getDealerId(request.getHeader()
					.getDealerKey());

			if (StringUtils.isBlank(dealerId)) {
				resp.setMessage("无效经销商代码");
				resp.setResult(ApiResponse.RESULT_DEALER_KEY_ERROR);
				return resp;
			}
			// 检查请求参数，订单和票号是否合法
			if (StringUtils.isBlank(refundCommand.getOrderId())) {
				throw new DZPWDealerApiException(
						RefundResponse.RESULT_REFUND_ERROR, "请求参数中订单号不能为空",
						RefundResponse.class);
			}
			if (refundCommand.getTicketNos() == null
					|| refundCommand.getTicketNos().length < 1) {
				throw new DZPWDealerApiException(
						RefundResponse.RESULT_REFUND_ERROR, "请求参数中票号不能为空",
						RefundResponse.class);
			}
			// 订单是否在当前经销商下
			TicketOrderQo ticketOrdeQo = new TicketOrderQo();
			ticketOrdeQo.setOrderId(refundCommand.getOrderId());
			ticketOrdeQo.setDealerId(dealerId);
			TicketOrder ticketOrder = ticketOrderService
					.queryUnique(ticketOrdeQo);

			if (ticketOrder == null) {
				throw new DZPWDealerApiException(
						RefundResponse.RESULT_ORDER_NOT_EXISTS, "订单不存在",
						RefundResponse.class);
			}

			lock = new RedisLock(refundCommand.getOrderId());
			hasLock = false;
			if (!(hasLock = lock.tryLock()))
				throw new DZPWDealerApiException(
						RefundResponse.RESULT_REFUND_ERROR, "订单正在处理中...",
						RefundResponse.class);

			if (!ticketOrder.getTicketPolicySnapshot().getSellInfo()
					.getAutoMaticRefund()) {
				throw new DZPWDealerApiException(
						RefundResponse.RESULT_REFUND_ERROR, "订单不支持退款",
						RefundResponse.class);
			}

			String[] ticketNos = refundCommand.getTicketNos();
			// 失败门票
			List<GroupTicketRefundDTO> failGroupTicketRefundDTOs = new ArrayList<GroupTicketRefundDTO>();
			ApplyRefundRecordQo refundRecordQo = new ApplyRefundRecordQo();
			GroupTicketQo groupTicketQo = new GroupTicketQo();
			for (String ticketNo : ticketNos) {
				groupTicketQo.setTicketNo(ticketNo);
				groupTicketQo.setTicketOrdeQo(ticketOrdeQo);
				GroupTicket groupTicket = groupTicketLocalService
						.queryUnique(groupTicketQo);
				if (groupTicket == null) {
					// 订单下不存在该票号
					GroupTicketRefundDTO dto = new GroupTicketRefundDTO();
					dto.setTicketNo(ticketNo);
					dto.setReason(RefundResponse.RESULT_TICKET_NOT_IN_SAME_ORDER);
					failGroupTicketRefundDTOs.add(dto);
					isSuccess = false;
					HgLogger.getInstance().debug(
							devName,
							appName + "订单" + refundCommand.getOrderId()
									+ "下不存在门票" + ticketNo);
				}
				// 只有票的状态是可退款待才能退
				if (groupTicket.getStatus().getRefundCurrent() != GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_CAN) {
					GroupTicketRefundDTO dto = new GroupTicketRefundDTO();
					dto.setTicketNo(ticketNo);
					dto.setReason(RefundResponse.RESULT_TICKET_CURRENT_NOT_APPLY_REFUND);
					failGroupTicketRefundDTOs.add(dto);
					isSuccess = false;
					HgLogger.getInstance().debug(devName,
							appName + "门票" + ticketNo + "当前状态不可退款");
				}
				//失效一个月不能申请退款
				Date useDateEnd = groupTicket.getUseDateEnd();
				Calendar useDateCalendar=Calendar.getInstance();
				useDateCalendar.setTime(useDateEnd);
				Calendar calendar=Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				if (calendar.after(useDateCalendar)) {
					GroupTicketRefundDTO dto = new GroupTicketRefundDTO();
					dto.setTicketNo(ticketNo);
					dto.setReason(RefundResponse.RESULT_REFUND_OUT_OF_DATE);
					failGroupTicketRefundDTOs.add(dto);
					isSuccess = false;
					HgLogger.getInstance().info("guotx", "门票失效超过一个月，不允许退款");
				}
				refundRecordQo.setGroupTicketQo(groupTicketQo);
				int recordSize = refundRecordLocalService
						.queryCount(refundRecordQo);
				if (recordSize > 0) {
					// 已存在该门票退款申请记录
					isSuccess = false;
					GroupTicketRefundDTO refundDTO = new GroupTicketRefundDTO();
					refundDTO.setTicketNo(ticketNo);
					refundDTO
							.setReason(RefundResponse.RESULT_TICKET_REPEAT_APPLY_REFUND);
					failGroupTicketRefundDTOs.add(refundDTO);
					HgLogger.getInstance().debug(devName,
							appName + "门票" + ticketNo + "的退款申请已存在");
				}
			}

			String[] refundTicketNos = refundCommand.getTicketNos();
			if (isSuccess) {
				for (String ticketNo : refundTicketNos) {
					// 关联订单
					groupTicketQo.setTicketOrdeQo(ticketOrdeQo);
					groupTicketQo.setTicketNo(ticketNo);
					GroupTicket groupTicket = groupTicketLocalService
							.queryUnique(groupTicketQo);

					// 添加到退款记录中，等待轮询处理
					ApplyRefundRecord record = new ApplyRefundRecord();
					record.setId(UUIDGenerator.getUUID());
					record.setGroupTicket(groupTicket);
					record.setRecordDate(new Date());
					record.setTicketOrder(ticketOrder);
					refundRecordLocalService.save(record);
				}
			} else {
				resp.setGroupTicketRefunds(failGroupTicketRefundDTOs);
				resp.setMessage("申请退款失败");
				resp.setResult(RefundResponse.RESULT_ERROR);
				return resp;
			}
			//修改可退款门票景区状态为退款待处理
			singleTicketLocalService.updateCanRefundSingleStatus(refundTicketNos);
			resp.setMessage("申请退款成功");
			resp.setResult(ApiResponse.RESULT_SUCCESS);
		} catch (DZPWDealerApiException e) {
			e.printStackTrace();
			resp.setMessage(e.getMessage());
			resp.setResult(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setMessage("退款失败,未知错误");
			resp.setResult(RefundResponse.RESULT_REFUND_ERROR);
		} finally {
			if (hasLock)
				lock.unlock();
		}
		return resp;
	}
	
	/**
	@Deprecated
	public RefundResponse refundOrder(ApiRequest<ApplyRefundCommand> request){
		
		ApplyRefundCommand refundCommand = request.getBody();
		RefundResponse resp = new RefundResponse();
		RedisLock lock = new RedisLock(refundCommand.getOrderId());
		boolean hasLock = false;
		
		//查询对应的经销商ID
		String dealerId = DealerApiAdapter.getDealerId(request.getHeader().getDealerKey());

		if (StringUtils.isBlank(dealerId)) {
			resp.setMessage("无效经销商代码");
			resp.setResult(resp.RESULT_DEALER_KEY_ERROR);
			return resp;
		}
		
		try {
			
			if(!(hasLock = lock.tryLock()))
				throw new DZPWDealerApiException(RefundResponse.RESULT_REFUND_ERROR, "订单正在处理中...", RefundResponse.class);
			
			TicketOrderQo toQo = new TicketOrderQo();
			toQo.setOrderId(refundCommand.getOrderId());
			TicketPolicySnapshotQo snapQo = new TicketPolicySnapshotQo();
			toQo.setTicketPolicySnapshotQo(snapQo);
			TicketOrder to = ticketOrderService.queryUnique(toQo);	
			
			if(to==null){
				resp.setMessage("订单不存在");
				resp.setResult(RefundResponse.RESULT_REFUND_ORDER_NOT_EXISTS);
				return resp;
			}
			
			if(to.getTicketPolicySnapshot().getSellInfo().getReturnRule()==TicketPolicySellInfo.RETURN_RULE_DISABLE){
				resp.setMessage("订单不支持退款");
				resp.setResult(RefundResponse.RESULT_REFUND_REFUSED);
				return resp;
			}
			//TODO 1.4版本处理 单独下单张票退
//			if(to.getStatus().getCurrentValue()==Integer.valueOf(OrderStatus.ORDER_STATUS_REFUND)){
//				resp.setMessage("不能给已退款的订单退款");
//				resp.setResult(RefundResponse.RESULT_REFUND_ORDER_AGAIN);
//				return resp;
//			}
			
//			if(to.getStatus().getCurrentValue()==Integer.valueOf(OrderStatus.ORDER_STATUS_CLOSE)){
//				resp.setMessage("不能给已关闭的订单退款");
//				resp.setResult(RefundResponse.RESULT_REFUND_ORDER_CLOSE);
//				return resp;
//			}
			
//			if(to.getStatus().getCurrentValue()==Integer.valueOf(OrderStatus.ORDER_STATUS_UNCOME)){
//				resp.setMessage("不能给未支付的订单退款");
//				resp.setResult(RefundResponse.RESULT_REFUND_ORDER_UNPAY);
//				return resp;
//			}

			DealerQo dQo = new DealerQo();
			dQo.setId(dealerId);
			Dealer dealer = dealerLocalService.queryUnique(dQo);
			
			DzpwTransferToDealerCommand command = new DzpwTransferToDealerCommand();
			command.setRcvCstNo(dealer.getAccountInfo().getAccountNumber());
			command.setOrderId(refundCommand.getOrderId());
			
			
			BigDecimal price = new BigDecimal(to.getPayInfo().getPrice().toString());
			BigDecimal cost  = new BigDecimal(to.getTicketPolicySnapshot().getSellInfo().getReturnCost().toString());
			
			//根据退款规则结算退款金额
			if(to.getTicketPolicySnapshot().getSellInfo().getReturnRule()==TicketPolicySellInfo.RETURN_RULE_COST_RMB_YUAN){//退票收取X元手续费
				//退款金额
				Double amount = price.subtract(cost).doubleValue();//price - cost
				if(amount<=0d)
					throw new DZPWDealerApiException(RefundResponse.RESULT_REFUND_ERROR, "退款失败,退款手续费大于等于订单金额", RefundResponse.class);
				
				command.setAmount(amount);
				
			}else if(to.getTicketPolicySnapshot().getSellInfo().getReturnRule()==TicketPolicySellInfo.RETURN_RULE_COST_PERCENT){//退票收取百分比手续费 
				//退款比例 小数
				BigDecimal r = new BigDecimal(1).subtract(cost.divide(new BigDecimal(100)));
				//退款金额
				Double amount = r.multiply(price).doubleValue();
				
				if(amount<=0)
					throw new DZPWDealerApiException(RefundResponse.RESULT_REFUND_ERROR, "退款失败,退款手续费大于等于订单金额", RefundResponse.class);
				
				command.setAmount(amount);
				
			}else{//无条件退
				command.setAmount(to.getPayInfo().getPrice());
			}
			
			ticketOrderService.refundTicketOrder(command);
			
			resp.setMessage("退款成功");
			resp.setResult(RefundResponse.RESULT_SUCCESS);
			
		} catch (DZPWDealerApiException e) {
			e.printStackTrace();
			resp.setMessage(e.getMessage());
			resp.setResult(e.getCode());
		} catch(Exception e){
			e.printStackTrace();
			resp.setMessage("退款失败");
			resp.setResult(RefundResponse.RESULT_REFUND_ERROR);
		}finally{
			if(hasLock)
				lock.unlock();
		}
			
		return resp;
	}
	
	*/
	
}
