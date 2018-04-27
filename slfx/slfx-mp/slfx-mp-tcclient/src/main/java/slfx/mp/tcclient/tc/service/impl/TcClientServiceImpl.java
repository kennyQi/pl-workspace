package slfx.mp.tcclient.tc.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.mp.tcclient.tc.dto.base.CityListByProvinceIdDto;
import slfx.mp.tcclient.tc.dto.base.CountyListByCityIdDto;
import slfx.mp.tcclient.tc.dto.base.DivisionInfoByIdDto;
import slfx.mp.tcclient.tc.dto.base.DivisionInfoByNameDto;
import slfx.mp.tcclient.tc.dto.base.ProvinceListDto;
import slfx.mp.tcclient.tc.dto.jd.NearbySceneryDto;
import slfx.mp.tcclient.tc.dto.jd.PriceCalendarDto;
import slfx.mp.tcclient.tc.dto.jd.SceneryDetailDto;
import slfx.mp.tcclient.tc.dto.jd.SceneryDto;
import slfx.mp.tcclient.tc.dto.jd.SceneryImageListDto;
import slfx.mp.tcclient.tc.dto.jd.SceneryPriceDto;
import slfx.mp.tcclient.tc.dto.jd.SceneryTrafficInfoDto;
import slfx.mp.tcclient.tc.dto.order.CancelSceneryOrderDto;
import slfx.mp.tcclient.tc.dto.order.SceneryOrderDetailDto;
import slfx.mp.tcclient.tc.dto.order.SceneryOrderListDto;
import slfx.mp.tcclient.tc.dto.order.SubmitSceneryOrderDto;
import slfx.mp.tcclient.tc.pojo.Response;
import slfx.mp.tcclient.tc.pojo.ResultNearbyScenery;
import slfx.mp.tcclient.tc.pojo.ResultPriceCalendar;
import slfx.mp.tcclient.tc.pojo.ResultSceneryDetail;
import slfx.mp.tcclient.tc.pojo.ResultSceneryImageList;
import slfx.mp.tcclient.tc.pojo.ResultSceneryList;
import slfx.mp.tcclient.tc.pojo.ResultSceneryPrice;
import slfx.mp.tcclient.tc.pojo.ResultSceneryTrafficInfo;
import slfx.mp.tcclient.tc.pojo.base.response.ResultCityListByProvinceId;
import slfx.mp.tcclient.tc.pojo.base.response.ResultCountyListByCityId;
import slfx.mp.tcclient.tc.pojo.base.response.ResultDivisionInfoById;
import slfx.mp.tcclient.tc.pojo.base.response.ResultDivisionInfoByName;
import slfx.mp.tcclient.tc.pojo.base.response.ResultProvinceList;
import slfx.mp.tcclient.tc.pojo.order.response.ResultCancelSceneryOrder;
import slfx.mp.tcclient.tc.pojo.order.response.ResultSceneryOrderDetail;
import slfx.mp.tcclient.tc.pojo.order.response.ResultSceneryOrderList;
import slfx.mp.tcclient.tc.pojo.order.response.ResultSubmitSceneryOrder;
import slfx.mp.tcclient.tc.service.ConnectService;
import slfx.mp.tcclient.tc.service.TcClientService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class TcClientServiceImpl implements TcClientService {

	@Resource
	private ConnectService tcConnectService = new TcConnectServiceImpl();

//	@SuppressWarnings("unused")
//	private static final Logger logger = LoggerFactory
//			.getLogger(TcClientServiceImpl.class);

	@Override
	public Response<ResultSceneryList> getSceneryList(SceneryDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultSceneryDetail> getSceneryDetail(SceneryDetailDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultSceneryTrafficInfo> getSceneryTrafficInfo(
			SceneryTrafficInfoDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultSceneryImageList> getSceneryImageList(
			SceneryImageListDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultNearbyScenery> getNearbyScenery(NearbySceneryDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultSceneryPrice> getSceneryPrice(SceneryPriceDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultPriceCalendar> getPriceCalendar(PriceCalendarDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultProvinceList> getProvinceList(ProvinceListDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultCityListByProvinceId> getCityListByProvinceId(
			CityListByProvinceIdDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultCountyListByCityId> getCountyListByCityId(
			CountyListByCityIdDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultDivisionInfoByName> getDivisionInfoByName(
			DivisionInfoByNameDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultDivisionInfoById> getDivisionInfoById(
			DivisionInfoByIdDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultSubmitSceneryOrder> submitSceneryOrder(
			SubmitSceneryOrderDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultSceneryOrderList> getSceneryOrderList(
			SceneryOrderListDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultSceneryOrderDetail> getSceneryOrderDetail(
			SceneryOrderDetailDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	@Override
	public Response<ResultCancelSceneryOrder> cancelSceneryOrder(
			CancelSceneryOrderDto dto) {
		return tcConnectService.getResponseByRequest(dto);
	}

	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// SceneryDto dto=new SceneryDto();
	// //dto.setServiceName("GetSceneryList");
	// //dto.setCityId("383");
	// //dto.setKeyword("杭州西湖");
	// //dto.setProvinceId("31");
	// //dto.setPageSize("33");
	// Response<ResultSceneryList> reponse=impl.getSceneryList(dto);
	// ResultSceneryList result= reponse.getResult();
	// List<Scenery> list=result.getScenery();
	// System.out.println(list.size()+"********************************");
	// for(int i=0;i<list.size();i++){
	// System.out.println(list.get(i).getSceneryName());
	//
	// if(list.get(i).getThemeList()!=null){
	// System.out.println(list.get(i).getThemeList().size()+"********************************");
	// for(int j=0;j<list.get(i).getThemeList().size();j++){
	// System.out.println(list.get(i).getThemeList().get(j).getThemeName());
	// }
	// }
	// System.out.println("**********************");
	// }
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// SceneryDetailDto dto=new SceneryDetailDto();
	// dto.setSceneryId("182270");
	// Response<ResultSceneryDetail> reponse=impl.getSceneryDetail(dto);
	// ResultSceneryDetail result= reponse.getResult();
	// System.out.println(result.getCityName());
	// System.out.println(result.getIntro());
	// System.out.println(result.getSceneryId());
	// System.out.println(result.getPayMode());
	// System.out.println(result.getIfUseCard());
	// System.out.println(result.getTheme().getThemeName());
	//
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// SceneryTrafficInfoDto dto=new SceneryTrafficInfoDto();
	//
	// dto.setSceneryId("180886");
	// Response<ResultSceneryTrafficInfo>
	// reponse=impl.getSceneryTrafficInfo(dto);
	// ResultSceneryTrafficInfo result= reponse.getResult();
	// System.out.println(result.getSceneryId());
	// System.out.println(result.getLongitude());
	// System.out.println(result.getLatitude());
	// System.out.println(result.getTraffic());
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// SceneryImageListDto dto=new SceneryImageListDto();
	// dto.setSceneryId("180886");
	// Response<ResultSceneryImageList> reponse=impl.getSceneryImageList(dto);
	// ResultSceneryImageList result= reponse.getResult();
	// System.out.println(result.getPage());
	// System.out.println(result.getPageSize());
	// System.out.println(result.getTotalCount());
	// System.out.println(result.getTotalPage());
	// System.out.println(result.getImage().get(0).getImagePath());
	// System.out.println(result.getImage().size());
	// System.out.println(result.getSizeCodeList().getSizeCode().size());
	// System.out.println(result.getSizeCodeList().getSizeCode().get(0).getSize());
	// System.out.println(result.getSizeCodeList().getSizeCode().get(2).getSizeCode());
	//
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// NearbySceneryDto dto=new NearbySceneryDto();
	//
	// dto.setSceneryId("180886");
	// Response<ResultNearbyScenery> reponse=impl.getNearbyScenery(dto);
	// ResultNearbyScenery result= reponse.getResult();
	// System.out.println(result.getPage());
	// System.out.println(result.getPageSize());
	// System.out.println(result.getScenery().get(0).getGrade());
	// System.out.println(result.getScenery().get(0).getId());
	// System.out.println(result.getScenery().get(0).getName());
	// System.out.println(result.getScenery().get(0).getAmount());
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// SceneryPriceDto dto=new SceneryPriceDto();
	// dto.setSceneryIds("47311");
	// Response<ResultSceneryPrice> reponse=impl.getSceneryPrice(dto);
	// ResultSceneryPrice result= reponse.getResult();
	// System.out.println(result.getScenery().get(0).getSceneryId());
	// System.out.println(result.getScenery().get(0).getNotice().size());
	// System.out.println(result.getScenery().get(0).getPolicy().get(0).getTcPrice()+"????");
	// System.out.println(result.getScenery().get(0).getPolicy().get(1).getTcPrice());
	// System.out.println(result.getScenery().get(0).getAhead().getTime());
	// System.out.println(result.getScenery().get(0).getAhead().getDay());
	// }
	public static void main(String[] args) {
		TcClientServiceImpl impl = new TcClientServiceImpl();
		PriceCalendarDto dto = new PriceCalendarDto();
		dto.setPolicyId(53846);
		dto.setStartDate("2014-07-29");
		Response<ResultPriceCalendar> reponse = impl.getPriceCalendar(dto);
		ResultPriceCalendar result = reponse.getResult();
		System.out.println(result.getNotice().size() + "???");
		System.out.println(result.getCalendar().get(0).getDate());
		System.out.println(result.getCalendar().size());
	}

	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// ProvinceListDto dto=new ProvinceListDto();
	//
	//
	// Response<ResultProvinceList> reponse=impl.getProvinceList(dto);
	// ResultProvinceList result= reponse.getResult();
	// System.out.println(result.getTotalCount());
	// System.out.println(result.getProvince().get(0).getEnName());
	// System.out.println(result.getProvince().get(0).getName());
	// System.out.println(result.getProvince().get(0).getPrefixLetter());
	// System.out.println(result.getProvince().get(0).getId());
	//
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// CityListByProvinceIdDto dto=new CityListByProvinceIdDto();
	// dto.setProvinceId(31);
	// Response<ResultCityListByProvinceId>
	// reponse=impl.getCityListByProvinceId(dto);
	// ResultCityListByProvinceId result= reponse.getResult();
	// System.out.println(result.getTotalCount());
	// System.out.println(result.getCity().get(0).getEnName());
	// System.out.println(result.getCity().get(0).getName());
	// System.out.println(result.getCity().get(0).getPrefixLetter());
	// System.out.println(result.getCity().get(0).getId());
	//
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// CountyListByCityIdDto dto=new CountyListByCityIdDto();
	//
	// dto.setCityId(392);
	// Response<ResultCountyListByCityId>
	// reponse=impl.getCountyListByCityId(dto);
	// ResultCountyListByCityId result= reponse.getResult();
	// System.out.println(result.getTotalCount());
	// System.out.println(result.getCounty().get(0).getEnName());
	// System.out.println(result.getCounty().get(0).getName());
	// System.out.println(result.getCounty().get(0).getPrefixLetter());
	// System.out.println(result.getCounty().get(0).getId());
	//
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// DivisionInfoByNameDto dto=new DivisionInfoByNameDto();
	//
	// dto.setDivisionName("临城新区");
	// Response<ResultDivisionInfoByName>
	// reponse=impl.getDivisionInfoByName(dto);
	// ResultDivisionInfoByName result=reponse.getResult();
	// System.out.println(result.getEnName());
	// System.out.println(result.getName());
	// System.out.println(result.getShortName());
	// System.out.println(result.getDivisionLevel());
	// System.out.println(result.getId());
	// System.out.println(result.getParentId());
	//
	//
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// DivisionInfoByIdDto dto=new DivisionInfoByIdDto();
	// dto.setDivisionId(5815);
	// Response<ResultDivisionInfoById> reponse=impl.getDivisionInfoById(dto);
	// ResultDivisionInfoById result=(ResultDivisionInfoById)
	// reponse.getResult();
	// System.out.println(result.getEnName());
	// System.out.println(result.getName());
	// System.out.println(result.getShortName());
	// System.out.println(result.getDivisionLevel());
	// System.out.println(result.getId());
	// System.out.println(result.getParentId());
	// }

	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// SubmitSceneryOrderDto dto=new SubmitSceneryOrderDto();
	//
	// dto.setbAddress("测试地址2");
	// dto.setbEmail("ceshi@163.com");
	// dto.setbMan("测试人2");
	// dto.setbMobile("15336525966");
	// dto.setbPostCode("311700");
	// dto.setIdCard("330127198701121833");
	// dto.setOrderIP("127.0.0.1");
	// dto.setPolicyId(76639);
	// dto.setSceneryId("180482");
	// dto.setTickets(1);
	// dto.settMobile("15336525966");
	// dto.settName("测试人2");
	// dto.setTravelDate(addDayDate(new Date(), 5));
	// List<Guest> list=new ArrayList<Guest>();
	// Guest g=new Guest();
	// g.setGMobile("15336525966");
	// g.setGName("测试人2");
	// Guest g1=new Guest();
	// g1.setGMobile("15336525966");
	// g1.setGName("李四");
	// list.add(g);
	// list.add(g1);
	// dto.setOtherGuest(list);
	// Response<ResultSubmitSceneryOrder> reponse=impl.submitSceneryOrder(dto);
	// ResultSubmitSceneryOrder result= reponse.getResult();
	// System.out.println(result.getMseconds());
	// System.out.println(result.getSerialId());st53cf6958210037d528
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// SceneryOrderListDto dto=new SceneryOrderListDto();
	//
	// dto.setBookingMan("测试人");
	// Response<ResultSceneryOrderList> reponse=impl.getSceneryOrderList(dto);
	// ResultSceneryOrderList result= reponse.getResult();
	// List<Order> orderList=result.getOrder();
	// System.out.println(orderList.size()+"!@!@!@!@!@");
	// for(int i=0;i<orderList.size();i++){
	// System.out.println(orderList.get(i).getBookingMan());
	// System.out.println(orderList.get(i).getBookingMobile());
	// System.out.println(orderList.get(i).getCreateDate());
	// System.out.println(orderList.get(i).getGuestMobile());
	// System.out.println(orderList.get(i).getGuestName());
	// System.out.println(orderList.get(i).getSceneryName());
	// System.out.println(orderList.get(i).getSerialId());
	// System.out.println(orderList.get(i).getTicketName());
	// System.out.println(orderList.get(i).getTravelDate());
	// System.out.println(orderList.get(i).getCurrentPayStatus());
	// }
	// }

	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// SceneryOrderDetailDto dto=new SceneryOrderDetailDto();
	//
	// dto.setSerialIds("st53cf6958210037d528");
	// Response<ResultSceneryOrderDetail>
	// reponse=impl.getSceneryOrderDetail(dto);
	// ResultSceneryOrderDetail result=(ResultSceneryOrderDetail)
	// reponse.getResult();
	//
	// System.out.println(result.getOrder().get(0).getBookingMan());
	// System.out.println(result.getOrder().get(0).getBookingMobile());
	// System.out.println(result.getOrder().get(0).getCreateDate());
	// System.out.println(result.getOrder().get(0).getGuestMobile());
	// System.out.println(result.getOrder().get(0).getGuestName());
	// System.out.println(result.getOrder().get(0).getSceneryName());
	// System.out.println(result.getOrder().get(0).getSerialId());
	// System.out.println(result.getOrder().get(0).getTicketName());
	// System.out.println(result.getOrder().get(0).getTravelDate());
	// System.out.println(result.getOrder().get(0).getCurrentPayStatus());
	// }
	// public static void main(String[] args){
	// TcClientServiceImpl impl=new TcClientServiceImpl();
	// CancelSceneryOrderDto dto=new CancelSceneryOrderDto();
	//
	// //dto.setSerialIds("st53c780da21002bc205");
	// dto.setSerialId("st53cf61262100379827");
	// dto.setCancelReason(1);
	// Response<ResultCancelSceneryOrder> reponse=impl.cancelSceneryOrder(dto);
	// ResultCancelSceneryOrder result= reponse.getResult();
	//
	// }
	/**
	 * 时间加上对应的天数
	 * 
	 * @param date
	 * @return
	 */
	public static Date addDayDate(Date date, int day) {
		String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
		if (ids.length == 0)
			System.exit(0);

		SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

		pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY,
				2 * 60 * 60 * 1000);

		Calendar calendar = new GregorianCalendar(pdt);
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

}
