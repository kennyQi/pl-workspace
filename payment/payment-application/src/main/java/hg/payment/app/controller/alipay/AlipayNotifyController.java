package hg.payment.app.controller.alipay;

import hg.log.util.HgLogger;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.controller.BaseController;
import hg.payment.app.pojo.qo.payorder.PayOrderLocalQO;
import hg.payment.app.service.local.payorder.AlipayPayOrderLocalService;
import hg.payment.app.service.local.payorder.PayOrderLocalService;
import hg.payment.domain.common.util.alipay.util.AlipayNotify;
import hg.payment.domain.model.channel.alipay.AlipayPayChannel;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.payorder.alipay.AlipayPayOrder;
import hg.payment.pojo.command.admin.ModifyPayOrderCommand;

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

import com.alibaba.fastjson.JSONArray;


/**
 * 
 *@类功能说明：支付宝异步回调
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月4日下午2:10:12
 *
 */
@Controller
@RequestMapping(value = "/alipay")
public class AlipayNotifyController extends BaseController{

//	@Autowired
//	private AlipayNotify notify;
	@Autowired
	private PayOrderLocalService payOrderLocalService;
	@Autowired
	private PaymentClientCacheManager cache;
	@Autowired
	AlipayPayOrderLocalService alipayPayOrderLocalService;
	@Autowired
	private AlipayPayChannel alipay;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/notify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = super.getPrintWriter(response,"utf-8");
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
			String trade_status = new String(request.getParameter("trade_status"));//交易状态
			String payeeTradeNo = new String(request.getParameter("trade_no"));//支付宝订单号
			String payerAccount = new String(request.getParameter("buyer_email"));//支付宝付款方帐号
			
			//退款回调不在此处处理
			if(StringUtils.isNotBlank(request.getParameter("refund_status"))){
				out.write("success");
				return;
			} 
			
			ModifyPayOrderCommand modifyCommand = new ModifyPayOrderCommand();
			modifyCommand.setPayerAccount(payerAccount);
			modifyCommand.setThirdPartyTradeNo(payeeTradeNo);
			modifyCommand.setPayOrderId(out_trade_no);
			//日志记录支付宝同步回调的反馈信息
			HgLogger.getInstance().info("luoyun", "【支付宝】支付平台支付宝订单" + out_trade_no+ "异步回调的反馈信息:"+JSONArray.toJSONString(params));
			
			
			AlipayPayOrder alipayPayOrder = new AlipayPayOrder();
			alipayPayOrder = alipayPayOrderLocalService.get(out_trade_no);
			if(alipayPayOrder == null){
				HgLogger.getInstance().info("luoyun", "【支付宝】支付平台支付宝订单" + out_trade_no+ "不存在");
			}
			
			AlipayNotify notify = new AlipayNotify();
			notify.setAlipay(alipay);
			notify.setKeys(alipayPayOrder.getKeys());
			notify.setPartner(alipayPayOrder.getPartner());
			
			if(notify.verify(params)){//验证成功
				//业务逻辑处理都放在异步中处理
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					//设置订单支付状态为第三方反馈支付成功 
					modifyCommand.setPaySuccess(true);
					
				}else{
					//设置订单支付状态为第三方反馈支付失败
					modifyCommand.setPaySuccess(false);
				}
				payOrderLocalService.payOrder(modifyCommand);
				
				PaymentClient client = null;
				PayOrder payOrder = null;
				try{
					PayOrderLocalQO qo = new PayOrderLocalQO();
					qo.setId(out_trade_no);
					payOrder = payOrderLocalService.queryUnique(qo);
					client = cache.getPaymentClient(payOrder.getPaymentClient().getId());
					//通知商城支付结果
					payOrderLocalService.notifyClientPay(out_trade_no);
				}catch(Exception e){
					HgLogger.getInstance().error("luoyun", "【支付宝】异步通知" + client.getName() + "支付宝订单"+ payOrder.getId() + "失败:" + e.getMessage());
					e.printStackTrace();
				}
				//通知支付宝异步回调成功
				out.write("success");
			}else{//验证失败
				HgLogger.getInstance().info("luoyun", "【支付宝】支付平台支付宝订单" + out_trade_no + "异步回调验证失败");
				out.write("fail");
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "【支付宝】支付平台支付宝订单" + out_trade_no + "异步回调失败:"+e.getMessage());
			e.printStackTrace();
			out.write("fail");
		}
		
	}
	
}
