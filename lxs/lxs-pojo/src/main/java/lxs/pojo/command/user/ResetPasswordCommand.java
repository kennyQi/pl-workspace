package lxs.pojo.command.user;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ResetPasswordCommand extends BaseCommand {
	/**
	 * 用户主键
	 */
	private String userId;
	/**
	 * 新密码
	 */
	private String newPassword;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
