package zzpl.domain.model.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "MENU")
public class Menu extends BaseModel {

	/**
	 * 菜单名称
	 */
	@Column(name = "MENU_NAME", length = 512)
	private String menuName;

	/**
	 * 菜单描述
	 */
	@Column(name = "DESCRIPTION", length = 512)
	private String description;

	/**
	 * URL
	 */
	@Column(name = "URL", length = 512)
	private String url;

	/**
	 * 排序
	 */
	@Column(name = "SORT", columnDefinition = M.NUM_COLUM)
	private Integer sort;

	/**
	 * 关联角色信息
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menu", cascade = { CascadeType.ALL })
	private List<RoleMenu> roleMenus;

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

	public List<RoleMenu> getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(List<RoleMenu> roleMenus) {
		this.roleMenus = roleMenus;
	}

}
