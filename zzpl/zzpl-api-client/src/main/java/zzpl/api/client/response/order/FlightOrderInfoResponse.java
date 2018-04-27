package zzpl.api.client.response.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.jp.GNPassengerDTO;
import zzpl.api.client.dto.order.FlightOrderInfo;

public class FlightOrderInfoResponse extends ApiResponse{
	private FlightOrderInfo flightOrderInfo;

	public FlightOrderInfo getFlightOrderInfo() {
		return flightOrderInfo;
	}

	public void setFlightOrderInfo(FlightOrderInfo flightOrderInfo) {
		this.flightOrderInfo = flightOrderInfo;
	}

	public static void main(String[] args) {
		FlightOrderInfoResponse flightOrderInfoResponse = new FlightOrderInfoResponse();
		FlightOrderInfo flightOrderInfo = new FlightOrderInfo();
		
		flightOrderInfo.setAirComp("深证航空");
		flightOrderInfo.setArrivalAirport("宝安国际机场");
		flightOrderInfo.setDepartAirport("太平国际机场");
		flightOrderInfo.setEndTime(new Date());
		flightOrderInfo.setFlightNO("MU5307");
		flightOrderInfo.setStartTime(new Date());
		flightOrderInfo.setOrderNO("asdasdasdsadasd");
		flightOrderInfo.setAirId("12312321321321");
		flightOrderInfo.setAirComp("深圳航空");
		flightOrderInfo.setStatus(1);
		flightOrderInfo.setBuildFee(150.00);
		flightOrderInfo.setOilFee(150.00);
		flightOrderInfo.setTotalPrice(1888.00);
		flightOrderInfo.setDstCity("深圳");
		flightOrderInfo.setOrgCity("哈尔滨");
		
		GNPassengerDTO gnPassengerDTO = new GNPassengerDTO();
		gnPassengerDTO.setIdNO("233333198801011111");
		gnPassengerDTO.setIdType("0");
		gnPassengerDTO.setTelephone("18888888888");
		gnPassengerDTO.setPassengerName("乘机人");
		List<GNPassengerDTO> passengerList = new ArrayList<GNPassengerDTO>();
		passengerList.add(gnPassengerDTO);
//		flightOrderInfo.setPassengerList(passengerList);
		
		flightOrderInfo.setLinkName("link");
		flightOrderInfo.setLinkEmail("link@zzpl.com");
		flightOrderInfo.setLinkTelephone("18888888888");
		
		flightOrderInfoResponse.setFlightOrderInfo(flightOrderInfo);
		
		System.out.println(JSON.toJSON(flightOrderInfoResponse));
	}
}
