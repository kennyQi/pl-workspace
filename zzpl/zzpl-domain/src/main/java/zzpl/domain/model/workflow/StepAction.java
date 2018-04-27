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
 * @类功能说明：环节操作表，流程流转过程时，获取当前环节action，按照sort依次执行
 * @类修改者：
 * @修改日期：2015年7月1日下午2:00:12
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月1日下午2:00:12
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_WORKFLOW + "STEP_ACTION")
public class StepAction extends BaseModel {

	/**
	 * action名字
	 */
	@Column(name = "ACTION_NAME", length = 512)
	private String actionName;

	/**
	 * button名字
	 */
	@Column(name = "BUTTON_NAME", length = 512)
	private String buttonName;

	/**
	 * action值
	 */
	@Column(name = "ACTION_VALUE", length = 512)
	private String actionValue;
	
	/**
	 * 作为待办列表展现的action值
	 */
	@Column(name = "VIEW_ACTION_VALUE", length = 512)
	private String viewActionValue;

	/**
	 * 订单类型 1：国内机票 2：国际机票
	 */
	@Column(name = "ORDER_TYPE", length = 512)
	private String orderType;

	/**
	 * 关联workflow
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stepAction", cascade = { CascadeType.ALL })
	private List<WorkflowStepAction> workflowStepActions;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getActionValue() {
		return actionValue;
	}

	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}

	public String getViewActionValue() {
		return viewActionValue;
	}

	public void setViewActionValue(String viewActionValue) {
		this.viewActionValue = viewActionValue;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public List<WorkflowStepAction> getWorkflowStepActions() {
		return workflowStepActions;
	}

	public void setWorkflowStepActions(List<WorkflowStepAction> workflowStepActions) {
		this.workflowStepActions = workflowStepActions;
	}

	
}
