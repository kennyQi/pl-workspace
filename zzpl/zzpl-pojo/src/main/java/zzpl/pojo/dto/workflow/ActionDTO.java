package zzpl.pojo.dto.workflow;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class ActionDTO extends BaseDTO {
	
	private String actionID;
	/**
	 * 环节编号
	 */
	private String buttonName;
	/**
	 * 环节名称
	 */
	private String actionName;
	/**
	 * action值
	 */
	private String actionValue;
	
	public String getActionID() {
		return actionID;
	}
	public void setActionID(String actionID) {
		this.actionID = actionID;
	}
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
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
	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}
	
	
	
}
