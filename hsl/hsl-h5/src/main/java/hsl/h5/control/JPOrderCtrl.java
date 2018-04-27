package hsl.h5.control;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.domain.model.coupon.CouponStatus;
import hsl.h5.base.result.jp.JPCreateOrderResult;
import hsl.h5.base.result.jp.JPQueryFlightResult;
import hsl.h5.base.result.jp.JPQueryOrderResult;
import hsl.h5.base.result.jp.JPQueryPolicyResult;
import hsl.h5.base.utils.HttpTracker;
import hsl.h5.base.utils.OpenidTracker;
import hsl.pojo.command.JPOrderCreateCommand;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.FlightDTO;
import hsl.pojo.dto.jp.FlightPolicyDTO;
import hsl.pojo.dto.jp.JPOrderCreateDTO;
import hsl.pojo.dto.jp.JPOrderDTO;
import hsl.pojo.dto.jp.JPOrderStatusConstant;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.api.jp.JPService;
import hsl.spi.inter.company.CompanyService;
import hsl.spi.inter.user.UserService;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
import slfx.api.v1.request.qo.jp.JPFlightQO;
import slfx.api.v1.request.qo.jp.JPPolicyQO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * 机票订单管理Action
 * @author 胡永伟
 */
@Controller
@RequestMapping("jpo")
public class JPOrderCtrl extends HslCtrl {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JPService jpService;
	
	@Autowired
	private  CouponService   couponService;
	
	@Resource
	private CompanyService companyService;

	
	/**
	 * 机票下单
	 * @param command  航班查询需要的参数command
	 * @return
	 */
	@RequestMapping("settle")
	public ModelAndView settle(HttpServletRequest request,JPFlightQO command, String classCode, String startCityName, String endCityName, String discount) {
		ModelAndView mav = new ModelAndView("jporder/settle");
		try {
				//获取微信跟踪器
				mav.addObject("openid", OpenidTracker.get());
				mav.addObject("condition", command);
				
				//从request中取得userId
				String userId = getUserId(request);
				
				HslCompanyQO hslCompanyQO = new HslCompanyQO();
				hslCompanyQO.setUserId(userId);//这里替换成用户的id，公司查询条件
				List<CompanyDTO> companyList = companyService.getCompanys(hslCompanyQO);
				mav.addObject("companyList", companyList);
				mav.addObject("userType",getUserByUserId(userId).getBaseInfo().getType());
				
				//通过userId取得user实体对象，从而获得用户的手机号码
				UserDTO  userDto = getUserByUserId(userId);
				if(userDto!=null){//如果查询得到的userDto不为空则取得用户的手机号码
					mav.addObject("curMobile",userDto.getContactInfo().getMobile());
				}
			
				//查询航班，得到一条航班记录
				List<FlightDTO> flightDTOs= jpService.queryFlight(command);
				JPQueryFlightResult jpQueryFlightResult = new JPQueryFlightResult();
				FlightDTO  flight  =  new  FlightDTO();
				
				if (flightDTOs == null) {
					jpQueryFlightResult.setMessage("失败！");
					jpQueryFlightResult.setResult(JPQueryFlightResult.RESULT_CODE_FAIL);
					//日志信息
					HgLogger.getInstance().info("liusong", "JPOrderCtrl->settle->queryFlight[航班查询失败！查询的QO]:" +JSON.toJSONString(command));
				} else {
					//日志信息
					HgLogger.getInstance().info("liusong", "JPOrderCtrl->settle->queryFlight[航班查询成功！]:" +JSON.toJSONString(flightDTOs));
					jpQueryFlightResult.setFlightList(flightDTOs);
					jpQueryFlightResult.setTotalCount(flightDTOs.size());
					flight = flightDTOs.get(0);
				}
				
				
				//查询航班政策得到航班政策对象
				JPPolicyQO jppCommand = new JPPolicyQO();
				jppCommand.setFlightNo(command.getFlightNo());
				jppCommand.setClassCode(classCode);
				jppCommand.setDepartDate(command.getDate());
				
				
				JPQueryPolicyResult  jpQueryPolicyResult = new JPQueryPolicyResult();//存放航班政策的result结果集
				FlightPolicyDTO policyDto = jpService.queryFlightPolicy(jppCommand);//查询航班政策
				
				if (policyDto == null) {
					jpQueryPolicyResult.setMessage("失败！");
					jpQueryPolicyResult.setResult(JPQueryPolicyResult.RESULT_CODE_FAIL);
					//日志信息
					HgLogger.getInstance().info("liusong", "JPOrderCtrl->settle->queryFlightPolicy[航班政策查询失败！查询的QO]:" +JSON.toJSONString(jppCommand));
				} else {
					jpQueryPolicyResult.setMessage("成功");
					jpQueryPolicyResult.setFlightPolicyDTO(policyDto);
					//日志信息
					HgLogger.getInstance().info("liusong", "JPOrderCtrl->settle->queryFlightPolicy[航班政策查询成功！]:" +JSON.toJSONString(policyDto));
				}
				
				mav.addObject("userId", userId);
			    mav.addObject("policy", policyDto);
			    mav.addObject("flight", flight);
				mav.addObject("classCode", classCode);
				String params = "from=" + command.getFrom() + "&arrive=" + command.getArrive() +
						"&startCityName=" + startCityName + "&endCityName=" + endCityName;
				mav.addObject("params", params);
				mav.addObject("discount", discount);
		} catch (Exception e) {
			//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->settle->queryFlightPolicy[航班政策查询失败！错误异常]:" +HgLogger.getStackTrace(e));
				log.error("hsl.err", e);
				mav.setViewName("error");
		}
				return mav;
	}
	
