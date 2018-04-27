package service.test;

import hg.common.util.DateUtil;
import hsl.app.dao.line.LineDAO;
import hsl.app.service.local.line.LineLocalService;
import hsl.app.service.local.line.LineOrderLocalService;
import hsl.domain.model.xl.DayRoute;
import hsl.domain.model.xl.Line;
import hsl.domain.model.xl.LineImage;
import hsl.domain.model.xl.LineRouteInfo;
import hsl.pojo.command.line.HslCreateLineOrderCommand;
import hsl.pojo.command.line.UpdateLineOrderStatusCommand;
import hsl.pojo.dto.jp.JPOrderDTO;
import hsl.pojo.dto.jp.JPOrderStatusConstant;
import hsl.pojo.dto.line.order.LineOrderBaseInfoDTO;
import hsl.pojo.dto.line.order.LineOrderDTO;
import hsl.pojo.dto.line.order.LineOrderLinkInfoDTO;
import hsl.pojo.dto.line.order.LineOrderStatusDTO;
import hsl.pojo.dto.line.order.LineOrderTravelerDTO;
import hsl.pojo.dto.line.order.LineOrderUserDTO;
import hsl.pojo.exception.LineException;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.spi.inter.api.jp.JPService;
import hsl.spi.inter.line.HslLineOrderService;
import hsl.spi.inter.line.HslLineService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.dto.line.LineImageDTO;
import slfx.xl.pojo.dto.line.LineRouteInfoDTO;
import slfx.xl.pojo.dto.route.DayRouteDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestLineOrderService {
	
	@Resource
	private LineOrderLocalService lineOrderLocalService;
	@Resource
	private HslLineOrderService lineOrderService;
	@Resource
	private LineLocalService lineLocalService;
	
	@Resource
	private LineDAO lineDAO;
	@Resource
	private JPService jpservice;
	
	@Test
	public void testCreateLineOrder(){
		//HslJPOrderQO orderQO = new HslJPOrderQO();
		//Integer[] status={Integer.valueOf(JPOrderStatusConstant.SHOP_PAY_WAIT),Integer.valueOf(JPOrderStatusConstant.SHOP_PAY_WAIT),
		//				Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_DEALING),Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_SUCC)};
		//orderQO.setSts(status);

		// 创建订单之前先进行一次订单查询，查询订单库中是否存在在当日该趟航班用该身份证就行下订单操作
		//List<JPOrderDTO> jporderList = jpservice.queryOrder(orderQO);
		//System.out.println("ddddddddddd"+jporderList.size());
		HslCreateLineOrderCommand cmd=new HslCreateLineOrderCommand();
		//出发日期
		String travleTime="2015-05-20 00:00:00";		
		//成人人数
		int adultNo=1;
		//儿童人数
		int childNo=0;
		
		//线路订单基本信息
		LineOrderBaseInfoDTO baseInfoDto=new LineOrderBaseInfoDTO();
		
		
		double adultUnitPrice=300.0;
		double childUnitPrice=0.0;
		baseInfoDto.setAdultNo(adultNo);
		baseInfoDto.setAdultUnitPrice(adultUnitPrice);
		baseInfoDto.setChildNo(childNo);
		baseInfoDto.setChildUnitPrice(childUnitPrice);
		baseInfoDto.setSalePrice(adultUnitPrice*adultNo);
		baseInfoDto.setBargainMoney(50.0);
		baseInfoDto.setSupplierAdultUnitPrice(200.0);
		baseInfoDto.setSupplierPrice(200.0);
		
		
		baseInfoDto.setTravelDate(DateUtil.parseDateTime(travleTime));
		
		baseInfoDto.setCreateDate(new Date());
		cmd.setBaseInfo(baseInfoDto);
		//联系人信息
		LineOrderLinkInfoDTO linkInfo=new LineOrderLinkInfoDTO();
		linkInfo.setLinkName("小明");
		linkInfo.setLinkMobile("15291967771");
		linkInfo.setEmail("zzy511@qq.com");
		cmd.setLinkInfo(linkInfo);
		//旅游人
		List<LineOrderTravelerDTO> travelerList=new ArrayList<LineOrderTravelerDTO>();
		LineOrderTravelerDTO traveler=new LineOrderTravelerDTO();
		traveler.setName("阿毛");
		traveler.setMobile("18629602906");
		traveler.setType(1);
		traveler.setIdNo("78585558885555555555");
		traveler.setIdType(1);
		traveler.setSingleSalePrice(adultUnitPrice);
		traveler.setSingleBargainMoney(childUnitPrice);
		travelerList.add(traveler);
		
		LineOrderTravelerDTO traveler2=new LineOrderTravelerDTO();
		traveler2.setName("张三");
		traveler2.setMobile("18525225222");
		traveler2.setType(1);
		traveler2.setIdNo("11111111111111111111");
		traveler2.setIdType(1);
		travelerList.add(traveler2);
		cmd.setTravelerList(travelerList);
	

		cmd.setLineID("a98360f274834bbfbd728782b857cfa4"); //所属线路ID
		cmd.setSource(1);
		//线路下单用户
		LineOrderUserDTO lineOrderUser=new LineOrderUserDTO();
		lineOrderUser.setLoginName("admin");
		lineOrderUser.setMobile("13222222222");
		lineOrderUser.setUserId("edcaa54d670042ec8663bba3ae10e5f2");
		cmd.setLineOrderUser(lineOrderUser);
		try {
			
			LineOrderDTO lineOrderDto=this.lineOrderLocalService.createLineOrder(cmd);
			
			System.out.println(lineOrderDto);
		} catch (LineException e) {
			
			e.printStackTrace();
		}
		
		
		
		
	}
	//@Test
	public void queryLineList(){
		HslLineOrderQO qo=new HslLineOrderQO();
		qo.setOrderId("466da82698fe418fad55284b4f6c7b2a");
		qo.setUserId("edcaa54d670042ec8663bba3ae10e5f2");
		List<LineOrderDTO> lineList=lineOrderService.queryList(qo);
		
		
		System.out.println(lineList.size());
	}
	
	//@Test
	public void updateLineOrderStatus(){
		UpdateLineOrderStatusCommand command=new UpdateLineOrderStatusCommand ();
		command.setDealerOrderCode("B505111133110000");
		command.setOrderStatus(Integer.parseInt("2001"));
		command.setPayStatus(Integer.parseInt("2102"));
		
		try {
			boolean res=this.lineOrderService.updateLineOrderStatus(command);
			System.out.println(res);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	//@Test
	public void testUpdateLineOrderStatus(){
		XLUpdateOrderStatusMessageApiCommand command=new XLUpdateOrderStatusMessageApiCommand();
		command.setLineOrderID("B505111133110000");
		Set<slfx.xl.pojo.dto.order.LineOrderTravelerDTO> travelers =
				new HashSet<slfx.xl.pojo.dto.order.LineOrderTravelerDTO> ();
		slfx.xl.pojo.dto.order.LineOrderTravelerDTO travel=new slfx.xl.pojo.dto.order.LineOrderTravelerDTO();
		travel.setId("90c58436b95d47cab5fafb1fa5dc0233");
		slfx.xl.pojo.dto.order.XLOrderStatusDTO xlOrderStatus=new slfx.xl.pojo.dto.order.XLOrderStatusDTO();
		xlOrderStatus.setStatus(2002);
		xlOrderStatus.setPayStatus(2103);
		travel.setXlOrderStatus(xlOrderStatus);
		
		slfx.xl.pojo.dto.order.LineOrderTravelerDTO travel2=new slfx.xl.pojo.dto.order.LineOrderTravelerDTO();
		travel2.setId("f5670cf842b642bbb55308481b87b1b8");
		travel2.setXlOrderStatus(xlOrderStatus);
		
		travelers.add(travel);
		travelers.add(travel2);
		
		command.setTravelers(travelers);
		
		try {
			boolean res=this.lineOrderService.updateLineOrderStatus(command);
		} catch (LineException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/*//@Test
	public void TestDeleteLineImage(){
		
		LineDTO line=new LineDTO();
		line.setId("84dab0d2bfb34ec4907b662cd5f5be8c");
		line.setLineSnapshotId(UUID.randomUUID().toString());
		 LineRouteInfoDTO routeInfo=new LineRouteInfoDTO();
		List<DayRouteDTO> dayRouteList=new ArrayList<DayRouteDTO>();
		DayRouteDTO dayRoute=new DayRouteDTO();
		dayRoute.setId("02c901f1342f4ec386e9d52a41bdf618");
		
		List<LineImageDTO> lineImageList=new ArrayList<LineImageDTO>();
		LineImageDTO lineImage=new LineImageDTO();
		lineImage.setId(UUID.randomUUID().toString());
		lineImage.setName("aaaa.jpg");
		lineImage.setDayRoute(dayRoute);
		
		lineImageList.add(lineImage);
		dayRoute.setLineImageList(lineImageList);
		dayRouteList.add(dayRoute);
		routeInfo.setDayRouteList(dayRouteList);
		line.setRouteInfo(routeInfo);
		
		
		this.lineLocalService.SaveOrUpdateLine(line);
		
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
		
	}*/
	
	
	
}	
