package hg.system.qo;

import hg.common.model.qo.DwzBasePaginQo;

import java.util.Date;

/**
 * 员工查询对象
 * @author zhurz
 */
@SuppressWarnings("serial")
public class AuthStaffQo extends DwzBasePaginQo {

	/**
	 * 员工登录名
	 */
	private String loginName;
	/**
	 * 登录名是否使用模糊匹配
	 */
	private Boolean isLoginNameLike=false;
	/**
	 * 员工真实姓名
	 */
	private String realName;
	/**
	 * 真实姓名是否使用模糊匹配
	 */
	private Boolean isRealNameLike=false;
	/**
	 * 员工手机号
	 */
	private String mobile;
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
}
