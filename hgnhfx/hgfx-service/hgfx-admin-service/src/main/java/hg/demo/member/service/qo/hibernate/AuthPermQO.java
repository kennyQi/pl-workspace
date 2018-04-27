/**
 * @PermQo.java Create on 2016-5-23上午11:28:58
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.service.qo.hibernate;

import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.spi.qo.AuthPermSQO;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import org.apache.commons.lang.StringUtils;

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
@QOConfig(daoBeanId = "authPermDao")
public class AuthPermQO extends BaseHibernateQO<String> {
	/**
	 * id
	 */
	@QOAttr(name = "id", type = QOAttrType.EQ)
	private String id;
	/**
	 * 名称
	 */
	@QOAttr(name = "displayName", type = QOAttrType.LIKE_ANYWHERE)
	private String displayName;
	/**
	 * url
	 */
	@QOAttr(name = "url", type = QOAttrType.LIKE_ANYWHERE)
	private String url;	
	/**
	 * 资源类型
	 */
	@QOAttr(name = "permType", type = QOAttrType.EQ)
	private Short permType;
	/**
	 * 需要检查的角色
	 */
	@QOAttr(name = "permRole", type = QOAttrType.EQ)
	private String permRole;
	/**
	 * 上级资源
	 */
	@QOAttr(name = "parentId", type = QOAttrType.EQ)
	private String parentId;

	/**
	 * 内连接查询角色
	 */
	@QOAttr(name = "authRoleSet", type = QOAttrType.LEFT_JOIN)
	private AuthRoleQO authRoleSet;
	/**
	 * 资源类
	 */
	private AuthPerm perm;
	/**
	 * id 列表
	 */
	private List<String> ids;
	/**
	 * 资源id
	 */
	private String permId;
	
	/**
	 * 内连接查询角色
	 */
	@QOAttr(name = "displayName", type = QOAttrType.ORDER)
	private Integer orderBy;
	public static AuthPermQO build(AuthPermSQO sqo) {
		AuthPermQO qo = new AuthPermQO();
		qo.setId(sqo.getId());
		qo.setIds(sqo.getIds());
		qo.setDisplayName(sqo.getDisplayName());
		qo.setPermType(sqo.getPermType());
		qo.setUrl(sqo.getUrl());
		qo.setParentId(sqo.getParentId());
		if (StringUtils.isNotBlank(sqo.getRoleId())) {
			AuthRoleQO authRoleQO = new AuthRoleQO();
			authRoleQO.setId(sqo.getRoleId());
			qo.setAuthRoleSet(authRoleQO);
		}
		//按照资源名称升序排序
		qo.setOrderBy(1);
		qo.setLimit(sqo.getLimit());
		return qo;
	}
	
	

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

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

	public AuthRoleQO getAuthRoleSet() {
		return authRoleSet;
	}

	public void setAuthRoleSet(AuthRoleQO authRoleSet) {
		this.authRoleSet = authRoleSet;
	}
}
