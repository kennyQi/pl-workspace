/**
 * @PermQo.java Create on 2016-5-23上午11:28:58
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.spi.qo;

import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.domain.model.AuthRole;
import hg.framework.common.base.BaseSPIQO;


import java.util.List;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23上午11:28:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23上午11:28:58
 * @version：
 */
@SuppressWarnings("serial")
public class AuthPermSQO extends BaseSPIQO {
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
	 * 资源对象
	 */
	private AuthPerm perm;
	/**
	 * id 列表
	 */
	private List<String> ids;
	/**
	 * 权限id
	 */
	private String permId;

	/**
	 * 角色id
	 */
	private String roleId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public Short getPermType() {
		return permType;
	}
	public void setPermType(Short permType) {
		this.permType = permType;
	}
	public AuthPerm getPerm() {
		return perm;
	}
	public void setPerm(AuthPerm perm) {
		this.perm = perm;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPermId() {
		return permId;
	}
	public void setPermId(String permId) {
		this.permId = permId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
