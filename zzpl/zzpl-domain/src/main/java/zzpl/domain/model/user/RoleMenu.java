package zzpl.domain.model.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "ROLE_MENU")
public class RoleMenu extends BaseModel {

	/**
	 * 菜单ID
	 */
	@ManyToOne
	@JoinColumn(name = "MENU_ID")
	private Menu menu;

	/**
	 * 角色ID
	 */
	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
