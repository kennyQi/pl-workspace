package hsl.h5.base.result.user;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.user.UserDTO;
public class UpdateBindMobileResult extends ApiResult{

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

