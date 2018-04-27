package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;

import com.alibaba.fastjson.JSON;

/**
 * 重置密码
 * 
 * @author zhuxy
 * 
 */
@SuppressWarnings("serial")
public class ResetPasswordCommand extends ApiPayload {

	/**
	 * 流程标识
	 */
	private String sagaId;

	/**
	 * 新密码（密文）
	 */
	private String encryptPassword;

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
	
	public static void main(String[] arg){
		ResetPasswordCommand r = new ResetPasswordCommand();
		r.setSagaId("b4fcf900-4c1e-46d5-9fe5-6f7c71b85b55");
		r.setEncryptPassword("6f7c71b85b55");
		System.out.println(JSON.toJSON(r));
	}

}
