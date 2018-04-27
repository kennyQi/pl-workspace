package lxs.api.v1.request.command.order.mp;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class ApplyRefundCommand extends ApiPayload {
	/**
	 * 退款门票号 支持统一订单下的多票号一起申请 必填
	 */
	private String ticketNO;

	/**
	 * 退款票号所属电子票务订单号 必填
	 */
	private String orderID;

	public String getTicketNO() {
		return ticketNO;
	}

	public void setTicketNO(String ticketNO) {
		this.ticketNO = ticketNO;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

}
