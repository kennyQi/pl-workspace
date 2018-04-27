package lxs.api.user;

import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.user.GetSMSValidCodeCommand;
import lxs.api.v1.response.user.GetSMSValidCodeResponse;

//package hsl.api;

public class GetSMSValidCodeTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		GetSMSValidCodeCommand command = new GetSMSValidCodeCommand();
		command.setMobile("18612345678");
		command.setSceneType(GetSMSValidCodeCommand.SCENE_TYPE_FIND_PASSWORD);

		ApiRequest request = new ApiRequest("GetSMSValidCode", UUID
				.randomUUID().toString(), command, "1.0");

		GetSMSValidCodeResponse response = client.send(request,
				GetSMSValidCodeResponse.class);

		System.out.println(response.getMessage());

		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));
	}


}
