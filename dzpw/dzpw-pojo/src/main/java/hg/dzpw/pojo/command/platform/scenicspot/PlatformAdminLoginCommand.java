package hg.dzpw.pojo.command.platform.scenicspot;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

/**
 * @类功能说明：管理员登录
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014年11月24日上午10:19:01
 */
@SuppressWarnings("serial")
public class PlatformAdminLoginCommand extends DZPWPlatformBaseCommand {
	/**
	 * 登录账户
	 */
	private String loginName;
	/**
	 * 登录密码
	 */
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
