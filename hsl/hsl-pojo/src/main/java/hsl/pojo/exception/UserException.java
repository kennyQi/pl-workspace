package hsl.pojo.exception;


@SuppressWarnings("serial")
public class UserException extends BaseException {

	
	public UserException(Integer code, String message) {
		super(code, message);
	}
	
	/**
	 * 非绑定用户手机号，找回密码时不可发送短信
	 */
	public final static Integer RESULT_MOBILE_UNBIND = 1; 

	/**
	 * 已绑定用户手机号，注册时不可发送短信
	 */
	public final static Integer RESULT_MOBILE_BIND = 2;
	/**

	 * 手机号码错误
	 */
	public final static Integer RESULT_MOBILE_WRONG=3;
	
	/**
	 *  短信验证码错误
	 */
	public final static Integer RESULT_VALICODE_WRONG = 4; 
	
	/**
	 * 账号已存在
	 */
	public final static Integer RESULT_LOGINNAME_REPEAT = 5; 
	
	/**
	 * 短信验证码过期
	 */
	public final static Integer VALIDCODE_OVERTIME = 6;  
	
	/**
	 * 用户名不存在
	 */
	public final static Integer RESULT_LOGINNAME_NOTFOUND = 7; 
	
	/**
	 * 用户名或密码错误
	 */
	public final static Integer RESULT_AUTH_FAIL = 8; 
	
	/**
	 * 微信号已被绑定
	 */
	public final static Integer RESULT_BINDING_REPEAT = 9;
	
	/**
	 * 汇购帐号不存在
	 */
	public final static Integer RESULT_HGLOGINNAME_NOTFOUND = 10; 
	
	/**
	 * 汇购帐号已绑定其它微信号
	 */
	public final static Integer RESULT_HGLOGINNAME_BINDING_REPEAT = 11; 
	
	/**
	 * 汇购密码有误
	 */
	public final static Integer RESULT_PASSWORD_WRONG = 12; 
	
	/**
	 * 旧密码有误
	 */
	public final static Integer OLD_PASSWORD_WRONG = 13; // 旧密码有误
	
	/**
	 * 用户不存在
	 */
	public final static Integer USER_NOT_FOUND = 14; // 用户不存在

	/**
	 * 验证码无效，请重新获取
	 */
	public final static Integer RESULT_VALICODE_INVALID = 15;
	/**
	 * 已绑定用户邮箱，注册时不可发送邮件
	 */
	public final static Integer RESULT_EMAIL_BIND = 16;
	
	/**
	 * 没有查询到相应的签到活动
	 */
	public final static Integer SIGN_NOT_FOUND = 17;
	
	/**
	 * 传入参数为空
	 */
	public final static Integer SIGN_ID_IS_NULL = 18;
	
	/**
	 * 上传的签到数据不完整
	 */
	public final static Integer UPLOAD_SIGN_DATA_NOT_COMPANT = 19;
	/**
	 * 上传文件为空
	 */
	public final static Integer UPLOAD_SIGN_DATA_IS_NULL = 20;
	/**
	 * 上传的Excel格式不正确
	 */
	public final static Integer UPLOAD_SIGN_DATA_NOT_JXL = 21;

	/**
	 * 发送短信已达到上限
	 */
	public final static Integer RESULT_MOBILE_MAX = 22;

}
