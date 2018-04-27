package hsl.web.controller.yxjp;

import com.alibaba.fastjson.JSON;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.app.service.local.coupon.CouponLocalService;
import hsl.app.service.local.jp.JPFlightLocalService;
import hsl.app.service.local.user.TravelerLocalService;
import hsl.app.service.local.yxjp.YXFlightService;
import hsl.app.service.local.yxjp.YXJPOrderLocalService;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.coupon.CouponUseConditionInfo;
import hsl.domain.model.sys.CityAirCode;
import hsl.domain.model.user.traveler.Traveler;
import hsl.domain.model.yxjp.YXJPOrder;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.yxjp.order.CreateYXJPOrderCommand;
import hsl.pojo.command.yxjp.order.PayToYXJPOrderCommand;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.coupon.CouponUseConditionInfoDTO;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.dto.yxjp.YXFlightDTO;
import hsl.pojo.dto.yxjp.YXLinkmanDTO;
import hsl.pojo.dto.yxjp.YXResultFlightDTO;
import hsl.pojo.dto.yxjp.YXResultQueryFlightDTO;
import hsl.pojo.exception.CouponException;
import hsl.pojo.exception.MPException;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.pojo.qo.user.TravelerQO;
import hsl.pojo.qo.yxjp.YXJPOrderQO;
import hsl.pojo.util.HSLConstants;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.company.CompanyService;
import hsl.spi.inter.mp.MPScenicSpotService;
import hsl.spi.qo.sys.CityAirCodeQO;
import hsl.web.controller.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import plfx.api.client.api.v1.gn.request.JPFlightGNQO;
import plfx.api.client.api.v1.gn.request.JPPolicyGNQO;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 易行机票Controller
 * Created by huanggg on 2015/11/25.
 */
@Controller
@RequestMapping(value="/yxjp")
public class YxJpController extends BaseController {

	private  String                 devName ="hgg";

	final    private                String  ORDER_NO_FOR_COUPONS = "ORDER_NO_FOR_COUPONS";

	final    private                String  YX_JP_SESSION_TOKEN = "YX_JP_SESSION_TOKEN";

	@Autowired
	private YXFlightService      	flightService;

	@Autowired
	private JPFlightLocalService 	jpFlightLocalService;

	@Autowired
	private YXJPOrderLocalService   orderLocalService;

	@Autowired
	private CompanyService 			companyService;

	@Autowired
	private TravelerLocalService    travelerLocalService;

	@Autowired
	private CouponLocalService      couponLocalService;

	@Autowired
	private CouponService 			couponService;

	@Autowired
	private MPScenicSpotService 	scenicSpotService;

	/**
	 * 机票主页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/main")
	public  String jpMain(Model model,JPFlightGNQO qo,HttpServletRequest request,String flightTime){

		model.addAttribute("qo",qo);
		model.addAttribute("flightTime",flightTime);
		//搜索热门景点
		getHotMpScenicSpots(model);

		return "yxjp/jp-list.html";
	}

	/**
	 * 查询机票
	 * auth:hgg
	 * @param model
	 * @param qo
	 * @return
	 */
	@RequestMapping("/search-jp")
	@ResponseBody
	public Object searchJp(Model model,JPFlightGNQO qo,HttpServletRequest request) throws ParseException, UnsupportedEncodingException {

		Map<String, Object> map = new HashMap<String, Object>();
		String flightTime = request.getParameter("flightTime");
		if(StringUtils.isBlank(flightTime)){
			flightTime = "null";
		}

		HgLogger.getInstance().info(devName,"查询机票，传入参数【"+JSON.toJSONString(qo)+"】");
		HgLogger.getInstance().info(devName,"查询机票，传入参数：flightTime"+flightTime);
		//查询到的航班
		List<YXResultFlightDTO> flights = new ArrayList<YXResultFlightDTO>();
		//满足条件的航班
		List<YXResultFlightDTO> needFlights = new ArrayList<YXResultFlightDTO>();
		//航空公司,出发地,目的地 三项参数必填
		if(qo.getStartDate() == null || StringUtils.isBlank(qo.getDstCity())||StringUtils.isBlank(qo.getOrgCity())){

			HgLogger.getInstance().error(devName, "查询机票，传入参数：航空公司,出发地中有一项为空");

			return JSON.toJSONString(map);
		}

		//传入参数航空公司,代表查询所有的航班
		if(StringUtils.equals(qo.getAirCompany(),"航空公司")){
			qo.setAirCompany("");
		}

		//检测用户输入,出发地和目的地是否合法
		boolean hasCityAirCode = hasCityAirCode(qo);
		if(!hasCityAirCode){
			HgLogger.getInstance().error(devName, "查询机票，检测用户输入,出发地和目的地是否合法查询结果：不合法");
			flights = null;
		}else{
			//查询航班
			YXResultQueryFlightDTO flightListGNDto = flightService.queryFlight(qo);
			if(flightListGNDto.getFlightList() != null){
				flights = flightListGNDto.getFlightList();

				map.put("buildFee",(flightListGNDto.getBuildFee()));
				map.put("oilFee", (flightListGNDto.getOilFee()));
				map.put("buildFeeOrOilFee", (flightListGNDto.getBuildFee() + flightListGNDto.getOilFee()));
				//筛选航班
				needFlights = needFlight(flights,qo,flightTime);
			}
			HgLogger.getInstance().info(devName,"查询机票，查询结果航班数量："+flights.size()+"，满足条件的航班数量:"+needFlights.size());
			HgLogger.getInstance().info(devName,"查询机票，查询结果航班详情【"+JSON.toJSONString(flights)+"】");
			HgLogger.getInstance().info(devName,"查询机票，满足条件航班详情【"+JSON.toJSONString(needFlights)+"】");

		}

		//搜索热门景点
		getHotMpScenicSpots(model);

		map.put("list", needFlights);

		model.addAttribute("qo", qo);

		return JSON.toJSONString(map);
	}

