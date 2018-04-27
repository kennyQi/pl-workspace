package zzpl.pojo.command.workflow;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ChooseStepCommand extends BaseCommand{

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
