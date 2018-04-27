//package slfx.jp;
//
//import java.util.Date;
//import java.util.Set;
//import java.util.UUID;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSONObject;
//
//import slfx.jp.app.service.local.JPOrderLocalService;
//import slfx.jp.domain.model.order.ComparePrice;
//import slfx.jp.domain.model.order.FlightPolicy;
//import slfx.jp.domain.model.order.FlightTicket;
//import slfx.jp.domain.model.order.JPOrder;
//import slfx.jp.domain.model.order.Passenger;
//import slfx.jp.domain.model.order.YGOrder;
//import slfx.jp.pojo.dto.policy.PolicyDTO;
//import slfx.jp.pojo.system.PolicyConstants;
//import slfx.jp.spi.inter.policy.PolicyService;
//import slfx.jp.spi.qo.admin.policy.PolicyQO;
//
///**
// * 平台DAO JUnit
// * @author renfeng
// *
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp.xml" })
//public class JPOderDAOJUnitTest {
//	
//	@Autowired
//	private JPOrderLocalService  jpOrderLocalService;
//	
//	/**
//	 * 平台的价格政策服务类
//	 */
//	@Autowired
//	private PolicyService policyService;
//	
//	@Test
//	public void saveYGOrder(){
//		
//		System.out.println("aaaaaaaaaaaaaaaygOrderDAO:"+jpOrderLocalService);
//		YGOrder ygOrder =new YGOrder();
//		String uuid=UUID.randomUUID().toString();
//		System.out.println("11111111111111111111111111111"+uuid);
//		ygOrder.setId(uuid);
//		ygOrder.setPnr("111111");
//		ygOrder.setAccountLevel("1");
//		ygOrder.setBalanceMoney(1200.0);
//		ygOrder.setBigPNR("asd120");
//		//ygOrder.setBookingOffice("惠金宝");
//		//ygOrder.setForceDelete("必须输入");
//		
//		ygOrder.setIsVip("10");
////		ygOrder.setOrderNo("HGTEACH20140729192000");
//		//ygOrder.setOrderNo("00000");
//		//ygOrder.setPataText("![[11111111111111111122222222222222222222]]");
//		ygOrder.setPlatCode("001");
//		ygOrder.setPlatformType("1");
//		
//		ygOrder.setPnr("GJA471");
//		ygOrder.setPnrText("111111111111111114444\n111111111111111111\n");
//		ygOrder.setPolicyId("dfdf-1251-dfdfdfd-erer-erer");
//		ygOrder.setRefundWorkTime("2014-07-29 14:00");
//		ygOrder.setRemark("");
//		//ygOrder.setTicketType("112211");
//		ygOrder.setWastWorkTime("2014-07-29 17:14");
//		
//		
//		
//		this.jpOrderLocalService.saveYGOrder(ygOrder);
//	}
//	
//	@Test
//	public void saveJPOrder(){
//		JPOrder jpOrder =new JPOrder();
//		jpOrder.setAbeOrderId("11111111111111");
//		jpOrder.setCommAmount(1000.0);
//		jpOrder.setCommRate(0.21);
//		
//		jpOrder.setCreateDate(new Date());
//		jpOrder.setDealerOrderCode("11111111111");
//		jpOrder.setFlightSnapshotJSON("{[wwwwwwww]}");
//		jpOrder.setId(UUID.randomUUID().toString());
//		
//		jpOrder.setOrderNo("1111111");
//		//jpOrder.setPayAmount(10.02);
//		jpOrder.setYgOrderNo("222222222222");
//		
//		//旅客信息列表
//		Set<Passenger> passangerList =jpOrder.getPassangerList();
//		
//		//保存乘客的机票信息
//		FlightTicket ticket=new FlightTicket();
//		ticket.setId(UUID.randomUUID().toString());
//		ticket.setAirportTax(100.0);
//		ticket.setTicketNo("1120");
//		this.jpOrderLocalService.saveFlightTicket(ticket);
//		
//		//保存乘客信息
//		Passenger psg=new Passenger();
//		psg.setBirthDay("2014-01-20 14:00");
//		psg.setCardNo("112120");
//		psg.setId(UUID.randomUUID().toString());
//		psg.setSalePrice(1200.2);
//		psg.setTicket(ticket);
//		this.jpOrderLocalService.savePassenger(psg);
//		
//	   passangerList.add(psg);
//	  
//	  //保存比价选中的政策信息
//	  FlightPolicy p=new FlightPolicy();
//	  p.setId(UUID.randomUUID().toString());
//	  p.setAutoTicket("Y");
//	  p.setCommAmount(1200.0);
//	  p.setCommRate(0.2);
//	  this.jpOrderLocalService.saveFlightPolicy(p);
//	  
//	 //保存比价信息
//	  ComparePrice comparePrice =new ComparePrice();
//	  comparePrice.setId(UUID.randomUUID().toString());
//	  comparePrice.setCompareResultPolicy(p);
//	  this.jpOrderLocalService.saveComparePrice(comparePrice);
//	  
//	  //jpOrder.setComparePrice(comparePrice);
//	  
//	  //最后保存机票订单信息
//	  this.jpOrderLocalService.saveJPOrder(jpOrder);
//	  
//	}
//	
//	@Test
//	public void queryPolicy(){
//		PolicyQO policyQO = new PolicyQO();
//		policyQO.setSuppId("001");//platCode
//		policyQO.setDealerId("F1001");//dealerCode
//		policyQO.setCurrentTime(new Date());
//		policyQO.setStatus(PolicyConstants.EFFECT);//价格政策已经生效
//		policyQO.setSortCreateTime(true);  //取最新的价格政策
//		PolicyDTO policyDTO = policyService.getEffectPolicy(policyQO);
//		System.out.println("========="+JSONObject.toJSON(policyDTO));
//	}
//
//}
