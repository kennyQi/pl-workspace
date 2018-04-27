package hsl.web.controller.jp;

import com.alibaba.fastjson.JSON;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.payment.pojo.command.dto.PayOrderRequestDTO;
import hg.payment.pojo.command.spi.payorder.alipay.CreateAlipayPayOrderCommand;
import hg.payment.pojo.dto.payorder.PayOrderDTO;
import hg.payment.pojo.exception.PaymentException;
import hg.payment.pojo.qo.payorder.PayOrderQO;
import hg.payment.spi.inter.PayOrderSpiService;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.jp.DealerReturnInfo;
import hsl.domain.model.sys.CityAirCode;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.account.AccountCommand;
import hsl.pojo.command.account.AccountConsumeSnapshotCommand;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.command.jp.BookGNFlightCommand;
import hsl.pojo.command.jp.JpScartCommand;
import hsl.pojo.command.jp.UpdateJPOrderStatusCommand;
import hsl.pojo.command.jp.plfx.JPPayOrderGNCommand;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.dto.commonContact.CommonContactDTO;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.pojo.dto.jp.FlightGNDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.plfx.JPBookOrderGNDTO;
import hsl.pojo.dto.jp.plfx.JPQueryFlightListGNDTO;
import hsl.pojo.dto.jp.plfx.JPQueryHighPolicyGNDTO;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.mp.MPSimpleDTO;
import hsl.pojo.dto.user.UserAuthInfoDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.AccountException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.exception.MPException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.CommonContact.CommonContactQO;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.pojo.qo.account.AccountQO;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.pojo.qo.jp.plfx.JPFlightGNQO;
import hsl.pojo.qo.jp.plfx.JPPolicyGNQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.pojo.qo.mp.HslUserClickRecordQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.account.AccountConsumeSnapshotService;
import hsl.spi.inter.account.AccountService;
import hsl.spi.inter.commonContact.CommonContactService;
import hsl.spi.inter.company.CompanyService;
import hsl.spi.inter.jp.JPService;
import hsl.spi.inter.mp.MPScenicSpotService;
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
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 *
 * @类功能说明： 机票控制器
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015-7-22下午3:29:07
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
@Controller
@RequestMapping("/jp")
public class JpController extends BaseController {

	@Resource
	private JPService jpService;
	@Resource
	private UserService memberService;

	@Resource
	private MPScenicSpotService scenicSpotService;

	@Resource
	private CompanyService companyService;

	@Resource
	private PayOrderSpiService payOrderSpiService;

	@Resource
	private CouponService couponService;

	@Resource
	private SMSUtils smsUtils;

	@Autowired
	private RabbitTemplate template;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountConsumeSnapshotService accountConsumeSnapshotService;
	@Autowired
	private CommonContactService commonContactService;

