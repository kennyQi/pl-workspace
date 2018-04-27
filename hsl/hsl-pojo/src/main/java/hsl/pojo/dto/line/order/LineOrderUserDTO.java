package hsl.pojo.dto.line.order;


public class LineOrderUserDTO {
	/**
	 * 订单用户ID
	 */
	private String userId;
	/**
	 * 订单用户名
	 */
	private String loginName;
	/**
	 * 订单用户手机号
	 */
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
