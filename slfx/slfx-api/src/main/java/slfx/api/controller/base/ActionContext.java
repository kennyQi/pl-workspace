package slfx.api.controller.base;

import java.util.Map;

/**
 * 
 * @类功能说明：分销api的接口名称和对象集合
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月23日下午2:49:30
 * @版本：V1.0
 *
 */
public class ActionContext {
	
	private Map<String, SLFXAction> actionMap;
	
	public SLFXAction get(final String actionName) {
		SLFXAction action = actionMap.get(actionName);
		return action == null ? actionMap.get("default") : action;
	}

	public void setActionMap(Map<String, SLFXAction> actionMap) {
		this.actionMap = actionMap;
	}
}
