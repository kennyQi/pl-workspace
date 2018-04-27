package zzpl.api.client.request.user;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class QueryUserQO extends ApiPayload{

	private String userID;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
