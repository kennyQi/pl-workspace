package hsl.api.v1.response.user;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.user.UserDTO;

public class UserEditResponse extends ApiResponse{
	
	public UserDTO userDTO;
	
	public final static String RESULT_USERID_NULL = "-1"; // 用户id不存在

	public final static String RESULT_USER_NOTEXIST = "-2"; // 用户不存在
	
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
}
