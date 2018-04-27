package hsl.spi.action;

import java.util.Map;

public class ActionContext {
	
	private Map<String, HSLAction> actionMap;
	
	public HSLAction get(final String actionName) {
		HSLAction action = actionMap.get(actionName);
		return action == null ? actionMap.get("default") : action;
	}

	public void setActionMap(Map<String, HSLAction> actionMap) {
		this.actionMap = actionMap;
	}
}
