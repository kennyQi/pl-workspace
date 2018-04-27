package hsl.h5.base.result.user;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.user.UserDTO;
public class UserUpdatePasswordResult extends ApiResult{
	
	public UserDTO userDTO;
	
	public final static String OLD_PASSWORD_WRONG = "-2"; // 旧密码有误
	
	public final static String VALIDCODE_WRONG = "-1"; // 短信验证码有误
	
	public final static String USER_NOT_FOUND = "-4"; // 用户不存在
	
	public final static String VALIDCODE_OVERTIME = "-3"; // 短信验证码过期

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	
}
