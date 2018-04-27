//import java.util.HashMap;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.pojo.dto.flight.SlfxFlightClassDTO;
//import slfx.jp.pojo.dto.supplier.abe.ABEPatFlightDTO;
//import slfx.jp.pojo.dto.supplier.yg.QueryWebFlightsDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
//import slfx.jp.spi.qo.client.PatFlightQO;
//import slfx.jp.spi.qo.client.QueryWebFlightsQO;
//import slfx.yg.open.inter.YGFlightService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:junit-test-spring-service.xml" })
//public class TestPatFlight {
//	
//	@Autowired
//	private YGFlightService ygFlightService;
//	
//	@Test
//	public void test(){
//		
//		// 航班查询
//				QueryWebFlightsQO qo = new QueryWebFlightsQO();
//				qo.setFrom("PEK");
//				qo.setArrive("DLC");
//				qo.setDateStr("2014-08-30");
//				QueryWebFlightsDTO dto = ygFlightService.queryFlights(qo);
//		
//				
//				YGFlightDTO f = dto.getFlightList().get(0);
//				
//				List<SlfxFlightClassDTO> list = dto.getFlightList().get(0).getFlightClassList();
//				SlfxFlightClassDTO c = list.get(0);
//				System.out.println("验价舱位-------");
//				System.out.println("航班号："+f.getFlightNo()+"舱位代码："+c.getClassCode()+"    舱位类型："+c.getClassType()+"   票面价："+c.getOriginalPrice());
//				System.out.println("--------------");
//				
//				PatFlightQO pfqo = new PatFlightQO(); 
//				pfqo.setCarrier(f.getCarrier());
//				pfqo.setClassCode(c.getClassCode());
//				pfqo.setEndPort(f.getEndPort());
//				pfqo.setFlightNo(f.getFlightNo());
//				pfqo.setStartDate(f.getStartDate());
//				pfqo.setStartPort(f.getStartPort());
//				pfqo.setStartTime(f.getStartTime());
//				
//				HashMap<String, ABEPatFlightDTO> map = ygFlightService.patByFlights(pfqo);
//				
//				ABEPatFlightDTO adult = map.get("ADT");
//				Double facePar = adult.getFacePar();
//				System.out.println("facePar===="+facePar);
////				Set<String> set = map.keySet();
////				Iterator it = set.iterator();
////				while(it.hasNext()){
////					String s = (String)it.next();
////					map.get(s);
////					System.out.println(map.get(s).getFacePar());
////				}
//	}
//	
//}
