//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypeDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
//import slfx.yg.open.inter.YGFlightService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:junit-test-spring-service.xml" })
//public class TestGetRefundActionType {
//	
//	@Autowired
//	private YGFlightService ygFlightService;
//	
//	@Test
//	public void test(){
//			
//		YGRefundActionTypesDTO dto = ygFlightService.getRefundActionType("008");
//		
//		System.out.println(dto.getErrorCode());
//		System.out.println(dto.getErrorMsg());
//		for(YGRefundActionTypeDTO t : dto.getActionTypeList()){
//			System.out.println(t.getActionTypeCode());
//			System.out.println(t.getActionTypeTxt());
//			System.out.println("------------------");
//		}
//		
//	}
//	
//}
