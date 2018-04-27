package zzpl.pojo.command.workflow;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class AddWorkflowCommand extends BaseCommand {

	private String userID;
	
	private String workflowName;
	
	private String[] roleIds;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
}
