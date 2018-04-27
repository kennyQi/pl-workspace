package hsl.h5.base.result.user;
import hsl.h5.base.result.api.ApiResult;
/**
 * 发送手机验证码响应
 * @author zhangqy
 */
public class SendValidCodeResult extends ApiResult{

	/**
	 * 令牌
	 */
	private String token;

	public final static String RESULT_MOBILE_UNBIND = "-1"; // 非绑定用户手机号，找回密码时不可发送短信

	public final static String RESULT_MOBILE_BIND = "-2"; // 已绑定用户手机号，注册时不可发送短信
	
	public final static String RESULT_MOBILE_WRONG="-3";//手机号码错误

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
