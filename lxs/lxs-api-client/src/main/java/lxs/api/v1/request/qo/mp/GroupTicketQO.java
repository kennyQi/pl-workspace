package lxs.api.v1.request.qo.mp;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class GroupTicketQO extends ApiPayload {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 每页数量
	 */
	private String pageSize;

	/**
	 * 页码
	 */
	private String pageNO;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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


}
