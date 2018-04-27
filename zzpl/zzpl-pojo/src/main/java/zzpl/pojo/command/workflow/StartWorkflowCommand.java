package zzpl.pojo.command.workflow;

import java.util.List;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：启动流程
 * @类修改者：
 * @修改日期：2015年7月2日下午3:07:55
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月2日下午3:07:55
 */
@SuppressWarnings("serial")
public class StartWorkflowCommand extends BaseCommand {

	/**
	 * 流程ID
	 */
	private String workflowID;

	/**
	 * 流程实例ID（起草时，不需要该字段）
	 */
	private String workflowInstanceID;

	/**
	 * 当前环节
	 */
	private Integer currentStepNO;
	
	/**
	 * 下一个环节
	 */
	private Integer nextStepNO;

	/**
	 * 当前执行人ID
	 */
	private String currentUserID;

	/**
	 * 下一步执行人ID（办结时，不需要该字段）
	 */
	private List<String> nextUserIDs;

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public String getWorkflowInstanceID() {
		return workflowInstanceID;
	}

	public void setWorkflowInstanceID(String workflowInstanceID) {
		this.workflowInstanceID = workflowInstanceID;
	}

	public Integer getCurrentStepNO() {
		return currentStepNO;
	}

	public void setCurrentStepNO(Integer currentStepNO) {
		this.currentStepNO = currentStepNO;
	}

	public Integer getNextStepNO() {
		return nextStepNO;
	}

	public void setNextStepNO(Integer nextStepNO) {
		this.nextStepNO = nextStepNO;
	}

	public String getCurrentUserID() {
		return currentUserID;
	}

	public void setCurrentUserID(String currentUserID) {
		this.currentUserID = currentUserID;
	}

	public List<String> getNextUserIDs() {
		return nextUserIDs;
	}

	public void setNextUserIDs(List<String> nextUserIDs) {
		this.nextUserIDs = nextUserIDs;
	}

}