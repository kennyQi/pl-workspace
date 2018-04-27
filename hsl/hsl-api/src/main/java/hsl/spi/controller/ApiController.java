package hsl.spi.controller;

import hg.common.util.JsonUtil;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hsl.alipay.util.AlipayNotify;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.util.Md5Util;
import hsl.app.service.local.jp.JPFlightLocalService;
import hsl.app.service.local.jp.JPOrderLocalService;
import hsl.domain.model.jp.JPOrder;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.exception.CouponException;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.spi.action.ActionContext;
import hsl.spi.action.HSLAction;
import hsl.spi.command.JPOrderCommand;
import hsl.spi.common.JPOrderStatus;
import hsl.spi.inter.Coupon.CouponService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;

import com.alibaba.fastjson.JSON;

@Controller
public class ApiController extends BaseController {

	@Resource
	private ActionContext actionContext;
	
	@Resource
	private JPOrderLocalService jpOrderLocalService;
	
	@Resource
	private JPFlightLocalService jpFlightLocalService;
	
	@Resource
	private SMSUtils smsUtils;
	
	@Resource
	private CouponService couponService;
	
	@ResponseBody
	@RequestMapping(value = "/api")
	public String api(HttpServletRequest request) {

		ApiResponse response = null;
		try {
			// 请求信息转ApiRequest类
			String msg = request.getParameter("msg");
			HgLogger.getInstance().info("zhangka", "ApiController->api->msg:" + msg);

			ApiRequest apiRequest = JSON.parseObject(msg, ApiRequest.class);
			
			if (checkSign(apiRequest)) {
				String actionName = apiRequest.getHead().getActionName();
				HSLAction action = actionContext.get(actionName);
				return action.execute(apiRequest);
			} else {
				response = new ApiResponse();
				response.setMessage("非法请求，请检查你的客户端密钥！");
				response.setResult(ApiResponse.RESULT_CODE_FAIL);
				HgLogger.getInstance().info("zhangka", "ApiController->api->msg:" + msg + "->非法请求，请检查你的客户端密钥！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhangka", "ApiController->api->exception:"+HgLogger.getStackTrace(e));
		}
		
		return JSON.toJSONString(response);
	}
	
	/**
	 * 支付宝异步通知地址      web处理方式
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/api/alipay/notify")
	public void notify(HttpServletRequest request) {
		HgLogger.getInstance().info("zhangka", "ApiController->notify->处理支付宝异步通知开始");
		//获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		HgLogger.getInstance().info("zhangka", "ApiController->notify->requestParams[post参数]"+JSON.toJSONString(requestParams));
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		HgLogger.getInstance().info("zhangka", "ApiController->notify->params[post参数]"+JSON.toJSONString(params));
		
		
		//商户订单号
		String out_trade_no = "";
		//支付宝交易号
		String trade_no = "";
		//交易状态
		String trade_status = "";
		//付款支付宝账号
		String buyer_email = "";
		
		boolean verifyNotify = false;
		try {
			verifyNotify = AlipayNotify.verifyNotify(params);
			Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
			
			out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
			trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
			trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();
			buyer_email = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "ApiController->notify->获取参数为空:" 
					+"|out_trade_no="+out_trade_no
					+"|trade_no"+trade_no
					+"|trade_status"+trade_status
					+ HgLogger.getStackTrace(e));
			return;
		}
		
		HgLogger.getInstance().info("zhangka", "out_trade_no="+out_trade_no+",trade_no="+trade_no+",trade_status="+trade_status);
		
		HslJPOrderQO qo = new HslJPOrderQO();
		qo.setStatus(Integer.parseInt(JPOrderStatus.PAY_WAIT));
		qo.setDealerOrderCode(out_trade_no);
		JPOrder jpOrder = jpOrderLocalService.queryUnique(qo);
		
		if (jpOrder == null) {
			HgLogger.getInstance().info("zhangka", "待支付订单查询失败，dealerOrderCode="+out_trade_no);
			return;
		} else {
			jpOrder.setBuyerEmail(buyer_email);//设置付款支付宝账号
			HgLogger.getInstance().info("zhangka", "ApiController->notify->jpOrder->ID="+jpOrder.getId());
		}

		if (verifyNotify) {//验证成功
			
			if (trade_status.equals("WAIT_BUYER_PAY")) {
				//交易创建，等待买家付款。
				jpOrder.setStatus(Integer.parseInt(JPOrderStatus.PAY_WAIT));
			} else if (trade_status.equals("TRADE_CLOSED")) {
				//1、在指定时间段内未支付时关闭的交易；
				//2、在交易完成全额退款成功时关闭的交易。
				jpOrder.setStatus(Integer.parseInt(JPOrderStatus.TICKET_CANCEL));
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				//更改订单状态
				jpOrder.setStatus(Integer.parseInt(JPOrderStatus.PAY_SUCC));
			} else if (trade_status.equals("TRADE_PENDING")) {
				//等待卖家收款（买家付款后，如果卖家账号被冻结）。
				jpOrder.setStatus(Integer.parseInt(JPOrderStatus.PAY_SUCC));
			} else if (trade_status.equals("TRADE_FINISHED")) {
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//该种交易状态只在两种情况下出现
				//1、开通了普通即时到账，买家付款成功后。
				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				
				//更改订单状态
				jpOrder.setStatus(Integer.parseInt(JPOrderStatus.PAY_SUCC));
			}

		} else {//验证失败
			jpOrder.setStatus(Integer.parseInt(JPOrderStatus.PAY_FAIL));
			jpOrderLocalService.update(jpOrder);
			HgLogger.getInstance().info("zhangka", "ApiController->notify->支付宝异步通知验证失败AlipayNotify.verifyNotify="+verifyNotify);
			return;
		}
		
		//发送短信通知用户付款成功
		if ((JPOrderStatus.PAY_SUCC).equals(String.valueOf(jpOrder.getStatus())) && jpOrder.getOrderUser() != null) {
			//请求出票
			try {
				//更新到数据库（支付状态和出票状态的修改可以不在同一个事务里面）
				jpOrderLocalService.update(jpOrder);
				
				JPAskOrderTicketCommand command = new JPAskOrderTicketCommand();
				command.setOrderId(out_trade_no);
				command.setPayOrderId(trade_no);
				command.setBuyerEmail(buyer_email);
				command.setPayWay("ZH");
				jpFlightLocalService.askOrderTicket(command);//请求出票
			} catch (Exception e) {
				HgLogger.getInstance().error("zhangka", "ApiController->notify->请求出票异常" + HgLogger.getStackTrace(e));
			}
			//发短信
			try {
				String mobile = jpOrder.getOrderUser().getMobile();
				String smsContent = "【汇商旅】您的订单" + jpOrder.getDealerOrderCode() + "，已经购买成功，出票完成后将短信通知，请您放心安排行程。查询订单请点击http://m.ply365.com/user/jpos，客服电话：0571-28280813。";
				HgLogger.getInstance().info("chenxy", "订单支付成功发送短信  :" + mobile + " smsContent:" + smsContent);
				smsUtils.sendSms(mobile, smsContent);
				HgLogger.getInstance().info("zhangka", "ApiController->notify->jpOrder->mobile:" + mobile + " smsContent:" + smsContent);
			} catch (Exception e) {
				HgLogger.getInstance().error("zhangka", "ApiController->notify->用户付款成功短信发送异常" + HgLogger.getStackTrace(e));
			}
		}
		
		HgLogger.getInstance().info("zhangka", "ApiController->/api/alipay/notify->处理支付宝异步通知结束");
	}
	
	/**
	 * 出票完成通知
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/api/ticket/notify")
	public String ticketNotify(HttpServletRequest request) {
		String msg = request.getParameter("msg");
		HgLogger.getInstance().info("zhangka", "ApiController->ticketNotify->出票完成通知：msg="+msg);
		if (StringUtils.isNotBlank(msg)) {
			JPOrderCommand command = JSON.parseObject(msg, JPOrderCommand.class);
			return jpOrderLocalService.orderTicketNotify(command) ? "SUCCESS" : "FAIL";
		}
		
		return "FAIL";
	}
	
	/**
	 * 退废票完成通知
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api/refund/notify")
	@ResponseBody
	public String refund(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		String dealerOrderCode = request.getParameter("dealerOrderCode");
		
		HgLogger.getInstance().info("zhangka", "ApiController->ticketNotify->废票完成通知：flag="+ flag + "|dealerOrderCode=" + dealerOrderCode);
		if (StringUtils.isNotBlank(flag) && StringUtils.isNotBlank(dealerOrderCode)) {
			JPOrderCommand command = new JPOrderCommand();
			command.setDealerOrderCode(dealerOrderCode);
			
			if (flag.equals("N")) {
				command.setStatus(Integer.parseInt(JPOrderStatus.TICKET_SUCC));
			} else {
				command.setStatus(Integer.parseInt(JPOrderStatus.DISCARD_SUCC_NOT_REFUND));
			}
			
			return jpOrderLocalService.orderRefundNotify(command) ? "SUCCESS" : "FAIL";
		}
		
		return "FAIL";
	}
	
	/**
	 * 分销平台订单状态同步通知商城
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api/syn/notify")
	@ResponseBody
	public String syn(HttpServletRequest request) {
		String dealerOrderCode = request.getParameter("dealerOrderCode");
		String status = request.getParameter("status");
		HgLogger.getInstance().info("chenxy","分销平台订单状态同步通知商城 >>同步状态为："+status);
		
		//判断此时订单的状态是否是已取消状态，是:则将卡券中的订单号删除
		if (JPOrderStatus.TICKET_CANCEL.equals(status) || JPOrderStatus.ORDER_CLOSED.equals(status) || JPOrderStatus.TICKET_FAIL_REFUND.equals(status) || JPOrderStatus.BACK_SUCC_REFUND.equals(status)) {
			HgLogger.getInstance().info("liusong","该订单号对应订单的订单状态已经为已取消状态，该订单号："+dealerOrderCode);
			
			//如果订单状态为已取消，则需要删除卡券中的与之相关联的订单号
			OrderRefundCommand command = new OrderRefundCommand();
			command.setOrderId(dealerOrderCode);
			try {
				couponService.orderRefund(command);
			} catch(CouponException e) {
				HgLogger.getInstance().error("liusong", "删除卡券中的与之相关联的订单号失败"+e.getMessage()+HgLogger.getStackTrace(e));
			}
		}
		
		HgLogger.getInstance().info("zhangka", "ApiController->syn->[分销平台订单状态同步通知商城]->dealerOrderCode:" + dealerOrderCode + " status:" + status);
		
		return jpOrderLocalService.updateOrderStatus(dealerOrderCode, Integer.parseInt(status),-1) ? "SUCCESS" : "FAIL";
	}
	
	/**
	 * 检验sign是否正确
	 * @param apiRequest
	 * @return
	 */
	private boolean checkSign(ApiRequest apiRequest) {

		// 客户端sign
		String clientSign = apiRequest.getHead().getSign();
		// 客户端key
		String clientKey = apiRequest.getHead().getClientKey();

		// 去掉客户端msg中的sign值
		apiRequest.getHead().setSign(null);
		
		String msg = JsonUtil.parseObject(apiRequest, false);
		
		String secret_key = SysProperties.getInstance().get(clientKey);
		
		String sign = Md5Util.MD5(clientKey + secret_key + msg);
		return sign.equalsIgnoreCase(clientSign) ? true : false;
		
	}

}
