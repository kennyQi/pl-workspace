package zzpl.api.client.response.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.order.FlightOrderList;

public class FlightOrderListResponse extends ApiResponse {
	private Integer isTheLastPage;

	private List<FlightOrderList> flightOrderLists;

	public Integer getIsTheLastPage() {
		return isTheLastPage;
	}

	public void setIsTheLastPage(Integer isTheLastPage) {
		this.isTheLastPage = isTheLastPage;
	}

	public List<FlightOrderList> getFlightOrderLists() {
		return flightOrderLists;
	}

	public void setFlightOrderLists(List<FlightOrderList> flightOrderLists) {
		this.flightOrderLists = flightOrderLists;
	}

	public static void main(String[] args) {
		FlightOrderListResponse flightOrderListResponse = new FlightOrderListResponse();
		flightOrderListResponse.setIsTheLastPage(1);
		
		 List<FlightOrderList> flightOrderLists = new ArrayList<FlightOrderList>();
		 
		FlightOrderList flightOrderList = new FlightOrderList();
		
		flightOrderList.setAirComp("深证航空");
		flightOrderList.setArrivalAirport("宝安国际机场");
		flightOrderList.setArrivalTerm("T1");
		flightOrderList.setDepartAirport("太平国际机场");
		flightOrderList.setDepartTerm("T2");
		flightOrderList.setEndTime(new Date());
		flightOrderList.setFlightNO("MU5307");
		flightOrderList.setPlaneType("AirBus320");
		flightOrderList.setStartTime(new Date());
		flightOrderList.setOrderID("asdasdasdsad");
		flightOrderList.setOrderNO("asdasdasdsadasd");
		flightOrderList.setAirComp("深圳航空");
		flightOrderList.setStatus(1);
		
		flightOrderLists.add(flightOrderList);
		flightOrderLists.add(flightOrderList);
		flightOrderLists.add(flightOrderList);
		
		flightOrderListResponse.setFlightOrderLists(flightOrderLists);
		System.out.println(JSON.toJSON(flightOrderListResponse));
		
		
	}
}
