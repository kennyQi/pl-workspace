package zzpl.pojo.command.workflow;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class DeleteWorkflowStepCommand extends BaseCommand {

	private String workflowStepID;

	public String getWorkflowStepID() {
		return workflowStepID;
	}

	public void setWorkflowStepID(String workflowStepID) {
		this.workflowStepID = workflowStepID;
	}

}
