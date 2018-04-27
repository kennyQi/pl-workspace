//package slfx.jp;
//
//import hg.common.page.Pagination;
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
//import slfx.api.v1.request.qo.jp.JPOrderQO;
//import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
//import slfx.jp.pojo.dto.order.JPOrderDTO;
//import slfx.jp.spi.inter.JPPlatformOrderService;
//import slfx.jp.spi.qo.admin.PassengerQO;
//import slfx.jp.spi.qo.admin.PlatformOrderQO;
//
///**
// * 商城向平台下单流程 JUnit测试
// * @author renfeng
// *
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp-test.xml" })
//public class JunitPlatformOrderServiceTest {
//	@Autowired
//	private JPPlatformOrderService jpPlatformOrderService;
//	
//	/*@Test
//	public void platformOrder(){
//
//		//构造商城向平台下单传入参数
//		APIJPOrderCreateCommand jPOrderCreateCommand=new  APIJPOrderCreateCommand();
//		jPOrderCreateCommand.setDealerOrderId("");
//		jPOrderCreateCommand.setBalanceMoney(2950.0);
//		jPOrderCreateCommand.setFlightNo("CZ6128");
//		jPOrderCreateCommand.setClassCode("F");
//		jPOrderCreateCommand.setIsDomc("");
//		jPOrderCreateCommand.setClassPrice(0.0);
//		jPOrderCreateCommand.setTicketLimitDate("");
//		jPOrderCreateCommand.setTicketLimitTime("");
//		jPOrderCreateCommand.setBigPNR("BJS280");
//		jPOrderCreateCommand.setPolicyId("965a3071-c91e-4f73-be92-6b397103d07e");
//		jPOrderCreateCommand.setPlatCode("001");
//		jPOrderCreateCommand.setPlatformType("Y");
//		jPOrderCreateCommand.setAccountLevel("10");
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
//		flightPolicyDTO.setTicketPrice(0.0);
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
//		jPOrderCreateCommand.setPassangers(passangers);
//		
//		JPOrderDTO platformOrderDTO=jpPlatformOrderService.shopCreateOrder(jPOrderCreateCommand);
//		
//		
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+platformOrderDTO);
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~调用结束。");
//		
//	}*/
//	
//	@Test
//	public void shopQueryOrderList(){
//	
//		JPOrderQO qo=new JPOrderQO();
//		qo.setOrderId("9b36940db4a34df290821352b5b0221f");
//		//qo.setUserId("谭登");
//		qo.setPageNo(1);
//		qo.setPageSize(10);
//		Pagination p=jpPlatformOrderService.shopQueryOrderList(qo);
//		
//	}
//	
//}
