package zzpl.api.client.response.jp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.jp.GNFlightListDTO;

import com.alibaba.fastjson.JSON;

/***
 * 
 * @类功能说明：查询航班RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月23日上午10:36:35
 * @版本：V1.0
 * 
 */
public class GNFlightResponse extends ApiResponse {

	/**
	 * 航班列表
	 */
	private List<GNFlightListDTO> flightGNListDTOs;

	public List<GNFlightListDTO> getFlightGNListDTOs() {
		return flightGNListDTOs;
	}

	public void setFlightGNListDTOs(List<GNFlightListDTO> flightGNListDTOs) {
		this.flightGNListDTOs = flightGNListDTOs;
	}

	public static void main(String[] args) {
		List<GNFlightListDTO> flightGNListDTOs = new ArrayList<GNFlightListDTO>();
		GNFlightListDTO gnFlightListDTO = new GNFlightListDTO();
		gnFlightListDTO.setAirComp("深证航空");
		gnFlightListDTO.setArrivalAirport("宝安国际机场");
		gnFlightListDTO.setArrivalTerm("T1");
		gnFlightListDTO.setBuildFee(150.00);
		gnFlightListDTO.setCabinDiscount("100");
		gnFlightListDTO.setCabinName("商务舱");
		gnFlightListDTO.setDepartAirport("太平国际机场");
		gnFlightListDTO.setDepartTerm("T2");
		gnFlightListDTO.setDstCity("哈尔滨");
		gnFlightListDTO.setEndTime(new Date());
		gnFlightListDTO.setFlightID("1");
		gnFlightListDTO.setFlightNO("MU5307");
		gnFlightListDTO.setIbePrice("1888");
		gnFlightListDTO.setOilFee(50.00);
		gnFlightListDTO.setOrgCity("深圳");
		gnFlightListDTO.setPlaneType("AirBus320");
		gnFlightListDTO.setStartTime(new Date());
		flightGNListDTOs.add(gnFlightListDTO);
		flightGNListDTOs.add(gnFlightListDTO);
		GNFlightResponse gnFlightResponse = new GNFlightResponse();
		gnFlightResponse.setFlightGNListDTOs(flightGNListDTOs);
		System.out.println(JSON.toJSON(gnFlightResponse));
		
	}
}
