package hg.fx.command.distributoruser;

import hg.framework.common.base.BaseSPICommand;

/**
 * 修改
 * @author Caihuan
 * @date   2016年6月1日
 */
@SuppressWarnings("serial")
public class ModifyDistributorUserCommand extends BaseSPICommand{
	
	private String id;
	
	/**
	 * 密码
	 */
	private String password;
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
