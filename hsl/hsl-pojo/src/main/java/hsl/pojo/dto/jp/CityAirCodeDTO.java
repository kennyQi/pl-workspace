package hsl.pojo.dto.jp;

public class CityAirCodeDTO {
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
