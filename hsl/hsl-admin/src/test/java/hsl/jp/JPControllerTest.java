//package hsl.jp;
//
//import static org.junit.Assert.*;
//import hsl.admin.common.ArraysUtil;
//import hsl.pojo.dto.jp.JPOrderDTO;
//import hsl.spi.inter.api.jp.JPService;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
//
//@SuppressWarnings("unused")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
//public class JPControllerTest {
//	@Autowired
//	JPService jpService;
//	
//	//测试退废票
//	@Test
//	public void testCancelTicket() {
//		JPCancelTicketCommand command = new JPCancelTicketCommand();
//		command.setOrderId("123123");//商城订单号
//		command.setTicketNumbers(ArraysUtil.toStringWithSlice(new String[]{"13123,123113"}, ","));//把数组转化成以“，”号分隔的字符串
//		command.setName("谭大湿");//后台登录名
//		
//		/**
//		 * 退废种类：退票（T），废票（F）
//		 */
//		command.setRefundType("T");
//		command.setBackPolicy("退废政策");//退废政策
//		command.setBackAmount("1200");//退废金额
//		command.setCancelReason("退废原因");//退废原因
//		JPOrderDTO jpOrderDTO = jpService.cancelTicket(command);
//	}
//}
