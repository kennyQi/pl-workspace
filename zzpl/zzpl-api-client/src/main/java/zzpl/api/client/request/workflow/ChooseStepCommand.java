package zzpl.api.client.request.workflow;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class ChooseStepCommand extends ApiPayload{

	private Integer currentStepNO;

	private String workflowID;

	public Integer getCurrentStepNO() {
		return currentStepNO;
	}

	public void setCurrentStepNO(Integer currentStepNO) {
		this.currentStepNO = currentStepNO;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

}
