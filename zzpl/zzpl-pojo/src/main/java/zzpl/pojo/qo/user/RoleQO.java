package zzpl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class RoleQO extends BaseQo{

	private String roleID;
	
	private String roleName;
	
	private String companyID;

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

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
}
