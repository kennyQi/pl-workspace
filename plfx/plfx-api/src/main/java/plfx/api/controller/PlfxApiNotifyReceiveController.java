package plfx.api.controller;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.yeexing.pojo.command.order.JPOrderCancelCommand;
import plfx.yeexing.pojo.command.order.JPOrderCommand;
import plfx.yeexing.pojo.command.order.JPOrderRefundCommand;
import plfx.yeexing.pojo.command.order.JPOrderRefuseCommand;
import plfx.yeexing.pojo.command.order.JPPayNotifyCommand;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.util.SignTool;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：通知接收
 * @类修改者：
 * @修改日期：2015-7-14下午4:15:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-14下午4:15:37
 */
@Controller
public class PlfxApiNotifyReceiveController {

	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;

	/**
	 * @throws UnsupportedEncodingException 
	 * 
	 * @方法功能说明：易行通知类接口
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月29日下午2:38:43
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/airTicket/notify",method=RequestMethod.POST)
	public String airTicketNotify(HttpServletRequest request) throws UnsupportedEncodingException {
		HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->开始执行");
		String type = request.getParameter("type");
		String key = SysProperties.getInstance().get("yeeXing_key");
		String sign = request.getParameter("sign");
		HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->通知类型type=" + type + ",sign="+sign);
		Map<String, String> params = new HashMap<String, String>();
		if(StringUtils.isNotBlank(type)){
			if("1".equals(type)){
				//出票成功通知
				String orderId = request.getParameter("orderid");
				String passengerName = request.getParameter("passengerName");
				String airId = request.getParameter("airId");
				String newPnr = request.getParameter("newPnr");
				params.put("orderId", orderId == null ? "":orderId);
				params.put("passengerName", passengerName == null ? "":passengerName);
				params.put("airId", airId == null ? "":airId);
				params.put("type", type == null ? "":type);
				params.put("newPnr", newPnr == null ? "":newPnr);
				String checkSign = SignTool.sign(params, key);
				HgLogger.getInstance().info("yuqz",
						"PlfxApiController->airTicketNotify->[出票成功通知]:orderId="
				+orderId+",passengerName="+passengerName+",airId="+airId+",type="+type+",newPnr="+newPnr+",checkSign="+checkSign + ",sign="+sign);
				if(StringUtils.isNotBlank(orderId) && checkSign.equals(sign)){
					if(StringUtils.isNotBlank(passengerName)){
						passengerName = URLDecoder.decode(passengerName, "utf-8");
					}
					JPOrderCommand jpOrderCommand = new JPOrderCommand();
					//商城通知地址
//					String notifyUrl = SysProperties.getInstance().get("http_domain") + "/api/ticket/notify";
//					jpOrderCommand.setNotifyUrl(notifyUrl);
					jpOrderCommand.setYeeXingOrderId(orderId);
					jpOrderCommand.setPassengerName(passengerName);
					jpOrderCommand.setAirId(airId);
					jpOrderCommand.setType(type);
					jpOrderCommand.setNewPnr(newPnr);
					jpOrderCommand.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_SUCC));//设置订单状态为出票成功
					jpOrderCommand.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_PAY_SUCC));//设置支付状态为已支付
					//更新订单信息
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->jpOrderCommand=" + JSON.toJSONString(jpOrderCommand));
					boolean bool = jpPlatformOrderService.updateJPOrder(jpOrderCommand);
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->返回bool=" + bool);
					if(bool){
						return yeeXingResponses(orderId);
					}
				}
			}
			else if("2".equals(type)){
				//支付成功通知
				String orderId = request.getParameter("orderid");
				String payId = request.getParameter("payid");
				String totalPrice = request.getParameter("totalPrice");
				String payPlatform = request.getParameter("payplatform");
				String payType = request.getParameter("paytype");//签名计算时候用payType，其他用paytype
				params.put("orderId", orderId == null ? "":orderId);
				params.put("payId", payId == null ? "":payId);
				params.put("totalPrice", totalPrice == null ? "":totalPrice);
				params.put("type", type == null ? "":type);
				params.put("payPlatform", payPlatform == null ? "":payPlatform);
				params.put("payType", payType == null ? "":payType);
				String checkSign = SignTool.sign(params, key);
				HgLogger.getInstance().info("yuqz",
						"PlfxApiController->airTicketNotify->[支付成功通知]:orderId="
				+orderId+",payId="+payId+",type="+type+",payPlatform="+payPlatform+",payType="+payType+",checkSign="+checkSign + ",sign="+sign);
				if(StringUtils.isNotBlank(orderId) && checkSign.equals(sign)){
					JPPayNotifyCommand jpPayNotifyCommand = new JPPayNotifyCommand();
					jpPayNotifyCommand.setYeeXingOrderId(orderId);
					jpPayNotifyCommand.setPayId(payId);
					jpPayNotifyCommand.setPayplatform(Integer.parseInt(payPlatform));
					jpPayNotifyCommand.setPayType(Integer.parseInt(payType));
					jpPayNotifyCommand.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_PAY_SUCC));
					//更新订单支付状态
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->jpPayNotifyCommand=" + JSON.toJSONString(jpPayNotifyCommand));
					boolean bool = jpPlatformOrderService.updatePayStatus(jpPayNotifyCommand);
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->返回bool=" + bool);
					if(bool){
						return yeeXingResponses(orderId);
					}
				}
			}
			else if("3".equals(type)){
				//取消成功通知
				String orderId = request.getParameter("orderid");
				String passengerName = request.getParameter("passengerName");
				params.put("orderId", orderId == null ? "":orderId);
				params.put("passengerName", passengerName == null ? "":passengerName);
				params.put("type", type == null ? "":type);
				String checkSign = SignTool.sign(params, key);
				HgLogger.getInstance().info("yuqz",
						"PlfxApiController->airTicketNotify->[取消成功通知]:orderId="
				+orderId+",passengerName="+passengerName+",checkSign="+checkSign + ",sign="+sign);
				if(StringUtils.isNotBlank(orderId) && checkSign.equals(sign)){
					if(StringUtils.isNotBlank(passengerName)){
						passengerName = URLDecoder.decode(passengerName, "utf-8");
					}
					JPOrderCancelCommand jpOrderCancelCommand = new JPOrderCancelCommand();
					//商城通知地址
					String notifyUrl = SysProperties.getInstance().get("http_domain") + "/api/ticket/notify";
					jpOrderCancelCommand.setNotifyUrl(notifyUrl);
					jpOrderCancelCommand.setYeeXingOrderId(orderId);
					jpOrderCancelCommand.setPassengerName(passengerName);
					//设置订单状态为已取消
					jpOrderCancelCommand.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_CANCEL));
					//取消订单
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->jpOrderCancelCommand=" + JSON.toJSONString(jpOrderCancelCommand));
					boolean bool = jpPlatformOrderService.jpOrderCancel(jpOrderCancelCommand);
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->返回bool=" + bool);
					if(bool){
						return yeeXingResponses(orderId);
					}
				}
			}
			else if("4".equals(type)){
				//退废票通知
				String orderId = request.getParameter("orderid");
				String airId = request.getParameter("airId");
				String refundPrice = request.getParameter("refundPrice");
				String refundStatus = request.getParameter("refundStatus");
				String refuseMemo = request.getParameter("refuseMemo");
				String procedures = request.getParameter("procedures");
				params.put("orderId", orderId == null ? "":orderId);
				params.put("airId", airId == null ? "":airId);
				params.put("refundPrice", refundPrice == null ? "":refundPrice);
				params.put("refundStatus", refundStatus == null ? "":refundStatus);
				params.put("refuseMemo", refuseMemo == null ? "":refuseMemo);
				params.put("procedures", procedures == null ? "":procedures);
				params.put("type", type == null ? "":type);
				String checkSign = SignTool.sign(params, key);
				HgLogger.getInstance().info("yuqz",
						"PlfxApiController->airTicketNotify->[退废票通知]:orderId="
				+orderId+",airId="+airId+",refundPrice="+refundPrice+",refundStatus="+refundStatus
				+",refuseMemo="+refuseMemo+",procedures="+procedures+",checkSign="+checkSign + ",sign="+sign);
				if(StringUtils.isNotBlank(orderId) && checkSign.equals(sign)){
					if(StringUtils.isNotBlank(refuseMemo)){
						refuseMemo = URLDecoder.decode(refuseMemo, "utf-8");
					}
					//退废票通知
					JPOrderRefundCommand jpOrderRefundCommand = new JPOrderRefundCommand();
					if(StringUtils.isNotBlank(orderId)){
						jpOrderRefundCommand.setYeeXingOrderId(orderId);
					}
					if(StringUtils.isNotBlank(airId)){
						jpOrderRefundCommand.setAirId(airId);
					}
					if(StringUtils.isNotBlank(refundPrice)){
						jpOrderRefundCommand.setRefundPrice(Double.parseDouble(refundPrice));
					}
					if(StringUtils.isNotBlank(refundStatus)){
						jpOrderRefundCommand.setRefundStatus(Integer.parseInt(refundStatus));
					}
					jpOrderRefundCommand.setRefuseMemo(refuseMemo);
					if(StringUtils.isNotBlank(procedures)){
						jpOrderRefundCommand.setProcedures(Double.parseDouble(procedures));
					}
					//处理退废票通知
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->jpOrderRefundCommand=" + JSON.toJSONString(jpOrderRefundCommand));
					boolean bool = jpPlatformOrderService.jpOrderRefund(jpOrderRefundCommand);
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->返回bool=" + bool);
					if(bool){
						return yeeXingResponses(orderId);
					}
				}
			}
			else if("6".equals(type)){
				//拒绝出票通知
				String orderId = request.getParameter("orderid");
				String passengerName = request.getParameter("passengerName");
				String refuseMemo = request.getParameter("refuseMemo");
				params.put("orderId", orderId == null ? "":orderId);
				params.put("passengerName", passengerName == null ? "":passengerName);
				params.put("refuseMemo", refuseMemo == null ? "":refuseMemo);
				params.put("type", type == null ? "":type);
				String checkSign = SignTool.sign(params, key);
				HgLogger.getInstance().info("yuqz",
						"PlfxApiController->airTicketNotify->[拒绝出票成功通知]:orderId="
				+orderId+",refuseMemo="+refuseMemo+",checkSign="+checkSign + ",sign="+sign);
				if(StringUtils.isNotBlank(orderId) && checkSign.equals(sign)){
					if(StringUtils.isNotBlank(passengerName)){
						passengerName = URLDecoder.decode(passengerName, "utf-8");
						
					}
					if(StringUtils.isNotBlank(refuseMemo)){
						refuseMemo = URLDecoder.decode(refuseMemo, "utf-8");
					}
					JPOrderRefuseCommand jpOrderRefuseCommand = new JPOrderRefuseCommand();
					jpOrderRefuseCommand.setYeeXingOrderId(orderId);
					jpOrderRefuseCommand.setPassengerName(passengerName);
					jpOrderRefuseCommand.setRefuseMemo(refuseMemo);
					jpOrderRefuseCommand.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_FAIL));
					jpOrderRefuseCommand.setPayStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_REBACK_SUCC));
					//处理拒绝出票通知
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->jpOrderRefuseCommand=" + JSON.toJSONString(jpOrderRefuseCommand));
					boolean bool = jpPlatformOrderService.jpOrderRefuse(jpOrderRefuseCommand);
					HgLogger.getInstance().info("yuqz","PlfxApiController->airTicketNotify->返回bool=" + bool);
					if(bool){
						return yeeXingResponses(orderId);
					}
				}
			}
		}
		
		return "FAIL";
	}
	
	/**
	 * 
	 * @方法功能说明：易行通知的返回值
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月29日下午2:44:45
	 * @修改内容：
	 * @参数：@param orderid
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String yeeXingResponses(String orderId){
		return "RECV_ORDID_" + orderId;
	}
	
	
}
