package zzpl.domain.model.workflow;

import hg.common.component.BaseModel;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import zzpl.domain.model.M;

/**
 * 
 * @类功能说明：流程实例表，每新起草一条流程在表中插入一条记录
 * @类修改者：
 * @修改日期：2015年7月1日下午2:04:29
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月1日下午2:04:29
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_WORKFLOW + "WORKFLOW_INSTANCE")
public class WorkflowInstance extends BaseModel{

	/**
	 * 流程ID（非主键，用于识别流程）
	 */
	@Column(name = "WORKFLOW_ID", length = 512)
	private String workflowID;

	/**
	 * 流程名称
	 */
	@Column(name = "WORKFLOW_NAME", length = 512)
	private String workflowName;

	/**
	 * 流程启动时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;

	/**
	 * 流程结束时间
	 */
	@Column(name = "FINISH_TIME", columnDefinition = M.DATE_COLUM)
	private Date finishTime;

	/**
	 * 起草人ID
	 */
	@Column(name = "USER_ID", length = 512)
	private String userID;

	/**
	 * 起草人姓名
	 */
	@Column(name = "USER_NAME", length = 512)
	private String userName;

	/**
	 * 起草人所属公司ID
	 */
	@Column(name = "COMPANY_ID", length = 512)
	private String companyID;

	/**
	 * 起草人所属公司名
	 */
	@Column(name = "COMPANY_NAME", length = 512)
	private String companyName;

	/**
	 * 起草人所属部门ID
	 */
	@Column(name = "DEPARTMENT_ID", length = 512)
	private String departmentID;

	/**
	 * 起草人所属部门名
	 */
	@Column(name = "DEPARTMENT_NAME", length = 512)
	private String departmentName;

	/**
	 * 流程状态 0：流转中 1：办结 2：作废
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;

	/**
	 * 关联订单
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workflowInstance", cascade = { CascadeType.ALL })
	private List<WorkflowInstanceOrder> workflowInstanceOrders;

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<WorkflowInstanceOrder> getWorkflowInstanceOrders() {
		return workflowInstanceOrders;
	}

	public void setWorkflowInstanceOrders(
			List<WorkflowInstanceOrder> workflowInstanceOrders) {
		this.workflowInstanceOrders = workflowInstanceOrders;
	}
	
}
