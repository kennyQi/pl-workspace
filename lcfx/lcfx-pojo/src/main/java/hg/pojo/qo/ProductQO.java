package hg.pojo.qo;

import java.util.ArrayList;
import java.util.List;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class ProductQO extends JxcBaseQo {
	private List<String> idList;
	private String supplierId;
	/**
	 * 单位id
	 */
	private String unitId;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品编码
	 */
	private String productCode;

	/**
	 * 商品状态
	 */
	private Boolean status;

	/**
	 * 商品属性
	 */
	private String attribute;

	/**
	 * 商品品牌id
	 */
	private String brandId;

	/**
	 * 商品类别id
	 */
	private String productCategoryId;

	private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	private String createDateStart;
	private String createDateEnd;

	/**
	 * 是否设置出库重量/体积
	 */
	private Boolean settingOutStockParam;

	/**
	 * 是否按照名字模糊查询
	 */
	private Boolean nameLike;
	/**
	 * 是否按照商品编号模糊查询
	 */
	private Boolean productCodeLike;

	public Boolean getProductCodeLike() {
		return productCodeLike;
	}

	public void setProductCodeLike(Boolean productCodeLike) {
		this.productCodeLike = productCodeLike;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Boolean getSettingOutStockParam() {
		return settingOutStockParam;
	}

	public void setSettingOutStockParam(Boolean settingOutStockParam) {
		this.settingOutStockParam = settingOutStockParam;
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public void createIdList() {
		this.idList = new ArrayList<String>();
	}

}
