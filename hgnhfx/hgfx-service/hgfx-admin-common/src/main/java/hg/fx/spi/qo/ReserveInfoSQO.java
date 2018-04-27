package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * 
 * @author xinglj
 *
 */
@SuppressWarnings("serial")
public class ReserveInfoSQO extends BaseSPIQO{
	private String distributorId;

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
}
