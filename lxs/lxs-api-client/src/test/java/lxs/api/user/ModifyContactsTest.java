package lxs.api.user;

import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.dto.user.ContactsDTO;
import lxs.api.v1.request.command.user.ModifyContactsCommand;
import lxs.api.v1.response.user.ModifyContactsResponse;

public class ModifyContactsTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		ContactsDTO contactsDTO = new ContactsDTO();
		contactsDTO.setContactsName("玉田");
		contactsDTO.setMobile("18888288888");
		contactsDTO.setContactsIdCardNO("11011912ss0114");
		contactsDTO.setId("7633da57d78548df83efabf552cd2ec6");
		contactsDTO.setType("adult");
		ModifyContactsCommand command = new ModifyContactsCommand();
		command.setContactsDTO(contactsDTO);
		
		
		
		
		
		

		ApiRequest request = new ApiRequest("ModifyContacts", UUID.randomUUID()
				.toString(), command, "1.0");
		ModifyContactsResponse response = client.send(request,
				ModifyContactsResponse.class);

		System.out.println(JSON.toJSONString(command));
		System.out.println(JSON.toJSONString(response));

	}
}
