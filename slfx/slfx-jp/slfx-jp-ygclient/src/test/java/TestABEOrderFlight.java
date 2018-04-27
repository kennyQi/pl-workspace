//import java.util.ArrayList;
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
//import slfx.jp.pojo.dto.supplier.abe.ABEOrderFlightDTO;
//import slfx.jp.pojo.dto.supplier.abe.ABEPatFlightDTO;
//import slfx.jp.pojo.dto.supplier.yg.QueryWebFlightsDTO;
//import slfx.jp.pojo.dto.supplier.yg.YGFlightDTO;
//import slfx.jp.spi.command.client.ABELinkerInfoCommand;
//import slfx.jp.spi.command.client.ABEOrderFlightCommand;
//import slfx.jp.spi.command.client.ABEOrderInfoCommand;
//import slfx.jp.spi.command.client.ABEPassengerCommand;
//import slfx.jp.spi.command.client.ABEPriceDetailCommand;
//import slfx.jp.spi.qo.client.PatFlightQO;
//import slfx.jp.spi.qo.client.QueryWebFlightsQO;
//import slfx.yg.open.inter.YGFlightService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:junit-test-spring-service.xml" })
//public class TestABEOrderFlight {
//	
//	@Autowired
//	private YGFlightService ygFlightService;
//
//	
//	@Test
//	public void test(){
//		
//		
//		//航班查询
//		QueryWebFlightsQO qo = new QueryWebFlightsQO();
//		qo.setFrom("PEK");
//		qo.setArrive("DLC");
//		qo.setDateStr("2014-08-28");
//		QueryWebFlightsDTO dto = ygFlightService.queryFlights(qo);
//		
//		YGFlightDTO fdto = dto.getFlightList().get(6);
//		SlfxFlightClassDTO fc = fdto.getFlightClassList().get(1);
//		
//		//验价
//		PatFlightQO pqo = new PatFlightQO();
//		pqo.setCarrier(fdto.getCarrier());
//		pqo.setClassCode(fc.getClassCode());
//		pqo.setEndPort(fdto.getEndPort());
//		pqo.setFlightNo(fdto.getFlightNo());
//		pqo.setStartDate(fdto.getStartDate());
//		pqo.setStartPort(fdto.getStartPort());
//		pqo.setStartTime(fdto.getStartTime());
//		HashMap<String, ABEPatFlightDTO>  map = ygFlightService.patByFlights(pqo);
//		
//		ABEOrderFlightCommand ofc = new ABEOrderFlightCommand();
//		ABEOrderInfoCommand oic = new ABEOrderInfoCommand();
//		ABELinkerInfoCommand lic = new ABELinkerInfoCommand();
//		ABEPassengerCommand pc = new ABEPassengerCommand();
//		List<ABEPassengerCommand> list = new ArrayList<ABEPassengerCommand>();
//		ABEPriceDetailCommand pdc = new ABEPriceDetailCommand();
//		List<ABEPriceDetailCommand> list2 = new ArrayList<ABEPriceDetailCommand>();
//		
//		
//		pdc.setAirportTax(map.get("ADT").getAirportTax());
//		pdc.setFare(map.get("ADT").getFacePar());
//		pdc.setFuelSurTax(map.get("ADT").getFuelSurTax());
//		pdc.setAmount(map.get("ADT").getAmount());
//		pdc.setPsgType("ADT");
//		pdc.setTaxAmount(map.get("ADT").getTaxAmount());
//		list2.add(pdc);
//		
//		//乘客信息
//		pc.setPsgId(1);//
////		pc.setName(URLEncoder.encode("杨康", "GB2312"));
////		pc.setName("kang/yang");
//		pc.setName("瞿红云");
//		pc.setPsgType("ADT");//
//		pc.setIdentityType("NI");//
//		pc.setCardType("NI");//
//		pc.setCardNo("330481198504230014");//
//		pc.setCountry("CN");//
//		pc.setBirthDay("1985-04-23");
//		pc.setMobliePhone("13757193676");
//		list.add(pc);
//		
//		//订单联系信息
////		lic.setAddress("浙江杭州滨江区江南大道1505号");
////		lic.setInvoicesSendType(invoicesSendType);
//		lic.setIsETiket("Y");
//		lic.setIsPrintSerial("Y");
////		lic.setLinkerEmail("123456@163.com");
////		lic.setLinkerName(URLEncoder.encode("杨康", "GB2312"));
//		lic.setLinkerName("kang/yang");
//		lic.setMobilePhone("13757193676");
////		lic.setNeedInvoices(needInvoices);
////		lic.setPayType(payType);
////		lic.setRemark(remark);
////		lic.setSendTime(sendTime);
////		lic.setSendTktsTypeCode(sendTktsTypeCode);
////		lic.setTelphone(telphone);
////		lic.setZip(zip);
//		
//		//订单信息
////		oic.setLinker(URLEncoder.encode("习大大", "GB2312"));
//		oic.setLinker("tou/shi");
//		oic.setIsDomc("D");
//		oic.setBalanceMoney(pdc.getAmount());
//		
//		//订单航段信息
//		ofc.setCarrier(fdto.getCarrier());
//		ofc.setFlightNo(fdto.getFlightNo());
//		ofc.setStartCityCode(fdto.getStartPort());
//		ofc.setEndCityCode(fdto.getEndPort());
////		ofc.setStartCityCode(fdto.getStartCityCode());
////		ofc.setEndCityCode(fdto.getEndCityCode());
//		ofc.setMileage(Integer.valueOf(fdto.getMileage()));
//		ofc.setClassCode(fc.getClassCode());
//		ofc.setClassPrice(fc.getOriginalPrice());
//		ofc.setClassRebate(String.valueOf(fc.getDiscount()));
//		ofc.setYPrice(fdto.getYPrice());
//		ofc.setFuelSurTax(fdto.getFuelSurTax());
//		ofc.setAirportTax(fdto.getAirportTax());
//		ofc.setStartDate(fdto.getStartDate());
//		ofc.setStartTime(fdto.getStartTime());
//		ofc.setEndDate(fdto.getEndDate());
//		ofc.setEndTime(fdto.getEndTime());
//		ofc.setAircraftCode(fdto.getAircraftCode());
//		
//		ofc.setAbeOrderInfoCommand(oic);
//		ofc.setAbeLinkerInfoCommand(lic);
//		ofc.setAbePriceDetailList(list2);
//		ofc.setAbePsgList(list);
//		
//		ABEOrderFlightDTO ofDto = ygFlightService.abeOrderFlight(ofc);
//		
//		System.out.println("PNR: "+ofDto.getPnr());
//		System.out.println("预定单号："+ofDto.getSubsOrderNo());
//		System.out.println("旅客人数："+ofDto.getPassengerCount());
//		System.out.println("订单金额: "+ofDto.getBalanceMoney());
//		System.out.println("错误描述："+ofDto.getErrMsg());
//		System.out.println("平台订单号："+ofDto.getPlatformOrderNo());
//		System.out.println("平台交易流水号:"+ofDto.getPlatformTransID());
//		
//		
//	}
//	
//}
