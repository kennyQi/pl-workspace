package hsl.pojo.command;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class UpdateBindMobileCommand extends BaseCommand {

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
