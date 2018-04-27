package zzpl.api.client.response.jp;

import zzpl.api.client.base.ApiResponse;

public class GNTeamBookResponse extends ApiResponse {

	/**
	 * 订单编号
	 */
	private String orderNO;

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

}
