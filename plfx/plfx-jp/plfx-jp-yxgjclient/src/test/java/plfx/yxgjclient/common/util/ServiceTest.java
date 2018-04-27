package plfx.yxgjclient.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import plfx.yxgjclient.pojo.param.ApplyCancelNoPayParams;
import plfx.yxgjclient.pojo.param.ApplyCancelParams;
import plfx.yxgjclient.pojo.param.ApplyRefundParams;
import plfx.yxgjclient.pojo.param.AvailableJourney;
import plfx.yxgjclient.pojo.param.CreateOrderParams;
import plfx.yxgjclient.pojo.param.Flight;
import plfx.yxgjclient.pojo.param.MatchPoliciesBySegInfoParams;
import plfx.yxgjclient.pojo.param.PassengerInfo;
import plfx.yxgjclient.pojo.param.PayAutoParams;
import plfx.yxgjclient.pojo.param.QueryAirRulesParams;
import plfx.yxgjclient.pojo.param.QueryFlightParams;
import plfx.yxgjclient.pojo.param.QueryMoreCabinsParams;
import plfx.yxgjclient.pojo.param.QueryOrderParams;
import plfx.yxgjclient.pojo.param.QueryPoliciesParams;
import plfx.yxgjclient.pojo.param.QueryPolicyStateParams;
import plfx.yxgjclient.pojo.param.TakeoffAvailJourney;
import plfx.yxgjclient.pojo.request.ApplyCancelNoPayRQ;
import plfx.yxgjclient.pojo.request.ApplyCancelRQ;
import plfx.yxgjclient.pojo.request.ApplyRefundRQ;
import plfx.yxgjclient.pojo.request.CreateOrderRQ;
import plfx.yxgjclient.pojo.request.MatchPoliciesBySegInfoRQ;
import plfx.yxgjclient.pojo.request.PayAutoRQ;
import plfx.yxgjclient.pojo.request.QueryAirRulesRQ;
import plfx.yxgjclient.pojo.request.QueryFlightRQ;
import plfx.yxgjclient.pojo.request.QueryMoreCabinsRQ;
import plfx.yxgjclient.pojo.request.QueryOrderDetailRQ;
import plfx.yxgjclient.pojo.request.QueryOrderStatusRQ;
import plfx.yxgjclient.pojo.request.QueryPoliciesRQ;
import plfx.yxgjclient.pojo.request.QueryPolicyStateRQ;
import plfx.yxgjclient.pojo.request.QueryRefundStatesRQ;
import plfx.yxgjclient.pojo.request.QueryTicketNoRQ;
import plfx.yxgjclient.pojo.response.ApplyCancelNoPayRS;
import plfx.yxgjclient.pojo.response.ApplyCancelRS;
import plfx.yxgjclient.pojo.response.ApplyRefundRS;
import plfx.yxgjclient.pojo.response.CreateOrderRS;
import plfx.yxgjclient.pojo.response.MatchPoliciesBySegInfoRS;
import plfx.yxgjclient.pojo.response.PayAutoRS;
import plfx.yxgjclient.pojo.response.QueryAirRulesRS;
import plfx.yxgjclient.pojo.response.QueryFlightRS;
import plfx.yxgjclient.pojo.response.QueryMoreCabinsRS;
import plfx.yxgjclient.pojo.response.QueryOrderDetailRS;
import plfx.yxgjclient.pojo.response.QueryOrderStatusRS;
import plfx.yxgjclient.pojo.response.QueryPoliciesRS;
import plfx.yxgjclient.pojo.response.QueryPolicyStateRS;
import plfx.yxgjclient.pojo.response.QueryRefundStatusRS;
import plfx.yxgjclient.pojo.response.QueryTicketNoRS;
import plfx.yxgjclient.service.YIGJBaseService;

import com.yeexing.iat.services.basic.utils.SignUtils;

public class ServiceTest {
	String userName = "Ply365365";
	String key = "7a555b191d58896560fa284e4f670bc0";
	String interUrl="http://is.yeexing.net/services/IatServices?wsdl";
	YIGJBaseService service = null;
	YIGJConfig yigjConfig=null;

