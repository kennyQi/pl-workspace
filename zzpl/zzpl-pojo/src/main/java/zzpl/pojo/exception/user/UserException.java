package zzpl.pojo.exception.user;

import zzpl.pojo.exception.BaseException;

@SuppressWarnings("serial")
public class UserException extends BaseException {

	public UserException(String message) {
		super(message);
	}

	public final static String USER_NO_REPEAT = "用户编号已存在";
	
	public final static String LOGIN_NAME_REPEAT = "登录名已存在";
	
	public final static String USER_IDCARDNO_REPEAT = "身份证已存在";
	
	public final static String DEPARMENT_NOT = "部门不存在";
	
	public final static String COMPANY_NOT = "公司不存在";
	
	public final static String ILLEGAL_HANDLE = "当前用户不能对自己进行删除操作";
	
	public final static String USER_PASSWORD_ERROR = "原密码错误";
	
	public final static String COMPANY_NO = "未选择公司";
	
}
