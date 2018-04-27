package hg.system.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class CityQo extends BaseQo {

	/**
	 * 省份代码
	 */
	private String provinceCode;

	/**
	 * 城市名称
	 */
	private String name;

	/**
	 * 是否对名字进行模糊查询
	 */
	private Boolean nameFuzzy = true;

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode == null ? null : provinceCode.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Boolean getNameFuzzy() {
		return nameFuzzy;
	}

	public void setNameFuzzy(Boolean nameFuzzy) {
		this.nameFuzzy = nameFuzzy;
	}

}
