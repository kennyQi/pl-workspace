package lxs.api.user;

import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.user.DeleteContactsCommand;
import lxs.api.v1.response.user.DeleteContactsResponse;

public class DeleteContactsTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		DeleteContactsCommand command = new DeleteContactsCommand();
		command.setContactsID("7633da57d78548df83efabf552cd2ec6");

		ApiRequest request = new ApiRequest("DeleteContacts", UUID.randomUUID()
				.toString(), command, "1.0");
		DeleteContactsResponse response = client.send(request,
				DeleteContactsResponse.class);

		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));

	}
}
