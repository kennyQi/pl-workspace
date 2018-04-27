package slfx.api.controller;

import hg.common.util.JsonUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.api.alipay.AlipayNotify;
import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.ActionContext;
import slfx.api.controller.base.BaseController;
import slfx.api.controller.base.SLFXAction;
import slfx.api.util.Md5Util;
import slfx.jp.command.admin.JPOrderCommand;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.spi.inter.CityAirCodeService;
import slfx.jp.spi.inter.FlightPolicyService;
import slfx.jp.spi.inter.JPPlatformOrderService;
import slfx.jp.spi.inter.WebFlightService;
import slfx.mp.spi.inter.api.ApiMPDatePriceService;
import slfx.mp.spi.inter.api.ApiMPOrderService;
import slfx.mp.spi.inter.api.ApiMPPolicyService;
import slfx.mp.spi.inter.api.ApiMPScenicSpotsService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：SLFX-API开放http请求接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月6日上午11:17:12
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value = "/slfx")
public class SlfxApiController extends BaseController {
	
	@Resource
	private ActionContext actionContext;
	
	@Autowired
	private WebFlightService webFlightService;
	@Autowired
	private FlightPolicyService flightPolicyService;
	@Autowired
	private CityAirCodeService cityAirCodeService;
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	
	@Autowired
	private ApiMPDatePriceService apiMPDatePriceService;
	@Autowired
	private ApiMPOrderService apiMPOrderService;
	@Autowired
	private ApiMPPolicyService apiMPPolicyService;
	@Autowired
	private ApiMPScenicSpotsService apiMPScenicSpotsService;
	
