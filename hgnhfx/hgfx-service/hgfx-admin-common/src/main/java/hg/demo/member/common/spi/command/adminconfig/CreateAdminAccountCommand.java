package hg.demo.member.common.spi.command.adminconfig;

import hg.framework.common.base.BaseSPICommand;


public class CreateAdminAccountCommand extends BaseSPICommand{
	private String loginName;
	private String pwd;
	private String email;
	private String realName;
	public String getLoginName() {
		return loginName;
	}                          
	public String getPwd() {
		return pwd;
	}
	public String getEmail() {
		return email;
	}
	public String getRealName() {
		return realName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	
}