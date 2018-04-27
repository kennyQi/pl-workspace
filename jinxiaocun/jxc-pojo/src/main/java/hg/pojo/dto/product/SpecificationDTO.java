package hg.pojo.dto.product;

import java.io.Serializable;


@SuppressWarnings("serial")
public class SpecificationDTO implements Serializable{

	/**
	 * 商品类别名称
	 */
	private String categoryName;

	/**
	 * 规格值
	 */
	private String specValueName;

	/**
	 * 规格名称
	 */
	private String specName;
	
	/**
	 * 是否启用
	 */
	private String using;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getUsing() {
		return using;
	}

	public void setUsing(String using) {
		this.using = using;
	}

	public String getSpecValueName() {
		return specValueName;
	}

	public void setSpecValueName(String specValueName) {
		this.specValueName = specValueName;
	}
	
}
