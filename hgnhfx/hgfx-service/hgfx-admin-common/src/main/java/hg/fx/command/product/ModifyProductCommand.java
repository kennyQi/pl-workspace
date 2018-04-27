package hg.fx.command.product;

import hg.framework.common.base.BaseSPICommand;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
public class ModifyProductCommand extends BaseSPICommand {

	/** 1--接口文档 */
	public static final Integer PRODUCT_TYPE_DOCUMENT = 1;

	/**
	 * 商品ID
	 */
	private String productID;

	/**
	 * 商品编码
	 */
	private String prodCode;

	/**
	 * 商品名称
	 */
	private String prodName;

	/**
	 * 商品名称 1--接口文档
	 */
	private Integer type;

	/**
	 * 文档路径
	 */
	private String documentPath;

	/**
	 * 协议路径
	 */
	private String agreementPath;

	/**
	 * 商品所属渠道
	 */
	private String channelID;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getAgreementPath() {
		return agreementPath;
	}

	public void setAgreementPath(String agreementPath) {
		this.agreementPath = agreementPath;
	}

	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
}
