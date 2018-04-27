import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import plfx.api.client.api.v1.gn.dto.PassengerGNDTO;
import plfx.api.client.api.v1.gn.dto.PassengerInfoGNDTO;
import plfx.api.client.api.v1.gn.request.JPBookTicketGNCommand;
import plfx.api.client.api.v1.gn.response.JPBookOrderGNResponse;
import plfx.api.client.common.util.PlfxApiClient;

import com.alibaba.fastjson.JSON;

public class TestPayBalacnes {
	private String httpUrl = "http://127.0.0.1:8080/plfx-api/api";
	
	private String dealerKey = "F4001";//经销商代码
	
	private String secretKey = "123456";//经销商key
	
	@Test
	public void PayBalaces(){

		// 创建api客户端类
		PlfxApiClient client = new PlfxApiClient(httpUrl, dealerKey, secretKey);
		// 创建要发送的业务内容command
		JPBookTicketGNCommand command = new JPBookTicketGNCommand();
		// 航班(必填字段)
		command.setFromDealerId(dealerKey);
		command.setDealerOrderId(UUID.randomUUID().toString().replace("-", ""));
		command.setFlightNo("HO1189");
		// 起飞时间(必填字段)
		command.setCabinCode("A");
		command.setCabinName("经济舱");
		command.setUserPayAmount(1479.0);
		command.setUserPayCash(120.0);
	//	command.setStartDate("2015-07-28 08:05");
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
		String startDate="2016-02-17";
		try {
			command.setStartDate(sp.parse(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 加密字符串(必填字段)
		command.setEncryptString("1fdeaeba64b1cfd691f503d148393c73fc5d0267392cea31c96e197f374277bc3abc13018fe8d8510a8a3338004685ac8f6bb637821637f2f31951e14f2098d2f5c4d1d00b27674794279556e32b65783e1f6b26b73fb35a4f2c90b4e9a8ad5e7f64f31d7af5c37f2c4237565595f85528d5a024fafb334dad618f4ac34053e14d3b8c6134e00575ad6a88b4fa37833d");
		PassengerInfoGNDTO passengerInfoDTO = new PassengerInfoGNDTO();
		// 乘客信息集合(必填字段)
		List<PassengerGNDTO> passengerList = new ArrayList<PassengerGNDTO>();
		// 联系人姓名(必填字段)
		passengerInfoDTO.setName("李艳勇");
		// 联系人手机(必填字段,且只能填一个)
		passengerInfoDTO.setTelephone("18458109377");
		PassengerGNDTO psg = new PassengerGNDTO();
		// 姓名(必填字段)
		psg.setPassengerName("胡小晴");
		// 乘客类型(必填字段)
		psg.setPassengerType("1");
		// 身份证号(必填字段)
		psg.setIdNo("330327199501013031");
		// 证件类型(必填字段)
		psg.setIdType("0");
		passengerList.add(psg);
//
		PassengerGNDTO psgg = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg.setPassengerName("赵啥勇");
		// 乘客类型(必填字段)
		psgg.setPassengerType("1");
		// 身份证号(必填字段)
		psgg.setIdNo("897644198501231657");
		// 证件类型(必填字段)
		psgg.setIdType("0");
		//passengerList.add(psgg);
		
		PassengerGNDTO psggg = new PassengerGNDTO();
		// 姓名(必填字段)
		psggg.setPassengerName("张安勇");
		// 乘客类型(必填字段)
		psggg.setPassengerType("1");
		// 身份证号(必填字段)
		psggg.setIdNo("331023199102060534");
		// 证件类型(必填字段)
		psggg.setIdType("0");
//			passengerList.add(psggg);
		
		PassengerGNDTO psgg1 = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg1.setPassengerName("阿勇");
		// 乘客类型(必填字段)
		psgg1.setPassengerType("1");
		// 身份证号(必填字段)
		psgg1.setIdNo("897644198501231657");
		// 证件类型(必填字段)
		psgg1.setIdType("0");
	//	passengerList.add(psgg1);
		
		PassengerGNDTO psgg2 = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg2.setPassengerName("李连勇");
		// 乘客类型(必填字段)
		psgg2.setPassengerType("1");
		// 身份证号(必填字段)
		psgg2.setIdNo("897644198501231657");
		// 证件类型(必填字段)
		psgg2.setIdType("0");
	//	passengerList.add(psgg2);
		
		PassengerGNDTO psgg3 = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg3.setPassengerName("张宝勇");
		// 乘客类型(必填字段)
		psgg3.setPassengerType("1");
		// 身份证号(必填字段)
		psgg3.setIdNo("411023198904102519");
		// 证件类型(必填字段)
		psgg3.setIdType("0");
	//	passengerList.add(psgg3);
		
		PassengerGNDTO psgg4 = new PassengerGNDTO();
		// 姓名(必填字段)
		psgg4.setPassengerName("李青勇");
		// 乘客类型(必填字段)
		psgg4.setPassengerType("1");
		// 身份证号(必填字段)
		psgg4.setIdNo("510824199108015796");
		// 证件类型(必填字段)
		psgg4.setIdType("0");
	//	passengerList.add(psgg4);

		passengerInfoDTO.setPassengerList(passengerList);
		command.setPassengerInfoGNDTO(passengerInfoDTO);
//			System.out.println(JSON.toJSONString(command));
		// 发送请求
		JPBookOrderGNResponse response = client.send(command,
				JPBookOrderGNResponse.class);
		// 查看返回内容
		System.out.println(JSON.toJSONString(response));

	}
}
