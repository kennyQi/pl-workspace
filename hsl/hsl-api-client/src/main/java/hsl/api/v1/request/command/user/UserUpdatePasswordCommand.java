package hsl.api.v1.request.command.user;

import hsl.api.base.ApiPayload;

@SuppressWarnings("serial")
public class UserUpdatePasswordCommand extends ApiPayload {
	/**
	 * 旧密码
	 */
	private String oldPwd;
	/**
	 * 新密码
	 */
	private String newPwd;
	/**
	 * 校验验证码的令牌，来自发送验证码接口返回
	 */
	private String validToken;
	/**
	 * 验证码
	 */
	private String identify;
	/**
	 * 用户ID
	 */
	private String userId;

	public String getValidToken() {
		return validToken;
	}

	public void setValidToken(String validToken) {
		this.validToken = validToken;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
