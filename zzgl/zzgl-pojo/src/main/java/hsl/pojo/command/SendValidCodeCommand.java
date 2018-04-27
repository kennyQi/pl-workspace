package hsl.pojo.command;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class SendValidCodeCommand extends BaseCommand {
	
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 业务场景 1 注册时验证 2 找回密码验证 3解绑手机 4绑定新手机号5查看充值兑换码
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
