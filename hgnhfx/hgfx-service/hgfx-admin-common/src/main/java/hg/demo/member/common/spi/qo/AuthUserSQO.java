package hg.demo.member.common.spi.qo;

import hg.framework.common.base.BaseSPIQO;

import java.util.Date;
import java.util.Set;

/**
 * Created by admin on 2016/5/20.
 */
@SuppressWarnings("serial")
public class AuthUserSQO extends BaseSPIQO {
	/**
	 * id
	 */
	private String id;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 状态
	 */
	private Short enable;

	/**
	 * 显示名称
	 */
	private String displayName;

	/**
	 * 起始时间
	 */
	private Date begincreateTime;

	/**
	 * 结束时间
	 */
	private Date endcreateTime;

	/**
	 * 角色id
	 */
	private String roleid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
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

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
}
