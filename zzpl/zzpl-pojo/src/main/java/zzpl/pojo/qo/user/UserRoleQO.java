package zzpl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class UserRoleQO extends BaseQo {

	/**
	 * 用户ID
	 */
	private String userID;

	/**
	 * 角色ID
	 */
	private String roleID;

	/**
	 * 根据公司ID关联查询user表
	 */
	private String companyIDLike;

	private Integer status;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getCompanyIDLike() {
		return companyIDLike;
	}

	public void setCompanyIDLike(String companyIDLike) {
		this.companyIDLike = companyIDLike;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
