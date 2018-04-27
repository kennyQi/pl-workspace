package hg.payment.app.controller.test;

import hg.log.util.HgLogger;
import hg.payment.pojo.dto.payorder.PayOrderDTO;
import hg.payment.pojo.exception.PaymentException;
import hg.payment.pojo.qo.payorder.PayOrderQO;
import hg.payment.spi.inter.PayOrderSpiService;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/notify")
public class NotifyTestController {
	
	@Autowired
	private PayOrderSpiService payOrderSpiService;
	
	@RequestMapping("/returnurl")
	public String clientReturnUrl(HttpServletRequest request, HttpServletResponse response){
		HgLogger.getInstance().debug("luoyun", "测试Test：同步通知商城成功");
		String orderNo = request.getParameter("orderNo");
		HgLogger.getInstance().debug("luoyun", "测试Test：同步通知商城，商城订单号：" + orderNo);
		PayOrderQO qo = new PayOrderQO();
		qo.setOrderNo(orderNo); //商城订单编号
		qo.setPaymentClientID("425078243aae407da092aaf3d4ab255b"); //客户端ID
		qo.setPaymentClienKeys("e10adc3949ba59abbe56e057f20f883e"); //客户端密钥
//		String json = "";
		try{
//			json = payOrderSpiService.queryPayOrder(qo);
//			HgLogger.getInstance().debug("luoyun", "测试Test：同步通知商城，商城查询订单状态结果：" + json);
//			JSONObject jsonObj = JSONObject.parseObject(json);
//			Boolean paySuccess = (Boolean) jsonObj.get("paySuccess");
//			HgLogger.getInstance().debug("luoyun", "测试Test：同步通知商城，商城查询订单是否支付成功" + paySuccess);
			
			qo.setPaySuccess(true);
			List<PayOrderDTO> payOrderList = payOrderSpiService.queryPayOrderList(qo);
			HgLogger.getInstance().debug("luoyun", "测试Test：同步通知商城，新接口-商城查询订单状态结果：" + JSONArray.toJSONString(payOrderList));
			HgLogger.getInstance().debug("luoyun", "测试Test：同步通知商城，新接口-商城查询订单是否支付成功：" + payOrderList.get(0).getPayStatus());
			
		}catch(PaymentException e){
			HgLogger.getInstance().error("luoyun", "向支付平台查询订单状态失败" + e.getMessage());
			e.printStackTrace();
		}
		AuthUser user = (AuthUser)request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		if(user!=null){
			return "/home.html";
		}else{
			return "/admin/login.html";
		}
	}
	
	@RequestMapping("/messageurl")
	public void clientMessageURLTest(HttpServletRequest request, HttpServletResponse response){
		
		HgLogger.getInstance().debug("luoyun", "测试Test：异步通知商城成功");
		
		String type = request.getParameter("type");
		if("PAY".equals(type)){
			
			String orderNo = request.getParameter("orderNo");
			HgLogger.getInstance().debug("luoyun", "测试Test：异步步通知商城，商城订单号：" + orderNo);
			PayOrderQO qo = new PayOrderQO();
			qo.setOrderNo(orderNo);
			qo.setPaymentClientID("425078243aae407da092aaf3d4ab255b");
			qo.setPaymentClienKeys("e10adc3949ba59abbe56e057f20f883e");
			String json = "";
			try{
				json = payOrderSpiService.queryPayOrder(qo);
				if(StringUtils.isNotBlank(json)){
					HgLogger.getInstance().debug("luoyun", "测试Test-旧接口：异步通知商城，商城查询订单状态结果：" + json);
					JSONObject jsonObj = JSONObject.parseObject(json);
					Boolean paySuccess = (Boolean) jsonObj.get("paySuccess");
					HgLogger.getInstance().debug("luoyun", "测试Test-旧接口：异步步通知商城，商城查询订单是否支付成功" + paySuccess);
				}
				qo.setPaySuccess(true);
				List<PayOrderDTO> payOrderList = payOrderSpiService.queryPayOrderList(qo);
				HgLogger.getInstance().debug("luoyun", "测试Test-新接口：异步通知商城，商城查询订单状态结果：" + JSONArray.toJSONString(payOrderList));
				if(payOrderList.size() == 0){
					HgLogger.getInstance().debug("luoyun", "测试Test-新接口：异步通知商城，新接口-商城查询订单是否支付成功：无结果");
				}else{
					HgLogger.getInstance().debug("luoyun", "测试Test-新接口：异步通知商城，新接口-商城查询订单是否支付成功：第一单结果" + payOrderList.get(0).getPayStatus());
				}
				
				
			}catch(PaymentException e){
				HgLogger.getInstance().error("luoyun", "向支付平台查询订单状态失败" + e.getMessage());
				e.printStackTrace();
			}
			
		}else if("ALIPAY_REFUND".equals(type)){
			String data = request.getParameter("refundList");
			HgLogger.getInstance().debug("luoyun", "测试Test：退款异步通知商城，退款信息：" + data);
		}
		

		
		
		
	}
	
	

}
