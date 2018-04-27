package zzpl.api.client.dto.user;

import zzpl.api.client.dto.BaseDTO;

@SuppressWarnings("serial")
public class UserRoleDTO extends BaseDTO {

	/**
	 * 用户ID
	 */
	private UserDTO user;

	/**
	 * 角色ID
	 */
	private RoleDTO role;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

}
