package hsl.api.v1.response.user;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.user.UserDTO;

/**
 * 用户名密码校验
 * @author yuxx
 *
 */
public class UserCheckResponse extends ApiResponse {
	
	public UserDTO userDTO;
	
	public final static String RESULT_LOGINNAME_NOTFOUND = "-1"; // 用户名不存在
	
	public final static String RESULT_AUTH_FAIL = "-2"; // 用户名或密码有误

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	
}
