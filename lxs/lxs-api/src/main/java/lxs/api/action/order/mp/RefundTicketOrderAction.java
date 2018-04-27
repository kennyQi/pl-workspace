package lxs.api.action.order.mp;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.order.mp.ApplyRefundCommand;
import lxs.api.v1.response.order.mp.RefundTicketOrderResponse;
import lxs.app.service.mp.AppService;
import lxs.app.service.mp.DZPWService;
import lxs.app.service.mp.TicketOrderService;
import lxs.app.service.mp.TouristService;
import lxs.domain.model.mp.TicketOrder;
import lxs.domain.model.mp.Tourist;
import lxs.pojo.exception.mp.DZPWException;
import lxs.pojo.qo.mp.TouristQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("RefundTicketOrderAction")
public class RefundTicketOrderAction implements LxsAction{

	@Autowired
	private DZPWService dzpwService;
	@Autowired
	private TicketOrderService ticketOrderService;
	@Autowired
	private TouristService touristService;
	@Autowired
	private AppService appService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【RefundTciketOrderAction】");
		ApplyRefundCommand apiApplyRefundCommand = JSON.parseObject(apiRequest.getBody().getPayload(), ApplyRefundCommand.class);
		HgLogger.getInstance().info("lxs_dev", "【RefundTciketOrderAction】【apiApplyRefundCommand】"+JSON.toJSONString(apiApplyRefundCommand));
		RefundTicketOrderResponse refundTicketOrderResponse = new RefundTicketOrderResponse();
		TicketOrder ticketOrder =new TicketOrder();
		Tourist tourist = new Tourist();
		int touristSum = 0;
		try{
			if(StringUtils.isBlank(apiApplyRefundCommand.getOrderID())||StringUtils.isBlank(apiApplyRefundCommand.getTicketNO())){
				throw new Exception();
			}
			ticketOrder = ticketOrderService.get(apiApplyRefundCommand.getOrderID());
			if(ticketOrder==null){
				throw new Exception();
			}
			TouristQO touristQO = new TouristQO();
			touristQO.setOrderID(apiApplyRefundCommand.getOrderID());
			touristSum = touristService.queryCount(touristQO);
			touristQO.setTicketNo(apiApplyRefundCommand.getTicketNO());
			tourist = touristService.queryUnique(touristQO);
			if(tourist==null){
				throw new Exception();
			}
			if(ticketOrder.getCurrentValue()==TicketOrder.ORDER_STATUS_OUT_SUCC){
				//出票成功
				ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_CANCEL_WAIT);
				ticketOrder.localstatus(TicketOrder.ORDER_STATUS_CANCEL_WAIT, TicketOrder.PAYED, Tourist.WAIT_TO_REFUND_USER);
				ticketOrderService.update(ticketOrder);
				tourist.setLocalStatus(Tourist.WAIT_TO_REFUND_USER);
				touristService.update(tourist);
				hg.dzpw.dealer.client.api.v1.request.ApplyRefundCommand applyRefundCommand = new hg.dzpw.dealer.client.api.v1.request.ApplyRefundCommand();
				applyRefundCommand.setOrderId(apiApplyRefundCommand.getOrderID());
				applyRefundCommand.setTicketNos(new String[]{apiApplyRefundCommand.getTicketNO()});
				dzpwService.refundTicketOrder(applyRefundCommand);
				/**
				 * 调用退款
				 */
				double refundMoney = 0.0;
				if(touristSum==0){
					throw new Exception();
				}else{
					refundMoney = ticketOrder.getPrice()/touristSum;
				}
				appService.alipayRefund(ticketOrder.getSerialNumber(), refundMoney);
				ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_CANCEL_SUCC);
				ticketOrder.localstatus(TicketOrder.ORDER_STATUS_CANCEL_SUCC, TicketOrder.PAYED, Tourist.WAIT_TO_REFUND_USER);
				ticketOrderService.update(ticketOrder);
				tourist.setLocalStatus(Tourist.WAIT_TO_REFUND_USER);
				touristService.update(tourist);
			}else{
				//出票失败
				if(ticketOrder.getLocalPayStatus()==TicketOrder.PAYED){
					//已支付
					/**
					 * 调用退款
					 */
					appService.alipayRefund(ticketOrder.getSerialNumber(), ticketOrder.getPrice());
					ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_DEAL_CLOSE);
					ticketOrder.localstatus(TicketOrder.ORDER_STATUS_DEAL_CLOSE, TicketOrder.PAYED, Tourist.WAIT_TO_REFUND_USER);
					ticketOrderService.update(ticketOrder);
					tourist.setLocalStatus(Tourist.WAIT_TO_REFUND_USER);
					touristService.update(tourist);
				}else if(ticketOrder.getLocalPayStatus()==TicketOrder.WAIT_TO_PAY){
					//未支付
					ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_DEAL_CLOSE);
					ticketOrder.localstatus(TicketOrder.ORDER_STATUS_DEAL_CLOSE, TicketOrder.WAIT_TO_PAY, Tourist.NOTHING);
					ticketOrderService.update(ticketOrder);
				}
			}
			refundTicketOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			if(e instanceof DZPWException){
				ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_CANCEL_FAIL);
				ticketOrder.localstatus(TicketOrder.ORDER_STATUS_CANCEL_FAIL, TicketOrder.PAYED, Tourist.WAIT_TO_REFUND_USER);
				ticketOrderService.update(ticketOrder);
				tourist.setLocalStatus(Tourist.WAIT_TO_REFUND_USER);
				touristService.update(tourist);
			}
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			refundTicketOrderResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			refundTicketOrderResponse.setMessage("退票失败");
		}
		HgLogger.getInstance().info("lxs_dev", "【RefundTciketOrderAction】【refundTicketOrderResponse】"+JSON.toJSONString(refundTicketOrderResponse));
		return JSON.toJSONString(refundTicketOrderResponse);
	}

}
