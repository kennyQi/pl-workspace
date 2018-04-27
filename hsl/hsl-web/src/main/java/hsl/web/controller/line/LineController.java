package hsl.web.controller.line;
import com.alibaba.fastjson.JSON;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;
import hg.payment.pojo.command.dto.PayOrderRequestDTO;
import hg.payment.pojo.command.spi.payorder.alipay.CreateAlipayPayOrderCommand;
import hg.payment.pojo.qo.payorder.PayOrderQO;
import hg.payment.spi.inter.PayOrderSpiService;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;
import hsl.app.common.util.AppInfoUtils;
import hsl.app.component.config.SysProperties;
import hsl.app.service.local.jp.JPFlightLocalService;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.sys.CityAirCode;
import hsl.domain.model.xl.Line;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.clickRecord.PLZXClickRecordCommand;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.command.line.HslCreateLineOrderCommand;
import hsl.pojo.command.line.UpdateLineOrderStatusCommand;
import hsl.pojo.dto.coupon.ConsumeOrderSnapshotDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.line.DateSalePriceDTO;
import hsl.pojo.dto.line.DayRouteDTO;
import hsl.pojo.dto.line.HotelInfoDTO;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.dto.line.order.*;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.CouponException;
import hsl.pojo.log.PLZXClickRecord;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.pojo.qo.line.HslLineQO;
import hsl.pojo.qo.log.PLZXClickRecordQo;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.clickRecord.PLZXClickRecordService;
import hsl.spi.inter.line.HslLineOrderService;
import hsl.spi.inter.line.HslLineService;
import hsl.spi.inter.user.UserService;
import hsl.spi.qo.sys.CityAirCodeQO;
import hsl.web.alipay.config.AlipayConfig;
import hsl.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @类功能： 线路控制器
 * @作者： zhaows
 * @创建时间：2015年3月27日上午
 */
@Controller
@RequestMapping("/line")
public class LineController extends BaseController {
	@Autowired
	private HslLineService hslLineService;
	@Autowired
	private JedisPool jedisPool;
	@Resource
	private CityService cityService;
	@Resource
	private ProvinceService provinceService;
	@Resource
	private CouponService couponService;
	@Autowired
	private HgLogger hgLogger;
	@Resource
	private PayOrderSpiService payOrderSpiService;
	@Resource
	private JPFlightLocalService jpFlightLocalService;
	@Autowired
	private HslLineOrderService lineOrderService;
	@Autowired
	private RabbitTemplate template;
	@Resource
	private SMSUtils smsUtils;
	@Resource
	private UserService memberService;
	@Autowired
	private PLZXClickRecordService plzxClickRecordService;

