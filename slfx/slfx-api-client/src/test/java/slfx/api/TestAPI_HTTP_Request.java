//package slfx.api;
//
//
//import hg.common.util.DateUtil;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import org.junit.Test;
//
//import slfx.api.base.ApiRequest;
//import slfx.api.base.SlfxApiClient;
//import slfx.api.util.HttpUtil;
//import slfx.api.v1.request.command.jp.APIJPOrderCreateCommand;
//import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;
//import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
//import slfx.api.v1.request.qo.jp.JPFlightQO;
//import slfx.api.v1.request.qo.jp.JPOrderQO;
//import slfx.api.v1.request.qo.jp.JPPolicyQO;
//import slfx.api.v1.response.jp.JPAskOrderTicketResponse;
//import slfx.api.v1.response.jp.JPCancelOrderTicketResponse;
//import slfx.api.v1.response.jp.JPCityAirCodeResponse;
//import slfx.api.v1.response.jp.JPCreateOrderResponse;
//import slfx.api.v1.response.jp.JPQueryFlightResponse;
//import slfx.api.v1.response.jp.JPQueryOrderResponse;
//import slfx.api.v1.response.jp.JPQueryPolicyResponse;
//import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
//import slfx.jp.pojo.dto.flight.SlfxFlightPolicyDTO;
//import slfx.jp.pojo.dto.order.JPOrderUserInfoDTO;
//
//import com.alibaba.fastjson.JSON;
//
//public class TestAPI_HTTP_Request {
//	
//	//private final static String httpUrl = "http://192.168.2.211:9080/slfx-api/slfx/api";
//	private final static String httpUrl = "http://192.168.10.101:8081/slfx-api/slfx/api";
//	
//	/**
//	 * 航班查询
//	 */
//	@Test
//	public void testJPQueryFlight(){
//		//		创建api客户端类，可复用
//		SlfxApiClient client = new SlfxApiClient(httpUrl,"JPQueryFlight","ghfdkgjs");
//
//		//	创建要发送的业务内容
//		JPFlightQO qo = new JPFlightQO();
//		qo.setFrom("PEK");
//		qo.setArrive("DLC");
//		qo.setDate("2015-02-30");
//		
//		//qo.setFlightNo("CA8908");
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPQueryFlight", "F1001","192.168.1.1",UUID.randomUUID().toString(), qo);
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
//		//		创建api客户端类，可复用
//		SlfxApiClient client = new SlfxApiClient(httpUrl,"JPAskOrderTicket","ghfdkgjs");
//		
//		//	创建要发送的业务内容
//		JPAskOrderTicketCommand command = new JPAskOrderTicketCommand();
//		//经销商订单号
//		command.setOrderId("11111111111"); 
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPAskOrderTicket","F1001", "192.168.1.1",UUID.randomUUID().toString(), command);
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
//	 * 订单创建
//	 */
//	@Test
//	public void testJPOrderCreate(){
//		//		创建api客户端类，可复用
//		SlfxApiClient client = new SlfxApiClient(httpUrl,"JPOrderCreate","ghfdkgjs");
//		
//		//	创建要发送的业务内容
//		APIJPOrderCreateCommand command = new APIJPOrderCreateCommand();
//		//商城向平台下单传入参数
//		command.setDealerOrderId("");
//		command.setFlightNo("CZ6128");
//		command.setClassCode("Y");
//		command.setBigPNR("BJS280");
//		command.setPolicyId("53775320");
//		/*command.setBalanceMoney(2950.0);
//		command.setStartDateStr("2014-08-04");
//		command.setIsDomc("");
//		command.setClassPrice(0.0);
//		command.setTicketLimitDate("");
//		command.setTicketLimitTime("");
//		command.setPlatCode("001");
//		command.setPlatformType("Y");
//		command.setAccountLevel("10");*/
//			
//			List<FlightPassengerDTO> passangers =new ArrayList<FlightPassengerDTO>();
//			
//			FlightPassengerDTO psg=new FlightPassengerDTO();
//			psg.setPsgNo(0);
//			psg.setPassangerType("ADT");
//			psg.setName("任风");
//			//psg.setCountry("");
//			psg.setIdentityType("NI");
//			psg.setCardType("NI");
//			psg.setCardNo("123");
//			//psg.setBirthDay("");
//			//psg.setInsueFee(0.0);
//			//psg.setInsueSum(0);
//			
//			SlfxFlightPolicyDTO flightPolicyDTO =new SlfxFlightPolicyDTO ();
//			flightPolicyDTO.setPolicyId("965a3071-c91e-4f73-be92-6b397103d07e");
//			flightPolicyDTO.setRebate(0);
//			flightPolicyDTO.setTicketPrice(0.00);
//			flightPolicyDTO.setFuelSurTax(50.0);
//			flightPolicyDTO.setAirportTax(60.0);
//			flightPolicyDTO.setAutoTicket("");
//			flightPolicyDTO.setReceipt("");
//			flightPolicyDTO.setPaymentType("");
//			flightPolicyDTO.setRemark("");
//			flightPolicyDTO.setTktWorktime("");
//			flightPolicyDTO.setFare(0.0);
//			
//			psg.setFlightPolicyDTO(flightPolicyDTO);
//			passangers.add(psg);
//			
//		command.setPassangers(passangers);
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPOrderCreate","F1001", "192.168.1.1",UUID.randomUUID().toString(), command);
//		
//		//	发送请求
//		JPCreateOrderResponse response = client.send(request, JPCreateOrderResponse.class);
//		
//		//	查看返回内容
//		System.out.println("response.getResult()=========="+response.getResult());
//		
//		System.out.println(JSON.toJSONString(response));
//		
//	}
//	
//	/**
//	 * 订单取消
//	 */
//	@Test
//	public void testJPCancelOrderTicket(){
//		//		创建api客户端类，可复用
//		SlfxApiClient client = new SlfxApiClient(httpUrl,"JPCancelOrderTicket","ghfdkgjs");
//		
//		//	创建要发送的业务内容
//		JPCancelTicketCommand command = new JPCancelTicketCommand();
//		command.setOrderId("38e63d21-1f07-4faf-9e51-28e66e8d7177");
//		command.setTicketNumbers("");
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPCancelOrderTicket", "F1001","192.168.1.1",UUID.randomUUID().toString(), command);
//		
//		//	发送请求
//		JPCancelOrderTicketResponse response = client.send(request, JPCancelOrderTicketResponse.class);
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
//		//		创建api客户端类，可复用
//		SlfxApiClient client = new SlfxApiClient(httpUrl,"JPQueryFlightPolicy","ghfdkgjs");
//		
//		//	创建要发送的业务内容
//		JPPolicyQO qo = new JPPolicyQO();
//		qo.setFlightNo("MU2751");
//		qo.setClassCode("Y");
//			Date departureDate = new Date();
//		qo.setDepartDate(DateUtil.formatDateTime(departureDate,"yyyyMMdd"));	
//		qo.setDepartDate("20140830");	
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPQueryFlightPolicy", "F1001","192.168.1.1",UUID.randomUUID().toString(), qo);
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
//	/**
//	 * 订单查询
//	 */
//	@Test
//	public void testJPOrderQuery(){
//		//		创建api客户端类，可复用
//		SlfxApiClient client = new SlfxApiClient(httpUrl,"JPOrderQuery","ghfdkgjs");
//		
//		//	创建要发送的业务内容
//		JPOrderQO qo = new JPOrderQO();
//		//qo.setUserId("");
//		qo.setOrderId("38e63d21-1f07-4faf-9e51-28e66e8d7177");
//		//qo.setDetail(true);
//		qo.setPageNo(1);
//		qo.setPageSize(10);
//				
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPQueryFlightPolicy", "F1001","192.168.1.1",UUID.randomUUID().toString(), qo);
//		
//		//	发送请求
//		JPQueryOrderResponse response = client.send(request, JPQueryOrderResponse.class);
//		
//		//	查看返回内容
//		System.out.println("response.getResult()=========="+response.getResult());
//		
//		System.out.println(JSON.toJSONString(response));
//		
//	}
//	
//	/**
//	 * 城市机场三字码查询（返回所有数据）
//	 */
//	@Test
//	public void testJPQueryCityAirCode(){
//		//		创建api客户端类，可复用
//		SlfxApiClient client = new SlfxApiClient(httpUrl,"JPQueryCityAirCode","ghfdkgjs");
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPQueryCityAirCode", "F1001","192.168.1.1",UUID.randomUUID().toString(), null);
//		
//		//	发送请求
//		JPCityAirCodeResponse response = client.send(request, JPCityAirCodeResponse.class);
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
//	/**
//	 * 订单创建
//	 */
//	@Test
//	public void testJPOrderCreateSLFX(){
//		//		创建api客户端类，可复用
//		SlfxApiClient client = new SlfxApiClient(httpUrl,"JPOrderCreate","ghfdkgjs");
//		
//		//	创建要发送的业务内容
//		APIJPOrderCreateCommand command = new APIJPOrderCreateCommand();
//		//商城向平台下单传入参数
//		command.setTotalPrice(2700.00);
//		command.setFlightNo("CA1699");
//		command.setDate("2014-08-18");
//		command.setClassCode("A");
//		command.setPolicyId("5eec86502fdb47868f7538b5c959f70e");
//		command.setPayType("1");
//		
//			List<FlightPassengerDTO> passangers =new ArrayList<FlightPassengerDTO>();
//			
//			FlightPassengerDTO psg=new FlightPassengerDTO();
//				
//				psg.setPsgNo(0);
//				psg.setName("谭登");
//				psg.setPassangerType("ADT");
//				psg.setIdentityType("身份证");
//				psg.setCardType("NI");
//				psg.setCardNo("421081198012121234");
//				psg.setBirthday("2004-08-08");
//				psg.setCarrierPsgNo(1);
//				psg.setInsueSum(1);
//				psg.setInsueFee(20.00);
//
//			passangers.add(psg);
//			
//		command.setPassangers(passangers);
//			
//			JPOrderUserInfoDTO userDTO = new JPOrderUserInfoDTO();
//			userDTO.setId("注册用户id");
//			userDTO.setMobile("13112345678");
////			userDTO.setPayType("1");
//		command.setLinker(userDTO);
//		
//		System.out.println(JSON.toJSONString(command));
//			
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPOrderCreate", "F1001","192.168.1.1",UUID.randomUUID().toString(), command);
//		
//		//	发送请求
//		JPCreateOrderResponse response = client.send(request, JPCreateOrderResponse.class);
//		
//		//	查看返回内容
//		System.out.println("response.getResult()=========="+response.getResult());
//		
//		System.out.println(JSON.toJSONString(response));
//		
//	}
//	
//	@Test
//	public void testHttpPost1(){
//		String notifyUrl = "http://183.129.207.14:8280/slfx/api/backOrDiscardTicket/notify";
//		String orderNo ="4110415316103";
//		String platOrderNo ="R2014110405590293";
//		String messageType ="2";
//		String payMoney = "815";
//		String flag = "Y";
//		HttpUtil.reqForPost(notifyUrl, "OrderNo=" +orderNo+ "&PlatOrderNo="+platOrderNo+"&MessageType="+messageType+"&PayMoney="+payMoney+"&Flag="+flag,60000);
//	}
//	
//	@Test
//	public void testHttpPost2(){
//		String notifyUrl = "http://192.168.10.101:8081/slfx-api/slfx/api/backOrDiscardTicket/notify";
//		String orderNo ="4110415156101";
//		String platOrderNo ="R2014110405590293";
//		String messageType ="2";
//		String payMoney = "815";
//		String flag = "Y";
//		HttpUtil.reqForPost(notifyUrl, "OrderNo=" +orderNo+ "&PlatOrderNo="+platOrderNo+"&MessageType="+messageType+"&PayMoney="+payMoney+"&Flag="+flag,60000);
//	}
//	@Test
//	public void testHttpPost3(){
//		String notifyUrl = "http://192.168.2.228:15003/slfx/api/backOrDiscardTicket/notify";
//		String orderNo ="4120415226104";
//		String platOrderNo ="112014120447660287";
//		String messageType ="2";
//		String payMoney = "337";
//		String flag = "Y";
//		HttpUtil.reqForPost(notifyUrl, "OrderNo=" +orderNo+ "&PlatOrderNo="+platOrderNo+"&MessageType="+messageType+"&PayMoney="+payMoney+"&Flag="+flag,60000);
//	}
//	@Test
//	public void test3(){
//		System.out.println(Double.parseDouble("-"+10d));
//		
//	}
//
//}
