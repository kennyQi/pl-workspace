package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

import java.util.Date;

@SuppressWarnings("serial")
public class FixedPriceIntervalSQO extends BaseSPIQO {

	/**
	 * 区间关联的商品
	 * */
	private String productID;

	/**
	 * 创建时间
	 */
	private int createDate;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public int getCreateDate() {
		return createDate;
	}

	public void setCreateDate(int createDate) {
		this.createDate = createDate;
	}

}
