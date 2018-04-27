package zzpl.pojo.dto.user;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class MenuDTO extends BaseDTO {

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 菜单描述
	 */
	private String description;

	/**
	 * URL
	 */
	private String url;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 权限
	 * 1：平台级
	 * 2：企业级
	 */
	private Integer authority;
	
	/**
	 * 关联角色信息
	 */
	private List<RoleMenuDTO> roleMenuDTOs;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	public List<RoleMenuDTO> getRoleMenuDTOs() {
		return roleMenuDTOs;
	}

	public void setRoleMenuDTOs(List<RoleMenuDTO> roleMenuDTOs) {
		this.roleMenuDTOs = roleMenuDTOs;
	}

}
