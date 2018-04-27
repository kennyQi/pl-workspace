package lxs.domain.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class UserAuthInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 登录名
	 */
	@Column(name="LOGINNAME", length = 64)
	private String loginName;
	/**
	 * 密码
	 */
	@Column(name="PASSWORD", length = 64)
	private String password;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
