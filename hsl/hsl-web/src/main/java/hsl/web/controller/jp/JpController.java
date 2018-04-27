package hsl.web.controller.jp;

import com.alibaba.fastjson.JSON;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;
import hg.payment.pojo.command.dto.PayOrderRequestDTO;
import hg.payment.pojo.command.spi.payorder.alipay.CreateAlipayPayOrderCommand;
import hg.payment.pojo.dto.payorder.PayOrderDTO;
import hg.payment.pojo.exception.PaymentException;
import hg.payment.pojo.qo.payorder.PayOrderQO;
import hg.payment.spi.inter.PayOrderSpiService;
import hg.system.model.auth.AuthUser;
import hsl.app.common.util.AppInfoUtils;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.component.config.SysProperties;
import hsl.app.service.local.jp.JPFlightLocalService;
import hsl.app.service.local.jp.JPOrderLocalService;
import hsl.app.service.local.user.UserLocalService;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.jp.JPOrder;
import hsl.domain.model.sys.CityAirCode;
import hsl.pojo.command.JPOrderCreateCommand;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.dto.coupon.ConsumeOrderSnapshotDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.*;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.mp.MPSimpleDTO;
import hsl.pojo.dto.user.UserAuthInfoDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.CouponException;
import hsl.pojo.exception.MPException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.pojo.qo.mp.HslUserClickRecordQO;
import hsl.spi.inter.Coupon.CouponActivityService;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.api.jp.JPService;
import hsl.spi.inter.company.CompanyService;
import hsl.spi.inter.mp.MPScenicSpotService;
import hsl.spi.inter.user.UserService;
import hsl.spi.qo.sys.CityAirCodeQO;
import hsl.web.alipay.config.AlipayConfig;
import hsl.web.controller.BaseController;
import hsl.web.util.JpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import slfx.api.v1.request.command.jp.APIJPOrderCreateCommand;
import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;
import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
import slfx.api.v1.request.qo.jp.JPFlightQO;
import slfx.api.v1.request.qo.jp.JPPolicyQO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @param <payOrderService>
 * @类功能： 机票控制器
 * @作者： liusong
 * @创建时间：2014年9月15日上午
 */
@Controller
@RequestMapping("/jp")
public class JpController<payOrderService> extends BaseController {

	@Resource
	private JPService jpservice;

	@Autowired
	private JPOrderLocalService jpOrderLocalService;

	@Resource
	private JPFlightLocalService jpFlightLocalService;

	@Resource
	private UserLocalService userLocalService;
	@Autowired
	private CouponActivityService couponActivityService;
	//	@Resource
	//	private AlipayPayOrderSpiService alipayPayOrderService;

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

	private Integer constant = 100;

