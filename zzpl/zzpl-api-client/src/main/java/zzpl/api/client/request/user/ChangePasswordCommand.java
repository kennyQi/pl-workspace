package zzpl.api.client.request.user;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class ChangePasswordCommand extends ApiPayload {

	private String userID;

	private String password;

	private String newPassword;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
