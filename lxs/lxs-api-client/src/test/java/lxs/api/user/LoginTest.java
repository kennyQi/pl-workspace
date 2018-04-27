package lxs.api.user;

import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.user.LoginCommand;
import lxs.api.v1.response.user.LoginResponse;

//package hsl.api;

public class LoginTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		LoginCommand command = new LoginCommand();
		command.setLoginName("18612345678");
		command.setPassword("e10adc3949ba59abbe56e057f20f883e");
		command.setQueryUserInfo(true);
		ApiRequest request = new ApiRequest("Login", UUID.randomUUID()
				.toString(), command, "1.0");
		LoginResponse response = client.send(request,
				LoginResponse.class);

		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));

	}

}
