package zzpl.api.client.request.user;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiPayload;
import zzpl.api.client.dto.user.UserDTO;

@SuppressWarnings("serial")
public class LoginCommand extends ApiPayload{
	private UserDTO userDTO;

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public static void main(String[] args) {
		LoginCommand loginCommand = new LoginCommand();
		UserDTO userDTO = new UserDTO();
		userDTO.setLoginName("admin");
		userDTO.setPassword("45asdf654ad65f454fh8yfghjkhs");
		userDTO.setUserNO("ZZPL001");
		loginCommand.setUserDTO(userDTO);
		System.out.println(JSON.toJSON(loginCommand));
	}
	
}
