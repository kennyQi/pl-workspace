package zzpl.domain.model.workflow;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;

/**
 * 
 * @类功能说明：流程各环节所对应的action
 * @类修改者：
 * @修改日期：2015年7月1日下午2:05:38
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月1日下午2:05:38
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_WORKFLOW + "WORKFLOW_STEP_ACTION")
public class WorkflowStepAction extends BaseModel {

	/**
	 * 流程ID
	 */
	@ManyToOne
	@JoinColumn(name = "WORKFLOW_STEP_ID")
	private WorkflowStep workflowStep;

	/**
	 * actionID
	 */
	@ManyToOne
	@JoinColumn(name = "STEP_ACTION_ID")
	private StepAction stepAction;

	/**
	 * 排序
	 */
	@Column(name = "SORT", columnDefinition = M.NUM_COLUM)
	private Integer sort;

	/**
	 * 状态
	 * 1：送出
	 * -1：退回
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;
	
	/**
	 * 订单类型 
	 * 1：国内机票
	 * 2：国际机票
	 */
	@Column(name = "ORDER_TYPE", length = 512)
	private String orderType;

	public WorkflowStep getWorkflowStep() {
		return workflowStep;
	}

	public void setWorkflowStep(WorkflowStep workflowStep) {
		this.workflowStep = workflowStep;
	}

	public StepAction getStepAction() {
		return stepAction;
	}

	public void setStepAction(StepAction stepAction) {
		this.stepAction = stepAction;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

}
