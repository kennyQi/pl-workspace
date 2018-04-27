package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;

import com.alibaba.fastjson.JSON;

/**
 * 用户登录
 * 
 * @author zhuxy
 * 
 */
@SuppressWarnings("serial")
public class LoginCommand extends ApiPayload {

	/**
	 * 用户登录名
	 */
	private String loginName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 是否查询用户信息并返回
	 */
	private boolean queryUserInfo = false ;

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

	public Boolean getQueryUserInfo() {
		return queryUserInfo;
	}

	public void setQueryUserInfo(Boolean queryUserInfo) {
		this.queryUserInfo = queryUserInfo;
	}
	
	public static void main(String[] arg){
		LoginCommand l = new LoginCommand();
		l.setLoginName("admin");
		l.setPassword("admin");
		l.setQueryUserInfo(true);
		System.out.println(JSON.toJSON(l));
	}

}
