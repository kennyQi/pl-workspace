//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderStatusDTO;
//import slfx.yg.open.inter.YGFlightService;
//
//import com.alibaba.fastjson.JSON;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:junit-test-spring-service.xml" })
//public class PlatformQueryOrderStatusServiceTest {
//	
//	@Autowired
//	YGFlightService ygFlightService;
//
//	@Test
//	public void testQueryOrderStatus() {
//		YGQueryOrderStatusDTO dto = ygFlightService.queryOrderStatus("4091617286101");
//		System.out.println("================="+JSON.toJSONString(dto));
//	}
//	
//	@Test
//	public void testQueryOrder() {
//		YGQueryOrderDTO dto = ygFlightService.queryOrder("4101420126101");
//		System.out.println("================="+JSON.toJSONString(dto));
//	}
//
//}
