package lxs.pojo.command.user;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ChangePasswordCommand extends BaseCommand {
	/***
	 * 新密码
	 */
	private String newPassWord;

	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}
}
