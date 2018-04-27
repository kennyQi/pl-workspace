package zzpl.pojo.qo.workflow;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class WorkflowStepActionQO extends BaseQo {

	private String workflowStepID;

	private Integer status;
	
	private String orderType;
	
	private String viewActionValue;

	public String getWorkflowStepID() {
		return workflowStepID;
	}

	public void setWorkflowStepID(String workflowStepID) {
		this.workflowStepID = workflowStepID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getViewActionValue() {
		return viewActionValue;
	}

	public void setViewActionValue(String viewActionValue) {
		this.viewActionValue = viewActionValue;
	}


}
