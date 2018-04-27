//package hsl.api;
//
//
//import hsl.api.base.ApiRequest;
//import hsl.api.base.HslApiClient;
//import hsl.api.v1.request.command.jp.JPAskOrderTicketCommand;
//import hsl.api.v1.request.command.jp.JPOrderCreateCommand;
//import hsl.api.v1.request.qo.jp.JPFlightQO;
//import hsl.api.v1.request.qo.jp.JPPolicyQO;
//import hsl.api.v1.response.jp.JPAskOrderTicketResponse;
//import hsl.api.v1.response.jp.JPCreateOrderResponse;
//import hsl.api.v1.response.jp.JPQueryFlightResponse;
//import hsl.api.v1.response.jp.JPQueryPolicyResponse;
//import hsl.pojo.dto.jp.FlightPassangerDto;
//import hsl.pojo.dto.user.UserDTO;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.junit.Test;
//
//import com.alibaba.fastjson.JSON;
//
//public class TestAPI_HTTP_Request {
//	
//	private final static String httpUrl = "http://192.168.10.67:28080/hsl-api/api";
////	创建api客户端类，可复用
//	HslApiClient client = new HslApiClient(httpUrl,"JPQueryFlight","ghfdkgjs");
//	
//	/**
//	 * 航班查询
//	 */
//	@Test
//	public void testJPQueryFlight(){
//		//	创建要发送的业务内容
//		JPFlightQO qo = new JPFlightQO();
//		qo.setFrom("PEK");
//		qo.setArrive("DLC");
//		qo.setDate("2014-08-14");
//		
//		//qo.setFlightNo();
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPQueryFlight", "192.168.1.1",UUID.randomUUID().toString(), qo, "1.0");
//		
//		//	发送请求
//		JPQueryFlightResponse response = client.send(request, JPQueryFlightResponse.class);
//		
//		//	查看返回内容
//		System.out.println("response.getResult()=========="+response.getResult());
//		
//		System.out.println(JSON.toJSONString(response));
//		
//	}
//	
//	/**
//	 * 请求出票
//	 */
//	@Test
//	public void testJPAskOrderTicket(){
//		//	创建要发送的业务内容
//		JPAskOrderTicketCommand command = new JPAskOrderTicketCommand();
//		//经销商订单号
//		command.setOrderId("11111111111"); 
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPAskOrderTicket", "192.168.1.1",UUID.randomUUID().toString(), command, "1.0");
//		
//		//	发送请求
//		JPAskOrderTicketResponse response = client.send(request, JPAskOrderTicketResponse.class);
//		
//		//	查看返回内容
//		System.out.println("response.getResult()=========="+response.getResult());
//		
//		System.out.println(JSON.toJSONString(response));
//		
//	}
//	
//	/**
//	 * 政策查询
//	 */
//	@Test
//	public void testJPQueryFlightPolicy(){
//		//	创建要发送的业务内容
//		JPPolicyQO qo = new JPPolicyQO();
//		qo.setFlightNo("HU7183");
//		qo.setClassCode("Y");
//		qo.setDepartDate("2014-08-14");	
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPQueryFlightPolicy", "192.168.1.1",UUID.randomUUID().toString(), qo, "1.0");
//		
//		//	发送请求
//		JPQueryPolicyResponse response = client.send(request, JPQueryPolicyResponse.class);
//		
//		//	查看返回内容
//		System.out.println("response.getResult()=========="+response.getResult());
//		
//		System.out.println(JSON.toJSONString(response));
//		
//	}
//	
//	@Test
//	public void test(){
//		
//		System.out.println("-------");	
//		
//	}
//	
///**
// * 订单创建
// */
//@Test
//public void testJPOrderCreate(){
//	//		创建api客户端类，可复用
//	HslApiClient client = new HslApiClient(httpUrl,"JPOrderCreate","ghfdkgjs");
//	
//	//	创建要发送的业务内容
//	JPOrderCreateCommand command = new JPOrderCreateCommand();
//	//商城向平台下单传入参数
//	//command.setTotalPrice(2700.00);
//	command.setFlightNo("MU5131");
//	command.setDate("2014-08-30");
//	command.setClassCode("N");
//	//command.setPolicyId("d278c88d037e41258bfae5660bcf7760");
//	command.setPolicyId("b1b28c97ef2742beb2e7f560167a9660");
//	
//		List<FlightPassangerDto> passangers = new ArrayList<FlightPassangerDto>();
//		
//			FlightPassangerDto psg=new FlightPassangerDto();
//			
//			psg.setPsgNo(0);
//			psg.setName("谭登t");
//			psg.setPassangerType("ADT");
//			psg.setIdentityType("NI");
//			psg.setCardType("NI");
//			psg.setCardNo("421081198012121111");
//			//psg.setBirthday("2004-08-08");
//			psg.setCarrierPsgNo(0);
//			psg.setInsueSum(0);
//			psg.setInsueFee(0.00);
//			psg.setMobile("13711111113");
//		passangers.add(psg);
//		
//	command.setPassangers(passangers);
//		
//		UserDTO userDTO = new UserDTO();
//		//userDTO.setId("0c8fddf6db304b0dbb153a93b708dff1");
//		userDTO.setId("1");
////				UserContactInfoDTO userContactInfoDTO = new UserContactInfoDTO();
////				userContactInfoDTO.setMobile("13112345678");
////			userDTO.setContactInfo(userContactInfoDTO);
//		userDTO.setMobile("13722222224");
//	command.setLinker(userDTO);
//	
//	command.setPayType(1);//1 支付宝
//	
//	//System.out.println(JSON.toJSONString(command));
//		
//	
//	//	创建要发送的请求对象
//	ApiRequest request = new ApiRequest("JPOrderCreate", "192.168.1.1",UUID.randomUUID().toString(), command,"v1.0");
//	
//	System.out.println(JSON.toJSONString(request));
//	//	发送请求
//	JPCreateOrderResponse response = client.send(request, JPCreateOrderResponse.class);
//	//	查看返回内容
//	System.out.println("response.getResult()=========="+response.getResult());
//	
//	System.out.println(JSON.toJSONString(response));
//}
//}
