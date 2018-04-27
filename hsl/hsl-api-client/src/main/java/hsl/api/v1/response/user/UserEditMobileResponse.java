package hsl.api.v1.response.user;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.user.UserDTO;

public class UserEditMobileResponse extends ApiResponse{

	private UserDTO userDTO;
	
	public final static String RESULT_USERID_NULL = "-1"; //用户id不存在

	public final static String RESULT_MOBILE_NULL = "-2"; //手机号码不存在
	
	public final static String RESULT_USER_NOTEXIST = "-3"; //手机号码不存在

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	
}

