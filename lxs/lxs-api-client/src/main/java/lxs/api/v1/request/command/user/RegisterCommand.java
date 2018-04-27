package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;

import com.alibaba.fastjson.JSON;

/**
 * 用户注册
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class RegisterCommand extends ApiPayload {

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
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 是否查询用户信息并返回
	 */
	private boolean queryUserInfo = false ;

	public boolean isQueryUserInfo() {
		return queryUserInfo;
	}

	public void setQueryUserInfo(boolean queryUserInfo) {
		this.queryUserInfo = queryUserInfo;
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

	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public static void main (String[] arg){
		RegisterCommand r = new RegisterCommand();
		r.setQueryUserInfo(true);
		r.setSagaId("b4fcf900-4c1e-46d5-9fe5-6f7c71b85b55");
		r.setEncryptPassword("175c9a6ffa39b1d637fde6f93a2bd339");
		r.setValidCode("123456");
		System.out.println(JSON.toJSON(r));
	}
}
