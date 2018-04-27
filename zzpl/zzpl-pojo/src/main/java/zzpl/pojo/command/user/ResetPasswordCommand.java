package zzpl.pojo.command.user;

import hg.common.component.BaseCommand;


@SuppressWarnings("serial")
public class ResetPasswordCommand extends BaseCommand {

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
