//package slfx.api;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
//import slfx.jp.spi.inter.JPPlatformOrderService;
//
//@SuppressWarnings("unused")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
//public class JPPlatformOrderServiceTest {
//	
//	@Autowired
//	JPPlatformOrderService jpPlatformOrderService;
//	
//	@Test
//	public void testShopRefundOrder() {
//		JPCancelTicketCommand command = new JPCancelTicketCommand();
//		command.setTicketNumbers("123123,456456");//票号
//		jpPlatformOrderService.shopRefundOrder(command);
//	}
//}
