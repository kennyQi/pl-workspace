package lxs.api.v1.request.qo.mp;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class ScenicSpotSelectiveQO extends ApiPayload {

	/**
	 * 每页数量
	 */
	private String pageSize;

	/**
	 * 页码
	 */
	private String pageNO;

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
