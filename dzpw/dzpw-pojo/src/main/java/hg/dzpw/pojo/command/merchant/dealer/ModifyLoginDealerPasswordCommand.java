package hg.dzpw.pojo.command.merchant.dealer;

import hg.dzpw.pojo.common.BaseCommand;

/**
 * @类功能说明：修改经销商登录密码
 * @类修改者：
 * @修改日期：2015-2-11下午3:18:26
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：guotx
 * @创建时间：2015-12-7下午3:55:26
 */
public class ModifyLoginDealerPasswordCommand extends BaseCommand {

	private static final long serialVersionUID = 1L;
	/**
	 * 经销商ID
	 */
	private String dealerId;

	/**
	 * 旧密码
	 */
	private String oldpwd;

	/**
	 * 新密码
	 */
	private String newPwd;

	/**
	 * 重复新密码
	 */
	private String reNewPwd;

	public String getOldpwd() {
		return oldpwd;
	}

	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getReNewPwd() {
		return reNewPwd;
	}

	public void setReNewPwd(String reNewPwd) {
		this.reNewPwd = reNewPwd;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

}
