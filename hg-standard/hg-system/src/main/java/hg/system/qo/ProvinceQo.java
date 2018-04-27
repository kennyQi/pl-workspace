package hg.system.qo;

import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "provinceDao")
public class ProvinceQo extends BaseQo {

	/**
	 * 省份行政编码
	 */
	private String code;

	/**
	 * 省份名字
	 */
	private String name;

	/**
	 * 是否对名字进行模糊查询
	 */
	private Boolean nameFuzzy = true;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
