package hg.payment.app.controller.hjb;

import hg.log.util.HgLogger;
import hg.payment.app.cache.HJBMessageCodeCacheManager;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.controller.BaseController;
import hg.payment.app.pojo.qo.payorder.PayOrderLocalQO;
import hg.payment.app.service.local.payorder.PayOrderLocalService;
import hg.payment.domain.common.util.hjb.DesUtil;
import hg.payment.domain.common.util.hjb.HJBNotifyResp;
import hg.payment.domain.common.util.hjb.HJBUtils;
import hg.payment.domain.common.util.hjb.XmlUtil;
import hg.payment.domain.model.channel.hjbPay.HJBPayChannel;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.payorder.hjbPay.HJBPayOrder;
import hg.payment.pojo.command.admin.ModifyPayOrderCommand;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 
 *@类功能说明：汇金宝异步回调
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月18日上午9:06:20
 *
 */
@Controller
@RequestMapping(value = "/hjb")
public class HJBNotifyController extends BaseController{
	
	@Autowired
	private PayOrderLocalService payOrderLocalService;
	@Autowired
	private PaymentClientCacheManager cache;
	@Autowired
	private HJBMessageCodeCacheManager hjbMessageCodeCacheManager;
	@Autowired
	private HJBPayChannel hjbPay;
	
	@RequestMapping(value = "/notify")
	public void hjbNotify(HttpServletRequest request,HttpServletResponse response){
		
		String tradeNo = "";
		String hjbOrderNo = "";
		String notifyAddr = ""; 
		
		try{
			
			String result = DesUtil.getDecryptString(request.getParameter("respData"));
			HgLogger.getInstance().debug("luoyun", "【汇金宝】支付平台汇金宝订单异步回调的反馈信息：" + result);
			
			tradeNo = XmlUtil.getNodeVal(result, "orderNo");
			hjbOrderNo = XmlUtil.getNodeVal(result, "hjbOrderNo");
			String tradeStatus = XmlUtil.getNodeVal(result, "status");
			String returnCode = XmlUtil.getNodeVal(result, "hostReturnCode");
			notifyAddr = XmlUtil.getNodeVal(result, "notifyAddr");
			
			String hjbMessage = hjbMessageCodeCacheManager.getMessageByCode(returnCode);
			ModifyPayOrderCommand modifyCommand = new ModifyPayOrderCommand();
			modifyCommand.setPayerAccount("");
			modifyCommand.setThirdPartyTradeNo(hjbOrderNo);
			modifyCommand.setPayOrderId(tradeNo);
			
			if(HJBPayOrder.paySuccess.equals(tradeStatus)){  //支付成功
				modifyCommand.setPaySuccess(true);
				
			}else if(HJBPayOrder.payFail.equals(tradeStatus)){ //支付失败
				modifyCommand.setPaySuccess(false);
				modifyCommand.setOrderRemark(returnCode + hjbMessage);
			}
			payOrderLocalService.payOrder(modifyCommand);
			
			PaymentClient client = null;
			PayOrder payOrder = null;
			try{
				PayOrderLocalQO qo = new PayOrderLocalQO();
				qo.setId(tradeNo);
				payOrder = payOrderLocalService.queryUnique(qo);
				client = cache.getPaymentClient(payOrder.getPaymentClient().getId());
				//通知商城支付结果
				payOrderLocalService.notifyClientPay(tradeNo);
			}catch(Exception e){
				HgLogger.getInstance().error("luoyun", "【汇金宝】异步通知" + client.getName() + "订单"+ payOrder.getId() + "失败:" + e.getMessage());
				e.printStackTrace();
			}
			
			
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "【汇金宝】支付平台汇金宝订单" + tradeNo + "异步回调失败" + e.getMessage());
			
		}
		
		HJBNotifyResp hjbNotifyResp = new HJBNotifyResp();
		hjbNotifyResp.setStatus("");
		hjbNotifyResp.setMessage("");
		hjbNotifyResp.setOrderNo(hjbOrderNo);
		
		String message = HJBUtils.buildNotifyResponseParam(hjbNotifyResp);
		
		HttpClient client = new HttpClient(); 
		
		PostMethod method = new PostMethod(notifyAddr);  
		method.addParameter("respData", message);
		HttpMethodParams param = method.getParams();  
		param.setContentCharset("UTF-8");  
		try {
			int result = client.executeMethod(method);
			HgLogger.getInstance().error("luoyun", "【汇金宝】支付平台汇金宝订单" + tradeNo + "异步回调响应给汇金宝" + notifyAddr + "返回值" + result);
		} catch (Exception e) {
			HgLogger.getInstance().error("luoyun", "【汇金宝】支付平台汇金宝订单" + tradeNo + "异步回调响应给汇金宝失败" + e.getMessage());
			
		} 
		
		
	}
	

}
