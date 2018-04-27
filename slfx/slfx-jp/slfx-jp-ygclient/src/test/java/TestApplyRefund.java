//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSONObject;
//
//import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypeDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
//import slfx.jp.spi.command.client.YGApplyRefundCommand;
//import slfx.yg.open.inter.YGFlightService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:junit-test-spring-service.xml" })
//public class TestApplyRefund {
//	
//	@Autowired
//	private YGFlightService ygFlightService;
//	
//	@Test
//	public void test(){
//		
//		YGRefundActionTypesDTO dto = ygFlightService.getRefundActionType("002");
//		System.out.println("--------"+JSONObject.toJSON(dto));
////		YGRefundActionTypeDTO  typeDto = dto.getActionTypeList().get(0);
////		
////		YGApplyRefundCommand command = new YGApplyRefundCommand();
////		command.setOrderNo("");
////		command.setActionType(typeDto.getActionTypeCode());
////		ygFlightServiceImpl.applyRefund(command);
//		
//		
//	}
//	@Test
//	public void test2(){
//		
//		//YGRefundActionTypesDTO dto = ygFlightService.getRefundActionType("008");
//		
//		//YGRefundActionTypeDTO  typeDto = dto.getActionTypeList().get(0);
//		
//		YGApplyRefundCommand command = new YGApplyRefundCommand();
//		command.setOrderNo("4101310376106");
//		command.setActionType("1");
//		command.setTicketNos("7312391511226");
//		command.setReason("调试废票");
//		command.setRefundType("F");
//		command.setNoticeUrl("http://183.129.207.6:8280/slfx/api/backOrDiscardTicket/notify");
//		
//		ygFlightService.applyRefund(command);
//		
//		
//	}
//}
