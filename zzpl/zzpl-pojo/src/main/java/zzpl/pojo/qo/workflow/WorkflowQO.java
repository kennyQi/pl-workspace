package zzpl.pojo.qo.workflow;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class WorkflowQO extends BaseQo {

	/**
	 * 用户角色
	 */
	private String roleID;

	/**
	 * 公司ID
	 */
	private String companyID;
	/**
	 * 流程名称
	 */
	private String workflowName;
	/**
	 * 流程ID
	 */
	private String workflowID;

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}
	
}
