package lxs.api.user;

import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.dto.user.ContactsDTO;
import lxs.api.v1.request.command.user.CreateContactsCommand;
import lxs.api.v1.response.user.CreateContactsResponse;

public class CreateContactsTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		ContactsDTO contactsDTO = new ContactsDTO();
		contactsDTO.setContactsName("宋小宝");
		contactsDTO.setMobile("18888888888");
		contactsDTO.setContactsIdCardNO("11111121212");
		contactsDTO.setUser("97aad580611f475a8e3946720498d315");
		contactsDTO.setType("child");
		CreateContactsCommand command = new CreateContactsCommand();
		command.setContactsDTO(contactsDTO);

		ApiRequest request = new ApiRequest("CreateContacts", UUID.randomUUID()
				.toString(), command, "1.0");
		CreateContactsResponse response = client.send(request,
				CreateContactsResponse.class);

		System.out.println(response.getMessage());
		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));

	}
}
