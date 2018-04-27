package hsl.api.v1.request.command.user;

import hsl.api.base.ApiPayload;

@SuppressWarnings("serial")
public class BindWXCommand extends ApiPayload {
	/**
	 * 微信openId
	 */
	private String wxAccountId;
	/**
	 * 微信号
	 */
	private String wxAccountName;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 登录密码
	 */
	private String password;

	public String getWxAccountName() {
		return wxAccountName;
	}

	public void setWxAccountName(String wxAccountName) {
		this.wxAccountName = wxAccountName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWxAccountId() {
		return wxAccountId;
	}

	public void setWxAccountId(String wxAccountId) {
		this.wxAccountId = wxAccountId;
	}

}
