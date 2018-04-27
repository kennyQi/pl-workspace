package zzpl.pojo.dto.workflow;

import java.util.Date;
import java.util.List;

import zzpl.pojo.dto.BaseDTO;
import zzpl.pojo.dto.user.RoleWorkflowDTO;

@SuppressWarnings("serial")
public class WorkflowDTO extends BaseDTO {
	/**
	 * 流程名称
	 */
	private String workflowName;

	/**
	 * 流程所属公司ID
	 */
	private String companyID;

	/**
	 * 流程所属公司名
	 */
	private String companyName;

	/**
	 * 流程启动时间
	 */
	private Date createTime;
	
	/**
	 * 关联流程信息
	 */
	private List<RoleWorkflowDTO> roleWorkflowDTOs;

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<RoleWorkflowDTO> getRoleWorkflowDTOs() {
		return roleWorkflowDTOs;
	}

	public void setRoleWorkflowDTOs(List<RoleWorkflowDTO> roleWorkflowDTOs) {
		this.roleWorkflowDTOs = roleWorkflowDTOs;
	}
	
}
