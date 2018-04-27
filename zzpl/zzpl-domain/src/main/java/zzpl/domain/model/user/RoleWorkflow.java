package zzpl.domain.model.user;

import hg.common.component.BaseModel;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;
import zzpl.domain.model.workflow.Workflow;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "ROLE_WORKFLOW")
public class RoleWorkflow extends BaseModel {

	/**
	 * 流程ID
	 */
	@ManyToOne
	@JoinColumn(name = "WORKFLOW_ID")
	private Workflow workflow;

	/**
	 * 角色ID
	 */
	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
