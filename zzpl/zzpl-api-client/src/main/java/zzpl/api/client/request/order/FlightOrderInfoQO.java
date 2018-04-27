package zzpl.api.client.request.order;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class FlightOrderInfoQO extends ApiPayload{
	private String orderID;
	
	private String orderNO;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	
}
