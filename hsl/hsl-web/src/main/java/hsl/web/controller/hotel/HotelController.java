package hsl.web.controller.hotel;
import hg.common.page.Pagination;
import hg.log.util.HgLogger;
import hsl.pojo.command.clickRecord.PLZXClickRecordCommand;
import hsl.pojo.command.hotel.CheckCreditCardNoCommand;
import hsl.pojo.command.hotel.JDOrderCreateCommand;
import hsl.pojo.dto.hotel.HotelDTO;
import hsl.pojo.dto.hotel.JDLocalHotelDetailDTO;
import hsl.pojo.dto.hotel.ListRatePlan;
import hsl.pojo.dto.hotel.RoomDTO;
import hsl.pojo.dto.hotel.order.CheckCreditCardNoDTO;
import hsl.pojo.dto.hotel.order.HotelOrderUserDTO;
import hsl.pojo.dto.hotel.order.OrderCreateResultDTO;
import hsl.pojo.dto.hotel.util.CommericalLocationDTO;
import hsl.pojo.dto.hotel.util.DistrictDTO;
import hsl.pojo.dto.hotel.util.HotelGeoDTO;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.log.PLZXClickRecord;
import hsl.pojo.qo.hotel.CommericalLocationQO;
import hsl.pojo.qo.hotel.DistrictQO;
import hsl.pojo.qo.hotel.HotelGeoQO;
import hsl.pojo.qo.hotel.JDHotelDetailQO;
import hsl.pojo.qo.hotel.JDHotelListQO;
import hsl.pojo.qo.hotel.JDLocalHotelDetailQO;
import hsl.pojo.qo.log.PLZXClickRecordQo;
import hsl.pojo.util.enumConstants.EnumConfirmationType;
import hsl.pojo.util.enumConstants.EnumCurrencyCode;
import hsl.pojo.util.enumConstants.EnumGuestTypeCode;
import hsl.pojo.util.enumConstants.EnumPaymentType;
import hsl.spi.inter.clickRecord.PLZXClickRecordService;
import hsl.spi.inter.hotel.HotelGeoService;
import hsl.spi.inter.hotel.HslBusinessZoneService;
import hsl.spi.inter.hotel.HslDistrictService;
import hsl.spi.inter.hotel.HslHotelOrderService;
import hsl.spi.inter.hotel.HslHotelService;
import hsl.spi.inter.line.HslLineService;
import hsl.web.controller.BaseController;
import hsl.web.util.TokenHandler;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.JedisPool;
import slfx.jd.pojo.dto.ylclient.hotel.PositionDTO;
import slfx.jd.pojo.dto.ylclient.order.CreateOrderRoomDTO;
import slfx.jd.pojo.dto.ylclient.order.CustomerDTO;
import slfx.jd.pojo.system.enumConstants.EnumGender;
import slfx.jd.pojo.system.enumConstants.EnumIdType;
import slfx.jd.pojo.system.enumConstants.EnumSortType;

import com.alibaba.fastjson.JSON;
/**
 * @类功能说明：酒店控制层
 * @类修改者：
 * @修改日期：2015年6月30日上午10:50:57
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年6月30日上午10:50:57
 */
@Controller
@RequestMapping("/jd")
public class HotelController extends BaseController{

	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private HotelGeoService hotelGeoService;
	@Autowired
	private HslHotelService hslHotelService;
	@Autowired
	private HslDistrictService hslDistrictService;
	@Autowired
	private HslBusinessZoneService hslBusinessZoneService;
	@Autowired
	private HslHotelOrderService hslHotelOrderService;
	
	@Autowired
	private PLZXClickRecordService plzxClickRecordService;
	/*@Autowired
	private PLZXClickRecordService plzxClickRecordService;*/

