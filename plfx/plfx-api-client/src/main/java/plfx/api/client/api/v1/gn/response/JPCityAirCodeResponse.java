package plfx.api.client.api.v1.gn.response;

import java.util.List;

import plfx.api.client.common.ApiResponse;
import plfx.api.client.dto.CityAirCodeDTO;


/****
 * 
 * @类功能说明：城市机场三字码RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午3:06:33
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
