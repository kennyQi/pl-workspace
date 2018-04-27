package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;

import com.alibaba.fastjson.JSON;

/**
 * 用户登录
 * 
 * @author zhuxy
 * 
 */
@SuppressWarnings("serial")
public class ResetPasswordCheckMobileCommand extends ApiPayload {

	/**
	 * 流程标识
	 */
	private String sagaId;

	/**
	 * 验证码
	 */
	private String validCode;

	public String getSagaId() {
		return sagaId;
	}

	public void setSagaId(String sagaId) {
		this.sagaId = sagaId;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
	public static void  main(String[] args){
		ResetPasswordCheckMobileCommand r = new ResetPasswordCheckMobileCommand();
		r.setSagaId("b4fcf900-4c1e-46d5-9fe5-6f7c71b85b55");
		r.setValidCode("123456");
		System.out.println(JSON.toJSON(r));
	}
}
