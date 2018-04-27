//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//
//import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
//import slfx.yg.open.inter.YGFlightService;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/junit-test-spring-service.xml" })
//public class PlatformGetRefundActionTypeServiceTest {
//
//	@Autowired
//	YGFlightService ygFlightService;
//	
//	@Test
//	public void testGetRefundActionType() {
//		YGRefundActionTypesDTO dto = ygFlightService.getRefundActionType("001");
//		System.out.println(JSON.toJSONString(dto));
//	}
//
//}
