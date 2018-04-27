package lxs.api.line;

import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.line.DayRouteQO;
import lxs.api.v1.response.line.QueryDayRouteResponse;

public class QueryDayRouteTest {

	public static void main(String[] args) throws ParseException {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		DayRouteQO qo = new DayRouteQO();
		qo.setLineId("b1cd8d8781794518ba463c7c0172ae72");
		ApiRequest request = new ApiRequest("QueryDayRoute", UUID.randomUUID()
				.toString(), qo, "1.0");
		QueryDayRouteResponse response = client.send(request,
				QueryDayRouteResponse.class);

		System.out.println(response.getMessage());
		System.out.println(JSON.toJSONString(qo));
		System.out.println(JSON.toJSONString(response));
	}
}
