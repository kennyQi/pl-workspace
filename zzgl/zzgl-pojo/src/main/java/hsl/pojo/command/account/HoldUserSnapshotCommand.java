package hsl.pojo.command.account;


import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class HoldUserSnapshotCommand extends BaseCommand{
	/**
	 * 用户标识
	 */
	private String userId;
	/**
	 * 用户登录名
	 */
	private String loginName;
	/**
	 * 用户真实姓名
	 */
	private String realName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
