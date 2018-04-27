package lxs.api.order.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.order.mp.CloseTicketOrderCommand;
import lxs.api.v1.response.order.mp.CloseTicketOrderResponse;

import com.alibaba.fastjson.JSON;

public class CloseTicketOrderTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		CloseTicketOrderCommand closeTicketOrderCommand = new CloseTicketOrderCommand();
		closeTicketOrderCommand.setOrderID("");
		closeTicketOrderCommand.setOrderNO("");
		
		ApiRequest request = new ApiRequest("CloseTicketOrder", UUID.randomUUID().toString(), closeTicketOrderCommand, "1.0");
		CloseTicketOrderResponse closeTicketOrderResponse = client.send(request,
				CloseTicketOrderResponse.class);
		System.out.println(JSON.toJSONString(closeTicketOrderCommand));
		System.out.println(JSON.toJSONString(closeTicketOrderResponse));
	}

}
