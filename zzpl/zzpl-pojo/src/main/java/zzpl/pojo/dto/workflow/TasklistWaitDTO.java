package zzpl.pojo.dto.workflow;

import java.util.Date;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class TasklistWaitDTO extends BaseDTO {
	/**
	 * 流程实例ID
	 */
	private String workflowInstanceID;

	/**
	 * 流程环节编号
	 */
	private Integer stepNO;

	/**
	 * 流程环节名称
	 */
	private String stepName;

	/**
	 * 上环节处理人ID
	 */
	private String previousUserID;

	/**
	 * 上环节处理人姓名
	 */
	private String previousUserName;

	/**
	 * 上环环节NO
	 */
	private Integer previousStepNO;

	/**
	 * 上环环节名
	 */
	private String previousStepName;

	/**
	 * 当前处理人ID
	 */
	private String currentUserID;

	/**
	 * 当前处理人姓名
	 */
	private String currentUserName;

	/**
	 * 当前待办操作
	 */
	private String action;

	/**
	 * 审批意见
	 */
	private String opinion;

	/**
	 * 接收时间
	 */
	private Date receiveTime;

	/**
	 * 送出时间
	 */
	private Date sendTime;

	/**
	 * 下个处理人ID
	 */
	private String nextUserID;

	/**
	 * 下个处理人姓名
	 */
	private String nextUserName;

	public String getWorkflowInstanceID() {
		return workflowInstanceID;
	}

	public void setWorkflowInstanceID(String workflowInstanceID) {
		this.workflowInstanceID = workflowInstanceID;
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

	public String getPreviousUserID() {
		return previousUserID;
	}

	public void setPreviousUserID(String previousUserID) {
		this.previousUserID = previousUserID;
	}

	public String getPreviousUserName() {
		return previousUserName;
	}

	public void setPreviousUserName(String previousUserName) {
		this.previousUserName = previousUserName;
	}

	public Integer getPreviousStepNO() {
		return previousStepNO;
	}

	public void setPreviousStepNO(Integer previousStepNO) {
		this.previousStepNO = previousStepNO;
	}

	public String getPreviousStepName() {
		return previousStepName;
	}

	public void setPreviousStepName(String previousStepName) {
		this.previousStepName = previousStepName;
	}

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getNextUserID() {
		return nextUserID;
	}

	public void setNextUserID(String nextUserID) {
		this.nextUserID = nextUserID;
	}

	public String getNextUserName() {
		return nextUserName;
	}

	public void setNextUserName(String nextUserName) {
		this.nextUserName = nextUserName;
	}
	
}
