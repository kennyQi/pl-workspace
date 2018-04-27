package lxs.api.v1.response.user;

import lxs.api.base.ApiResponse;

/**
 * 发送手机验证码响应
 * 
 * @author yuxx
 * 
 */
public class GetSMSValidCodeResponse extends ApiResponse {

	/**
	 * 流程id
	 */
	private String sagaId;

	public final static String RESULT_MOBILE_UNBIND = "-200"; // 非绑定用户手机号，找回密码时不可发送短信
	public final static String RESULT_MOBILE_BIND = "-201"; // 已绑定用户手机号，注册时不可发送短信
	public final static String RESULT_MOBILE_WRONG = "-202";// 手机号码格式错误

	public String getSagaId() {
		return sagaId;
	}

	public void setSagaId(String sagaId) {
		this.sagaId = sagaId;
	}
 }
