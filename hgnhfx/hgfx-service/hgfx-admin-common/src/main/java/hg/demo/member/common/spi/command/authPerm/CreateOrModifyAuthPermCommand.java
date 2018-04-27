/**
 * @CreateAuthPermCommand.java Create on 2016-5-24下午3:20:58
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.spi.command.authPerm;

import java.util.LinkedHashSet;
import java.util.Set;


import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.domain.model.AuthRole;
import hg.framework.common.base.BaseSPICommand;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-24下午3:20:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-24下午3:20:58
 * @version：
 */
public class CreateOrModifyAuthPermCommand extends BaseSPICommand{

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 名称
	 */
	private String displayName;
	/**
	 * url地址
	 */
	private String url;
	/**
	 * 资源类型
	 */
	private Short permType;
	/**
	 * 需要检查的角色
	 */
	private String permRole;
	/**
	 * 上级资源
	 */
	private String parentId;
	/**
	 * 资源类
	 */
	private AuthPerm perm;
	/**
	 * 菜单 rel内容
	 */
	private String rel;
	/**
	 * 资源对应的角色
	 */
	private Set<AuthRole> authRoleSet = new LinkedHashSet<AuthRole>();

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

	public AuthPerm getPerm() {
		return perm;
	}

	public void setPerm(AuthPerm perm) {
		this.perm = perm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}
	
}
