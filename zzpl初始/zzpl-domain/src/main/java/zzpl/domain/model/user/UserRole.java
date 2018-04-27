package zzpl.domain.model.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "USER_ROLE")
public class UserRole extends BaseModel {

	/**
	 * 用户ID
	 */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	/**
	 * 角色ID
	 */
	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
