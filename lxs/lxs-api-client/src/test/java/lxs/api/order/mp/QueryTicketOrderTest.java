package lxs.api.order.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.order.mp.TicketOrderQO;
import lxs.api.v1.response.order.mp.TicketOrderResponse;

import com.alibaba.fastjson.JSON;

public class QueryTicketOrderTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		TicketOrderQO ticketOrderQO = new TicketOrderQO();
		ticketOrderQO.setPageNO("1");
		ticketOrderQO.setPageSize("10");
		ticketOrderQO.setUserID("0bc0caf81ea6423dbfb5aa953f35e32b");
		
		ApiRequest request = new ApiRequest("QueryTicketOrder", UUID.randomUUID().toString(), ticketOrderQO, "1.0");
		TicketOrderResponse ticketOrderResponse = client.send(request,
				TicketOrderResponse.class);
		System.out.println(JSON.toJSONString(ticketOrderQO));
		System.out.println(JSON.toJSONString(ticketOrderResponse));
	}

}
