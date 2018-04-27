package service.test;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hsl.pojo.command.hotel.CheckCreditCardNoCommand;
import hsl.pojo.command.hotel.JDOrderCreateCommand;
import hsl.pojo.dto.hotel.HotelDTO;
import hsl.pojo.dto.hotel.order.CheckCreditCardNoDTO;
import hsl.pojo.dto.hotel.order.HotelOrderDetailDTO;
import hsl.pojo.dto.hotel.order.OrderCreateCommandDTO;
import hsl.pojo.dto.hotel.order.OrderCreateResultDTO;
import hsl.pojo.exception.HotelException;
import hsl.pojo.qo.hotel.HotelOrderDetailQO;
import hsl.pojo.qo.hotel.JDHotelDetailQO;
import hsl.pojo.qo.hotel.JDHotelListQO;
import hsl.pojo.util.enumConstants.EnumConfirmationType;
import hsl.pojo.util.enumConstants.EnumCurrencyCode;
import hsl.pojo.util.enumConstants.EnumGuestTypeCode;
import hsl.pojo.util.enumConstants.EnumPaymentType;
import hsl.spi.inter.hotel.HslHotelOrderService;
import hsl.spi.inter.hotel.HslHotelService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slfx.jd.pojo.dto.ylclient.order.ContactDTO;
import slfx.jd.pojo.dto.ylclient.order.CreateOrderRoomDTO;
import slfx.jd.pojo.dto.ylclient.order.CreditCardDTO;
import slfx.jd.pojo.dto.ylclient.order.CustomerDTO;
import slfx.jd.pojo.system.enumConstants.EnumGender;
import slfx.jd.pojo.system.enumConstants.EnumIdType;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestHotelService {
	
	
	@Resource
	private HslHotelService hslHotelService;
	@Resource
	private HslHotelOrderService hslHotelOrderService;
	
	//@Test
	public void hotelPageQuery(){
		int pageNo=1;
		int pageSize=5;
		Pagination pagination =new Pagination ();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		JDHotelListQO listQo=new JDHotelListQO();
		Date date = new Date();	
		date =  Tool.addDate(date, 1);
		date.setHours(8);
		date.setMinutes(0);
		date.setSeconds(0);
		Date date2 =  Tool.addDate(date, 1);
		date2.setHours(14);
		date2.setMinutes(0);
		date2.setSeconds(0);
		listQo.setArrivalDate(date);
		listQo.setDepartureDate(date2);
		//listQo.setArrivalDate(DateUtil.parseDate3("2015-07-08"));//日住日期要大于等于今天
		//listQo.setDepartureDate(DateUtil.parseDate3("2015-07-10"));//离店日期要大于等于明天，并且入住和退房日期相差不能大于20天
		listQo.setCityId("1201"); //默认查询杭州
		listQo.setCustomerType("None"); 
		listQo.setResultType("1,2,3"); //包括房间明细
		listQo.setPageIndex(pageNo);
		listQo.setPageSize(pageSize);
		
		pagination.setCondition(listQo);
		
		try {
			pagination=this.hslHotelService.queryPagination(pagination);
		} catch (HotelException e) {
			e.printStackTrace();
		}
		
		System.out.println(pagination);
	}
	
	
	//@Test
	public void hotelDetailQuery(){
		
		JDHotelDetailQO qo=new JDHotelDetailQO();
		
		Date date = new Date();	
		date =  Tool.addDate(date, 1);
		date.setHours(8);
		date.setMinutes(0);
		date.setSeconds(0);
		Date date2 =  Tool.addDate(date, 1);
		date2.setHours(14);
		date2.setMinutes(0);
		date2.setSeconds(0);
		
		qo.setArrivalDate(date);
		qo.setDepartureDate(date2);
		
		//qo.setArrivalDate(DateUtil.parseDate3("2015-07-25"));
		//qo.setDepartureDate(DateUtil.parseDate3("2015-07-30"));
		qo.setHotelIds("90725118");//杭州新延安饭店
		
		try {
			HotelDTO dto=this.hslHotelService.queryHotelDetail(qo);
			System.out.println("Hotel detail:"+JSON.toJSONString(dto));
			
		} catch (HotelException e) {
			e.printStackTrace();
		}
		
		
	}
	
	//测试明天日期
	/*public static void main(String[] args){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String formatdata=sdf.format(date);
		long time=date.getTime()+86400000*1;//第几天 传几
		Date date1=new Date(time);
		 
		 System.out.println(formatdata+","+sdf.format(date1));
	}*/
	
	/**
	 * @方法功能说明：测试下单
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月7日上午11:04:06
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@Test
	public void createHotelOrder(){
		JDOrderCreateCommand command=new JDOrderCreateCommand();
		OrderCreateCommandDTO orderCreateDTO=new OrderCreateCommandDTO ();
		/*command.setDealerOrderNO("1234556");
		command.setDealerProjcetId("hsl");*/
		command.setHotelName("东莞凤岗镇合意租房");
		//command.setHotelNO("21201389");
		command.setRatePlanName("不含早");
		command.setSupplierProjcetId("yl");
		command.setRoomName("普通房（不含发票）");
		command.setTotalPrice(30.0);
		
		
		Date date = new Date();	
		date =  Tool.addDate(date, 1);
		date.setHours(8);
		date.setMinutes(0);
		date.setSeconds(0);
		Date date2 =  Tool.addDate(date, 1);
		date2.setHours(14);
		date2.setMinutes(0);
		date2.setSeconds(0);
		Date date3 =  Tool.addDate(date, 0);
		date3.setHours(10);
		date3.setMinutes(0);
		date3.setSeconds(0);
		Date date4 =  Tool.addDate(date, 0);
		date4.setHours(14);
		date4.setMinutes(0);
		date4.setSeconds(0); 
		
		orderCreateDTO.setHotelId("90725118");
		orderCreateDTO.setRoomTypeId("0003");
		orderCreateDTO.setRatePlanId(848987);
		orderCreateDTO.setArrivalDate(date);
		orderCreateDTO.setDepartureDate(date2);
		orderCreateDTO.setCustomerType(EnumGuestTypeCode.All);
		orderCreateDTO.setPaymentType(EnumPaymentType.SelfPay);
		orderCreateDTO.setNumberOfRooms(1);
		orderCreateDTO.setNumberOfCustomers(1);
		orderCreateDTO.setEarliestArrivalTime(date3);
		orderCreateDTO.setLatestArrivalTime(date4);
		orderCreateDTO.setCurrencyCode(EnumCurrencyCode.RMB);
		orderCreateDTO.setTotalPrice(new BigDecimal(30.0));
		orderCreateDTO.setConfirmationType(EnumConfirmationType.SMS_cn);
		orderCreateDTO.setGuaranteeOrCharged(false);//是否已担保或预付款，true 要设置信用卡相关参数，否则不用
		//orderCreateDTO.setAffiliateConfirmationId("234567890");//合作伙伴订单确认号
		orderCreateDTO.setCustomerIPAddress("192.168.10.5");//客户访问ip
		orderCreateDTO.setIsForceGuarantee(false);//是否担保
		orderCreateDTO.setExtendInfo(null);
		ContactDTO contact=new ContactDTO();
		contact.setName("陈鑫亚");
		contact.setPhone("18629602906");
		orderCreateDTO.setContact(contact);
		contact.setMobile("18629602906");
		//房间信息,一个房间，一个客人
		List<CreateOrderRoomDTO> orderRoomsDTO=new ArrayList<CreateOrderRoomDTO>();
		CreateOrderRoomDTO roomDto=new CreateOrderRoomDTO();
		List<CustomerDTO> customers=new ArrayList<CustomerDTO>();
		//客人
		CustomerDTO customer=new CustomerDTO();
		customer.setName("陈鑫亚");
		customer.setMobile("18629602906");
		customer.setPhone("18629602906");
		customer.setGender(EnumGender.Maile);
		customer.setEmail("aaaa@qq.com");
		customers.add(customer);
		
		roomDto.setCustomers(customers);
		orderRoomsDTO.add(roomDto);
		orderCreateDTO.setOrderRoomsDTO(orderRoomsDTO);
		
		
		//信用卡:担保时 用到
//		CreditCardDTO creditCard=new CreditCardDTO ();
//		creditCard.setExpirationMonth(1);
//		creditCard.setExpirationYear(2018);
//		creditCard.setHolderName("赵文书");
//		creditCard.setIdNo("411381199009063124");
//		creditCard.setIdType(EnumIdType.IdentityCard);
//		creditCard.setNumber("4033910000000000");
//		orderCreateDTO.setCreditCard(creditCard);
		
		
		command.setOrderCreateDTO(orderCreateDTO);
		try {
			
			OrderCreateResultDTO resultDto=this.hslHotelOrderService.createHotelOrder(command);
			System.out.println("下单返回结果 resultDto："+JSON.toJSONString(resultDto));
			
		} catch (HotelException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @方法功能说明：酒店订单详情
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月15日上午9:24:17
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	//@Test
	public void hotelOrderDetail(){
		HotelOrderDetailQO qo=new HotelOrderDetailQO ();
		qo.setOrderId(311091618);
		try {
			HotelOrderDetailDTO dto=hslHotelOrderService.queryHotelOrderDetail(qo);
			
			System.out.println("订单详情："+JSON.toJSONString(dto));
			
		} catch (HotelException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * @方法功能说明：校验信用卡
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月15日上午9:15:23
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	//@Test
	public void testValidateCreditCard(){
		String creditCardNo="1234456789098711";
		//String creditCardNo="4033910000000000";
		CheckCreditCardNoCommand command=new CheckCreditCardNoCommand();
		command.setCreditCardNo(creditCardNo);
		try {
			CheckCreditCardNoDTO dto=hslHotelOrderService.checkCreditCardNo(command);
			
			System.out.println("信用卡验证结果："+JSON.toJSONString(dto));
		} catch (HotelException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
