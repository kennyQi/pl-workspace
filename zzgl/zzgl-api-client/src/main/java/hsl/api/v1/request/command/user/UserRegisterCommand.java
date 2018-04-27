package hsl.api.v1.request.command.user;

import hsl.api.base.ApiPayload;

/**
 * 
 * @author zhangqy
 * 
 */
@SuppressWarnings("serial")
public class UserRegisterCommand extends ApiPayload {
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 校验验证码的令牌，来自发送验证码接口返回
	 */
	private String validToken;
	/**
	 * 短信验证码
	 */
	private String validCode;
	
	/**
	 * 类型 1为个人 2为企业
	 */
	private Integer type;


	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getValidToken() {
		return validToken;
	}

	public void setValidToken(String validToken) {
		this.validToken = validToken;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}



}
