package zzpl.domain.model.workflow;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;

/**
 * 
 * @类功能说明：流程流传表，流程所有流转信息在此表中记录
 * @类修改者：
 * @修改日期：2015年7月1日下午2:01:35
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月1日下午2:01:35
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_WORKFLOW + "TASKLIST")
public class Tasklist extends BaseModel {

	/**
	 * 流程实例ID
	 */
	@Column(name = "WORKFLOW_INSTANCE_ID", length = 512)
	private String workflowInstanceID;

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
	 * 上环节处理人ID
	 */
	@Column(name = "PREVIOUS_USER_ID", length = 512)
	private String previousUserID;

	/**
	 * 上环节处理人姓名
	 */
	@Column(name = "PREVIOUS_USER_NAME", length = 512)
	private String previousUserName;

	/**
	 * 上环环节NO
	 */
	@Column(name = "PREVIOUS_STEP_NO", columnDefinition = M.NUM_COLUM)
	private Integer previousStepNO;

	/**
	 * 上环环节名
	 */
	@Column(name = "PREVIOUS_STEP_NAME", length = 512)
	private String previousStepName;

	/**
	 * 当前处理人ID
	 */
	@Column(name = "CURRENT_USER_ID", length = 512)
	private String currentUserID;

	/**
	 * 当前处理人姓名
	 */
	@Column(name = "CURRENT_USER_NAME", length = 512)
	private String currentUserName;

	/**
	 * 当前待办操作
	 */
	@Column(name = "ACTION", length = 512)
	private String action;

	/**
	 * 审批意见
	 */
	@Column(name = "OPINION", columnDefinition = M.TEXT_COLUM)
	private String opinion;

	/**
	 * 接收时间
	 */
	@Column(name = "RECEIVE_TIME", columnDefinition = M.DATE_COLUM)
	private Date receiveTime;

	/**
	 * 送出时间
	 */
	@Column(name = "SEND_TIME", columnDefinition = M.DATE_COLUM)
	private Date sendTime;

	/**
	 * 下个处理人ID
	 */
	@Column(name = "NEXT_USER_ID", columnDefinition = M.TEXT_COLUM)
	private String nextUserID;

	/**
	 * 下个处理人姓名
	 */
	@Column(name = "NEXT_USER_NAME", columnDefinition = M.TEXT_COLUM)
	private String nextUserName;

	/**
	 * 关联订单
	 */
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "tasklist", cascade = { CascadeType.ALL })
	private Comment comment;
	
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