	private Integer constant = 100;
	/**
	 * 获取卡券的使用条件以及获得获取卡券所属的活动id
	 * @param request
	 * */
	@RequestMapping("/getCouponCondition")
	@ResponseBody
	private String getCouponCondition(HttpServletRequest request) {
		String couponId = request.getParameter("couponId");
		HslCouponQO hslCouponQO = new HslCouponQO();
		CouponDTO couponDto = null;
		hslCouponQO.setId(couponId);
		if (hslCouponQO != null) {
			hslCouponQO.setIdLike(true);
		}
		try {
			couponDto = couponService.queryUnique(hslCouponQO);
			HgLogger.getInstance().info("zhaows", "getCouponCondition-->查询卡券成功:"+couponDto);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("liusong","getCouponCondition-->获取卡券失败:" + HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(couponDto);
	}
	/**
	 * @方法功能说明：查询城市
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月2日上午8:30:20
	 * @修改内容：
	 * @throws
	 */

	public HashMap<String,String> getCityMap(){
		HashMap<String,String> cityMap = new HashMap<String,String>();
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		if(cityList!=null&&cityList.size()>0){
			for(City city : cityList){
				cityMap.put(city.getCode(), city.getName());
			}
		}
		return cityMap;
	}
	/**
	 * @方法功能说明：查询线路详情
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月1日上午8:30:20
	 * @修改内容：
	 * @throws
	 */
	@RequestMapping("/detail")
	public String getDetail(HslLineQO hslLineQO,HttpServletRequest request,Model model,String identification){
		try {
			//加载图片
			hslLineQO.setGetPictures(true);
			//加载日历
			hslLineQO.setGetDateSalePrice(true);
			hslLineQO.setForSale(0);
			//根据id得到详情
			HgLogger.getInstance().info("zhaows", "getDetail-->线路id:"+hslLineQO);
			LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
			if(lineDTO!=null){
				//创建用户浏览记录
				UserDTO userDTO=getUserBySession(request);
				if(null!=userDTO){
					PLZXClickRecordCommand command=new PLZXClickRecordCommand();
					command.setObjectId(lineDTO.getId());
					command.setObjectType(PLZXClickRecord.PLZX_CLICK_RECORD_LINE);
					command.setUrl("/line/detail?id="+lineDTO.getId());
					command.setUserId(userDTO.getId());
					command.setFromIP(request.getRemoteHost());
					plzxClickRecordService.createPLZXClickRecord(command);
				}
				List<DayRouteDTO> dayRoutes = lineDTO.getRouteInfo().getDayRouteList();
				//酒店
				for(DayRouteDTO dayDto: dayRoutes){
					List<HotelInfoDTO> list = JSON.parseArray(dayDto.getHotelInfoJson(), HotelInfoDTO.class);
					dayDto.setHotelList(list);
				}
				/*得到当天之后的价格日历*/
				Date date=new Date();
				List<DateSalePriceDTO> list=new ArrayList<DateSalePriceDTO>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for(DateSalePriceDTO listdsp:lineDTO.getDateSalePriceList()){
					DateSalePriceDTO dateSalePriceDTO=new DateSalePriceDTO();
					String SaleDate=sdf.format(listdsp.getSaleDate());
					String getDate=sdf.format(date);
					if(sdf.parse(SaleDate).getTime()>=sdf.parse(getDate).getTime()){
						dateSalePriceDTO.setSaleDate(listdsp.getSaleDate());
						dateSalePriceDTO.setAdultPrice(listdsp.getAdultPrice());
						dateSalePriceDTO.setChildPrice(listdsp.getChildPrice());
						dateSalePriceDTO.setId(listdsp.getId());
						dateSalePriceDTO.setNumber(listdsp.getNumber());
						list.add(dateSalePriceDTO);
					}


				}
				lineDTO.setDateSalePriceList(list);
				/*查询订单列表*/
				HslLineOrderQO hslLineOrderQO=new HslLineOrderQO();
				List<LineOrderDTO> lineOrder=lineOrderService.queryList(hslLineOrderQO);//查询下单用户
				HslLineQO hslLineQO1=new HslLineQO();
				List<LineDTO> listlineDTO = (List<LineDTO>) hslLineService.queryList(hslLineQO1);//查询线路
				List<LineOrderFirstTopDTO> listlineOrderFirstTopDTO=new ArrayList<LineOrderFirstTopDTO>();
				HgLogger.getInstance().info("zhaows", "getDetail-->"+lineOrder.size() +"==="+listlineDTO.size());
				for(int i=0;i<=20;i++){
					if(lineOrder.size()>i&&listlineDTO.size()>i){
						LineOrderFirstTopDTO lineOrderFirstTopDTO=new LineOrderFirstTopDTO();
						//默认按分钟计算
						long datetime=(date.getTime()-lineOrder.get(i).getBaseInfo().getCreateDate().getTime())/60000;
						String hm="";
						if(datetime<60){
							hm=datetime+"分钟";
						}else if(datetime>60&&datetime<1440){
							//按小时计算
							datetime=(date.getTime()-lineOrder.get(i).getBaseInfo().getCreateDate().getTime())/3600000;
							hm=datetime+"小时";
						}else if(datetime>=1440){
							//计算天
							datetime=(date.getTime()-lineOrder.get(i).getBaseInfo().getCreateDate().getTime())/86400000;
							hm=datetime+"天";
						}
						lineOrderFirstTopDTO.setGetTime(hm+"");
						lineOrderFirstTopDTO.setCountPeople(lineOrder.get(i).getBaseInfo().getAdultNo()+lineOrder.get(i).getBaseInfo().getChildNo());
						//得到订单号
						lineOrderFirstTopDTO.setDealerOrderNo(lineOrder.get(i).getLinkInfo().getLinkName().substring(1));

						lineOrderFirstTopDTO.setId(listlineDTO.get(i).getId());
						//得到线路名称
						lineOrderFirstTopDTO.setLineName(listlineDTO.get(i).getBaseInfo().getName());
						listlineOrderFirstTopDTO.add(lineOrderFirstTopDTO);
					}
				}
				if(StringUtils.isBlank(identification)){
					identification="2";
				}

				HgLogger.getInstance().info("zhaows", "getDetail-->线路详情查询:"+lineDTO);
				model.addAttribute("line", lineDTO);
				model.addAttribute("listlineOrderFirstTopDTO", listlineOrderFirstTopDTO);
				model.addAttribute("cityMap", getCityMap());
				model.addAttribute("identification", identification);
				String image_host=SysProperties.getInstance().get("image_host");
				model.addAttribute("image_host", image_host);

			}else{
				model.addAttribute("error", "该线路已下架或正在维护，请回到首页刷新后在操作。。。。。");
				return "ticket/error.jsp";
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows","getDetail-->线路详情查询失败" + HgLogger.getStackTrace(e));
			model.addAttribute("identification", "error");
			e.printStackTrace();
		}

		return "line/detail.html";
	}
	/**
	 * @方法功能说明：线路预定
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月3日上午8:30:20
	 * @修改内容：
	 * @throws
	 */
	@RequestMapping("/reseve")
	public  Object getReseve(HslLineQO hslLineQO,HttpServletRequest request,Model model,
							 @RequestParam(value="adults",required=false) String  adults,
							 @RequestParam(value="chidrens",required=false) String chidrens,
							 @RequestParam(value="ids",required=false) String ids,
							 @RequestParam(value="startdate",required=false) String startdate
	){
		try {
			if(adults!=null){
				//加载日历
				hslLineQO.setGetDateSalePrice(true);
				hslLineQO.setForSale(1);
				LineDTO line=hslLineService.queryUnique(hslLineQO);
				HgLogger.getInstance().info("zhaows","getReseve-->查询当前线路成功，查询的QO："+line);
				if(line==null){
					return "ticket/error.jsp";
				}
				/*if(line!=null){*/
				//查询当前线路
				//查询当前线路是否有对应的余票  传入线路Id,时间,成人人数,儿童人数
				List<DateSalePriceDTO> list=line.getDateSalePriceList();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				int num=Integer.parseInt(adults)+Integer.parseInt(chidrens);
				int msg=0;
				for(DateSalePriceDTO listdsp:list){
					if(startdate.equals(sdf.format(listdsp.getSaleDate()))){
						if(listdsp.getNumber()<num){//<=
							msg=1;
						}
					}

				}
				if(msg==1){
					return getDetail(hslLineQO, request, model,"1");
				}else{
					//得到当前用户的卡券
					// 根据登录用户的userid查询该用户拥有的卡券列表
					UserDTO user = getUserBySession(request); // 获取登录用户的信息
					HslCouponQO hslCouponQO = new HslCouponQO();
					//if(user!=null){
					hslCouponQO.setUserId(user.getId());
					// 只查询未使用的卡券并且满足使用条件的卡券
					hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
					hslCouponQO.setOverdue(true);
					//}
					try {
						if (hslCouponQO.getUserId() != null) {
							List<CouponDTO> couponList = couponService.queryList(hslCouponQO);
							//得到当前用户代金券的数量
							String count="0";
							for(CouponDTO counts:couponList){
								if(counts.getBaseInfo().getCouponActivity().getBaseInfo().getCouponType()==1){
									count="1";
								}
							}

							/*计算是否付全款*/
							//得到当天时间
							Date daydate=new Date();
							long payTotalDaysBeforeStart=line.getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
							/*出发时间*/
							long date=sdf.parse(startdate).getTime();
							if(sdf.parse(sdf.format(daydate)).getTime()+payTotalDaysBeforeStart<=date){
								//支付定金
								model.addAttribute("frontmoney", "1");
							}else{
								//全额支付
								model.addAttribute("frontmoney", "2");
							}
							/*付款or不付款判断 1能付款 2不能付款    在详情页面也验证,创建订单不需要再验证*/
							/*long lastPayTotalDaysBeforeStart=line.getPayInfo().getLastPayTotalDaysBeforeStart()*24*60*60*1000;
							if(sdf.parse(sdf.format(daydate)).getTime()+lastPayTotalDaysBeforeStart<=sdf.parse(startdate).getTime()){
								model.addAttribute("onpay", "1");
							}else{
								model.addAttribute("onpay", "2");
							}*/
							HgLogger.getInstance().info("zhaows","getReseve-->查询用户所拥有的可使用的卡券成功，查询的QO："+ JSON.toJSONString(hslCouponQO));
							model.addAttribute("count", count);
							model.addAttribute("couponNoUsed", couponList);
							//当前线路信息
							model.addAttribute("line", line);
							model.addAttribute("cityMap", getCityMap());
							model.addAttribute("startdate", startdate);
							model.addAttribute("adults", adults);
							model.addAttribute("chidrens", chidrens);
							model.addAttribute("user", user);
							return "line/reseve.html";
						} else {
							return new RedirectView("/user/login", true);
						}

					} catch (Exception e) {
						HgLogger.getInstance().error("zhaows","getReseve-->根据登录用户的userid查询该用户拥有的卡券列表失败"+ e.getStackTrace());
						//return "line/reseve.html";
						e.printStackTrace();
						return "ticket/error.jsp";
					}
				}
			}else{
				return lineList( hslLineQO, request, model, null, null, startdate, startdate, startdate, startdate,null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","getReseve-->线路详情查询失败"+ e.getStackTrace());
			return "line/reseve.html";
		}


	}
	/**
	 * @方法功能说明：查询线路列表
	 * @修改者名字：zhaows
	 * @修改时间：2015年3月26日上午14:30:20
	 * @修改内容：
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public Object lineList(HslLineQO hslLineQO,HttpServletRequest request,Model model,
						   @RequestParam(value="pageNum",required=false) Integer pageNo,
						   @RequestParam(value="pageSize",required=false) Integer pageSize,
						   @RequestParam(value="picorder",required=false) String picorder,
						   @RequestParam(value="salesorder",required=false) String salesorder,
						   @RequestParam(value="recommend",required=false) String recommend,
						   @RequestParam(value="hideprovince",required=false) String hideprovince,
						   @RequestParam(value="startCityName",required=false) String startCityName
	){
		hslLineQO.setGetPictures(true);
		try {
			if(hslLineQO.getStartCity()==null||hslLineQO.getStartCity().equals("")){
				hslLineQO.setStartCity(null);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String beginDateTime = "";
			String endDateTime = "";
			Date begin = hslLineQO.getBeginDateTime();
			Date end = hslLineQO.getEndDateTime();
			if(null!=begin){
				if(!(sdf.format(begin).equals("1983-01-01"))){
					beginDateTime = sdf.format(begin);
					hslLineQO.setBeginDateTime(sdf.parse(beginDateTime));
				}else{
					hslLineQO.setBeginDateTime(null);
				}
			}
			if(null!=end){
				if(!(sdf.format(end).equals("1983-01-01"))){
					endDateTime = sdf.format(end);
					hslLineQO.setEndDateTime(sdf.parse(endDateTime));
				}else{
					hslLineQO.setEndDateTime(null);
				}
			}

			if(salesorder==null&&recommend==null&&picorder==null){
				hslLineQO.setOrderKind(1);
				hslLineQO.setOrderType(true);
				recommend="1";
			}else{
				//价格排序
				if((picorder.equals("1"))){
					hslLineQO.setOrderKind(3);
					hslLineQO.setOrderType(true);
					picorder="1";
				}else if(picorder.equals("2")){
					hslLineQO.setOrderKind(3);
					picorder="2";
				}else if(picorder.equals("0")){
					picorder="0";
				}//销量排序
				if(salesorder.equals("1")){
					hslLineQO.setOrderKind(2);
					hslLineQO.setOrderType(true);
					salesorder="1";
				}else if(salesorder.equals("2")){
					hslLineQO.setOrderKind(2);
					salesorder="2";
				}else if(salesorder.equals("0")){
					salesorder="0";
				}
				//推荐指数排序
				if((recommend.equals("1"))){
					hslLineQO.setOrderKind(1);
					hslLineQO.setOrderType(true);
					recommend="1";

				}else if(recommend.equals("2")){
					hslLineQO.setOrderKind(1);
					recommend="2";
				}else if(recommend.equals("0")){
					recommend="0";
				}

			}
			//得到到达城市查询code
			if(hslLineQO.getEndCity()!=""&&hslLineQO.getEndCity()!=null){
				CityAirCodeQO cityaircodeqo = new CityAirCodeQO();
				cityaircodeqo.setName(hslLineQO.getEndCity());
				CityAirCode cityaircode = jpFlightLocalService.queryLocalCityAirCode(cityaircodeqo);
				HgLogger.getInstance().info("zhaows", "lineList-->查询出发城市成功:"+cityaircode);
				// 判断所选城市是否有对应的三字码
				if (cityaircode != null) {
					hslLineQO.setEndCity(cityaircode.getCode());
					model.addAttribute("endCity", cityaircode.getName());
				}else{
					hslLineQO.setEndCity("");
				}
			}
			//得到出发城市查询出发
			if(startCityName!=""&&startCityName!=null){
				CityAirCodeQO cityaircodeqo = new CityAirCodeQO();
				cityaircodeqo.setName(startCityName);
				CityAirCode cityaircode = jpFlightLocalService.queryLocalCityAirCode(cityaircodeqo);
				HgLogger.getInstance().info("zhaows", "lineList-->查询出发城市成功:"+cityaircode);
				// 判断所选城市是否有对应的三字码
				if (cityaircode != null) {
					hslLineQO.setStartCity(cityaircode.getCode());
					hideprovince=cityaircode.getParentCode();
				}else{
					hideprovince="";
					hslLineQO.setStartCity("");
				}
			}

			Pagination pagination = new Pagination();
			pageNo=pageNo==null?new Integer(1):pageNo;
			pageSize=pageSize==null?new Integer(9):pageSize;
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setCondition(hslLineQO);
			pagination.setCheckPassLastPageNo(false);
			hslLineQO.setGetDateSalePrice(true);
			hslLineQO.setForSale(Line.SALE);
			HgLogger.getInstance().info("zhaows", "lineList-->列表查询条件:"+JSON.toJSONString(pagination));
			pagination = hslLineService.queryPagination(pagination);
			HgLogger.getInstance().info("zhaows", "lineList-->查询线路列表返回结果:"+JSON.toJSONString(pagination));
			//只取今天以后的价格日历
			if(pagination!=null){
				List<LineDTO> lineDTOList=(List<LineDTO>) pagination.getList();
				for(LineDTO lineDTO:lineDTOList){
					List<DateSalePriceDTO>  dateSalePriceDTOList=lineDTO.getDateSalePriceList();
					List<DateSalePriceDTO>  dateSalePriceDTOList_=new ArrayList<DateSalePriceDTO>();

					for(DateSalePriceDTO dateSaleProceDTO:dateSalePriceDTOList){
						if(dateSaleProceDTO.getSaleDate().after(new Date())){
							dateSalePriceDTOList_.add(dateSaleProceDTO);
						}
					}
					lineDTO.setDateSalePriceList(dateSalePriceDTOList_);

				}
			}


			ProvinceQo provinceQo = new ProvinceQo();
			List<Province> provinceList = provinceService.queryList(provinceQo);
			model.addAttribute("provinces", provinceList);
			model.addAttribute("pagination", pagination);
			model.addAttribute("hslLineQO", hslLineQO);
			model.addAttribute("picorder", picorder);
			model.addAttribute("recommend", recommend);
			model.addAttribute("salesorder", salesorder);
			model.addAttribute("cityMap", getCityMap());
			model.addAttribute("beginDateTime", beginDateTime);
			model.addAttribute("endDateTime", endDateTime);
			model.addAttribute("hideprovince", hideprovince);
			String image_host=SysProperties.getInstance().get("image_host");
			model.addAttribute("image_host", image_host);
			//查询用户点击记录
			//创建用户浏览记录
			UserDTO userDTO=getUserBySession(request);
			if(null!=userDTO){
				PLZXClickRecordQo plzxClickRecordQo=new PLZXClickRecordQo();
				plzxClickRecordQo.setUserId(userDTO.getId());
				List<LineDTO> lineDTOs=hslLineService.queryUserClickRecord(plzxClickRecordQo);
				model.addAttribute("browseLines", lineDTOs);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows","lineList-->查询线路列表失败" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return "line/list.html";

	}
	/**
	 * @方法功能说明：根据省份查询相应的城市
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月8日上午9.22
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param province
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/searchCity")
	@ResponseBody
	public String searchCity(HttpServletRequest request,Model model,
							 @RequestParam(value = "province", required = false) String province){
		// 查询市
		CityQo cityQo = new CityQo();
		cityQo.setProvinceCode(province);
		List<City> cityList = cityService.queryList(cityQo);
		hgLogger.info("chenxy", "根据省份查询城市成功");
		return JSON.toJSONString(cityList);
	}

	/**
	 * @方法功能说明：支付平台异步通知地址
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月8日上午10:48:02
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/async/paymentRequest")
	public void asyncPaymentRequest(HttpServletRequest request,HttpServletResponse response) {
		try {
			String orderNo = request.getParameter("orderNo");// 获取商城订单编号
			String ordertwo=orderNo;
			String[] orderNos=orderNo.split("_");
			if(orderNos.length>1){
				orderNo=orderNos[0];
				HgLogger.getInstance().info("zhaows","syncPaymentRequest-->截取订单号：" +orderNo );
			}
			HgLogger.getInstance().info("zhaows","asyncPaymentRequest-->异步通知方法订单号："+ orderNo);
			PayOrderQO payorderQO = new PayOrderQO();
			payorderQO.setOrderNo(ordertwo);// 商城订单编号
			payorderQO.setPaymentClientID(SysProperties.getInstance().get("paymentLineClientId"));// 客户端id
			payorderQO.setPaymentClienKeys(SysProperties.getInstance().get("paymentLineClienKeys"));// 客户端密钥
			payorderQO.setPaySuccess(true);
			/*查询订单*/
			HslLineOrderQO hslLineOrderQO=new HslLineOrderQO();
			hslLineOrderQO.setDealerOrderNo(orderNo);
			LineOrderDTO lineOrderdto=lineOrderService.queryUnique(hslLineOrderQO);
			HgLogger.getInstance().info("zhaows","asyncPaymentRequest-->查询订单成功"+ lineOrderdto.getId());
			Integer paySuccess = 0;// 默认支付状态支付失败，如果查询出订单，则赋值为1
			String buyeremail = "";
			String trade = "";
			// 商城查询订单是否支付成功
			HgLogger.getInstance().info("zhaows","asyncPaymentRequest-->查询payorderQO条件" + payorderQO);
			//List<PayOrderDTO> payOrderList = payOrderSpiService.queryPayOrderList(payorderQO);
			//HgLogger.getInstance().info("zhaows","asyncPaymentRequest-->查询支付平台订单状态,查询得到的结果集 ：" + payOrderList);
			//if (payOrderList.size() > 0) {
			//paySuccess = payOrderList.get(0).getPayStatus();// 取得订单状态
			//buyeremail = payOrderList.get(0).getBuyer_email();// 取得支付人的支付宝帐号
			//trade = payOrderList.get(0).getTrade_no();// 取得支付宝订单号

			if (lineOrderdto.equals("")||lineOrderdto==null) {
				HgLogger.getInstance().info("zhaows","asyncPaymentRequest-->该订单号对应订单的订单状态已经为支付成功状态，该订单号："+ orderNo);
				return;
			} else {
				/*计算是否付全款*/
				//得到当天时间
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date daydate=new Date();
				int frontmoney=1;
				Date startdate=lineOrderdto.getBaseInfo().getTravelDate();
				long PayTotalDaysBeforeStart=lineOrderdto.getLineSnapshot().getLine().getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
				/*出发时间*/
				long date=startdate.getTime();
				/*定金or全额支付 1为支付定金2为全额支付*/
				if(daydate.getTime()+PayTotalDaysBeforeStart>date){
					frontmoney=2;//全款支付
				}
				HgLogger.getInstance().info("zhaows","asyncPaymentRequest-->当前订单状态："+lineOrderdto.getStatus().getOrderStatus());
				HslLineOrderQO hslLineOrderQOone=new HslLineOrderQO();
				hslLineOrderQOone.setDealerOrderNo(orderNo);
				LineOrderDTO lineOrderdtoone=lineOrderService.queryUnique(hslLineOrderQOone);
				String OrderStatus="";
				HgLogger.getInstance().info("zhaows", "asyncPaymentRequest调用支付平台>>支付订单"+JSON.toJSONString(lineOrderdtoone));
				Set<LineOrderTravelerDTO> travelers=lineOrderdtoone.getTravelers();
				for(LineOrderTravelerDTO travelerss:travelers){
					if(travelerss.getLineOrderStatus().getOrderStatus()==Integer.parseInt((XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT))){
						HgLogger.getInstance().info("zhaows", "asyncPaymentRequest调用支付平台>>支付尾款修改订单号");
						OrderStatus="1";
						break;
					}
				}
				HgLogger.getInstance().info("zhaows", "asyncPaymentRequest支付状态"+OrderStatus);
				//判断订单状态是否是下单成功已锁定位置
				if(OrderStatus.equals("1")){
					//支付尾款
					frontmoney=3;
				}
				Integer status =null;
				Integer payStatus =null;
				//判断是支付定金还是支付全款或者是支付尾款
				HgLogger.getInstance().info("zhaows","asyncPaymentRequest-->判断是支付定金还是支付全款或者是支付尾款："+frontmoney);
				HslLineQO newHslLineQO = new HslLineQO();
				newHslLineQO.setId(lineOrderdto.getLineSnapshot().getLine().getId());
				LineDTO newLineDTO = hslLineService.queryLine(newHslLineQO);
				HgLogger.getInstance().info("yuqz", newLog("newLineDTO-647", JSON.toJSONString(newLineDTO)));
				if(newLineDTO.getPayInfo().getDownPayment() * 100.0/100 == 100.00d){
					status = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
					payStatus = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);//全款支付
				}else{
					if(frontmoney==1){
						status = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
						payStatus = Integer.parseInt(XLOrderStatusConstant.SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX);
					}else if(frontmoney==2){
						status = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
						payStatus = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);//全款支付
					}else if(frontmoney==3){
						status = Integer.parseInt(XLOrderStatusConstant.SHOP_RESERVE_SUCCESS);
						payStatus = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);//支付尾款
					}
				}
				UpdateLineOrderStatusCommand updateLineOrderStatusCommand=new UpdateLineOrderStatusCommand();
				updateLineOrderStatusCommand.setOrderStatus(status);
				updateLineOrderStatusCommand.setPayStatus(payStatus);
				updateLineOrderStatusCommand.setDealerOrderCode(orderNo);
				updateLineOrderStatusCommand.setOrderId(lineOrderdto.getId());
				HgLogger.getInstance().error("zhaows","asyncPaymentRequest>修改订单状态command："+ JSON.toJSONString(updateLineOrderStatusCommand));
				lineOrderService.updateLineOrderStatus(updateLineOrderStatusCommand);
				HgLogger.getInstance().info("zhaows","asyncPaymentRequest-->修改订单状态成功："+ orderNo);
				// 如果支付成功，则同时修改与该订单相关的卡券的状态为已消费状态
				HslCouponQO hslCouponQO = new HslCouponQO();
				hslCouponQO.setOrderId(orderNo);
				//设置查询条件卡券状态为占用
				// 根据订单号查询一个与订单绑定的卡券快照
				List<CouponDTO> coupondtos = couponService.queryList(hslCouponQO);
				//标识卡券是否使用
				int couponstatus=0;
				if(coupondtos!=null&&coupondtos.size()>0){
					ConsumeOrderSnapshotDTO consumeOrderSnapshotDTO=new ConsumeOrderSnapshotDTO();
					HgLogger.getInstance().info("zhaows","asyncPaymentRequest根据订单号查询一个与订单绑定的卡券快照"+ JSON.toJSONString(coupondtos));
					String couponid="";
					for (CouponDTO coupondto : coupondtos) {
						if(coupondto!=null){
							couponid+=coupondto.getId()+",";
							consumeOrderSnapshotDTO.setPayPrice(coupondto.getConsumeOrder().getPayPrice());
							consumeOrderSnapshotDTO.setOrderId(coupondto.getConsumeOrder().getOrderId());
						}
						if(coupondto.getStatus().getCurrentValue()==CouponStatus.TYPE_ISUSED){
							couponstatus=1;
							break;
						}
					}
					String[] couponids=couponid.split(",");
					// 修改卡券的状态
					HgLogger.getInstance().info("zhaows","asyncPaymentRequest得到卡券状态 1为已使用"+ couponstatus);
					if(couponstatus!=1){
						BatchConsumeCouponCommand couponCommand = new BatchConsumeCouponCommand();
						couponCommand.setCouponIds(couponids);
						couponCommand.setOrderId(consumeOrderSnapshotDTO.getOrderId());
						couponCommand.setPayPrice(consumeOrderSnapshotDTO.getPayPrice());
						try {
							couponService.confirmConsumeCoupon(couponCommand);
						} catch (CouponException e) {
							e.printStackTrace();
							HgLogger.getInstance().error("zhaows","asyncPaymentRequest>订单状态修改失败："+ e.getMessage()+ HgLogger.getStackTrace(e));
						}
					}
				}
				/*end 修改订单状态*/
				// 发放卡券：订单满
				CreateCouponCommand cmd = new CreateCouponCommand();
				cmd.setSourceDetail("订单满送");
				//按总金额满多少发送
				cmd.setPayPrice(lineOrderdto.getBaseInfo().getSalePrice());
				cmd.setMobile(lineOrderdto.getLineOrderUser().getMobile());
				cmd.setUserId(lineOrderdto.getLineOrderUser().getUserId());
				cmd.setLoginName(lineOrderdto.getLineOrderUser().getLoginName());
				CouponMessage baseAmqpMessage = new CouponMessage();
				baseAmqpMessage.setMessageContent(cmd);
				String issue = SysProperties.getInstance().get("issue_on_full");
				int type = 0;
				if (StringUtils.isBlank(issue))
					type = 2;// 默认为2
				else {
					type = Integer.parseInt(issue);
				}
				baseAmqpMessage.setType(type);
				baseAmqpMessage.setSendDate(new Date());
				baseAmqpMessage.setArgs(null);
				template.convertAndSend("hsl.order", baseAmqpMessage);
				HgLogger.getInstance().info("zhaows", "asyncPaymentRequest-->卡券发放成功"+baseAmqpMessage);
				// 发送短信通知下单人,商旅分销已经有调用过发送短信的方法接口了
				try {
					//查询一条订单记录
					//HslLineOrderQO qo=new HslLineOrderQO();
					//预订成功待付尾款   已支付订金
					HslLineOrderQO hslLineOrderQOs=new HslLineOrderQO();
					hslLineOrderQOs.setDealerOrderNo(orderNo);
					hslLineOrderQOs.setOrderId(lineOrderdto.getId());
					LineOrderDTO lineOrderdtos=lineOrderService.queryUnique(hslLineOrderQOs);
					int Status=lineOrderdtos.getStatus().getPayStatus();
					String paymentorPaid="定金";
					if(Status==Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS)){
						paymentorPaid="全款";
					}
					String	smsContent = ("【"+SysProperties.getInstance().get("sms_sign", "票量旅游")+"】您的订单"+ orderNo+ "，订单"+paymentorPaid+"支付成功。查询订单请点击http://m.ply365.com，客服电话：0571-28280813。");
					smsUtils.sendSms(lineOrderdto.getLinkInfo().getLinkMobile(), smsContent);
					HgLogger.getInstance().info("zhaows", "短信发送成功" + lineOrderdto.getLinkInfo().getLinkMobile() + " smsContent:" + smsContent);
				} catch (Exception e) {
					HgLogger.getInstance().error("zhaows","asyncPaymentRequest-->用户付款成功短信发送异常"+ HgLogger.getStackTrace(e));
				}
			}
			//}

		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows","asyncPaymentRequest-->支付失败" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
	}
	/**
	 * @方法功能说明：支付平台同步通知地址
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月8日上午10:48:27
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/sync/paymentRequest")
	public String syncPaymentRequest(HttpServletRequest request,HttpServletResponse response,Model model) {
		String orderNo = request.getParameter("orderNo");// 获取订单号
		if(StringUtils.isBlank(orderNo)){
			return "ticket/error.jsp";
		}
		String[] orderNos=orderNo.split("_");
		if(orderNos.length>1){
			orderNo=orderNos[0];
			HgLogger.getInstance().info("zhaows","syncPaymentRequest-->截取订单号：" +orderNo );
		}
		HslLineOrderQO hslLineOrderQO=new HslLineOrderQO();
		hslLineOrderQO.setDealerOrderNo(orderNo);
		// 查询判断多种状态(已支付)
		/*		根据订单号查询
		HslLineOrderQO hslLineOrderQOone=new HslLineOrderQO();
		hslLineOrderQOone.setDealerOrderNo(orderNo);
		LineOrderDTO lineOrderdtoone=lineOrderService.queryUnique(hslLineOrderQOone);
		String OrderStatus="";
		HgLogger.getInstance().info("zhaows", "asyncPaymentRequest调用支付平台>>支付订单"+JSON.toJSONString(lineOrderdtoone));
		Set<LineOrderTravelerDTO> travelers=lineOrderdtoone.getTravelers();
		for(LineOrderTravelerDTO travelerss:travelers){
			if(travelerss.getLineOrderStatus().getPayStatus()==Integer.parseInt((XLOrderStatusConstant.SHOP_PAY_SUCCESS))){
				HgLogger.getInstance().info("zhaows", "asyncPaymentRequest调用支付平台>>支付尾款修改订单号");
				OrderStatus="1";
				break;
			}
		}*/
		LineOrderDTO lineOrder=lineOrderService.queryUnique(hslLineOrderQO);
		HgLogger.getInstance().info("zhaows","syncPaymentRequest-->根据订单号获取一条订单记录：" + JSON.toJSONString(lineOrder));

