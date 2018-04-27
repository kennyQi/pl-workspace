package hsl.h5.base.result.jp;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import java.util.List;
import hsl.h5.base.result.api.ApiResult;

public class JPCityAirCodeResult extends ApiResult{
	private List<CityAirCodeDTO> cityAirCodeList;

	public List<CityAirCodeDTO> getCityAirCodeList() {
		return cityAirCodeList;
	}

	public void setCityAirCodeList(List<CityAirCodeDTO> cityAirCodeList) {
		this.cityAirCodeList = cityAirCodeList;
	}
}
