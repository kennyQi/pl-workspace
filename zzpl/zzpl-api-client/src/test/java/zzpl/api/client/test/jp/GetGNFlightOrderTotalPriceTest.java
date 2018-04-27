package zzpl.api.client.test.jp;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.order.GetGNFlightOrderTotalPriceCommand;
import zzpl.api.client.response.order.GetGNFlightOrderTotalPriceResponse;

import com.alibaba.fastjson.JSON;

public class GetGNFlightOrderTotalPriceTest {
	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios","0ca80e796fb94d46bfd500adddd0f7de");
		GetGNFlightOrderTotalPriceCommand getGNFlightOrderTotalPriceCommand = new GetGNFlightOrderTotalPriceCommand();
		getGNFlightOrderTotalPriceCommand.setOrderID("16f9cd279f534ba692aec1d32d19a4bd");
		getGNFlightOrderTotalPriceCommand.setOrderNO("B818083651010000");
		ApiRequest request = new ApiRequest("GetGNFlightOrderTotalPrice",	"981bd6cf2c764345b9a360d5f4c8cad3", getGNFlightOrderTotalPriceCommand, "1.0");
		GetGNFlightOrderTotalPriceResponse response = new GetGNFlightOrderTotalPriceResponse();
		response = client.send(request, GetGNFlightOrderTotalPriceResponse.class);
		System.out.println(JSON.toJSON(response));
	}
}
