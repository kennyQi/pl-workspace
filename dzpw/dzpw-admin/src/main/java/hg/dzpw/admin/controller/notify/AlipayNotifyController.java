package hg.dzpw.admin.controller.notify;

import java.util.Date;
import java.util.Map;

import hg.common.component.RedisLock;
import hg.dzpw.app.common.util.alipay.AlipayCore;
import hg.dzpw.app.common.util.alipay.AlipayReflectUtil;
import hg.dzpw.app.common.util.alipay.AlipaySubmitUtil;
import hg.dzpw.app.component.manager.DealerRefundNotifyCacheManager;
import hg.dzpw.app.pojo.qo.AliPayTransferRecordQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.service.local.AliPayTransferRecordLocalService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.SingleTicketLocalService;
import hg.dzpw.app.service.local.TicketOrderLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.dealer.DealerRefundNotifyRecord;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.pay.AliPayTransferRecord;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.pojo.api.alipay.RefundDetailData;
import hg.dzpw.pojo.api.alipay.RefundDetailData.MainTrade;
import hg.dzpw.pojo.api.alipay.RefundNotifyRequest;
import hg.log.util.HgLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：支付宝异步通知响应处理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-23上午11:02:28
 * @版本：
 */
@Controller
public class AlipayNotifyController {

