package hsl.pojo.command;

import hg.common.component.BaseCommand;



@SuppressWarnings("serial")
public class BindWXCommand extends BaseCommand {
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
