package zzpl.pojo.qo.jp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class FlightOrderInfoQO extends BaseQo {
	
	/**
	 * 订单ID
	 */
	private String orderID;
	/**
	 * 公司ID
	 */
	private String companyID;
	
	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

}
