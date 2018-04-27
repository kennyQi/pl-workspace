package hsl.pojo.command;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class SendMailCodeCommand extends BaseCommand {
	
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 邮件号码
	 */
	private String mail;
	/**
	 * 业务常量：找回密码：1
	 */
	public static final int SENDMAIL_SCENE_RESETPWD=1;
	/**
	 * 业务常量：注册激活：2
	 */
	public static final int SENDMAIL_SCENE_REGISTER=2;
	/**
	 * 业务场景 1 找回密码  2 注册激活
	 */
	private Integer scene;

	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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
