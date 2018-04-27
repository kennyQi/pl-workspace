package lxs.api.v1.request.qo.user;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class ContactsQO extends ApiPayload {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
