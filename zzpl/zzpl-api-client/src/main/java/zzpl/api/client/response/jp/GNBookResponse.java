package zzpl.api.client.response.jp;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiResponse;

public class GNBookResponse extends ApiResponse {

	/**
	 * 订单编号
	 */
	private String orderID;

	/**
	 * 订单编号
	 */
	private String orderNO;

	/**
	 * 支付状态
	 */
	private String payType;

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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public static void main(String[] args) {
		System.out.println(JSON.toJSON(new GNBookResponse()));
	}
}
