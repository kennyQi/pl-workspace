package hg.demo.member.common.spi.command.userinfo;

import hg.framework.common.base.BaseSPICommand;

/**
 * 
* <p>Title: SaveUserInfoCommand</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月28日 上午9:11:18
 */
@SuppressWarnings("serial")
public class ModifyPasswordCommand extends BaseSPICommand {

	/**
	 * 用户ID
	 */
	private String id;
	
	/**
	 * 原始密碼
	 */
	private String orginalPass;

	/**
	 * 
	 */
	private String newPass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrginalPass() {
		return orginalPass;
	}

	public void setOrginalPass(String orginalPass) {
		this.orginalPass = orginalPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
}
