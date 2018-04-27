package hsl.h5.base.result.user;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.user.UserDTO;
/**
 * 注册响应
 * @author zhangqy
 *
 */
public class UserRegisterResult extends ApiResult{
	

	public UserDTO userDTO;
	
	public final static String RESULT_VALICODE_WRONG = "-1"; // 短信验证码错误

	public final static String RESULT_LOGINNAME_REPEAT = "-2"; // 登录名重复
	
	public final static String VALIDCODE_OVERTIME = "-3"; // 短信验证码过期

	public final static String RESULT_VALICODE_INVALID="-4"; //验证码无效，请重新获取
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	
}
