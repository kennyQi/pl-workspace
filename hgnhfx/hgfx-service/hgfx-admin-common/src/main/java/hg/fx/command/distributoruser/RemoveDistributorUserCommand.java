package hg.fx.command.distributoruser;

import hg.framework.common.base.BaseSPICommand;

/**
 * 逻辑删除商户账户
 * @author Caihuan
 * @date   2016年6月2日
 */
@SuppressWarnings("serial")
public class RemoveDistributorUserCommand extends BaseSPICommand{

	//商户账户id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
