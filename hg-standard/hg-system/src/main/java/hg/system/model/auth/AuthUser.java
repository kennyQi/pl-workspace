package hg.system.model.auth;

import hg.common.component.BaseModel;
import hg.system.model.M;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 简单用户
 * 
 * @author zhurz
 */
@Entity
@Table(name = "AUTH_USER")
public class AuthUser extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Column(name = "LOGIN_NAME", length = 64)
	private String loginName;

	@Column(name = "PASSWD", length = 128)
	private String passwd;

	@Column(name = "DISPLAY_NAME", length = 96)
	private String displayName;

	@Column(name = "ENABLE")
	private Short enable;

	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "AUTH_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<AuthRole> authRoleSet = new LinkedHashSet<AuthRole>();

	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}

	public Set<AuthRole> getAuthRoleSet() {
		return authRoleSet;
	}

	public void setAuthRoleSet(Set<AuthRole> authRoleSet) {
		this.authRoleSet = authRoleSet;
	}


}