	/**
	 * 订单的创建
	 * @param command  订单创建需要的参数command
	 * @return
	 */
	@RequestMapping("confirm")
	public void confirm(String command, PrintWriter out) {
		try {
			//将command内容编码用UTF-8格式转换，防止乱码
			command = URLDecoder.decode(command, "UTF-8");
			
			//创建订单需要的command
			JPOrderCreateCommand createCommand = JSONObject.parseObject(command, JPOrderCreateCommand.class);
			
			JPCreateOrderResult    jpCreateOrderResult = new JPCreateOrderResult();//创建机票订单的result结果集
			JPOrderCreateDTO  jpOrderCreateDto = jpService.orderCreate(createCommand);//创建机票订单
			
			if (jpOrderCreateDto == null) {
				jpCreateOrderResult = new JPCreateOrderResult();
				jpCreateOrderResult.setMessage("失败！");
				jpCreateOrderResult.setResult(JPCreateOrderResult.RESULT_CODE_FAIL);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->confirm->orderCreate[机票创建订单失败！所传的参数]："+JSON.toJSONString(createCommand));
			} else {
				jpCreateOrderResult = BeanMapperUtils.getMapper().map(jpOrderCreateDto, JPCreateOrderResult.class);
				jpCreateOrderResult.setMessage("成功");
				jpCreateOrderResult.setResult(JPCreateOrderResult.RESULT_CODE_OK);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->confirm->orderCreate[机票创建订单成功！]：" +JSON.toJSONString(jpCreateOrderResult));
			}
			
			//返回结果
			out.print(JSON.toJSONString(jpCreateOrderResult));
		} catch (Exception e) {
			//日志信息
			HgLogger.getInstance().info("liusong", "JPOrderCtrl->confirm->orderCreate[机票创建订单失败！错误异常]"+HgLogger.getStackTrace(e));
			out.print(getHslErrResponseJsonStr(e));
		}
	}
	
