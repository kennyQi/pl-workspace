package lxs.api.v1.response.order.line;

import lxs.api.base.ApiResponse;

public class GetShouldPayMoneyResponse extends ApiResponse {
	/**
	 * 支付定金
	 */
	public static final String PAY_BARGAIN_MONEY = "1";
	/**
	 * 支付尾款
	 */
	public static final String PAY_BALANCE_MONEY = "2";
	/**
	 * 支付全款
	 */
	public static final String SHOP_ALL_MONEY = "3";

	private String shouldPayMoney;

	private String orderNO;

	private String orderID;

	private String payType;

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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getShouldPayMoney() {
		return shouldPayMoney;
	}

	public void setShouldPayMoney(String shouldPayMoney) {
		this.shouldPayMoney = shouldPayMoney;
	}

}
