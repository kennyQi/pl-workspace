/**
 * @RuleQo.java Create on 2016-5-23上午11:29:35
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.service.qo.hibernate;


import hg.demo.member.common.spi.qo.AuthRoleSQO;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;

import java.util.Date;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23上午11:29:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23上午11:29:35
 * @version：
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "authRoleDao")
public class AuthRoleQO extends BaseHibernateQO<String>{
	/**
	 * id
	 */
	@QOAttr(name = "id", type = QOAttrType.EQ)
	private String id;
	/**
	 * 角色名称
	 */
	@QOAttr(name = "roleName",type = QOAttrType.LIKE_ANYWHERE)
	private String roleName;
	/**
	 * 显示名称
	 */
	@QOAttr(name = "displayName",type = QOAttrType.LIKE_ANYWHERE)
	private String displayName;
	/**
	 * 开始时间
	 */
	@QOAttr(name = "createTime",type = QOAttrType.GE)
	private Date begincreateTime;
	/**
	 * 结束时间
	 */
	@QOAttr(name = "createTime",type = QOAttrType.LE)
	private Date endcreateTime;
	
	public static AuthRoleQO build(AuthRoleSQO sqo) {
		AuthRoleQO qo = new AuthRoleQO();
		qo.setRoleName(sqo.getRoleName());
		qo.setId(sqo.getId());
		qo.setLimit(sqo.getLimit());
		return qo;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

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

	public Date getBegincreateTime() {
		return begincreateTime;
	}

	public void setBegincreateTime(Date begincreateTime) {
		this.begincreateTime = begincreateTime;
	}

	public Date getEndcreateTime() {
		return endcreateTime;
	}

	public void setEndcreateTime(Date endcreateTime) {
		this.endcreateTime = endcreateTime;
	}
}
