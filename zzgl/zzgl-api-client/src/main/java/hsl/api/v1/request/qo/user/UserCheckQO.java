package hsl.api.v1.request.qo.user;

import hsl.api.base.ApiPayload;
/**
 * 校验用户名密码
 * @author zhangqy
 *
 */
public class UserCheckQO extends ApiPayload {
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	
	
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
	
}
