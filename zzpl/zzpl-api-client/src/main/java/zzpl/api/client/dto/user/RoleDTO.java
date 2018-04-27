package zzpl.api.client.dto.user;

import java.util.Date;
import java.util.List;

import zzpl.api.client.dto.BaseDTO;


@SuppressWarnings("serial")
public class RoleDTO extends BaseDTO {

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String description;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 添加时间
	 */
	private Date createTime;

	/**
	 * 所属公司
	 */
	private CompanyDTO companyDTO;

	/**
	 * 关联用户信息
	 */
	private List<UserRoleDTO> userRoleDTOs;

	/**
	 * 关联菜单信息
	 */
	private List<RoleMenuDTO> roleMenuDTOs;

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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

	public List<UserRoleDTO> getUserRoleDTOs() {
		return userRoleDTOs;
	}

	public void setUserRoleDTOs(List<UserRoleDTO> userRoleDTOs) {
		this.userRoleDTOs = userRoleDTOs;
	}

	public List<RoleMenuDTO> getRoleMenuDTOs() {
		return roleMenuDTOs;
	}

	public void setRoleMenuDTOs(List<RoleMenuDTO> roleMenuDTOs) {
		this.roleMenuDTOs = roleMenuDTOs;
	}

}
