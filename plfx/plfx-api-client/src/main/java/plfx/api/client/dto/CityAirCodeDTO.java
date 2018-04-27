package plfx.api.client.dto;

import plfx.api.client.common.BaseDTO;

/**
 * 
 * @类功能说明：城市机场三字码DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月4日下午2:31:08
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class CityAirCodeDTO extends BaseDTO {

	/** 城市名称 */
	private String cityName;

	/** 城市机场三字码 */
	private String cityAirCode;

	/**
	 * 城市简拼
	 */
	private String cityJianPin;

	/**
	 * 城市拼音首字母排序
	 */
	private Integer sort;

	/**
	 * 城市全拼
	 */
	private String cityQuanPin;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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
