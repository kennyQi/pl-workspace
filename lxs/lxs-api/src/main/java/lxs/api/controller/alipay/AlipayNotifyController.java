package lxs.api.controller.alipay;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import lxs.api.controller.BaseController;
import lxs.api.controller.util.AlipayNotify;
import lxs.app.service.line.LineOrderLocalService;
import lxs.app.service.mp.AppService;
import lxs.app.service.mp.TicketOrderService;
import lxs.pojo.command.line.AlipayCommand;
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
	private LineOrderLocalService lineOrderLocalService;
	@Autowired
	private TicketOrderService ticketOrderService;
	@Autowired
	private AppService appService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/notify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response){
		HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】收到支付宝异步通知");
//		HgLogger.getInstance().error("lxs_dev", "【支付宝】支付平台异步回调请求："+JSON.toJSONString(request));
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
		    HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【商户订单号】："+JSON.toJSONString(out_trade_no));
			String trade_status = new String(request.getParameter("trade_status"));//交易状态
			HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【交易状态】："+JSON.toJSONString(trade_status));
			String payeeTradeNo = new String(request.getParameter("trade_no"));//支付宝订单号
			HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【支付宝订单号】："+JSON.toJSONString(payeeTradeNo));
			String payerAccount = new String(request.getParameter("buyer_email"));//支付宝付款方帐号
			HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【支付宝付款方帐号】："+JSON.toJSONString(payerAccount));
			Double price = new Double(request.getParameter("price"));//支付宝付款方帐号
			HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】支付平台异步回调请求【付款金额】："+JSON.toJSONString(price));
			AlipayNotify notify = new AlipayNotify();
			boolean flag=notify.verify(params);
			HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】验签结果***************************"+flag+"****************************");
			if(flag){//验证成功
			//----------本地测试-----------------
//				if(true){
			//----------本地测试-----------------
				//业务逻辑处理都放在异步中处理
				HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】支付平台验证成功");
				if(trade_status.equals("TRADE_SUCCESS")){
					HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】支付状态验证成功开始本地化");
					AlipayCommand alipayCommand = new AlipayCommand();
					alipayCommand.setDealerOrderNo(out_trade_no);
					alipayCommand.setPrice(price);
					alipayCommand.setPaymentAccount(payerAccount);
					alipayCommand.setSerialNumber(payeeTradeNo);
					alipayCommand.setRequestType(AlipayCommand.ALIPAY);
					if(StringUtils.startsWith(out_trade_no, "MP")){
						lxs.pojo.command.mp.AlipayCommand mpAlipayCommand = JSON.parseObject(JSON.toJSONString(alipayCommand), lxs.pojo.command.mp.AlipayCommand.class);
						if(ticketOrderService.receviedAlipayNotify(mpAlipayCommand)){
							HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】更改本地门票支付状态成功，异步回调成功");
							out.write("success");
						}else{
							HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】更改本地门票支付状态失败，异步回调失败");
							out.write("fail");
						}
					}else{
						if(lineOrderLocalService.payLineOrder(alipayCommand)){
							HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】更改本地支付状态成功，异步回调成功");
							out.write("success");
						}else{
							HgLogger.getInstance().error("lxs_dev", "【alipayNotify】"+"【支付宝】更改本地支付状态失败，异步回调失败");
							out.write("fail");
						}
					}
				}else{
					HgLogger.getInstance().info("lxs_dev", "【alipayNotify】"+"【支付宝】支付平台支付宝订单" + out_trade_no + "，订单号"+out_trade_no+"支付失败");
					out.write("fail");
				}
			}else{//验证失败
				HgLogger.getInstance().info("lxs_dev", "【alipayNotify】"+"【支付宝】支付平台支付宝订单" + out_trade_no + "异步回调验证失败");
				out.write("fail");
			}
			//通知支付宝异步回调成功
		}catch(Exception e){
			HgLogger.getInstance().info("lxs_dev", "【alipayNotify】"+"【支付宝】支付平台支付宝订单" + out_trade_no + "异步回调失败:"+HgLogger.getStackTrace(e));
			e.printStackTrace();
			out.write("fail");
		}
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
	
	/**
	 * 接受支付宝订单退款结果
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/paybackResult")
	public String paybackResult(HttpServletRequest request){
		HgLogger.getInstance().info("cs", "【接收到退款通知】");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		if(requestParams==null||requestParams.isEmpty()){
			HgLogger.getInstance().warn("zhuxy", "【接收到退款通知】ApiController->paybackResult->params is null or empty");
			return "fail";
		}
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
		
		HgLogger.getInstance().info("zhuxy", "【接收到退款通知】ApiController->paybackResult->params:" + sb.toString());
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		String result_details = request.getParameter("result_details");	//处理结果详情
		HgLogger.getInstance().info("zhuxy", "【接收到退款通知】ApiController->paybackResult->result_details:" + result_details);
		if (lxs.app.util.alipay.refund.AlipayNotify.verify(params)) {//验证成功
			//修改订单状态并且激活已使用的卡券
			try {
				appService.refundSuccess(result_details);
			} catch (Exception e) {
				return "fail";
			}
			return "success";
		} else {//验证失败
			HgLogger.getInstance().info("zhuxy", "【接收到退款通知】ApiController->paybackResult->验证失败");
			return "fail";
		}
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/testAlipay")
	public void TestAlipay(HttpServletRequest request, HttpServletResponse response){
		AlipayCommand alipayCommand = new AlipayCommand();
		alipayCommand.setDealerOrderNo("B716160819110000_w");
		alipayCommand.setPrice(0.01);
		lineOrderLocalService.payLineOrder(alipayCommand);
	}
}
