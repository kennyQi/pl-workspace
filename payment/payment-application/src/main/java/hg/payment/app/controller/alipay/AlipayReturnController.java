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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONArray;



/**
 * 
 *@类功能说明：支付宝同步回调
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月4日下午2:09:33
 *
 */
@Controller
@RequestMapping(value = "/alipay")
public class AlipayReturnController extends BaseController{

	@Autowired
	private PayOrderLocalService payOrderLocalService;
	@Autowired
	private PaymentClientCacheManager cache;
	@Autowired
	AlipayPayOrderLocalService alipayPayOrderLocalService;
	@Autowired
	private AlipayPayChannel alipay;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/return")
	public ModelAndView aliPayreturn(HttpServletRequest request, HttpServletResponse response,Model model){
		
		String out_trade_no = null;
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
			//日志记录支付宝同步回调的反馈信息
			HgLogger.getInstance().info("luoyun", "支付平台支付宝订单"+ out_trade_no + "同步回调的反馈信息："+JSONArray.toJSONString(params));
			
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
			//同步不做业务逻辑 只做页面跳转
			//跳转到商城的重定向地址，并提供商城订单编号，让商城通过接口查询订单状态
			PayOrderLocalQO qo = new PayOrderLocalQO();
			qo.setId(out_trade_no);
			PayOrder payOrder = payOrderLocalService.queryUnique(qo);
			PaymentClient client = cache.getPaymentClient(payOrder.getPaymentClient().getId());
			String messageUrl = client.getClientReturnURL();
			model.addAttribute("orderNo", payOrder.getClientTradeNo());
			ModelAndView view = new ModelAndView(new RedirectView(messageUrl,true));
			
			//日志记录支付宝同步回调的反馈信息
			HgLogger.getInstance().info("luoyun", "支付平台支付宝订单"+ out_trade_no + "同步验证成功，请求商户地址为："+messageUrl);
			return view;
			
			}else{ //验证失败
				HgLogger.getInstance().error("luoyun", "支付平台支付宝订单"  + out_trade_no + "同步回调验证失败");
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "支付平台支付宝订单" + out_trade_no + "同步回调失败:"+e.getMessage());
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	
	
}
