package hg.fx.command.distributor;


import hg.framework.common.base.BaseSPICommand;

import java.util.List;
/**
 * 
 * 修改商户状态命令
 * @date 2016-6-16上午10:27:07
 * @since
 */
@SuppressWarnings("serial")
public class ChangeStatusDistributorCommand extends BaseSPICommand {
	/**
	 * 0--禁用  
	 * 1--启用
	 */
	private Integer status;
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
