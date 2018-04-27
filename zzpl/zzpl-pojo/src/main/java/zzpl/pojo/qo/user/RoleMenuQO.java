package zzpl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class RoleMenuQO extends BaseQo {
	/**
	 * 菜单ID
	 */
	private String menuID;

	/**
	 * 角色ID
	 */
	private String roleID;

	public String getMenuID() {
		return menuID;
	}

	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

}
