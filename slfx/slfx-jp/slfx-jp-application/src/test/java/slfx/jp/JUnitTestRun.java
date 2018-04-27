//package slfx.jp;
//
//import java.util.HashMap;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.api.v1.request.qo.jp.JPFlightQO;
//import slfx.jp.pojo.dto.flight.FlightClassPriceDTO;
//import slfx.jp.pojo.dto.flight.PlatformQueryWebFlightsDTO;
//import slfx.jp.spi.inter.WebFlightService;
//import slfx.jp.spi.qo.client.PatFlightQO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp-test.xml" })
//public class JUnitTestRun {
//
//	@Autowired
//	private WebFlightService webFlightService;
//	
//	@Test
//	public void testQueryFlights() {
//		System.out.println("test begin!");
//		long beginTime = System.currentTimeMillis();
//		
//		JPFlightQO qo = new JPFlightQO();
//		PlatformQueryWebFlightsDTO platformQueryWebFlightsDTO = webFlightService.queryFlights(qo);
//		System.out.println(platformQueryWebFlightsDTO);
//		System.out.println("test end! time cost = "
//				+ (System.currentTimeMillis() - beginTime));
//	}
//	
//	@Test
//	public void testPatByFlights(){
//		PatFlightQO qo = new PatFlightQO();
//		HashMap<String, FlightClassPriceDTO> fClassPrice = webFlightService
//				.patByFlights(qo);
//		if (fClassPrice != null) {
//			for (String objKeys : fClassPrice.keySet()) {
//				FlightClassPriceDTO obj = fClassPrice.get(objKeys);
//				System.out.println(obj);
//			}
//		} else {
//			System.out.println("return value is null!");
//		}
//		
//	}
//}
