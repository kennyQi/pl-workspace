package hg.pojo.dto.product;

import hg.pojo.dto.BaseDTO;

public class SkuProductDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商品编码
	 */
	private String productCode;
	/**
	 * 商品编码
	 */
	private String productName;

	/**
	 * sku 编码
	 */
	private String skuCode;

	/**
	 * 规格
	 */
	private String specificationsName;

	/**
	 * 单位
	 */
	private String unit;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSpecificationsName() {
		return specificationsName;
	}

	public void setSpecificationsName(String specificationsName) {
		this.specificationsName = specificationsName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
