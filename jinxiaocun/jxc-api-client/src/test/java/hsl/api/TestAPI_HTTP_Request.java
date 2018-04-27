//package hsl.api;
//
//
//import hsl.api.base.ApiRequest;
//import hsl.api.base.HslApiClient;
//import hsl.api.v1.request.command.jp.JPAskOrderTicketCommand;
//import hsl.api.v1.request.command.jp.JPCancelTicketCommand;
//import hsl.api.v1.request.command.jp.JPOrderCreateCommand;
//import hsl.api.v1.request.qo.jp.JPAirCodeQO;
//import hsl.api.v1.request.qo.jp.JPFlightQO;
//import hsl.api.v1.request.qo.jp.JPOrderQO;
//import hsl.api.v1.request.qo.jp.JPPolicyQO;
//import hsl.api.v1.response.jp.JPAskOrderTicketResponse;
//import hsl.api.v1.response.jp.JPCancelOrderTicketResponse;
//import hsl.api.v1.response.jp.JPCityAirCodeResponse;
//import hsl.api.v1.response.jp.JPCreateOrderResponse;
//import hsl.api.v1.response.jp.JPQueryFlightResponse;
//import hsl.api.v1.response.jp.JPQueryOrderResponse;
//import hsl.api.v1.response.jp.JPQueryPolicyResponse;
//import hsl.pojo.dto.jp.FlightDTO;
//import hsl.pojo.dto.jp.FlightPassangerDto;
//import hsl.pojo.dto.jp.FlightPolicyDTO;
//import hsl.pojo.dto.jp.JPOrderDTO;
//import hsl.pojo.dto.jp.TicketDto;
//import hsl.pojo.dto.user.UserContactInfoDTO;
//import hsl.pojo.dto.user.UserDTO;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import org.junit.Test;
//
//import com.alibaba.fastjson.JSON;
//
//public class TestAPI_HTTP_Request {
//	
//	//private final static String httpUrl = "http://192.168.2.211:9080/hsl-api/api";
//	private final static String httpUrl = "http://192.168.10.101:8081/hsl-api/api";
//	
//	//	创建api客户端类，可复用
//	HslApiClient client = new HslApiClient(httpUrl,"weixin","c196266f837d14e0b693f961bee37b66");
//	//HslApiClient client = new HslApiClient(httpUrl,"wap","ca4d8c5af3036c2f6d8f533a054457fd");
//	
//	/**
//	 * 航班列表查询
//	 */
//	@Test
//	public void testJPQueryFlight(){
//		//	创建要发送的业务内容
//		JPFlightQO qo = new JPFlightQO();
//		qo.setFrom("PEK");
////		qo.setArrive("FOC");
//		qo.setArrive("PVG");
//		qo.setDate("2014-10-13");
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
//	 * 指定航班查询
//	 */
//	@Test
//	public void testJPQueryFlightByNO(){
//		//	创建要发送的业务内容
//		JPFlightQO qo = new JPFlightQO();
//
//		qo.setDate("2014-09-10");
//		qo.setFlightNo("CA1701");
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
////		qo.setFlightNo("CZ6412");-20140830
//		qo.setFlightNo("CA1699");
//		qo.setClassCode("E");
////		Date departureDate = new Date();
//		qo.setDepartDate("2014-10-30");	
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
////	
//	/**
//	 * 订单创建
//	 */
//	@Test
//	public void testJPOrderCreate(){
//		//		创建api客户端类，可复用
//		HslApiClient client = new HslApiClient(httpUrl,"weixin","c196266f837d14e0b693f961bee37b66");
//		
//		//	创建要发送的业务内容
//		JPOrderCreateCommand command = new JPOrderCreateCommand();
//		//商城向平台下单传入参数
//		//command.setTotalPrice(2700.00);
//		command.setFlightNo("CA1699");
//		command.setDate("2014-10-30");
//		command.setClassCode("E");
//		//command.setPolicyId("52a04a9db4404489a0b8a7813c19a174");
//		command.setPolicyId("878c30ab55d64531b240569b882625b7");
//		
//			List<FlightPassangerDto> passangers =new ArrayList<FlightPassangerDto>();
//			
//				FlightPassangerDto psg=new FlightPassangerDto();
//				
//				psg.setPsgNo(0);
//				psg.setName("谭登");
//				psg.setPassangerType("ADT");
//				psg.setIdentityType("NI");
//				psg.setCardType("NI");
//				psg.setCardNo("421081198612121111");
//				//psg.setBirthday("2004-08-08");
//				psg.setCarrierPsgNo(0);
//				psg.setInsueSum(0);
//				psg.setInsueFee(0.00);
//				psg.setMobile("13711111119");
//			passangers.add(psg);
//			
////			FlightPassangerDto psg2=new FlightPassangerDto();
////			
////			psg2.setPsgNo(0);
////			psg2.setName("章卡");
////			psg2.setPassangerType("ADT");
////			psg2.setIdentityType("NI");
////			psg2.setCardType("NI");
////			psg2.setCardNo("421081198712121112");
////			//psg.setBirthday("2004-08-08");
////			psg2.setCarrierPsgNo(0);
////			psg2.setInsueSum(0);
////			psg2.setInsueFee(0.00);
////			psg2.setMobile("13711111114");
////			passangers.add(psg2);
//			
//		command.setPassangers(passangers);
//			
//			UserDTO userDTO = new UserDTO();
//			//userDTO.setId("ca90ec61ab764f1d99a22182e493c17e");
//			userDTO.setId("1");
//			userDTO.setMobile("13819457136");
//		command.setLinker(userDTO);
//		
//		command.setPayType(1);//1 支付宝
//		
//		//System.out.println(JSON.toJSONString(command));
//			
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPOrderCreate", "192.168.1.1",UUID.randomUUID().toString(), command,"v1.0");
//		
//		System.out.println(JSON.toJSONString(request));
//		//	发送请求
//		JPCreateOrderResponse response = client.send(request, JPCreateOrderResponse.class);
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
//		HslApiClient client = new HslApiClient(httpUrl,"weixin","c196266f837d14e0b693f961bee37b66");
//		
//		//	创建要发送的业务内容
//		JPCancelTicketCommand command = new JPCancelTicketCommand();
//		command.setOrderId("38e63d21-1f07-4faf-9e51-28e66e8d7177");
//		command.setTicketNumbers("");
//		
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPCancelOrderTicket", "192.168.1.1",UUID.randomUUID().toString(), command,"v1.0");
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
//	 * 订单查询
//	 */
//	@Test
//	public void testJPOrderQuery(){
//		//创建api客户端类，可复用
//		HslApiClient client = new HslApiClient(httpUrl,"weixin","c196266f837d14e0b693f961bee37b66");
//		
//		//	创建要发送的业务内容
//		JPOrderQO qo = new JPOrderQO();
//		qo.setUserId("ca90ec61ab764f1d99a22182e493c17e");
//		qo.setDealerOrderCode("A903101801000000");
//		//qo.setDetail(true);
//		qo.setPageNo(1);
//		qo.setPageSize(10);
//				
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPOrderQuery", "192.168.1.1",UUID.randomUUID().toString(), qo,"v1.0");
//		
//		//	发送请求
//		JPQueryOrderResponse response = client.send(request, JPQueryOrderResponse.class);
//		
//		//	查看返回内容
//		System.out.println("response.getResult()=========="+response.getResult());
//		System.out.println("response==============="+JSON.toJSONString(response.getResult()));
//		/*JPQueryOrderResponse response = new JPQueryOrderResponse();
//		response.setMessage("成功");
//		response.setResult("1");
//		response.setTotalCount(1);
//			
//			List<JPOrderDTO> jpOrders = new ArrayList<JPOrderDTO>();
//			JPOrderDTO jpOrder = new JPOrderDTO();
//			//jpOrder.setOrderCode("");
//			jpOrder.setStatus(1);
//				FlightDTO flightDTO = new FlightDTO();
//				flightDTO.setStartCity("北京");
//				flightDTO.setEndCity("大连");
//				flightDTO.setAirCompName("国际航空");
//				flightDTO.setFlightNo("CA1699");
//				flightDTO.setStartDate("2014-08-18");
//				flightDTO.setStartTime("06:35");
//				flightDTO.setStartPortName("首都机场");
//				flightDTO.setEndPortName("大连周水子机场");
//				flightDTO.setEndDate("2014-08-18");
//				flightDTO.setEndTime("07:50");
//				flightDTO.setFlyTime(130);
//				flightDTO.setClassCode("Y");
//				flightDTO.setMileage(810);
//			jpOrder.setFlightDTO(flightDTO);
//			jpOrder.setPayAmount(100.00);
//			jpOrder.setDealerOrderCode("ebb140612190512843");
//			jpOrder.setAgencyName(jpOrder.getAgencyName());
//			
//			//jpOrder.setCreateDate(new Date());
//			//jpOrder.setPnr("pnr");
//				List<FlightPassangerDto> flightPassangerDtoList = new ArrayList<FlightPassangerDto>();
//					FlightPassangerDto flightPassangerDto1 = new FlightPassangerDto();
//					flightPassangerDto1.setName("零零七");
//					flightPassangerDto1.setPassangerType("ADT");
//					flightPassangerDto1.setCardNo("123456789012345678");
//						TicketDto ticketDto1 = new TicketDto();
//						ticketDto1.setTicketNo("781-2475972229");
//					flightPassangerDto1.setTicketDto(ticketDto1);
//					
//				flightPassangerDtoList.add(flightPassangerDto1);
//				
//					FlightPassangerDto flightPassangerDto2 = new FlightPassangerDto();
//					flightPassangerDto2.setName("凌玲琪");
//					flightPassangerDto2.setPassangerType("ADT");
//					flightPassangerDto2.setCardNo("123456789012345678");
//					TicketDto ticketDto2 = new TicketDto();
//					ticketDto2.setTicketNo("781-2475972229");
//					flightPassangerDto2.setTicketDto(ticketDto2);
//					
//				flightPassangerDtoList.add(flightPassangerDto2);
//			jpOrder.setFlightPassangerDtoList(flightPassangerDtoList);
//			//jpOrder.setSupplierPayType("支付宝");
//		jpOrders.add(jpOrder);
//		response.setJpOrders(jpOrders);*/
//				
//		
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
//	@Test
//	public void testGetCityAirCode(){
//		//		创建api客户端类，可复用
//		HslApiClient client = new HslApiClient(httpUrl,"weixin","c196266f837d14e0b693f961bee37b66");
//		JPAirCodeQO cityQuery = new JPAirCodeQO();
//		cityQuery.setFromClientKey("F1001");
//		//	创建要发送的请求对象
//		ApiRequest request = new ApiRequest("JPQueryCityAirCode", "192.168.1.1",UUID.randomUUID().toString(), cityQuery,"v1.0");
//		System.out.println(JSON.toJSONString(request));
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
//	public void testJsonSort(){
//		List<String> list = new ArrayList<String>();
//		list.add("1");
//		list.add("2");
//		list.add("3");
//		list.add("4");
//		list.add("5");
//		list.add("6");
//		list.add("7");
//		list.add("11");
//		list.add("21");
//		list.add("31");
//		list.add("41");
//		list.add("51");
//		list.add("61");
//		list.add("71");
//for (String integer : list) {
//	System.out.print(integer+"\t");
//}
//		String temp = JSON.toJSONString(list);
//		System.out.println(temp);	
//		List<String> list2  = (List<String>)JSON.parseObject(temp,List.class);
//		
//for (String integer : list2) {
//	System.out.print(integer+"\t");
//}		
//
//	}
//
//}
