package zzpl.pojo.exception.user;

import zzpl.pojo.exception.BaseException;

/**
 * @类功能说明：登陆_exception
 * @类修改者：zzb
 * @修改日期：2014年12月1日下午12:16:00
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年12月1日下午12:16:00
 */
@SuppressWarnings("serial")
public class LoginException extends BaseException {

	public LoginException(int code, String message) {
		super(code, message);
	}

	
	/**
	 * 验证码不正确
	 */
	public final static int VAILD_CODE_ERROR 		= 1001;
	
	/**
	 * 用户名/密码 为空
	 */
	public final static int NAME_OR_PAS_IS_EMPT 	 = 1002;
	
	/**
	 * 密码 不正确
	 */
	public final static int PASSWORD_ERROR	 		 = 1003;
	
	/**
	 * 用户已被锁定
	 */
	public final static int USER_LOCKED	 		     = 1004;
	
	/**
	 * 用户异常
	 */
	public final static int USER_ERROR	 		     = 1005;
	
	/**
	 * 公司编号错误
	 */
	public final static int COMPANY_ERROR 		= 1006;
	
}
