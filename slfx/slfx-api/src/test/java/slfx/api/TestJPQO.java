//package slfx.api;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.spi.inter.CityAirCodeService;
//import slfx.jp.spi.inter.FlightPolicyService;
//import slfx.jp.spi.inter.JPPlatformOrderService;
//import slfx.jp.spi.inter.PlatformGetRefundActionTypeService;
//import slfx.jp.spi.inter.WebFlightService;
//import slfx.xl.pojo.dto.line.LineDTO;
//import slfx.xl.pojo.qo.LineQO;
//import slfx.xl.spi.inter.LineService;
//
///**
// * 商城调用平台QO接口单元测试
// * 测试人员使用
// * @author tandeng
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-xl-test.xml" })
//public class TestJPQO {
//	
//	@Autowired
//	private WebFlightService webFlightService;
//	
//	@Autowired
//	private FlightPolicyService flightPolicyService;
//	
//	@Autowired
//	private JPPlatformOrderService jpPlatformOrderService;
//	
//	@Autowired
//	private PlatformGetRefundActionTypeService jpPlatformGetRefundActionTypeService;
//	
//	@Autowired
//	private CityAirCodeService cityAirCodeService;
//	
//	@Autowired
//	private LineService lineService;
//	
////	/**
////	 * 航班查询
////	 */
////	@Test
////	public void testJPFlightQO(){	
////		//航班查询
////		JPFlightQO qo = new JPFlightQO();
////		qo.setFrom("PEK");
////		qo.setArrive("DLC");
////		qo.setDate("2014-08-13");
////		qo.setFlightNo("CZ6132");
////
////		PlatformQueryWebFlightsDTO dto = webFlightService.queryFlights(qo);
////		System.out.println("END=====END"+JSON.toJSONString(dto));
////					
////	}
////	
////	/**
////	 * 订单查询
////	 */
////	@SuppressWarnings("unchecked")
////	@Test
////	public void testJPOrderQO(){
////		JPOrderQO jpOrderQO = new JPOrderQO();
////		//jpOrderQO.setUserId("");
////		jpOrderQO.setOrderId("38e63d21-1f07-4faf-9e51-28e66e8d7177");
////		//jpOrderQO.setDetail(true);
////		jpOrderQO.setPageNo(1);
////		jpOrderQO.setPageSize(10);
////		Pagination pagination = jpPlatformOrderService.shopQueryOrderList(jpOrderQO);
////		if(pagination != null && pagination.getList() != null 
////				&& pagination.getList().size() > 0){
////			List<JPOrderDTO> jPOrderDTOList = (List<JPOrderDTO>) pagination.getList();
////			for (JPOrderDTO jpOrderDTO : jPOrderDTOList) {
////				//System.out.println(JSON.toJSONString(jpOrderDTO));
////				System.out.println((jpOrderDTO));
////				
////			}
////		}
////		//System.out.println("pagination==============="+JSON.toJSONString(pagination));
////	}
////	
////	/**
////	 * 政策查询
////	 */
////	@Test
////	public void testJPPolicyQO(){
////		
////		Date departureDate = new Date();
////		
////		JPPolicyQO jpPolicyQO = new JPPolicyQO();
////		jpPolicyQO.setFlightNo("CZ6128");
////		jpPolicyQO.setClassCode("Y");	
////		jpPolicyQO.setDepartDate(DateUtil.formatDateTime(departureDate,"yyyyMMdd"));	
////		
////		
////		//航班号、仓位、出发日期、到达日期、起飞机场代码、到达机场代码、航司编码
////		//PolicyByPnrQo qo = new PolicyByPnrQo("CA8902","H",departureDate,arrivalDate,"PEK","DLC","CA");
////		
////		//如果还要设置其他信息请在qo添加
////		
////		FlightPolicyDTO policys = flightPolicyService.queryPlatformPolicy(jpPolicyQO);
////		//将平台dto转化成商城dto
////		
////		System.out.println("1111111111111111111111111111"+JSON.toJSONString(policys));
////	}
////	
////	@Test
////	public void testGetRefundActionType() {
////		
////		YGRefundActionTypesDTO dto = jpPlatformGetRefundActionTypeService.getRefundActionType("001");
////		System.out.println("============"+JSON.toJSONString(dto));
////	}
////	
////	@Test
////	public void testGetCityAirCode() {
////		
////		List<CityAirCodeDTO> cityAirCodeDTOList = cityAirCodeService.queryCityAirCodeList();
////		
////		JPCityAirCodeResponse jpCityAirCodeResponse = null;
////		if(cityAirCodeDTOList != null && cityAirCodeDTOList.size() > 0){
////			jpCityAirCodeResponse = new JPCityAirCodeResponse();
////			jpCityAirCodeResponse.setMessage("成功");
////			jpCityAirCodeResponse.setResult(ApiResponse.RESULT_CODE_OK);
////			jpCityAirCodeResponse.setCityAirCodeList(cityAirCodeDTOList);
////		}else{
////			jpCityAirCodeResponse = new JPCityAirCodeResponse();
////			jpCityAirCodeResponse.setMessage("失败");
////			jpCityAirCodeResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
////			jpCityAirCodeResponse.setCityAirCodeList(null);
////		}
////		
////		
////		System.out.println(JSON.toJSONString(cityAirCodeDTOList));
////	}
////	
////	@Test
////	public void queryLine(){
////		LineQO qo= new LineQO();
////		qo.setNumber("12");
////		//qo.setLineName("123213aaaa");
////		LineDTO line = lineService.queryUnique(qo);
////		System.out.println(line.getId());
////	}
//}
