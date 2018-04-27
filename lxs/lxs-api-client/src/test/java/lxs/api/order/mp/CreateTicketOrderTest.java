package lxs.api.order.mp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.dto.mp.TouristDTO;
import lxs.api.v1.request.command.order.mp.CreateTicketOrderCommand;
import lxs.api.v1.response.order.mp.CreateTicketOrderResponse;

import com.alibaba.fastjson.JSON;

public class CreateTicketOrderTest {
	
	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		
		CreateTicketOrderCommand createTicketOrderCommand = new CreateTicketOrderCommand();
		List<TouristDTO> touristDTOs = new ArrayList<TouristDTO>();
		TouristDTO touristDTO = new TouristDTO();
		touristDTO.setAddress("地址");
		touristDTO.setAge(28);
		touristDTO.setBirthday(new Date());
		touristDTO.setGender(1);
		touristDTO.setIdNo("239922199101012012");
		touristDTO.setIdType(1);
		touristDTO.setName("郭达");
		touristDTO.setNation("多米尼加共和国");
		touristDTO.setTelephone("13798765432");
		touristDTOs.add(touristDTO);
		TouristDTO touristDTO1 = new TouristDTO();
		touristDTO1.setAddress("地址");
		touristDTO1.setAge(28);
		touristDTO1.setBirthday(new Date());
		touristDTO1.setGender(1);
		touristDTO1.setIdNo("239922199101012013");
		touristDTO1.setIdType(1);
		touristDTO1.setName("杰森斯坦森");
		touristDTO1.setNation("牙买加");
		touristDTO1.setTelephone("13798765432");
		touristDTOs.add(touristDTO1);
		createTicketOrderCommand.setTourists(touristDTOs);
		createTicketOrderCommand.setBookMan("随心游");
		createTicketOrderCommand.setBookManAliPayAccount("abc@123.com");
		createTicketOrderCommand.setBookManMobile("18612345678");
		createTicketOrderCommand.setTicketPolicyId("fda73095ab6346aa8b4178796785dcd6");
		createTicketOrderCommand.setUserID("123465");
		String s = "20160315";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		
		try {
			createTicketOrderCommand.setTravelDate(simpleDateFormat.parse(s));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		ApiRequest request = new ApiRequest("CreateTicketOrder", UUID.randomUUID().toString(), createTicketOrderCommand, "1.0");
		CreateTicketOrderResponse createTicketOrderResponse = client.send(request,
				CreateTicketOrderResponse.class);

		System.out.println(JSON.toJSONString(createTicketOrderResponse));
	}

}
