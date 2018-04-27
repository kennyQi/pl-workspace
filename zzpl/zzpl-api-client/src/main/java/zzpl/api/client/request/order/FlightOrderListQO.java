package zzpl.api.client.request.order;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class FlightOrderListQO extends ApiPayload {

	private String userID;

	private Integer pageSize;

	private Integer pageNO;

	/**
	 * 订单状态
	 */
	private Integer status;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNO() {
		return pageNO;
	}

	public void setPageNO(Integer pageNO) {
		this.pageNO = pageNO;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
