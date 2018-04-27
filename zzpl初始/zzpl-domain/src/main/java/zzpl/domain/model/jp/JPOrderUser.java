package zzpl.domain.model.jp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class JPOrderUser {

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "LOGIN_NAME")
	private String loginName;

	@Column(name = "MOBILE")
	private String mobile;

	
	////////
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
