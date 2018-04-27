package zzpl.api.client.response.user;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.UserDTO;

public class QueryUserResponse extends ApiResponse {

	private UserDTO userDTO;

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

}
