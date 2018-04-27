package lxs.api.v1.response.user;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.user.UserDTO;

import com.alibaba.fastjson.JSON;

public class RegisterResponse extends ApiResponse {
	/**
	 * 流程id
	 */

	public UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public static void main (String[] arg){
		RegisterResponse R = new RegisterResponse();
		R.setMessage("注册成功");
		R.setResult(1);
		System.out.println(JSON.toJSON(R));
		
	}
}
