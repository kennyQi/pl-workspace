package hsl.pojo.qo.hotel;

import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class HotelGeoQO extends BaseQo{
	private String cityName;//城市编码
	
	private String cityCode;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	
}
