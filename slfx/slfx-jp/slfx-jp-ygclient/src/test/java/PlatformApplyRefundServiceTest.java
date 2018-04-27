//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.pojo.dto.supplier.yg.YGApplyRefundDTO;
//import slfx.jp.spi.command.client.YGApplyRefundCommand;
//import slfx.yg.open.inter.YGFlightService;
//
//import com.alibaba.fastjson.JSON;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/spring-service.xml" })
//public class PlatformApplyRefundServiceTest {
//
//	@Autowired
//	private YGFlightService ygFlightService;
//	
//	@Test
//	public void testApplyRefund() {
//		YGApplyRefundCommand command = new YGApplyRefundCommand();
//		command.setOrderNo("123456789");
//		command.setOrderNo("123123123|456456456");
//		YGApplyRefundDTO dto = ygFlightService.applyRefund(command);
//		System.out.println(JSON.toJSONString(dto));
//	}
//
//}
