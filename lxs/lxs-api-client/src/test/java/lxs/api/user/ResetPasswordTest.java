package lxs.api.user;


import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.user.ResetPasswordCommand;
import lxs.api.v1.response.user.ResetPasswordResponse;

//package hsl.api;

public class ResetPasswordTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		ResetPasswordCommand command = new ResetPasswordCommand();

		command.setSagaId("5fd29b86-c430-4915-b37c-4e3143ef0ac7");
		command.setEncryptPassword("e10adc3949ba59abbe56e057f20f883e");
		
		ApiRequest request = new ApiRequest("ResetPassword", UUID.randomUUID()
				.toString(), command, "1.0");
		ResetPasswordResponse response = client.send(request,ResetPasswordResponse.class);

		System.out.println(response.getMessage());
		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));
	}

}
