package zzpl.pojo.qo.jp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class GJFlightOrderQO extends BaseQo {

	private String orderNO;

	private String userID;

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
