package zzpl.pojo.command.workflow;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ConfigWorkflowCommand extends BaseCommand {

	private String workflowID;

	/**
	 * 流程环节名称
	 */
	private String stepName;

	/**
	 * 下步执行人选人方式 0：结束 1：固定人员 2：按角色选人 3：按部门选人 4：自定义
	 */
	private Integer chooseExecutorType;

	/**
	 * 下步执行人 1：人员ID 2：角色ID 3：部门ID
	 */
	private String executor;

	/**
	 * 绑定事件ID
	 */
	private String actionID;

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public Integer getChooseExecutorType() {
		return chooseExecutorType;
	}

	public void setChooseExecutorType(Integer chooseExecutorType) {
		this.chooseExecutorType = chooseExecutorType;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getActionID() {
		return actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}

}
