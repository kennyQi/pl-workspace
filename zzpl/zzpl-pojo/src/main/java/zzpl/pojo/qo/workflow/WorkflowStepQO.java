package zzpl.pojo.qo.workflow;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class WorkflowStepQO extends BaseQo {

	private String workflowStepID;

	private String workflowID;

	private Integer stepNO;

	private String orderByStepNOasc;

	public String getWorkflowStepID() {
		return workflowStepID;
	}

	public void setWorkflowStepID(String workflowStepID) {
		this.workflowStepID = workflowStepID;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public Integer getStepNO() {
		return stepNO;
	}

	public void setStepNO(Integer stepNO) {
		this.stepNO = stepNO;
	}

	public String getOrderByStepNOasc() {
		return orderByStepNOasc;
	}

	public void setOrderByStepNOasc(String orderByStepNOasc) {
		this.orderByStepNOasc = orderByStepNOasc;
	}

}
