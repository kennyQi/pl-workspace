package zzpl.pojo.command.saga;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ResetPasswordCommand extends BaseCommand {

	private String mobile;

	private String userID;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
