package lxs.api.order.line;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.dto.order.line.LineOrderDTO;
import lxs.api.v1.dto.user.ContactsDTO;
import lxs.api.v1.request.command.order.line.CreateLineOrderCommand;
import lxs.api.v1.response.order.line.CreateLineOrderResponse;

public class CreateLineOrderCommandTest {
	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		CreateLineOrderCommand createLineOrderCommand = new CreateLineOrderCommand();
		LineOrderDTO lineOrderDTO = new LineOrderDTO();
		lineOrderDTO.setUserID("ac2c6110f9f541a5a17c00d4dedb9a40");
		lineOrderDTO.setInsurancePrice(30);
		lineOrderDTO.setIsBuyInsurance(1);
		//线路
		lineOrderDTO.setLineID("0b5c4f5c6130476885f848134e58d2a9");
		//联系人
		List<ContactsDTO> list = new ArrayList<ContactsDTO>();
		ContactsDTO contactsDTO = new ContactsDTO();
		contactsDTO.setContactsName("库里");
		contactsDTO.setMobile("18612345678");
		contactsDTO.setContactsIdCardNO("13945489");
		contactsDTO.setType("adult");
		list.add(contactsDTO);
		contactsDTO = new ContactsDTO();
		contactsDTO.setContactsName("汤普森");
		contactsDTO.setMobile("18612345678");
		contactsDTO.setContactsIdCardNO("13945489");
		contactsDTO.setType("adult");
		list.add(contactsDTO);
		contactsDTO = new ContactsDTO();
		/*contactsDTO.setContactsName("格林");
		contactsDTO.setMobile("18612345678");
		contactsDTO.setContactsIdCardNO("13945489");
		contactsDTO.setType("child");
		list.add(contactsDTO);*/
		lineOrderDTO.setContactsList(list);
		//联系人
		lineOrderDTO.setLinkName("麦克海尔2");
		lineOrderDTO.setLinkEmail("michale@rockets.com");
		lineOrderDTO.setLinkMobile("18887884444");
		//基本信息
		lineOrderDTO.setAdultNO("2");
		lineOrderDTO.setAdultUnitPrice("500");
		lineOrderDTO.setChildNO("1");
		lineOrderDTO.setChildUnitPrice("250");
		lineOrderDTO.setSalePrice("1250");
		lineOrderDTO.setBargainMoney("937.5");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			 date = sdf.parse("2015-06-19 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lineOrderDTO.setTravelDate(date);
		lineOrderDTO.setPayment("zhifubao");
		createLineOrderCommand.setLineOrderDTO(lineOrderDTO);
		System.out.println(JSON.toJSONString(createLineOrderCommand));
		ApiRequest request = new ApiRequest("CreateLineOrder", UUID.randomUUID()
				.toString(), createLineOrderCommand, "1.0");
		CreateLineOrderResponse response = client.send(request,CreateLineOrderResponse.class);

		System.out.println(JSON.toJSONString(response));
	}
}
