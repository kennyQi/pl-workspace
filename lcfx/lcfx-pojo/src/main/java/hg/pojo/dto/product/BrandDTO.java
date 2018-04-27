package hg.pojo.dto.product;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BrandDTO implements Serializable{

	/**
	 * 品牌编码
	 */
	private String brandCode;
	
	/**
	 * 品牌中文名称
	 */
	private String chineseName;
	
	/**
	 * 品牌英文名称
	 */
	private String englishName;
	
	/**
	 * 品牌简介
	 */
	private String  remark;

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
