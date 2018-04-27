package hsl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HslUserQO extends BaseQo {
	
	/**
	 * 按商城用户名查询
	 */
	private String loginName;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 来源
	 */
	private String source;

	/**
	 * 起始时间
	 */
	private String beginTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 是否支持登录名模糊查询
	 */
	private Boolean loginNameLike;
	
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 用户邮箱
	 */
	private String email;
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Boolean getLoginNameLike() {
		return loginNameLike;
	}

	public void setLoginNameLike(Boolean loginNameLike) {
		this.loginNameLike = loginNameLike;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
