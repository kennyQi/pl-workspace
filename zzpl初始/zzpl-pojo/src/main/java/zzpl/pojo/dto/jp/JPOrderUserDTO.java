package zzpl.pojo.dto.jp;

import zzpl.pojo.dto.BaseDTO;


public class JPOrderUserDTO extends BaseDTO{
	private static final long serialVersionUID = 1L;

	private String userId;

	private String loginName;

	private String mobile;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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
}
