package zzpl.pojo.exception.user;

import zzpl.pojo.exception.BaseException;

@SuppressWarnings("serial")
public class CompanyException extends BaseException {

	public CompanyException(String message) {
		super(message);
	}

	public final static String COMPANY_ID_REPEAT = "公司ID已存在";
	
	public final static String LOGIN_NAME_REPEAT = "管理员已存在";
}
