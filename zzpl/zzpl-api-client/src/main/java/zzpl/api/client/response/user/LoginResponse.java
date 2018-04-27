package zzpl.api.client.response.user;

import zzpl.api.client.base.ApiResponse;

public class LoginResponse extends ApiResponse {

	private String sessionID;

	private String secretKey;

	private String userID;

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	
}