	/**
	 * @throws
	 * @方法功能说明：跳转机票的主页
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月13日下午3:24:24
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 */
//	@RequestMapping("/main")
	public String main(HttpServletRequest request, Model model) {
		UserDTO user = getUserBySession(request);
		String userId = "";
		if (user != null) {
			userId = user.getId();
			HgLogger.getInstance().debug("liusong", "机票航班列表有用户登录：" + user.getAuthInfo().getLoginName());
		} else {
			HgLogger.getInstance().debug("liusong", "机票航班列表没有用户登录");
		}
		// 获取浏览记录
		getRecord(userId, model, request);
		// 获取热门景点数据
		getHotMpScenicSpots(model);
		return "ticket/ticket_list.html";
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @throws
	 * @方法功能说明：航班查询
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月13日下午3:26:39
	 * @修改内容：
	 * @参数：@param jpFlightQO
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:Object
	 */
	@RequestMapping("/ticketList")
	@ResponseBody
	public Object ticketList(JPFlightQO jpFlightQO, HttpServletRequest request, HttpServletResponse response,
							 Model model) throws UnsupportedEncodingException {
		String from = request.getParameter("from");
		if (StringUtils.isNotBlank(from)) {
			from = java.net.URLDecoder.decode(from, "UTF-8");
		}
		String arrive = request.getParameter("to");
		if (StringUtils.isNotBlank(arrive)) {
			arrive = java.net.URLDecoder.decode(arrive, "UTF-8");
		}
		String time = request.getParameter("time");
		String airtime = request.getParameter("clock");
		if (!airtime.equals("undefined")) {
			airtime = java.net.URLDecoder.decode(airtime, "UTF-8");
			if (airtime.equals("起飞时间")) {
				airtime = "";
			}
		}
		String airportname = request.getParameter("company");
		if (!airportname.equals("undefined")) {
			airportname = java.net.URLDecoder.decode(airportname, "UTF-8");
			if (airportname.equals("航空公司")) {
				airportname = "";
			}

		}
		//判断查询QO中的出发城市、到达城市以及出发日期是否为空，如果为空就直接跳转机票主页
		if (from == "" || arrive == "" || time == "") {
			return new RedirectView("/jp/main", true);
		}
		Map<String, List<FlightDTO>> map = new HashMap<String, List<FlightDTO>>();
		List<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();
		List<FlightDTO> flightListByAir = new ArrayList<FlightDTO>();
		try {

			String nextDate = "";// 声明指定日期的后一天变量
			String pvDate = "";// 声明指定日期的前一天变量
			// 查询出发城市以及到达城市的三字码
			String[] cityName = new String[2];
			String[] cityNameCode = new String[2];
			Boolean isCityAirCode = true;// 标识城市是否有三字码，无则不查询
			// 赋值完成后调用jpservice中的queryFlight()进行航班列表查询

			if (from != null && arrive != null && time != null) {
				cityName[0] = from;// 获取出发城市名称
				cityName[1] = arrive;// 获取到达城市名称
				for (int i = 0; i < cityName.length; i++) {
					CityAirCodeQO cityaircodeqo = new CityAirCodeQO();
					cityaircodeqo.setName(cityName[i]);
					CityAirCode cityaircode = new CityAirCode();
					cityaircode = jpFlightLocalService.queryLocalCityAirCode(cityaircodeqo);
					HgLogger.getInstance().info("liusong", "出发到达城市的三字码，查询回传：" + JSON.toJSONString(cityaircode));

					// 判断所选城市是否有对应的三字码
					if (cityaircode != null && !cityaircode.getAirCode().equals("")) {
						cityNameCode[i] = cityaircode.getAirCode();
					} else {
						isCityAirCode = false;
					}
				}
			} else {
				main(request, model);
				isCityAirCode = false;
			}

			// 将转换后的城市三字码赋值到JPFlightQO中进行查询
			if (isCityAirCode) {
				jpFlightQO.setFrom(cityNameCode[0]);
				jpFlightQO.setArrive(cityNameCode[1]);
				jpFlightQO.setDate(time);

				// 获取指定时间的后一天
				nextDate = getSpecifiedDayAfter(time);
				pvDate = getSpecifiedDayBefore(time);

				// 真实航班列表查询
				flightDTOs = jpservice.queryFlight(jpFlightQO);
			} else {
				flightDTOs = null;
			}

			if (flightDTOs != null && flightDTOs.size() > 0) {
				// 计算参考税费(为机建费+燃油费)
				for (int i = 0; i < flightDTOs.size(); i++) {
					Double fuelSurTax = flightDTOs.get(i).getFuelSurTax();// 取得燃油费
					Double airportTax = flightDTOs.get(i).getAirportTax();// 取得机建费
					flightDTOs.get(i).setTaxAmount(airportTax + fuelSurTax);// 设置参考税费
					if (airportname == "" && airtime == "") {
						flightListByAir.add(flightDTOs.get(i));
					} else if (airportname != "" && airtime == "") {
						// 循环查询到的结果，删选出符合条件的结果
						if (flightDTOs.get(i).getAirCompName().equals(airportname)) {
							flightListByAir.add(flightDTOs.get(i));
						}
					} else if (airtime != "" && airportname == "") {
						if (airtime.equals("上午")) {
							String str1 = "06:00";
							String str2 = "12:00";
							String start = flightDTOs.get(i).getStartTime();
							boolean isFlag = compareToTime(str1, str2, start);
							if (isFlag) {
								flightListByAir.add(flightDTOs.get(i));
							}
						} else if (airtime.equals("下午")) {
							String str1 = "12:00";
							String str2 = "18:00";
							String start = flightDTOs.get(i).getStartTime();
							boolean isFlag = compareToTime(str1, str2, start);
							if (isFlag) {
								flightListByAir.add(flightDTOs.get(i));
							}
						} else if (airtime.equals("晚上")) {
							String str1 = "18:00";
							String str2 = "24:00";
							String start = flightDTOs.get(i).getStartTime();
							boolean isFlag = compareToTime(str1, str2, start);
							if (isFlag) {
								flightListByAir.add(flightDTOs.get(i));
							}
						}
					} else if (airportname != "" && airtime != "") {
						if (airtime.equals("上午")) {
							String str1 = "06:00";
							String str2 = "12:00";
							String start = flightDTOs.get(i).getStartTime();
							boolean isFlag = compareToTime(str1, str2, start);
							if (isFlag) {
								if (flightDTOs.get(i).getAirCompName().equals(
										airportname)) {
									flightListByAir.add(flightDTOs.get(i));
								}
							}
						} else if (airtime.equals("下午")) {
							String str1 = "12:00";
							String str2 = "18:00";
							String start = flightDTOs.get(i).getStartTime();
							boolean isFlag = compareToTime(str1, str2, start);
							if (isFlag) {
								if (flightDTOs.get(i).getAirCompName().equals(
										airportname)) {
									flightListByAir.add(flightDTOs.get(i));
								}
							}
						} else if (airtime.equals("晚上")) {
							String str1 = "18:00";
							String str2 = "24:00";
							String start = flightDTOs.get(i).getStartTime();
							boolean isFlag = compareToTime(str1, str2, start);
							if (isFlag) {
								if (flightDTOs.get(i).getAirCompName().equals(
										airportname)) {
									flightListByAir.add(flightDTOs.get(i));
								}
							}
						}
					}
				}
				map.put("list", flightListByAir);
				//model.addAttribute("map", map);
			}

			/**
			 * 获取用户区分是企业用户还是个人用户
			 */
			UserDTO user = getUserBySession(request);
			String userId = "";
			if (user != null) {
				userId = user.getId();// 获取user的ID号
				HgLogger.getInstance().debug("liusong", "机票航班列表有用户登录：" + user.getAuthInfo().getLoginName());
			} else {
				HgLogger.getInstance().debug("liusong", "机票航班列表没有用户登录");
			}
			// 获取浏览记录
			getRecord(userId, model, request);

			// 获取热门景点数据
			getHotMpScenicSpots(model);

			model.addAttribute("from", cityName[0]);
			model.addAttribute("arrive", cityName[1]);
			model.addAttribute("startDate", time);
			model.addAttribute("nextDate", nextDate);
			model.addAttribute("pvDate", pvDate);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("liusong", "JpController->tickList->NullPointException:" + HgLogger.getStackTrace(e));
		}
        /*		FlightDTO flightDTOss=new FlightDTO();
		flightDTOss.setAirCompName("四川航空");
		FlightDTO flightDTOss1=new FlightDTO();
		flightDTOss1.setAirCompName("东方航空");
		flightDTOs.add(flightDTOss);
		flightDTOs.add(flightDTOss1);*/
		//map.put("list", flightDTOs);
		return JSON.toJSONString(map);
	}

	/**
	 * @throws
	 * @方法功能说明：航班列表机票点击预订并跳转到填写订单页面
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月13日下午3:27:42
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param policyqo
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 */
	@RequestMapping("/price")
	public String jpPrice(Model model, JPPolicyQO policyqo, HttpServletRequest request) {
		// 拿到user的类型，确定是个人用户还是企业用户
		UserDTO user = getUserBySession(request);
		Integer type = user.getBaseInfo().getType();// 获取用户的类型
		String flightNo = request.getParameter("flightNo");
		String departDate = request.getParameter("departDate");
		String classCode = request.getParameter("classCode");
		policyqo.setDealerCode(classCode);
		policyqo.setFlightNo(flightNo);
		policyqo.setDepartDate(departDate);
		HgLogger.getInstance().info("liusong", "查询航班政策,查询的机票政策的QO：" + JSON.toJSONString(policyqo));
		// 比价得到航班政策dto
		FlightPolicyDTO flightPolicy = jpservice.queryFlightPolicy(policyqo);
		if (flightPolicy != null) {// 判断返回的航班政策是否为空
			HgLogger.getInstance().info("liusong", "查询航班政策,返回一个航班政策dto：" + JSON.toJSONString(flightPolicy));
			String policyId = flightPolicy.getPolicyId();// 获取航班政策的ID号
			// 通过指定的航班号查询得到某一航班信息
			JPFlightQO qo = new JPFlightQO();
			qo.setFlightNo(policyqo.getFlightNo());// 通过航班航班政策policyqo获取航班号
			qo.setDate(policyqo.getDepartDate());// 通过航班航班政策policyqo获取航班日期

			// 查询得到航班信息
			List<FlightDTO> flightByNo = new ArrayList<FlightDTO>();
			flightByNo = jpservice.queryFlight(qo);
			HgLogger.getInstance().info("liusong", "通过航班号查询指定航班,返回一个航班结果集：" + JSON.toJSONString(flightByNo));

			// new出一个map对象用于存放该航班信息
			Map<String, List<FlightDTO>> mapFlight = new HashMap<String, List<FlightDTO>>();
			if (flightByNo != null && flightByNo.size() > 0) {// 判断得到的flightByNo结果集是否为空
				// 计算参考税费(为机建费+燃油费)
				Double fuelSurTax = flightByNo.get(0).getFuelSurTax();// 取得燃油费
				Double airportTax = flightByNo.get(0).getAirportTax();// 取得机建费
				Double originalPrice = null;
				// 循环得出所在舱位的票面价
				for (FlightClassDTO flightClassDTO : flightByNo.get(0).getFlightClassList()) {
					if (flightClassDTO.getClassCode().equals(policyqo.getClassCode())) {
						originalPrice = flightClassDTO.getOriginalPrice();
					}
				}
				flightByNo.get(0).setTaxAmount(airportTax + fuelSurTax);// 设置参考费
				flightByNo.get(0).setFare(originalPrice);// 设置票面价
				flightByNo.get(0).setClassCode(policyqo.getClassCode());// 设置舱位代码

				// 获取到的航班记录放入map中
				mapFlight.put("list", flightByNo);
			}
			//根据机票的舱位列表和订单的舱位码判断哪个舱位
			String jClassName = JpUtil.getJpClassName(flightByNo.get(0));
			model.addAttribute("jClassName", jClassName);

			model.addAttribute("mapFlight", mapFlight);
			model.addAttribute("policyId", policyId);// 航班政策id
			model.addAttribute("type", type);
		} else {//如果查询得到的航班政策为null
			JPFlightQO qo = new JPFlightQO();
			qo.setFlightNo(policyqo.getFlightNo());
			qo.setDate(policyqo.getDepartDate());

			// 查询得到航班信息
			List<FlightDTO> flightByNo = new ArrayList<FlightDTO>();
			flightByNo = jpservice.queryFlight(qo);
			HgLogger.getInstance().info("liusong", "通过航班号查询指定航班,返回一个航班结果集：" + JSON.toJSONString(flightByNo));
			model.addAttribute("flightByNo", flightByNo);
			// 返回到view
			return "ticket/ssucce_error.html";
		}
		// 返回到view
		return "ticket/scart.html";
	}

	/**
	 * @throws
	 * @方法功能说明：航班机票创建订单处理
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月13日下午3:32:11
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:Object
	 */
	@RequestMapping("/createOrder")
	public Object createOrder(JPOrderCreateCommand command, Model model, HttpServletRequest request) {
		HgLogger.getInstance().info("zhaows", "查询当前传入参数" + JSON.toJSONString(command));
		// 判断command中航班号和出发日期是否为空，如果为空则跳转到机票查询页面
		if (command.getFlightNo() == null || command.getDate() == null) {
			return new RedirectView("/jp/ticketList", true);
		}

		UserDTO user = getUserBySession(request); // 获取登录用户的信息

		command.getLinker().setId(user.getId());// 设置联系人的ID号为登录用户的ID号
		command.setFromClientKey(ClientKeyUtil.FROM_CLIENT_KEY);// 设置HTTP请求来源标识
		command.setClientUserKey(ClientKeyUtil.FROM_USER_KEY);// 设置来源标识：0 mobile
		// , 1
		// pc；经销商所属用户KEY(规则=
		// 来源标识_key)

		HslJPOrderQO orderQO = new HslJPOrderQO();
		Integer[] status = {Integer.valueOf(JPOrderStatusConstant.SHOP_PAY_WAIT), Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_DEALING),
				Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_SUCC)};
		orderQO.setSts(status);
		orderQO.setFlightNo(command.getFlightNo());// 设置订单的航班号
		orderQO.setCardNo(command.getPassangers().get(0).getCardNo());// 设置订单的乘客信息
		orderQO.setFlightStartTime(command.getDate());// 设置航班的起飞时间

