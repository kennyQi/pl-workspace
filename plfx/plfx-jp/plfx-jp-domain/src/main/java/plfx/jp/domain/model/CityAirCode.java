package plfx.jp.domain.model;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @类功能说明：城市编码model
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月26日下午1:39:55
 * @版本：V1.0
 */
@Entity
@Table(name = "SYS_CITY")
@SuppressWarnings("serial")
public class CityAirCode extends BaseModel {

	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 32)
	private String name;

	/**
	 * 代码
	 */
	@Column(name = "CODE", length = 8)
	private String code;

	/**
	 * 省代码
	 */
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

	/**
	 * 是否为国内机场
	 */
	@Type(type = "yes_no")
	@Column(name = "DOMESTIC")
	private Boolean domestic;

	public CityAirCode() {
	}

	public CityAirCode(String code, String name, String parentCode) {
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

	public Boolean getDomestic() {
		return domestic;
	}

	public void setDomestic(Boolean domestic) {
		this.domestic = domestic;
	}

}
