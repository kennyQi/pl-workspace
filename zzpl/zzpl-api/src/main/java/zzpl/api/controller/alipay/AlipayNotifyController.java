package zzpl.api.controller.alipay;

import hg.log.util.HgLogger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zzpl.api.controller.BaseController;
import zzpl.api.util.AlipayNotify;
import zzpl.app.service.local.jp.gn.AlipayNotifyService;
import zzpl.app.service.local.log.LogService;
import zzpl.pojo.command.jp.AlipayCommand;
import zzpl.pojo.command.log.SystemCommunicationLogNotifyCommand;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：支付宝异步回调
 * @类修改者：
 * @修改日期：2015年5月21日下午2:13:51
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年5月21日下午2:13:51
 */
@Controller
@RequestMapping(value = "/alipay")
public class AlipayNotifyController extends BaseController{
	@Autowired
	private AlipayNotifyService alipayNotifyService;
	@Autowired
	private LogService logService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/notify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response){
		HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】收到支付宝异步通知");
//		HgLogger.getInstance().error("cs", "【支付宝】支付平台异步回调请求："+JSON.toJSONString(request));
		PrintWriter out = getPrintWriter(response,"utf-8");
		String out_trade_no = "";
		try{
			//获取支付宝反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数
		    out_trade_no = new String(request.getParameter("out_trade_no"));//商户订单号
		    HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【商户订单号】："+JSON.toJSONString(out_trade_no));
			String trade_status = new String(request.getParameter("trade_status"));//交易状态
			HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【交易状态】："+JSON.toJSONString(trade_status));
			String payeeTradeNo = new String(request.getParameter("trade_no"));//支付宝订单号
			HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【支付宝订单号】："+JSON.toJSONString(payeeTradeNo));
			String payerAccount = new String(request.getParameter("buyer_email"));//支付宝付款方帐号
			HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【支付宝付款方帐号】："+JSON.toJSONString(payerAccount));
			Double price = new Double(request.getParameter("price"));//支付宝付款方帐号
			HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【付款金额】："+JSON.toJSONString(price));
			AlipayNotify notify = new AlipayNotify();
			boolean flag=notify.verify(params);
			HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】验签结果***************************"+flag+"****************************");
			//----------本地测试-----------------
//				if(true){
			//----------本地测试-----------------
			if(flag){//验证成功
				//业务逻辑处理都放在异步中处理
				HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】支付平台验证成功");
//				if(true){
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】支付状态验证成功开始本地化");
					AlipayCommand alipayCommand = new AlipayCommand();
					alipayCommand.setOrderNO(out_trade_no);
					alipayCommand.setPrice(price);
					alipayCommand.setTrade_no(payeeTradeNo);
					alipayCommand.setBuyer_email(payerAccount);
					SystemCommunicationLogNotifyCommand command = new SystemCommunicationLogNotifyCommand();
					command.setNotifyHost("支付宝收款");
					command.setNotifyContent(JSON.toJSONString(alipayCommand));
					logService.saveLog(command);
					if(alipayNotifyService.payGNFlight(alipayCommand)){
						HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】更改本地支付状态成功，异步回调成功");
						out.write("success");
					}else{
						HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】更改本地支付状态失败，异步回调失败");
						out.write("fail");
					}
				}else{
					HgLogger.getInstance().info("cs", "【alipayNotify】"+"【支付宝】支付平台支付宝订单" + out_trade_no + "，订单号"+out_trade_no+"支付失败");
					out.write("fail");
				}
			}else{//验证失败
				HgLogger.getInstance().info("cs", "【alipayNotify】"+"【支付宝】支付平台支付宝订单" + out_trade_no + "异步回调验证失败");
				out.write("fail");
			}
			//通知支付宝异步回调成功
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【alipayNotify】"+"【支付宝】支付平台支付宝订单" + out_trade_no + "异步回调失败:"+HgLogger.getStackTrace(e));
			out.write("fail");
		}
	}
	
	/**
	 * 支付宝异步通知地址      web处理方式
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/h5notify")
	public void notify(HttpServletRequest request, HttpServletResponse response){
		HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】收到支付宝异步通知");
		PrintWriter out = getPrintWriter(response,"utf-8");
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
		//支付金额
		String total_fee = "";
		boolean verifyNotify = false;
		try {
			verifyNotify = zzpl.api.util.alipay.h5.AlipayNotify.verifyNotify(params,false);
			Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
			
			out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
			trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
			trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();
			buyer_email = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();
			total_fee = doc_notify_data.selectSingleNode("//notify/total_fee").getText();
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "ApiController->notify->获取参数为空:" 
					+"|out_trade_no="+out_trade_no
					+"|trade_no"+trade_no
					+"|trade_status"+trade_status
					+ HgLogger.getStackTrace(e));
			return;
		}
		HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】支付平台异步回调请求【商户订单号】："+JSON.toJSONString(out_trade_no));
		HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】支付平台异步回调请求【支付宝交易号】："+JSON.toJSONString(trade_no));
		HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】支付平台异步回调请求【交易状态】："+JSON.toJSONString(trade_status));
		HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】支付平台异步回调请求【付款支付宝账号】："+JSON.toJSONString(buyer_email));
		HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】支付平台异步回调请求【付款金额】："+JSON.toJSONString(total_fee));
		HgLogger.getInstance().info("zhangka", "out_trade_no="+out_trade_no+",trade_no="+trade_no+",trade_status="+trade_status);

		if (verifyNotify) {//验证成功
			if (trade_status.equals("TRADE_SUCCESS")||trade_status.equals("TRADE_FINISHED")) {
				HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】支付状态验证成功开始本地化");
				AlipayCommand alipayCommand = new AlipayCommand();
				alipayCommand.setOrderNO(out_trade_no);
				alipayCommand.setPrice(Double.valueOf(total_fee));
				alipayCommand.setTrade_no(trade_no);
				alipayCommand.setBuyer_email(buyer_email);
				SystemCommunicationLogNotifyCommand command = new SystemCommunicationLogNotifyCommand();
				command.setNotifyHost("支付宝退款");
				command.setNotifyContent(JSON.toJSONString(alipayCommand));
				logService.saveLog(command);
				if(alipayNotifyService.payGNFlight(alipayCommand)){
					HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】更改本地支付状态成功，异步回调成功");
					out.write("success");
				}else{
					HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】更改本地支付状态失败，异步回调失败");
					out.write("fail");
				}
			} else {//验证失败
				out.write("fail");
			}
		}else{
			out.write("fail");
		}
	/*	//发送短信通知用户付款成功
		HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】更改本地支付状态成功，异步回调成功");
		out.write("success");*/
	}
	
	
	/**
	 * 获得response返回字符编码输出流对象
	 * @param response 
	 * @param encoding 需要设置的字符集
	 * @return
	 */
	public static PrintWriter getPrintWriter(HttpServletResponse response,String encoding) {
		response.reset();
		response.setContentType("text/html;charset=" + encoding);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			return out;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
