package hg.payment.app.controller.payorder;

import hg.common.page.Pagination;
import hg.log.util.HgLogger;
import hg.payment.app.cache.PaymentClientCacheManager;
import hg.payment.app.common.util.PayChannelUtils;
import hg.payment.app.controller.BaseController;
import hg.payment.app.pojo.qo.client.PaymentClientLocalQO;
import hg.payment.app.pojo.qo.payorder.PayOrderLocalQO;
import hg.payment.app.pojo.qo.refund.AlipayRefundLocalQO;
import hg.payment.app.service.local.client.PaymentClientLocalService;
import hg.payment.app.service.local.payorder.AlipayPayOrderLocalService;
import hg.payment.app.service.local.payorder.HJBPayOrderLocalService;
import hg.payment.app.service.local.payorder.PayOrderLocalService;
import hg.payment.app.service.local.refund.AlipayRefundLocalService;
import hg.payment.app.service.local.refund.RefundLocalService;
import hg.payment.domain.common.util.PaymentConstant;
import hg.payment.domain.model.channel.PayChannel;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.payorder.alipay.AlipayPayOrder;
import hg.payment.domain.model.payorder.hjbPay.HJBPayOrder;
import hg.payment.domain.model.refund.AlipayRefund;
import hg.system.model.backlog.Backlog;
import hg.system.qo.BacklogQo;
import hg.system.service.BacklogService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 *@类功能说明：支付订单列表
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：lixx
 *@创建时间：2014年9月10日下午2:57:49
 *
 */
@Controller
@RequestMapping(value = "/payorder")
public class PayOrderController extends BaseController{

	@Autowired
	private PayOrderLocalService payOrderService;
	@Autowired
	private PaymentClientLocalService clientService;
	@Autowired
	private AlipayPayOrderLocalService alipayPayOrderService;
	@Autowired
	private PaymentClientCacheManager cache;
	@Autowired
	private BacklogService backlogService;
	@Autowired
	private HJBPayOrderLocalService hjbPayorderLocalService;
	@Autowired
	private RefundLocalService refundLocalService;
	@Autowired
	private AlipayRefundLocalService alipayRefundLocalService;
	
