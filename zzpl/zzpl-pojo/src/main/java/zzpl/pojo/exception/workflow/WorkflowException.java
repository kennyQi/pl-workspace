package zzpl.pojo.exception.workflow;

import zzpl.pojo.exception.BaseException;

@SuppressWarnings("serial")
public class WorkflowException extends BaseException {

	public WorkflowException(Integer code, String message) {
		super(code, message);
	}

	public final static Integer SEND_NEXT_ERROR_CODE = 500;
	
	public final static String SEND_NEXT_ERROR_MESSAGE = "转送下一步错误";
	
	public final static Integer EXECUTE_ACTION_FAILED_CODE = 501;
	
	public final static String EXECUTE_ACTION_FAILED_MESSAGE = "执行service失败";

	public final static Integer TASKLIST_NOT_EXIST_CODE = 502;
	
	public final static String TASKLIST_NOT_EXIST_MESSAGE = "任务不存在";
	
	public final static Integer USER_ID_NULL_CODE = 503;

	public final static String USER_ID_NULL_MESSAGE = "用户ID为空";
	
}