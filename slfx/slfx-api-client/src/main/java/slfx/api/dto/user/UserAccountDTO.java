package slfx.api.dto.user;
/**
 * 商城用户帐号信息
 * @author zhangqy
 *
 */
public class UserAccountDTO {
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 登录名
	 */
	private String LoginName;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 用户手机号
	 */
	private String mobile;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
