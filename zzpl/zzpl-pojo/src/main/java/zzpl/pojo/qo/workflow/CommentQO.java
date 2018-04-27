package zzpl.pojo.qo.workflow;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class CommentQO extends BaseQo {

	private String tasklistID;

	private Integer stepNO;

	private String currentUserID;

	public String getTasklistID() {
		return tasklistID;
	}

	public void setTasklistID(String tasklistID) {
		this.tasklistID = tasklistID;
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
	

}