		// 判断是否支付成功
		if (lineOrder!=null) {
			/*返回支付成功页面*/
			// 支付成功页面读取相关信息
			model.addAttribute("dealerOrderNo", orderNo);
			model.addAttribute("lineName", lineOrder.getLineSnapshot().getLineName());
			int status=lineOrder.getStatus().getPayStatus();
			HgLogger.getInstance().info("zhaows", "syncPaymentRequest-->当前状态和数据库状态判断:"+"当前状态="+Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS)+"数据库状态="+status);
			if(status==Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS)){
				model.addAttribute("frontmoney","2");
			}
			return "line/succes.html";
		} else {
			// 返回支付失败页面
			return "line/worry.html";
		}
	}
	/**
	 * @方法功能说明：保存用户信息
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月9日下午14.01
	 */
	@RequestMapping(value="/commandUser")
	public Object commandUser(HttpServletRequest request,Model model,HslCreateLineOrderCommand hslCreateLineOrderCommand,
							  @RequestParam(value = "touristname", required = false) String touristname,
							  @RequestParam(value = "touristphone", required = false) String touristphone,
							  @RequestParam(value = "touristid", required = false) String touristid,
							  @RequestParam(value = "travelDate", required = false) String travelDate
	){
		try {
			String moneyCoupon=request.getParameter("moneyCoupon");
			String shouldmoneys=request.getParameter("shouldmoneys");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int adultNo=hslCreateLineOrderCommand.getBaseInfo().getAdultNo();
			int childNo=hslCreateLineOrderCommand.getBaseInfo().getChildNo();
			HashMap<String,Double> map=lineCost(adultNo, childNo, hslCreateLineOrderCommand.getLineID(), travelDate);
		/*HslLineQO hslLineQO=new HslLineQO();
		hslLineQO.setId(hslCreateLineOrderCommand.getLineID());
		hslLineQO.setGetDateSalePrice(true);
		LineDTO line=hslLineService.queryUnique(hslLineQO);
		double adultPrice=0.00;
		double childPrice=0.00;
		for(int i=0;i<line.getDateSalePriceList().size();i++){
		if((sdf.parse(sdf.format(line.getDateSalePriceList().get(i).getSaleDate())).getTime()==(sdf.parse(travelDate)).getTime())){
			//得到日历价格
			adultPrice=line.getDateSalePriceList().get(i).getAdultPrice();
			childPrice=line.getDateSalePriceList().get(i).getChildPrice();
		}
		}
		int adultNo=hslCreateLineOrderCommand.getBaseInfo().getAdultNo();
		int childNo=hslCreateLineOrderCommand.getBaseInfo().getChildNo();*/
	  /*  adultPrice=adultPrice*adultNo;
		childPrice=childPrice*childNo;
		double countPrice=0.00;
			*//*计算是否付全款*//*
		//得到当天时间
		Date daydates=new Date();
		long payTotalDaysBeforeStart=line.getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
			*//*出发时间*//*
		long dates=sdf.parse(travelDate).getTime();
			*//*定金or全额支付 1为支付定金2为全额支付*//*
		if(daydates.getTime()+payTotalDaysBeforeStart<dates){
		//支付定金
		Double downPayment=line.getPayInfo().getDownPayment()/100;
		countPrice=(adultPrice+childPrice)*downPayment;
		}else{
		//全额支付
		countPrice=adultPrice+childPrice;
		}
		//java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
		///BigDecimal   b=new   BigDecimal(countPrice);
		countPrice=(countPrice-Double.parseDouble(moneyCoupon));
		countPrice=Double.parseDouble(String .format("%.2f",countPrice));
		HgLogger.getInstance().info("zhaows", "commandUser-->得到后台计算价格"+countPrice+"==前台提交价格="+shouldmoneys);
		if(countPrice<0.00){
		countPrice=0.00;
		}*/
			if(map.get("frontMoney")!=Double.parseDouble(shouldmoneys)&&map.get("trailMoney")!=Double.parseDouble(shouldmoneys)
					){
				return "ticket/error.jsp";
			}else{
				hslCreateLineOrderCommand.getBaseInfo().setSalePrice(map.get("countMoney"));//总金额
				hslCreateLineOrderCommand.getBaseInfo().setBargainMoney(map.get("frontMoney"));//定金
				UserDTO user = getUserBySession(request); // 获取登录用户的信息
				//if(user!=null){
				//得到游客信息
				List<LineOrderTravelerDTO> travelerList=new ArrayList<LineOrderTravelerDTO>();
				String [] name=touristname.split(",");
				String [] phone=touristphone.split(",");
				String [] tid=touristid.split(",");
				for(int i=0;i<name.length;i++){
					LineOrderTravelerDTO traveler=new LineOrderTravelerDTO();
					traveler.setIdType(1);
					if(name[i]!=""){
						traveler.setName(name[i]);
					}
					if(i<phone.length){
						traveler.setMobile(phone[i]);
					}
					if(i<tid.length){
						traveler.setType(1);
						traveler.setIdNo(tid[i]);
					}else{
						traveler.setType(2);
					}
					travelerList.add(traveler);
				}
				HgLogger.getInstance().info("zhaows", "commandUser-->得到游客信息"+travelerList);
				hslCreateLineOrderCommand.setTravelerList(travelerList);
				//保存会员信息
				//UserDTO user = getUserBySession(request);
				LineOrderUserDTO lineOrderUser=new LineOrderUserDTO();
				lineOrderUser.setLoginName(user.getAuthInfo().getLoginName());
				lineOrderUser.setMobile(user.getMobile());
				lineOrderUser.setUserId(user.getId());
				hslCreateLineOrderCommand.setLineOrderUser(lineOrderUser);
				hslCreateLineOrderCommand.setSource(1);
				hslCreateLineOrderCommand.getBaseInfo().setTravelDate(DateUtil.parseDateTime(travelDate));
				LineOrderDTO lineOrderDto=lineOrderService.createLineOrder(hslCreateLineOrderCommand);
				lineOrderDto.getBaseInfo().setBargainMoney(map.get("frontMoney"));
				HgLogger.getInstance().info("zhaows","commandUser-->line订单号生成成功：" + JSON.toJSONString(lineOrderDto));
				//经销商订单号
				Integer status = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);//预定成功
				Integer payStatus = Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY);
				UpdateLineOrderStatusCommand updateLineOrderStatusCommand=new UpdateLineOrderStatusCommand();
				updateLineOrderStatusCommand.setOrderStatus(status);
				updateLineOrderStatusCommand.setPayStatus(payStatus);
				updateLineOrderStatusCommand.setDealerOrderCode(lineOrderDto.getBaseInfo().getDealerOrderNo());
				updateLineOrderStatusCommand.setOrderId(lineOrderDto.getId());
				lineOrderService.updateLineOrderStatus(updateLineOrderStatusCommand);
				HgLogger.getInstance().info("zhaows", "commandUser-->修改订单状态成功-->参数" + updateLineOrderStatusCommand);
				model.addAttribute("lineOrderDto", lineOrderDto);
				model.addAttribute("cityMap", getCityMap());
				model.addAttribute("shouldmoneys", Double.parseDouble(shouldmoneys));
				model.addAttribute("routeDay", request.getParameter("routeDay"));
				model.addAttribute("moneyCoupon", moneyCoupon);
				model.addAttribute("hslCreateLineOrderCommand", hslCreateLineOrderCommand);
				if(map.get("identifying")==0.00){
					model.addAttribute("identifying","1");//1标识支付定金
				}else{
					model.addAttribute("identifying","3");//3标识支付全款
					model.addAttribute("balance", map.get("countMoney"));

				}
				return "line/pay.html";
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows","commandUser-->保存订单信息失败" + HgLogger.getStackTrace(e));
			e.printStackTrace();
			return "line/orderworry.html";
		}

	}
	/**
	 * @return
	 * @方法功能说明：调用支付
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月9日下午14.01
	 */
	@RequestMapping(value="/pay")
	@ResponseBody
	public Object pay(HttpServletRequest request,HttpServletResponse response,Model model,HslCreateLineOrderCommand hslCreateLineOrderCommand){
		try {
			UserDTO     user = getUserBySession(request);
			String 	dealerOrderNo=request.getParameter("dealerOrderId");
			// String 	shouldmoneys=request.getParameter("shouldmoneys");
			String 	identifying = request.getParameter("identifying");
			/********************删除卡券中的与之相关联的订单号成功*****************************/
			OrderRefundCommand command = new OrderRefundCommand();
			command.setOrderId(dealerOrderNo);
			try {
				couponService.orderRefund(command);
				HgLogger.getInstance().info("zhaows","删除卡券中的与之相关联的订单号成功："+ JSON.toJSONString(command));
			} catch (CouponException e) {
				e.printStackTrace();
				HgLogger.getInstance().error("zhaows","删除卡券中的与之相关联的订单号失败！！"+ HgLogger.getStackTrace(e));
			}
			/****************判断应付金额和页面提交的金额是否一致************************/
			// 保存用户选择的卡券,生成一个订单快照
			String useCouponIDs = request.getParameter("useCouponIDs");

			/****************判断应付金额和页面提交的金额是否一致************************/
			Double balance = judgePayBalance(dealerOrderNo, identifying);//得到实际应付金额
			Double couponBalance = 0.00;
			if (StringUtils.isNotBlank(useCouponIDs)) {
				couponBalance = couponService.queryTotalPrice(useCouponIDs,balance.intValue());//卡券金额
			}
			Double money=(balance * constant - couponBalance * constant) / constant;
			String[] ids =null;
			if(StringUtils.isNotBlank(useCouponIDs)){
				ids = useCouponIDs.split(",");
				BatchConsumeCouponCommand consumeCouponCommand = null;
				consumeCouponCommand = new BatchConsumeCouponCommand();
				consumeCouponCommand.setCouponIds(ids);
				consumeCouponCommand.setOrderId(dealerOrderNo);
				consumeCouponCommand.setPayPrice(balance);
				consumeCouponCommand.setOrderType(ConsumeOrderSnapshot.ORDERTYPE_XL);
				couponService.occupyCoupon(consumeCouponCommand);
				HgLogger.getInstance().info("zhaows", "commandUser-->修改卡券的状态成功-->参数"+JSON.toJSONString(consumeCouponCommand));
			}
			if(money>0.00){
				HgLogger.getInstance().info("zhaows", "commandUser-->调用支付宝"+money);
				return invokingPay(dealerOrderNo,user,money.toString());
			}else{
				HslLineOrderQO hslLineOrderQO=new HslLineOrderQO();
				hslLineOrderQO.setDealerOrderNo(dealerOrderNo);
				LineOrderDTO lineOrderDto=lineOrderService.queryUnique(hslLineOrderQO);
				Date daydate=new Date();
				int frontmoneys=1;
				Date startdate=lineOrderDto.getBaseInfo().getTravelDate();
				long PayTotalDaysBeforeStart=lineOrderDto.getLineSnapshot().getLine().getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
				/*出发时间*/
				long date=startdate.getTime();

				/*定金or全额支付 1为支付定金2为全额支付*/
				if(daydate.getTime()+PayTotalDaysBeforeStart>date){
					//全款支付
					frontmoneys=2;
				}
				List<LineOrderTravelerDTO> travelers=lineOrderDto.getTravelerList();
				for(LineOrderTravelerDTO travelerss:travelers){
					if(travelerss.getLineOrderStatus().getOrderStatus()==Integer.parseInt((XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT))){
						HgLogger.getInstance().info("zhaows", "asyncPaymentRequest调用支付平台>>支付尾款修改订单号");
						frontmoneys=3;
						break;
					}
				}
				/*start 修改订单状态*/
				//判断是否是全额支付
				Integer statuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);//预定成功待付尾款
				Integer payStatuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX);
				HslLineQO newHslLineQO = new HslLineQO();
				newHslLineQO.setId(hslCreateLineOrderCommand.getLineID());
				LineDTO newLineDTO = hslLineService.queryLine(newHslLineQO);
				HgLogger.getInstance().info("yuqz", newLog("newLineDTO-975", JSON.toJSONString(newLineDTO)));
				HgLogger.getInstance().info("yuqz", newLog("newLineDTO-975", JSON.toJSONString(newLineDTO)));
				if(frontmoneys==2 || newLineDTO.getPayInfo().getDownPayment() * 100.0/100 == 100.00d){
					model.addAttribute("frontmoney","2");
					statuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);//全款支付成功
					payStatuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);
				}else if(frontmoneys==3){
					statuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_RESERVE_SUCCESS);
					payStatuscoupon = Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS);//支付尾款
				}else{
					model.addAttribute("frontmoney","1");
				}

				UpdateLineOrderStatusCommand updateLineOrderStatusCommands=new UpdateLineOrderStatusCommand();
				updateLineOrderStatusCommands.setOrderStatus(statuscoupon);
				updateLineOrderStatusCommands.setPayStatus(payStatuscoupon);
				updateLineOrderStatusCommands.setDealerOrderCode(lineOrderDto.getBaseInfo().getDealerOrderNo());
				updateLineOrderStatusCommands.setOrderId(lineOrderDto.getBaseInfo().getId());
				lineOrderService.updateLineOrderStatus(updateLineOrderStatusCommands);
				HgLogger.getInstance().info("zhaows","commandUser-->修改订单状态："+ lineOrderDto.getBaseInfo().getDealerOrderNo());
				/*end 修改订单状态*/
				// 修改卡券的状态
				if(useCouponIDs!=""){
					BatchConsumeCouponCommand consumeCouponCommand = null;
					consumeCouponCommand = new BatchConsumeCouponCommand();
					consumeCouponCommand.setCouponIds(ids);
					consumeCouponCommand.setOrderId(dealerOrderNo);
					consumeCouponCommand.setPayPrice(balance);
					couponService.confirmConsumeCoupon(consumeCouponCommand);
					HgLogger.getInstance().info("zhaows", "commandUser-->卡券支付时修改卡券状态成功-->参数"+consumeCouponCommand);

				}
				// 发放卡券：订单满
				CreateCouponCommand cmd = new CreateCouponCommand();
				cmd.setSourceDetail("订单满送");
				//按总金额满多少发送
				cmd.setPayPrice(balance);
				cmd.setMobile(user.getMobile());
				cmd.setUserId(user.getId());
				cmd.setLoginName(user.getName());
				CouponMessage baseAmqpMessage = new CouponMessage();
				baseAmqpMessage.setMessageContent(cmd);
				String issue = SysProperties.getInstance().get("issue_on_full");
				int type = 0;
				if (StringUtils.isBlank(issue))
					type = 2;// 默认为2
				else {
					type = Integer.parseInt(issue);
				}
				baseAmqpMessage.setType(type);
				baseAmqpMessage.setSendDate(new Date());
				baseAmqpMessage.setArgs(null);
				template.convertAndSend("hsl.order", baseAmqpMessage);
				HgLogger.getInstance().info("zhaows", "asyncPaymentRequest-->卡券发放成功"+baseAmqpMessage);
				/*发送短信*/
				int Status=payStatuscoupon;
				String paymentorPaid="定金";
				if(Status==Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS)){
					paymentorPaid="全款";
				}
				try {
					String	smsContent = ("【"+SysProperties.getInstance().get("sms_sign", "票量旅游")+"】您的订单"+ lineOrderDto.getBaseInfo().getDealerOrderNo()+ "，订单"+paymentorPaid+"支付成功。查询订单请点击http://m.ply365.com，客服电话：0571-28280813。");
					smsUtils.sendSms(lineOrderDto.getLinkInfo().getLinkMobile(), smsContent);
					HgLogger.getInstance().info("zhaows", "commandUser-->mobile:" + lineOrderDto.getLinkInfo().getLinkMobile() + " smsContent:" + smsContent);

				} catch (Exception e) {
					e.printStackTrace();
					HgLogger.getInstance().error("zhaows","pay-->短信发送失败" + HgLogger.getStackTrace(e));
				}
				//返回订单号预定线路
				model.addAttribute("dealerOrderNo", lineOrderDto.getBaseInfo().getDealerOrderNo());
				model.addAttribute("lineName", lineOrderDto.getLineSnapshot().getLineName());
				String lineName=java.net.URLDecoder.decode(lineOrderDto.getLineSnapshot().getLineName(),"UTF-8");
				return "<script type='text/javascript'>location.href='"+request.getContextPath()+"/line/success?dealerOrderNo="+dealerOrderNo+"&lineName="+lineName+"'</script>";

			}

		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows","pay-->调用支付宝失败" + HgLogger.getStackTrace(e));
			e.printStackTrace();
			return "<script type='text/javascript'>location.href='"+request.getContextPath()+"/line/worry'</script>";

		}

	}
	/**
	 * 跳转错误页面,支付成功页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/success")
	public String payssucce(Model model, HttpServletRequest request) {
		return "line/success.html";
	}
	/**
	 * 跳转错误页面,支付成功页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/worry")
	public String payworry(Model model, HttpServletRequest request) {
		return "line/worry.html";
	}
	/**
	 * 获取浏览记录
	 *
	 * @param model
	 * @param userId
	 * */
	/*private void getRecord(String userId, final Model model,HttpServletRequest request) {
		HgLogger.getInstance().debug("zhaows", "线路列表,浏览记录查询");
		//没有用户登录的时候默认将浏览记录添加到session中，查询的浏览记录也从session中取
		if (StringUtils.isBlank(userId)) {
			List<MPSimpleDTO> record = new ArrayList<MPSimpleDTO>();
			@SuppressWarnings("unchecked")
			HashMap<String, MPSimpleDTO> recordMap = (HashMap<String, MPSimpleDTO>) request.getSession().getAttribute("recordMap");
			if (recordMap != null) {
				Set<String> keySet = recordMap.keySet();
				for (String key : keySet) {
					record.add(recordMap.get(key));
				}
			}
			model.addAttribute("record", record);
		} else {
			//有用户按用户id查询浏览记录
			HslUserClickRecordQO ucrq = new HslUserClickRecordQO();
			ucrq.setPageSize(10);
			ucrq.setUserId(userId);
			List<MPSimpleDTO> record = new ArrayList<MPSimpleDTO>();
			record = memberService.queryUserClickRecord(ucrq);
			model.addAttribute("record", record);
		}
	}*/
	/**
	 * @方法功能说明：根据订单号查询游玩人人数 定金 支付尾款
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月13日下午16.09
	 */
	@RequestMapping(value="/payBalance")
	public String payBalance(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			/*查询订单*/
			HslLineOrderQO hslLineOrderQO =new HslLineOrderQO();
			hslLineOrderQO.setDealerOrderNo(request.getParameter("orderid"));
			LineOrderDTO lineOrderdto=lineOrderService.queryUnique(hslLineOrderQO);
			String identification=request.getParameter("identification");
			model.addAttribute("cityMap", getCityMap());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(identification.equals("1")){
				// 如果支付成功，则同时修改与该订单相关的卡券的状态为已消费状态
				HslCouponQO hslCouponQO = new HslCouponQO();
				hslCouponQO.setOrderId(request.getParameter("orderid"));
				// 根据订单号查询一个与订单绑定的卡券快照
				HslLineQO hslLineQO=new HslLineQO();
				//得到线路id和出发日期判断是支付全款还是定金
				String lineid=lineOrderdto.getLineSnapshot().getLine().getId();
				Date endDateTime=lineOrderdto.getBaseInfo().getTravelDate();
				hslLineQO.setId(lineid);
				//hslLineQO.setEndDateTime(endDateTime);
				LineDTO line=hslLineService.queryUnique(hslLineQO);
				//得到当天时间
				Date daydate=new Date();
				long payTotalDaysBeforeStart=line.getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
				//出发时间
				long date=endDateTime.getTime();
				//定金or全额支付 1为支付定金2为全额支付
				if(sdf.parse(sdf.format(daydate)).getTime()+payTotalDaysBeforeStart<=date){
					//支付定金
					lineOrderdto.getBaseInfo().setBargainMoney(lineOrderdto.getBaseInfo().getBargainMoney());
				}else{
					//全额支付
					lineOrderdto.getBaseInfo().setBargainMoney(lineOrderdto.getBaseInfo().getSalePrice());
				}
				model.addAttribute("lineOrderDto", lineOrderdto);
				model.addAttribute("identifying","1");
				return"line/pay.html";
			} else {
				//根据订单号查询几个人出去游玩
				List<LineOrderTravelerDTO> lineOrderTravelerDTO=lineOrderdto.getTravelerList();
				HgLogger.getInstance().info("zhaows","payBalance-->根据订单号查询游玩人人数 定金 支付尾款成功："+ lineOrderTravelerDTO.size());
				Double balance=0.00;
				for(LineOrderTravelerDTO  lineOrderTraveler:lineOrderTravelerDTO){
					if(lineOrderTraveler.getLineOrderStatus().getPayStatus()==Integer.parseInt((XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY))){
						//得到单人尾款
						balance+=lineOrderTraveler.getSingleSalePrice()-lineOrderTraveler.getSingleBargainMoney();
					}
				}
				model.addAttribute("lineOrderDto", lineOrderdto);
				model.addAttribute("identifying","2");
				model.addAttribute("balance",balance);
				return"line/pay.html";
			}


		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","payBalance-->根据订单号查询游玩人人数 定金 支付尾款失败" + HgLogger.getStackTrace(e));
			return "line/worry.html";
		}
	}

	public String invokingPay(String dealerOrderNo,UserDTO user,String monery){
		try {
			HslLineOrderQO hslLineOrderQO=new HslLineOrderQO();
			hslLineOrderQO.setDealerOrderNo(dealerOrderNo);
			LineOrderDTO lineOrderdto=lineOrderService.queryUnique(hslLineOrderQO);
			HgLogger.getInstance().info("zhaows", "调用支付平台>>支付订单"+JSON.toJSONString(lineOrderdto));
			Set<LineOrderTravelerDTO> travelers=lineOrderdto.getTravelers();
			for(LineOrderTravelerDTO travelerss:travelers){
				if(travelerss.getLineOrderStatus().getOrderStatus()==Integer.parseInt((XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT))){
					HgLogger.getInstance().info("zhaows", "调用支付平台>>支付尾款修改订单号");
					dealerOrderNo=dealerOrderNo+"_two";
					break;
				}
			}
			/*			调用支付宝					*/
			String messageUrl = "";// 消息地址
			// 创建一个支付command
			CreateAlipayPayOrderCommand alipayPayOrder = new CreateAlipayPayOrderCommand();
			//商城在支付平台编号
			String   clientId = SysProperties.getInstance().get("paymentLineClientId");
			//商城在支付平台密钥
			String   clientKeys = SysProperties.getInstance().get("paymentLineClienKeys");
			/*支付渠道 支付宝支付为1*/
			Integer payType = Integer.parseInt("1");
			// 创建一个支付请求dto
			PayOrderRequestDTO payOrderRequestDto = new PayOrderRequestDTO();
			payOrderRequestDto.setClientTradeNo(dealerOrderNo);// 生成订单后返回的商城订单号
			payOrderRequestDto.setPayerClientUserId(user.getId());// 登录用户的id
			payOrderRequestDto.setName(user.getBaseInfo().getName());// 登录用户的真实姓名
			payOrderRequestDto.setIdCardNo(user.getBaseInfo().getIdCardNo());// 登录用户的身份证号
			payOrderRequestDto.setMobile(user.getMobile());// 登录用户的手机号
			payOrderRequestDto.setAmount(Double.parseDouble(monery));// 订单的支付总价
			//payOrderRequestDto.setAmount(Double.parseDouble("0.01"));
			payOrderRequestDto.setPaymentClientId(clientId);
			payOrderRequestDto.setPaymentClienKeys(clientKeys);
			payOrderRequestDto.setPayChannelType(payType);
			// 收款方
			payOrderRequestDto.setPayeeAccount(SysProperties.getInstance().get("line_payeeAccount")==null?AlipayConfig.email:SysProperties.getInstance().get("line_payeeAccount"));
			payOrderRequestDto.setPayeeName(SysProperties.getInstance().get("line_payeeName")==null?"票量旅游":SysProperties.getInstance().get("line_payeeName"));
			payOrderRequestDto.setPayerRemark(SysProperties.getInstance().get("line_payerRemark")==null?"":SysProperties.getInstance().get("line_payerRemark"));
			// 收款方
			alipayPayOrder.setPartner(SysProperties.getInstance().get("line_partner")==null?AlipayConfig.partner:SysProperties.getInstance().get("line_partner"));
			alipayPayOrder.setKeys(SysProperties.getInstance().get("line_key")==null?AlipayConfig.key:SysProperties.getInstance().get("line_key"));
			alipayPayOrder.setSubject("国内游:订单号["+dealerOrderNo+"]");
			alipayPayOrder.setBody(AppInfoUtils.getAlipayRemarkPrefix() + (SysProperties.getInstance().get("body") == null ? "" : SysProperties.getInstance().get("body")));
			alipayPayOrder.setShowUrl(SysProperties.getInstance().get("showUrl") == null ? "": SysProperties.getInstance().get("showUrl"));
			// 创建支付订单
			alipayPayOrder.setPayOrderCreateDTO(payOrderRequestDto);
			String context  =  JSON.toJSONString(alipayPayOrder);
			HgLogger.getInstance().info("zhaows", "支付平台生成订单,请求CreateAlipayPayOrderCommand参数："+JSON.toJSONString(context));
			messageUrl = payOrderSpiService.pay(clientId, clientKeys, payType, context);

			return messageUrl;
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows","invokingPay-->调用支付宝失败" + HgLogger.getStackTrace(e));
			e.printStackTrace();
			return "line/worry.html";
		}
	}
	/**
	 *
	 * @方法功能说明：查询是否已到最晚付款时间
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-17下午4:32:14
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/checkPay")
	@ResponseBody
	private String checkPay(HttpServletRequest request) {
		String msg="";
		try {
			String startdate=request.getParameter("start");
			HslLineQO hslLineQO=new HslLineQO();
			hslLineQO.setId(request.getParameter("lineid"));
			LineDTO line=hslLineService.queryUnique(hslLineQO);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date daydate=new Date();
			/*付款or不付款判断 1能付款 2不能付款*/
			long lastPayTotalDaysBeforeStart=line.getPayInfo().getLastPayTotalDaysBeforeStart()*24*60*60*1000;
			if(sdf.parse(sdf.format(daydate)).getTime()+lastPayTotalDaysBeforeStart<=sdf.parse(startdate).getTime()){
				msg="1";
			}else{
				msg="2";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(msg);
	}
	/**
	 * @方法功能说明：判断应付费用
	 * @创建者名字：zhaows
	 * @创建时间：2015-10-30上午10:38:58
	 * @参数：@String orderId   订单号
	 * @参数：@String identification  类型判断是付全款还是尾款
	 * @return:Double
	 */
	public Double judgePayBalance(String orderId, String identifying) {
		Double balance = 0.00;
		try {
		/*查询订单*/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
			hslLineOrderQO.setDealerOrderNo(orderId);
			LineOrderDTO lineOrderdto = lineOrderService.queryUnique(hslLineOrderQO);
			if (identifying.equals("1")||identifying.equals("3")) {//1定金 3全款
				// 如果支付成功，则同时修改与该订单相关的卡券的状态为已消费状态
				HslCouponQO hslCouponQO = new HslCouponQO();
				hslCouponQO.setOrderId(orderId);
				HslLineQO hslLineQO = new HslLineQO();
				//得到线路id和出发日期判断是支付全款还是定金
				String lineid = lineOrderdto.getLineSnapshot().getLine().getId();
				Date endDateTime = lineOrderdto.getBaseInfo().getTravelDate();
				hslLineQO.setId(lineid);
				LineDTO line = hslLineService.queryUnique(hslLineQO);
				//得到当天时间
				Date daydate = new Date();
				long payTotalDaysBeforeStart = line.getPayInfo().getPayTotalDaysBeforeStart() * 24 * 60 * 60 * 1000;
				//出发时间
				long date = endDateTime.getTime();
				//定金or全额支付 1为支付定金2为全额支付
				if (sdf.parse(sdf.format(daydate)).getTime() + payTotalDaysBeforeStart <= date) {
					//支付定金
					balance = lineOrderdto.getBaseInfo().getBargainMoney();
				} else {
					//全额支付
					balance = lineOrderdto.getBaseInfo().getSalePrice();
				}
				return balance;
			} else {
				//根据订单号查询几个人出去游玩
				List<LineOrderTravelerDTO> lineOrderTravelerDTO = lineOrderdto.getTravelerList();
				HgLogger.getInstance().info("zhaows", "judgePayBalance-->根据订单号查询游玩人人数 定金 支付尾款成功：" + lineOrderTravelerDTO.size());
				for (LineOrderTravelerDTO lineOrderTraveler : lineOrderTravelerDTO)
					if (lineOrderTraveler.getLineOrderStatus().getPayStatus() == Integer.parseInt((XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY))) {
						//得到单人尾款
						balance += lineOrderTraveler.getSingleSalePrice() - lineOrderTraveler.getSingleBargainMoney();
					}
				return balance;
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "judgePayBalance" + HgLogger.getStackTrace(e));
			return balance;
		}
	}
	/**
	 *
	 * @方法功能说明：下单时判断应付金额
	 * @创建者名字：zhaows
	 * @创建时间：2015-11-12下午4:51:55
	 * @参数：@param adults 成人人数
	 * @参数：@param chidrens 儿童人数
	 * @参数：@param lineId 线路Id
	 * @参数：@param startdate 出发时间
	 * @参数：@return
	 * @return:Double
	 * @throws
	 */
	public HashMap<String,Double> lineCost(int adult,int chidren,String lineId,String startdate) throws ParseException {
		HashMap<String,Double> map=new HashMap<String, Double>();
		Double frontMoney = 0.00;
		Double trailMoney = 0.00;
		Double identifying=0.00;//0.00标识支付定金  1.00标识支付尾款
		try {
			HslLineQO hslLineQO = new HslLineQO();
			DecimalFormat decimalFormat=new DecimalFormat("0.00");
			//加载日历
			hslLineQO.setId(lineId);
			hslLineQO.setGetDateSalePrice(true);
			hslLineQO.setForSale(1);
			LineDTO line = hslLineService.queryUnique(hslLineQO);
			HgLogger.getInstance().info("zhaows", "lineCost-getReseve-->查询当前线路成功，查询的QO：" + line);
			if (line != null) {
				//查询当前线路
				//查询当前线路是否有对应的余票  传入线路Id,时间,成人人数,儿童人数
				List<DateSalePriceDTO> list = line.getDateSalePriceList();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Double adultPrice=0.00;
				Double childPrice=0.00;
				for(int i=0;i<line.getDateSalePriceList().size();i++){
					if((sdf.parse(sdf.format(line.getDateSalePriceList().get(i).getSaleDate())).getTime()==(sdf.parse(startdate)).getTime())){
						//得到日历价格
						adultPrice=line.getDateSalePriceList().get(i).getAdultPrice();
						childPrice=line.getDateSalePriceList().get(i).getChildPrice();
					}
				}
				/*********************** 计算是否付全款****************/
				Double DownPayment=line.getPayInfo().getDownPayment()/100;//支付定金比例
				//得到当天时间
				Date daydate = new Date();
				long payTotalDaysBeforeStart = line.getPayInfo().getPayTotalDaysBeforeStart() * 24 * 60 * 60 * 1000;
				// 出发时间
				long date = sdf.parse(startdate).getTime();
				Double countMoney= Double.parseDouble(String .format("%.2f",adultPrice*adult+childPrice*chidren));
				// 定金or全额支付 1为支付定金2为全额支付
				if (daydate.getTime() + payTotalDaysBeforeStart <= date) {
					//支付定金  成人人数*成人价格+儿童人数*儿童价格
					//model.addAttribute("frontmoney", "1");
					frontMoney=Double.parseDouble(String .format("%.2f",countMoney*DownPayment));
					trailMoney=Double.parseDouble(String .format("%.2f",countMoney-frontMoney));

				} else {
					//全额支付
					frontMoney=Double.parseDouble(String .format("%.2f",countMoney));
					identifying=1.00;
				}
				map.put("frontMoney",frontMoney);
				map.put("trailMoney",trailMoney);
				map.put("countMoney",countMoney);
				map.put("identifying",identifying);

			}
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "lineCost-getReseve-->线路详情查询失败" + HgLogger.getStackTrace(e));
		}
		return  map;//返回定金和尾款
	}
}
