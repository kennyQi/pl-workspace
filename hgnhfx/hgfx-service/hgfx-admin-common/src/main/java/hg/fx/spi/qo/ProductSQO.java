package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
public class ProductSQO extends BaseSPIQO {

	/**
	 * 商品ID
	 */
	private String productID;

	/**
	 * 渠道ID
	 */
	private String channelID;

	/**
	 * 商品编码
	 */
	private String prodCode;

	/**
	 * 商品名称
	 */
	private String prodName;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

}
