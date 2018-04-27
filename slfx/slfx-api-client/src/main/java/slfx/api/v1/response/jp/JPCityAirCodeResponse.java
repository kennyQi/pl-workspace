package slfx.api.v1.response.jp;

import java.util.List;

import slfx.api.base.ApiResponse;
import slfx.jp.pojo.dto.flight.CityAirCodeDTO;

/**
 * 
 * @类功能说明：城市机场三字码RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月4日下午3:06:25
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPCityAirCodeResponse extends ApiResponse{
	
	private List<CityAirCodeDTO> cityAirCodeList;

	public List<CityAirCodeDTO> getCityAirCodeList() {
		return cityAirCodeList;
	}

	public void setCityAirCodeList(List<CityAirCodeDTO> cityAirCodeList) {
		this.cityAirCodeList = cityAirCodeList;
	}

}