	/**
	 * 查询所有的支付订单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/list")
	public String payOrderList(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute PayOrderLocalQO qo,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		
		try{
			
			if(qo == null){
				qo = new PayOrderLocalQO();
			}
			
			Pagination pagination = new Pagination();
			pagination.setPageNo(pageNo == null?new Integer(1):pageNo);
			pagination.setPageSize(pageSize == null?new Integer(20):pageSize);
			pagination.setCondition(qo);
			
			pagination = payOrderService.queryPagination(pagination);
			model.addAttribute("pagination", pagination);
			model.addAttribute("qo", qo);
			
			//订单支付状态列表
			List<String> payStatusList = new ArrayList<String>();
			//订单通知状态列表
			List<String> notifyStatusList = new ArrayList<String>();
			
			List<PaymentClient> clientList = new ArrayList<PaymentClient>();
			PaymentClientLocalQO clientQo = new PaymentClientLocalQO();
			clientList = clientService.queryList(clientQo);
			List<PayOrder> orderList = (List<PayOrder>) pagination.getList();
			for(PayOrder order:orderList){
				
				order.setPayChannel(PayChannelUtils.getPayChannel(order.getPayChannelType()));
				//判断订单页面显示的支付状态
				payStatusList.add(payOrderService.showOrderPayStatus(order));
				//判断订单页面显示的通知状态
				notifyStatusList.add(payOrderService.showOrderNotify(order));
				
			}
			
			model.addAttribute("paychannellist", PayChannelUtils.getPayChannelList());
			model.addAttribute("clientlist",clientList);
			model.addAttribute("paystatuslist",payStatusList);
			model.addAttribute("notifystatuslist",notifyStatusList);
			
			return "/payorder/payorder_list.html";
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "支付平台查询订单列表失败:"+e.getMessage());
			e.printStackTrace();
			model.addAttribute("message", "查询订单列表失败");
			return "/error/error.html";
		}
		
	}
	
	/**
	 * 查询订单详情
	 * @param request
	 * @param response
	 * @param model
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/detail")
	public String payOrderDetail(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value="id",required=true) String orderId){
		
		try{
			
			if(StringUtils.isBlank(orderId)){
				model.addAttribute("message", "查询订单详细失败,请提供订单编号");
				return "/error/error.html";
			}
			PayOrder payOrder = payOrderService.get(orderId);
			if(payOrder == null){
				model.addAttribute("message", "查询订单详细失败,编号为："+orderId+"的订单不存在");
				return "/error/error.html";
			}
			
			Integer orderType = payOrder.getPayChannelType();
			//查询支付宝订单详情
			if(PayChannel.PAY_TYPE_ALIPAY == orderType){
				
				AlipayPayOrder order = alipayPayOrderService.get(orderId);
				model.addAttribute("order", order);
				
				//订单支付状态
				String payStatus = "";
				//订单通知状态
				String notifyStatus = "";
				payStatus = payOrderService.showOrderPayStatus(order);
				notifyStatus = payOrderService.showOrderNotify(order);
				
				model.addAttribute("payStatus", payStatus);
				model.addAttribute("notifyStatus", notifyStatus);
				return "/payorder/alipayorder_detail.html";
			}else if(PayChannel.PAY_TYPE_HJB == orderType){
				HJBPayOrder order = hjbPayorderLocalService.get(orderId);
				model.addAttribute("order", order);
				
				//订单支付状态
				String payStatus = "";
				//订单通知状态
				String notifyStatus = "";
				payStatus = payOrderService.showOrderPayStatus(order);
				notifyStatus = payOrderService.showOrderNotify(order);
				
				model.addAttribute("payStatus", payStatus);
				model.addAttribute("notifyStatus", notifyStatus);
				return "/payorder/hjborder_detail.html";
			}
			
			return "";
			
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("luoyun", "支付平台查询订单详细失败:"+e.getMessage());
			model.addAttribute("message", "查询订单详细失败");
			return "/error/error.html";
		}
		
		
	}
	
	/**
	 * 定时任务执行controller，向商城发送通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/execute")
	public void executeBacklog(HttpServletRequest request,HttpServletResponse response){
		
		try{
			//查询需要执行的待办事项
			BacklogQo backlogQo = new BacklogQo();
			backlogQo.setSuccess(false);
			backlogQo.setExecuteCountValid(true);
			List<Backlog> backlogList = backlogService.queryList(backlogQo);
			for(Backlog backlog:backlogList){
				
				if(backlog.getBacklogInfo().getType() == PaymentConstant.BACKLOG_TYPE_PAYMENT_PAY){ //支付
					//解析支付平台下的待办事项中的描述信息，获取订单ID
					String orderId = (String)backlog.parseDescription("orderId");
					PayOrder payOrder = payOrderService.get(orderId);
					if(payOrder == null){
						HgLogger.getInstance().error("luoyun", "支付平台执行待办任务，向商城发送支付结果通知失败:订单" + orderId + "不存在");
						return ;
					}
					try{
						payOrderService.notifyClientPay(orderId);
					}catch(Exception e){
						PaymentClient client = cache.getPaymentClient(payOrder.getPaymentClient().getId());
						if(client != null){
							HgLogger.getInstance().error("luoyun","支付平台执行待办任务，向" + client.getName() + "发送支付结果通知失败:订单" + orderId +  e.getMessage());
						}else{
							HgLogger.getInstance().error("luoyun","支付平台执行待办任务,发送支付结果通知失败:订单" + orderId +  e.getMessage());
						}
						
						e.printStackTrace();
					}
				}else if(backlog.getBacklogInfo().getType() == PaymentConstant.BACKLOG_TYPE_PAYMENT_REFUND_ALIPAY){ //支付宝退款
					//解析支付平台下的待办事项中的描述信息，获取退款记录批次号
					String batchNo = (String)backlog.parseDescription("refundBatchNo");
					AlipayRefundLocalQO alipayRefundLocalQO = new AlipayRefundLocalQO();
					alipayRefundLocalQO.setRefundBatchNo(batchNo);
					List<AlipayRefund> alipayRefundList = new ArrayList<AlipayRefund>();
					alipayRefundList = alipayRefundLocalService.queryList(alipayRefundLocalQO);
					if(alipayRefundList.size() == 0){
						HgLogger.getInstance().error("luoyun", "支付平台执行待办任务，向商城发送退款结果通知失败:批次号" + batchNo + "不存在退款记录");
						return ;
					}
					try{
						alipayRefundLocalService.notifyClientRefundList(batchNo);
					}catch(Exception e){
						PaymentClient client = cache.getPaymentClient(alipayRefundList.get(0).getPayOrder().getPaymentClient().getId());
						if(client != null){
							HgLogger.getInstance().error("luoyun","支付平台执行待办任务，向" + client.getName() + "发送退款结果通知失败：退款记录批次号" + batchNo +  e.getMessage());
						}else{
							HgLogger.getInstance().error("luoyun","支付平台执行待办任务,发送退款结果通知失败:退款记录批次号" + batchNo +  e.getMessage());
						}
						
						e.printStackTrace();
					}
				}
				
			}
		}catch(Exception e){
			HgLogger.getInstance().error(PayOrderController.class,"luoyun", "支付平台定时任务执行待办事项失败",e);
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