		// 创建订单之前先进行一次订单查询，查询订单库中是否存在在当日该趟航班用该身份证就行下订单操作
		List<JPOrderDTO> jporderList = jpservice.queryOrder(orderQO);
		HgLogger.getInstance().info("liusong", "查询一个订单，返回一个订单记录：" + JSON.toJSONString(jporderList));
		JPOrderCreateDTO jporderdto = null;

		if (jporderList.size() > 0) {    // 用来限定一趟航班一个身份证号在当天只能预订一次
			return new RedirectView("/jp/fail", true);
		} else {
			// 创建订单，并返回一个下单成功后返回商城订单号
			jporderdto = jpservice.orderCreate(command);
			HgLogger.getInstance().info("liusong", "创建一个订单，返回一个订单dto：" + JSON.toJSONString(jporderdto));

			//创建订单成功后会有一个订单号返回，取得该订单号查询一条该条订单记录，返回JPorder
			if (jporderdto != null) {
				jporderdto.setClassCode(command.getClassCode());
				String dealerOrderCode = jporderdto.getDealerOrderCode();//取得返回的订单号
				HslJPOrderQO order = new HslJPOrderQO();
				// 根据订单号获取该号对应的订单记录，此时取到的订单记录的状态为待支付状态
				order.setDealerOrderCode(dealerOrderCode);
				order.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_PAY_WAIT));//此时订单状态为待确认
				order.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));//此时订单的支付状态为待支付
				List<JPOrderDTO> jporders = jpservice.queryOrder(orderQO);//根据订单号返回一条订单记录
				if (jporders.size() > 0) {
					if (jporders != null && jporders.size() > 0) {
						JPOrderDTO dto = jporders.get(0);
						FlightDTO flightDTO = dto.getFlightDTO();
						if (flightDTO != null && dto.getFlightDTO().getClassCode() == null) {
							flightDTO.setClassCode(dto.getClassCode());
						}
						//根据机票的舱位列表和订单的舱位码判断哪个舱位
						String jClassName = JpUtil.getJpClassName(flightDTO);
						model.addAttribute("jClassName", jClassName);
						model.addAttribute("jporderDto", jporders.get(0));
						//根据机票的舱位列表和订单的舱位码判断哪个舱位
						HashMap<String, String> jpStatusMap = JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP;
						model.addAttribute("jpStatusMap", jpStatusMap);
					}
				}
			} else {// 如果返回的jporderdto为空，则下单失败
				return new RedirectView("/jp/error", true);
			}
		}
		// 根据登录用户的userid查询该用户拥有的卡券列表\
		HslCouponQO hslCouponQO = new HslCouponQO();
		hslCouponQO.setUserId(user.getId());
		// 只查询未使用的卡券并且满足使用条件的卡券
		hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
		hslCouponQO.setOrderPrice(jporderdto.getPayAmount());
		try {
			if (hslCouponQO.getUserId() != null) {
				List<CouponDTO> couponList = couponService.queryList(hslCouponQO);
				//得到当前用户代金券的数量
				String count = "0";
				for (CouponDTO counts : couponList) {
					if (counts.getBaseInfo().getCouponActivity().getBaseInfo().getCouponType() == 1) {
						count = "1";
					}
				}
				HgLogger.getInstance().info("liusong", "查询用户所拥有的可使用的卡券成功，查询的QO：" + JSON.toJSONString(hslCouponQO));
				model.addAttribute("count", count);
				model.addAttribute("couponNoUsed", couponList);
			} else {
				return new RedirectView("/user/login", true);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("liusong", "根据登录用户的userid查询该用户拥有的卡券列表失败" + e.getStackTrace());
			e.printStackTrace();
		}
		// 返回到支付页面
		return "ticket/payOrder_info.html";
	}

	/**
	 * @throws
	 * @方法功能说明：支付页面提交订单之前，验证卡券是否可用
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月13日下午3:32:31
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 */
	@RequestMapping(value = "/getCouponUsed")
	@ResponseBody
	private String getCouponUsed(HttpServletRequest request) {
		// 查询用户选择的卡券是否可使用
		String useCouponIDs = request.getParameter("useCouponIDs");
		String dealerOrderId = request.getParameter("dealerOrderId");// 获取订单号
		Double payPrice = Double.parseDouble(request.getParameter("payPrice"));// 获取实际价格
		String[] id = useCouponIDs.split(",");
		BatchConsumeCouponCommand consumeCouponCommand = null;
		consumeCouponCommand = new BatchConsumeCouponCommand();
		consumeCouponCommand.setCouponIds(id);
		consumeCouponCommand.setOrderId(dealerOrderId);
		consumeCouponCommand.setPayPrice(payPrice);
		String message = "";// 标识卡券是否可用的字段变量
		// 如果用户选择了多个卡券，那么每循环出一个卡券id就要去做一次判断，判断该卡券是否可用
		try {
			boolean flag = couponService.checkCoupon(consumeCouponCommand);
			if (flag) {
				HgLogger.getInstance().info("liusong", "卡券可用");
				message = "success";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			HgLogger.getInstance().error("liusong", "卡券不可用->JpController->payOrder->exception:" + HgLogger.getStackTrace(ex));
			System.out.println(HgLogger.getStackTrace(ex) + "==========================");

			return ex.getMessage();
		}
		return message;
	}

	/**
	 * 点击我的订单时，有待支付的订单时，点击操作框中的“去支付”按钮跳转需要调用的方法
	 *
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/toPayOrderInfo")
	public Object toPayOrderInfo(Model model, HttpServletRequest request) {
		String dealerOrderCode = request.getParameter("dealerOrderCode");// 获取到传入的订单号
		HslJPOrderQO hslJpOrderQo = new HslJPOrderQO();
		if (dealerOrderCode == null) {//从我的订单跳转到支付页面时传入的订单号是否为空，为空则跳转到登录页
			return new RedirectView("/user/login", true);
		}
		// 根据订单号获取该号对应的订单记录，此时取到的订单记录的状态为未支付状态
		hslJpOrderQo.setDealerOrderCode(dealerOrderCode);
		hslJpOrderQo.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_PAY_WAIT));//此时订单状态为待确认
		hslJpOrderQo.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));//此时订单的支付状态为待支付

		if (hslJpOrderQo != null) {
			try {
				HgLogger.getInstance().info("liusong", "根据我的订单模块传入的订单号查询一条订单记录,查询的QO：" + JSON.toJSONString(hslJpOrderQo));
				List<JPOrderDTO> jporderList = jpservice.queryOrder(hslJpOrderQo);
				HgLogger.getInstance().info("liusong", "根据我的订单模块传入的订单号查询一条订单记录：" + JSON.toJSONString(jporderList));
				if (jporderList != null && jporderList.size() > 0) {
					JPOrderDTO dto = jporderList.get(0);
					FlightDTO flightDTO = dto.getFlightDTO();
					if (flightDTO != null && dto.getFlightDTO().getClassCode() == null) {
						flightDTO.setClassCode(dto.getClassCode());
					}
					model.addAttribute("jporderDto", dto);
					//根据机票的舱位列表和订单的舱位码判断哪个舱位
					String jClassName = JpUtil.getJpClassName(flightDTO);
					model.addAttribute("jClassName", jClassName);
				}
				// 若查询能得到订单记录，那就删除卡券中的与之相关联的订单号
				if (jporderList.size() > 0) {
					UserDTO user = getUserBySession(request); // 获取登录用户的信息
					// 判断该订单是否属于该登录用户
					if (user.getAuthInfo().getLoginName().equals(jporderList.get(0).getOrderUser().getLoginName())) {
						OrderRefundCommand command = new OrderRefundCommand();
						command.setOrderId(dealerOrderCode);
						try {
							couponService.orderRefund(command);
							HgLogger.getInstance().info("liusong", "删除卡券中的与之相关联的订单号成功：" + JSON.toJSONString(command));
						} catch (CouponException e) {
							e.printStackTrace();
							HgLogger.getInstance().error("liusong", "删除卡券中的与之相关联的订单号失败！！" + HgLogger.getStackTrace(e));
						}
						// 根据登录用户的userid查询该用户拥有的卡券列表\
						HslCouponQO hslCouponQO = new HslCouponQO();
						hslCouponQO.setUserId(user.getId());
						// 只查询未使用的卡券并且满足使用条件的卡券
						hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
						hslCouponQO.setOrderPrice(jporderList.get(0).getPayAmount());
						try {
							if (hslCouponQO.getUserId() != null) {
								List<CouponDTO> couponList = couponService.queryList(hslCouponQO);
								//得到当前用户代金券的数量
								String count = "0";
								for (CouponDTO counts : couponList) {
									if (counts.getBaseInfo().getCouponActivity().getBaseInfo().getCouponType() == 1) {
										count = "1";
									}
								}
								HgLogger.getInstance().info("liusong", "查询用户所拥有的可使用的卡券成功，查询的QO：" + JSON.toJSONString(hslCouponQO));
								model.addAttribute("count", count);
								model.addAttribute("couponNoUsed", couponList);
							} else {
								return new RedirectView("/user/login", true);
							}
						} catch (Exception e) {
							HgLogger.getInstance().error("liusong", "根据登录用户的userid查询该用户拥有的卡券列表失败" + e.getStackTrace());
						}
						// 返回页面
						model.addAttribute("jporderDto", jporderList.get(0));
						HashMap<String, String> jpStatusMap = JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP;
						model.addAttribute("jpStatusMap", jpStatusMap);
					} else {
						HgLogger.getInstance().error("liusong", "根据我的订单模块传入的订单号查询订单记录,该订单不属于该登录用户！！");
						return new RedirectView("/jp/worry", true);
					}
				} else {
					HgLogger.getInstance().error("liusong", "根据我的订单模块传入的订单号查询订单记录,该订单已经取消！！");
					return "ticket/orderCancel.html";
				}
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().error("liusong", "根据我的订单模块传入的订单号查询一条订单记录失败！！" + HgLogger.getStackTrace(e));
				return new RedirectView("/jp/worry", true);
			}
		}
		return "ticket/payOrder_info.html";
	}

	/**
	 * 支付页面提交订单到该方法，该方法去访问支付平台，请求支付
	 *
	 * @param command
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/payOrder")
	@ResponseBody
	public Object payOrder(APIJPOrderCreateCommand command, Model model,HttpServletRequest request) {
		String dealerOrderId = "";
		try {
			// 保存用户选择的卡券,生成一个订单快照
			String useCouponIDs = request.getParameter("useCouponIDs");
			dealerOrderId = request.getParameter("dealerOrderId");// 获取订单号
			//根据订单号查询当前订单
			HslJPOrderQO hslJpOrderQo = new HslJPOrderQO();
			if (dealerOrderId == null) {//从我的订单跳转到支付页面时传入的订单号是否为空，为空则跳转到登录页
				return new RedirectView("/user/login", true);
			}
			// 根据订单号获取该号对应的订单记录，此时取到的订单记录的状态为未支付状态
			hslJpOrderQo.setDealerOrderCode(dealerOrderId);
			hslJpOrderQo.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_PAY_WAIT));//此时订单状态为待确认
			hslJpOrderQo.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));//此时订单的支付状态为待支付
			HgLogger.getInstance().info("liusong", "payOrder-根据我的订单模块传入的订单号查询一条订单记录,查询的QO：" + JSON.toJSONString(hslJpOrderQo));
			List<JPOrderDTO> jporderList = jpservice.queryOrder(hslJpOrderQo);
			HgLogger.getInstance().info("liusong", "payOrder-根据我的订单模块传入的订单号查询一条订单记录：" + JSON.toJSONString(jporderList));
			Double payAmount = jporderList.get(0).getPayAmount();//得到本应支付的总金额
			Double couponBalance = 0.00;//得到卡券的金额
			if (StringUtils.isNotBlank(useCouponIDs)) {
				couponBalance = couponService.queryTotalPrice(useCouponIDs, payAmount.intValue());//卡券金额
			}
			//Double payPrice = Double.parseDouble(request.getParameter("payPrice"));// 获取实际价格
			Double payPrice = (payAmount * constant - couponBalance * constant)/constant;
			HgLogger.getInstance().info("zhaows", "payOrder-->payPrice" + payPrice);
			String[] ids = useCouponIDs.split(",");
			if (useCouponIDs != "") {
				BatchConsumeCouponCommand consumeCouponCommand = null;
				consumeCouponCommand = new BatchConsumeCouponCommand();
				consumeCouponCommand.setCouponIds(ids);
				consumeCouponCommand.setOrderId(dealerOrderId);
				consumeCouponCommand.setPayPrice(payPrice);
				consumeCouponCommand.setOrderType(ConsumeOrderSnapshot.ORDERTYPE_JP);
				HgLogger.getInstance().info("zhaows", "卡券id-->payOrder" + ids);
				couponService.occupyCoupon(consumeCouponCommand);
			}
			if (payPrice > 0) {
				HgLogger.getInstance().info("liusong",
						"JPController>>createClientNotify>支付command" + JSON.toJSONString(command));
				// 如果直接回车访问payOrder方法则直接返回航班列表查询页
				if (command == null) {
					return new RedirectView("/jp/ticketList", true);
				}
				UserDTO user = getUserBySession(request); // 获取登录用户的信息

				// 创建一个支付command
				CreateAlipayPayOrderCommand alipayPayOrder = new CreateAlipayPayOrderCommand();
				String messageUrl = "";// 消息地址
				String startCity = request.getParameter("startCity");// 获取起飞城市
				String endCity = request.getParameter("endCity");// 获取到达城市
				//商城在支付平台编号
				String clientId = SysProperties.getInstance().get("paymentClientId") == null ? "33f5145719314364b0924158d2a1c610" : SysProperties.getInstance().get("paymentClientId");
				//商城在支付平台密钥
				String clientKeys = SysProperties.getInstance().get("paymentClienKeys") == null ? "e10adc3949ba59abbe56e057f20f883e" : SysProperties.getInstance().get("paymentClienKeys");
				//支付渠道 支付宝支付为1
				Integer payType = Integer.parseInt(command.getPayType());
				// 创建一个支付请求dto
				PayOrderRequestDTO payOrderRequestDto = new PayOrderRequestDTO();
				payOrderRequestDto.setClientTradeNo(command.getDealerOrderId());// 生成订单后返回的商城订单号
				payOrderRequestDto.setPayerClientUserId(user.getId());// 登录用户的id
				payOrderRequestDto.setName(user.getBaseInfo().getName());// 登录用户的真实姓名
				payOrderRequestDto.setIdCardNo(user.getBaseInfo().getIdCardNo());// 登录用户的身份证号
				payOrderRequestDto.setMobile(user.getMobile());// 登录用户的手机号
				payOrderRequestDto.setAmount(payPrice);// 订单的支付总价
				payOrderRequestDto.setPaymentClientId(clientId);
				payOrderRequestDto.setPaymentClienKeys(clientKeys);
				payOrderRequestDto.setPayChannelType(payType);
				// 收款方
				payOrderRequestDto.setPayeeAccount(SysProperties.getInstance().get("payeeAccount") == null ? AlipayConfig.email : SysProperties.getInstance().get("payeeAccount"));
				payOrderRequestDto.setPayeeName(SysProperties.getInstance().get("payeeName") == null ? "票量科技" : SysProperties.getInstance().get("payeeName"));
				payOrderRequestDto.setPayerRemark(SysProperties.getInstance().get("payerRemark") == null ? "" : SysProperties.getInstance().get("payerRemark"));
				// 创建支付订单
				alipayPayOrder.setPayOrderCreateDTO(payOrderRequestDto);
				// 收款方
				alipayPayOrder.setSellerEmail(SysProperties.getInstance().get("sellerEmail") == null ? AlipayConfig.email : SysProperties.getInstance().get("sellerEmail"));
				alipayPayOrder.setPartner(SysProperties.getInstance().get("partner") == null ? AlipayConfig.partner : SysProperties.getInstance().get("partner"));
				alipayPayOrder.setKeys(SysProperties.getInstance().get("key") == null ? AlipayConfig.key : SysProperties.getInstance().get("key"));
				alipayPayOrder.setSubject("飞机票[" + startCity + "-" + endCity + "]*1");
				alipayPayOrder.setBody(AppInfoUtils.getAlipayRemarkPrefix() + (SysProperties.getInstance().get("body") == null ? "" : SysProperties.getInstance().get("body")));
				alipayPayOrder.setShowUrl(SysProperties.getInstance().get("showUrl") == null ? "" : SysProperties.getInstance().get("showUrl"));
				String context = JSON.toJSONString(alipayPayOrder);
				HgLogger.getInstance().info("liusong", "支付平台生成订单,请求CreateAlipayPayOrderCommand参数：" + context);
				// 支付平台生成订单，并返回支付宝请求地址
				messageUrl = payOrderSpiService.pay(clientId, clientKeys, payType, context);
				HgLogger.getInstance().info("liusong", "支付平台生成订单，并返回支付宝请求地址：" + messageUrl);
				return messageUrl;
			} else {
				//如果 使用优惠券 大于支付金额，直接 请求出票
				Integer status = Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING);// 此时订单状态为出票处理中
				Integer payStatus = Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC);// 此时订单的支付状态已支付状态
				jpservice.updateOrderStatus(dealerOrderId, status, payStatus);// 修改入库订单状态和订单的支付状态
				HgLogger.getInstance().info("liusong",
						"JPController>>createClientNotify>入库订单的订单号：" + dealerOrderId);
				// 支付成功后发送出票请求
				JPAskOrderTicketCommand jpAskOrderTicketCommand = new JPAskOrderTicketCommand();
				jpAskOrderTicketCommand.setOrderId(dealerOrderId);
				jpAskOrderTicketCommand.setPayOrderId("");
				jpAskOrderTicketCommand.setBuyerEmail(SysProperties.getInstance().get("payeeAccount") == null ? AlipayConfig.email : SysProperties.getInstance().get("payeeAccount"));
				jpAskOrderTicketCommand.setPayWay("ZH");

				// 配置 askOrderTicket != false 时调用
				if (!"false".equals(SysProperties.getInstance().get("askOrderTicket")))
					jpFlightLocalService.askOrderTicket(jpAskOrderTicketCommand);// 请求出票

				HgLogger.getInstance().info("liusong", "JPController>>createClientNotify>发送出票请求：" + JSON.toJSONString(command));
				// 修改卡券的状态
				if (useCouponIDs != "") {
					BatchConsumeCouponCommand couponCommand = new BatchConsumeCouponCommand();
					couponCommand.setCouponIds(ids);
					couponCommand.setOrderId(dealerOrderId);
					couponCommand.setPayPrice(payPrice);
					couponService.confirmConsumeCoupon(couponCommand);
				}
				return "<script type='text/javascript'>location.href='" + request.getContextPath() + "/jp/ssucce?dealerOrderId=" + dealerOrderId + "'</script>";
			}

		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("liusong", "JpController->payOrder->exception:" + HgLogger.getStackTrace(e));
			return "<script type='text/javascript'>location.href='" + request.getContextPath() + "/jp/worry?dealerOrderId=" + dealerOrderId + "'</script>";
		}
	}
	/**
	 * 跳转错误页面
	 *
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
	 *
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
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/worry")
	public String payFail(Model model, HttpServletRequest request) {
		return "ticket/worry.html";
	}

	/**
	 * 跳转错误页面,支付成功页面
	 *
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
	 *
	 * @param request
	 */
	@RequestMapping(value = "/getclient")
	public void createClientNotify(HttpServletRequest request, HttpServletResponse response) {
		String orderNo = request.getParameter("orderNo");// 获取商城订单编号
		PayOrderQO payorderQO = new PayOrderQO();
		payorderQO.setOrderNo(orderNo);// 商城订单编号
		payorderQO.setPaymentClientID(SysProperties.getInstance().get("paymentClientId") == null ? "33f5145719314364b0924158d2a1c610" : SysProperties.getInstance().get("paymentClientId"));// 客户端id
		payorderQO.setPaymentClienKeys(SysProperties.getInstance().get("paymentClienKeys") == null ? "e10adc3949ba59abbe56e057f20f883e" : SysProperties.getInstance().get("paymentClienKeys"));// 客户端密钥
		payorderQO.setPaySuccess(true);
		// 根据商城订单号，查询一条订单记录
		HslJPOrderQO hslJPOrderQO = new HslJPOrderQO();
		hslJPOrderQO.setDealerOrderCode(orderNo);
		hslJPOrderQO.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));//此时订单的支付状态为待支付
		List<JPOrderDTO> jporderList = jpservice.queryOrder(hslJPOrderQO);
		HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->根据订单号获取一条订单记录：" + JSON.toJSONString(jporderList));
		try {
			HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->查询支付平台订单状态,查询的QO：" + JSON.toJSONString(payorderQO));
			// 商城查询订单是否支付成功
			List<PayOrderDTO> payOrderList = payOrderSpiService.queryPayOrderList(payorderQO);
			HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->查询支付平台订单状态,查询得到的结果集 ：" + JSON.toJSONString(payOrderList));
			Integer paySuccess = 0;//默认支付状态支付失败，如果查询出订单，则赋值为1
			String buyer_email = "";
			String trade_no = "";
			if (payOrderList.size() > 0) {
				paySuccess = payOrderList.get(0).getPayStatus();// 取得订单状态
				buyer_email = payOrderList.get(0).getBuyer_email();// 取得支付人的支付宝帐号
				trade_no = payOrderList.get(0).getTrade_no();// 取得支付宝订单号
			}
			// 通过调用payOrderSpiService接口得到订单状态，若成功支付则更改入库订单的状态
			if (paySuccess == 1) {
				if (jporderList.size() == 0) {
					HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->该订单号对应订单的订单状态已经为支付成功状态，该订单号：" + orderNo);
					return;
				} else {
					Integer status = Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING);//此时订单状态为出票处理中
					Integer payStatus = Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC);//此时订单的支付状态已支付状态
					jpservice.updateOrderStatus(orderNo, status, payStatus);// 修改入库订单状态和订单的支付状态
					HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->入库订单的订单号：" + orderNo);
					// 支付成功后发送出票请求
					JPAskOrderTicketCommand command = new JPAskOrderTicketCommand();
					command.setOrderId(orderNo);
					command.setPayOrderId(trade_no);
					command.setBuyerEmail(buyer_email);
					command.setPayWay("ZH");

					// 配置 askOrderTicket != false 时调用
					if (!"false".equals(SysProperties.getInstance().get("askOrderTicket")))
						jpFlightLocalService.askOrderTicket(command);// 请求出票
					
					HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->发送出票请求：" + JSON.toJSONString(command));

					// 如果支付成功，则同时修改与该订单相关的卡券的状态为已消费状态
					HslCouponQO hslCouponQO = new HslCouponQO();
					hslCouponQO.setOrderId(orderNo);

					// 根据订单号查询一个与订单绑定的卡券快照
					List<CouponDTO> coupondtos = couponService.queryList(hslCouponQO);
					HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->根据订单号查询一个与订单绑定使用的卡券" + JSON.toJSONString(coupondtos));
					if (coupondtos != null && coupondtos.size() > 0) {
						ConsumeOrderSnapshotDTO consumeOrderSnapshotDTO = new ConsumeOrderSnapshotDTO();
						List<String> ids = new ArrayList<String>();
						for (CouponDTO coupondto : coupondtos) {
							if (coupondto != null) {
								ids.add(coupondto.getId());
							}
						}
						// 修改卡券的状态
						BatchConsumeCouponCommand couponCommand = new BatchConsumeCouponCommand();
						couponCommand.setCouponIds(ids.toArray(new String[ids.size()]));
						couponCommand.setOrderId(consumeOrderSnapshotDTO.getOrderId());
						couponCommand.setPayPrice(consumeOrderSnapshotDTO.getPayPrice());

						HgLogger.getInstance().error("liusong", "机票支付--支付平台--异步通知-->机票出票后确认使用卡券command：" + JSON.toJSONString(couponCommand));
						try {
							couponService.confirmConsumeCoupon(couponCommand);
						} catch (CouponException e) {
							e.printStackTrace();
							HgLogger.getInstance().error("liusong", "机票支付--支付平台--异步通知-->机票出票后确认使用卡券出现异常：" + e.getMessage() + HgLogger.getStackTrace(e));
						}
					}
					// 发放卡券：订单满
					CreateCouponCommand cmd = new CreateCouponCommand();
					cmd.setSourceDetail("订单满送");
					cmd.setPayPrice(jporderList.get(0).getPayAmount());
					cmd.setMobile(jporderList.get(0).getOrderUser().getMobile());
					cmd.setUserId(jporderList.get(0).getOrderUser().getUserId());
					cmd.setLoginName(jporderList.get(0).getOrderUser().getLoginName());
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

					// 发送短信通知下单人,商旅分销已经有调用过发送短信的方法接口了

					try {
						//查询一条订单记录
						HslJPOrderQO qo = new HslJPOrderQO();
						qo.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING));
						qo.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));
						qo.setDealerOrderCode(orderNo);
						List<JPOrderDTO> jpOrderList = jpservice.queryOrder(qo);
						JPOrderDTO jpOrder = jpOrderList.get(0);
						HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->机票订单支付成功发送短信时查询得到的机票DTO" + JSON.toJSONString(jpOrder));

						String mobile = jpOrder.getOrderUser().getMobile();//获取下单人手机号
						String smsContent = ("【" + SysProperties.getInstance().get("sms_sign", "票量旅游") + "】您的订单" + orderNo + "，已经购买成功，出票完成后将短信通知，请您放心安排行程。查询订单请点击http://m.ply365.com，客服电话：0571-28280813。");
						smsUtils.sendSms(mobile, smsContent);
						HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->:" + mobile + " smsContent:" + smsContent);
					} catch (Exception e) {
						HgLogger.getInstance().error("liusong", "机票支付--支付平台--异步通知-->用户付款成功短信发送异常" + HgLogger.getStackTrace(e));
					}
				}
			} else {
				HgLogger.getInstance().info("liusong", "机票支付--支付平台--异步通知-->该订单号对应订单的订单状态已经为支付失败状态，该订单号：" + orderNo);
				// 如果支付失败，则需要删除订单快照
				OrderRefundCommand command = new OrderRefundCommand();
				command.setOrderId(orderNo);
				try {
					couponService.orderRefund(command);
				} catch (CouponException e) {
					e.printStackTrace();
					HgLogger.getInstance().error("liusong", "机票支付--支付平台--异步通知-->删除订单快照失败" + e.getMessage() + HgLogger.getStackTrace(e));
				}
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("liusong", "机票支付--支付平台--异步通知-->向支付平台查询订单失败:" + e.getMessage() + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/**
	 * 重定向地址跳转成功页面（支付平台同步回调方法）
	 *
	 * @param model
	 * @param request
	 */
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
			payOrderList = payOrderSpiService.queryPayOrderList(payorderQO);
			HgLogger.getInstance().info("chenxy", "机票支付--支付平台--同步通知-->查询得到的结果集 ：" + JSON.toJSONString(payOrderList));
			HslJPOrderQO hslJPOrderQO = new HslJPOrderQO();
			hslJPOrderQO.setDealerOrderCode(clientNo);
			// 查询判断多种状态(已支付)
			hslJPOrderQO.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));//订单的支付状态为已支付
			List<JPOrderDTO> jporderList = jpservice.queryOrder(hslJPOrderQO);
			HgLogger.getInstance().info("chenxy", "机票支付--支付平台--同步通知-->本地根据订单状态查询记录：" + JSON.toJSONString(jporderList));
			// 支付成功页面读取相关信息
			model.addAttribute("clientNo", clientNo);
			model.addAttribute("jporderList", jporderList);
			// 判断是否支付成功
			if (jporderList.size() > 0 || payOrderList.size() > 0) {
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
	 *
	 * @param specifiedDay
	 * @return
	 */
	private static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("liusong", "获取指定日期的后一天日期错误:" + HgLogger.getStackTrace(e));
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter; // 返回后一天日期
	}

	/**
	 * 获得指定日期的前一天
	 *
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	private static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("liusong", "获取指定日期的前一天失败:" + HgLogger.getStackTrace(e));
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore; // 返回前一天日期
	}

	/**
	 * 获取所有的公司
	 *
	 * @param request
	 */
	@RequestMapping("/companys")
	@ResponseBody
	private String getCompany(HttpServletRequest request) {
		UserDTO user = getUserBySession(request);
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		hslCompanyQO.setUserId(user.getId());// 这里替换成用户的id，公司查询条件
		List<CompanyDTO> companyList = companyService.getCompanys(hslCompanyQO);// 获得所有的公司列表
		HgLogger.getInstance().info("liusong", "查询所有的公司集合：" + JSON.toJSONString(companyList));
		return JSON.toJSONString(companyList);
	}

	/**
	 * 根据公司Id获取部门
	 *
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/getDepartments")
	@ResponseBody
	private String getDepartments(@RequestParam("companyId") String companyId) {
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		List<DepartmentDTO> departmentList = new ArrayList<DepartmentDTO>();
		if (StringUtils.isNotBlank(companyId)) {
			HslCompanyQO hslCompanyQO = new HslCompanyQO();
			hslCompanyQO.setId(companyId);
			hslDepartmentQO.setCompanyQO(hslCompanyQO);
			departmentList = companyService.getDepartments(hslDepartmentQO);// 获取指定公司的所有部门列表
		}
		HgLogger.getInstance().info("liusong", "根据所选公司的ID查询所有的部门集合：" + JSON.toJSONString(departmentList));
		return JSON.toJSONString(departmentList);
	}

	/**
	 * 根据部门Id获取员工
	 *
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/getMembers")
	@ResponseBody
	private String getMembers(@RequestParam("deptId") String deptId) {
		HslMemberQO hslMemberQO = new HslMemberQO();
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		if (StringUtils.isNotBlank(deptId)) {
			hslMemberQO.setDepartmentId(deptId);
			memberList = companyService.getMembers(hslMemberQO);// 获取指定部门的所有员工列表
		}
		HgLogger.getInstance().info("liusong", "根据所选部门的ID查询所有的员工集合：" + JSON.toJSONString(memberList));
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
		List<MemberDTO> memberInfoList = new ArrayList<MemberDTO>();
		if (StringUtils.isNotBlank(memberId)) {
			hslMemberQO.setId(memberId);
			memberInfoList = companyService.getMembers(hslMemberQO);// 获取指定员工的详细信息
		}
		HgLogger.getInstance().info("liusong", "根据所选员工的ID查询员工的详细信息：" + JSON.toJSONString(memberInfoList));
		return JSON.toJSONString(memberInfoList);
	}

	/**
	 * 获取用户的登录名
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLoginName")
	@ResponseBody
	private String getLoginName(HttpServletRequest request) {
		// 获取user
		UserDTO user = getUserBySession(request);
		UserAuthInfoDTO userAuthDto = new UserAuthInfoDTO();
		// 设置登录用户名，便于前端获取
		userAuthDto.setLoginName(user.getAuthInfo().getLoginName());
		return JSON.toJSONString(userAuthDto);
	}

	/**
	 * 获取卡券的使用条件以及获得获取卡券所属的活动id
	 *
	 * @param request
	 */
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
			HgLogger.getInstance().error("liusong", "获取卡券记录失败:" + HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(couponDto);
	}

	/**
	 * 获取用户区分是企业用户还是个人用户
	 UserDTO user = getUserBySession(request);
	 String userId = "";
	 if (user != null) {
	 userId = user.getId();
	 HgLogger.getInstance().debug("liusong", "机票航班列表有用户登录："+ user.getAuthInfo().getLoginName());
	 } else {
	 HgLogger.getInstance().debug("liusong", "机票航班列表没有用户登录");
	 }
	 // 获取浏览记录
	 getRecord(userId, model, request);
	 // 获取热门景点数据
	 getHotMpScenicSpots(model);
	 model.addAttribute("from", cityName[0]);
	 model.addAttribute("arrive", cityName[1]);
	 model.addAttribute("startDate", request.getParameter("date"));
	 model.addAttribute("nextDate", nextDate);
	 model.addAttribute("pvDate", pvDate);
	 model.addAttribute("airportname", airportname);
	 model.addAttribute("airtime", airtime);
	 } catch (Exception e) {
	 e.printStackTrace();
	 }
	 //return "ticket/ticket_list.html";
	 //map.put("list", flightDTOs);
	 return JSON.toJSONString(map);

	 }*/

	/**
	 * 取消订单（包括退废票）
	 *
	 * @return
	 */
	@RequestMapping(value = "/order/cancel")
	@ResponseBody
	public String orderCancel(@RequestParam(value = "id", required = false) String id,
							  @RequestParam(value = "ticketNumbers", required = false) String[] ticketNumbers,
							  @RequestParam(value = "refundType", required = false) String refundType,
							  @RequestParam(value = "backPolicy", required = false) String backPolicy,
							  @RequestParam(value = "backAmount", required = false) String backAmount,
							  @RequestParam(value = "actionType", required = false) String actionType,
							  @RequestParam(value = "reason", required = false) String reason,
							  @RequestParam(value = "op", required = false) String op
	) {
		String success = "";
		String fail = "";
		if (StringUtils.isBlank(op)) {
			success = "订单取消成功";
			fail = "订单取消失败";
		} else if (op.equals("1")) {
			success = "废票成功";
			fail = "废票失败";
		} else if (op.equals("2")) {
			success = "退票成功";
			fail = "退票失败";
		}

		AuthUser authUser = super.getAuthUser();
		if (authUser != null) {

			HslJPOrderQO qo = new HslJPOrderQO();
			qo.setId(id);
			JPOrder jpOrder = jpOrderLocalService.queryOrderDetail(qo);

			if (jpOrder != null) {
				JPCancelTicketCommand command = new JPCancelTicketCommand();
				command.setOrderId(jpOrder.getOrderCode());
				command.setDealerOrderCode(jpOrder.getDealerOrderCode());
				command.setTicketNumbers(ArraysUtil.toStringWithSlice(ticketNumbers, ","));
				command.setName(authUser.getLoginName());
				command.setRefundType(refundType);
				command.setBackPolicy(backPolicy);
				command.setBackAmount(backAmount);
				command.setActionType(actionType);
				command.setReason(reason);
				JPOrderDTO jpOrderDTO = jpservice.cancelTicket(command);

				if (jpOrderDTO == null) {
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, fail, "", "");
				} else {
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, success, DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "jp");
				}
			} else {
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "查询不到相应订单", "", "");
			}
		} else {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "请登录", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "");
		}
	}

	static class ArraysUtil {
		public static String toStringWithSlice(Object[] objs, String slice) {
			if (objs == null || objs.length == 0)
				return null;

			StringBuilder sb = new StringBuilder();
			for (Object obj : objs) {
				sb.append(obj).append(slice);
			}

			//去掉最后一个slice
			if (sb.length() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}

			return sb.toString();
		}

		public static void main(String[] args) {
			String[] str = {"1", "2", "3"};
			System.out.println(ArraysUtil.toStringWithSlice(str, ","));
		}
	}

	/**
	 * 时间比较大小
	 *
	 * @param str1
	 * @param str2
	 * @param str3
	 */
	public Boolean compareToTime(String str1, String str2, String str3) throws ParseException {
		boolean isExists;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		// 定义区间值
		Date dateAfter = df.parse(str2);
		Date dateBefor = df.parse(str1);
		// 将你输入的String 数据转化为Date
		Date time = df.parse(str3);
		// 判断time是否在XX之后，并且 在XX之前
		if (time.getTime() >= dateBefor.getTime() && time.getTime() < dateAfter.getTime()) {
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
	 */
	private void getRecord(String userId, final Model model, HttpServletRequest request) {
		HgLogger.getInstance().debug("liusong", "景点列表,浏览记录查询");
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
	 *
	 * @param model
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
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
			HgLogger.getInstance().info("liusong", "热门景点数据，查询数据：" + JSON.toJSONString(hotScenicMap));
			model.addAttribute("hotScenicMap", hotScenicMap);
		} catch (MPException e) {
			hotScenicMap = new HashMap();
			hotScenicMap.put("dto", new ArrayList<HotScenicSpotDTO>());
			hotScenicMap.put("count", 0);
			model.addAttribute("hotScenicMap", hotScenicMap);
			e.printStackTrace();
			HgLogger.getInstance().error("liusong", "JpController->main->exception:" + HgLogger.getStackTrace(e));
		}
	}

	/**
	 * @throws
	 * @方法功能说明：查询订单详情
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-26下午1:45:57
	 * @参数：@param model
	 * @return:void
	 */
	@RequestMapping("/getOrderDetails")
	@ResponseBody
	public String getOrderDetails(Model model, HttpServletRequest request) {
		List<JPOrderDTO> jporderList = null;
		try {
			String orderid = request.getParameter("dealerOrderId");
			HslJPOrderQO hslJPOrderQO = new HslJPOrderQO();
			hslJPOrderQO.setDealerOrderCode(orderid);
			jporderList = jpservice.queryOrder(hslJPOrderQO);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows", "getOrderDetails->机票订单查询失败" + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return JSON.toJSONString(jporderList);
	}

}
