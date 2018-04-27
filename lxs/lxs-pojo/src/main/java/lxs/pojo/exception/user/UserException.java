package lxs.pojo.exception.user;

import lxs.pojo.BaseException;


@SuppressWarnings("serial")
public class UserException extends BaseException {

	
	public UserException(Integer code, String message) {
		super(code, message);
	}
	
	/**
	 * 非绑定用户手机号，找回密码时不可发送短信
	 */
	public final static Integer RESULT_MOBILE_UNBIND = 11; 

	/**
	 * 已绑定用户手机号，注册时不可发送短信
	 */
	public final static Integer RESULT_MOBILE_BIND = 12; 
	
	/**
	 * 手机号码格式错误
	 */
	public final static Integer RESULT_MOBILE_WRONG=13;
	
	/**
	 *  短信发送失败
	 */
	public final static Integer RESULT_SEND_SMS_FAIL = 14; 
	
	/**
	 *  短信验证码错误
	 */
	public final static Integer RESULT_VALICODE_WRONG = 15; 
	
	/**
	 * 账号已存在
	 */
	public final static Integer RESULT_LOGINNAME_REPEAT = 16; 
	
	/**
	 * 短信验证码过期
	 */
	public final static Integer VALIDCODE_OVERTIME = 17;
	
	/**
	 * 短信验证码验证次数过多
	 */
	public final static Integer VALIDCODE_TOO_MANY_TIMES = 18;  
	
	/**
	 * 短信验证码输入有误
	 */
	public final static Integer VALIDCODE_WRONG = 19;  
	
	/**
	 * 用户名不存在
	 */
	public final static Integer RESULT_LOGINNAME_NOTFOUND = 20; 
	
	/**
	 * 用户名或密码错误
	 */
	public final static Integer RESULT_AUTH_FAIL = 21; 
	
	/**
	 * 微信号已被绑定
	 */
	public final static Integer RESULT_BINDING_REPEAT = 22;
	
	/**
	 * 汇购帐号不存在
	 */
	public final static Integer RESULT_HGLOGINNAME_NOTFOUND = 23; 
	
	/**
	 * 汇购帐号已绑定其它微信号
	 */
	public final static Integer RESULT_HGLOGINNAME_BINDING_REPEAT = 24; 
	
	/**
	 * 汇购密码有误
	 */
	public final static Integer RESULT_PASSWORD_WRONG = 25; 
	
	/**
	 * 旧密码有误
	 */
	public final static Integer OLD_PASSWORD_WRONG = 26; // 旧密码有误
	
	/**
	 * 用户不存在
	 */
	public final static Integer USER_NOT_FOUND = 27; // 用户不存在

	/**
	 * 验证码无效，请重新获取
	 */
	public final static Integer RESULT_VALICODE_INVALID = 28;
	/**
	 * 已绑定用户邮箱，注册时不可发送邮件
	 */
	public final static Integer RESULT_EMAIL_BIND = 29;
	
	/**
	 * 修改用户信息查询省份失败
	 */
	public final static Integer RESULT_PROVINCE_NOT_FOUND = 30;
	
	/**
	 * 修改用户信息查询城市失败
	 */
	public final static Integer RESULT_CITY_NOT_FOUND = 31;

	/**
	 * 修改用户信息查询区域失败
	 */
	public final static Integer RESULT_AREA_NOT_FOUND = 32;
	
	/**
	 * 用户名或密码不能为空
	 */
	public final static Integer RESULT_USERNAME_OR_PASSWORD_IS_ENPTY = 34;
}
