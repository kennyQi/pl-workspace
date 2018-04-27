package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * 用户使用商品qo
 * @author Caihuan
 * @date   2016年6月1日
 */
@SuppressWarnings("serial")
public class ProductInUseSQO extends BaseSPIQO{

	/**
	 * 商户id
	 */
	private String distributorId;
	
	/**
	 * 商品使用状态
	 * 0--试用中  
	 * 1--申请中  
	 * 2--使用中  
	 * 3--已拒绝  
	 * 4--停用中
	 */
	private Integer status;
	
	private Integer[] statusArray;

	public Integer[] getStatusArray() {
		return statusArray;
	}

	public void setStatusArray(Integer[] statusArray) {
		this.statusArray = statusArray;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
