package zzpl.pojo.command.user;

public class AddRoleCommand {

	private String roleName;
	
	private String description;
	
	private String userID;
	
	private String[] menuID;
	
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
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String[] getMenuID() {
		return menuID;
	}
	public void setMenuID(String[] menuID) {
		this.menuID = menuID;
	}
	
}
