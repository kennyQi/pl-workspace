package lxs.api.user;


import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.user.RegisterCommand;
import lxs.api.v1.response.user.RegisterResponse;

//package hsl.api;

public class RegisterTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		RegisterCommand command = new RegisterCommand();
		command.setSagaId("5ded75bc-7cad-4fa0-8e44-9ff66b04aeba");
		command.setValidCode("015887");
		command.setEncryptPassword("e10adc3949ba59abbe56e057f20f883e");
		command.setQueryUserInfo(true);
		command.setNickName("3-22test");

		ApiRequest request = new ApiRequest("Register", UUID.randomUUID()
				.toString(), command, "1.0");
		RegisterResponse response = client
				.send(request, RegisterResponse.class);

		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));

	}

}
