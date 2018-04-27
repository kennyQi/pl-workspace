package zzpl.pojo.command.workflow;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CancelOrderCommentCommand extends BaseCommand {

	/**
	 * 当前处理人ID
	 */
	private String currentUserID;

	/**
	 * 当前处理人姓名
	 */
	private String currentUserName;

	/**
	 * 流程环节编号
	 */
	private Integer stepNO;

	/**
	 * 流程环节名称
	 */
	private String stepName;
	/**
	 * 意见
	 */
	private String comment;
	/**
	 * 意见JSON
	 */
	private String commentJSON;

	/**
	 * 意见类型
	 */
	private String commentType;

	private String tasklistID;

	private String tasklistWaitID;

	/**
	 * 下一环节
	 */
	private Integer nextStepNO;
	/**
	 * 下一步执行人
	 */
	private String nextUserIDs;

	private String opinion = "Y";

	public String getCurrentUserID() {
		return currentUserID;
	}

	public void setCurrentUserID(String currentUserID) {
		this.currentUserID = currentUserID;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentJSON() {
		return commentJSON;
	}

	public void setCommentJSON(String commentJSON) {
		this.commentJSON = commentJSON;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public String getTasklistID() {
		return tasklistID;
	}

	public void setTasklistID(String tasklistID) {
		this.tasklistID = tasklistID;
	}

	public String getTasklistWaitID() {
		return tasklistWaitID;
	}

	public void setTasklistWaitID(String tasklistWaitID) {
		this.tasklistWaitID = tasklistWaitID;
	}

	public Integer getNextStepNO() {
		return nextStepNO;
	}

	public void setNextStepNO(Integer nextStepNO) {
		this.nextStepNO = nextStepNO;
	}

	public String getNextUserIDs() {
		return nextUserIDs;
	}

	public void setNextUserIDs(String nextUserIDs) {
		this.nextUserIDs = nextUserIDs;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

}
