package zzpl.pojo.command.user;

public class ModifyRoleCommand {

	private String roleID;
	
	private String roleName;
	
	private String description;
	
	private String[] menuID;

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getMenuID() {
		return menuID;
	}

	public void setMenuID(String[] menuID) {
		this.menuID = menuID;
	}
	
}