	/**
	 * 页面点击预定,查询航班政策
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/flight-book")
	@ResponseBody
	public String flightBook(Model model,JPPolicyGNQO qo){

		Map<String, Object> map=new HashMap<String, Object>();
		String message = "";
		try {

			String orderEncryptString = java.net.URLDecoder.decode(qo.getEncryptString(), "UTF-8");
			qo.setEncryptString(orderEncryptString);
			HgLogger.getInstance().info(devName, "页面点击预定,查询航班政策,加密字符串:" + orderEncryptString);
			//调接口查询航班政策信息
			YXFlightDTO yXFlightDTO = flightService.queryAirPolicy(qo.getEncryptString());

			map.put("yXFlightDTO",yXFlightDTO);
			HgLogger.getInstance().info(devName, "页面点击预定,查询航班政策,查询结果【" + JSON.toJSONString(yXFlightDTO)+"】");
		}catch (ShowMessageException e){
			map.put("yXFlightDTO",new YXFlightDTO());
			map.put("message",e.getMessage());
			HgLogger.getInstance().error(devName, "页面点击预定,查询航班政策,异常:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return JSON.toJSONString(map);
	}

	/**
	 * 能预定,则跳转到填写订单页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/flight-order")
	public Object flightOrder(Model model,JPPolicyGNQO qo,HttpServletRequest request){

		String message = "";
		Map<String,Object> map = new HashMap<String, Object>();
		String orderEncryptString = qo.getEncryptString();

		HgLogger.getInstance().info(devName, "跳转到填写订单页面:加密字符串:" + orderEncryptString);

		//获取当前登陆用户
		UserDTO user = getUserBySession(request);
		if(user == null){
			return new RedirectView("/user/login", true);
		}

		if(StringUtils.isBlank(orderEncryptString)){
			return "yxjp/error.html";
		}
		YXFlightDTO yXFlightDTO = null;
		try {
			if(StringUtils.isNotBlank(user.getId())){

				try {
					if(!orderEncryptString.contains("+")){
						orderEncryptString = java.net.URLDecoder.decode(orderEncryptString, "UTF-8");
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				//调接口查询航班政策信息
				yXFlightDTO = flightService.queryAirPolicy(orderEncryptString);

				TravelerQO travelerQO = new TravelerQO();
				travelerQO.setFromUserId(user.getId());
				List<Traveler> travelers = travelerLocalService.queryList(travelerQO);
				model.addAttribute("travelers",travelers);

				HgLogger.getInstance().info(devName, "跳转到填写订单页面:用户ID:" + user.getId());
			}
		}catch (ShowMessageException e){
			message = e.getMessage();
			HgLogger.getInstance().error(devName, "跳转到填写订单页面:异常:" + message);
			return "yxjp/error.html";
		}

		//*******计算飞行的小时和分钟数*******//
		Long flyTime = yXFlightDTO.getBaseInfo().getEndTime().getTime() - yXFlightDTO.getBaseInfo().getStartTime().getTime();
		long flyHours = flyTime / (60 * 60 * 1000) % 24;
		long flyMinutes = flyTime / (60 * 1000) % 60;

