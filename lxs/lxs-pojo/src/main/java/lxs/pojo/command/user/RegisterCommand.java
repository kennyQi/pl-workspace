package lxs.pojo.command.user;

import hg.common.component.BaseCommand;


/**
 * 用户注册
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class RegisterCommand extends BaseCommand {

	/**
	 * 流程标识
	 */
	private String sagaId;

	/**
	 * 密码（密文）
	 */
	private String encryptPassword;

	/**
	 * 短信验证码
	 */
	private String validCode;
	
	/**
	 * 来源
	 */
	private String source;
	
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSagaId() {
		return sagaId;
	}

	public void setSagaId(String sagaId) {
		this.sagaId = sagaId;
	}

	public String getEncryptPassword() {
		return encryptPassword;
	}

	public void setEncryptPassword(String encryptPassword) {
		this.encryptPassword = encryptPassword;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

}
