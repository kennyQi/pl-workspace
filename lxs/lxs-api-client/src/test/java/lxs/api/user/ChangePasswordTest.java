package lxs.api.user;


import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.user.ChangePasswordCommand;
import lxs.api.v1.response.user.ChangePasswordResponse;

//package hsl.api;

public class ChangePasswordTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		ChangePasswordCommand command = new ChangePasswordCommand();
		command.setUserId("92ab1c459edd472199980d6eb21956ca");
		command.setOldPassWord("e10adc3949ba59abbe56e057f20f883e");
		command.setNewPassWord("123456");
		ApiRequest request = new ApiRequest("ChangePassword", UUID.randomUUID()
				.toString(), command, "1.0");
		ChangePasswordResponse response = client.send(request,
				ChangePasswordResponse.class);

		System.out.println(response.getMessage());
		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));
	}

}
