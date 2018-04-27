package lxs.api.v1.request.qo.order.mp;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class TicketOrderQO extends ApiPayload {
	/**
	 * 当前用户id
	 */
	private String userID;

	/**
	 * 每页数量
	 */
	private String pageSize;

	/**
	 * 页码
	 */
	private String pageNO;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNO() {
		return pageNO;
	}

	public void setPageNO(String pageNO) {
		this.pageNO = pageNO;
	}

	public static void main(String[] args) {
		TicketOrderQO ticketOrderQO = new TicketOrderQO();
		ticketOrderQO.setUserID("登录人ID");
		System.out.println(JSON.toJSONString(ticketOrderQO));
	}
}
