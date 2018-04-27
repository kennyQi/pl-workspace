package hsl.domain.model.yxjp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 易行机票订单下单用户信息
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings("serial")
public class YXJPOrderCreateUserInfo implements Serializable {

	/**
	 * 用户ID
	 */
	@Column(name = "USER_ID", length = 64)
	private String userId;

	/**
	 * 用户帐号
	 */
	@Column(name = "LOGIN_NAME", length = 64)
	private String loginName;

	/**
	 * 用户手机号
	 */
	@Column(name = "MOBILE", length = 64)
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
