package lxs.pojo.command.user;

import hg.common.component.BaseCommand;

/**
 * 用户登录
 * 
 * @author zhuxy
 * 
 */
@SuppressWarnings("serial")
public class LoginCommand extends BaseCommand {
	
	/**
	 * 用户登录名
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
