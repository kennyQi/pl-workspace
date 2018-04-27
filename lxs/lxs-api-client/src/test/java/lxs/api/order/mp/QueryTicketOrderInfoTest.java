package lxs.api.order.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.order.mp.TicketOrderInfoQO;
import lxs.api.v1.response.order.mp.TicketOrderInfoResponse;

import com.alibaba.fastjson.JSON;

public class QueryTicketOrderInfoTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		TicketOrderInfoQO ticketOrderInfoQO = new TicketOrderInfoQO();
		ticketOrderInfoQO.setOrderID("JX00060000000265");
		
		ApiRequest request = new ApiRequest("QueryTicketOrderInfo", UUID.randomUUID().toString(), ticketOrderInfoQO, "1.0");
		TicketOrderInfoResponse ticketOrderInfoResponse = client.send(request,
				TicketOrderInfoResponse.class);
		System.out.println(JSON.toJSONString(ticketOrderInfoQO));
		System.out.println(JSON.toJSONString(ticketOrderInfoResponse));
	}

}
