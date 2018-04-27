package zzpl.domain.model.workflow;

import java.util.Date;
import java.util.List;

import hg.common.component.BaseModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import zzpl.domain.model.M;
import zzpl.domain.model.user.RoleWorkflow;

/**
 * 
 * @类功能说明：流程表
 * @类修改者：
 * @修改日期：2015年7月7日下午4:45:38
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月7日下午4:45:38
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_WORKFLOW + "WORKFLOW")
public class Workflow extends BaseModel {

	/**
	 * 流程名称
	 */
	@Column(name = "WORKFLOW_NAME", length = 512)
	private String workflowName;

	/**
	 * 流程所属公司ID
	 */
	@Column(name = "COMPANY_ID", length = 512)
	private String companyID;

	/**
	 * 流程所属公司名
	 */
	@Column(name = "COMPANY_NAME", length = 512)
	private String companyName;

	/**
	 * 流程启动时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;
	
	/**
	 * 关联流程信息
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workflow", cascade = { CascadeType.ALL })
	private List<RoleWorkflow> roleWorkflows;

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

	public List<RoleWorkflow> getRoleWorkflows() {
		return roleWorkflows;
	}

	public void setRoleWorkflows(List<RoleWorkflow> roleWorkflows) {
		this.roleWorkflows = roleWorkflows;
	}
	
}
