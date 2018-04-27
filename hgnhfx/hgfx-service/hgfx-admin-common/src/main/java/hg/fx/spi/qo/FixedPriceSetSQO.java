package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

@SuppressWarnings("serial")
public class FixedPriceSetSQO extends BaseSPIQO {

	/**
	 * 区间ID
	 */
	private String fixedPriceIntervalID;
	
	/**
	 * 以下参数针对列表展示页
	 */
	/**
	 * 生效时间 YYYYMM 必传
	 */
	private int implementDate;

	/**
	 * 商品ID 必传
	 */
	private String productID;

	/**
	 * 商户ID 非必传
	 */
	private String distributorID;

	public int getImplementDate() {
		return implementDate;
	}

	public void setImplementDate(int implementDate) {
		this.implementDate = implementDate;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}

	public String getFixedPriceIntervalID() {
		return fixedPriceIntervalID;
	}

	public void setFixedPriceIntervalID(String fixedPriceIntervalID) {
		this.fixedPriceIntervalID = fixedPriceIntervalID;
	}
	
	
}