	/**
	 * 订单的支付
	 * 
	 * @param orderId 订单号
	 * @return
	 */
	@RequestMapping("pay")
	public  ModelAndView pay(HttpServletRequest request,String orderId,String youh,String id) {
		ModelAndView mav = new ModelAndView("jporder/pay");
		try {
			mav.addObject("openid", OpenidTracker.get());//获取微信跟踪器
			
			//根据订单号获取一条订单记录
			HslJPOrderQO  hslJpOrderQO  = new  HslJPOrderQO();
			hslJpOrderQO.setDealerOrderCode(orderId);
			hslJpOrderQO.setPageNo(1);
			hslJpOrderQO.setPageSize(1);
			
			JPQueryOrderResult   jpQueryOrderResult = new  JPQueryOrderResult();
			JPOrderDTO order  =  new  JPOrderDTO();
			List<JPOrderDTO> jpOrderDTOs  =   jpService.queryOrder(hslJpOrderQO);
			if (jpOrderDTOs == null) {
				jpQueryOrderResult.setMessage("失败！");
				jpQueryOrderResult.setResult(JPQueryOrderResult.RESULT_CODE_FAIL);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->pay->queryOrder[根据订单号查询订单失败！查询的QO]:" +JSON.toJSONString(hslJpOrderQO));
			} else {
				jpQueryOrderResult.setJpOrders(jpOrderDTOs);
				jpQueryOrderResult.setTotalCount(jpOrderDTOs.size());
				order = jpOrderDTOs.get(0);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->pay->queryOrder[根据订单号查询订单成功！]:" +JSON.toJSONString(jpQueryOrderResult));
			}
			
			if(order!=null){//如果返回的订单dto不为空
				String start = order.getFlightDTO().getStartCity();//获取机票的起飞城市
				String end = order.getFlightDTO().getEndCity();//获取机票的到达城市
				Integer num = order.getPassangers().size();//获取机票乘客的数量
				StringBuilder subject = new StringBuilder("飞机票 [");
				subject.append(start).append("-").append(end).append("]*").append(num);
				mav.addObject("subject", subject.toString());
				mav.addObject("order", order);
			}
			
			//判断是否是由选择卡券页面回调到支付页，如果是，则此时用户的看到的实际支付额应该为减去卡券面值之后的值
			double youhui;
			if(youh==null){
				 youhui=0.00;
				 mav.addObject("youhui", youhui);
			}else{
				youhui=Double.parseDouble(youh);
				mav.addObject("youhui", youhui);
				mav.addObject("couponId", id);
			}
			
			//根据用户的id号获取用户所拥有的可用的卡券
			HslCouponQO  hslCouponQO = new HslCouponQO();
			hslCouponQO.setUserId(getUserId(request));
			// 只查询未使用的卡券并且满足使用条件的卡券
			hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
			hslCouponQO.setOrderPrice(order.getPayAmount());
			try {
				if (hslCouponQO.getUserId() != null) {
					List<CouponDTO> couponList = couponService.queryList(hslCouponQO);
					mav.addObject("couponList", couponList);
				}
			} catch (Exception e) {
				HgLogger.getInstance().error("liusong","根据登录用户的userid查询该用户拥有的卡券列表失败" + e.getStackTrace());
			}
		} catch (Exception e) {
			//日志信息
			   HgLogger.getInstance().info("liusong", "JPOrderCtrl->pay->queryOrder[根据订单号查询订单失败！错误异常]:" +HgLogger.getStackTrace(e));
				log.error("hsl.err", e);
				mav.setViewName("error");
		}
		String couponError=request.getParameter("couponError");
		mav.addObject("couponError", couponError);
		return mav;
	}
	
