//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.pojo.dto.flight.FlightPolicyDTO;
//import slfx.jp.spi.qo.client.PolicyByPnrQo;
//import slfx.yg.open.inter.YGFlightService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/junit-test-spring-service.xml" })
//public class TestPolicyByPnr {
//	
//	@Resource
//	private YGFlightService ygFlightService;
//	
//	@Test
//	public void test(){
//		Date departureDate = new Date();
//		Date arrivalDate = new Date(departureDate.getTime()+1000*60*5);
//		//航班号、仓位、出发日期、到达日期、起飞机场代码、到达机场代码、航司编码
//		PolicyByPnrQo qo = new PolicyByPnrQo("CA8902","H",departureDate,arrivalDate,"PEK","DLC","CA");
//		//如果还要设置其他信息请在qo添加
//		
//		List<FlightPolicyDTO> policys = ygFlightService.queryPolicyByPNR(qo);
//		
//		for (FlightPolicyDTO flightPolicyDTO : policys) {
//			System.out.println(flightPolicyDTO);
//		}
//	
//	}
//	
//}
