package hsl.api.v1.response.jp;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.jp.CityAirCodeDTO;

import java.util.List;

public class JPCityAirCodeResponse extends ApiResponse {
	private List<CityAirCodeDTO> cityAirCodeList;

	public List<CityAirCodeDTO> getCityAirCodeList() {
		return cityAirCodeList;
	}

	public void setCityAirCodeList(List<CityAirCodeDTO> cityAirCodeList) {
		this.cityAirCodeList = cityAirCodeList;
	}
}
