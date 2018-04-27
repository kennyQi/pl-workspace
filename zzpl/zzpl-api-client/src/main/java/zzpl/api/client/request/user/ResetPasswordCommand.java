package zzpl.api.client.request.user;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class ResetPasswordCommand extends ApiPayload {

	private String sagaID;

	private String newPassword;

	public String getSagaID() {
		return sagaID;
	}

	public void setSagaID(String sagaID) {
		this.sagaID = sagaID;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
