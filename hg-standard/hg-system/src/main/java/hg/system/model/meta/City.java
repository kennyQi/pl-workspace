package hg.system.model.meta;

import hg.common.component.BaseModel;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = M.TABLE_PREFIX_SYS + "CITY")
public class City extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 32)
	private String name;

	@Column(name = "CODE", length = 8)
	private String code;

	@Column(name = "PARENT", length = 8)
	private String parentCode;

	/**
	 * 机场三字码
	 */
	@Column(name = "AIR_CODE", length = 8)
	private String airCode;

	/**
	 * 城市机场三字码
	 */
	@Column(name = "CITY_AIR_CODE", length = 8)
	private String cityAirCode;
	
	/**
	 * 城市简拼
	 */
	@Column(name = "CITY_JIAN_PIN", length = 8)
	private String cityJianPin;
	
	/**
	 * 城市拼音首字母排序
	 */
	@Column(name = "SORT")
	private Integer sort;
	
	/**
	 * 城市全拼
	 */
	@Column(name = "CITY_QUAN_PIN", length = 64)
	private String cityQuanPin;

	public City() {
	}

	public City(String code, String name, String parentCode) {
		this.code = code;
		this.name = name;
		this.parentCode = parentCode;
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

	public String getAirCode() {
		return airCode;
	}

	public void setAirCode(String airCode) {
		this.airCode = airCode;
	}

	public String getCityAirCode() {
		return cityAirCode;
	}

	public void setCityAirCode(String cityAirCode) {
		this.cityAirCode = cityAirCode;
	}

	public String getCityJianPin() {
		return cityJianPin;
	}

	public void setCityJianPin(String cityJianPin) {
		this.cityJianPin = cityJianPin;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCityQuanPin() {
		return cityQuanPin;
	}

	public void setCityQuanPin(String cityQuanPin) {
		this.cityQuanPin = cityQuanPin;
	}

}
