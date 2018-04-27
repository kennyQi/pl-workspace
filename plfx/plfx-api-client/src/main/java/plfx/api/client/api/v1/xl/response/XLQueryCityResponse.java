package plfx.api.client.api.v1.xl.response;

import java.util.List;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.xl.pojo.dto.CityOfCountryDTO;


/****
 * 
 * @类功能说明：城市查询RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月18日下午12:50:39
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLQueryCityResponse extends ApiResponse {
	
	/**
	 * 城市集合
	 */
	private List<CityOfCountryDTO> countryCityList;

	public List<CityOfCountryDTO> getCountryCityList() {
		return countryCityList;
	}

	public void setCountryCityList(List<CityOfCountryDTO> countryCityList) {
		this.countryCityList = countryCityList;
	}	
}
