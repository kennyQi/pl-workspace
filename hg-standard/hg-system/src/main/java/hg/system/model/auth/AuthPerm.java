package hg.system.model.auth;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.perm.CreateAuthPermCommand;
import hg.system.command.perm.ModifyAuthPermCommand;
import hg.system.exception.HGException;

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
 * 权限资源
 * 
 * @author zhurz
 */
@Entity
@Table(name = "AUTH_PERM")
public class AuthPerm extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "DISPLAY_NAME", length = 96)
	private String displayName;

	@Column(name = "URL", length = 512)
	private String url;

	@Column(name = "PERM_TYPE")
	private Short permType;

	@Column(name = "PERM_ROLE", length = 96)
	private String permRole;

	@Column(name = "PARENT_ID", length = 64)
	private String parentId;

	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "AUTH_ROLE_PERM", joinColumns = { @JoinColumn(name = "PERM_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<AuthRole> authRoleSet = new LinkedHashSet<AuthRole>();

	public AuthPerm() {
	}

	public AuthPerm(String url) {
		this.url = url;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Short getPermType() {
		return permType;
	}

	public void setPermType(Short permType) {
		this.permType = permType;
	}

	public String getPermRole() {
		return permRole;
	}

	public void setPermRole(String permRole) {
		this.permRole = permRole;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Set<AuthRole> getAuthRoleSet() {
		return authRoleSet;
	}

	public void setAuthRoleSet(Set<AuthRole> authRoleSet) {
		this.authRoleSet = authRoleSet;
	}

	public void createAuthPerm(CreateAuthPermCommand command)
			throws HGException {

		// 1. 检查
		StringBuffer requiredName = new StringBuffer("");
		if (StringUtils.isBlank(command.getDisplayName()))
			requiredName.append("【资源名称】");
		if (StringUtils.isBlank(command.getUrl()))
			requiredName.append("【资源链接】");
		if (command.getPermType() == null)
			requiredName.append("【资源类型】");

		if (requiredName.length() > 0)
			throw new HGException(HGException.AUTH_PERM_CREATE_NOT_REQUIRED,
					requiredName + "不能为空");

		// 2. 设置属性
		setId(UUIDGenerator.getUUID());
		setDisplayName(command.getDisplayName());
		setUrl(command.getUrl());
		setPermType(command.getPermType());
		setPermRole(command.getPermRole());
		setParentId(command.getParentId());
	}

	public void modifyAuthPerm(ModifyAuthPermCommand command)
			throws HGException {

		// 1. 检查
		StringBuffer requiredName = new StringBuffer("");
		if (StringUtils.isBlank(command.getDisplayName()))
			requiredName.append("【资源名称】");
		if (StringUtils.isBlank(command.getUrl()))
			requiredName.append("【资源链接】");
		if (command.getPermType() == null)
			requiredName.append("【资源类型】");

		if (requiredName.length() > 0)
			throw new HGException(HGException.AUTH_PERM_CREATE_NOT_REQUIRED,
					requiredName + "不能为空");

		// 2. 设置属性
		setDisplayName(command.getDisplayName());
		setUrl(command.getUrl());
		setPermType(command.getPermType());
		setPermRole(command.getPermRole());
		setParentId(command.getParentId());

	}

}