package hsl.h5.base.result.user;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.user.UserDTO;
/**
 * 用户名密码校验
 * @author yuxx
 *
 */
public class UserCheckResult extends ApiResult{
	
	public UserDTO userDTO;
	
	public final static String RESULT_LOGINNAME_NOTFOUND = "-1"; // 用户名不存在
	
	public final static String RESULT_AUTH_FAIL = "-2"; // 用户名或密码有误

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	
}
