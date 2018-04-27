package hsl.api.v1.request.qo.company;

import hsl.api.base.ApiPayload;

public class CompanySearchQO extends ApiPayload{
	
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 搜索关键字
	 */
	private String searchName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	
}
