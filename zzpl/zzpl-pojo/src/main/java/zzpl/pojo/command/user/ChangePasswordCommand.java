package zzpl.pojo.command.user;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ChangePasswordCommand extends BaseCommand {

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
