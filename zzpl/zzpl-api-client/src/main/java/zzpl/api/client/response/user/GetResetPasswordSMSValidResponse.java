package zzpl.api.client.response.user;

import zzpl.api.client.base.ApiResponse;

public class GetResetPasswordSMSValidResponse extends ApiResponse {

	private String sagaID;

	/**
	 * 联系电话
	 */
	private String linkMobile;

	public String getSagaID() {
		return sagaID;
	}

	public void setSagaID(String sagaID) {
		this.sagaID = sagaID;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

}
