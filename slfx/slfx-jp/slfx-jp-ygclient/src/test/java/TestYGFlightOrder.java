//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.pojo.dto.supplier.yg.YGFlightOrderDTO;
//import slfx.jp.spi.command.client.YGFlightCommand;
//import slfx.jp.spi.command.client.YGOrderCommand;
//import slfx.jp.spi.command.client.YGPassengerCommand;
//import slfx.yg.open.inter.YGFlightService;
///**
// * 易购下单 单元测试
// * @author renfeng
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:junit-test-spring-service.xml" })
//public class TestYGFlightOrder {
//	
//	@Autowired
//	private YGFlightService ygFlightService;
//	@Test
//	public void orderByPnr(){
//		YGOrderCommand yGOrderCommand=new YGOrderCommand();
//		yGOrderCommand.setPnr("JYP6DJ");
//		yGOrderCommand.setPnrText(""
//								+"<![CDATA[ 1.李俊利 JYP6DJ "
//								+"\n"
//								+" 2. CZ6132 M TH31JUL PEKDLC KK1 0025 0150 E T2-\n" 
//								+" 3.BJS/T BJS/T 010-84555999/FEAS /HEJIE ABCDEFG\n" 
//								+" 4.15811040472\n" 
//								+" 5.BY OPT WFWC 2014/07/22 1436A\n" 
//								+" 6.TL/2355/30JUL/BJS280\n" 
//								+" 7.SSR FOID CZ HK1 NI123/P1\n" 
//								+" 8.SSR ADTK 1E BY BJS22JUL14/1536 OR CXL CZ BOOKING\n" 
//								+" 9.OSI YY CTCT15811040472\n" 
//								+"10.RMK AO/ABE/KH-/OP-NN /DT-20140722143609\n" 
//								+"11.RMK CA/NCDPKY\n" 
//								+"12.BJS280"
//								+"\n"
//								+"]]>"
//									);
//		yGOrderCommand.setPataText("![CDATA[01 M FARE:CNY570.00 TAX:CNY50.00 YQ:CNY60.00 TOTAL:680.00 ]]");
//		yGOrderCommand.setTicketType("YeeGo");
//		yGOrderCommand.setBigPNR("NCDPKY");
//		yGOrderCommand.setPolicyId("cc427377-f0bb-414e-b9bd-5532d2fa1b3d");
//		yGOrderCommand.setPolicyId("026f0cfd-8b25-4d26-b19b-645ebcccf5ae");
//		yGOrderCommand.setPlatCode("001");
//		
//		yGOrderCommand.setPlatformType("Y");
//		
//		//是否vip 可以不传
//		yGOrderCommand.setVip("10");
//		
//		yGOrderCommand.setAccountLevel("10");
//		
//		yGOrderCommand.setBalanceMoney(1321.92);
//		
//		
//		yGOrderCommand.setRemark("无");
//		yGOrderCommand.setWorkTime("08:00-22:00");
//		yGOrderCommand.setRefundWorkTime("08:00-21:30");
//		yGOrderCommand.setWastWorkTime("2014-07-22 21:30止");
//		yGOrderCommand.setForceDelete("");
//		yGOrderCommand.setBookingOffice("");
//		
//		
//		YGFlightCommand yGFlightCommand=new YGFlightCommand();
//		yGFlightCommand.setCarrier("CZ");	
//		yGFlightCommand.setFlightNo("CZ6132");
//		yGFlightCommand.setBoardPoint("PEK"); //离港机场代码
//		yGFlightCommand.setOffPoint("DLC"); //到港机场代码
//		yGFlightCommand.setClassCode("M");
//		yGFlightCommand.setDepartureDate("2014-07-31"); //离港日期
//		
//		yGOrderCommand.setFlight(yGFlightCommand);
//		
//		List<YGPassengerCommand> passengers = new ArrayList<YGPassengerCommand>();
//		
//		YGPassengerCommand yGPassengerCommand =new YGPassengerCommand();
//		yGPassengerCommand.setName("李俊利");
//		yGPassengerCommand.setPsgType("ADT");
//		yGPassengerCommand.setIdentityType("NI");
//		yGPassengerCommand.setCardType("NI");
//		yGPassengerCommand.setCardNo("123");
//		yGPassengerCommand.setMobilePhone("");
//		yGPassengerCommand.setInsueSum(0);
//		yGPassengerCommand.setFare(0.0);
//		yGPassengerCommand.setSalePrice(0.0);
//		yGPassengerCommand.setTaxAmount(0.0);
//
//		
//		passengers.add(yGPassengerCommand);
//		yGOrderCommand.setPassengers(passengers);
//		
//		
//		YGFlightOrderDTO yGFlightOrderDTO =this.ygFlightService.orderByPnr(yGOrderCommand);
//		
//		System.out.println(yGFlightOrderDTO);
//
//	}
//	
//}
