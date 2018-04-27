package hsl.pojo.command;

import hg.common.component.BaseCommand;


@SuppressWarnings("serial")
public class UserUpdatePasswordCommand extends BaseCommand {
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
	/**
	 * 找回密码类型 1手机找回 2 邮箱找回
	 */
	private int type;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