	private Integer constant=100;
	/**
	 * @方法功能说明：跳转机票的主页
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月13日下午3:24:24
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/main")
	public String main(HttpServletRequest request, Model model) {
		UserDTO user = getUserBySession(request);
		String userId = "";
		if (user != null) {
			userId = user.getId();
			HgLogger.getInstance().debug("zhaows", "机票航班列表有用户登录："+ user.getAuthInfo().getLoginName());
		} else {
			HgLogger.getInstance().debug("zhaows", "机票航班列表没有用户登录");
		}
		// 获取浏览记录
		getRecord(userId, model, request);
		// 获取热门景点数据
		getHotMpScenicSpots(model);
		return "ticket/ticket_list.html";
	}
	/**
	 * @方法功能说明：查询航班列表
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-23上午9:11:20
	 * @参数：@param jPFlightGNQO
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @参数：@throws UnsupportedEncodingException
	 * @return:Object
	 * @throws
	 */
	@RequestMapping("/ticketList")
	@ResponseBody
	public Object ticketList(JPFlightGNQO jPFlightGNQO,HttpServletRequest request, HttpServletResponse response,
							 Model model) throws UnsupportedEncodingException {
		String from= request.getParameter("orgCity");
		String arrive= request.getParameter("dstCity");
		HgLogger.getInstance().info("zhaows","查询机票列表的出发城市与到达城市:>>>" + JSON.toJSONString(jPFlightGNQO)+from+arrive);
		String[] cityName = new String[2];
		String[] cityNameCode = new String[2];
		//Boolean isCityAirCode = true;// 标识城市是否有三字码，无则不查询
		if (from != null && arrive!= null) {
			from=java.net.URLDecoder.decode(from,"UTF-8");
			arrive=java.net.URLDecoder.decode(arrive,"UTF-8");
			jPFlightGNQO.setOrgCityName(from);
			jPFlightGNQO.setDstCityName(arrive);
			cityName[0] = from;// 获取出发城市名称
			cityName[1] = arrive;// 获取到达城市名称
			for (int i = 0; i < cityName.length; i++) {
				CityAirCodeQO cityaircodeqo = new CityAirCodeQO();
				cityaircodeqo.setName(cityName[i]);
				CityAirCode cityaircode = new CityAirCode();
				CityAirCodeDTO cityAirCodeDTO = jpService.queryLocalCityAirCode(cityaircodeqo);
				HgLogger.getInstance().info("zhaows","出发到达城市的三字码，查询回传：" + JSON.toJSONString(cityaircode));
				// 判断所选城市是否有对应的三字码
				if (cityAirCodeDTO != null && !cityAirCodeDTO.getCityAirCode().equals("")) {
					cityNameCode[i] = cityAirCodeDTO.getCityAirCode();
					jPFlightGNQO.setOrgCity(cityNameCode[0]);
					jPFlightGNQO.setDstCity(cityNameCode[1]);
				} else {
					main(request, model);
				}
			}
		} else {
			main(request, model);
		}
		String airtime= request.getParameter("clock");
		//String time= request.getParameter("time");
		if(!airtime.equals("undefined")){
			airtime=java.net.URLDecoder.decode(airtime,"UTF-8");
			if(airtime.equals("起飞时间")){
				airtime="";
			}
		}
		String airportname= request.getParameter("airCompany");
		if(!airportname.equals("undefined")){
			airportname=java.net.URLDecoder.decode(airportname,"UTF-8");
			if(airportname.equals("航空公司")){
				airportname="";
			}else{
				jPFlightGNQO.setAirCompany(airportname);
			}

		}
		//判断查询QO中的出发城市、到达城市以及出发日期是否为空，如果为空就直接跳转机票主页
		if (StringUtils.isBlank(jPFlightGNQO.getOrgCity())&&StringUtils.isBlank(jPFlightGNQO.getDstCity()) &&StringUtils.isBlank(jPFlightGNQO.getStartDate())) {
			return new RedirectView("/jp/main", true);
		}
		Map<String, Object> map = new HashMap<String,Object>();
		JPQueryFlightListGNDTO flightDTOs=new JPQueryFlightListGNDTO();
		List<FlightGNDTO> flightList=new ArrayList<FlightGNDTO>();
		List<FlightGNDTO> flightListByAir = new ArrayList<FlightGNDTO>();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
			String nextDate = "";// 声明指定日期的后一天变量
			String pvDate = "";// 声明指定日期的前一天变量
			// 赋值完成后调用jpService中的queryFlight()进行航班列表查询
			if (StringUtils.isNotBlank(jPFlightGNQO.getOrgCity())&&StringUtils.isNotBlank(jPFlightGNQO.getDstCity()) &&StringUtils.isNotBlank(jPFlightGNQO.getStartDate())) {
				// 获取指定时间的后一天
				nextDate = getSpecifiedDayAfter(jPFlightGNQO.getStartDate());
				pvDate = getSpecifiedDayBefore(jPFlightGNQO.getStartDate());
				// 真实航班列表查询
				flightDTOs = jpService.queryFlight(jPFlightGNQO);
				flightList=flightDTOs.getFlightList();
			} else {
				main(request, model);
			}
			if (flightList!= null && flightList.size() > 0) {
				for (int i = 0; i < flightList.size(); i++) {
					flightList.get(i).setMinPic(flightList.get(i).getCabinList().get(flightList.get(i).getCabinList().size()-1).getIbePrice());
					if(airportname == "" && airtime == ""){
						flightListByAir.add(flightList.get(i));
					}else if (airportname != "" && airtime == "") {
						// 循环查询到的结果，删选出符合条件的结果
						if (flightList.get(i).getAirCompanyName().equals(airportname)) {
							flightListByAir.add(flightList.get(i));
						}
					} else if (airtime != "" && airportname == "") {
						if (airtime.equals("上午")) {
							String str1 = "06:00";
							String str2 = "12:00";
							boolean isFlag = compareToTime(str1, str2,sdf.format(flightList.get(i).getStartTime()) );
							if (isFlag) {
								flightListByAir.add(flightList.get(i));
							}
						} else if (airtime.equals("下午")) {
							String str1 = "12:00";
							String str2 = "18:00";
							boolean isFlag =compareToTime(str1, str2,sdf.format(flightList.get(i).getStartTime()) );
							if (isFlag) {
								flightListByAir.add(flightList.get(i));
							}
						} else if (airtime.equals("晚上")) {
							String str1 = "18:00";
							String str2 = "24:00";
							boolean isFlag = compareToTime(str1, str2,sdf.format(flightList.get(i).getStartTime()) );
							if (isFlag) {
								flightListByAir.add(flightList.get(i));
							}
						}
					} else if (airportname != "" && airtime != "") {
						if (airtime.equals("上午")) {
							String str1 = "06:00";
							String str2 = "12:00";
							boolean isFlag = compareToTime(str1, str2,sdf.format(flightList.get(i).getStartTime()) );
							if (isFlag) {
								if (flightList.get(i).getAirCompanyName().equals(
										airportname)) {
									flightListByAir.add(flightList.get(i));
								}
							}
						} else if (airtime.equals("下午")) {
							String str1 = "12:00";
							String str2 = "18:00";
							boolean isFlag = compareToTime(str1, str2,sdf.format(flightList.get(i).getStartTime()) );
							if (isFlag) {
								if (flightList.get(i).getAirCompanyName().equals(
										airportname)) {
									flightListByAir.add(flightList.get(i));
								}
							}
						} else if (airtime.equals("晚上")) {
							String str1 = "18:00";
							String str2 = "24:00";
							boolean isFlag = compareToTime(str1, str2,sdf.format(flightList.get(i).getStartTime()) );
							if (isFlag) {
								if (flightList.get(i).getAirCompanyName().equals(
										airportname)) {
									flightListByAir.add(flightList.get(i));
								}
							}
						}
					}
				}
				map.put("list", flightListByAir);
				map.put("buildFee", (flightDTOs.getBuildFee()));
				map.put("oilFee", (flightDTOs.getOilFee()));
				map.put("buildFeeOrOilFee", (flightDTOs.getBuildFee()+flightDTOs.getOilFee()));
			}

			/**
			 * 获取用户区分是企业用户还是个人用户
			 */
			UserDTO user = getUserBySession(request);
			String userId = "";
			if (user != null) {
				userId = user.getId();// 获取user的ID号
				HgLogger.getInstance().debug("zhaows", "机票航班列表有用户登录："+ user.getAuthInfo().getLoginName());
			} else {
				HgLogger.getInstance().debug("zhaows", "机票航班列表没有用户登录");
			}
			// 获取浏览记录
			getRecord(userId, model, request);

			// 获取热门景点数据
			getHotMpScenicSpots(model);
			model.addAttribute("buildFeeOrOilFee", flightDTOs.getBuildFee()+flightDTOs.getOilFee());
			model.addAttribute("nextDate", nextDate);
			model.addAttribute("pvDate", pvDate);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","JpController->tickList->NullPointException:"+ HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(map);
	}
	/**
	 * @方法功能说明：查询航班是否能预定
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-23上午9:11:51
	 * @参数：@param model
	 * @参数：@param jPPolicyGNQO
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/price")
	@ResponseBody
	public String jpPrice(Model model, JPPolicyGNQO jPPolicyGNQO,HttpServletRequest request) {
		// 拿到user的类型，确定是个人用户还是企业用户
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			String encryptString=jPPolicyGNQO.getEncryptString();
			jPPolicyGNQO.setEncryptString(encryptString);//设置加密字符串
			jPPolicyGNQO.setTickType(DealerReturnInfo.JP_ORDER_TICKTYPE_ALL);//票号类型   
			HgLogger.getInstance().info("zhaows", "查询航班政策,查询的机票政策的QO：" + JSON.toJSONString(jPPolicyGNQO));
			JPQueryHighPolicyGNDTO flightPolicy = jpService.queryFlightPolicy(jPPolicyGNQO);
			HgLogger.getInstance().info("zhaows", "查询航班政策,返回一个航班政策dto：" + JSON.toJSONString(flightPolicy));
			if (flightPolicy.getPricesGNDTO() != null) {//判断返回的航班政策是否为空
				map.put("flightPolicy", flightPolicy);
				return JSON.toJSONString(map);
			}else{
				map.put("error", "error");
				return JSON.toJSONString(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "查询航班政策--->price:" + HgLogger.getStackTrace(e));
			map.put("error", "error");
			return JSON.toJSONString(map);
		}
	}
	/**
	 *
	 * @方法功能说明：航班列表机票点击预订并跳转到填写订单页面
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-17上午10:19:23
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/scart")
	public String scart(Model model,JpScartCommand command  ,HttpServletRequest request) {
		try {
			HgLogger.getInstance().info("zhaows", "scart--参数"+JSON.toJSONString(command));
			model.addAttribute("jpScartCommand", command);
			// 拿到user的类型，确定是个人用户还是企业用户
			UserDTO user = getUserBySession(request);
			CommonContactQO commonContactQO=new CommonContactQO();
			commonContactQO.setUserId(user.getId());
			List<CommonContactDTO>  commonContactDTOList=commonContactService.queryList(commonContactQO);
			model.addAttribute("commonContactDTOList", commonContactDTOList);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "查询联系人信息--->scart:" + HgLogger.getStackTrace(e));
		}
		return "ticket/scart.html";
	}

	/**
	 * @方法功能说明：航班机票创建订单处理
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-27上午10:33:17
	 * @参数：@param command
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping("/createOrder")
	public Object createOrder(BookGNFlightCommand command, Model model,HttpServletRequest request
	) {
		HgLogger.getInstance().info("zhaows","查询当前传入参数" + JSON.toJSONString(command));
		try {
			//查询政策判断总价格和用户提交的价格是否一致
			JPPolicyGNQO jPPolicyGNQO =new JPPolicyGNQO();
			jPPolicyGNQO.setEncryptString(request.getParameter("flightencryptString"));//仓位加密字符串
			jPPolicyGNQO.setTickType(DealerReturnInfo.JP_ORDER_TICKTYPE_ALL);//票号类型   
			HgLogger.getInstance().info("zhaows", "查询航班政策,查询的机票政策的QO--createOrder:" + JSON.toJSONString(jPPolicyGNQO));
			JPQueryHighPolicyGNDTO flightPolicy = jpService.queryFlightPolicy(jPPolicyGNQO);
			HgLogger.getInstance().info("zhaows", "查询航班政策,返回一个航班政策dto--createOrder:" + JSON.toJSONString(flightPolicy));
			if(flightPolicy!=null&&flightPolicy.getPricesGNDTO()!=null&&command!=null&&command.getPassengers()!=null&&
					command.getFlightPriceInfoDTO()!=null){
				if(flightPolicy.getPricesGNDTO().getSinglePlatTotalPrice()*command.getPassengers().size()!=command.getFlightPriceInfoDTO().getPayAmount()){
					return new RedirectView("/jp/fail", true);
				}
				command.getFlightPriceInfoDTO().setSinglePlatTotalPrice(flightPolicy.getPricesGNDTO().getSinglePlatTotalPrice());
				String startDate=request.getParameter("startDate");
				String endDate=request.getParameter("endDate");
				/***********************设置登录用户信息start**************************************/
				UserDTO user = getUserBySession(request); // 获取登录用户的信息
				command.setUserID(user.getId());// 设置联系人的ID号为登录用户的ID号
				command.setUserName(user.getAuthInfo().getLoginName());
				command.setUserMobilePhone(user.getContactInfo().getMobile());
				/***********************查询订单库中是否存在在当日该趟航班用该身份证start****************/
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				flightOrderQO.setIdNo(command.getPassengers().get(0).getIdNo());//身份证号
				flightOrderQO.setStartDate(sdf.parse(startDate));//起飞日期
				flightOrderQO.setFlightNo(command.getFlightNO());//航班号
				//flightOrderQO.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));
				// 创建订单之前先进行一次订单查询，查询订单库中是否存在在当日该趟航班用该身份证就行下订单操作
				HgLogger.getInstance().info("zhaows","createOrder---->查询当前订单查询条件" + JSON.toJSONString(flightOrderQO));
				List<FlightOrderDTO> jPBookOrderGNDTOList = jpService.queryOrder(flightOrderQO);
				HgLogger.getInstance().info("zhaows","查询一个订单，返回一个订单记录：" + JSON.toJSONString(jPBookOrderGNDTOList));
				for(FlightOrderDTO jPBookOrderGNDTO:jPBookOrderGNDTOList){
					if(jPBookOrderGNDTO.getPayStatus()!=Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC)){
						return new RedirectView("/jp/fail", true);	// 用来限定一趟航班一个身份证号在当天只能预订一次
					}
				}
				/***********************查询订单库中是否存在在当日该趟航班用该身份证end*******************/
				FlightOrderDTO jporderdto = null;
				command.setStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_PAY_WAIT));
				command.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));
				HgLogger.getInstance().info("zhaows", "createOrder------->订单信息" + JSON.toJSONString(command));
				for(int i=0;i<command.getPassengers().size();i++){//设置证件类型和乘客类型
					command.getPassengers().get(i).setPassengerType("1");
					command.setLinkName(user.getBaseInfo().getName());//临时设置登录用户为联系人
				}
				command.setStartTime(sdf.parse(startDate));
				command.setEndTime(sdf.parse(endDate));
				command.setCreateTime(new Date());//设置创建时间
				HgLogger.getInstance().info("zhaows","创建订单"+ JSON.toJSONString(command));
				/**********创建订单只保存本地订单***************/
				jporderdto = jpService.createLocalityOrder(command);
				HgLogger.getInstance().info("zhaows", "createOrder----->创建一个订单，返回一个订单dto：" + JSON.toJSONString(command));
				//创建订单成功后会有一个订单号返回，取得该订单号查询一条该条订单记录，返回JPorder
