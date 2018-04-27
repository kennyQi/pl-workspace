package hg.dzpw.pojo.api.appv1.request;

import hg.dzpw.pojo.api.appv1.base.ApiRequestBody;

/**
 * @类功能说明：登陆（用于管理员登录验票app。）
 * @类修改者：
 * @修改日期：2014-11-18下午2:43:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午2:43:06
 */
public class LoginRequestBody extends ApiRequestBody {
	private static final long serialVersionUID = 1L;

	/**
	 * 登录名称
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
		this.loginName = loginName == null ? null : loginName.trim();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}
}