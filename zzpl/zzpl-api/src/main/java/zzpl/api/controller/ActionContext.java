package zzpl.api.controller;

import java.util.Map;

import zzpl.api.action.CommonAction;

public class ActionContext {

	private Map<String, CommonAction> actionMap;

	public CommonAction get(final String actionName) {
		CommonAction action = actionMap.get(actionName);
		return action == null ? actionMap.get("default") : action;
	}

	public void setActionMap(Map<String, CommonAction> actionMap) {
		this.actionMap = actionMap;
	}
}
