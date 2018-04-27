package zzpl.pojo.dto.user;

import zzpl.pojo.dto.BaseDTO;
import zzpl.pojo.dto.workflow.WorkflowDTO;

@SuppressWarnings("serial")
public class RoleWorkflowDTO extends BaseDTO {

	/**
	 * 流程ID
	 */
	private WorkflowDTO workflowDTO;

	/**
	 * 角色ID
	 */
	private RoleDTO roleDTO;

	public WorkflowDTO getWorkflowDTO() {
		return workflowDTO;
	}

	public void setWorkflowDTO(WorkflowDTO workflowDTO) {
		this.workflowDTO = workflowDTO;
	}

	public RoleDTO getRoleDTO() {
		return roleDTO;
	}

	public void setRoleDTO(RoleDTO roleDTO) {
		this.roleDTO = roleDTO;
	}
	
}
