package hsl.api.v1.request.command.user;

import hsl.api.base.ApiPayload;

@SuppressWarnings("serial")
public class UserEditMobileCommand extends ApiPayload{
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 绑定的新手机号
	 */
	private String mobile;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
