package lxs.api.v1.response.user;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.user.UserDTO;

/**
 * 发送手机验证码响应
 * 
 * @author yuxx
 * 
 */
public class ModifyUserInfoResponse extends ApiResponse {

	public final static String RESULT_USER_NOTFOUND = "-100"; // 用户不存在
	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
