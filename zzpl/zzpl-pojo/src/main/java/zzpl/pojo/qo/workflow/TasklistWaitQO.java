package zzpl.pojo.qo.workflow;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class TasklistWaitQO extends BaseQo {

	private String workflowInstanceID;

	private Integer stepNO;

	private String currentUserID;

	private String stepName;

	private String action;

	public String getWorkflowInstanceID() {
		return workflowInstanceID;
	}

	public void setWorkflowInstanceID(String workflowInstanceID) {
		this.workflowInstanceID = workflowInstanceID;
	}

	public Integer getStepNO() {
		return stepNO;
	}

	public void setStepNO(Integer stepNO) {
		this.stepNO = stepNO;
	}

	public String getCurrentUserID() {
		return currentUserID;
	}

	public void setCurrentUserID(String currentUserID) {
		this.currentUserID = currentUserID;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
