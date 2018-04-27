package lxs.api.order.line;

import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.order.line.UpdateLineOrderStatusCommand;
import lxs.api.v1.response.order.line.UpdateLineOrderStatusResponse;

public class UpdateLineOrderStatusTest {
	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:8080/lxs-api/api", "android", "123");
		UpdateLineOrderStatusCommand command = new UpdateLineOrderStatusCommand();
		command.setOrderNO("B504135113110000");
		command.setOrderID("eb49a184196c4cbf81e5374e36f18ef8");
		command.setPayType("1");
		
		ApiRequest request = new ApiRequest("UpdateLineOrderStatus", UUID.randomUUID()
				.toString(), command, "1.0");
		
		UpdateLineOrderStatusResponse response = client.send(request,UpdateLineOrderStatusResponse.class);

		System.out.println(response.getMessage());
	}
}
