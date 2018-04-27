package lxs.api.order.line;

import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.order.line.LineOrderQO;
import lxs.api.v1.response.order.line.QueryLineOrderResponse;

public class QueryLineOrderTest {
	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		LineOrderQO lineOrderQO = new LineOrderQO();
//		lineOrderQO.setPageNO("1");
//		lineOrderQO.setPageSize("20");
//		lineOrderQO.setUserId("d55e4e1c299646ab922a02b8dcd05ffa");
//		lineOrderQO.setPayType("2");

		lineOrderQO.setLineOrderID("60263c8c1ede46d4b9323b412173276d");
		System.out.println(JSON.toJSONString(lineOrderQO));
		ApiRequest request = new ApiRequest("QueryLineOrder", UUID.randomUUID()
				.toString(), lineOrderQO, "1.0");

		QueryLineOrderResponse response = client.send(request,
				QueryLineOrderResponse.class);

		System.out.println(JSON.toJSONString(response));
	}
}
