package hg.system.qo;

import hg.common.component.BaseQo;

import java.util.Date;

/**
 * 员工查询对象
 * 
 * @author zhurz
 */
@SuppressWarnings("serial")
public class StaffQo extends BaseQo {

	/**
	 * 员工登录名
	 */
	private String loginName;
	/**
	 * 是否查询角色
	 */
	private boolean queryAuthRole;
	/**
	 * 登录名是否使用模糊匹配
	 */
	private Boolean isLoginNameLike = false;
	/**
	 * 员工真实姓名
	 */
	private String realName;
	/**
	 * 真实姓名是否使用模糊匹配
	 */
	private Boolean isRealNameLike = false;
	/**
	 * 员工手机号
	 */
	private String mobile;
	private Boolean isMobileLike = false;
	/**
	 * 注册时期区间（开始）
	 */
	private Date createDateBegin;
	/**
	 * 注册日期区间（结束）
	 */
	private Date createDateEnd;

	/**
	 * 是否可用
	 */
	private Short enable;

	/**
	 * 角色ID
	 */
	private String roleId;

	/**
	 * 电子邮箱
	 */
	private String email;
	private boolean emailLike;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Boolean getIsLoginNameLike() {
		return isLoginNameLike;
	}

	public void setIsLoginNameLike(Boolean isLoginNameLike) {
		this.isLoginNameLike = isLoginNameLike;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Boolean getIsRealNameLike() {
		return isRealNameLike;
	}

	public void setIsRealNameLike(Boolean isRealNameLike) {
		this.isRealNameLike = isRealNameLike;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailLike() {
		return emailLike;
	}

	public void setEmailLike(boolean emailLike) {
		this.emailLike = emailLike;
	}

	public Boolean getIsMobileLike() {
		return isMobileLike;
	}

	public void setIsMobileLike(Boolean isMobileLike) {
		this.isMobileLike = isMobileLike;
	}

	public boolean isQueryAuthRole() {
		return queryAuthRole;
	}

	public void setQueryAuthRole(boolean queryAuthRole) {
		this.queryAuthRole = queryAuthRole;
	}

}
