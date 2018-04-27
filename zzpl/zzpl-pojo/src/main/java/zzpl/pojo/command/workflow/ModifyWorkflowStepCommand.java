package zzpl.pojo.command.workflow;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ModifyWorkflowStepCommand extends BaseCommand {

	/**
	 * 流程环节ID
	 */
	private String workflowStepID;

	/**
	 * 流程环节名称
	 */
	private String stepName;

	/**
	 * 下个环节NO 99：为结束流程
	 */
	private String nextStepNO;

	/**
	 * 下步执行人选人方式 0：结束 1：固定人员 2：按角色选人 3：按部门选人 4：自定义
	 */
	private Integer chooseExecutorType;

	/**
	 * 下步执行人 1：人员ID 2：角色ID 3：部门ID
	 */
	private String Executor;

	/**
	 * 绑定事件ID
	 */
	private String actionID;

	public String getWorkflowStepID() {
		return workflowStepID;
	}

	public void setWorkflowStepID(String workflowStepID) {
		this.workflowStepID = workflowStepID;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getNextStepNO() {
		return nextStepNO;
	}

	public void setNextStepNO(String nextStepNO) {
		this.nextStepNO = nextStepNO;
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

	public String getActionID() {
		return actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}

}