	/**
	 * 机票订单的数据api
	 * @param request
	 * @param out
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("ajaxList")
	public void ajaxList(HttpServletRequest request, PrintWriter out,Integer pageNo, Integer pageSize) {
		try {
			JPQueryOrderResult   jpQueryOrderResult = new  JPQueryOrderResult();
			
			HslJPOrderQO  hslJpOrderQO  = new  HslJPOrderQO();
			hslJpOrderQO.setUserId(getUserId(request));//设置userid
			hslJpOrderQO.setPageNo(pageNo);
			hslJpOrderQO.setPageSize(pageSize);
			
			//根据UserId获取订单列表
			List<JPOrderDTO> jpOrderDTOs = jpService.queryOrder(hslJpOrderQO);
			if (jpOrderDTOs == null) {
				jpQueryOrderResult.setMessage("失败！");
				jpQueryOrderResult.setResult(JPQueryOrderResult.RESULT_CODE_FAIL);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->ajaxList->queryOrder[根据UserId获取订单列表失败！查询的QO]:" +JSON.toJSONString(hslJpOrderQO));
			} else {
				jpQueryOrderResult.setJpOrders(jpOrderDTOs);
				jpQueryOrderResult.setTotalCount(jpOrderDTOs.size());
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->ajaxList->queryOrder[根据UserId获取订单列表成功！]:" +JSON.toJSONString(jpQueryOrderResult));
			}
			
			//返回结果
			out.print(JSON.toJSONString(jpQueryOrderResult));
		} catch (Exception e) {
			//日志信息
			HgLogger.getInstance().info("liusong", "JPOrderCtrl->ajaxList->queryOrder[根据UserId获取订单列表失败！查询的QO]:" +HgLogger.getStackTrace(e));
			out.print(getHslErrResponseJsonStr(e));
		}
	}
	
	@RequestMapping("success")
	public ModelAndView success(String orderId,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("jporder/success");
		try {
			mav.addObject("openid", OpenidTracker.get());
			
			//根据订单号获取一条订单记录
			HslJPOrderQO  hslJpOrderQO  = new  HslJPOrderQO();
			hslJpOrderQO.setDealerOrderCode(orderId);
			hslJpOrderQO.setPageNo(1);
			hslJpOrderQO.setPageSize(1);
			
			JPQueryOrderResult   jpQueryOrderResult = new  JPQueryOrderResult();//订单查询结果集
			JPOrderDTO order  =  new  JPOrderDTO();//订单dto
			List<JPOrderDTO> jpOrderDTOs  =   jpService.queryOrder(hslJpOrderQO);//查询订单列表
			if (jpOrderDTOs == null) {
				jpQueryOrderResult.setMessage("失败！");
				jpQueryOrderResult.setResult(JPQueryOrderResult.RESULT_CODE_FAIL);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->pay->queryOrder[预订结果页，根据订单号查询订单失败！查询的QO]:" +JSON.toJSONString(hslJpOrderQO));
			} else {
				jpQueryOrderResult.setJpOrders(jpOrderDTOs);
				jpQueryOrderResult.setTotalCount(jpOrderDTOs.size());
				order = jpOrderDTOs.get(0);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->pay->queryOrder[预订结果页，根据订单号查询订单成功！]:" +JSON.toJSONString(hslJpOrderQO));
			}
			
			if(order!=null){//如果返回的订单dto不为空
				String start = order.getFlightDTO().getStartCity();//获取机票的起飞城市
				String end = order.getFlightDTO().getEndCity();//获取机票的到达城市
				Integer num = order.getPassangers().size();//获取机票乘客的数量
				StringBuilder subject = new StringBuilder("飞机票 [");
				subject.append(start).append("-").append(end).append("]*").append(num);
				mav.addObject("subject", subject.toString());
				mav.addObject("order", order);
			}
		} catch (Exception e) {
			//日志信息
			HgLogger.getInstance().info("liusong", "JPOrderCtrl->pay->queryOrder[预订结果页，根据订单号查询订单失败！错误异常]:" +HgLogger.getStackTrace(e));
			log.error("hsl.err", e);
			mav.setViewName("error");
		}
		return mav;
	}
	
	/**
	 * 获取订单详情
	 * @param orderId
	 * @return
	 */
	@RequestMapping("detail")
	public ModelAndView detail(String orderId) {
		ModelAndView mav = new ModelAndView("jporder/jpDetail");
		try {
			mav.addObject("openid", OpenidTracker.get());
			UserDTO user = getUserByUserId(getUserId(HttpTracker.getRequest()));
			mav.addObject("userDTO", user);
			mav.addObject("statusMap",JSON.toJSONString(JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP));
			//根据订单号获取一条订单记录
			HslJPOrderQO  hslJpOrderQO  = new  HslJPOrderQO();
			hslJpOrderQO.setDealerOrderCode(orderId);
			hslJpOrderQO.setPageNo(1);
			hslJpOrderQO.setPageSize(1);
			
			JPQueryOrderResult   jpQueryOrderResult = new  JPQueryOrderResult();//订单查询结果集
			JPOrderDTO order  =  new  JPOrderDTO();//订单dto
			List<JPOrderDTO> jpOrderDTOs  =   jpService.queryOrder(hslJpOrderQO);//查询订单列表
			if (jpOrderDTOs == null) {
				jpQueryOrderResult.setMessage("失败！");
				jpQueryOrderResult.setResult(JPQueryOrderResult.RESULT_CODE_FAIL);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->detail->queryOrder[获取订单详情时查询订单失败！查询的QO]:" +JSON.toJSONString(hslJpOrderQO));
			} else {
				jpQueryOrderResult.setJpOrders(jpOrderDTOs);
				jpQueryOrderResult.setTotalCount(jpOrderDTOs.size());
				order = jpOrderDTOs.get(0);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->detail->queryOrder[获取订单详情时查询订单成功！]:" +JSON.toJSONString(jpQueryOrderResult));
			}
			
			if(order!=null){//如果返回的订单dto不为空
				String start = order.getFlightDTO().getStartCity();//获取机票的起飞城市
				String end = order.getFlightDTO().getEndCity();//获取机票的到达城市
				Integer num = order.getPassangers().size();//获取机票乘客的数量
				StringBuilder subject = new StringBuilder("飞机票 [");
				subject.append(start).append("-").append(end).append("]*").append(num);
				mav.addObject("subject", subject.toString());
				String status = JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP.get(""+order.getStatus()+order.getPayStatus());
				if(status==null){
					status = "订单状态异常";
				}
				if(order.getPayStatus()!=null&&order.getPayStatus().equals(Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC))){
					mav.addObject("backprice",order.getReturnedPrice());
				}
				mav.addObject("PAYWAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);
				mav.addObject("NOPAY",JPOrderStatusConstant.SHOP_TICKET_NO_PAY);
				mav.addObject("status",status);
				mav.addObject("order", order);
			}
		} catch (Exception e) {
			//日志信息
			HgLogger.getInstance().info("liusong", "JPOrderCtrl->detail->queryOrder[获取订单详情时查询订单失败！错误异常]:" +HgLogger.getStackTrace(e));
			log.error("hsl.err", e);
			mav.setViewName("error");
		}
		return mav;
	}
	