	/*	private List<HotelGeo> getHotelGeos(){//商圈
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		String str = jedis.get("HotelGeoList");
		List<HotelGeo> hotelGeoList=JSONArray.parseArray(str, HotelGeo.class);
		if(hotelGeoList!=null&&hotelGeoList.size()>0){
			//将jedis连接返回给jedisPool中
			jedisPool.returnResource(jedis);
			return hotelGeoList;
		}else{
			List<HotelGeo> hotelGeos=HotelUtil.getInstance().getHotelGeo();
			jedis.set("HotelBrandList",JSON.toJSONString(hotelGeos));
			//将jedis连接返回给jedisPool中
			jedisPool.returnResource(jedis);
			return hotelGeos;
		}
	}
	private List<HotelBrand> getHotelBrands(){//酒店品牌
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		String str = jedis.get("HotelBrandList");
		List<HotelBrand> hotelBrandList=JSONArray.parseArray(str, HotelBrand.class);
		if(hotelBrandList!=null&&hotelBrandList.size()>0){
			//将jedis连接返回给jedisPool中
			jedisPool.returnResource(jedis);
			return hotelBrandList;
		}else{
			List<HotelBrand> hotelBrands=HotelUtil.getInstance().getHotelBrands();
			jedisPool.returnResource(jedis);
			return hotelBrands;
		}
	}*/
	/**
	 * 
	 * @方法功能说明：返回时间
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-1上午11:19:52
	 * @参数：@param msg day
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getDate(String msg,int day){//msg 是否需要返回两个日期 day返回当天和n天日期
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String formatdata=sdf.format(date);
		if(msg.equals("")){
			return formatdata;
		}else{
			long time=date.getTime()+86400000*day;
			Date date1=new Date(time);
			return formatdata+","+sdf.format(date1);
		}

	}
	/**
	 * 
	 * @方法功能说明：查询商圈
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-3下午4:29:37
	 * @参数：@param request
	 * @参数：@param responsel
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping("/getHotelGeo")
	@ResponseBody
	public Object getHotelGeo(HttpServletRequest request, HttpServletResponse responsel){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			String city=request.getParameter("city");
			HotelGeoQO hotelGeoQO=new HotelGeoQO();
			hotelGeoQO.setCityName(java.net.URLDecoder.decode(city,"UTF-8"));
			HgLogger.getInstance().info("zhaows","getHotelGeo->商圈查询条件"+ JSON.toJSONString(hotelGeoQO));
			HotelGeoDTO hotelGeoDTO=hotelGeoService.queryUnique(hotelGeoQO);
			HgLogger.getInstance().info("zhaows","getHotelGeo->商圈查询"+ JSON.toJSONString(hotelGeoDTO));
			if(hotelGeoDTO!=null){
				map.put("hotelGeoDTO", hotelGeoDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","getHotelGeo->商圈查询失败"+HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(map);
	}
	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response, JDHotelListQO jdHotelListQO,Model model,
			@RequestParam(value="low",required=false) String low,@RequestParam(value="high",required=false) String high,
			@RequestParam(value="arrivalTime",required=false) String arrivalTime,
			@RequestParam(value="departureTime",required=false) String departureTime
			){
		String city="";
		String rate="";
		String rank="";
		String jdmc="";
		String sqwz="";
		String sqrido="";
		String longitude="";
		String latitude=""; 
		try {
			city=jdHotelListQO.getCityId();
			if(city==null||city.equals("")){
				city="杭州";
				jdHotelListQO.setCityId("杭州");
			}
			Pagination pagination = new Pagination();
			String prices=request.getParameter("prices");//价格排序标识符
			String ranks=request.getParameter("ranks");//酒店星级排序标识符
			jdmc=request.getParameter("jdmc");//酒店名称
			sqwz=request.getParameter("sqwz");//input商圈位置
			sqrido=request.getParameter("sqrido");//radio商圈查询
			
			longitude=request.getParameter("longitude");//经度
			latitude=request.getParameter("latitude");//纬度
			
			/*************************经度纬度附近酒店查询***************************/
			if(StringUtils.isNotBlank(longitude)&&StringUtils.isNotBlank(latitude)){
				PositionDTO position=new PositionDTO ();
				position.setLongitude(BigDecimal.valueOf(Double.parseDouble(longitude)));
				position.setLatitude(BigDecimal.valueOf(Double.parseDouble(latitude)));
				position.setRadius(1000);
				jdHotelListQO.setPosition(position);
				
				model.addAttribute("longitude", longitude);
				model.addAttribute("latitude", latitude);
			}
			
