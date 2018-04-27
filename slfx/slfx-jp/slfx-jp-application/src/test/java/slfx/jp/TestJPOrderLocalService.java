//
//package slfx.jp;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//
//import slfx.jp.app.service.local.JPOrderLocalService;
//import slfx.jp.app.service.local.LocalCityAirCodeService;
//import slfx.jp.domain.model.order.JPOrder;
//import slfx.jp.pojo.dto.flight.CityAirCodeDTO;
//import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
//import slfx.jp.pojo.dto.flight.SlfxFlightClassDTO;
//import slfx.jp.pojo.dto.order.JPOrderDTO;
//import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
//import slfx.jp.pojo.dto.supplier.abe.ABEOrderFlightDTO;
//import slfx.jp.pojo.dto.supplier.abe.ABEPatFlightDTO;
//import slfx.jp.pojo.dto.supplier.yg.QueryWebFlightsDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
//import slfx.jp.qo.admin.PlatformOrderQO;
//import slfx.jp.spi.inter.JPPlatformOrderService;
//import slfx.yg.open.inter.YGFlightService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp.xml" })
//public class TestJPOrderLocalService {
//	
//	@Autowired
//	private JPOrderLocalService jpOrderLocalService;
//	
//	@Autowired
//	private YGFlightService ygFlightService;
//	
//	@Autowired
//	private JPPlatformOrderService jpPlatformOrderService;
//	
//	@Test
//	public void zk_test() {
//		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
//		platformOrderQO.setOrderNo("F1001AC02101040000000");
//		//JPOrder jpOrder =jpOrderLocalService.queryJPOrderUnique(platformOrderQO);
//		JPOrderDTO jpOrder = jpPlatformOrderService.queryUnique(platformOrderQO);
//		//JPOrder jpOrder = jpOrderLocalService.queryJPOrderUnique(platformOrderQO);
//		//JPOrderDTO jpOrder = jpPlatformOrderService.queryList(platformOrderQO);
//		System.out.println("==========="+JSON.toJSON(jpOrder));
//	}
//}
//package slfx.jp;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import slfx.jp.app.service.local.JPOrderLocalService;
//import slfx.jp.domain.model.order.JPOrder;
//import slfx.jp.pojo.dto.order.JPOrderStatus;
//import slfx.jp.spi.inter.JPPlatformOrderService;
//import slfx.jp.spi.qo.admin.PlatformOrderQO;
//import slfx.yg.open.inter.YGFlightService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-slfx-jp.xml" })
//public class TestJPOrderLocalService {
//	
//	@Autowired
//	private JPOrderLocalService jpOrderLocalService;
//	
//	@Autowired
//	private YGFlightService ygFlightService;
//	
//	@Autowired
//	private JPPlatformOrderService jpPlatformOrderService;
//	
//	@Test
//	public void zk_test() {
//		PlatformOrderQO qo = new PlatformOrderQO();
//		qo.setStatus(Integer.parseInt(JPOrderStatus.DISCARD_SUCC_NOT_REFUND));
//		List<JPOrder> jpDiscardOrders = jpOrderLocalService.queryList(qo);
//		System.out.println("====================" + jpDiscardOrders.size());
//	}
//	
//	@Test
//	public void td_test() {
//		
//		PlatformOrderQO qo = new PlatformOrderQO();
//		qo.setPayTradeNo("1111111111");
//		qo.setStatus(32,33);
//		JPOrder jpOrder = jpOrderLocalService.queryList(qo).get(0);
//		//JPOrder jpOrder = jpOrderLocalService.queryUnique(qo);
//		System.out.println("====================" + jpOrder);
//	}
//	
//	@Test
//	public void test(){
////		jpoLocalService.testABEOrderSave();
//		
//		
//		//航班查询
//				QueryWebFlightsQO qo = new QueryWebFlightsQO();
//				qo.setFrom("PEK");
//				qo.setArrive("DLC");
//				qo.setDateStr("2014-08-30");
//				QueryWebFlightsDTO dto = ygFlightService.queryFlights(qo);
//				
//				YGFlightDTO fdto = dto.getFlightList().get(6);
//				SlfxFlightClassDTO fc = fdto.getFlightClassList().get(1);
//				
//				//验价
//				PatFlightQO pqo = new PatFlightQO();
//				pqo.setCarrier(fdto.getCarrier());
//				pqo.setClassCode(fc.getClassCode());
//				pqo.setEndPort(fdto.getEndPort());
//				pqo.setFlightNo(fdto.getFlightNo());
//				pqo.setStartDate(fdto.getStartDate());
//				pqo.setStartPort(fdto.getStartPort());
//				pqo.setStartTime(fdto.getStartTime());
//				HashMap<String, ABEPatFlightDTO>  map = ygFlightService.patByFlights(pqo);
//				
//				ABEOrderFlightCommand ofc = new ABEOrderFlightCommand();
//				ABEOrderInfoCommand oic = new ABEOrderInfoCommand();
//				ABELinkerInfoCommand lic = new ABELinkerInfoCommand();
//				ABEPassengerCommand pc = new ABEPassengerCommand();
//				List<ABEPassengerCommand> list = new ArrayList<ABEPassengerCommand>();
//				ABEPriceDetailCommand pdc = new ABEPriceDetailCommand();
//				List<ABEPriceDetailCommand> list2 = new ArrayList<ABEPriceDetailCommand>();
//				
//				
//				pdc.setAirportTax(map.get("ADT").getAirportTax());
//				pdc.setFare(map.get("ADT").getFacePar());
//				pdc.setFuelSurTax(map.get("ADT").getFuelSurTax());
//				pdc.setAmount(map.get("ADT").getAmount());
//				pdc.setPsgType("ADT");
//				pdc.setTaxAmount(map.get("ADT").getTaxAmount());
//				list2.add(pdc);
//				
//				//乘客信息
//				pc.setPsgId(1);//
////				pc.setName(URLEncoder.encode("杨康", "GB2312"));
////				pc.setName("kang/yang");
//				pc.setName("瞿红云");
//				pc.setPsgType("ADT");//
//				pc.setIdentityType("NI");//
//				pc.setCardType("NI");//
//				pc.setCardNo("330481198504230014");//
//				pc.setCountry("CN");//
//				pc.setBirthDay("1985-04-23");
//				pc.setMobliePhone("13757193676");
//				list.add(pc);
//				
//				//订单联系信息
////				lic.setAddress("浙江杭州滨江区江南大道1505号");
////				lic.setInvoicesSendType(invoicesSendType);
//				lic.setIsETiket("Y");
//				lic.setIsPrintSerial("Y");
////				lic.setLinkerEmail("123456@163.com");
////				lic.setLinkerName(URLEncoder.encode("杨康", "GB2312"));
//				lic.setLinkerName("kang/yang");
//				lic.setMobilePhone("13757193676");
////				lic.setNeedInvoices(needInvoices);
////				lic.setPayType(payType);
////				lic.setRemark(remark);
////				lic.setSendTime(sendTime);
////				lic.setSendTktsTypeCode(sendTktsTypeCode);
////				lic.setTelphone(telphone);
////				lic.setZip(zip);
//				
//				//订单信息
////				oic.setLinker(URLEncoder.encode("习大大", "GB2312"));
//				oic.setLinker("tou/shi");
//				oic.setIsDomc("D");
//				oic.setBalanceMoney(pdc.getAmount());
//				
//				//订单航段信息
//				ofc.setCarrier(fdto.getCarrier());
//				ofc.setFlightNo(fdto.getFlightNo());
//				ofc.setStartCityCode(fdto.getStartPort());
//				ofc.setEndCityCode(fdto.getEndPort());
////				ofc.setStartCityCode(fdto.getStartCityCode());
////				ofc.setEndCityCode(fdto.getEndCityCode());
//				ofc.setMileage(Integer.valueOf(fdto.getMileage()));
//				ofc.setClassCode(fc.getClassCode());
//				ofc.setClassPrice(fc.getOriginalPrice());
//				ofc.setClassRebate(String.valueOf(fc.getDiscount()));
//				ofc.setYPrice(fdto.getYPrice());
//				ofc.setFuelSurTax(fdto.getFuelSurTax());
//				ofc.setAirportTax(fdto.getAirportTax());
//				ofc.setStartDate(fdto.getStartDate());
//				ofc.setStartTime(fdto.getStartTime());
//				ofc.setEndDate(fdto.getEndDate());
//				ofc.setEndTime(fdto.getEndTime());
//				ofc.setAircraftCode(fdto.getAircraftCode());
//				
//				ofc.setAbeOrderInfoCommand(oic);
//				ofc.setAbeLinkerInfoCommand(lic);
//				ofc.setAbePriceDetailList(list2);
//				ofc.setAbePsgList(list);
//				
//				ABEOrderFlightDTO abe = jpOrderLocalService.abeOrderFlight(ofc);
//				System.out.println(abe.getPnr());
//				
//				ABEDeletePnrDTO abeDto = jpOrderLocalService.testDeletePnr(abe.getPnr());
//				System.out.println(abeDto.getStatus());
//				System.out.println(abeDto.getMsg());
//	}
//	
//	@Autowired
//	private LocalCityAirCodeService localCityAirCodeService;
//	
//	@Test
//	public void getLocalCityAirCodeService(){
//		List<CityAirCodeDTO> cityAirCodeDTOList = localCityAirCodeService.queryCityAirCodeList();
//		
//		System.out.println(JSON.toJSONString(cityAirCodeDTOList));
//	}
//	
//	@Test
//	public void getOrderBYNo(){
//		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
//		platformOrderQO.setOrderNo("F1001A902164942000000");
//		
//		JPOrder jpOrder = this.jpOrderLocalService.queryJPOrderUnique(platformOrderQO);
//		String ygOrderId = jpOrder.getYgOrderNo();
//		System.out.println("ygOrderId="+ygOrderId);
//		System.out.println("--------"+JSON.toJSONString(ygOrderId));
//	}
//	
//	@Test
//	public void getOrderBYDealerOrderCode(){
//		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
//		platformOrderQO.setDealerOrderCode("A924155913000000");
//		String ygOrderId = this.jpOrderLocalService.queryJPOrderUnique(platformOrderQO).getYgOrderNo();
//		System.out.println("--------"+JSON.toJSONString(ygOrderId));
//	}
//	
//	@Test
//	public void getOrder(){
//		//查询所有未支付的机票订单
//		PlatformOrderQO jpOrderQO =new PlatformOrderQO();
//		
//		//jpOrderQO.setStatus(JPOrderStatus.ALREADY_PAY);//已支付
//		
//		jpOrderQO.setType(JPOrderStatus.COMMON_TYPE);//正常订单
//
//		List<JPOrderDTO> orderList = jpPlatformOrderService.queryJPOrderList(jpOrderQO);
//		
//		System.out.println("--------"+JSON.toJSONString(orderList));
//	}
//	
//	@Test
//	public void getOrderByQO(){
//		//判断订单是否已经存在
//		try {
//			PlatformOrderQO qo = new PlatformOrderQO(); 
//			qo.setFlightNo("123456");//设置航班号
//			
//			String dateStr = "2014-10-15";
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Date date = sdf.parse(dateStr);
//			date.setDate(date.getDate() + 1);
//			dateStr = sdf.format(date);
//			
//			qo.setStartDepartureTime(jPAPIOrderCreateCommand.getDate());
//			qo.setEndDepartureTime(dateStr);//设置航班起飞时间
//			
//			List<FlightPassengerDTO> passangerDTOs = jPAPIOrderCreateCommand.getPassangers();
//			if (passangerDTOs == null || passangerDTOs.size() == 0) {
//				//return null;
//			} else {
//				FlightPassengerDTO passengerDTO = passangerDTOs.get(0);
//				PassengerQO passengerQO = new PassengerQO();
//				passengerQO.setName(passengerDTO.getName());
//				qo.setPassengerQO(passengerQO);
//			}//设置乘机人
//			
//			JPOrder isExsits =  jpOrderLocalService.queryUnique(qo);
//			//if (isExsits != null) return null;
//		} catch (ParseException e1) {
//			e1.printStackTrace();
//		}
//	}
//	
//}