	/**
	 * 获取用户所拥有的可用的卡券
	 * 参数：@param orderId  创建订单时得到的订单号
	 * @return
	 */
	@RequestMapping("coupon")
	public  ModelAndView  coupon(HttpServletRequest request,String orderId){
		ModelAndView mav = new ModelAndView("jporder/coupon");
		try{
			mav.addObject("openid", OpenidTracker.get());//获取微信跟踪器
			
			//根据订单号获取一条订单记录
			HslJPOrderQO  hslJpOrderQO  = new  HslJPOrderQO();
			hslJpOrderQO.setDealerOrderCode(orderId);
			hslJpOrderQO.setPageNo(1);
			hslJpOrderQO.setPageSize(1);
			
			JPQueryOrderResult   jpQueryOrderResult = new  JPQueryOrderResult();
			JPOrderDTO order  =  new  JPOrderDTO();
			List<JPOrderDTO> jpOrderDTOs  =   jpService.queryOrder(hslJpOrderQO);
			if (jpOrderDTOs == null) {
				jpQueryOrderResult.setMessage("失败！");
				jpQueryOrderResult.setResult(JPQueryOrderResult.RESULT_CODE_FAIL);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->pay->queryOrder[根据订单号查询订单失败！查询的QO]:" +JSON.toJSONString(hslJpOrderQO));
			} else {
				jpQueryOrderResult.setJpOrders(jpOrderDTOs);
				jpQueryOrderResult.setTotalCount(jpOrderDTOs.size());
				order = jpOrderDTOs.get(0);
				//日志信息
				HgLogger.getInstance().info("liusong", "JPOrderCtrl->pay->queryOrder[根据订单号查询订单成功！]:" +JSON.toJSONString(jpQueryOrderResult));
			}
			
			if(order!=null){//如果返回的订单dto不为空
				mav.addObject("order", order);
			}
			
			
			//根据用户的id号获取用户所拥有的可用的卡券
			HslCouponQO  hslCouponQO = new HslCouponQO();
			hslCouponQO.setUserId(getUserId(request));
			// 只查询未使用的卡券并且满足使用条件的卡券
			hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
			hslCouponQO.setOrderPrice(order.getPayAmount());
			try {
				if (hslCouponQO.getUserId() != null) {
					List<CouponDTO> couponList = couponService.queryList(hslCouponQO);
					mav.addObject("couponList", couponList);
				}
			} catch (Exception e) {
				HgLogger.getInstance().error("liusong","根据登录用户的userid查询该用户拥有的卡券列表失败" + e.getStackTrace());
			}
		}catch(Exception e){
			HgLogger.getInstance().info("liusong","JPOrderCtrl->coupon->queryOrder[根据订单号查询订单时失败！！]"+HgLogger.getStackTrace(e));
			log.error("hsl.err",e);
			mav.setViewName("error");
		}
		return mav;
	}
	
