package hg.pojo.dto.product;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class ProductDTO implements Serializable {

	/**
	 * 商品名称
	 */

	private String productName;

	/**
	 * 商品品牌
	 */

	private String brandName;

	/**
	 * 商品类别
	 */
	private String categoryName;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 重量
	 */
	private Double weight;

	/**
	 * 出库重量
	 */
	private Double outStockWeight;

	/**
	 * 单位
	 */
	private String unitName;

	/**
	 * 商品属性
	 */
	private String attribute;

	/**
	 * 规格值
	 */
	private String specValue;

	/**
	 * 是否启用
	 */
	private String using;

	private Double sizeWidth;
	private Double sizeHigh;
	private Double sizeLong;
	/**
	 * 出库尺寸
	 */
	private Double outstockSizeWidth;
	private Double outstockSizeHigh;
	private Double outstockSizeLong;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getOutStockWeight() {
		return outStockWeight;
	}

	public void setOutStockWeight(Double outStockWeight) {
		this.outStockWeight = outStockWeight;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getSpecValue() {
		return specValue;
	}

	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}

	public String getUsing() {
		return using;
	}

	public void setUsing(String using) {
		this.using = using;
	}

	public Double getSizeWidth() {
		return sizeWidth;
	}

	public void setSizeWidth(Double sizeWidth) {
		this.sizeWidth = sizeWidth;
	}

	public Double getSizeHigh() {
		return sizeHigh;
	}

	public void setSizeHigh(Double sizeHigh) {
		this.sizeHigh = sizeHigh;
	}

	public Double getSizeLong() {
		return sizeLong;
	}

	public void setSizeLong(Double sizeLong) {
		this.sizeLong = sizeLong;
	}

	public Double getOutstockSizeWidth() {
		return outstockSizeWidth;
	}

	public void setOutstockSizeWidth(Double outstockSizeWidth) {
		this.outstockSizeWidth = outstockSizeWidth;
	}

	public Double getOutstockSizeHigh() {
		return outstockSizeHigh;
	}

	public void setOutstockSizeHigh(Double outstockSizeHigh) {
		this.outstockSizeHigh = outstockSizeHigh;
	}

	public Double getOutstockSizeLong() {
		return outstockSizeLong;
	}

	public void setOutstockSizeLong(Double outstockSizeLong) {
		this.outstockSizeLong = outstockSizeLong;
	}

}
