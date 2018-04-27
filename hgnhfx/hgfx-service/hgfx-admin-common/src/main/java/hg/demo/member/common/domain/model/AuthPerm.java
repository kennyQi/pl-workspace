/**
 * @AuthPerm.java Create on 2016-5-19下午3:32:17
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.domain.model;

import hg.framework.common.base.BaseStringIdModel;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @类功能说明：资源类
 * @类修改者：
 * @修改日期：2016-5-19下午3:32:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-19下午3:32:17
 * @version：
 */
@Entity
@Table(name = "AUTH_PERM")
public class AuthPerm extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	@Column(name = "DISPLAY_NAME", length = 96)
	private String displayName;
	/**
	 * url地址
	 */
	@Column(name = "URL", length = 512)
	private String url;
	/**
	 * 资源类型
	 */
	@Column(name = "PERM_TYPE")
	private Short permType;
	/**
	 * 需要检查的角色名
	 */
	@Column(name = "PERM_ROLE", length = 96)
	private String permRole;
	/**
	 * 上级资源
	 */
	@Column(name = "PARENT_ID", length = 64)
	private String parentId;
	/**
	 * 菜单 rel内容
	 */
	@Column(name = "REL", length = 64)
	private String rel;
	/**
	 * 资源对应的角色
	 */
	@Fetch(FetchMode.SUBSELECT)
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

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public Set<AuthRole> getAuthRoleSet() {
		return authRoleSet;
	}

	public void setAuthRoleSet(Set<AuthRole> authRoleSet) {
		this.authRoleSet = authRoleSet;
	}

}
