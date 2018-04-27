//
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.junit.Test;
//
//import plfx.api.base.ApiRequest;
//import plfx.api.base.PlfxApiClient;
//import plfx.api.v1.request.command.jp.JPBookTicketCommand;
//import plfx.api.v1.request.qo.jp.JPFlightQO;
//import plfx.api.v1.request.qo.jp.JPPolicyQO;
//import plfx.api.v1.response.jp.JPBookOrderResponse;
//import plfx.api.v1.response.jp.JPQueryFlightListResponse;
//import plfx.api.v1.response.jp.JPQueryHighPolicyResponse;
//import plfx.jp.app.component.FlightCacheManager;
//import plfx.jp.pojo.dto.supplier.yx.PassengerDTO;
//import plfx.jp.pojo.dto.supplier.yx.PassengerInfoDTO;
//import plfx.jp.pojo.dto.supplier.yx.YeeXingFlightDTO;
//
//import com.alibaba.fastjson.JSON;
//
//public class TestJP {
//	private final static String httpUrl = "http://192.168.10.59:8080/plfx-api/plfx/api";
//	/**
//	 * 航班查询
//	 */
//	//@Test
//	public void testJPQueryFlight(){
//		//创建api客户端类，可复用
//		PlfxApiClient client = new PlfxApiClient(httpUrl,"JPQueryFlight","ghfdkgjs");
//		//创建要发送的业务内容
//		JPFlightQO qo = new JPFlightQO();
//		qo.setOrgCity("SHA");
//		qo.setDstCity("CAN");
//		qo.setStartDate("2015-07-20");
//		qo.setStartTime("07:30");
//		//创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPQueryFlight", "F1001","192.168.1.1",UUID.randomUUID().toString(), qo);
//		//发送请求
//		JPQueryFlightListResponse response = client.send(request, JPQueryFlightListResponse.class);
//		System.out.println(JSON.toJSONString(response.getFlightList()));
//		System.out.println(JSON.toJSON(response.getFlightList()));
//	//	List<PlfxFlightDTO> flightList =BeanMapperUtils.getMapper().mapAsList(response.getFlightList(), PlfxFlightDTO.class);
//		System.out.println(JSON.toJSONString(response));
//		
//	}
//	/**
//	 * 政策查询
//	 */
//	@Test
//	public void testJPQueryFlightPolicy(){
//		//创建api客户端类，可复用
//		PlfxApiClient client = new PlfxApiClient(httpUrl,"JPQueryAirPolicy","ghfdkgjs");
//		//创建要发送的业务内容
//		JPPolicyQO qo = new JPPolicyQO();
//		qo.setAirpGet("");
//		qo.setAirpSource("");
//		qo.setTickType("1");
//		qo.setEncryptString("79fccac98f1646870f3bf74a1530f1e601cc4325bba8d0a82acc48d971ca6f0be06aa3e9e64252f65eb185d58032c30c777a7fd1797e473b823b0fc06752d7b3c5566a4f47f867f209014c4b89d04ea9284f263c3fd8c11a");
//		//创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPQueryAirPolicy", "F1001","192.168.1.1",UUID.randomUUID().toString(), qo);
//		//发送请求
//		JPQueryHighPolicyResponse response = client.send(request, JPQueryHighPolicyResponse.class);
//		System.out.println(JSON.toJSONString(response));	
//	}
//
//	/**
//	 * 订单创建
//	 */
//	@Test
//	public void testJPOrderCreate(){
//		//创建api客户端类，可复用
//		PlfxApiClient client = new PlfxApiClient(httpUrl,"JPBookTicket","ghfdkgjs");
//		//创建要发送的业务内容
//		JPBookTicketCommand command = new JPBookTicketCommand();
//		command.setFlightNo("MU5301");
//		command.setStartDate("2015-07-20 07:30");
//		command.setEncryptString("79fccac98f1646870f3bf74a1530f1e601cc4325bba8d0a82acc48d971ca6f0be06aa3e9e64252f6e54aa53759e5cc058b97925240cdd2c7f909a85ce594908f23ac2e909e33ced2caa5bbe4a9196590f685ea84d6a67d1d5a50d3fabfce97825cf6338b6b24de7107886c443d83ba6c0db6bab2ed11e2e1cab83da0e0579c954989b31750bcc0e054ead4e07d30d3f2");
//		PassengerInfoDTO passengerInfoDTO=new PassengerInfoDTO();
//		List<PassengerDTO> passengerList=new ArrayList<PassengerDTO>();
//		passengerInfoDTO.setName("赵文书");
//		passengerInfoDTO.setTelephone("18271260697");
//		PassengerDTO psg=new PassengerDTO();
//		psg.setPassengerName("谭登");
//		psg.setPassengerType("1");
//		psg.setIdNo("421081198012121234");
//		psg.setIdType("0");	
//		passengerList.add(psg);
//		passengerInfoDTO.setPassengerList(passengerList);
//		command.setPassengerInfoDTO(passengerInfoDTO);
//		System.out.println(JSON.toJSONString(command));	
//		//创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPBookTicket", "F1001","192.168.1.1",UUID.randomUUID().toString(), command);
//		//发送请求
//		JPBookOrderResponse response = client.send(request, JPBookOrderResponse.class);
//		//查看返回内容
//		System.out.println("response.getResult()=========="+response.getResult());
//		System.out.println(JSON.toJSONString(response));
//		
//	}
//	//@Test
//	public void testFindCacheFlight(){
//		FlightCacheManager f=new FlightCacheManager();
//		YeeXingFlightDTO yx=f.getYXFlightDTO("MU5301","2015-07-20 07:30");
//		System.out.println("999999999999999");
//		System.out.println(JSON.toJSONString(yx));
//		System.out.println("TTTTTTTTTTTTTTTT");
//	}
//	
//
//
//}
