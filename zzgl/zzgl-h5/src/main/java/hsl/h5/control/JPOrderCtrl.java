package hsl.h5.control;
import hg.log.util.HgLogger;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.jp.DealerReturnInfo;
import hsl.h5.base.result.jp.JPQueryOrderResult;
import hsl.h5.base.utils.HttpTracker;
import hsl.h5.base.utils.OpenidTracker;
import hsl.h5.exception.HslapiException;
import hsl.pojo.command.jp.BookGNFlightCommand;
import hsl.pojo.dto.commonContact.CommonContactDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.plfx.JPQueryHighPolicyGNDTO;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.qo.CommonContact.CommonContactQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.pojo.qo.jp.plfx.JPPolicyGNQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.commonContact.CommonContactService;
import hsl.spi.inter.jp.JPService;
import hsl.spi.inter.user.UserService;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 机票订单管理Action
 * 
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
	private CouponService couponService;

	@Autowired
	private CommonContactService commonContactService;

	/**
	 * 机票下单
	 * 
	 * @param command
	 *            航班查询需要的参数command
	 * @return
	 */
	@RequestMapping("settle")
	public ModelAndView settle(HttpServletRequest request,BookGNFlightCommand command) {
		ModelAndView mav = new ModelAndView("jporder/settle");
		// 获取微信跟踪器
		mav.addObject("openid", OpenidTracker.get());
		
		
		
		// 查询当前用户常用联系人
		try {
			// 从request中取得userId
			String userId = getUserId(request);
			mav.addObject("userId", userId);
			CommonContactQO commonContactQO = new CommonContactQO();
			commonContactQO.setUserId(userId);
			List<CommonContactDTO> commonContactDTOList = commonContactService
					.queryList(commonContactQO);
			mav.addObject("contactList", commonContactDTOList);

			// 通过userId取得user实体对象，从而获得用户的手机号码
			UserDTO userDto = getUserByUserId(userId);
			if (userDto != null) {// 如果查询得到的userDto不为空则取得用户的手机号码
				mav.addObject("curMobile", userDto.getContactInfo().getMobile());
			}
		} catch (Exception e) {
			// 日志信息
			HgLogger.getInstance().info("rehnfeng","JPOrderCtrl->settle[用户常用联系人查询失败！错误异常]:"+ HgLogger.getStackTrace(e));
							
			mav.setViewName("error");
			return mav;
		}
				
		// 查询航班政策得到航班政策对象
		JPPolicyGNQO jPPolicyGNQO = new JPPolicyGNQO();
		try {

			jPPolicyGNQO.setEncryptString(command.getEncryptString());// 设置加密字符串
			jPPolicyGNQO.setTickType(DealerReturnInfo.JP_ORDER_TICKTYPE_ALL);// 票号类型
			HgLogger.getInstance().info("renfeng",
					"查询航班政策,查询的机票政策的QO：" + JSON.toJSONString(jPPolicyGNQO));
			JPQueryHighPolicyGNDTO flightPolicy = jpService
					.queryFlightPolicy(jPPolicyGNQO);
			HgLogger.getInstance().info("renfeng",
					"查询航班政策,返回一个航班政策dto：" + JSON.toJSONString(flightPolicy));
			if (flightPolicy.getPricesGNDTO() != null
					&& StringUtils.isNotBlank(flightPolicy.getPricesGNDTO()
							.getEncryptString())) {// 判断返回的航班政策是否为空
				mav.addObject("flightPolicy", flightPolicy);
			} else {
				HgLogger.getInstance().error("renfeng",
						"机票预定--->settle:查询航班政策失败：");
				mav.setViewName("error");
				return mav;
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("renfeng",
					"机票预定--->settle:查询航班政策失败：" + HgLogger.getStackTrace(e));
			mav.setViewName("error");
			return mav;
		}

		mav.addObject("command", command);
		mav.addObject("startDate",request.getParameter("startDate"));
		String params = "orgCity=" + command.getOrgCity() + "&dstCity=" + command.getDstCity() +
				"&startCityName=" + request.getParameter("startCityName") + "&endCityName=" + request.getParameter("endCityName");
		mav.addObject("params", params);
		return mav;
	}

	/**
	 * 订单的创建
	 * 
	 * @param command
	 *            订单创建需要的参数command
	 * @return
	 */
	@RequestMapping("confirm")
	public void confirm(HttpServletRequest request, String command,
			PrintWriter out) {
		Map<String, String> map = new HashMap<String, String>();

		try {

			// 将command内容编码用UTF-8格式转换，防止乱码
			command = URLDecoder.decode(command, "UTF-8");
			System.out.println(command);
			HgLogger.getInstance().info("renfeng","查询当前传入参数" + JSON.toJSONString(command));
					
			// 创建订单需要的command
			BookGNFlightCommand createCommand = JSONObject.parseObject(command,BookGNFlightCommand.class);
					
			System.out.println(JSON.toJSON(createCommand));

			// 登录用户
			String userId = getUserId(request);
			UserDTO user = getUserByUserId(userId);

			/*********************** 查询订单库中是否存在在当日该趟航班用该身份证start ****************/
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setIdNo(createCommand.getPassengers().get(0).getIdNo());// 身份证号
					
			flightOrderQO.setStartDate(createCommand.getStartTime());// 起飞日期
			flightOrderQO.setFlightNo(createCommand.getFlightNO());// 航班号
			flightOrderQO.setPayStatus(Integer
					.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));
			// 创建订单之前先进行一次订单查询，查询订单库中是否存在在当日该趟航班用该身份证就行下订单操作
			HgLogger.getInstance().info("renfeng","createOrder---->查询当前订单查询条件"+ JSON.toJSONString(flightOrderQO));
							
			List<FlightOrderDTO> jPBookOrderGNDTOList = jpService.queryOrder(flightOrderQO);
					
			HgLogger.getInstance().info("renfeng","查询一个订单，返回一个订单记录："+ JSON.toJSONString(jPBookOrderGNDTOList));
							
			/*********************** 查询订单库中是否存在在当日该趟航班用该身份证end *******************/
			FlightOrderDTO jporderdto = null;
			if (jPBookOrderGNDTOList.size() > 0) { // 用来限定一趟航班一个身份证号在当天只能预订一次
				// 日志信息
				HgLogger.getInstance().info("renfeng","JPOrderCtrl->confirm->orderCreate[机票创建订单失败！错误异常]");
				//out.print("一个身份证号在当天只能预订一次");
				map.put("message", "预定失败：一个身份证号在当天只能预订一次");
			} else {
				String startCityName=request.getParameter("startCityName");
				startCityName=URLDecoder.decode(startCityName,"UTF-8");
				createCommand.setOrgCity(startCityName);
				createCommand.setDstCity(request.getParameter("endCityName"));
				if (createCommand.getArrivalTerm().equals("--")) {
					createCommand.setArrivalTerm("T1");
				}
				createCommand.setUserID(user.getId());// 设置联系人的ID号为登录用户的ID号
				createCommand.setUserName(user.getAuthInfo().getLoginName());
				createCommand.setUserMobilePhone(createCommand.getLinkTelephone());
				createCommand.setStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_PAY_WAIT));
						
				createCommand.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));
						
				HgLogger.getInstance().info("renfeng","createOrder------->订单信息" + JSON.toJSONString(command));
						
				for (int i = 0; i < createCommand.getPassengers().size(); i++) {// 设置证件类型和乘客类型
					createCommand.getPassengers().get(i).setPassengerType("1");
					createCommand.setLinkName(user.getBaseInfo().getName());// 临时设置登录用户为联系人
				}
				// 计算总价格
				int psgNum = createCommand.getPassengers().size();
				/*double buildFee = createCommand.getFlightPriceInfoDTO()
						.getBuildFee();
				double oilFee = createCommand.getFlightPriceInfoDTO()
						.getOilFee();
				Double ibePrice = Double.parseDouble(createCommand
						.getFlightPriceInfoDTO().getIbePrice());*/
				
				
				//使用价格政策中返回的单人支付总价
				Double singlePlatTotalPrice=createCommand.getFlightPriceInfoDTO().getSinglePlatTotalPrice();
				//查询最新航班政策
				JPPolicyGNQO jPPolicyGNQO = new JPPolicyGNQO();
				
				//使用原来的加密串
				String last_encryptString=request.getParameter("last_encryptString");
				HgLogger.getInstance().info("renfeng",
						"机票下单，查询航班最新政策,查询的机票政策的加密串：" + last_encryptString);
				jPPolicyGNQO.setEncryptString(last_encryptString);// 设置加密字符串
				jPPolicyGNQO.setTickType(DealerReturnInfo.JP_ORDER_TICKTYPE_ALL);// 票号类型
				HgLogger.getInstance().info("renfeng",
						"机票下单，查询航班最新政策,查询的机票政策的QO：" + JSON.toJSONString(jPPolicyGNQO));
				JPQueryHighPolicyGNDTO flightPolicy = jpService.queryFlightPolicy(jPPolicyGNQO);
						
				HgLogger.getInstance().info("renfeng",
						"机票下单，查询航班最新政策,返回一个航班政策dto：" + JSON.toJSONString(flightPolicy));
				
				//使用航班最新政策中的单人价格
				if(flightPolicy!=null){
					singlePlatTotalPrice=flightPolicy.getPricesGNDTO().getSinglePlatTotalPrice();
				}
				
				Double payAmount = psgNum * singlePlatTotalPrice;
				createCommand.getFlightPriceInfoDTO().setPayAmount(payAmount);
				
				createCommand.setCreateTime(new Date());// 设置创建时间
				HgLogger.getInstance().info("renfeng","创建订单" + JSON.toJSONString(command));
						
				/********** 创建订单只保存本地订单 ***************/
				jporderdto = jpService.createLocalityOrder(createCommand);
				HgLogger.getInstance().info("renfeng","createOrder----->创建一个订单，返回一个订单dto："+ JSON.toJSONString(command));
								
				// 创建订单成功后会有一个订单号返回，取得该订单号查询一条该条订单记录，返回JPorder
				if (jporderdto != null) {
					// 查询订单
					//long time = jporderdto.getFlightBaseInfo().getEndTime().getTime()- jporderdto.getFlightBaseInfo().getStartTime().getTime();// 得到起飞和到达的时间毫秒差
					//map.put("flyTime", time / 60000 + "");// 返回分钟
					map.put("dealerOrderCode", jporderdto.getOrderNO());
					map.put("orderId", jporderdto.getId());
					map.put("result", "1");

					// 日志信息
					HgLogger.getInstance().info("renfeng","JPOrderCtrl->confirm->orderCreate[机票创建订单成功！]："+ JSON.toJSONString(jporderdto));
									
				} else {
					map.put("message", "机票创建订单失败！");
					HgLogger.getInstance().info("renfeng","JPOrderCtrl->confirm->orderCreate[机票创建订单失败！]");
							
				}

			}

			out.print(JSON.toJSONString(map));

		} catch (Exception e) {
			// 日志信息
			HgLogger.getInstance().info("renfeng","JPOrderCtrl->confirm->orderCreate[机票创建订单失败！错误异常]"+ HgLogger.getStackTrace(e));
							
			out.print(getHslErrResponseJsonStr(e));
		}
	}

	/**
	 * 订单的支付
	 * 
	 * @param orderId
	 *            订单号
	 * @return
	 */
	@RequestMapping("pay")
	public ModelAndView pay(HttpServletRequest request, String orderId,
			String youh, String id) {
		ModelAndView mav = new ModelAndView("jporder/pay");
		FlightOrderDTO flightOrderDTO = null;
		String message="";
		try {
			mav.addObject("openid", OpenidTracker.get());// 获取微信跟踪器
			mav.addObject("userId", getUserId(request));
			
			// 根据订单号获取一条订单记录
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setId(orderId);
		    flightOrderDTO = jpService.queryOrderDetail(flightOrderQO);
		    
		    String userId=this.getUserId(request);
			if(flightOrderDTO==null||!flightOrderDTO.getJpOrderUser().getUserId().equals(userId)){
				// jpQueryOrderResult.setMessage("失败！");
				//mav.setViewName("error");
				message="订单不存在或非当前用户订单";
				HgLogger.getInstance().info("renfeng", "JPOrderCtr>>pay>>订单不存在或非当前用户订单");
			}else if(flightOrderDTO.getStatus().equals(JPOrderStatusConstant.SHOP_TICKET_CANCEL)){
				message="订单已取消";
				HgLogger.getInstance().info("renfeng", "JPOrderCtr>>pay>>订单已取消，订单信息："+JSON.toJSONString(flightOrderDTO));
				
			}else {
			
				String start = flightOrderDTO.getFlightBaseInfo().getOrgCity();// 获取机票的起飞城市
				String end = flightOrderDTO.getFlightBaseInfo().getDstCity();// 获取机票的到达城市
				Integer num = flightOrderDTO.getPassengers().size();// 获取机票乘客的数量
				StringBuilder subject = new StringBuilder("飞机票 [");
				subject.append(start).append("-").append(end).append("]*").append(num);
						
				mav.addObject("subject", subject.toString());
				mav.addObject("order", flightOrderDTO);
				
				// 判断是否是由选择卡券页面回调到支付页，如果是，则此时用户的看到的实际支付额应该为减去卡券面值之后的值
				double youhui;
				if (youh == null) {
					youhui = 0.00;
					mav.addObject("youhui", youhui);
				} else {
					youhui = Double.parseDouble(youh);
					mav.addObject("youhui", youhui);
					mav.addObject("couponId", id);
				}
				// 根据用户的id号获取用户所拥有的可用的卡券
				
				HslCouponQO hslCouponQO = new HslCouponQO();
				hslCouponQO.setUserId(userId);
				// 只查询未使用的卡券并且满足使用条件的卡券
				hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
				hslCouponQO.setOrderPrice(flightOrderDTO.getFlightPriceInfo().getPayAmount());
				hslCouponQO.setOverdue(true);//是否过期   
				
				if (hslCouponQO.getUserId() != null) {
					List<CouponDTO> couponList = couponService.queryList(hslCouponQO);
					mav.addObject("couponList", couponList);
				}
				
			} 
		} catch (Exception e) {
			// 日志信息
			HgLogger.getInstance().info(
					"renfeng",
					"JPOrderCtrl->pay->queryOrder[根据订单号查询订单失败！错误异常]:"
							+ HgLogger.getStackTrace(e));
			log.error("hsl.err", e);
			mav.setViewName("error");
		}	
		
		
		String couponError = request.getParameter("couponError");
		mav.addObject("couponError", couponError);
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * 机票订单的数据api
	 * 
	 * @param request
	 * @param out
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("ajaxList")
	public void ajaxList(HttpServletRequest request, PrintWriter out,
			Integer pageNo, Integer pageSize) {
		try {
			JPQueryOrderResult jpQueryOrderResult = new JPQueryOrderResult();
			String userId = getUserId(request);
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setUserID(userId);
			flightOrderQO.setPageNo(pageNo);
			flightOrderQO.setPageSize(pageSize);
			// 日志信息
			HgLogger.getInstance().info(
					"liusong",
					"JPOrderCtrl->ajaxList->queryOrder[根据UserId获取订单列表失败！查询的QO]:"
							+ JSON.toJSONString(flightOrderQO));

			List<FlightOrderDTO> jpOrderDTOs = jpService
					.queryOrder(flightOrderQO);
			// 日志信息
			HgLogger.getInstance().info(
					"liusong",
					"JPOrderCtrl->ajaxList->queryOrder[根据UserId获取订单列表成功！]:"
							+ JSON.toJSONString(jpOrderDTOs));

			if (jpOrderDTOs == null) {
				jpQueryOrderResult.setMessage("查询失败！");
				jpQueryOrderResult
						.setResult(JPQueryOrderResult.RESULT_CODE_FAIL);
			} else {
				jpQueryOrderResult.setFlightOrderDTO(jpOrderDTOs);
				jpQueryOrderResult.setTotalCount(jpOrderDTOs.size());
				jpQueryOrderResult.setResult(JPQueryOrderResult.RESULT_CODE_OK);
				// mav.addObject("order", jpOrderDTOs);
			}
			// 返回结果
			out.print(JSON.toJSONString(jpQueryOrderResult));
		} catch (Exception e) {
			// 日志信息
			HgLogger.getInstance().info(
					"liusong",
					"JPOrderCtrl->ajaxList->queryOrder[根据UserId获取订单列表失败！查询的QO]:"
							+ HgLogger.getStackTrace(e));
			out.print(getHslErrResponseJsonStr(e));
		}
	}


	/**
	 * 获取订单详情
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("detail")
	public ModelAndView detail(String orderId,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("jporder/jpDetail");
		String message="";		
		try {
			mav.addObject("openid", OpenidTracker.get());
			UserDTO user = getUserByUserId(getUserId(HttpTracker.getRequest()));
			mav.addObject("userDTO", user);
			mav.addObject("statusMap",JSON.toJSONString(JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP));
			
					
			// 根据订单号获取一条订单记录
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setId(orderId);
			JPQueryOrderResult jpQueryOrderResult = new JPQueryOrderResult();// 订单查询结果集
			HgLogger.getInstance().info("liusong","JPOrderCtrl->detail->queryOrder[获取订单详情时查询订单失败！查询的QO]:"+ JSON.toJSONString(flightOrderQO));
							
			FlightOrderDTO jpOrderDTOs = jpService.queryOrderDetail(flightOrderQO);// 查询订单列表
			
			HgLogger.getInstance().info("liusong","JPOrderCtrl->detail->queryOrder[获取订单详情时查询订单成功！]:"+ JSON.toJSONString(jpOrderDTOs));
			
			//检查查询的订单是否是当前用户的
			String userId = getUserId(request);
			if(jpOrderDTOs==null){
				message="订单不存在";
				
			}else if(!jpOrderDTOs.getJpOrderUser().getUserId().equals(userId)){
				message="操作用户异常";
				jpOrderDTOs=null;
			}else{
				jpQueryOrderResult.setFlightOrder(jpOrderDTOs);
				jpQueryOrderResult.setTotalCount(1);
				jpQueryOrderResult.setResult(JPQueryOrderResult.RESULT_CODE_OK);
				// 日志信息
				
				String start = jpOrderDTOs.getFlightBaseInfo().getOrgCity();// 获取机票的起飞城市
				String end = jpOrderDTOs.getFlightBaseInfo().getDstCity();// 获取机票的到达城市
				Integer num = jpOrderDTOs.getPassengers().size();// 获取机票乘客的数量
				StringBuilder subject = new StringBuilder("飞机票 [");
				subject.append(start).append("-").append(end).append("]*")
						.append(num);
				mav.addObject("subject", subject.toString());
				String status = JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP
						.get("" + jpOrderDTOs.getStatus()
								+ jpOrderDTOs.getPayStatus());
				if (status == null) {
					status = "订单状态异常";
				}
				if (jpOrderDTOs.getPayStatus() != null&& jpOrderDTOs.getPayStatus()
						.equals(Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC))){
										
					mav.addObject("backprice", jpOrderDTOs.getFlightPriceInfo().getReturnedPrice());
							
				}
				
				long hour = (jpOrderDTOs.getFlightBaseInfo().getEndTime().getTime()- jpOrderDTOs.getFlightBaseInfo().getStartTime().getTime())/3600000;
				long min=((jpOrderDTOs.getFlightBaseInfo().getEndTime().getTime()- jpOrderDTOs.getFlightBaseInfo().getStartTime().getTime())/60000)%60;
						
				mav.addObject("hour", hour);	
				mav.addObject("min", min);	
				
				mav.addObject("PAYWAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);
				mav.addObject("NOPAY", JPOrderStatusConstant.SHOP_TICKET_NO_PAY);
				mav.addObject("status", status);
				mav.addObject("order", jpOrderDTOs);
			}
							
			
			
		} catch (Exception e) {
			// 日志信息
			HgLogger.getInstance().info("liusong","JPOrderCtrl->detail->queryOrder[获取订单详情时查询订单失败！错误异常]:"+ HgLogger.getStackTrace(e));
						 	
			log.error("hsl.err", e);
			mav.setViewName("error");
		}
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * 获取用户所拥有的可用的卡券 参数：@param orderId 创建订单时得到的订单号
	 * 
	 * @return
	 */
	@RequestMapping("coupon")
	public ModelAndView coupon(HttpServletRequest request, String orderId) {
		ModelAndView mav = new ModelAndView("jporder/coupon");
		try {
			mav.addObject("openid", OpenidTracker.get());// 获取微信跟踪器

			// 根据订单号获取一条订单记录
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setOrderNO(orderId);
			flightOrderQO.setOrderType("1");
			FlightOrderDTO flightOrderDTO = jpService.queryOrderDetail(flightOrderQO);
			HslCouponQO hslCouponQO = new HslCouponQO();
			if (flightOrderDTO != null) {// 如果返回的订单dto不为空
				mav.addObject("order", flightOrderDTO);
				
				// 根据用户的id号获取用户所拥有的可用的卡券
				
				hslCouponQO.setUserId(getUserId(request));
				// 只查询未使用的卡券并且满足使用条件的卡券
				hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
				hslCouponQO.setOverdue(true);//是否过期   
				hslCouponQO.setOrderPrice(flightOrderDTO.getFlightPriceInfo().getPayAmount());
			}

			if (hslCouponQO.getUserId() != null) {
				List<CouponDTO> couponList = couponService.queryList(hslCouponQO);
				mav.addObject("couponList", couponList);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("renfeng",
					"根据登录用户的userid查询该用户拥有的卡券列表失败" + e.getStackTrace());
							
			log.error("hsl.err", e);
			mav.setViewName("error");
		}
		return mav;
	}

	
}