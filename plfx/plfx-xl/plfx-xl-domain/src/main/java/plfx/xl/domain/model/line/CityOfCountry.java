package plfx.xl.domain.model.line;

import hg.common.component.BaseModel;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = M.TABLE_PREFIX_SYS + "COUNTRY_CITY")
@SuppressWarnings("serial")
public class CityOfCountry extends BaseModel {
	/**
	 * 城市名称
	 */
	@Column(name = "NAME", length = 32)
	private String name;

	/***
	 * 城市代码
	 */
	@Column(name = "CODE", length = 8)
	private String code;

	/***
	 * 城市所在的国家代码
	 */
	@Column(name = "PARENT", length = 8)
	private String parentCode;

	/***
	 * 城市所在的国家名称
	 */
	@Column(name = "COUNTRY_NAME", length = 32)
	private String countryName;
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	
}
