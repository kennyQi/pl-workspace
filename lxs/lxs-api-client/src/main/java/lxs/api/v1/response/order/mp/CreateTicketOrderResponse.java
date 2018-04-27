package lxs.api.v1.response.order.mp;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiResponse;

public class CreateTicketOrderResponse extends ApiResponse {

	/**
	 * 订单ID
	 */
	private String orderID;

	private String orderNO;

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public static void main(String[] args) {
		CreateTicketOrderResponse createTicketOrderResponse = new CreateTicketOrderResponse();
		createTicketOrderResponse.setOrderID("订单ID");
		System.out.println(JSON.toJSONString(createTicketOrderResponse));
	}
}
