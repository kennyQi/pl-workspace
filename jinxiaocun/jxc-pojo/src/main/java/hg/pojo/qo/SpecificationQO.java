package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class SpecificationQO extends JxcBaseQo {

	/**
	 * 规格名称
	 */
	private String specName;
	
	/**
	 * 商品分类id
	 */
	private String productCategoryId;
	/**
	 * 分类名
	 */
	private String categoryName;
	/**
	 * 是否按照规格名称模糊查询
	 */
	private Boolean specNameLike;
	/**
	 * 判断是否启用
	 */
	private Boolean using;
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Boolean getSpecNameLike() {
		return specNameLike;
	}

	public void setSpecNameLike(Boolean specNameLike) {
		this.specNameLike = specNameLike;
	}

}
