package hg.fx.command.distributor;


import hg.framework.common.base.BaseSPICommand;

import java.util.List;
/**
 * 删除商户命令
 * @date 2016-6-16上午10:42:43
 * @since
 */
@SuppressWarnings("serial")
public class RemoveDistributorCommand extends BaseSPICommand {
	
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
