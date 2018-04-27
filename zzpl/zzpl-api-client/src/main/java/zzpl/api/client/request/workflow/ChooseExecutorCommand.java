package zzpl.api.client.request.workflow;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class ChooseExecutorCommand extends ApiPayload {

	private String userID;

	private String workflowInstanceID;

	private String workflowID;

	private Integer nextStepNO;

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

}
