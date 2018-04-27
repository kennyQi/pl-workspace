//package slfx.api;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.api.v1.request.command.jp.APIJPOrderCreateCommand;
//import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;
//import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
//import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
//import slfx.jp.pojo.dto.flight.FlightPolicyDTO;
//import slfx.jp.pojo.dto.order.JPOrderDTO;
//import slfx.jp.spi.inter.JPPlatformOrderService;
//
//import com.alibaba.fastjson.JSON;
//
//
///**
// * 商城调用平台Command接口单元测试
// * 测试人员使用
// * @author tandeng
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp-test.xml" })
//public class TestJPCommand {
//
//	@Autowired
//	private JPPlatformOrderService jpPlatformOrderService;
//
//	/**
//	 * 商城向平台下单
//	 */
//	@Test
//	public void testJPOrderCreateCommand(){
//		
//		//构造商城向平台下单传入参数
//		APIJPOrderCreateCommand jpOrderCreateCommand=new  APIJPOrderCreateCommand();
//		jpOrderCreateCommand.setDealerOrderId("");
//		jpOrderCreateCommand.setBalanceMoney(2950.0);
//		jpOrderCreateCommand.setFlightNo("CZ6128");
//		jpOrderCreateCommand.setStartDateStr("2014-08-04");
//		jpOrderCreateCommand.setClassCode("F");
//		jpOrderCreateCommand.setIsDomc("");
//		jpOrderCreateCommand.setClassPrice(0.0);
//		jpOrderCreateCommand.setTicketLimitDate("");
//		jpOrderCreateCommand.setTicketLimitTime("");
//		jpOrderCreateCommand.setBigPNR("BJS280");
//		jpOrderCreateCommand.setPolicyId("965a3071-c91e-4f73-be92-6b397103d07e");
//		jpOrderCreateCommand.setPlatCode("001");
//		jpOrderCreateCommand.setPlatformType("Y");
//		jpOrderCreateCommand.setAccountLevel("10");
//		
//		List<FlightPassengerDTO> passangers =new ArrayList<FlightPassengerDTO>();
//		
//		FlightPassengerDTO psg=new FlightPassengerDTO();
//		psg.setPsgNo(0);
//		psg.setPassangerType("ADT");
//		psg.setName("任风");
//		//psg.setCountry("");
//		psg.setIdentityType("NI");
//		psg.setCardType("NI");
//		psg.setCardNo("123");
//		//psg.setBirthDay("");
//		//psg.setInsueFee(0.0);
//		//psg.setInsueSum(0);
//		
//		FlightPolicyDTO flightPolicyDTO =new FlightPolicyDTO ();
//		flightPolicyDTO.setPolicyId("965a3071-c91e-4f73-be92-6b397103d07e");
//		flightPolicyDTO.setRebate(0);
//		flightPolicyDTO.setTicketPrice(0.00);
//		flightPolicyDTO.setFuelSurTax(50.0);
//		flightPolicyDTO.setAirportTax(60.0);
//		flightPolicyDTO.setAutoTicket("");
//		flightPolicyDTO.setReceipt("");
//		flightPolicyDTO.setPaymentType("");
//		flightPolicyDTO.setRemark("");
//		flightPolicyDTO.setTktWorktime("");
//		flightPolicyDTO.setFare(0.0);
//		
//		psg.setFlightPolicyDTO(flightPolicyDTO);
//		passangers.add(psg);
//		
//		
//		jpOrderCreateCommand.setPassangers(passangers);
//		
//		JPOrderDTO platformOrderDTO=jpPlatformOrderService.shopCreateOrder(jpOrderCreateCommand);
//		
//		//平台dto转成商城dto
//		System.out.println(JSON.toJSONString(platformOrderDTO));
//	}
//	
//	/**
//	 * 商城向平台取消订单
//	 */
//	@Test
//	public void testJPCancelTicketCommand(){
//		JPCancelTicketCommand jpCancelTicketCommand = new JPCancelTicketCommand();
//		jpCancelTicketCommand.setOrderId("38e63d21-1f07-4faf-9e51-28e66e8d7177");
//		jpCancelTicketCommand.setTicketNumbers("");
//		
//		boolean bool = jpPlatformOrderService.shopCancelOrder(jpCancelTicketCommand);
//		System.out.println("bool=========="+bool);
//	}
//	
//	/**
//	 * 商城向平台请求出票
//	 */
//	@Test
//	public void testJPAskOrderTicketCommand(){
//		JPAskOrderTicketCommand jpAskOrderTicketCommand = new JPAskOrderTicketCommand();
//		jpAskOrderTicketCommand.setOrderId("11111111111"); //平台订单号，即商城传过来的经销商订单号
//		//jpAskOrderTicketCommand.setPayOrderId("");
//		//jpAskOrderTicketCommand.setPayWay(1);//支付宝
//		
//		List<FlightPassengerDTO> psgs = jpPlatformOrderService.shopAskOrderTicket(jpAskOrderTicketCommand);
//		System.out.println("---------------"+JSON.toJSONString(psgs));
//	}
//
//}
