package zzpl.pojo.dto.user;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class RoleMenuDTO extends BaseDTO {

	/**
	 * 菜单ID
	 */
	private MenuDTO menu;

	/**
	 * 角色ID
	 */
	private RoleDTO role;

	public MenuDTO getMenu() {
		return menu;
	}

	public void setMenu(MenuDTO menu) {
		this.menu = menu;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

}
