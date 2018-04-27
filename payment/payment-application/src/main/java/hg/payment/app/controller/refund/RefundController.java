package hg.payment.app.controller.refund;

import hg.payment.app.controller.BaseController;
import hg.payment.app.pojo.qo.refund.AlipayRefundLocalQO;
import hg.payment.app.service.local.payorder.PayOrderLocalService;
import hg.payment.app.service.local.refund.AlipayRefundLocalService;
import hg.payment.app.service.local.refund.RefundLocalService;
import hg.payment.domain.model.channel.PayChannel;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.refund.AlipayRefund;
import hg.payment.domain.model.refund.Refund;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * 
 *@类功能说明：退款
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月28日下午5:35:09
 *
 */
@Controller
@RequestMapping(value = "/refund")
public class RefundController extends BaseController{
	
	@Autowired
	private PayOrderLocalService payOrderService;
	@Autowired
	private AlipayRefundLocalService alipayRefundLocalService;
	@Autowired
	private RefundLocalService refundLocalService;
	
	/**
	 * 
	 * @param request 查询订单退款记录
	 * @param response
	 * @param model
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/detail")
	public String refundDetail(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value="id",required=true) String orderId){
		
		PayOrder payOrder = payOrderService.get(orderId);
		if(payOrder.getPayChannelType() == PayChannel.PAY_TYPE_ALIPAY){
			AlipayRefundLocalQO refundQO = new AlipayRefundLocalQO();
			refundQO.setTradeNo(orderId);
			List<AlipayRefund> refundList = new ArrayList<AlipayRefund>();
			refundList = alipayRefundLocalService.queryList(refundQO);
			model.addAttribute("refundList", refundList);
			
			//退款状态列表
			List<String> refundStatusList = new ArrayList<String>();
			//退款通知状态列表
			List<String> notifyStatusList = new ArrayList<String>();
			for(Refund refund:refundList){
				refundStatusList.add(refundLocalService.showRefundStatus(refund));
				notifyStatusList.add(refundLocalService.showRefundNotify(refund));
			}
			model.addAttribute("refundStatusList", refundStatusList);
			model.addAttribute("notifyStatusList", notifyStatusList);
			return "/refund/alipay_refund_detail.html";
		}
		
		return "";
		
		
	}
	

}
