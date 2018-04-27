package zzpl.app.service.local.util;

import hg.common.component.BaseCommand;

public interface CommonService {
	
	/**
	 * 业务service执行成功
	 */
	public final static String SUCCESS = "1";
	
	/**
	 * 业务service执行失败，并办结流程
	 */
	public final static String FAIL = "0";
	
	
	public String execute(BaseCommand command,String workflowInstanceID);
}
