package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;

/**
 * 获取短信验证码
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class GetSMSValidCodeCommand extends ApiPayload {

	/**
	 * 接收验证码的手机号
	 */
	private String mobile;

	/**
	 * 业务场景
	 */
	private Integer sceneType;

	public final static Integer SCENE_TYPE_FIND_PASSWORD = 1;	//	找回密码
	public final static Integer SCENE_TYPE_REGISTER = 2;		//	注册

	public Integer getSceneType() {
		return sceneType;
	}

	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
