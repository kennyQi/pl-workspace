package lxs.api.action.order.mp;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.order.mp.CloseTicketOrderCommand;
import lxs.api.v1.response.order.mp.CloseTicketOrderResponse;
import lxs.app.service.mp.DZPWService;
import lxs.app.service.mp.TicketOrderService;
import lxs.domain.model.mp.TicketOrder;
import lxs.domain.model.mp.Tourist;
import lxs.pojo.exception.mp.CloseTicketOrderException;
import lxs.pojo.exception.mp.DZPWException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("CloseTicketOrderAction")
public class CloseTicketOrderAction implements LxsAction{

	@Autowired
	private TicketOrderService ticketOrderService;
	@Autowired
	private DZPWService dzpwService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【CloseTicketOrderAction】");
		CloseTicketOrderCommand closeTicketOrderCommand = JSON.parseObject(apiRequest.getBody().getPayload(), CloseTicketOrderCommand.class);
		HgLogger.getInstance().info("lxs_dev", "【CloseTicketOrderAction】【closeTicketOrderCommand】"+JSON.toJSONString(closeTicketOrderCommand));
		CloseTicketOrderResponse closeTicketOrderResponse = new CloseTicketOrderResponse();
		try{
			if(StringUtils.isBlank(closeTicketOrderCommand.getOrderID())||StringUtils.isBlank(closeTicketOrderCommand.getOrderNO())){
				throw new CloseTicketOrderException(CloseTicketOrderException.PARAMETER_ILLEGAL);
			}
			TicketOrder ticketOrder = ticketOrderService.get(closeTicketOrderCommand.getOrderID());
			if(ticketOrder!=null&&StringUtils.isNotBlank(ticketOrder.getOrderNO())&&StringUtils.equals(ticketOrder.getOrderNO(), closeTicketOrderCommand.getOrderNO())){
				hg.dzpw.dealer.client.api.v1.request.CloseTicketOrderCommand DZPWCloseTicketOrderCommand = new hg.dzpw.dealer.client.api.v1.request.CloseTicketOrderCommand();
				DZPWCloseTicketOrderCommand.setDealerOrderId(ticketOrder.getOrderNO());
				dzpwService.closeTicketOrder(DZPWCloseTicketOrderCommand);
				ticketOrder.setCurrentValue(TicketOrder.ORDER_STATUS_DEAL_CLOSE);
				ticketOrder.localstatus(TicketOrder.ORDER_STATUS_DEAL_CLOSE, TicketOrder.WAIT_TO_PAY, Tourist.NOTHING);
				ticketOrderService.update(ticketOrder);
			}
			closeTicketOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			String message = "";
			if(e instanceof CloseTicketOrderException||e instanceof DZPWException){
				message=e.getMessage();
			}else {
				message="关闭门票订单失败";
			}
			closeTicketOrderResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			closeTicketOrderResponse.setMessage(message);
		}
		HgLogger.getInstance().info("lxs_dev", "【CloseTicketOrderAction】【closeTicketOrderResponse】"+JSON.toJSONString(closeTicketOrderResponse));
		return JSON.toJSONString(closeTicketOrderResponse);
	}

}