		model.addAttribute("orderEncryptString", yXFlightDTO.toEncryptString());
		model.addAttribute("yXFlightDTO", yXFlightDTO);
		model.addAttribute("userType",user.getBaseInfo().getType());
		model.addAttribute("flyHours",flyHours);
		model.addAttribute("flyMinutes", flyMinutes);

		HgLogger.getInstance().info(devName, "跳转到填写订单页面:orderEncryptString:" + yXFlightDTO.toEncryptString());
		HgLogger.getInstance().info(devName, "跳转到填写订单页面:航班政策信息【" + JSON.toJSONString(yXFlightDTO) + "】");

		return "yxjp/jp_order.html";
	}

	/**
	 * 获取所有公司
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/companys")
	@ResponseBody
	public String companys(Model model,HttpServletRequest request){

		//获取当前登陆用户
		UserDTO user = getUserBySession(request);
		List<CompanyDTO> companys = new ArrayList<CompanyDTO>();

		if(user != null){

			HslCompanyQO qo = new HslCompanyQO();
			qo.setUserId(user.getId());

			//公司列表
			companys = companyService.getCompanys(qo);

			HgLogger.getInstance().info(devName, "获取所有公司,企业ID:" + user.getId());
			HgLogger.getInstance().info(devName,"获取所有公司,查询结果详情【"+JSON.toJSONString(companys)+"】");
		}

		return  JSON.toJSONString(companys);

	}

	/**
	 * 根据公司ID 获取部门
	 * @param model
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/depMents")
	@ResponseBody
	public String depMent(Model model,String companyId){

		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		List<DepartmentDTO> departmentList =new ArrayList<DepartmentDTO>();

		HgLogger.getInstance().info(devName, "根据公司ID 获取部门列表,公司ID:" + companyId);

		if(StringUtils.isNotBlank(companyId)){

			HslCompanyQO hslCompanyQO = new HslCompanyQO();
			hslCompanyQO.setId(companyId);
			hslDepartmentQO.setCompanyQO(hslCompanyQO);

			// 获取指定公司的所有部门列表
			departmentList = companyService.getDepartments(hslDepartmentQO);

			HgLogger.getInstance().info(devName, "根据公司ID 获取部门列表,查询结果详情【" + JSON.toJSONString(departmentList) + "】");
		}

		return JSON.toJSONString(departmentList);
	}

	/**
	 * 根据部门ID 查询员工列表
	 * @param model
	 * @param depmentId
	 * @return
	 */
	@RequestMapping(value = "/members")
	@ResponseBody
	public String members(Model model,String depmentId){

		HslMemberQO hslMemberQO = new HslMemberQO();
		List<MemberDTO> memberList =new ArrayList<MemberDTO>();

		HgLogger.getInstance().info(devName, "根据部门ID 查询员工列表,部门ID:" + depmentId);

		if(StringUtils.isNotBlank(depmentId)){

			hslMemberQO.setDepartmentId(depmentId);
			// 获取指定部门的所有员工列表
			memberList = companyService.getMembers(hslMemberQO);
			HgLogger.getInstance().info(devName, "根据部门ID 查询员工列表,查询结果详情【" + JSON.toJSONString(memberList) + "】");
		}

		return JSON.toJSONString(memberList);
	}
	/**
	 * 创建机票订单
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/create-jporder")
	@ResponseBody
	public Object createJpOrder(Model model, CreateYXJPOrderCommand command, String orderEncryptString, HttpServletRequest request){

		String orderId = "";
		String message = "";
		//获取用户信息
		UserDTO user = getUserBySession(request);
		if(user != null){

			//设置来源用户
			command.setFromUserId(user.getId());

			//设置联系人
			String linkMobile = request.getParameter("linkMobile");
			YXLinkmanDTO linkman = new YXLinkmanDTO();
			linkman.setLinkMobile(linkMobile);
			command.setLinkman(linkman);

			//解密航班政策信息
			YXFlightDTO yXFlightDTO = YXFlightDTO.valueOfEncryptString(orderEncryptString);
			if(yXFlightDTO == null){
				HgLogger.getInstance().error(devName, "YX机票订单创建失败:解密航班政策失败");
				message ="F1";
			}
			command.setFlightInfo(yXFlightDTO);

			//创建机票订单
			try {

				orderId = orderLocalService.createOrder(command);
				message = "订单ID,"+orderId;
			}catch (ShowMessageException e){
				message= e.getMessage();
			}
		}else{
			message ="F2";
			HgLogger.getInstance().error(devName, "YX机票订单创建失败:用户未登陆");
		}
		return JSON.toJSONString(message);
	}

	/**
	 * 跳转到支付页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay-order-pag")
	public String payOrderPage(Model model,String orderId,HttpServletRequest request){

		HgLogger.getInstance().info(devName, "YX机票订单创建成功:订单ID:" + orderId);

		if(StringUtils.isBlank(orderId)){
			HgLogger.getInstance().error(devName, "YX机票订单创建失败:订单ID:为空");
			return "yxjp/error.html";
		}

		request.getSession().setAttribute(ORDER_NO_FOR_COUPONS, orderId);

		//查询机票订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setId(orderId);
		qo.setFetchPassengers(true);
		YXJPOrder jPOrder = orderLocalService.queryUnique(qo);

		//*******计算飞行的小时和分钟数*******//
		Long flyTime = jPOrder.getFlight().getBaseInfo().getEndTime().getTime() - jPOrder.getFlight().getBaseInfo().getStartTime().getTime();
		long flyHours = flyTime / (60 * 60 * 1000) % 24;
		long flyMinutes = flyTime / (60 * 1000) % 60;

		HgLogger.getInstance().info(devName, "YX机票订单创建成功:飞行时间：" + flyHours + "小时" + flyMinutes + "分钟");

		model.addAttribute("orderId",orderId);
		model.addAttribute("jPOrder",jPOrder);
		model.addAttribute("orderStatusMap", HSLConstants.YXJPOrderPassengerTicket.yxOrderStatusMap);
		model.addAttribute("flyHours",flyHours);
		model.addAttribute("flyMinutes",flyMinutes);

		return "yxjp/jp_pay_order.html";
	}

	/**
	 * 查询卡券
	 * @param request
	 * @param model
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/query-coupons")
	@ResponseBody
	public String queryCoupons(HttpServletRequest request,Model model,String orderNo){

		List<CouponDTO> needCoupons = new ArrayList<CouponDTO>();

		if(StringUtils.isNotBlank(orderNo)){
			//获取用户信息
			UserDTO user = getUserBySession(request);
			if(user != null){
				//查询机票订单
				YXJPOrderQO qo = new YXJPOrderQO();
				qo.setOrderNo(orderNo);
				qo.setFetchPassengers(true);
				YXJPOrder jPOrder = orderLocalService.queryUnique(qo);
				if(jPOrder != null && jPOrder.getPayInfo() != null){

					//查询当前订单未占用的卡券
					HslCouponQO couponQO = new HslCouponQO();
					couponQO.setUserId(user.getId());
					couponQO.setUsed(false);
					couponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);

					List<CouponDTO> coupons = couponService.queryList(couponQO);

					HgLogger.getInstance().info(devName, "查询卡券,查询结果未占用卡券数量:" + coupons.size());
					for (CouponDTO coupon : coupons){
						//判断当前卡券是否能使用
						boolean canUse = couponCanUse(coupon,jPOrder);
						if(canUse){
							needCoupons.add(coupon);
						}
					}
					//查询当前订单占用的卡券
					HslCouponQO couponQo = new HslCouponQO();
					couponQo.setUserId(user.getId());
					couponQo.setOrderId(orderNo);
					couponQo.setUsedKind(CouponUseConditionInfo.USED_KIND_JP);
					couponQo.setUsed(true);
					couponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
					List<CouponDTO> consumeCoupons = couponService.queryList(couponQo);
					HgLogger.getInstance().info(devName, "查询卡券,查询结果占用卡券数量:" + consumeCoupons.size());
					for (CouponDTO consumeCoupon : consumeCoupons){
						needCoupons.add(consumeCoupon);
					}
				}
			}else{
				HgLogger.getInstance().error(devName,"查询卡券,当前用户未登陆");
			}
		}

		HgLogger.getInstance().info(devName,"查询卡券,查询结果满足条件卡券数量:"+needCoupons.size());

		return JSON.toJSONString(needCoupons);
	}

	/**
	 * 支付订单
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay-order")
	public String payOrder(Model model,PayToYXJPOrderCommand command,HttpServletRequest request){

		String message = "";
		String payFormHtml = "";
		String url ="yxjp/success.html";
		//获取用户信息
		UserDTO user = getUserBySession(request);
		if(user != null){

			//查询机票订单
			YXJPOrderQO qo = new YXJPOrderQO();
			qo.setId(command.getOrderId());
			qo.setFetchPassengers(true);
			YXJPOrder jPOrder = orderLocalService.queryUnique(qo);
			model.addAttribute("order",jPOrder);
			model.addAttribute("orderNo",command.getOrderId());

			command.setFromUserId(user.getId());
			try {
				payFormHtml = orderLocalService.payToOrder(command);
				if(StringUtils.isNotBlank(payFormHtml)){
					HgLogger.getInstance().error(devName,"支付订单,支付成功。支付宝传回表单信息+"+payFormHtml);
					model.addAttribute("payForm", payFormHtml);
					return "yxjp/pay-form.html";
				}
			} catch (CouponException e) {
				message = e.getMessage();
				HgLogger.getInstance().error(devName,"支付订单,发生异常。"+message);
			}catch (ShowMessageException e){
				message = e.getMessage();
				HgLogger.getInstance().error(devName,"支付订单,发生异常。"+message);
			}

		}else {
			HgLogger.getInstance().error(devName,"支付订单,发生异常。用户未登陆");
			url="yxjp/error.html";
		}

		if(StringUtils.isNotBlank(message)){
			HgLogger.getInstance().error(devName, "支付订单,发生异常。" + message);
			url="yxjp/error.html";
		}

		return url;
	}

	/**
	 * 跳转到成功页面
	 * @param model
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/success")
	public String success(Model model,String orderId){

		if(StringUtils.isBlank(orderId)){
			HgLogger.getInstance().error(devName,"跳转到成功页面,发生异常。查询机票订单ID为空");
			return "yxjp/error.html";
		}
		//查询机票订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setId(orderId);
		qo.setFetchPassengers(true);
		YXJPOrder jPOrder = orderLocalService.queryUnique(qo);

		if(jPOrder == null){
			HgLogger.getInstance().error(devName,"跳转到成功页面,发生异常。查询机票订单为空");
			return "yxjp/error.html";
		}

		model.addAttribute("order",jPOrder);
		model.addAttribute("orderId", orderId);

		String token = UUIDGenerator.getUUID();
		model.addAttribute("yxJpPayOrderToken", token);

		return "yxjp/success.html";
	}

	/**
	 * 我的订单页面 点击待支付,跳转到订单支付页面去
	 * @param model
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/pay-jporder")
	public String payJpOrder(Model model,String orderNo,HttpServletRequest request){

		//查询机票订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setId(orderNo);
		qo.setFetchPassengers(true);
		YXJPOrder jPOrder = orderLocalService.queryUnique(qo);
		if(jPOrder == null){
			return "yxjp/error.html";
		}

		model.addAttribute("jPOrder",jPOrder);
		model.addAttribute("orderStatusMap", HSLConstants.YXJPOrderPassengerTicket.yxOrderStatusMap);

		return "yxjp/jp_pay_order.html";
	}

	/**
	 * 验证卡券是否被使用
	 * @param model
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/volid-coupon")
	@ResponseBody
	public String volidCoupon(Model model,String orderNo,String couponIds){

		String message = "";
		if(StringUtils.isBlank(orderNo) || StringUtils.isBlank(couponIds)){
			HgLogger.getInstance().info(devName, "支付订单验证卡券否被使用,传入参数orderNo或者couponIds为空。");
			return message;
		}

		HgLogger.getInstance().info(devName,"支付订单验证卡券否被使用,参数【orderNo:"+orderNo+",couponIds:"+couponIds+"】");
		//查询机票订单
		YXJPOrderQO qo = new YXJPOrderQO();
		qo.setOrderNo(orderNo);
		YXJPOrder jPOrder = orderLocalService.queryUnique(qo);
		if(jPOrder == null){
			HgLogger.getInstance().info(devName,"支付订单验证卡券否被使用,查询机票为空。");
			return message;
		}
		//订单总价
		Double totalPrice = jPOrder.getPayInfo().getTotalPrice();
		String[] id = couponIds.split(",");

		BatchConsumeCouponCommand consumeCouponCommand = null;
		consumeCouponCommand = new BatchConsumeCouponCommand();
		consumeCouponCommand.setCouponIds(id);
		consumeCouponCommand.setOrderId(orderNo);
		consumeCouponCommand.setPayPrice(totalPrice);

		try {
			boolean flag = couponService.checkCoupon(consumeCouponCommand);
			if(flag){
				message ="success";
			}
			HgLogger.getInstance().info(devName, "支付订单验证卡券否被使用,结果：" + flag);
		} catch (CouponException e) {
			message = e.getMessage();
			HgLogger.getInstance().error(devName,"支付订单验证卡券否被使用,发生异常："+message);
		}

		return message;
	}


	/**
	 * 检测用户输入,出发地和目的地是否合法
	 * auth:hgg
	 * @param qo
	 * @return
	 */
	private boolean hasCityAirCode(JPFlightGNQO qo){

		boolean hasCityAirCode = true;
		CityAirCodeQO cityaircodeqo = new CityAirCodeQO();

		//出发地
		String orgCity = qo.getOrgCity();
		cityaircodeqo.setName(orgCity);
		CityAirCode orgCityAirCode = jpFlightLocalService.queryLocalCityAirCode(cityaircodeqo);
		if(orgCityAirCode == null || StringUtils.isBlank(orgCityAirCode.getAirCode())){

			HgLogger.getInstance().error(devName,"查询机票,检测用户输入,出发地:不合法");
			hasCityAirCode = false;
		}else{
			//赋值 查询需要的机场三字码
			qo.setOrgCity(orgCityAirCode.getAirCode());
		}

		//目的地
		String dstCity = qo.getDstCity();
		cityaircodeqo.setName(dstCity);
		CityAirCode DstCityAirCode = jpFlightLocalService.queryLocalCityAirCode(cityaircodeqo);
		if(DstCityAirCode == null || StringUtils.isBlank(DstCityAirCode.getAirCode())){

			HgLogger.getInstance().error(devName,"查询机票,检测用户输入,目的地:不合法");
			hasCityAirCode = false;
		}else{
			//赋值 查询需要的机场三字码
			qo.setDstCity(DstCityAirCode.getAirCode());
		}

		return hasCityAirCode;
	}

	/**
	 * 筛选满足条件的航班
	 * @param flights
	 * @return
	 */
	private List<YXResultFlightDTO> needFlight(List<YXResultFlightDTO> flights,JPFlightGNQO qo,String flightTime)throws ParseException{

		List<YXResultFlightDTO> needFlights = new ArrayList<YXResultFlightDTO>();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");

		//航空公司
		String airCompany = qo.getAirCompany();

		if(CollectionUtils.isEmpty(flights)){
			return flights;
		}

		if(StringUtils.equals("null",flightTime) && StringUtils.isBlank(airCompany)){
			return flights;
		}

		for (YXResultFlightDTO flight:flights) {
			//起飞时间为空,航空公司不为空
			if(StringUtils.equals("null", flightTime) && StringUtils.isNotBlank(airCompany)){

				if(StringUtils.equals(flight.getAirCompanyName(),airCompany)){
					HgLogger.getInstance().info(devName,"查询航班,起飞时间为空,航空公司【"+flight.getAirCompanyName()+"】");
					needFlights.add(flight);
				}
				//起飞时间不为空,航空公司为空
			}else if(!StringUtils.equals("null", flightTime) && StringUtils.isBlank(airCompany)){
				if(checkTime(flightTime,flight.getStartTime())){
					HgLogger.getInstance().info(devName,"查询航班,起飞时间"+flight.getStartTime()+",航空公司为空");
					needFlights.add(flight);
				}
				//起飞时间不为空,航空公司不为空
			}else if(!StringUtils.equals("null", flightTime) && StringUtils.isNotBlank(airCompany)){
				if(checkTime(flightTime,flight.getStartTime())){
					if(StringUtils.equals(flight.getAirCompanyName(),airCompany)){
						HgLogger.getInstance().info(devName,"查询航班,起飞时间"+flight.getStartTime()+",航空公司【"+flight.getAirCompanyName()+"】");
						needFlights.add(flight);
					}
				}
			}
		}

		return needFlights;
	}


	/**比较时间
	 *
	 * @param startTime（页面传入参数,起飞时间），flightStartTime（飞机出发时间）
	 * @return
	 * @throws ParseException
	 */
	private boolean checkTime(String startTime,Date flightStartTime)throws ParseException{

		boolean isExists;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String flightTime = df.format(flightStartTime);
		Date sDate = df.parse("06:00");
		Date TDate = df.parse("12:00");
		Date BDate = df.parse("18:00");
		Date TSDate = df.parse("24:00");

		Date flightTimeN = df.parse(flightTime);

		//上午
		Date dateBefor = sDate;
		Date dateAfter = TDate;

		if(StringUtils.equals("下午(12:00-18:00)", startTime)){
			dateBefor = TDate;
			dateAfter = BDate;
		}else if(StringUtils.equals("晚上(18:00-24:00)",startTime)){
			dateBefor = BDate;
			dateAfter = TSDate;
		}
		//flightStartTime：飞机起飞时间,startTime:页面传递的起飞时间，dateAfter，dateBefor：区间时间
		if (flightTimeN.getTime()>=dateBefor.getTime() && flightTimeN.getTime()<dateAfter.getTime()) {
			isExists = true;
		} else {
			isExists = false;
		}
		return isExists;
	}

	/**
	 * 判断卡券能否使用
	 * @param couponDTO
	 * @param jPOrder
	 * @return
	 */
	private boolean couponCanUse(CouponDTO couponDTO,YXJPOrder jPOrder){


		//订单总价
		Double orderPrice =jPOrder.getPayInfo().getTotalPrice();
		//订单编号
		String orderNo = jPOrder.getBaseInfo().getOrderNo();

		if(couponDTO.getStatus().getCurrentValue() != CouponStatus.TYPE_NOUSED){
			return  false;
		}

		// 检查适用类型
		CouponUseConditionInfoDTO  useCondition = couponDTO.getBaseInfo().getCouponActivity().getUseConditionInfo();

		// 检查订单总价是否达到使用卡券的条件
		if (useCondition.getCondition() == 1 && orderPrice < useCondition.getMinOrderPrice()) {
			HgLogger.getInstance().error(devName,"查询卡券,当前订单金额不满足当前卡券的最低金额，卡券ID:"+couponDTO.getId());
			return false;
		}

		Date now = new Date();
		// 检查并更正卡券状态
		if (couponDTO.getConsumeOrder() == null && now.after(useCondition.getEndDate())) {
			couponDTO.getStatus().setCurrentValue(CouponStatus.TYPE_OVERDUE);
		}
		// 检索是否在有效期
		if (now.before(useCondition.getBeginDate()) || now.after(useCondition.getEndDate())) {
			HgLogger.getInstance().error(devName,"查询卡券,当前卡券没在有效期时间内，卡券ID:"+couponDTO.getId());
			return false;
		}

		// 机票订单是否可用
		if (!useCondition.getUsedKind().contains("1")){
			HgLogger.getInstance().error(devName,"查询卡券,机票订单不能使用当前卡券，卡券ID:"+couponDTO.getId());
			return false;
		}

		// 检查订单快照是否为空或已占用的订单快照就是当前订单
		if (couponDTO.getConsumeOrder() == null || (couponDTO.getConsumeOrder().getOrderType().equals(ConsumeOrderSnapshot.ORDERTYPE_YXJP) && couponDTO.getConsumeOrder().getOrderId().equals(orderNo))) {
			return true;
		}

		return  true;
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
			HgLogger.getInstance().info("hgg","热门景点数据，查询数据：" + JSON.toJSONString(hotScenicMap));
			model.addAttribute("hotScenicMap", hotScenicMap);
		} catch (MPException e) {
			hotScenicMap = new HashMap();
			hotScenicMap.put("dto", new ArrayList<HotScenicSpotDTO>());
			hotScenicMap.put("count", 0);
			model.addAttribute("hotScenicMap", hotScenicMap);
			e.printStackTrace();
			HgLogger.getInstance().error("hgg","JpController->main->exception:"+ HgLogger.getStackTrace(e));
		}
	}

}
