package hsl.api.v1.response.user;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.user.UserBindAccountDTO;

public class BindWXResponse extends ApiResponse {
	
	public UserBindAccountDTO userBindAccountDTO;

	public final static String RESULT_BINDING_REPEAT = "-1"; // 微信号已被绑定
	
	public final static String RESULT_HGLOGINNAME_NOTFOUND = "-2"; // 汇购帐号不存在
	
	public final static String RESULT_HGLOGINNAME_BINDING_REPEAT = "-3"; // 汇购帐号已绑定其它微信号
	
	public final static String RESULT_PASSWORD_WRONG = "-4"; // 汇购密码有误

	public void setUserBindAccountDTO(UserBindAccountDTO userBindAccountDTO) {
		this.userBindAccountDTO = userBindAccountDTO;
	}
	
	
}
