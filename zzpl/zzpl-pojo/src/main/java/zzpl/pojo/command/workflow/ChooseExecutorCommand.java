package zzpl.pojo.command.workflow;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ChooseExecutorCommand extends BaseCommand {

	private String userID;

	private String workflowInstanceID;

	private String workflowID;

	private Integer nextStepNO;
	
	private String companyID;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getWorkflowInstanceID() {
		return workflowInstanceID;
	}

	public void setWorkflowInstanceID(String workflowInstanceID) {
		this.workflowInstanceID = workflowInstanceID;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public Integer getNextStepNO() {
		return nextStepNO;
	}

	public void setNextStepNO(Integer nextStepNO) {
		this.nextStepNO = nextStepNO;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

}
