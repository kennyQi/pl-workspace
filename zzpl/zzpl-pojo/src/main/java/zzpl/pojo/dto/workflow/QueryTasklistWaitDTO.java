package zzpl.pojo.dto.workflow;

import java.util.Date;
import java.util.List;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class QueryTasklistWaitDTO extends BaseDTO {
	/**
	 * 流程实例ID
	 */
	private String workflowInstanceID;
	/**
	 * 流程ID
	 */
	private String workflowID;

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
	private List<String> previousUserName;

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

	/**
	 * action名字
	 */
	private String actionName;
	private String actionID;
	
	/**
	 * action值
	 */
	private String actionValue;
	
	/**
	 * 流程名称
	 */
	private String workflowName;
	
	/**
	 * 起草人姓名
	 */
	private String userName;
	
	/**
	 * 起草人所属部门名
	 */
	private String departmentName;
	
	/**
	 * 流程环节ID
	 */
	private String workflowStepID;
	
	/**
	 * 订单类型 
	 * 1：国内机票
	 * 2：国际机票
	 */
	private String orderType;
	/**
	 * button名字
	 */
	private String buttonName;
	/**
	 * action
	 */
	private List<ActionDTO> actionDTOs;
	
	
	/**
	 * 下一环节列表
	 */
	private List<StepDTO> stepDTOs;
	
	/**
	 * 订单关联
	 */
	private List<WorkflowInstanceOrderDTO> workflowInstanceOrderDTOs;

	public String getWorkflowInstanceID() {
		return workflowInstanceID;
	}

	public void setWorkflowInstanceID(String workflowInstanceID) {
		this.workflowInstanceID = workflowInstanceID;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
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

	public List<String> getPreviousUserName() {
		return previousUserName;
	}

	public void setPreviousUserName(List<String> previousUserName) {
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

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionValue() {
		return actionValue;
	}

	public String getActionID() {
		return actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}

	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getWorkflowStepID() {
		return workflowStepID;
	}

	public void setWorkflowStepID(String workflowStepID) {
		this.workflowStepID = workflowStepID;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public List<StepDTO> getStepDTOs() {
		return stepDTOs;
	}

	public void setStepDTOs(List<StepDTO> stepDTOs) {
		this.stepDTOs = stepDTOs;
	}

	public List<WorkflowInstanceOrderDTO> getWorkflowInstanceOrderDTOs() {
		return workflowInstanceOrderDTOs;
	}

	public void setWorkflowInstanceOrderDTOs(
			List<WorkflowInstanceOrderDTO> workflowInstanceOrderDTOs) {
		this.workflowInstanceOrderDTOs = workflowInstanceOrderDTOs;
	}

	public List<ActionDTO> getActionDTOs() {
		return actionDTOs;
	}

	public void setActionDTOs(List<ActionDTO> actionDTOs) {
		this.actionDTOs = actionDTOs;
	}
	
}