	@Before
	public void setUp() throws Exception {
		service = new YIGJBaseService();
		yigjConfig=new YIGJConfig();
		yigjConfig.setInterUrl(interUrl);
		yigjConfig.setKey(key);
		yigjConfig.setUserName(userName);
		service.setYigjConfig(yigjConfig);
		service.init();
		String folderName = "E:\\gjjp_file";
		File paramFile = new File(folderName);
		if (!paramFile.exists()) {
			paramFile.mkdir();
		}

	}

	@After
	public void tearDown() throws Exception {
		//System.out.println("接口参数和返回结果保存在E:\\gjjp_file中");
	}

	/**
	 * 航班查询测试
	 * 2015-07-23 09:19:04 
	 * 目前测试结果：测试通过
	 */
	@Test
	public void testFlightQuery() {
		// 测试航班查询
		QueryFlightRQ rq = new QueryFlightRQ();
		QueryFlightParams params = rq.getQueryFlightParams();
		// 设置航空公司
		// params.setAirComp("airComp");
		params.setOrgCity("SHA");// 上海
		params.setDstCity("JFK");// 纽约
		params.setOrgDate("2015-08-25");
		try {
			String sign = SignUtils.getSign(params, key);
			//System.out.println("sign:" + sign);
			rq.setSign(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}

		QueryFlightRS rs = service.queryFlight(rq);
		//System.out.println(rs.getQueryFlightResult().getAvailableJourney()
		//		.size());
		if (rs != null && rs.getIsSuccess().equals("T")) {
		//	System.out.println("查询到记录数："
		//			+ rs.getQueryFlightResult().getAvailableJourney().size());
		}
	}

	/**
	 * 易行国际机票接口更多舱位测试
	 * 2015-07-23 16:30:23
	 * 目前测试结果：查无结果
	 */
	@Test
	public void testMoreCabin() {
		QueryMoreCabinsRQ rq = new QueryMoreCabinsRQ();
		QueryMoreCabinsParams params = rq.getQueryMoreCabinsParams();
		AvailableJourney availableJourney = new AvailableJourney();
		TakeoffAvailJourney takeoffAvailJourney = new TakeoffAvailJourney();
		List<Flight> flights = new ArrayList<Flight>();
		Flight flight = new Flight();
		flight.setOrgCity("PVG");
		flight.setDstCity("ICN");
		flight.setMarketingAirline("OZ");
		flight.setFlightNo("362");
		flight.setPlaneType("333");
		flight.setStartTime("2015-08-25 11:40");
		flight.setEndTime("2015-08-25 14:30");
		flight.setResBookDesigList("C:9,D:9,Z:9,U:9,Y:9,B:9,M:9,H:9,E:9,Q:9,K:9,S:9,V:9,L:9,W:9");
		flights.add(flight);
		takeoffAvailJourney.setFlights(flights);
		availableJourney.setTakeoffAvailJourney(takeoffAvailJourney);
		params.setAvailableJourney(availableJourney);
		QueryMoreCabinsRS rs = service.queryMoreCabins(rq);
		if (rs != null) {
			//System.out.println(rs.getIsSuccess());
			//System.out.println(rs.getErrorMessage());
		}
	}

	/**
	 * 易行国际接口获取政策接口单元测试 
	 * 2015-07-23 16:45:39 
	 * 目前测试结果：测试通过
	 */
	@Test
	public void testQueryPolicies() {
		QueryPoliciesRQ rq = new QueryPoliciesRQ();
		QueryPoliciesParams params = rq.getQueryPoliciesParams();
		params.setPassengerType("2");// 乘客类型-学生
		params.setSegType("1");// 航程类型-不限
		params.setPeopleNum("2");// 乘客人数
		params.setIsPermitOpen("0");
		params.setPolicyType("0");
		params.setSortType("ADT");
		params.setIsBestPolicy("0");
		params.setEncryptString("93e5819267830efd612a8fe6a4e613d4d1a7814b8689fa14c051af93a255771a17b29e4645d8aab3777a7fd1797e473b994d0f83c3900935c5566a4f47f867f2550c218fc147a5a928036ecbbdd73ebdcb1d06b5aab3f2d1ac6d4d641ab4056afe9c4b9c4844f710c407e8b86bc3f3b1a7e825836549e131027707811bbb3080af7db80ec520f5c959341a74be8cdbfa422d1cb411eb9bc6d6ad64038084e8e3ad6e0fd3335e044384f66a6c6573cdf219103b8c0bfbfb02c5566a4f47f867f2b557a9665a6c2c7f88b7f8cc2a3fa5d88318f8dc8581818b903646dbf6a6d84a92a46a56203cc427e4220140da1a1c019601ab250bca54b816d3ed33c064c71abd350421c88d943fbaa57c476a43646d0fe2aaadc3aa416b6da5cc757624345edeb7a0d14d77760ca259ebf08613eeb0ba81da3980315180615299cd88d503d75a42aaa8b27bc057ef80a41376a73c20445854ff6b43ddfd");
		QueryPoliciesRS rs = service.queryPolicies(rq);
		if (rs != null) {
			//System.out.println(rs.getErrorCode() + "\n" + rs.getIsSuccess());
			//System.out.println(rs.getQueryPoliciesResult().getRewPolicyInfos()
			//		.get(0).getOrdFinalPirce());
		}
	}

	/**
	 * 航段获取政策接口单元测试
	 *  2015-07-23 17:32:35 
	 *  目前测试结果：未查到政策信息
	 */
	@Test
	public void testMatchPolice() {
		// 参数太多，从文件中读取
		String fileName = "src\\test\\java\\plfx\\yxgjclient\\common\\util\\sourcefile\\availableJourney.xml";
		String availableJourneyString = FileUtil.stringFromFile(fileName);
		AvailableJourney availableJourney = XStreamUtil.xmlToObject(
				AvailableJourney.class, availableJourneyString);
		MatchPoliciesBySegInfoRQ rq = new MatchPoliciesBySegInfoRQ();
		MatchPoliciesBySegInfoParams params = rq
				.getMatchPoliciesBySegInfoParams();
		params.setPassengerType("1");
		params.setSegType("1");
		params.setPeopleNum("1");
		params.setIsPermitOpen("0");
		params.setPolicyType("0");
		params.setSortType("ADT");
		params.setIsBestPolicy("0");
		params.setAvailableJourney(availableJourney);
		MatchPoliciesBySegInfoRS rs = service.matchPoliciesBySegInfo(rq);
		if (rs != null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println("接口调用成功");
				//System.out.println("文件政策信息数："
				//		+ rs.getMatchPoliciesBySegInfoResult()
				//				.getFilePolicyInfos().size());
			} else {
				//System.out.println(rs.getErrorMessage());
			}
		}
	}

