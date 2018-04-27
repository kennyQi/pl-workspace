package zzpl.pojo.qo.workflow;

import java.util.Date;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class TasklistQO extends BaseQo {

	private String workflowInstanceID;

	private Integer stepNO;

	private String currentUserID;
	
	/**
	 * 送出时间
	 */
	private Date leSendTime;

	private boolean orderByReceiveTimeDesc = false;
	
	private boolean sendTimeAsc = false;

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

	public String getCurrentUserID() {
		return currentUserID;
	}

	public void setCurrentUserID(String currentUserID) {
		this.currentUserID = currentUserID;
	}

	public Date getLeSendTime() {
		return leSendTime;
	}

	public void setLeSendTime(Date leSendTime) {
		this.leSendTime = leSendTime;
	}

	public boolean isOrderByReceiveTimeDesc() {
		return orderByReceiveTimeDesc;
	}

	public void setOrderByReceiveTimeDesc(boolean orderByReceiveTimeDesc) {
		this.orderByReceiveTimeDesc = orderByReceiveTimeDesc;
	}

	public boolean isSendTimeAsc() {
		return sendTimeAsc;
	}

	public void setSendTimeAsc(boolean sendTimeAsc) {
		this.sendTimeAsc = sendTimeAsc;
	}

}
