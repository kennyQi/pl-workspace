//package service.test;
//
//import hsl.api.base.ApiResponse;
//import hsl.api.base.HslApiClient;
//import hsl.pojo.command.MPOrderCreateCommand;
//import hsl.pojo.dto.mp.MPSimpleDTO;
//import hsl.pojo.dto.mp.PolicyDTO;
//import hsl.pojo.dto.user.UserBaseInfoDTO;
//import hsl.pojo.dto.user.UserContactInfoDTO;
//import hsl.pojo.dto.user.UserDTO;
//import hsl.pojo.exception.MPException;
//import hsl.pojo.qo.mp.HslMPDatePriceQO;
//import hsl.pojo.qo.mp.HslMPPolicyQO;
//import hsl.pojo.qo.mp.HslMPScenicSpotQO;
//import hsl.pojo.qo.mp.HslRankListQO;
//import hsl.pojo.qo.mp.HslUserClickRecordQO;
//import hsl.spi.inter.mp.MPOrderService;
//import hsl.spi.inter.mp.MPScenicSpotService;
//import hsl.spi.inter.user.UserService;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//
//
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class TestMPOrderService {
//	
//	@Resource
//	private UserService memberService;
//	
//	@Resource
//	private MPOrderService orderService;
//	
//	@Resource
//	private MPScenicSpotService scenicSpotService;
//	
//	private HslApiClient client;
//	
//	@Before
//	public void before(){
//		client = new HslApiClient("http://127.0.0.1:8080/hsl-api/api","weixin", "c196266f837d14e0b693f961bee37b66");
//		//HslApiClient client = new HslApiClient("http://192.168.10.21:8080/hsl-api/api","wx", "ghfdkgjs");
//	}
//
//	//测试浏览记录的查询
////	@Test
//	public void testRecordQO(){
//		Assert.assertNotNull(memberService);
//		System.out.println("浏览记录service");
//		HslUserClickRecordQO ucrq = new HslUserClickRecordQO();
//		ucrq.setPageSize(10);
//		ucrq.setUserId("1");//这里要替换为userid
//		List<MPSimpleDTO> record;
//		record = memberService.queryUserClickRecord(ucrq);
//		System.out.println("浏览记录的数量"+record.size());
////		System.out.println(record.get(0).getScenicSpotName());
//		Assert.assertTrue(record.size()>0);
//	}
//	
//	//测试日期价格查询
////	@Test
//	public void testDatePrice() throws MPException{
//		//价格日历查询
////		MPDatePriceQO datePriceQO=new MPDatePriceQO();
////		datePriceQO.setPolicyId("86921");
////		ApiRequest datePriceRequest=new ApiRequest("MPQueryDatePrice", "127.0.0.1", UUID.randomUUID().toString(), datePriceQO, "1.0");
////		MPQueryDatePriceResponse datePriceResponse=client.send(datePriceRequest, MPQueryDatePriceResponse.class);
////		System.out.println(JSON.toJSONString(datePriceResponse));
//		HslMPDatePriceQO mpDatePriceQO = new HslMPDatePriceQO();
//		mpDatePriceQO.setPolicyId("86921");
//		Map map = scenicSpotService.queryDatePrice(mpDatePriceQO);
//		System.out.println(map.get("count").toString());
//		System.out.println(JSON.toJSONString(map));
//	}
//	
//	//测试下订单
////	@Test
//	public void testCreateOrder(){
//		Assert.assertNotNull(orderService);
//		System.out.println("开始测试下订单");
//		MPOrderCreateCommand mpOrderCreateCommand = new MPOrderCreateCommand();
//		mpOrderCreateCommand.setPrice(800d);
//		mpOrderCreateCommand.setPolicyId("86921");
//		mpOrderCreateCommand.setNumber(20);
//		mpOrderCreateCommand.setTravelDate("2014-09-23");
//		mpOrderCreateCommand.setBookManIP("127.0.0.1");
//		
//		//模拟下单人信息
//		UserDTO orderUserInfo = new UserDTO();
//		orderUserInfo.setId("11253");
//		orderUserInfo.setMobile("18667916501");
//		UserBaseInfoDTO baseInfo = new UserBaseInfoDTO();
//		baseInfo.setName("牛");
//		UserContactInfoDTO contactInfo = new UserContactInfoDTO();
//		contactInfo.setMobile("18667916501");
//		orderUserInfo.setBaseInfo(baseInfo);
//		orderUserInfo.setContactInfo(contactInfo);
//		//模拟取票人信息
//		UserDTO takeTicketUserInfo = new UserDTO();
//		takeTicketUserInfo.setMobile("18667916501");
//		UserBaseInfoDTO baseInfo2 = new UserBaseInfoDTO();
//		baseInfo2.setName("牛");
//		UserContactInfoDTO contactInfo2 = new UserContactInfoDTO();
//		contactInfo2.setMobile("18667916501");
//		takeTicketUserInfo.setBaseInfo(baseInfo2);
//		takeTicketUserInfo.setContactInfo(contactInfo2);
//		
//		mpOrderCreateCommand.setOrderUserInfo(orderUserInfo);
//		mpOrderCreateCommand.setTakeTicketUserInfo(takeTicketUserInfo);
//		Map orderMap = orderService.createMPOrder(mpOrderCreateCommand);
//		String code = orderMap.get("code").toString();
//		if(ApiResponse.RESULT_CODE_OK.equals(code)){
//			System.out.println("下单成功");
//		}else{
//			System.out.println("下单失败");
//		}
//	}
//	
//	//测试景区排行
////	@Test
//	public void testRate(){
//		HslRankListQO rankListQO = new HslRankListQO();
//		List<MPSimpleDTO> rankList = scenicSpotService.queryScenicSpotClickRate(rankListQO);
//		Assert.assertTrue(rankList.size()>0);
//	}
//	
//	/**
//	 * 测试单政策查询
//	 */
////	@Test
//	public void testPolicyQuery(){
//		HslMPPolicyQO hslMPPolicyQO = new HslMPPolicyQO();
//		hslMPPolicyQO.setPolicyId("86921");
//		Map policysMap = scenicSpotService.queryScenicPolicy(hslMPPolicyQO);
//		List<PolicyDTO> dtos = (List<PolicyDTO>)policysMap.get("dtos");
//		if(dtos!=null&&dtos.size()>0){
//			PolicyDTO policy = dtos.get(0);
//			System.out.println("policy name : "+policy.getName());
//		}else{
//			System.out.println("政策没有找到");
//		}
//		
//	}
//	
//	/**
//	 * 热门景点查询
//	 * @throws MPException 
//	 */
////	@Test
//	public void testHotScenicSpotQuery() throws MPException{
//		//热门景点查询
//		HslMPScenicSpotQO hotMpScenicSpotsQO = new HslMPScenicSpotQO();
//		hotMpScenicSpotsQO.setHot(true);
//		hotMpScenicSpotsQO.setPageNo(1);
//		hotMpScenicSpotsQO.setPageSize(6);
//		hotMpScenicSpotsQO.setIsOpen(true);
//		hotMpScenicSpotsQO.setContent(null);
//		Map hotScenicMap = scenicSpotService.queryScenicSpot(hotMpScenicSpotsQO);
//	}
//}
