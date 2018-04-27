package zzpl.domain.model.workflow;

import hg.common.component.BaseModel;

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
 * @类功能说明：流程环节表
 * @类修改者：
 * @修改日期：2015年7月1日下午2:03:57
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月1日下午2:03:57
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_WORKFLOW + "WORKFLOW_STEP")
public class WorkflowStep extends BaseModel {

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
	 * 流程环节编号
	 */
	@Column(name = "STEP_NO", columnDefinition = M.NUM_COLUM)
	private Integer stepNO;

	/**
	 * 流程环节名称
	 */
	@Column(name = "STEP_NAME", length = 512)
	private String stepName;

	/**
	 * 上各环节NO 0：为启动流程
	 */
	@Column(name = "PREVIOUS_STEP_NO", length = 512)
	private String previousStepNO;

	/**
	 * 下个环节NO 99：为结束流程
	 */
	@Column(name = "NEXT_STEP_NO", length = 512)
	private String nextStepNO;

	/**
	 * 当前环节类型 1：单签
	 */
	@Column(name = "STEP_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer StepType;

	/**
	 * 下步执行人选人方式 0：结束 1：固定人员 2：按角色选人 3：按部门选人 4：自定义
	 */
	@Column(name = "CHOOSE_EXECUTOR_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer chooseExecutorType;
	
	public final static int END = 0;
	
	public final static int PERSON = 1;
	
	public final static int ROLE = 2;
	
	public final static int DEPT = 3;
	
	public final static int CUSTOM = 4;
	

	/**
	 * 下步执行人 1：人员ID 2：角色ID 3：部门ID
	 */
	@Column(name = "EXECUTOR", length = 512)
	private String Executor;

	/**
	 * 关联action
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workflowStep", cascade = { CascadeType.ALL })
	private List<WorkflowStepAction> workflowStepActions;

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

	public Integer getStepNO() {
		return stepNO;
	}

	public void setStepNO(Integer stepNO) {
		this.stepNO = stepNO;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getPreviousStepNO() {
		return previousStepNO;
	}

	public void setPreviousStepNO(String previousStepNO) {
		this.previousStepNO = previousStepNO;
	}

	public String getNextStepNO() {
		return nextStepNO;
	}

	public void setNextStepNO(String nextStepNO) {
		this.nextStepNO = nextStepNO;
	}

	public Integer getStepType() {
		return StepType;
	}

	public void setStepType(Integer stepType) {
		StepType = stepType;
	}

	public Integer getChooseExecutorType() {
		return chooseExecutorType;
	}

	public void setChooseExecutorType(Integer chooseExecutorType) {
		this.chooseExecutorType = chooseExecutorType;
	}

	public String getExecutor() {
		return Executor;
	}

	public void setExecutor(String executor) {
		Executor = executor;
	}

	public List<WorkflowStepAction> getWorkflowStepActions() {
		return workflowStepActions;
	}

	public void setWorkflowStepActions(
			List<WorkflowStepAction> workflowStepActions) {
		this.workflowStepActions = workflowStepActions;
	}

}
