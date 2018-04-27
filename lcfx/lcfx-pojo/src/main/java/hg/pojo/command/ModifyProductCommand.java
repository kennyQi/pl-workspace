package hg.pojo.command;

import java.util.List;

/**
 * 修改商品
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
public class ModifyProductCommand extends JxcCommand {
	private List<String> supplierList;

	/**
	 * 商品id
	 */
	private String productId;
	/**
	 * 商品编码
	 */
	private String productCode;

	/**
	 * 商品名称
	 */

	private String productName;

	/**
	 * 商品品牌
	 */

	private String brandId;

	/**
	 * 商品类别
	 */
	private String categoryId;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 商品描述
	 */
	private String productDescribe;

	/**
	 * 尺寸
	 */
	private String size;

	/**
	 * 重量
	 */
	private Double weight;

	/**
	 * 出库尺寸
	 */
	private String outStockSize;

	/**
	 * 出库重量
	 */
	private Double outStockWeight;

	/**
	 * 单位
	 */
	private String unitId;

	/**
	 * 商品属性
	 */
	private Integer attribute;

	/**
	 * 是否启用
	 */
	private Boolean using;

	/**
	 * 是否已设置出库重量/体积
	 */
	private Boolean settingOutStockParam;

	/**
	 * 尺寸
	 */
	private Double sizeWidth;
	private Double sizeHigh;
	private Double sizeLong;
	/**
	 * 出库尺寸
	 */
	private Double outstockSizeWidth;
	private Double outstockSizeHigh;
	private Double outstockSizeLong;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProductDescribe() {
		return productDescribe;
	}

	public void setProductDescribe(String productDescribe) {
		this.productDescribe = productDescribe;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getOutStockSize() {
		return outStockSize;
	}

	public void setOutStockSize(String outStockSize) {
		this.outStockSize = outStockSize;
	}

	public Double getOutStockWeight() {
		return outStockWeight;
	}

	public void setOutStockWeight(Double outStockWeight) {
		this.outStockWeight = outStockWeight;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}

	public Boolean getSettingOutStockParam() {
		return settingOutStockParam;
	}

	public void setSettingOutStockParam(Boolean settingOutStockParam) {
		this.settingOutStockParam = settingOutStockParam;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public List<String> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<String> supplierList) {
		this.supplierList = supplierList;
	}
}
