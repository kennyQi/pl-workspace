package hg.dzpw.pojo.command.merchant.scenicspot;

import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;

/**
 * @类功能说明：商户管理员登录
 * @类修改者：
 * @修改日期：2015-2-11上午10:09:38
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-11上午10:09:38
 */
@SuppressWarnings("serial")
public class MerchantLoginCommand extends DZPWMerchantBaseCommand {
	
	/**
	 * 登录账户
	 */
	private String loginName;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 验证码
	 */
	private String validcode;

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

	public String getValidcode() {
		return validcode;
	}

	public void setValidcode(String validcode) {
		this.validcode = validcode;
	}

}
