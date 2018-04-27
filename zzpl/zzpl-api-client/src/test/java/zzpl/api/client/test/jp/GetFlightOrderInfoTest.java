package zzpl.api.client.test.jp;

import java.text.ParseException;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.order.FlightOrderInfoQO;
import zzpl.api.client.response.order.FlightOrderInfoResponse;

public class GetFlightOrderInfoTest {
	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios","a792ff7bf3ca4015940a3f357c6df7e0");
		FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
		flightOrderInfoQO.setOrderID("29f447cd240d4f4c9ec928d56aaa3a29");
		ApiRequest request = new ApiRequest("GetFlightOrderInfo",	"0e6c6bccf53d4b7fb4c90c68b6035c28", flightOrderInfoQO, "1.0");
		FlightOrderInfoResponse response = new FlightOrderInfoResponse();
		response = client.send(request, FlightOrderInfoResponse.class);
		System.out.println(JSON.toJSON(response));
	}
}
