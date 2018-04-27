/**
 * @Auth.java Create on 2016-5-19下午3:32:39
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.domain.model;


import hg.framework.common.base.BaseStringIdModel;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @类功能说明：用户角色表
 * @类修改者：
 * @修改日期：2016-5-19下午3:32:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-19下午3:32:39
 * @version：
 */
@Entity
@Table(name = "AUTH_ROLE")
public class AuthRole extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色名称
	 */
	@Column(name = "ROLE_NAME", length = 64)
	private String roleName;
	/**
	 * 名称
	 */
	@Column(name = "DISPLAY_NAME", length = 96)
	private String displayName;


	
	/**
	 * 角色与资源
	 */
	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "AUTH_ROLE_PERM", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERM_ID") })
	private Set<AuthPerm> authPermSet = new LinkedHashSet<AuthPerm>();

	/**
	 * 角色与用户关联
	 */
	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "AUTH_USER_ROLE", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	private Set<AuthUser> authUserSet = new LinkedHashSet<AuthUser>();

	/**
	 * 创建时间
	 */
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
