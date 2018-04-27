package zzpl.pojo.command.user;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class DeleteRoleCommand extends BaseCommand {

	private String roleID;
	
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	
}