			/*************************结束经度纬度附近酒店查询***************************/
			
			
			/*************************酒店名称查询or商圈位置***************************/
			if(StringUtils.isNotBlank(jdmc)&&(StringUtils.isNotBlank(sqwz)||StringUtils.isNotBlank(sqrido))){
				jdHotelListQO.setQueryText(jdmc);
			}else if(StringUtils.isNotBlank(sqwz)&&StringUtils.isNotBlank(sqrido)){//如果input商圈位置和radio商圈查询都存在radio商圈查询优先
				jdHotelListQO.setQueryText(sqrido);
			}else{
				if(StringUtils.isNotBlank(jdmc)){
					jdHotelListQO.setQueryText(jdmc);
				}
				if(StringUtils.isNotBlank(sqrido)){
					jdHotelListQO.setQueryText(sqrido);
				}
				if(StringUtils.isNotBlank(sqwz)){
					jdHotelListQO.setQueryText(sqwz);
				}
			}
			model.addAttribute("jdmc", jdmc);
			/*************************end酒店名称查询or商圈位置***************************/
			/*************************价格区间***************************/
			if(StringUtils.isNotBlank(low)||StringUtils.isNotBlank(high)){
				if(StringUtils.isBlank(low)){
					jdHotelListQO.setLowRate(0);
				}else{
					jdHotelListQO.setLowRate(Integer.parseInt(low));
				}
				if(StringUtils.isBlank(high)){
					jdHotelListQO.setHighRate(99999);
				}else{
					jdHotelListQO.setHighRate(Integer.parseInt(high));
				}
			}
			/*if(StringUtils.isNotBlank(high)){
				jdHotelListQO.setHighRate(Integer.parseInt(high));
			}*/
			/*************************排序***************************/
			if(prices!=null&&prices.equals("1")){
				jdHotelListQO.setSort(Enum.valueOf(EnumSortType.class, "RateDesc"));
				rate="1";
			}else if(prices!=null&&prices.equals("0")){
				jdHotelListQO.setSort(Enum.valueOf(EnumSortType.class, "RateAsc"));
				rate="0";
			}else{
				jdHotelListQO.setSort(Enum.valueOf(EnumSortType.class, "Default"));
			}
			if(ranks!=null&&ranks.equals("0")){
				rank="0";
			}else if(ranks!=null&&ranks.equals("1")){
				jdHotelListQO.setSort(Enum.valueOf(EnumSortType.class, "StarRankDesc"));
				rank="1";
			}else{
				jdHotelListQO.setSort(Enum.valueOf(EnumSortType.class, "Default"));
			}

			/*************************排序end***************************/
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

			/****************查询城市citycode***********************/
			HotelGeoQO hotelGeoQO=new HotelGeoQO();
			hotelGeoQO.setCityName(jdHotelListQO.getCityId());
			HgLogger.getInstance().info("zhaows","jdlist->城市查询"+ JSON.toJSONString(jdHotelListQO));
			HotelGeoDTO hotelGeoDTO=hotelGeoService.queryUnique(hotelGeoQO);
			HgLogger.getInstance().info("zhaows","jdlist->城市查询返回结果"+ JSON.toJSONString(hotelGeoDTO));
			if(StringUtils.isBlank(arrivalTime)||StringUtils.isBlank(departureTime)){
				String  sameData=this.getDate("1",1);
				String[] sameDatas=sameData.split(",");
				jdHotelListQO.setArrivalDate(sdf.parse(sameDatas[0]));
				jdHotelListQO.setDepartureDate(sdf.parse(sameDatas[1]));
			}else{
				jdHotelListQO.setArrivalDate(sdf.parse(arrivalTime));
				jdHotelListQO.setDepartureDate(sdf.parse(departureTime));
			}

			/**********************查询酒店列表*********************************/
			Integer pageNo=1;
			Integer pageSize=5;
			if(StringUtils.isNotBlank(request.getParameter("pageNum"))&&StringUtils.isNotBlank(request.getParameter("pageSize"))){
				pageNo=Integer.parseInt(request.getParameter("pageNum"));
				pageSize=Integer.parseInt(request.getParameter("pageSize"));
			}
			HgLogger.getInstance().info("zhaows","jdlist->酒店查询条件"+ JSON.toJSONString(jdHotelListQO));
			pageNo=pageNo==null?new Integer(1):pageNo;
			pageSize=pageSize==null?new Integer(5):pageSize;
			jdHotelListQO.setCityId(hotelGeoDTO.getCityCode());
			jdHotelListQO.setCustomerType("None");
			jdHotelListQO.setResultType("1,2,3");
			pagination.setCondition(jdHotelListQO);
			pagination.setCheckPassLastPageNo(false);
			jdHotelListQO.setPageSize(pageSize);
			jdHotelListQO.setPageIndex(pageNo);
			pagination=hslHotelService.queryPagination(pagination);
			/**********************得到返回信息根据codeid查询区域*********************************/
			List<HotelDTO> hotelDTO=(List<HotelDTO>) pagination.getList();
			if(hotelDTO!=null){
				for(HotelDTO hotel:hotelDTO){
					//查询商业区名称
					CommericalLocationQO commericalLocationQO=new CommericalLocationQO();
					commericalLocationQO.setBusinessZoneId(hotel.getDetail().getBusinessZone());
					CommericalLocationDTO commericalLocationDTO=this.hslBusinessZoneService.queryUnique(commericalLocationQO);
					if(commericalLocationDTO!=null){
						hotel.getDetail().setBusinessZone(commericalLocationDTO.getName());
					}

					//查询行政区名称
					DistrictQO districtQO=new DistrictQO();
					districtQO.setDistrictId(hotel.getDetail().getDistrict());
					districtQO.setHotelGeoId(hotel.getDetail().getCity());
					DistrictDTO districtDTO=this.hslDistrictService.queryUnique(districtQO);
					if(districtDTO!=null){
						hotel.getDetail().setDistrict(districtDTO.getName());
					}
				}
				model.addAttribute("pagination", pagination.getList());
				model.addAttribute("totalCount", pagination.getTotalCount());
				model.addAttribute("pageNo", pageNo);
				
				model.addAttribute("jdHotelListQO", jdHotelListQO);
			}
			
