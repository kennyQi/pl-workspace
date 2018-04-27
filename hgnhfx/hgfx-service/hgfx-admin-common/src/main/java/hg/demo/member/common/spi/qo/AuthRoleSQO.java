/**
 * @RuleQo.java Create on 2016-5-23上午11:29:35
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.spi.qo;


import hg.framework.common.base.BaseSPIQO;

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
public class AuthRoleSQO extends BaseSPIQO{
	/**
	 * id
	 */
	private String id;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 显示名称
	 */
	private String displayName;
	/**
	 * 开始时间
	 */
	private Date begincreateTime;
	/**
	 * 结束时间
	 */
	private Date endcreateTime;

	public String getId() {
		return id;
	}

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