	/**
	 * 
	 * @方法功能说明：分销接口入口
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月24日上午9:29:03
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/api")
	public String api(HttpServletRequest request) {
		HgLogger.getInstance().info("tandeng","SlfxApiController->api->开始执行");

		ApiResponse response = null;
		try {
			// 请求信息转ApiRequest类
			String msg = request.getParameter("msg");

			// 请求信息转ApiRequest类
			ApiRequest apiRequest = JSON.parseObject(msg, ApiRequest.class);
			//if (checkSign(apiRequest)) {
			if (apiRequest != null) {
						
				String controllerName = apiRequest.getHead().getActionName();
				
				SLFXAction action = actionContext.get(controllerName);
				
				return action.execute(apiRequest);
				
			} else {
				response = new ApiResponse();
				response.setMessage("非法请求！");
				response.setResult(ApiResponse.RESULT_CODE_FAILE);
				HgLogger.getInstance().info("tandeng", "SlfxApiController->api->msg:" + msg + "->非法请求，请检查你的客户端密钥！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("tandeng", "SlfxApiController->api->exception:"+HgLogger.getStackTrace(e));
		}
		System.out.println("--------------"+JSON.toJSONString(response));
		return JSON.toJSONString(response);
	}


	/**
	 * 易购出票结果完成，异步通知接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/api/ticket/notify",method=RequestMethod.POST)
	public String ticketNotify(HttpServletRequest request) {
		HgLogger.getInstance().info("tandeng","SlfxApiController->ticketNotify->开始执行");
		
		String orderNo = request.getParameter("OrderNo");
		String ticketNos = request.getParameter("TicketNos");
		String passengers = request.getParameter("Passengers");
		String messageType = request.getParameter("MessageType");
		String flag = request.getParameter("Flag");
		String orderStatus = request.getParameter("OrderStatus");
		
		HgLogger.getInstance().info("tandeng",
				"SlfxApiController->ticketNotify->[通知出票]:orderNo="
		+orderNo+",ticketNos="+ticketNos+",messageType="+messageType+",flag="+flag+",passengers="+passengers+",orderStatus="+orderStatus);
		
		//支付通知处理
		if (messageType != null && "1".equals(messageType.trim())) {
			String platCode = request.getParameter("PlatCode");
			String platOrderNo = request.getParameter("PlatOrderNo");
			String payType = request.getParameter("PayType");
			String payMoney = request.getParameter("PayMoney");
			String payAccount = request.getParameter("PayAccount");
			String tradeNo = request.getParameter("TradeNo");
			HgLogger.getInstance().info("tandeng",
					"SlfxApiController->ticketNotify->[支付通知]:"
							+"orderNo="+orderNo
							+",messageType="+messageType
							+",flag="+flag
							+",platCode="+platCode
							+",platOrderNo="+platOrderNo
							+",payType="+payType
							+",payMoney="+payMoney
							+",payAccount="+payAccount
							+",tradeNo="+tradeNo
							+",orderStatus="+orderStatus
							+",passengers="+passengers);
			
			//同步给商城
			//String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/syn/notify";
			//HttpUtil.reqForPost(notifyUrl, "dealerOrderCode=" + tradeNo + "&status=" + JPOrderStatusConstant.SHOP_TICKET_SUCC + "&payStatus=" + JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC, 60000);
			
			return "SUCCESS";
		}
		
		//出票通知处理
		if (StringUtils.isNotBlank(messageType) && "0".equals(messageType) 
				&& StringUtils.isNotBlank(orderNo) ){
			
			JPOrderCommand command = new JPOrderCommand();
			//商城通知地址
			String notifyUrl = SysProperties.getInstance().get("hsl_domain") + "/api/ticket/notify";
			command.setNotifyUrl(notifyUrl);
			command.setFlag(flag);
			command.setYgOrderNo(orderNo);
			command.setYgOrderStatus(orderStatus);
			
			if("Y".equalsIgnoreCase(flag) && StringUtils.isNotBlank(ticketNos)){
				//出票成功
				command.setTktNo(ticketNos.split("\\|"));
				command.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_SUCC));
				return jpPlatformOrderService.updateOrderStatus(command) ? "SUCCESS" : "FAIL";
			}else if("N".equalsIgnoreCase(flag)){
				//出票失败
				command.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_FAIL));
				return jpPlatformOrderService.getTicketNoFailure(command) ? "SUCCESS" : "FAIL";
			}else{
				//易购参数有误
				HgLogger.getInstance().info("tandeng","SlfxApiController->ticketNotify->易购通知数据有误,flag="+flag);
				return "FAIL";
			}
		}
		
		return "FAIL";
	}
	
	/**
	 * 
	 * @方法功能说明：易购退废票回调通知
	 * 收到消息，钱款第三方已经退出，不保证及时到账，
	 * 并且同步信息给商城的接口
	 * @修改者名字：tandeng
	 * @修改时间：2014年11月25日下午3:00:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/api/backOrDiscardTicket/notify",method=RequestMethod.POST)
	public String backOrDiscardTicketNotify(HttpServletRequest request) {
		HgLogger.getInstance().info("tandeng","SlfxApiController->backOrDiscardTicketNotify->开始执行");
	
		String messageType = request.getParameter("MessageType");
		String flag = request.getParameter("Flag");
		String orderNo = request.getParameter("OrderNo");
		String platCode = request.getParameter("PlatCode");
		String platOrderNo = request.getParameter("PlatOrderNo");
		String payMoney = request.getParameter("PayMoney");
		String ygOrderStatus = request.getParameter("OrderStatus");
		HgLogger.getInstance().info("tandeng","SlfxApiController->backOrDiscardTicketNotify->[退废票通知]:messageType="+messageType+",flag="+flag+",orderNo="+orderNo+",platCode="+platCode+",platOrderNo="+platOrderNo+",payMoney="+payMoney+",ygOrderStatus="+ygOrderStatus);
		//messageType=2,flag=Y,orderNo=5032418036108,platCode=007,platOrderNo=TT2015032412558,payMoney=--743.00,ygOrderStatus=FR][]
		//不是退废票消息，直接返回
		//if (messageType != null && "2".equals(messageType.trim())) {
		if(StringUtils.isNotBlank(messageType) && "2".equals(messageType.trim())){
			//添加日志
			HgLogger.getInstance().info("tandeng+yaosanfeng1","SlfxApiController->backOrDiscardTicketNotify->[退废票通知]:messageType="+messageType+",flag="+flag+",orderNo="+orderNo+",platCode="+platCode+",platOrderNo="+platOrderNo+",payMoney="+payMoney+",ygOrderStatus="+ygOrderStatus);
			JPOrderCommand command = new JPOrderCommand();
			command.setYgOrderNo(orderNo);  
			command.setSupplierOrderNo(platOrderNo);
			command.setFlag(flag);
			command.setPayAmount(Double.parseDouble(payMoney));
			HgLogger.getInstance().info("tandeng+yaosanfeng2","SlfxApiController->backOrDiscardTicketNotify->[退废票通知]:orderNo="+command.getOrderNo()+",platOrderNo="+command.getPlatCode()+",flag="+command.getFlag()+",payMoney="+command.getPayAmount()+",platOrderNo="+platOrderNo);
			if (flag.equals("N")) {
				command.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_SUCC));
				command.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_RECEIVE_PAYMENT_SUCC));
				HgLogger.getInstance().info("tandeng+yaosanfeng3","SlfxApiController->backOrDiscardTicketNotify->[退废票通知]:flag状态为N");
			} else {
				command.setStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_REFUND_SUCC));//退/废成功
				command.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SLFX_TICKET_TO_BE_BACK_SUCC));//已回款
				HgLogger.getInstance().info("tandeng+yaosanfeng4","SlfxApiController->backOrDiscardTicketNotify->[退废票通知]:订单状态="+command.getStatus()+",支付状态="+command.getPayStatus());
			}
			try{
				String ret = jpPlatformOrderService.backOrDiscardTicketNotify(command) ? "SUCCESS" : "FAIL";
				HgLogger.getInstance().info("tandeng+yaosanfeng5","SlfxApiController->backOrDiscardTicketNotify->开始结束,ret="+ret);
				return ret;
			
			}catch(Exception e){
				HgLogger.getInstance().info("tandeng+yaosanfeng6","SlfxApiController->backOrDiscardTicketNotify->结束"+e.getMessage());
			}
		//	String ret = jpPlatformOrderService.backOrDiscardTicketNotify(command) ? "SUCCESS" : "FAIL";
	//		HgLogger.getInstance().info("tandeng","SlfxApiController->backOrDiscardTicketNotify->开始结束,ret="+ret);
		//	return ret;
			
		}
		HgLogger.getInstance().info("tandeng","SlfxApiController->backOrDiscardTicketNotify->开始结束,同步数据失败");
		return "FAIL";
	}
	
	/**
	 * 支付宝批量退款异步通知
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/api/alipay/refund/notify")
	public String refundNotify(HttpServletRequest request) {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
			sb.append(name).append("=").append(valueStr).append("&");
		}
		
		HgLogger.getInstance().info("zhangka", "SlfxApiController->refundNotify->params:" + sb.toString());
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		String result_details = request.getParameter("result_details");	//处理结果详情
		HgLogger.getInstance().info("zhangka", "SlfxApiController->refundNotify->result_details:" + result_details);

		if (AlipayNotify.verify(params)) {//验证成功
			boolean bool = jpPlatformOrderService.refund(result_details);
			if(bool){
				return "success";
			}
			
		}else{
			//验证失败
			HgLogger.getInstance().info("zhangka", "SlfxApiController->refundNotify->验证失败params="+params);			
		}
		
		HgLogger.getInstance().info("zhangka", "SlfxApiController->refundNotify->处理支付宝批量退款异步通知结束");
		return "fail";
	}

	
	/**
	 * 检验sign是否正确
	 * @param apiRequest
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkSign(ApiRequest apiRequest) {

		// 获取客户端sign
		String clientSign = apiRequest.getHead().getSign();
		
		// 去掉客户端msg中的sign值
		apiRequest.getHead().setSign(null);
		String msg = JsonUtil.parseObject(apiRequest, false);
		
		// 客户端key
		String clientKey = apiRequest.getHead().getFromClientKey();
		
		// 从数据库获取密匙key
		String secretKey = "";
		String sign = Md5Util.MD5(clientKey + secretKey + msg);
		
		return sign.equals(clientSign) ? true : false;
	}

}