//				OrderRefundCommand com = new OrderRefundCommand();
//				com.setOrderId(jporderdto.getOrderNO());
//				couponService.orderRefund(com);
				/**********删除有占用状态的消费快照*************/
//				this.updateAccount(user.getId(), jporderdto.getOrderNO());
				if (jporderdto != null) {
					//查询订单
					long time=jporderdto.getFlightBaseInfo().getEndTime().getTime()-jporderdto.getFlightBaseInfo().getStartTime().getTime();//得到起飞和到达的时间毫秒差
					model.addAttribute("flyTime", time/60000);//返回分钟
					model.addAttribute("flightOrderDTO", jporderdto);
					model.addAttribute("jporderdto", jporderdto);
					HashMap<String, String> jpStatusMap = JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP;
					model.addAttribute("jpStatusMap", jpStatusMap);
					return "ticket/payOrder_info.html";	// 返回到支付页面
				}else{
					return new RedirectView("/jp/error", true);
				}
			}else{
				return new RedirectView("/jp/fail", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","订单创建失败>createOrder-->exception:"+ HgLogger.getStackTrace(e));
			return new RedirectView("/jp/error", true);// 如果返回的jporderdto为空，则下单失败
		}
	}

	/**
	 * @方法功能说明：支付页面提交订单之前，验证卡券是否可用
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月13日下午3:32:31
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/getCouponUsed")
	@ResponseBody
	private String getCouponUsed(HttpServletRequest request) {
		// 查询用户选择的卡券是否可使用
		UserDTO user = getUserBySession(request); // 获取登录用户的信息
		String useCouponIDs = request.getParameter("useCouponIDs");
		String dealerOrderId = request.getParameter("dealerOrderId");// 获取订单号
		Double payPrice = Double.parseDouble(request.getParameter("payPrice"));// 获取实际价格
		String[] id = useCouponIDs.split(",");
		BatchConsumeCouponCommand consumeCouponCommand = null;
		consumeCouponCommand = new BatchConsumeCouponCommand();
		consumeCouponCommand.setCouponIds(id);
		consumeCouponCommand.setOrderId(dealerOrderId);
		consumeCouponCommand.setPayPrice(payPrice);
		consumeCouponCommand.setUserID(user.getId());
		String message = "";// 标识卡券是否可用的字段变量
		// 如果用户选择了多个卡券，那么每循环出一个卡券id就要去做一次判断，判断该卡券是否可用
		try {
			boolean flag = couponService.checkCoupon(consumeCouponCommand);
			if (flag) {
				HgLogger.getInstance().info("zhaows", "卡券可用");
				message = "success";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			HgLogger.getInstance().error("zhaows","卡券不可用->JpController->payOrder->exception:"+ HgLogger.getStackTrace(ex));
			return ex.getMessage();
		}
		return message;
	}

	/**
	 * @方法功能说明：点击我的订单时，有待支付的订单时，点击操作框中的“去支付”按钮跳转需要调用的方法
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-23下午2:48:45
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping(value = "/toPayOrderInfo")
	public Object toPayOrderInfo(Model model, HttpServletRequest request) {
		String dealerOrderCode = request.getParameter("dealerOrderCode");// 获取到传入的订单号
		FlightOrderQO flightOrderQO = new FlightOrderQO();
		flightOrderQO.setOrderType("1");

		if (dealerOrderCode == null) {//从我的订单跳转到支付页面时传入的订单号是否为空，为空则跳转到登录页
			return new RedirectView("/user/login", true);
		}
		/********************根据订单号获取该号对应的订单记录，此时取到的订单记录的状态为未支付状态******************************/
		flightOrderQO.setOrderNO(dealerOrderCode);//设置订单号
		flightOrderQO.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_PAY_WAIT));//此时订单状态为待确认
		flightOrderQO.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));//此时订单的支付状态为待支付
		if (flightOrderQO != null) {
			try {
				HgLogger.getInstance().info("zhaows","根据我的订单模块传入的订单号查询一条订单记录,查询的QO："+ JSON.toJSONString(flightOrderQO));
				FlightOrderDTO flightOrderDTO = jpService.queryOrderDetail(flightOrderQO);
				HgLogger.getInstance().info("zhaows","根据我的订单模块传入的订单号查询一条订单记录："+ JSON.toJSONString(flightOrderDTO));
				if(flightOrderDTO!=null){
					long time=flightOrderDTO.getFlightBaseInfo().getEndTime().getTime()-flightOrderDTO.getFlightBaseInfo().getStartTime().getTime();//得到起飞和到达的时间毫秒差
					model.addAttribute("flyTime", time/60000);//返回分钟
					model.addAttribute("flightOrderDTO", flightOrderDTO);
					UserDTO user = getUserBySession(request); // 获取登录用户的信息
					if (user.getId().equals(flightOrderDTO.getJpOrderUser().getUserId())) {// 判断该订单是否属于该登录用户
						try {
							/********************删除卡券中的与之相关联的订单号成功*****************************/
							OrderRefundCommand com = new OrderRefundCommand();
							com.setOrderId(dealerOrderCode);
							couponService.orderRefund(com);
							/**********删除有占用状态的消费快照*************/
							this.updateAccount(user.getId(), dealerOrderCode);
						} catch (CouponException e) {
							e.printStackTrace();
							HgLogger.getInstance().error("zhaows","删除卡券中的与之相关联的订单号失败！！"+ HgLogger.getStackTrace(e));
						}
						// 根据登录用户的userid查询该用户拥有的卡券列表\
						HslCouponQO hslCouponQO = new HslCouponQO();
						hslCouponQO.setUserId(user.getId());
						// 只查询未使用的卡券并且满足使用条件的卡券 
						hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
						hslCouponQO.setOrderPrice(flightOrderDTO.getFlightPriceInfo().getPayAmount());//机票总价格
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
								HgLogger.getInstance().info("zhaows","查询用户所拥有的可使用的卡券成功，查询的QO："+ JSON.toJSONString(hslCouponQO));
								model.addAttribute("count", count);
								model.addAttribute("couponNoUsed", couponList);
							} else {
								return new RedirectView("/user/login", true);
							}
						} catch (Exception e) {
							HgLogger.getInstance().error("zhaows","根据登录用户的userid查询该用户拥有的卡券列表失败"+ e.getStackTrace());
						}
						HashMap<String, String> jpStatusMap = JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP;
						model.addAttribute("jpStatusMap", jpStatusMap);
					} else {
						HgLogger.getInstance().error("zhaows","根据我的订单模块传入的订单号查询订单记录,该订单不属于该登录用户！！");
						return new RedirectView("/jp/worry", true);
					}
				}else{
					HgLogger.getInstance().error("zhaows","根据我的订单模块传入的订单号查询订单记录,该订单已经取消！！");
					return new RedirectView("/jp/worry", true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().error("zhaows","根据我的订单模块传入的订单号查询一条订单记录失败！！"+ HgLogger.getStackTrace(e));
				return new RedirectView("/jp/worry", true);
			}
		}
		return "ticket/payOrder_info.html";
	}

	/**
	 *
	 * @方法功能说明：支付页面提交订单到该方法，该方法去访问支付平台，请求支付
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-23下午5:20:48
	 * @参数：@param command
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping(value = "/payOrder")
	@ResponseBody
	public Object payOrder(JPPayOrderGNCommand command, Model model,HttpServletRequest request) {
		HgLogger.getInstance().info("zhaows",
				"JPController>>createClientNotify>支付command" + JSON.toJSONString(command));
		// 如果直接回车访问payOrder方法则直接返回航班列表查询页
		if (command == null) {
			return new RedirectView("/jp/ticketList", true);
		}
		String dealerOrderId=command.getDealerOrderId();
		//String couponPrice=request.getParameter("couponPrice");
		String useCouponIDs = request.getParameter("useCouponIDs");
		try {
			UserDTO user = getUserBySession(request); // 获取登录用户的信息
			/**************根据订单号查询对应的价格*********************/
			FlightOrderQO flightOrderQO=new FlightOrderQO();
			flightOrderQO.setOrderNO(dealerOrderId);
			flightOrderQO.setOrderType("1");
			flightOrderQO.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));
			FlightOrderDTO flightOrderDTO=jpService.queryOrderDetail(flightOrderQO);
			if((!flightOrderDTO.getJpOrderUser().getUserId().equals(user.getId()))||flightOrderDTO==null){
				return "<script type='text/javascript'>location.href='"+request.getContextPath()+"/jp/worry?dealerOrderId="+dealerOrderId+"'</script>";
			}
			Double payAmount=flightOrderDTO.getFlightPriceInfo().getPayAmount();
			Double couponBalance=0.00;//总支付卡券的价格
			Double avgCashPay=0.00;//平均每个人现金支付价格
			//如果使用账户余额
			if(command.getAccountBalance()>0){//占用账户余额快照
				updateAccount(user.getId(),dealerOrderId);
				consumptionAccount(user.getId(), command.getAccountBalance(), flightOrderDTO.getOrderNO(), AccountConsumeSnapshot.STATUS_ZY, false);
			}
			//得到卡券ID
			if (StringUtils.isNotBlank(useCouponIDs)) {
				couponBalance = couponService.queryTotalPrice(useCouponIDs,flightOrderDTO.getFlightPriceInfo().getPayAmount().intValue());//卡券金额
			}
			double payPrice=Double.parseDouble(String.format("%.2f", (payAmount * constant - couponBalance * constant - command.getAccountBalance() * constant) / constant));
			if(payPrice>0){
				avgCashPay=Double.parseDouble(String.format("%.2f", payPrice/flightOrderDTO.getPassengers().size()));
			}
			// 保存用户选择的卡券,生成一个订单快照
			String[] ids = useCouponIDs.split(",");
			if(StringUtils.isNotBlank(useCouponIDs)){
				BatchConsumeCouponCommand consumeCouponCommand = null;
				consumeCouponCommand = new BatchConsumeCouponCommand();
				consumeCouponCommand.setCouponIds(ids);
				consumeCouponCommand.setOrderId(dealerOrderId);
				consumeCouponCommand.setPayPrice(payAmount);
				consumeCouponCommand.setOrderType(ConsumeOrderSnapshot.ORDERTYPE_JP);
				consumeCouponCommand.setUserID(user.getId());
				HgLogger.getInstance().info("zhaows", "卡券id-->payOrder"+ids);
				couponService.occupyCoupon(consumeCouponCommand);
			}
			UpdateJPOrderStatusCommand updateJPOrderStatusCommands=new UpdateJPOrderStatusCommand();
			updateJPOrderStatusCommands.setDealerOrderCode(command.getDealerOrderId());
			updateJPOrderStatusCommands.setPayCash(avgCashPay);//设置平均每个人现金支付价格
			if (command.getAccountBalance() < 0.00){
				updateJPOrderStatusCommands.setPayBalance(0.00);//设置余额支付价格
			}else{
				updateJPOrderStatusCommands.setPayBalance((double)(int)(command.getAccountBalance()/flightOrderDTO.getPassengers().size()));//设置余额支付价格
			}
			HgLogger.getInstance().info("zhaows", "支付平台生成订单,修改实际支付金额参数" + JSON.toJSONString(updateJPOrderStatusCommands));
			jpService.updateOrderStatus(updateJPOrderStatusCommands);// 修改入实际支付金额
			if(payPrice>0){
				/*double bdOne= (command.getPayPrice()*constant-Double.parseDouble(couponPrice)*constant)/constant;
				double bdTwo= (flightOrderDTO.getFlightPriceInfo().getPayAmount()*constant-Double.parseDouble(couponPrice)*constant)/constant;
				HgLogger.getInstance().info("zhaows", "支付平台生成订单,价格对比："+(bdOne*constant -bdTwo*constant)/constant);
				if((bdOne*constant -bdTwo*constant)/constant !=0||flightOrderDTO.getPayStatus()!=Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY)){
					return "<script type='text/javascript'>location.href='"+request.getContextPath()+"/jp/worry?dealerOrderId="+dealerOrderId+"'</script>";
				}*/
				// 创建一个支付command
				CreateAlipayPayOrderCommand alipayPayOrder = new CreateAlipayPayOrderCommand();
				String messageUrl = "";// 消息地址
				String startCity = request.getParameter("startCity");// 获取起飞城市
				String endCity = request.getParameter("endCity");// 获取到达城市
				//商城在支付平台编号
				String   clientId = SysProperties.getInstance().get("paymentClientId") == null ? "33f5145719314364b0924158d2a1c610": SysProperties.getInstance().get("paymentClientId");
				//商城在支付平台密钥
				String   clientKeys = SysProperties.getInstance().get("paymentClienKeys") == null ? "e10adc3949ba59abbe56e057f20f883e": SysProperties.getInstance().get("paymentClienKeys");
				//支付渠道 支付宝支付为1
				Integer payType = Integer.parseInt("1");
				// 创建一个支付请求dto
				PayOrderRequestDTO payOrderRequestDto = new PayOrderRequestDTO();
				payOrderRequestDto.setClientTradeNo(command.getDealerOrderId());// 生成订单后返回的商城订单号
				payOrderRequestDto.setPayerClientUserId(user.getId());// 登录用户的id
				payOrderRequestDto.setName(user.getBaseInfo().getName());// 登录用户的真实姓名
				payOrderRequestDto.setIdCardNo(user.getBaseInfo().getIdCardNo());// 登录用户的身份证号
				payOrderRequestDto.setMobile(user.getMobile());// 登录用户的手机号
				HgLogger.getInstance().info("zhaows", "支付平台生成订单,支付宝"+payPrice);
				payOrderRequestDto.setAmount(payPrice);// 订单的现金支付总价
				payOrderRequestDto.setPaymentClientId(clientId);
				payOrderRequestDto.setPaymentClienKeys(clientKeys);
				payOrderRequestDto.setPayChannelType(payType);
				// 收款方
				payOrderRequestDto.setPayeeAccount(SysProperties.getInstance().get("payeeAccount")==null?AlipayConfig.email:SysProperties.getInstance().get("payeeAccount"));
				payOrderRequestDto.setPayeeName(SysProperties.getInstance().get("payeeName")==null?"票量科技":SysProperties.getInstance().get("payeeName"));
				payOrderRequestDto.setPayerRemark(SysProperties.getInstance().get("payerRemark")==null?"":SysProperties.getInstance().get("payerRemark"));
				// 创建支付订单
				alipayPayOrder.setPayOrderCreateDTO(payOrderRequestDto);
				// 收款方
				alipayPayOrder.setPartner(SysProperties.getInstance().get("partner")==null?AlipayConfig.partner:SysProperties.getInstance().get("partner"));
				alipayPayOrder.setKeys(SysProperties.getInstance().get("key")==null?AlipayConfig.key:SysProperties.getInstance().get("key"));
				alipayPayOrder.setSubject("飞机票[" + startCity + "-" + endCity + "]*1");
				alipayPayOrder.setBody(SysProperties.getInstance().get("body") == null ? "": SysProperties.getInstance().get("body"));
				alipayPayOrder.setShowUrl(SysProperties.getInstance().get("showUrl") == null ? "": SysProperties.getInstance().get("showUrl"));
				String context  =  JSON.toJSONString(alipayPayOrder);
				HgLogger.getInstance().info("zhaows", "支付平台生成订单,请求CreateAlipayPayOrderCommand参数："+context);
				// 支付平台生成订单，并返回支付宝请求地址
				messageUrl = payOrderSpiService.pay(clientId, clientKeys, payType, context);
				HgLogger.getInstance().info("zhaows", "支付平台生成订单，并返回支付宝请求地址："+messageUrl);
				command.setPayPrice(payPrice);
				return messageUrl;
			}else{
				try {
					//如果使用账户余额
					if(command.getAccountBalance()>0){
						consumptionAccount(user.getId(),command.getAccountBalance(),flightOrderDTO.getOrderNO(),AccountConsumeSnapshot.STATUS_SY,true);
					}
					// 修改卡券的状态
					if(useCouponIDs!=""){
						BatchConsumeCouponCommand couponCommand = new BatchConsumeCouponCommand();
						couponCommand.setCouponIds(ids);
						couponCommand.setOrderId(dealerOrderId);
						couponCommand.setPayPrice(payAmount);
						couponService.confirmConsumeCoupon(couponCommand);
					}
					//修改订单状态
					updateOrderStatus(JPOrderStatusConstant.SHOP_TICKET_DEALING,JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC,dealerOrderId,"","");
				} catch (Exception e) {
					e.printStackTrace();
					HgLogger.getInstance().info("zhaows","JPController>>createClientNotify>余额支付失败"+HgLogger.getStackTrace(e));
					return "<script type='text/javascript'>location.href='"+request.getContextPath()+"/jp/worry?dealerOrderId="+dealerOrderId+"'</script>";
				}
				try {
					BookGNFlightCommand bookGNFlightCommand=new BookGNFlightCommand();
					bookGNFlightCommand.setOrderNO(dealerOrderId);
					JPBookOrderGNDTO jPBookOrderGNDTO=jpService.orderCreate(bookGNFlightCommand);//创建分销订单
					// 支付成功后发送出票请求
					JPPayOrderGNCommand jPPayOrderGNCommand = new JPPayOrderGNCommand();
					jPPayOrderGNCommand.setDealerOrderId(command.getDealerOrderId());
					jPPayOrderGNCommand.setPayPlatform(jPBookOrderGNDTO.getPriceGNDTO().getPayType());
					jPPayOrderGNCommand.setTotalPrice(jPBookOrderGNDTO.getPriceGNDTO().getTotalPrice());//给分销传分销支付价格
					HgLogger.getInstance().info("zhaows", "JPController>>请求出票：" + JSON.toJSONString(jPPayOrderGNCommand));
					jpService.askOrderTicket(jPPayOrderGNCommand);// 请求出票
					// 发送短信通知下单人,商旅分销已经有调用过发送短信的方法接口了			
					try {
						//查询一条订单记录
						flightOrderQO.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING));
						flightOrderQO.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));
						FlightOrderDTO jpOrder=jpService.queryUnique(flightOrderQO);
						HgLogger.getInstance().info("zhaows", "机票支付--支付平台--卡券支付-->机票订单支付成功发送短信时查询得到的机票DTO"+JSON.toJSONString(jpOrder));

						String mobile = jpOrder.getJpLinkInfo().getLinkTelephone();//获取联系人手机号
						String smsContent = ("【"+SysProperties.getInstance().get("sms_sign")+"】您的订单"+ dealerOrderId+ "，已经购买成功，出票完成后将短信通知，请您放心安排行程。客服电话：010—65912283。");
						smsUtils.sendSms(mobile, smsContent);
						HgLogger.getInstance().info("zhaows", "机票支付--支付平台--卡券支付-->:" + mobile + " smsContent:" + smsContent);
					} catch (Exception e) {
						HgLogger.getInstance().error("zhaows","机票支付--支付平台--卡券支付-->用户付款成功短信发送异常"+ HgLogger.getStackTrace(e));
					}

				} catch (Exception e) {
					e.printStackTrace();
					HgLogger.getInstance().info("zhaows","JPController>>createClientNotify--出票失败"+ HgLogger.getStackTrace(e));
					//修改订单状态
					updateOrderStatus(JPOrderStatusConstant.SHOP_TICKET_FAIL,JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT,dealerOrderId,"","");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","JpController->payOrder->exception:"+ HgLogger.getStackTrace(e));
			return "<script type='text/javascript'>location.href='"+request.getContextPath()+"/jp/worry?dealerOrderId="+dealerOrderId+"'</script>";
		}

		return "<script type='text/javascript'>location.href='"+request.getContextPath()+"/jp/ssucce?dealerOrderId="+dealerOrderId+"'</script>";
	}

	/**
	 * 跳转错误页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/error")
	public String error(Model model, HttpServletRequest request) {
		return "ticket/order_error.html";
	}

	/**
	 * 跳转错误页面，用来限定一趟航班一个身份证号在当天只能预订一次
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/fail")
	public String fail(Model model, HttpServletRequest request) {
		return "ticket/order_fail.html";
	}

	/**
	 * 跳转错误页面,支付失败页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/worry")
	public String payFail(Model model, HttpServletRequest request) {
		return "ticket/worrys.html";
	}
	/**
	 * 跳转错误页面,支付成功页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ssucce")
	public String payssucce(Model model, HttpServletRequest request) {
		return "ticket/ssucce.html";
	}
	/**
	 * 通知PC端的方法，获取到订单支付状态情况（支付平台异步回调方法）
	 * @param request
	 * */
	@RequestMapping(value = "/getclient")
	@ResponseBody
	public void createClientNotify(HttpServletRequest request,HttpServletResponse response) {
		String orderNo = request.getParameter("orderNo");// 获取商城订单编号
		PayOrderQO payorderQO = new PayOrderQO();
		payorderQO.setOrderNo(orderNo);// 商城订单编号
		payorderQO.setPaymentClientID(SysProperties.getInstance().get("paymentClientId") == null ? "33f5145719314364b0924158d2a1c610": SysProperties.getInstance().get("paymentClientId"));// 客户端id
		payorderQO.setPaymentClienKeys(SysProperties.getInstance().get("paymentClienKeys") == null ? "e10adc3949ba59abbe56e057f20f883e" : SysProperties.getInstance().get("paymentClienKeys"));// 客户端密钥
		payorderQO.setPaySuccess(true);
		// 根据商城订单号，查询一条订单记录
		FlightOrderQO flightOrderQO = new FlightOrderQO();
		flightOrderQO.setOrderNO(orderNo);
		flightOrderQO.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));
		FlightOrderDTO flightOrderDTO = jpService.queryUnique(flightOrderQO);
		HgLogger.getInstance().info("zhaows", "机票支付--支付平台--异步通知-->根据订单号获取一条订单记录：" + JSON.toJSONString(flightOrderDTO));
		try {
			HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->查询支付平台订单状态,查询的QO："+ JSON.toJSONString(payorderQO));
			// 商城查询订单是否支付成功
			List<PayOrderDTO>  payOrderList  = payOrderSpiService.queryPayOrderList (payorderQO);
			HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->查询支付平台订单状态,查询得到的结果集 ：" + payOrderList.size());
			Integer paySuccess=1;//默认支付状态支付失败，如果查询出订单，则赋值为1
			String buyer_email="";
			String trade_no="";
			if(payOrderList.size()>0){
				paySuccess = payOrderList.get(0).getPayStatus();// 取得订单状态
				buyer_email = payOrderList.get(0).getBuyer_email();// 取得支付人的支付宝帐号
				trade_no = payOrderList.get(0).getTrade_no();// 取得支付宝订单号
			}
			// 通过调用payOrderSpiService接口得到订单状态，若成功支付则更改入库订单的状态
			if (paySuccess == 1) {
				if (flightOrderDTO == null) {
					HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->该订单号对应订单的订单状态已经为支付成功状态，该订单号："+ orderNo);
					return;
				} else {
					// 如果支付成功，则同时修改与该订单相关的卡券的状态为已消费状态
					HslCouponQO hslCouponQO = new HslCouponQO();
					hslCouponQO.setOrderId(orderNo);
					// 根据订单号查询一个与订单绑定的卡券快照
					List<CouponDTO> coupondtos = couponService.queryList(hslCouponQO);
					HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->根据订单号查询一个与订单绑定使用的卡券"+ JSON.toJSONString(coupondtos));
					if(coupondtos!=null&&coupondtos.size()>0){
						List<String> ids=new ArrayList<String>();
						for (CouponDTO coupondto : coupondtos) {
							if(coupondto!=null){
								ids.add(coupondto.getId());
							}
						}
						// 修改卡券的状态
						BatchConsumeCouponCommand couponCommand = new BatchConsumeCouponCommand();
						couponCommand.setCouponIds(ids.toArray(new String[ids.size()]));
						couponCommand.setOrderId(flightOrderDTO.getId());
						couponCommand.setPayPrice(flightOrderDTO.getFlightPriceInfo().getPayAmount());
						HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->机票出票后确认使用卡券command："+JSON.toJSONString(couponCommand));
						try {
							couponService.confirmConsumeCoupon(couponCommand);
						} catch (CouponException e) {
							e.printStackTrace();
							HgLogger.getInstance().error("zhaows","机票支付--支付平台--异步通知-->机票出票后确认使用卡券出现异常："+ e.getMessage()+ HgLogger.getStackTrace(e));
						}
					}
					AccountConsumeSnapshotQO qo=new AccountConsumeSnapshotQO();
					qo.setOrderId(flightOrderDTO.getOrderNO());//根据订单id查询余额订单快照
					HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知--账户余额>参数"+JSON.toJSONString(qo));
					AccountConsumeSnapshotDTO accDTO=accountConsumeSnapshotService.queryUnique(qo);
					HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知--账户余额>返回DTO"+JSON.toJSONString(accDTO));
					//如果使用账户余额
					if(accDTO!=null&&accDTO.getPayPrice()>0){//占用账户余额快照
						consumptionAccount(accDTO.getAccount().getUser().getId(),accDTO.getPayPrice(),flightOrderDTO.getOrderNO(),AccountConsumeSnapshot.STATUS_SY,true);
					}


					//创建分销订单
					BookGNFlightCommand bookGNFlightCommand=new BookGNFlightCommand();
					bookGNFlightCommand.setOrderNO(orderNo);
					JPBookOrderGNDTO jPBookOrderGNDTO=null;
					try {
						jPBookOrderGNDTO=jpService.orderCreate( bookGNFlightCommand);//创建分销订单jpService.orderCreate(bookGNFlightCommand);
						updateOrderStatus(JPOrderStatusConstant.SHOP_TICKET_DEALING,JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC,orderNo,buyer_email,trade_no);
					} catch (Exception e) {
						e.printStackTrace();
						//修改订单状态
						updateOrderStatus(JPOrderStatusConstant.SHOP_TICKET_FAIL,JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT,orderNo,buyer_email,trade_no);
						HgLogger.getInstance().error("zhaows","getclient:分销创建订单失败"+ e.getMessage() + HgLogger.getStackTrace(e));
					}
					JPPayOrderGNCommand jPPayOrderGNCommand = new JPPayOrderGNCommand();
					jPPayOrderGNCommand.setDealerOrderId(orderNo);
					jPPayOrderGNCommand.setPayPrice(flightOrderDTO.getFlightPriceInfo().getPayAmount());//支付总价格
					jPPayOrderGNCommand.setBuyerEmail(buyer_email);//支付帐号
					jPPayOrderGNCommand.setPayTradeNo(trade_no);//支付订单号
					// 易行支持的支付方式
					if(jPBookOrderGNDTO!=null) {
						jPPayOrderGNCommand.setPayPlatform(jPBookOrderGNDTO.getPriceGNDTO().getPayType());
						jPPayOrderGNCommand.setTotalPrice(jPBookOrderGNDTO.getPriceGNDTO().getTotalPrice());//给分销传分销支付价格
						HgLogger.getInstance().info("zhaows", "机票支付--支付平台--异步通知-->发送出票请求：" + JSON.toJSONString(jPPayOrderGNCommand));
						jpService.askOrderTicket(jPPayOrderGNCommand);// 请求出票
					}
					// 发放卡券：订单满
					CreateCouponCommand cmd = new CreateCouponCommand();
					cmd.setSourceDetail("订单满送");
					cmd.setPayPrice(flightOrderDTO.getFlightPriceInfo().getPayAmount());
					cmd.setMobile(flightOrderDTO.getJpOrderUser().getMobile());
					cmd.setUserId(flightOrderDTO.getJpOrderUser().getUserId());
					cmd.setLoginName(flightOrderDTO.getJpOrderUser().getLoginName());
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
					template.convertAndSend("zzpl.order", baseAmqpMessage);
					/**-------通知支付记录平台，创建该订单的支付记录--Start--*/
					/*String httpUrl = StringUtils.isNotBlank(SysProperties.getInstance().get("payRecord_httpUrl"))?SysProperties.getInstance().get("payRecord_httpUrl"):"http://127.0.0.1:8080/pay-record-api/api";
					String modulus = StringUtils.isNotBlank(SysProperties.getInstance().get("payRecord_modulus"))?SysProperties.getInstance().get("payRecord_modulus"):
										"124772110688646963986080649555964103539422188358187828168937575305950354245761868431506632387775213795975959903801685687213725513485979867335122668713621985468566123166884763277431607358336804344573792493151032125487645367798247528849276077786351681288665334101843452187400145922866688656369859774560299619129";
					String public_exponent = StringUtils.isNotBlank(SysProperties.getInstance().get("payRecord_public_exponent"))?SysProperties.getInstance().get("payRecord_public_exponent"):
											"65537";
					PayRecordApiClient client;
					try {
						client = new PayRecordApiClient(httpUrl, modulus, public_exponent);
						CreateAirPayReocrdCommand command = new CreateAirPayReocrdCommand();
						command.setFromProjectCode(1000);
						command.setPayPlatform(1);
						command.setRecordType(1);
						StringBuffer names=new StringBuffer();
						for(PassengerGNDTO passengerGNDTO:flightOrderDTO.getPassengers()){
							if(StringUtils.isNotBlank(names.toString())){
								names.append("|"+passengerGNDTO.getPassengerName());
							}else{
								names.append(passengerGNDTO.getPassengerName());
							}
						}
						command.setBooker(names.toString());
						command.setDealerOrderNo(flightOrderDTO.getOrderNO());
						command.setPaySerialNumber(flightOrderDTO.getPayTradeNo());
						command.setStartCity(flightOrderDTO.getFlightBaseInfo().getOrgCity());
						command.setDestCity(flightOrderDTO.getFlightBaseInfo().getDstCity());
						command.setPayAccountNo(flightOrderDTO.getBuyerEmail());
						command.setIncomeMoney(flightOrderDTO.getFlightPriceInfo().getPayCash()*flightOrderDTO.getPassengers().size());
						command.setFromClientType(1);
						CreateAirPayRecordResponse recordResponse = client.send(command, CreateAirPayRecordResponse.class);
						HgLogger.getInstance().info("zhaows", "机票--支付平台--异步通知-->:recordResponse" + JSON.toJSONString(recordResponse));
					}catch (Exception e){
						e.printStackTrace();
						HgLogger.getInstance().info("chenxy","记录支付记录出现问题-->"+HgLogger.getStackTrace(e));
					}*/
					/**-------通知支付记录平台，创建该订单的支付记录--End--*/
					// 发送短信通知下单人,商旅分销已经有调用过发送短信的方法接口了			
					try {
						//查询一条订单记录
						flightOrderQO.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING));
						flightOrderQO.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));
						FlightOrderDTO jpOrder=jpService.queryUnique(flightOrderQO);
						HgLogger.getInstance().info("zhaows", "机票支付--支付平台--异步通知-->机票订单支付成功发送短信时查询得到的机票DTO"+JSON.toJSONString(jpOrder));

						String mobile = jpOrder.getJpLinkInfo().getLinkTelephone();//获取联系人手机号
						String smsContent = ("【"+SysProperties.getInstance().get("sms_sign")+"】您的订单"+ orderNo+ "，已经购买成功，出票完成后将短信通知，请您放心安排行程。客服电话：010—65912283。");
						smsUtils.sendSms(mobile, smsContent);
						HgLogger.getInstance().info("zhaows", "机票支付--支付平台--异步通知-->:" + mobile + " smsContent:" + smsContent);
					} catch (Exception e) {
						HgLogger.getInstance().error("zhaows","机票支付--支付平台--异步通知-->用户付款成功短信发送异常"+ HgLogger.getStackTrace(e));
					}
				}
			} else {
				HgLogger.getInstance().info("zhaows", "机票支付--支付平台--异步通知-->该订单号对应订单的订单状态已经为支付失败状态，该订单号：" + orderNo);
				// 如果支付失败，则需要删除订单快照
				try {
					/********************删除卡券中的与之相关联的订单号成功*****************************/
					OrderRefundCommand command = new OrderRefundCommand();
					command.setOrderId(orderNo);
					couponService.orderRefund(command);
				} catch (CouponException e) {
					e.printStackTrace();
					HgLogger.getInstance().error("zhaows","机票支付--支付平台--异步通知-->删除订单快照失败"+ e.getMessage()+ HgLogger.getStackTrace(e));
				}
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows", "机票支付--支付平台--异步通知-->向支付平台查询订单失败:" + e.getMessage() + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/**
	 * 重定向地址跳转成功页面（支付平台同步回调方法）
	 * @param model
	 * @param request
	 * */
	@RequestMapping(value = "/getOrderStatus")
	public String getOrderStatus(HttpServletRequest request, Model model) {
		String clientNo = request.getParameter("orderNo");// 获取订单号
		//String clientNo = "B609144937000001";
		PayOrderQO payorderQO = new PayOrderQO();
		payorderQO.setOrderNo(clientNo);// 商城订单编号
		payorderQO.setPaymentClientID(SysProperties.getInstance().get("paymentClientId") == null ? "33f5145719314364b0924158d2a1c610" : SysProperties.getInstance().get("paymentClientId"));// 客户端id
		payorderQO.setPaymentClienKeys(SysProperties.getInstance().get("paymentClienKeys") == null ? "e10adc3949ba59abbe56e057f20f883e" : SysProperties.getInstance().get("paymentClienKeys"));// 客户端密钥
		payorderQO.setPaySuccess(true);
		HgLogger.getInstance().info("chenxy", "机票支付--支付平台--同步通知-->查询支付平台订单状态,查询的QO：" + JSON.toJSONString(payorderQO));
		// 商城查询订单是否支付成功
		List<PayOrderDTO> payOrderList = null;
		try {
			payOrderList = payOrderSpiService.queryPayOrderList (payorderQO);
			HgLogger.getInstance().info("chenxy","机票支付--支付平台--同步通知-->查询得到的结果集 ：" + payOrderList.size());
			FlightOrderQO flightOrderQO=new FlightOrderQO();
			flightOrderQO.setOrderNO(clientNo);
			flightOrderQO.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));
			List<FlightOrderDTO> flightOrderDTOList=jpService.queryOrder(flightOrderQO);
			// 查询判断多种状态(已支付)
			HgLogger.getInstance().info("chenxy","机票支付--支付平台--同步通知-->本地根据订单状态查询记录：" + flightOrderDTOList.size());
			// 支付成功页面读取相关信息
			model.addAttribute("clientNo", clientNo);
			model.addAttribute("jporderList", flightOrderDTOList);
			// 判断是否支付成功
			if (flightOrderDTOList.size() > 0||payOrderList.size()>0) {
				// 返回支付成功页面
				return "ticket/ssucce.html";
			} else {
				// 返回支付失败页面
				return "ticket/worry.html";
			}
		} catch (PaymentException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "机票支付--支付平台--同步通知-->异常错误" + HgLogger.getStackTrace(e));
			return "ticket/error.jsp";
		}

	}

	/**
	 * 获得指定日期的后一天
	 * @param specifiedDay
	 * @return
	 */
	private static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","获取指定日期的后一天日期错误:" + HgLogger.getStackTrace(e));
		}
		if(date!=null) {
			calendar.setTime(date);
		}
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day + 1);
		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		return dayAfter; // 返回后一天日期
	}

	/**
	 * 获得指定日期的前一天
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	private static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","获取指定日期的前一天失败:" + HgLogger.getStackTrace(e));
		}
		if(date!=null) {
			calendar.setTime(date);
		}
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day - 1);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		return dayBefore; // 返回前一天日期
	}

	/**
	 * 获取所有的公司
	 * @param request
	 */
	@RequestMapping("/companys")
	@ResponseBody
	private String getCompany(HttpServletRequest request) {
		UserDTO user = getUserBySession(request);
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		hslCompanyQO.setUserId(user.getId());// 这里替换成用户的id，公司查询条件
		List<CompanyDTO> companyList = companyService.getCompanys(hslCompanyQO);// 获得所有的公司列表
		HgLogger.getInstance().info("zhaows", "查询所有的公司集合：" + JSON.toJSONString(companyList));
		return JSON.toJSONString(companyList);
	}

	/**
	 * 根据公司Id获取部门
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/getDepartments")
	@ResponseBody
	private String getDepartments(@RequestParam("companyId") String companyId) {
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		List<DepartmentDTO> departmentList =new ArrayList<DepartmentDTO>();
		if(StringUtils.isNotBlank(companyId)){
			HslCompanyQO hslCompanyQO = new HslCompanyQO();
			hslCompanyQO.setId(companyId);
			hslDepartmentQO.setCompanyQO(hslCompanyQO);
			departmentList = companyService.getDepartments(hslDepartmentQO);// 获取指定公司的所有部门列表
		}
		HgLogger.getInstance().info("zhaows","根据所选公司的ID查询所有的部门集合：" + JSON.toJSONString(departmentList));
		return JSON.toJSONString(departmentList);
	}
	/**
	 * 根据部门Id获取员工
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/getMembers")
	@ResponseBody
	private String getMembers(@RequestParam("deptId") String deptId) {
		HslMemberQO hslMemberQO = new HslMemberQO();
		List<MemberDTO> memberList =new ArrayList<MemberDTO>();
		if(StringUtils.isNotBlank(deptId)){
			hslMemberQO.setDepartmentId(deptId);
			memberList = companyService.getMembers(hslMemberQO);// 获取指定部门的所有员工列表
		}
		HgLogger.getInstance().info("zhaows","根据所选部门的ID查询所有的员工集合：" + JSON.toJSONString(memberList));
		return JSON.toJSONString(memberList);
	}

	/**
	 * 根据员工Id获取员工详细信息
	 *
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/getMembersInfomation")
	@ResponseBody
	private String getMembersInfomation(@RequestParam("memberId") String memberId) {
		HslMemberQO hslMemberQO = new HslMemberQO();
		List<MemberDTO> memberInfoList=new ArrayList<MemberDTO>();
		if(StringUtils.isNotBlank(memberId)){
			hslMemberQO.setId(memberId);
			memberInfoList = companyService.getMembers(hslMemberQO);// 获取指定员工的详细信息
		}
		HgLogger.getInstance().info("zhaows","根据所选员工的ID查询员工的详细信息：" + JSON.toJSONString(memberInfoList));
		return JSON.toJSONString(memberInfoList);
	}

	/**
	 * 获取用户的登录名
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLoginName")
	@ResponseBody
	public String getLoginName(HttpServletRequest request) {
		// 获取user
		UserDTO user = getUserBySession(request);
		UserAuthInfoDTO userAuthDto = new UserAuthInfoDTO();
		// 设置登录用户名，便于前端获取
		userAuthDto.setLoginName(user.getAuthInfo().getLoginName());
		return JSON.toJSONString(userAuthDto);
	}

	/**
	 * 获取卡券的使用条件以及获得获取卡券所属的活动id
	 * @param request
	 * */
	@RequestMapping(value = "/getCouponCondition")
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
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","获取卡券记录失败:" + HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(couponDto);
	}



	/**
	 * 时间比较大小
	 * @param str1
	 * @param str2
	 * @param str3
	 * */
	public Boolean compareToTime(String str1, String str2, String str3)throws ParseException {
		boolean isExists;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		// 定义区间值
		Date dateBefor = df.parse(str1);
		Date dateAfter = df.parse(str2);
		Date time = df.parse(str3);
		// 将你输入的String 数据转化为Date
		//Date time = df.parse(str3);
		// 判断time是否在XX之后，并且 在XX之前
		if (time.getTime()>=dateBefor.getTime() && time.getTime()<dateAfter.getTime()) {
			isExists = true;
		} else {
			isExists = false;
		}
		return isExists;
	}

	/**
	 * 获取浏览记录
	 *
	 * @param model
	 * @param userId
	 * */
	private void getRecord(String userId, final Model model,HttpServletRequest request) {
		HgLogger.getInstance().debug("zhaows", "景点列表,浏览记录查询");
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
	}

	/**
	 * 获取热门景点数据
	 * @param model
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getHotMpScenicSpots(Model model) {
		// 获取热门景点
		HslMPScenicSpotQO hotMpScenicSpotsQO = new HslMPScenicSpotQO();
		// 热门景点数据
		Map hotScenicMap;
		hotMpScenicSpotsQO.setHot(true);
		hotMpScenicSpotsQO.setPageNo(1);
		hotMpScenicSpotsQO.setPageSize(6);
		hotMpScenicSpotsQO.setIsOpen(true);
		try {
			hotScenicMap = scenicSpotService.queryScenicSpot(hotMpScenicSpotsQO);
			HgLogger.getInstance().info("zhaows","热门景点数据，查询数据：" + JSON.toJSONString(hotScenicMap));
			model.addAttribute("hotScenicMap", hotScenicMap);
		} catch (MPException e) {
			hotScenicMap = new HashMap();
			hotScenicMap.put("dto", new ArrayList<HotScenicSpotDTO>());
			hotScenicMap.put("count", 0);
			model.addAttribute("hotScenicMap", hotScenicMap);
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","JpController->main->exception:"+ HgLogger.getStackTrace(e));
		}
	}
	/**
	 * @方法功能说明：查询订单详情
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-26下午1:45:57
	 * @参数：@param model
	 * @return:void
	 * @throws
	 */
	@RequestMapping("/getOrderDetails")
	@ResponseBody
	public String  getOrderDetails(Model model,HttpServletRequest request) {
		FlightOrderDTO flightOrderDTO=null;
		try {
			String orderid=request.getParameter("dealerOrderId");
			FlightOrderQO flightOrderQO=new FlightOrderQO();
			flightOrderQO.setOrderNO(orderid);
			flightOrderDTO = jpService.queryOrderDetail(flightOrderQO);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows", "getOrderDetails->机票订单查询失败" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return JSON.toJSONString(flightOrderDTO);
	}
	/**
	 *
	 * @方法功能说明：消费账户余额
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-7下午1:43:27
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void consumptionAccount(String userID,double accountBalance,String orderNo,int stutas,boolean consumption) throws AccountException {
		HgLogger.getInstance().info("zhaows","jP--->consumptionAccount=" + userID+"="+accountBalance+"="+orderNo+"="+stutas);
		AccountCommand accountCommand=new AccountCommand();
		accountCommand.setCommandId(userID);//余额账户ID
		accountCommand.setCurrentMoney(accountBalance);//本次消费金额
		accountCommand.setConsumption(consumption);
		AccountConsumeSnapshotCommand snapshotCommand=new AccountConsumeSnapshotCommand();
		snapshotCommand.setOrderType(AccountConsumeSnapshot.ORDERTYPE_JP);
		snapshotCommand.setOrderId(orderNo);
		snapshotCommand.setStatus(stutas);
		snapshotCommand.setPayPrice(accountBalance);
		//snapshotCommand.setDetail("暂无详细");
		accountCommand.setAccountConsumeSnapshotCommand(snapshotCommand);
		accountService.consumptionAccount(accountCommand);
	}
	/**
	 *
	 * @方法功能说明：修改订单状态
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-24下午2:57:13
	 * @参数：@param status
	 * @参数：@param payStatus
	 * @参数：@param dealerOrderId
	 * @return:void
	 * @throws
	 */
	public void updateOrderStatus(String status,String payStatus,String dealerOrderId,String buyer_email,String trade_no){
		HgLogger.getInstance().info("zhaows","updateOrderStatus--修改订单状态=" + status+"="+payStatus+"="+dealerOrderId+"="+buyer_email+"="+trade_no);
		try {
			UpdateJPOrderStatusCommand updateJPOrderStatusCommand=new UpdateJPOrderStatusCommand();
			updateJPOrderStatusCommand.setBuyerEmail(buyer_email);//支付帐号
			updateJPOrderStatusCommand.setPayTradeNo(trade_no);//支付订单号
			updateJPOrderStatusCommand.setDealerOrderCode(dealerOrderId);
			updateJPOrderStatusCommand.setStatus(Integer.parseInt(status));
			updateJPOrderStatusCommand.setPayStatus(Integer.parseInt(payStatus));
			//updateJPOrderStatusCommand.setPayCash(0.00);//设置现金支付价格
			HgLogger.getInstance().info("zhaows","JPController>>createClientNotify>修改订单状态"+ JSON.toJSONString(updateJPOrderStatusCommand));
			jpService.updateOrderStatus(updateJPOrderStatusCommand);// 修改入库订单状态和订单的支付状态
			HgLogger.getInstance().info("zhaows", "JPController>>createClientNotify>入库订单的订单号：" + dealerOrderId);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","updateOrderStatus---修改订单状态失败");
		}
	}
	/**
	 * 修改占用状态的账户余额
	 * @param userId
	 * @param OrderNo
	 */
	public  void updateAccount(String userId,String OrderNo){
		AccountConsumeSnapshotQO qo = new AccountConsumeSnapshotQO();
		AccountQO accountQO = new AccountQO();
		accountQO.setUserID(userId);
		AccountDTO dto = accountService.queryUnique(accountQO);
		if(dto!=null){
			qo.setAccountId(dto.getId());
			qo.setAccountstatus(AccountConsumeSnapshot.STATUS_ZY);
			qo.setOrderId(OrderNo);
			HgLogger.getInstance().info("zhaows", "支付平台生成订单查询是否有消费快照QO--pay" + JSON.toJSONString(qo));
			List<AccountConsumeSnapshotDTO> consumeSnapshotList = accountConsumeSnapshotService.queryList(qo);
			HgLogger.getInstance().info("zhaows", "支付平台生成订单查询是否有消费快照实体--pay" + consumeSnapshotList.size());
			String ids="";
			for (AccountConsumeSnapshotDTO consumeSnapshot : consumeSnapshotList) {
				ids+=consumeSnapshot.getId()+",";
			}
			accountConsumeSnapshotService.update(ids);
		}
	}
}
