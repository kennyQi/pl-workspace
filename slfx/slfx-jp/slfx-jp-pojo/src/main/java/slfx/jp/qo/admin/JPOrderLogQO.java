package slfx.jp.qo.admin;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class JPOrderLogQO extends BaseQo{
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
