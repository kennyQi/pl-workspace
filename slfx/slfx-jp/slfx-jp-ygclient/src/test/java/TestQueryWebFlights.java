//import java.util.Iterator;
//import java.util.Set;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.yg.open.inter.YGFlightService;
//import slfx.jp.pojo.dto.supplier.yg.QueryWebFlightsDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
//import slfx.jp.spi.qo.client.QueryWebFlightsQO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:junit-test-spring-service.xml" })
//public class TestQueryWebFlights {
//	
//	@Autowired
//	private YGFlightService ygFlightService;
//	
//	@Test
//	public void test(){
//		
//		//航班查询
//		QueryWebFlightsQO qo = new QueryWebFlightsQO();
////		qo.setFrom("PEK");
//		qo.setFrom("PEK");
////		qo.setArrive("DLC");
//		qo.setArrive("FOC");
//		qo.setDateStr("2014-11-05");
////		QueryWebFlightsDTO dto = ygFlightService.queryFlights(qo);
//		QueryWebFlightsDTO dto = ygFlightService.queryFlightsV2(qo);
//		
//		for(YGFlightDTO fdto : dto.getFlightList()){
//			System.out.println("航班："+fdto.getFlightNo()+"信息-------");
//			System.out.println("航空公司代码："+fdto.getCarrier()+">>>>>>>航空公司名称："+fdto.getAirCompName()+">>>>>>机型代码："+fdto.getAircraftCode()+">>>>>机型名称："+fdto.getAircraftName());
//			System.out.println("出发日期："+fdto.getStartDate()+">>>>>出发时间："+fdto.getStartTime()+">>>>>出发港口："+fdto.getStartPort()+">>>>>出发航站楼："+fdto.getStartTerminal()+">>>>>出发城市代码："+fdto.getStartCityCode()+">>>>>出发港口名称"+fdto.getStartPortName());
//			System.out.println("到达日期："+fdto.getEndDate()+">>>>>到达时间："+fdto.getEndTime()+">>>>>到达航站楼："+fdto.getEndTerminal()+">>>>>到达城市代码："+fdto.getEndCityCode()+">>>>>达到港口："+fdto.getEndPort()+">>>>>到达港口名称："+fdto.getEndPortName());
//			System.out.println("Y舱价格："+fdto.getYPrice()+">>>>>燃油费："+fdto.getFuelSurTax()+">>>>>机场建设费："+fdto.getAirportTax()+">>>>>飞行时间："+fdto.getFlyTime()+">>>>>经停次数："+fdto.getStopNum()+">>>>>里程："+fdto.getMileage());
//			System.out.println("经停次数："+fdto.getStopNum()+">>>>>有无餐食："+fdto.getIsFood());
//			System.out.println("航班："+fdto.getFlightNo()+"信息结束------");
//		}
//		
//		Set<String> set = dto.getTgNoteMap().keySet();
//		Iterator<String> it = set.iterator();
//		while(it.hasNext()){
//			String s = it.next();
//			System.out.println(dto.getTgNoteMap().get(s));
//		}
//		
//	}
//	
//}
