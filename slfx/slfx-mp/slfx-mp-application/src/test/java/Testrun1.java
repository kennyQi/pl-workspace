//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.mp.app.service.local.DateSalePriceLocalService;
//import slfx.mp.domain.model.platformpolicy.DateSalePrice;
//import slfx.mp.tcclient.tc.dto.jd.PriceCalendarDto;
//import slfx.mp.tcclient.tc.pojo.Response;
//import slfx.mp.tcclient.tc.pojo.ResultPriceCalendar;
//import slfx.mp.tcclient.tc.service.TcClientService;
//
//import com.alibaba.fastjson.JSON;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-mp-test.xml" })
//public class Testrun1 {
//	
//	@Autowired
//	TcClientService service;
//	
//	@Autowired
//	DateSalePriceLocalService dateSalePriceLocalService;
//	
//	@Test
//	public void test2() {
//		List<DateSalePrice> list = dateSalePriceLocalService.getDateSalePrice(
//				"85638", "xxxx");
//		System.out.println(JSON.toJSONString(list, true));
//	}
//	
//	@Test
//	public void test(){
//
////		TcClientService service = new TcClientServiceImpl();
//		// 181582
//		PriceCalendarDto dto = new PriceCalendarDto();
//		dto.setPolicyId(85638);
//		dto.setStartDate("2014-11-10");
////		dto.set
//		Response<ResultPriceCalendar> response = service.getPriceCalendar(dto);
//		System.out.println(JSON.toJSONString(response, true));
//		
////		SceneryPriceDto dto2=new SceneryPriceDto();
////		dto2.setSceneryIds("181578");
////		System.out.println(JSON.toJSONString(service.getSceneryPrice(dto2), true));
////		
//		
//		
//		// Response response = service.getProvinceList(new ProvinceListDto());
//		// ResultProvinceList result=(ResultProvinceList) response.getResult();
//		// System.out.println(JSON.toJSONString(result));
//	}
//}
