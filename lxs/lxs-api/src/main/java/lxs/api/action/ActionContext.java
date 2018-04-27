package lxs.api.action;

import java.util.Map;

public class ActionContext {
	
	private Map<String, LxsAction> actionMap;
	
	public LxsAction get(final String actionName) {
		LxsAction action = actionMap.get(actionName);
		return action == null ? actionMap.get("default") : action;
	}

	public void setActionMap(Map<String, LxsAction> actionMap) {
		this.actionMap = actionMap;
	}
}
