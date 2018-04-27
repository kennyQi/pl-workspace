package lxs.api.order.line;

import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.order.line.PayBargainMoneyCommand;
import lxs.api.v1.response.order.line.PayBargainMoneyResponse;

public class PayBargainMoneyTest {
	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(	"http://115.238.43.242:60000/lxs-api/api", "ios", "ios");
		PayBargainMoneyCommand payBargainMoneyCommand = new PayBargainMoneyCommand();
		payBargainMoneyCommand.setDealerOrderNo("B611103841110000");
		ApiRequest request = new ApiRequest("PayBargainMoney", UUID.randomUUID()
				.toString(), payBargainMoneyCommand, "1.0");

		PayBargainMoneyResponse response = client.send(request,
				PayBargainMoneyResponse.class);

		System.out.println(response.getMessage());
		System.out.println(JSON.toJSONString(payBargainMoneyCommand));
		System.out.println(JSON.toJSONString(response));
	}
}
