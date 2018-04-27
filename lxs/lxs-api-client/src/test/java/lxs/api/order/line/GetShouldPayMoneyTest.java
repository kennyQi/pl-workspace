package lxs.api.order.line;

import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.order.line.GetShouldPayMoneyCommand;
import lxs.api.v1.response.order.line.GetShouldPayMoneyResponse;

public class GetShouldPayMoneyTest {
	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:8080/lxs-api/api", "android", "123");
		GetShouldPayMoneyCommand command = new GetShouldPayMoneyCommand();
		command.setOrderNO("B504135113110000");
													
		
		ApiRequest request = new ApiRequest("GetShouldPayMoney", UUID.randomUUID()
				.toString(), command, "1.0");
		
		GetShouldPayMoneyResponse response = client.send(request,GetShouldPayMoneyResponse.class);

		System.out.println(response.getMessage());
	}
}
