package lxs.api.user;


import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.user.ResetPasswordCheckMobileCommand;
import lxs.api.v1.response.user.ResetPasswordCheckMobileResponse;

//package hsl.api;

public class ResetPasswordCheckMobileTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		ResetPasswordCheckMobileCommand command = new ResetPasswordCheckMobileCommand();

		command.setSagaId("5fd29b86-c430-4915-b37c-4e3143ef0ac7");
		command.setValidCode("232351");
		
		ApiRequest request = new ApiRequest("ResetPasswordCheckMobile", UUID.randomUUID()
				.toString(), command, "1.0");
		ResetPasswordCheckMobileResponse response = client.send(request,
				ResetPasswordCheckMobileResponse.class);

		System.out.println(response.getMessage());
		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));
	}

}
