package hg.fx.command.productInUse;

import hg.framework.common.base.BaseSPICommand;

@SuppressWarnings("serial")
public class ModifyProductInUseCommand extends BaseSPICommand{

	private String productInUseId;
	
	/**
	 * 状态
	 */
	private Integer status;

	public String getProductInUseId() {
		return productInUseId;
	}

	public void setProductInUseId(String productInUseId) {
		this.productInUseId = productInUseId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
