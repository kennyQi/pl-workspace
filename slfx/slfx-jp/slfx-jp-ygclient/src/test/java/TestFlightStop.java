//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.pojo.dto.flight.FlightStopInfoDTO;
//import slfx.jp.pojo.dto.supplier.yg.QueryWebFlightsDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
//import slfx.jp.spi.qo.client.QueryWebFlightsQO;
//import slfx.yg.open.inter.YGFlightService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/junit-test-spring-service.xml" })
//public class TestFlightStop {
//
//	@Autowired
//	private YGFlightService ygFlightService;
//
//	@Test
//	public void test() {
//		
//		// 航班查询
//		QueryWebFlightsQO qo = new QueryWebFlightsQO();
//		qo.setFrom("PEK");
//		qo.setArrive("DLC");
//		qo.setDateStr("2014-07-31");
//		QueryWebFlightsDTO dto = ygFlightService.queryFlights(qo);
//
//		YGFlightDTO fdto = dto.getFlightList().get(0);
//		System.out.println(fdto.getStopNum());
//		System.out.println(fdto.getFlightNo());
//		System.out.println(fdto.getStartDate());
//
//		List<FlightStopInfoDTO> list = ygFlightService.queryFlightStop(
//				fdto.getFlightNo(), fdto.getStartDate());
//
//		if (list != null && list.size() > 0) {
//			for (FlightStopInfoDTO o : list) {
//				System.out.println("抵达时间：" + o.getArriveTime() + ">>>>>起飞时间："
//									+ o.getDepartureTime() + ">>>>>机型代码："
//									+ o.getAircraftCode() + ">>>>>机场代码："
//									+ o.getAirportCode());
//			}
//		} else {
//			System.out.println("无经停信息");
//		}
//	}
//}