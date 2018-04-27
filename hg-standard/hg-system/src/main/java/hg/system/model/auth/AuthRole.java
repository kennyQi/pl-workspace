package hg.system.model.auth;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.role.CreateAuthRoleCommand;
import hg.system.command.role.ModifyAuthRoleCommand;
import hg.system.exception.HGException;

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

import org.apache.commons.lang.StringUtils;

/**
 * 用户角色
 * 
 * @author zhurz
 */
@Entity
@Table(name = "AUTH_ROLE")
public class AuthRole extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "ROLE_NAME", length = 64)
	private String roleName;

	@Column(name = "DISPLAY_NAME", length = 96)
	private String displayName;

	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "AUTH_ROLE_PERM", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERM_ID") })
	private Set<AuthPerm> authPermSet = new LinkedHashSet<AuthPerm>();

	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "AUTH_USER_ROLE", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	private Set<AuthUser> authUserSet = new LinkedHashSet<AuthUser>();

        @Column(name = "CREATE_TIME")
        private Date createTime;
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Set<AuthPerm> getAuthPermSet() {
		return authPermSet;
	}

	public void setAuthPermSet(Set<AuthPerm> authPermSet) {
		this.authPermSet = authPermSet;
	}

	public Set<AuthUser> getAuthUserSet() {
		return authUserSet;
	}

	public void setAuthUserSet(Set<AuthUser> authUserSet) {
		this.authUserSet = authUserSet;
	}

	public void createAuthRole(CreateAuthRoleCommand command)
			throws HGException {

		// 1. 检查
		StringBuffer requiredName = new StringBuffer("");
		if (StringUtils.isBlank(command.getRoleName()))
			requiredName.append("【角色名】");

		if (requiredName.length() > 0)
			throw new HGException(HGException.AUTH_ROLE_CREATE_NOT_REQUIRED,
					requiredName + "不能为空");

		// 2. 设置属性
		setId(UUIDGenerator.getUUID());
		setRoleName(command.getRoleName());
		setDisplayName(command.getDisplayName());
		setCreateTime(new Date());
	}

	public void modifyAuthRole(ModifyAuthRoleCommand command)
			throws HGException {

		// 1. 检查
		StringBuffer requiredName = new StringBuffer("");
		if (StringUtils.isBlank(command.getRoleName()))
			requiredName.append("【角色名】");

		if (requiredName.length() > 0)
			throw new HGException(HGException.AUTH_ROLE_CREATE_NOT_REQUIRED,
					requiredName + "不能为空");

		// 2. 设置属性
		setRoleName(command.getRoleName());
		setDisplayName(command.getDisplayName());
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
	    return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
	    this.createTime = createTime;
	}
}