	/**
	 * @描述 出票规则接口单元测试
	 * @date 2015-07-24 09:50:43
	 * @result 测试通过
	 */
	@Test
	public void testQueryAirRules(){
		QueryAirRulesRQ rq=new QueryAirRulesRQ();
		QueryAirRulesParams params=rq.getQueryAirRulesParams();
		params.setOrgCity("SHA");
		params.setDstCity("CAN");
		params.setStartTime("2015-07-25 08:45");
		params.setFilingAirline("CZ");
		params.setFareReference("U2PRCWZU");
		params.setRef1("GEPY01(USER(SHA,'1E',&lt;&gt;,Y,DEPT(&lt;&gt;,&lt;&gt;),&lt;&gt;,&lt;&gt;,&lt;&gt;),PF2(Y,[(AGENCY(SHA888),IATANUM('08312393'),N)],[],[],[],N,&lt;&gt;),&lt;&gt;)");
		params.setRef2("003ZU05PANADTN0010000100ATP");
		QueryAirRulesRS rs=service.queryAirRules(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println(rs.getQueryAirRulesResult().getResultData());
			}else {
				//System.out.println(rs.getErrorMessage());
			}
		}
	}
	/**
	 * @描述 生成订单单元测试
	 * @date 2015-07-24
	 * @结果 测试成功
	 */
	@Test
	public void testCreateOrder(){
		String fileName = "src\\test\\java\\plfx\\yxgjclient\\common\\util\\sourcefile\\createOrderParam.xml";
		String createOrderParamsString = FileUtil.stringFromFile(fileName);
		CreateOrderParams params=XStreamUtil.xmlToObject(CreateOrderParams.class, createOrderParamsString);
		CreateOrderRQ rq=new CreateOrderRQ();
		rq.setCreateOrderParams(params);
		CreateOrderRS rs=service.createOrder(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println("订单号："+rs.getCreateOrderResult().getOrderId());
			}else {
				//System.out.println(rs.getErrorMessage());
			}
			
		}
	}
	/**
	 * @描述 自动扣款测试
	 * @date 2015-07-24 14:40:41
	 * @result 测试通过，定单在审核中，无法支付
	 */
	@Test
	public void testPayAuto(){
		PayAutoRQ rq=new PayAutoRQ();
		PayAutoParams params=rq.getPayAutoParams();
		params.setOrderId("I201507241169623594");//有创建订单生成
		params.setExtOrderId("PLLV201507241053");//有创建订单生成
		params.setTotalPrice("7774.0");//有创建订单生成
		params.setPayPlatform("2");//支付宝支付
		params.setPayNotifyUrl("http://www.ply365.com");
		params.setOutNotifyUrl("https://www.ply365.com");
		PayAutoRS rs=service.payAuto(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				if (rs.getPayAutoResult().getPayStatus().equals("1")) {
					//System.out.println("支付成功");
				}else {
					//System.out.println("支付失败");
				}
				//System.out.println("支付流水号："+rs.getPayAutoResult().getTradeNo());
			}else {
				//System.out.println(rs.getErrorMessage());
			}
		}else {
			//System.err.println("接口调用失败");
		}
	}
	/**
	 * 订单状态单元测试
	 * @date 2015-07-24 15:00:33
	 * @result 订单在审核
	 */
	@Test
	public void testQueryOrderStatus(){
		QueryOrderStatusRQ rq=new QueryOrderStatusRQ();
		QueryOrderParams params=rq.getQueryOrderParams();
		params.setOrderId("I201507241169623594");//有创建订单生成
		params.setExtOrderId("PLLV201507241053");//有创建订单生成
		QueryOrderStatusRS rs=service.queryOrderState(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println(rs.getQueryOrderStatusResult().getOrdState());
			}else {
				//System.out.println(rs.getErrorMessage());
			}
		}else {
			//System.err.println("接口调用失败");
		}
	}
	/**
	 * @描述 申请取消已支付订单单元测试
	 * @date 2015-07-24 15:05:43
	 * @result 没有已支付订单，不能取消
	 */
	@Test
	public void testApplyCancelOrder(){
		ApplyCancelRQ rq=new ApplyCancelRQ();
		ApplyCancelParams params=rq.getApplyCancelParams();
		params.setOrderId("I201507241169623594");//有创建订单生成
		params.setExtOrderId("PLLV201507241053");//有创建订单生成
		params.setCancelNotifyUrl("http://www.ply365.com");
		ApplyCancelRS rs=service.applyCancelOrder(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println(rs.getApplyCancelResult().getOrderId());
			}else {
				//System.err.println(rs.getErrorMessage());
			}
		}
	}
	/**
	 * @描述 申请退废票单元测试
	 * @date 2015-07-24 15:44:38
	 * @result 无测试数据
	 */
	@Test
	public void testApplyRefundTicket(){
		ApplyRefundRQ rq=new ApplyRefundRQ();
		ApplyRefundParams params=rq.getApplyRefundParams();
		params.setOrderId("I201507241169623594");
		params.setExtOrderId("PLLV201507241053");
		PassengerInfo passengerInfo=new PassengerInfo();
		passengerInfo.setPassengerName("");
		passengerInfo.setOrdDetEticketNo("");
		List<PassengerInfo>passengerInfos=new ArrayList<PassengerInfo>();
		passengerInfos.add(passengerInfo);
		params.setPassengerInfos(passengerInfos);
		ApplyRefundRS rs=service.applyRefundTicket(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println(rs.getApplyRefundResult().getOrderId());
			}else {
				//System.out.println(rs.getErrorMessage());
			}
		}else {
			//System.err.println("接口调用失败");
		}
	}
	/**
	 * @描述 申请未支付订单单元测试
	 * @date 2015-07-24 15:50:22
	 * @result 
	 */
	@Test
	public void testApplyCancelOrderNoPay(){
		ApplyCancelNoPayRQ rq=new ApplyCancelNoPayRQ();
		ApplyCancelNoPayParams params=rq.getApplyCancelNoPayParams();
		params.setOrderId("I201507241169623594");
		params.setExtOrderId("PLLV201507241053");
		params.setCancelReasonType("4");//原因：证件不齐
		ApplyCancelNoPayRS rs=service.applyCancelOrderNoPay(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println(rs.getApplyCancelNoPayResult().getExtOrderId());
			}else {
				//System.out.println(rs.getErrorMessage());
			}
		}else {
			//System.out.println("接口调用失败");
		}
	}
	/**
	 * @描述 查询订单票号接口测试
	 * 票号要单独查询，没有哪个接口顺带返回
	 * @date 2015-07-24 16:04:42
	 * @result 
	 */
	@Test
	public void testQueryTicketNo(){
		QueryTicketNoRQ rq=new QueryTicketNoRQ();
		QueryOrderParams params=rq.getQueryOrderParams();
		params.setOrderId("I201508079227606800");
		params.setExtOrderId("F1003A808071647170401");
		QueryTicketNoRS rs=service.queryTicketNo(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println("调用成功");
			}else {
				//System.out.println(rs.getErrorMessage());
			}
		}else {
			//System.err.println("接口调用失败");
		}
	}
	/**
	 * @描述 查询订单详情
	 * @date 2015-07-24 16:15:33
	 * @result 测试通过
	 */
	@Test
	public void testQueryOrderDetail(){
		QueryOrderDetailRQ rq=new QueryOrderDetailRQ();
		QueryOrderParams params = rq.getQueryOrderParams();
		params.setOrderId("I201507241169623594");
		params.setExtOrderId("PLLV201507241053");
		QueryOrderDetailRS rs=service.queryOrderDetail(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println("调用成功，详情请查看返回参数报文");
			}else {
				//System.out.println(rs.getErrorMessage());
			}
		}else {
			System.err.println("接口调用失败");
		}
	}
	/**
	 * @描述 查询退费票信息单元测试
	 * @date 2015-07-24 16:45:28
	 * @result 无参数可查
	 */
	@Test
	public void testQueryRefundState(){
		QueryRefundStatesRQ rq = new QueryRefundStatesRQ();
		QueryOrderParams params = rq.getQueryRefundTktParams();
		params.setOrderId("I201507241169623594");
		params.setExtOrderId("PLLV201507241053");
		params.setPassengerName("Guo/tianxiang");
		params.setOrdDetEticketNo("tfsdgfdsghdshd");//无此参数
		QueryRefundStatusRS rs=service.queryRefundState(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println("接口调用成功");
			}else {
				//System.out.println(rs.getErrorMessage());
			}
		}else {
			//System.err.println("接口调用失败");
		}
	}
	/**
	 * @描述 查询政策信息单元测试
	 * @date 2015-07-24 17:10:32
	 * @result 查无数据
	 */
	@Test
	public void testQueryPolicyState(){
		QueryPolicyStateRQ rq=new QueryPolicyStateRQ();
		QueryPolicyStateParams params=rq.getQueryPolicyStateParams();
		params.setPolicyType("1");
		params.setPolicyId("-1");
		QueryPolicyStateRS rs=service.queryPoliciesState(rq);
		if (rs!=null) {
			if (rs.getIsSuccess().equals("T")) {
				//System.out.println("接口调用成功，详情请查看接口返回报文");
			}else {
				//System.out.println(rs.getErrorMessage());
			}
		}else {
			//System.err.println("接口调用失败");
		}
	}
	public static void main(String[] args) {
		ServiceTest test=new ServiceTest();
		try {
			test.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		test.testQueryOrderDetail();
	}

}