	/**
	 * 取消订单（包括退废票）
	 * @return
	 */
	@RequestMapping(value = "/cancel")
	@ResponseBody
	public String orderCancel(@RequestParam(value = "id", required = false) String id, //是订单的id而不是订单号
							  @RequestParam(value = "ticketNumbers", required = false) String[] ticketNumbers, 
							  @RequestParam(value = "refundType", required = false) String refundType, 
							  @RequestParam(value = "backPolicy", required = false) String backPolicy, 
							  @RequestParam(value = "backAmount", required = false) String backAmount, 
							  @RequestParam(value = "actionType", required = false) String actionType,
							  @RequestParam(value = "reason", required = false) String reason,
							  @RequestParam(value = "op", required = false) String op
			) {
		Map<String,String> map = new HashMap<String, String>();
		String success = "";
		String fail = "";
		if(StringUtils.isBlank(op)){
			if(StringUtils.isBlank(reason)){
				reason = "用户取消";
			}
			success = "订单取消成功";
			fail = "订单取消失败";
		}else if(op.equals("1")){
			success = "废票成功";
			fail = "废票失败";
		}else if(op.equals("2")){
			success = "退票成功";
			fail = "退票失败";
		}
		
			HslJPOrderQO qo = new HslJPOrderQO();
			qo.setId(id);
			JPOrderDTO jpOrder = jpService.queryOrderDetail(qo);//新写方法，脱离本地服务，需要测试
//			JPOrder jpOrder = jpOrderLocalService.queryOrderDetail(qo);
			
			if (jpOrder != null) {
				JPCancelTicketCommand command = new JPCancelTicketCommand();
				command.setOrderId(jpOrder.getOrderCode());
				command.setDealerOrderCode(jpOrder.getDealerOrderCode());
				command.setTicketNumbers(ArraysUtil.toStringWithSlice(ticketNumbers, ","));
				command.setName("H5USER");
				command.setRefundType(refundType);
				command.setBackPolicy(backPolicy);
				command.setBackAmount(backAmount);
				command.setActionType(actionType);
				command.setReason(reason);
				JPOrderDTO jpOrderDTO = jpService.cancelTicket(command);
				
				if (jpOrderDTO == null) {
					map.put("result", "-1");
					map.put("msg", fail);
				} else {
					map.put("result", "1");
					map.put("msg", success);
				}
			} else {
				map.put("result", "0");
				map.put("msg", "查不到该订单");
			}
			return JSON.toJSONString(map);
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
}