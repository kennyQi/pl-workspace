package hg.system.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class AreaQo extends BaseQo {

	/**
	 * 城市代码
	 */
	private String cityCode;

	/**
	 * 区县名称
	 */
	private String name;

	/**
	 * 是否对名字进行模糊查询
	 */
	private Boolean nameFuzzy = true;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getNameFuzzy() {
		return nameFuzzy;
	}

	public void setNameFuzzy(Boolean nameFuzzy) {
		this.nameFuzzy = nameFuzzy;
	}

}