	@Autowired
	private AliPayTransferRecordLocalService transferRecordLocalService;
	@Autowired
	private SingleTicketLocalService singleTicketLocalService;
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	@Autowired
	private TicketOrderLocalService ticketOrderLocalService;
	@Autowired
	private DealerRefundNotifyCacheManager dealerRefundNotifyCacheManager;
	/**
	 * 
	 * @描述： 支付宝退款异步通知请求
	 * @param notifyRequest
	 *            支付宝通知参数
	 * @author: guotx
	 * @version: 2016-3-23 上午11:22:39
	 */
	@RequestMapping("refundNotify")
	@ResponseBody
	public String alipayRefundNotify(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute RefundNotifyRequest notifyRequest) {
		HgLogger.getInstance().info("guotx", "【支付宝退款异步通知】请求参数："+JSON.toJSONString(notifyRequest));
		if (!checkSign(notifyRequest)) {
			// 签名错误
			HgLogger.getInstance().info("guotx", "【支付宝退款异步通知】签名错误");
			return null;
		}

		// 获取本次通知对应的退款批次号
		String batch_no = notifyRequest.getBatch_no();
		// 解析返回退款交易信息，默认只解析第一个，也就是申请退款的时候也是默认每个申请只有一笔交易
		RefundDetailData refundDetailData = RefundDetailData
				.parseResultDetails(notifyRequest.getResult_details());
		// 查询并更新此次退款申请记录
		AliPayTransferRecordQo transferRecordQo = new AliPayTransferRecordQo();
		transferRecordQo.setRefundBatchNo(batch_no);
		AliPayTransferRecord alipayTransferReford = transferRecordLocalService
				.queryUnique(transferRecordQo);
		if (alipayTransferReford==null) {
			HgLogger.getInstance().info("guotx", "【支付宝退款异步通知】根据batchNo查询不到退款申请记录"+batch_no);
		}
		MainTrade mainTrade = refundDetailData.getMainTrade();
		// 原支付交易号
		String tradeNo = mainTrade.getTradeNo();
		if (alipayTransferReford.getTradeNo().equals(tradeNo)) {
			// 同一个交易的退款,而且要保证接口返回的是success
			if (mainTrade.getResultCode().equalsIgnoreCase("success")) {
				//获取支付记录中的转入传出账户
				AliPayTransferRecord payTransferRecord=null;
				transferRecordQo.setRefundBatchNo(null);
				transferRecordQo.setTradeNo(tradeNo);
				transferRecordQo.setType(AliPayTransferRecord.TYPE_DEALER_TO_DZPW);
				payTransferRecord=transferRecordLocalService.queryUnique(transferRecordQo);
				if (payTransferRecord!=null) {
					alipayTransferReford.setTransAccountIn(payTransferRecord.getTransAccountOut());
					alipayTransferReford.setTransAccountOut(payTransferRecord.getTransAccountIn());
				}
				alipayTransferReford.setIsSuccess(true);
				//通知经销商,添加到redis的通知队列中，同时修改门票状态
				addPublicTaskRecord(alipayTransferReford.getGroupTicketId(),batch_no);
				
				alipayTransferReford.setStatus("REFUND_SUCCESS");
				// 修改门票订单状态,加锁
				RedisLock orderLock=new RedisLock(alipayTransferReford.getTicketOrderId());
				while(true){
					if(orderLock.tryLock()){
						//修改景区状态为退款成功
						singleTicketLocalService.updateRefundSuccessStatus(alipayTransferReford.getGroupTicketId());
						singleTicketLocalService.updateGroupTicketAndOrderStatus(alipayTransferReford.getGroupTicketId());
						HgLogger.getInstance().info("guotx", "【支付宝退款异步通知】更新门票订单和景区状态完成");
						orderLock.unlock();
						break;
					}else {
						HgLogger.getInstance().info("guotx", "【支付宝退款异步通知】修改状态时获取门票锁失败，订单id："+alipayTransferReford.getTicketOrderId());
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				HgLogger.getInstance().info("guotx", "【支付宝退款异步通知，退款失败，错误代码：】"+mainTrade.getResultCode());
				alipayTransferReford.setErrorCode(mainTrade.getResultCode());
				alipayTransferReford.setIsSuccess(false);
				alipayTransferReford.setStatus("REFUND_FAIL");
			}
		}else {
			HgLogger.getInstance().info("guotx", "【支付宝退款异步通知，通知参数中主交易号和申请记录中主交易号不匹配】");
			return null;
		}
		// 有返回结果
		alipayTransferReford.setHasResponse(true);

		alipayTransferReford.setResponseDataJson(JSON
				.toJSONString(notifyRequest));

		transferRecordLocalService.update(alipayTransferReford);

		return "success";

	}

	/**
	 * 
	 * @描述： 添加通知记录到redis库中，等待轮询,同时添加门票的退款批次号
	 * @author: guotx 
	 * @version: 2016-3-24 下午12:15:40
	 */
	@Transactional
	private void addPublicTaskRecord(String groupTicketId,String batchNo) {
		GroupTicketQo groupTicketQo=new GroupTicketQo();
		groupTicketQo.setId(groupTicketId);
		GroupTicket groupTicket = groupTicketLocalService.queryUnique(groupTicketQo);
		if (groupTicket!=null) {
			
			groupTicket.setRefundBatchNo(batchNo);
			groupTicket.getStatus().setRefundDate(new Date());
			groupTicketLocalService.update(groupTicket);
			
			TicketOrder ticketOrder = groupTicket.getTicketOrder();
			if (ticketOrder!=null) {
				TicketOrderQo ticketOrderQo=new TicketOrderQo();
				ticketOrderQo.setTicketQo(groupTicketQo);
				ticketOrder = ticketOrderLocalService.queryUnique(ticketOrderQo);
			}
			if (StringUtils.isBlank(ticketOrder.getBaseInfo().getDealerOrderId())) {
				//平台下单，非经销商接口下单,不用通知
				return;
			}
			DealerRefundNotifyRecord record=new DealerRefundNotifyRecord();
			record.setTicketNo(groupTicket.getTicketNo());
			Dealer fromDealer = ticketOrder.getBaseInfo().getFromDealer();
			String key = fromDealer.getClientInfo().getKey();
			record.setDealerKey(key);
			record.setHasNotifyNum(0);
			record.setNotifyDate(new Date());
			dealerRefundNotifyCacheManager.addRecord(record);
		}
	}


	/**
	 * 
	 * @描述： 请求参数签名校验
	 * 
	 * @author: guotx
	 * @param notifyRequest
	 *            请求的参数
	 * @version: 2016-3-23 下午1:26:20
	 */
	private boolean checkSign(RefundNotifyRequest notifyRequest) {
		Map<String, String> requestMap = AlipayReflectUtil
				.buildParameterMap(notifyRequest);
		Map<String, String> filterMap = AlipayCore.paraFilter(requestMap);
		String sign = AlipaySubmitUtil.buildRequestMysign(filterMap);
		if (sign.equals(notifyRequest.getSign())) {
			return true;
		}
		return false;
	}
	
}
