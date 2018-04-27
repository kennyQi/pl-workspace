package hsl.api.v1.request.qo.company;

import hsl.api.base.ApiPayload;

public class CompanyQO extends ApiPayload{

	/**
	 * 用户id
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
