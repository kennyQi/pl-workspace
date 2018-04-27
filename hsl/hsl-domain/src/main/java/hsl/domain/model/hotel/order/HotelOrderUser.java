package hsl.domain.model.hotel.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HotelOrderUser implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单用户ID
	 */
	@Column(name = "USER_ID",length = 64)
	private String userId;
	/**
	 * 订单用户名
	 */
	@Column(name = "LOGIN_NAME",length = 64)
	private String loginName;
	/**
	 * 订单用户手机号
	 */
	@Column(name = "MOBILE",length = 64)
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
