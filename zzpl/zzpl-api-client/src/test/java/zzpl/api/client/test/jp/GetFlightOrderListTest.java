package zzpl.api.client.test.jp;

import java.text.ParseException;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.order.FlightOrderListQO;
import zzpl.api.client.response.order.FlightOrderListResponse;

public class GetFlightOrderListTest {
	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:8888/zzpl-api/api", "ios","523110ec93064ee1b7c6fc3c5696c254");
		FlightOrderListQO flightOrderListQO = new FlightOrderListQO();
		flightOrderListQO.setPageNO(1);
		flightOrderListQO.setPageSize(5);
//		flightOrderListQO.setStatus(100);
		flightOrderListQO.setUserID("eff08e2c007c44a8867ed0e08c0b9b37");
		ApiRequest request = new ApiRequest("GetFlightOrderList",	"ab474aad42fd4ed0bfa93fde07308192", flightOrderListQO, "1.0");
		FlightOrderListResponse response = new FlightOrderListResponse();
		response = client.send(request, FlightOrderListResponse.class);
		System.out.println(JSON.toJSON(response));
	}
}
