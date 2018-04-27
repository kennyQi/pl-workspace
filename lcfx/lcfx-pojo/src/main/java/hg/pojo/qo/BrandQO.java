package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class BrandQO extends JxcBaseQo {

	/**
	 * 中文名称
	 */
	private String chineseName;
	
	/**
	 * 英文名称
	 */
	private String englishName;
	
	/**
	 * 是否按照中文名称模糊查询
	 */
	private Boolean chineseNameLike;
	
	/**
	 * 是否按照英文名称模糊查询
	 */
	private Boolean englishNameLike;

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

	public Boolean getChineseNameLike() {
		return chineseNameLike;
	}

	public void setChineseNameLike(Boolean chineseNameLike) {
		this.chineseNameLike = chineseNameLike;
	}

	public Boolean getEnglishNameLike() {
		return englishNameLike;
	}

	public void setEnglishNameLike(Boolean englishNameLike) {
		this.englishNameLike = englishNameLike;
	}

	
}
