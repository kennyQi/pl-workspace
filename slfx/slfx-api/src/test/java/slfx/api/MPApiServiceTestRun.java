//package slfx.api;
//
//
//import hg.common.util.DateUtil;
//
//import java.util.ArrayList;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.api.v1.request.command.mp.MPOrderCreateCommand;
//import slfx.api.v1.request.qo.mp.MPDatePriceQO;
//import slfx.api.v1.request.qo.mp.MPOrderQO;
//import slfx.api.v1.request.qo.mp.MPPolicyQO;
//import slfx.api.v1.request.qo.mp.MPScenicSpotsQO;
//import slfx.api.v1.response.mp.MPOrderCreateResponse;
//import slfx.api.v1.response.mp.MPQueryDatePriceResponse;
//import slfx.api.v1.response.mp.MPQueryOrderResponse;
//import slfx.api.v1.response.mp.MPQueryPolicyResponse;
//import slfx.api.v1.response.mp.MPQueryScenicSpotsResponse;
//import slfx.mp.pojo.dto.order.MPOrderUserInfoDTO;
//import slfx.mp.spi.inter.api.ApiMPDatePriceService;
//import slfx.mp.spi.inter.api.ApiMPOrderService;
//import slfx.mp.spi.inter.api.ApiMPPolicyService;
//import slfx.mp.spi.inter.api.ApiMPScenicSpotsService;
//
//import com.alibaba.fastjson.JSON;
//
///**
// * @类功能说明：门票 API SERVICE 单元测试
// * @类修改者：
// * @修改日期：2014-8-7下午2:10:17
// * @修改说明：
// * @公司名称：浙江汇购科技有限公司
// * @作者：zhurz
// * @创建时间：2014-8-7下午2:10:17
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-mp-test.xml" })
//public class MPApiServiceTestRun {
//
//	@Autowired
//	private ApiMPDatePriceService apiMPDatePriceService;
//	@Autowired
//	private ApiMPOrderService apiMPOrderService;
//	@Autowired
//	private ApiMPPolicyService apiMPPolicyService;
//	@Autowired
//	private ApiMPScenicSpotsService apiMPScenicSpotsService;
//
//	/**
//	 * @方法功能说明：测试景区查询
//	 * @修改者名字：zhurz
//	 * @修改时间：2014-8-7下午2:19:33
//	 * @修改内容：
//	 * @参数：
//	 * @return:void
//	 * @throws
//	 */
//	@Test
//	public void testMpQueryScenicSpots(){
//		MPScenicSpotsQO qo = new MPScenicSpotsQO();
////		qo.setImagesFetchAble(true);
////		qo.setTcPolicyNoticeFetchAble(true);
//		qo.setName("杭州");
//		qo.setImagesFetchAble(true);
//		MPQueryScenicSpotsResponse response = apiMPScenicSpotsService.queryScenicSpots(qo);
//		String json = JSON.toJSONString(response, true);
//		System.out.println(json);
//	}
//	
//	/**
//	 * @方法功能说明：测试政策查询
//	 * @修改者名字：zhurz
//	 * @修改时间：2014-8-7下午2:19:58
//	 * @修改内容：
//	 * @参数：
//	 * @return:void
//	 * @throws
//	 */
//	@Test
//	public void testMpQueryPolicy(){
//		MPPolicyQO qo = new MPPolicyQO();
//		qo.setScenicSpotId("225e86af49db4c4ba0a9cc5285bd0298");
//		qo.setPolicyId("19889");
//		MPQueryPolicyResponse response = apiMPPolicyService.apiQueryPolicy(qo);
//		String json = JSON.toJSONString(response, true);
//		System.out.println(json);
//	}
//
//	/**
//	 * @方法功能说明：测试查询价格日历
//	 * @修改者名字：zhurz
//	 * @修改时间：2014-8-7下午2:20:44
//	 * @修改内容：
//	 * @参数：
//	 * @return:void
//	 * @throws
//	 */
//	@Test
//	public void testMpQueryDatePrice(){
//		MPDatePriceQO qo = new MPDatePriceQO();
//		qo.setFromClientKey("0003");
//		qo.setPolicyId("76476");
//		MPQueryDatePriceResponse response = apiMPDatePriceService.apiQueryDateSalePrices(qo);
//		String json = JSON.toJSONString(response, true);
//		System.out.println(json);
//	}
//	
//	/**
//	 * @方法功能说明：测试下单
//	 * @修改者名字：zhurz
//	 * @修改时间：2014-8-7下午2:20:13
//	 * @修改内容：
//	 * @参数：
//	 * @return:void
//	 * @throws
//	 */
//	@Test
//	public void testMpCreateOrder(){
//		MPOrderCreateCommand command = new MPOrderCreateCommand();
//		command.setFromClientKey("0003");;
//		command.setDealerOrderId("TEST_00000002");
//		command.setPrice(50d);
//		command.setPolicyId("19889");
//		command.setNumber(1);
//		MPOrderUserInfoDTO orderUserInfo = new MPOrderUserInfoDTO();
//		orderUserInfo.setChannelUserId("007");
//		orderUserInfo.setIdCardNo("522324197508045617");
//		orderUserInfo.setName("杨思剑");
//		orderUserInfo.setMobile("13634153082");
//		orderUserInfo.setAddress("杭州");
//		orderUserInfo.setPostcode("310000");
//		orderUserInfo.setEmail("zhurz@hgtech365.com");
//		MPOrderUserInfoDTO takeTicketUserInfo = new MPOrderUserInfoDTO();
//		takeTicketUserInfo.setChannelUserId("007");
//		takeTicketUserInfo.setIdCardNo("522324197508045617");
//		takeTicketUserInfo.setName("杨思剑");
//		takeTicketUserInfo.setMobile("13634153082");
//		takeTicketUserInfo.setAddress("杭州");
//		takeTicketUserInfo.setPostcode("310000");
//		takeTicketUserInfo.setEmail("zhurz@hgtech365.com");
//		command.setOrderUserInfo(orderUserInfo);
//		command.setTakeTicketUserInfo(takeTicketUserInfo);
//		command.setTravelDate(DateUtil.parseDateTime("2014-08-12", "yyyy-MM-dd"));
//		command.setBookManIP("127.0.0.1");
//		command.setTraveler(new ArrayList<MPOrderUserInfoDTO>());
//
//		MPOrderCreateResponse response = apiMPOrderService.apiCreateOrder(command);
//		String json = JSON.toJSONString(response, true);
//		System.out.println(json);
//	}
//	
//	/**
//	 * @方法功能说明：测试订单查询
//	 * @修改者名字：zhurz
//	 * @修改时间：2014-8-7下午2:21:31
//	 * @修改内容：
//	 * @参数：
//	 * @return:void
//	 * @throws
//	 */
//	@Test
//	public void testMpQueryOrder() {
//		MPOrderQO qo = new MPOrderQO();
//		qo.setOrderId("c30e81ec76ff48d4a70d543d096ffd89");
//		qo.setUserId("007");
//		MPQueryOrderResponse response = apiMPOrderService.apiQueryOrder(qo);
//		String json = JSON.toJSONString(response, true);
//		System.out.println(json);
//	}
//	
//}
