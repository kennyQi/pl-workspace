package zzpl.pojo.command.user;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class DeleteUserCommand extends BaseCommand{

	private String userID;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
