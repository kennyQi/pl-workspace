package zzpl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class RoleWorkflowQO extends BaseQo {

	/**
	 * 用户角色
	 */
	private String roleID;

	/**
	 * 公司ID
	 */
	private String companyID;
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

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}
	
}
