package hsl.api.v1.request.command.user;

import hsl.api.base.ApiPayload;

@SuppressWarnings("serial")
public class SendValidCodeCommand extends ApiPayload {
	
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 业务场景 1 注册时验证 2 找回密码验证3解绑手机 4绑定新手机号
	 */
	private Integer scene;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