			//查询用户点击记录
			UserDTO userDTO=getUserBySession(request);
			if(null!=userDTO){
				PLZXClickRecordQo plzxClickRecordQo=new PLZXClickRecordQo();
				plzxClickRecordQo.setUserId(userDTO.getId());
				List<HotelDTO> browseHotels=this.hslHotelService.queryUserClickRecord(plzxClickRecordQo);
				model.addAttribute("browseHotels", browseHotels);
			}
			
			
		}catch (Exception e) {
			HgLogger.getInstance().error("zhaows","jdlist->查询酒店列表失败"+ HgLogger.getStackTrace(e));
			e.printStackTrace();
			model.addAttribute("totalCount", 0);
			model.addAttribute("pageNo", 1);
			model.addAttribute("jdHotelListQO", jdHotelListQO);
		}finally{
			model.addAttribute("cityName", city);
			model.addAttribute("rate", rate);
			model.addAttribute("ranks", rank);
			model.addAttribute("sqwz", sqwz);
			model.addAttribute("jdmc", jdmc);
			model.addAttribute("sqrido", sqrido);
			model.addAttribute("jdHotelListQO", jdHotelListQO);

		}
		
		
		return "hotel/hotel.html";
	}
	/**
	 * 
	 * @方法功能说明：查询酒店详情or房间详情
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-8上午9:06:01
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param jdHotelListQO
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, JDHotelDetailQO jDHotelDetailQO,Model model,
			@RequestParam(value="arrivalTime",required=false) String arrivalTime,
			@RequestParam(value="departureTime",required=false) String departureTime) 
			{
			Date arrDate=null;
			Date depDate=null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isBlank(arrivalTime)||StringUtils.isBlank(departureTime)){
				String  sameData=this.getDate("1",1);
				String[] sameDatas=sameData.split(",");
				arrDate=sdf.parse(sameDatas[0]);
				depDate=sdf.parse(sameDatas[1]);
				
			}else{
				arrDate=sdf.parse(arrivalTime);
				depDate=sdf.parse(departureTime);
			}
			jDHotelDetailQO.setArrivalDate(arrDate);
			jDHotelDetailQO.setDepartureDate(depDate);
			jDHotelDetailQO.setOptions("1,2,3,4");
			String roomTypeId=request.getParameter("roomTypeId");
			if(StringUtils.isNotBlank(roomTypeId)){
				model.addAttribute("roomTypeId",roomTypeId);
			}
			String ratePlan=request.getParameter("ratePlan");
			if(StringUtils.isNotBlank(ratePlan)){
				model.addAttribute("ratePlanId",ratePlan);
			}
			jDHotelDetailQO.setRoomTypeId(null);
			jDHotelDetailQO.setRatePlanId(0);
			HgLogger.getInstance().info("zhaows","detail->查询酒店详情"+ JSON.toJSONString(jDHotelDetailQO));
			
			
			UserDTO userDTO=getUserBySession(request);
			//查询用户点击记录
			if(null!=userDTO){
				PLZXClickRecordQo plzxClickRecordQo=new PLZXClickRecordQo();
				plzxClickRecordQo.setUserId(userDTO.getId());
				List<HotelDTO> browseHotels=this.hslHotelService.queryUserClickRecord(plzxClickRecordQo);
				model.addAttribute("browseHotels", browseHotels);
			}
			
	
			HotelDTO hotelDTO=hslHotelService.queryHotelDetail(jDHotelDetailQO);
			HgLogger.getInstance().info("zhaows","detail->返回酒店详情"+ JSON.toJSONString(hotelDTO));
			if(StringUtils.isNotBlank(hotelDTO.getDetail().getTraffic())){
				hotelDTO.getDetail().setTraffic(hotelDTO.getDetail().getTraffic().replaceAll("\n", "<br/>"));
				
			}
			if(StringUtils.isNotBlank(hotelDTO.getDetail().getIntroEditor())){
				hotelDTO.getDetail().setIntroEditor(hotelDTO.getDetail().getIntroEditor().replaceAll("\n", "<br/>"));
			}
			
			//创建用户浏览记录
			if(null!=userDTO){
				PLZXClickRecordCommand command=new PLZXClickRecordCommand();
				command.setObjectId(hotelDTO.getHotelId());
				command.setObjectType(PLZXClickRecord.PLZX_CLICK_RECORD_HOTEL);
				command.setUrl("/jd/detail");
				command.setUserId(userDTO.getId());
				command.setFromIP(request.getRemoteHost());
				command.setArrivalDate(arrDate);
				command.setDepartureDate(depDate);
				plzxClickRecordService.createPLZXClickRecord(command);
			}
			
			model.addAttribute("hotelDTO", hotelDTO);
			return "hotel/hotel_detail.html";
			
			
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","detail->查询酒店详情失败"+ HgLogger.getStackTrace(e));
		}
		return "ticket/error.jsp";
	}
	
	/**
	 * @方法功能说明：查询房间详情
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月24日下午2:37:15
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param jDHotelDetailQO
	 * @参数：@param model
	 * @参数：@param arrivalTime
	 * @参数：@param departureTime
	 * @参数：@param mark
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/bookHotel")
	public String bookHotel(HttpServletRequest request, HttpServletResponse response, JDHotelDetailQO jDHotelDetailQO,Model model,
			@RequestParam(value="arrivalTime",required=false) String arrivalTime,
			@RequestParam(value="departureTime",required=false) String departureTime){
			Date arrDate=null;
			Date depDate=null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isBlank(arrivalTime)||StringUtils.isBlank(departureTime)){
				String  sameData=this.getDate("1",1);
				String[] sameDatas=sameData.split(",");
				arrDate=sdf.parse(sameDatas[0]);
				depDate=sdf.parse(sameDatas[1]);
				
			}else{
				arrDate=sdf.parse(arrivalTime);
				depDate=sdf.parse(departureTime);
			}
			jDHotelDetailQO.setArrivalDate(arrDate);
			jDHotelDetailQO.setDepartureDate(depDate);
			jDHotelDetailQO.setOptions("1,2,3,4");
			
			HgLogger.getInstance().info("zhaows","detail->查询房间详情"+ JSON.toJSONString(jDHotelDetailQO));
			String roomTypeId=request.getParameter("roomTypeId");
			if(StringUtils.isNotBlank(roomTypeId)){
				jDHotelDetailQO.setRoomTypeId(roomTypeId);
			}
			String ratePlan=request.getParameter("ratePlan");
			if(StringUtils.isNotBlank(ratePlan)){
				jDHotelDetailQO.setRatePlanId(Integer.parseInt(ratePlan));
			}
			HttpSession session=request.getSession();
			TokenHandler.generateGUID(session);
			HotelDTO hotelDTO=hslHotelService.queryHotelDetail(jDHotelDetailQO);
			
			
			HgLogger.getInstance().info("zhaows","detail->返回房间详情"+ JSON.toJSONString(hotelDTO));
			if(hotelDTO!=null&&hotelDTO.getRooms()!=null&&hotelDTO.getRooms().size()>0){
				hotelDTO.getRooms().get(0).setComments(hotelDTO.getRooms().get(0).getComments().replaceAll("\n", "<br/>"));
			}
			
			model.addAttribute("hotelDTO", hotelDTO);
			model.addAttribute("token", session.getAttribute("SPRING_TOKEN"));
			model.addAttribute("jDHotelDetailQO", jDHotelDetailQO);
			
			return "hotel/hotel_reserv.html";
			
			
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","detail->查询预定酒店失败"+ HgLogger.getStackTrace(e));
		}
		return "ticket/error.jsp";
	}
	
	/**
	 * @方法功能说明：ajax动态查询房型信息
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-8上午11:06:33
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param jDHotelDetailQO
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */	
	@RequestMapping("/getHotelRooms")
	@ResponseBody
	public String getHotelRooms(HttpServletRequest request, HttpServletResponse response, @ModelAttribute JDHotelDetailQO jDHotelDetailQO,Model model,
			@RequestParam(value="arrivalTime",required=false) String arrivalTime,
			@RequestParam(value="departureTime",required=false) String departureTime
			){
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isBlank(arrivalTime)||StringUtils.isBlank(departureTime)){
				String  sameData=this.getDate("1",1);
				String[] sameDatas=sameData.split(",");
				jDHotelDetailQO.setArrivalDate(sdf.parse(sameDatas[0]));
				jDHotelDetailQO.setDepartureDate(sdf.parse(sameDatas[1]));
			}else{
				jDHotelDetailQO.setArrivalDate(sdf.parse(arrivalTime));
				jDHotelDetailQO.setDepartureDate(sdf.parse(departureTime));
			}
			String roomTypeId=request.getParameter("roomTypeId");
			String ratePlan=request.getParameter("ratePlanId");
			jDHotelDetailQO.setRoomTypeId(null);
			jDHotelDetailQO.setRatePlanId(0);
			jDHotelDetailQO.setOptions("1,2,3,4");
			HgLogger.getInstance().info("zhaows","getHotelRooms->ajax动态查询房型条件"+ JSON.toJSONString(jDHotelDetailQO));
			HotelDTO hotelDTO=hslHotelService.queryHotelDetail(jDHotelDetailQO);
			
			//查询某一个房型时：
			if(StringUtils.isNotBlank(roomTypeId)){
				List<RoomDTO> rooms=hotelDTO.getRooms();
				List<RoomDTO> rooms_=new ArrayList<RoomDTO>();
				for(RoomDTO room:rooms){
					if(room.getRoomId().equals(roomTypeId)){
						List<ListRatePlan> ratePlans=room.getRatePlans();
						List<ListRatePlan> ratePlans_=new ArrayList<ListRatePlan> ();
						if(StringUtils.isNotBlank(ratePlan)){
							for(ListRatePlan ratePlan_:ratePlans){
								if(ratePlan_.getRatePlanId()==Integer.parseInt(ratePlan)){
									ratePlans_.add(ratePlan_);
								}
							}
							
						}
						
						
						room.setRatePlans(ratePlans_);
						rooms_.add(room);		
					}
				}
				hotelDTO.setRooms(rooms_);
			}
			
			HgLogger.getInstance().info("zhaows","getHotelRooms->ajax动态查询房型信息"+ JSON.toJSONString(hotelDTO));
			map.put("hotelDTO", hotelDTO);
			map.put("jDHotelDetailQO", jDHotelDetailQO);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","getHotelRooms->ajax动态查询房型信息失败"+ HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(map);
	}
	/**
	 * 
	 * @方法功能说明：ajax动态查询产品信息集信息
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-9下午4:57:04
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param jDHotelDetailQO
	 * @参数：@param model
	 * @参数：@param arrivalTime
	 * @参数：@param departureTime
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/getRatePlans")
	@ResponseBody
	public String getRatePlans(HttpServletRequest request, HttpServletResponse response, @ModelAttribute JDHotelDetailQO jDHotelDetailQO,Model model,
			@RequestParam(value="arrivalTime",required=false) String arrivalTime,
			@RequestParam(value="departureTime",required=false) String departureTime){
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isBlank(arrivalTime)||StringUtils.isBlank(departureTime)){
				String  sameData=this.getDate("1",1);
				String[] sameDatas=sameData.split(",");
				jDHotelDetailQO.setArrivalDate(sdf.parse(sameDatas[0]));
				jDHotelDetailQO.setDepartureDate(sdf.parse(sameDatas[1]));
			}else{
				jDHotelDetailQO.setArrivalDate(sdf.parse(arrivalTime));
				jDHotelDetailQO.setDepartureDate(sdf.parse(departureTime));
			}
			jDHotelDetailQO.setOptions("1,2,3,4");
			String ratePlan=request.getParameter("ratePlan");
			if(StringUtils.isNotBlank(ratePlan)){
				jDHotelDetailQO.setRatePlanId(Integer.parseInt(ratePlan));
			}
			HgLogger.getInstance().info("zhaows", "getRatePlans->查询条件"+jDHotelDetailQO);
			HotelDTO hotelDTO=hslHotelService.queryHotelDetail(jDHotelDetailQO);
			HgLogger.getInstance().info("zhaows", "getRatePlans->查询返回DTO"+JSON.toJSONString(hotelDTO));
			map.put("hotelDTO", hotelDTO);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","getRatePlans->ajax动态查询产品信息集信息失败"+ HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(map);
	}
	/**
	 * 
	 * @方法功能说明：创建订单
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-10上午9:32:10
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param jDHotelDetailQO
	 * @参数：@param model
	 * @参数：@param arrivalTime
	 * @参数：@param departureTime
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/creteOrder")
	public String creteOrder(HttpServletRequest request, HttpServletResponse response, JDOrderCreateCommand jDOrderCreateCommand,Model model,
			@RequestParam(value="arrivalTimes",required=false) String arrivalTimes,
			@RequestParam(value="departureTimes",required=false) String departureTimes){
		try {
			String token=request.getParameter("token");
			String isGuaranteeOrCharged=request.getParameter("isGuaranteeOrCharged");
			HgLogger.getInstance().info("zhaows", "creteOrder->判断token是否相等"+token.equals(request.getSession().getAttribute("SPRING_TOKEN")));
			if(token.equals(request.getSession().getAttribute("SPRING_TOKEN"))){
				String totalPrices=request.getParameter("totalPrices");
				Double totalPrice=Double.parseDouble(totalPrices);
				//jDOrderCreateCommand.setSupplierProjcetId("yl");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(StringUtils.isNotBlank(arrivalTimes)||StringUtils.isNotBlank(departureTimes)){
					/****************************查询总价**************************************/
					JDHotelDetailQO jDHotelDetailQO=new JDHotelDetailQO();
					jDHotelDetailQO.setArrivalDate(sdf.parse(arrivalTimes));
					jDHotelDetailQO.setDepartureDate(sdf.parse(departureTimes));
					jDHotelDetailQO.setOptions("1,2,3,4");
					jDHotelDetailQO.setHotelIds(jDOrderCreateCommand.getOrderCreateDTO().getHotelId());//酒店ID
					jDHotelDetailQO.setRoomTypeId(jDOrderCreateCommand.getOrderCreateDTO().getRoomTypeId());//房型编号
					jDHotelDetailQO.setRatePlanId(jDOrderCreateCommand.getOrderCreateDTO().getRatePlanId());//产品型号
					HotelDTO hotelDTO=hslHotelService.queryHotelDetail(jDHotelDetailQO);
					BigDecimal bigDecimal=hotelDTO.getRooms().get(0).getRatePlans().get(0).getTotalRate();
					
					HgLogger.getInstance().info("zhaows", "creteOrder->判断价格是否相等"+bigDecimal.doubleValue());
					int roomNum=jDOrderCreateCommand.getOrderCreateDTO().getNumberOfRooms();
					int canRoomNum=hotelDTO.getRooms().get(0).getRatePlans().get(0).getCurrentAlloment();
					if(bigDecimal.doubleValue()*roomNum==totalPrice){
						/****************************end查询总价**************************************/
						jDOrderCreateCommand.setTotalPrice(totalPrice);
						jDOrderCreateCommand.getOrderCreateDTO().setTotalPrice(bigDecimal);
						jDOrderCreateCommand.getOrderCreateDTO().setArrivalDate(sdf.parse(arrivalTimes));
						jDOrderCreateCommand.getOrderCreateDTO().setDepartureDate(sdf.parse(departureTimes));
						jDOrderCreateCommand.getOrderCreateDTO().setCustomerType(EnumGuestTypeCode.Chinese);
						jDOrderCreateCommand.getOrderCreateDTO().setPaymentType(EnumPaymentType.SelfPay);
						jDOrderCreateCommand.getOrderCreateDTO().setCurrencyCode(EnumCurrencyCode.RMB);//货币类型
						jDOrderCreateCommand.getOrderCreateDTO().setConfirmationType(EnumConfirmationType.SMS_cn);//
						jDOrderCreateCommand.getOrderCreateDTO().setCustomerIPAddress(getIp(request));//客户访问ip
						if(isGuaranteeOrCharged.equals("0")){
							jDOrderCreateCommand.getOrderCreateDTO().setGuaranteeOrCharged(false);//不需要担保
							jDOrderCreateCommand.getOrderCreateDTO().setIsForceGuarantee(false);//不需要担保
						}else{
							jDOrderCreateCommand.getOrderCreateDTO().setGuaranteeOrCharged(true);//担保
							jDOrderCreateCommand.getOrderCreateDTO().setIsForceGuarantee(true);//担保
							jDOrderCreateCommand.getOrderCreateDTO().getCreditCard().setIdType(EnumIdType.IdentityCard);
						}
						Date date = new Date(); 
						Calendar   dar=Calendar.getInstance();
						dar.setTime(date); 
						dar.add(java.util.Calendar.HOUR, +2);
						SimpleDateFormat sdfTwo=new SimpleDateFormat("HH");
						jDOrderCreateCommand.getOrderCreateDTO().setEarliestArrivalTime(sdfr.parse(arrivalTimes+" "+sdfTwo.format(dar.getTime())+":00:00"));//
						dar.add(java.util.Calendar.HOUR, +2);
						jDOrderCreateCommand.getOrderCreateDTO().setLatestArrivalTime(sdfr.parse(arrivalTimes+" "+sdfTwo.format(dar.getTime())+":00:00"));//
						/*****************************存放客人信息***********************************/
						List<CreateOrderRoomDTO> orderRoomsDTO=new ArrayList<CreateOrderRoomDTO>();
						CreateOrderRoomDTO roomDto=new CreateOrderRoomDTO();
						List<CustomerDTO> customers=new ArrayList<CustomerDTO>();
						String customersName=request.getParameter("customersName");
						if(StringUtils.isNotBlank(customersName)){
							CustomerDTO customerDTO=new CustomerDTO();
							String [] name=customersName.split(",");
							for(int i=0;i<name.length;i++){
								customerDTO.setName(name[i]);
								customerDTO.setGender(EnumGender.Unknown);
								//下单需要客人手机号码,暂时把所有的客人手机号设为联系人的手机号
								customerDTO.setMobile(jDOrderCreateCommand.getOrderCreateDTO().getContact().getPhone());
								customerDTO.setEmail(jDOrderCreateCommand.getOrderCreateDTO().getContact().getEmail());
								customerDTO.setPhone(jDOrderCreateCommand.getOrderCreateDTO().getContact().getPhone());
								customers.add(customerDTO);
							}
							roomDto.setCustomers(customers);
							orderRoomsDTO.add(roomDto);
							jDOrderCreateCommand.getOrderCreateDTO().setOrderRoomsDTO(orderRoomsDTO);
							
							//下单人
							HotelOrderUserDTO hotelOrderUserDTO=new HotelOrderUserDTO ();
							UserDTO userDTO=getUserBySession(request);
							hotelOrderUserDTO.setLoginName(userDTO.getAuthInfo().getLoginName());
							hotelOrderUserDTO.setUserId(userDTO.getId());
							hotelOrderUserDTO.setMobile(userDTO.getMobile());
							jDOrderCreateCommand.setHotelOrderUserDTO(hotelOrderUserDTO);
							
							
						}
					}else{
						//返回到下单失败页面
						
						return "hotel/fly_fail.html";
					}
				}
				HgLogger.getInstance().info("zhaows", "creteOrder->查询条件"+JSON.toJSONString(jDOrderCreateCommand));
				OrderCreateResultDTO resultDto=this.hslHotelOrderService.createHotelOrder(jDOrderCreateCommand);
				HgLogger.getInstance().info("zhaows", "creteOrder->返回数据"+JSON.toJSONString(resultDto));
				model.addAttribute("resultDto", resultDto);
			}else{
				return "hotel/fly_fail.html";
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","creteOrder->创建订单失败"+ HgLogger.getStackTrace(e));
			return "hotel/fly_fail.html";
		}
		return "hotel/ssucce.html";
	}
	/**
	 * 
	 * @方法功能说明：获取客服端ip
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-10下午1:49:06
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if(index != -1){
				return ip.substring(0,index);
			}else{
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			return ip;
		}
		return request.getRemoteAddr();
	}
	/**
	 * 
	 * @方法功能说明：ajax验证信用卡是否可用
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-15下午2:33:53
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/checkBlock")
	@ResponseBody
	public  String checkBlock(HttpServletRequest request) {
		CheckCreditCardNoDTO checkCreditCardNoDTO=null;
		try {
			String blokId=request.getParameter("blockId");
			CheckCreditCardNoCommand checkCreditCardNoCommand=new CheckCreditCardNoCommand();
			checkCreditCardNoCommand.setCreditCardNo(blokId);
			HgLogger.getInstance().info("zhaows", "checkBlock->信用卡信息"+JSON.toJSONString(checkCreditCardNoCommand));
			checkCreditCardNoDTO=this.hslHotelOrderService.checkCreditCardNo(checkCreditCardNoCommand);
			HgLogger.getInstance().info("zhaows", "checkBlock->查询信用卡是否可用"+JSON.toJSONString(checkCreditCardNoDTO));
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","creteOrder->创建订单失败"+ HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(checkCreditCardNoDTO);
	}
}
