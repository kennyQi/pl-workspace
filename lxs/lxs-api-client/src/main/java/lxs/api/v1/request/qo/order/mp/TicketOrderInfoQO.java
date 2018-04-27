package lxs.api.v1.request.qo.order.mp;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class TicketOrderInfoQO extends ApiPayload {

	/**
	 * 订单ID
	 */
	private String orderID;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public static void main(String[] args) {
		TicketOrderInfoQO ticketOrderInfoQO = new TicketOrderInfoQO();
		ticketOrderInfoQO.setOrderID("订单ID");
		System.out.println(JSON.toJSONString(ticketOrderInfoQO));
	}
}
