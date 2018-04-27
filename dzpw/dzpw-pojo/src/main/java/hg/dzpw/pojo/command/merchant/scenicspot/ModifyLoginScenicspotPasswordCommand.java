package hg.dzpw.pojo.command.merchant.scenicspot;

import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;

/**
 * @类功能说明：修改登录景区的密码
 * @类修改者：
 * @修改日期：2015-2-11下午3:18:26
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-11下午3:18:26
 */
@SuppressWarnings("serial")
public class ModifyLoginScenicspotPasswordCommand extends DZPWMerchantBaseCommand {

	/**
	 * 景区ID
	 */
	private String scenicspotId;
	
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

	public String getScenicspotId() {
		return scenicspotId;
	}

	public void setScenicspotId(String scenicspotId) {
		this.scenicspotId = scenicspotId;
	}

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

}
