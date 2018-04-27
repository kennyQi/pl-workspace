package plfx.gjjp.app.service.local;

import hg.log.util.HgLogger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.api.client.api.v1.gj.event.GJCancelOrderEventMessage;
import plfx.api.client.api.v1.gj.event.GJOrderAuditEndEventMessage;
import plfx.api.client.api.v1.gj.event.GJOrderAuditEndEventMessage.PriceDetail;
import plfx.api.client.api.v1.gj.event.GJOutTicketFailureEventMessage;
import plfx.api.client.api.v1.gj.event.GJOutTicketSuccessEventMessage;
import plfx.api.client.api.v1.gj.event.GJPaySuccessEventMessage;
import plfx.api.client.api.v1.gj.event.GJRefundTicketEventMessage;
import plfx.api.client.common.publish.PublishEventRequest;
import plfx.gjjp.app.common.message.PlatformAmqpMessage;
import plfx.gjjp.app.common.message.YXGJJPAmqpMessage;
import plfx.gjjp.app.dao.GJJPOrderDao;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.gjjp.domain.model.GJPassenger;
import plfx.gjjp.domain.model.GJPassengerTicketPriceDetail;
import plfx.jp.command.api.gj.GJJPOrderAuditSuccessCommand;
import plfx.jp.command.api.gj.GJJPOrderAuditSuccessCommand.GJJPSupplierPolicy;
import plfx.jp.command.api.gj.GJRefundTicketCommand;
import plfx.jp.command.pub.gj.CreateGJJPOrderLogCommand;
import plfx.jp.common.ThreadTrackingTokenGenerator;
import plfx.jp.pojo.system.SupplierCode;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：处理易行国际接口通知服务
 * @类修改者：
 * @修改日期：2015-7-20下午1:52:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-20下午1:52:58
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GJJPProcessNotifyLocalService {

	@Autowired
	private GJJPOrderDao jpOrderDao;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private GJJPOrderLogLocalService orderLogLocalService;
	@Autowired
	private GJJPOrderLocalService jpOrderLocalService;

	@Autowired
	private GJPassengerLocalService passengerLocalService;
	
	private final static String devName = "zhurz";
	private HgLogger getHgLogger() {
		return HgLogger.getInstance();
	}

	/**
	 * @方法功能说明：处理支付成功通知1
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:52:11
	 * @修改内容：
	 * @参数：@param message
	 * @参数：@return
	 * @return:void
	 * @throws
	 */
	public void processPaySuccessNotify(YXGJJPAmqpMessage message) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理支付成功通知1->processPaySuccessNotify(%s)", trackingToken,
						JSON.toJSONString(message, true)), trackingToken);
		
		Map<String, String> map = message.getContent();
		String platformOrderId = map.get("extOrderId");
		String tradeNo = map.get("tradeNo");
		Double totalPrice = Double.valueOf(map.get("totalPrice"));
		Integer payPlatform = Integer.valueOf(map.get("payPlatform"));
		GJJPOrder jpOrder = jpOrderDao.get(platformOrderId);
		
		if (jpOrder == null) {
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理支付成功通知1->订单不存在(%s)", trackingToken,
							platformOrderId), trackingToken);
			return;
		}

		// 检查订单状态
		if (!GJJPConstants.ORDER_STATUS_PAY_WAIT.equals(jpOrder.getStatus().getCurrentValue())) {
			
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理支付成功通知1->非待支付状态停止处理(%s)", trackingToken,
							platformOrderId), trackingToken);
			
			return;
		}
		
		jpOrder.payToSupplierSuccess(payPlatform, tradeNo, totalPrice);
		jpOrderDao.update(jpOrder);

		// 通知经销商
		GJPaySuccessEventMessage paySuccessMsg = new GJPaySuccessEventMessage();
		paySuccessMsg.setPlatformOrderId(platformOrderId);
		paySuccessMsg.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		paySuccessMsg.setTotalPrice(jpOrder.getPayInfo().getTotalPrice());
		PublishEventRequest eventRequest = new PublishEventRequest(PublishEventRequest.EVENT_GJPaySuccess, JSON.toJSONString(paySuccessMsg));
		PlatformAmqpMessage platformMessage = new PlatformAmqpMessage(jpOrder.getBaseInfo().getFromDealer().getCode(), eventRequest);
		rabbitTemplate.convertAndSend("plfx.gjjp.platform.paySuccess", platformMessage);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理支付成功通知1->发布通知消息(%s)", trackingToken,
						JSON.toJSONString(platformMessage, true)), trackingToken);

		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "处理支付成功通知", SupplierCode.YEEXING_GJ,
				GJJPConstants.LOG_WORKER_TYPE_SUPPLIER, "处理支付成功通知完毕"));
		
	}

	/**
	 * @方法功能说明：处理取消订单通知2
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:49:57
	 * @修改内容：
	 * @参数：@param message
	 * @参数：@return
	 * @return:void
	 * @throws
	 */
	public void processCancelOrderNotify(YXGJJPAmqpMessage message) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理取消订单通知2->processCancelOrderNotify(%s)", trackingToken,
						JSON.toJSONString(message, true)), trackingToken);

		Map<String, String> map = message.getContent();
		String platformOrderId = map.get("extOrderId");
		// 取消状态 1：成功取消；2:拒绝取消
		Integer cancleStatus = Integer.valueOf(map.get("cancleStatus"));
		String cancleMemo = map.get("cancleMemo");
		GJJPOrder jpOrder = jpOrderDao.get(platformOrderId);

		if (jpOrder == null) {
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理取消订单通知2->订单不存在(%s)", trackingToken,
							platformOrderId), trackingToken);
			return;
		}
		
		// 检查订单状态
		if (!GJJPConstants.ORDER_STATUS_CANCEL_WAIT.equals(jpOrder.getStatus().getCurrentValue())) {
			
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理取消订单通知2->非待取消状态停止处理(%s)", trackingToken,
							platformOrderId), trackingToken);
			
			return;
		}

		if (Integer.valueOf(1).equals(cancleStatus)) {
			// 成功取消
			jpOrder.cancelToSupplierSuccess();
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理取消订单通知2->成功取消(%s)", trackingToken,
							platformOrderId), trackingToken);
		} else {
			// 拒绝取消
			jpOrder.cancelToSupplierFailure(cancleMemo);
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理取消订单通知2->拒绝取消(%s)", trackingToken,
							platformOrderId), trackingToken);
		}
		jpOrderDao.update(jpOrder);

		// 通知经销商
		GJCancelOrderEventMessage cancelOrderMsg = new GJCancelOrderEventMessage();
		cancelOrderMsg.setCancleStatus(cancleStatus);
		cancelOrderMsg.setCancleMemo(cancleMemo);
		cancelOrderMsg.setPlatformOrderId(platformOrderId);
		cancelOrderMsg.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		PublishEventRequest eventRequest = new PublishEventRequest(PublishEventRequest.EVENT_GJCancelOrder, JSON.toJSONString(cancelOrderMsg));
		PlatformAmqpMessage platformMessage = new PlatformAmqpMessage(jpOrder.getBaseInfo().getFromDealer().getCode(), eventRequest);
		rabbitTemplate.convertAndSend("plfx.gjjp.platform.cancelOrder", platformMessage);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理取消订单通知2->发布通知消息(%s)", trackingToken,
						JSON.toJSONString(platformMessage, true)), trackingToken);
		
		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "处理取消订单通知", SupplierCode.YEEXING_GJ,
				GJJPConstants.LOG_WORKER_TYPE_SUPPLIER, "处理取消订单通知完毕"));
		
	}

	/**
	 * @方法功能说明：处理审核完成通知6
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:54:20
	 * @修改内容：
	 * @参数：@param message
	 * @参数：@return
	 * @return:void
	 * @throws
	 */
	public void processOrderAuditEndNotify(YXGJJPAmqpMessage message) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理审核完成通知6->processOrderAuditEndNotify(%s)", trackingToken,
						JSON.toJSONString(message, true)), trackingToken);

		Map<String, String> map = message.getContent();

		String platformOrderId = map.get("extOrderId");
		// 审核状态 1-通过，2-退回
		Integer auditStatus = Integer.valueOf(map.get("auditStatus"));
		// 供应商订单状态
		Integer ordState = Integer.valueOf(map.get("ordState"));
		GJJPOrder jpOrder = jpOrderDao.get(platformOrderId);

		if (jpOrder == null) {
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理审核完成通知6->订单不存在(%s)", trackingToken,
							platformOrderId), trackingToken);
			return;
		}

		// 检查订单状态
		if (!GJJPConstants.ORDER_STATUS_AUDIT_WAIT.equals(jpOrder.getStatus().getCurrentValue())) {
			
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理审核完成通知6->非待审核状态停止处理(%s)", trackingToken,
							platformOrderId), trackingToken);
			
			return;
		}

		// 通知经销商
		GJOrderAuditEndEventMessage orderAuditEndMsg = new GJOrderAuditEndEventMessage();
		orderAuditEndMsg.setPlatformOrderId(platformOrderId);
		orderAuditEndMsg.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		orderAuditEndMsg.setAuditStatus(auditStatus);

		if (Integer.valueOf(1).equals(auditStatus)) {
			// 通过
			// 乘客类型
			String[] psgType = map.get("psgType").split("\\^");
			// 票面价 用 ^ 分隔
			String[] ticketPrice = map.get("ticketPrice").split("\\^");
			// 基础返点 对应乘客类型，用 ^ 分隔
			String[] basicDisc = map.get("basicDisc").split("\\^");
			// 奖励返点 对应乘客类型，用 ^ 分隔
			String[] extraDisc = map.get("extraDisc").split("\\^");
			// tax 税金 对应乘客类型，用 ^ 分隔
			String[] tax = map.get("tax").split("\\^");
			// 开票费 对应乘客类型，用 ^ 分隔
			String[] outTktMoney = map.get("outTktMoney").split("\\^");
			// 单张支付总价 对应乘客类型，用 ^ 分隔
			String[] ordDetPrice = map.get("ordDetPrice").split("\\^");
			// 订单支付总价
			Double ordAllPrice = Double.valueOf(map.get("ordAllPrice"));

			Map<Integer, GJJPSupplierPolicy> supplierPolicyMap = new HashMap<Integer, GJJPSupplierPolicy>();
			for (int i = 0; i < psgType.length; i++) {
				try {
					GJJPSupplierPolicy supplierPolicy = new GJJPSupplierPolicy();
					supplierPolicy.setPsgType(Integer.valueOf(psgType[i]));
					supplierPolicy.setTicketPrice(Double.valueOf(ticketPrice[i]));
					supplierPolicy.setBasicDisc(Double.valueOf(basicDisc[i]));
					supplierPolicy.setExtraDisc(Double.valueOf(extraDisc[i]));
					supplierPolicy.setTax(Double.valueOf(tax[i]));
					supplierPolicy.setOutTktMoney(Double.valueOf(outTktMoney[i]));
					supplierPolicy.setOrdDetPrice(Double.valueOf(ordDetPrice[i]));
					supplierPolicyMap.put(supplierPolicy.getPsgType(), supplierPolicy);
				} catch (Exception e) {
					// 记录日志
					getHgLogger().error(getClass(), devName,
							String.format("(%s)->处理审核完成通知6->供应商政策转换失败", trackingToken), e, trackingToken);
				}
			}
			
			GJJPOrderAuditSuccessCommand auditSuccessCommand = new GJJPOrderAuditSuccessCommand();
			auditSuccessCommand.setPolicyMap(supplierPolicyMap);
			auditSuccessCommand.setSupplierOrderTotalPrice(ordAllPrice);
			auditSuccessCommand.setSupplierOrderStatus(ordState);

			jpOrder.supplierAuditSuccess(auditSuccessCommand);
			jpOrderDao.update(jpOrder);

			Map<Integer, PriceDetail> priceDetailMap = new HashMap<Integer, PriceDetail>();
			for (GJPassenger passenger : jpOrder.getPassengers()) {
				if (priceDetailMap.containsKey(passenger.getBaseInfo().getPassengerType()))
					continue;
				PriceDetail priceDetail = new PriceDetail();
				GJPassengerTicketPriceDetail ticketPriceDetail = passenger.getTicketPriceDetail();
				priceDetail.setTicketPrice(ticketPriceDetail.getParValue());
				priceDetail.setTax(ticketPriceDetail.getSupplierTax());
				priceDetail.setOutTktMoney(ticketPriceDetail.getSupplierOutTickMoney());
				priceDetail.setPolicyPrice(ticketPriceDetail.getPlatformPolicy());
				priceDetail.setOrdDetPrice(ticketPriceDetail.getPlatformTotalPrice());
				priceDetailMap.put(passenger.getBaseInfo().getPassengerType(), priceDetail);
			}
			orderAuditEndMsg.setOrdAllPrice(jpOrder.getPayInfo().getTotalPrice());
			orderAuditEndMsg.setPriceDetailMap(priceDetailMap);
		} else if (Integer.valueOf(2).equals(auditStatus)) {
			// 退回
			jpOrder.supplierAuditFailure(ordState);
			jpOrderDao.update(jpOrder);
			// 客服审核退回原因
			orderAuditEndMsg.setRefuseMemo(map.get("refuseMemo"));
		}

		// 通知经销商
		PublishEventRequest eventRequest = new PublishEventRequest(PublishEventRequest.EVENT_GJOrderAuditEnd, JSON.toJSONString(orderAuditEndMsg));
		PlatformAmqpMessage platformMessage = new PlatformAmqpMessage(jpOrder.getBaseInfo().getFromDealer().getCode(), eventRequest);
		rabbitTemplate.convertAndSend("plfx.gjjp.platform.orderAuditEnd", platformMessage);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理审核完成通知6->发布通知消息(%s)", trackingToken,
						JSON.toJSONString(platformMessage, true)), trackingToken);

		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "处理审核完成通知", SupplierCode.YEEXING_GJ,
				GJJPConstants.LOG_WORKER_TYPE_SUPPLIER, "处理审核完成通知完毕"));
	}

	/**
	 * @方法功能说明：处理退废票结果通知4
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:54:43
	 * @修改内容：
	 * @参数：@param message
	 * @参数：@return
	 * @return:void
	 * @throws
	 */
	public void processRefundTicketNotify(YXGJJPAmqpMessage message) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理退废票结果通知4->processRefundTicketNotify(%s)", trackingToken,
						JSON.toJSONString(message, true)), trackingToken);
		
		Map<String, String> map = message.getContent();
		String platformOrderId = map.get("extOrderId");
		// 退票状态 1—成功2—拒绝退废票
		Integer refundStatus = Integer.valueOf(map.get("refundStatus"));
		// 乘客姓名
		String passengerName = map.get("passengerName");
		// 机票票号 多个票号之间用 ^ 分隔
		String[] eticketNo = map.get("eticketNo").split("\\^");
		// 拒绝退票理由
		String refuseMemo = map.get("refuseMemo");

		// 实退金额
		Double refundPrice = Double.valueOf(map.get("refundPrice") == null ? "0" : map.get("refundPrice"));
		// 退款手续费
		Double refundFee = Double.valueOf(map.get("refundFee") == null ? "0" : map.get("refundFee"));

		// 退废票处理
		GJRefundTicketCommand command = new GJRefundTicketCommand();
		command.setPlatformOrderId(platformOrderId);
		command.setPassengerName(passengerName);
		command.setEticketNo(Arrays.asList(eticketNo));
		command.setRefundPrice(refundPrice);
		command.setRefundStatus(refundStatus);
		command.setRefuseMemo(refuseMemo);
		command.setRefundFee(refundFee);
		GJPassenger passenger = passengerLocalService.refundSupplierTicket(command);

		if (passenger == null){
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理退废票结果通知4->passengerLocalService.refundSupplierTicket(%s)->乘客不存在", trackingToken,
							JSON.toJSONString(command, true)), trackingToken);
			return;
		}
		
		// 通知经销商
		GJJPOrder jpOrder = passenger.getJpOrder();
		GJRefundTicketEventMessage refundTicketMsg = new GJRefundTicketEventMessage();
		refundTicketMsg.setPlatformOrderId(platformOrderId);
		refundTicketMsg.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		refundTicketMsg.setPassengerName(passenger.getBaseInfo().getPassengerName());
		refundTicketMsg.setIdType(passenger.getBaseInfo().getIdType());
		refundTicketMsg.setIdNo(passenger.getBaseInfo().getIdNo());
		refundTicketMsg.setEticketNo(Arrays.asList(eticketNo));
		refundTicketMsg.setRefundPrice(passenger.getTicketRefundDetail().getRefundPrice());
		refundTicketMsg.setRefundStatus(refundStatus);
		refundTicketMsg.setRefuseMemo(refuseMemo);
		refundTicketMsg.setRefundFee(passenger.getTicketRefundDetail().getRefundFee());
		PublishEventRequest eventRequest = new PublishEventRequest(PublishEventRequest.EVENT_GJRefundTicket, JSON.toJSONString(refundTicketMsg));
		PlatformAmqpMessage platformMessage = new PlatformAmqpMessage(jpOrder.getBaseInfo().getFromDealer().getCode(), eventRequest);
		rabbitTemplate.convertAndSend("plfx.gjjp.platform.refundTicket", platformMessage);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理退废票结果通知4->发布通知消息(%s)", trackingToken,
						JSON.toJSONString(platformMessage, true)), trackingToken);
		
		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "处理退废票结果通知", SupplierCode.YEEXING_GJ,
				GJJPConstants.LOG_WORKER_TYPE_SUPPLIER, "处理退废票结果通知完毕"));
	}

	/**
	 * @方法功能说明：处理拒绝出票通知5
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午4:55:20
	 * @修改内容：
	 * @参数：@param message
	 * @参数：@return
	 * @return:void
	 * @throws
	 */
	public void processOutTicketFailureNotify(YXGJJPAmqpMessage message) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理拒绝出票通知5->processOutTicketFailureNotify(%s)", trackingToken,
						JSON.toJSONString(message, true)), trackingToken);

		Map<String, String> map = message.getContent();
		String platformOrderId = map.get("extOrderId");
		String refuseMemo = map.get("refuseMemo");

		GJJPOrder jpOrder = jpOrderDao.get(platformOrderId);
		if (jpOrder == null) {
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理拒绝出票通知5->订单不存在(%s)", trackingToken,
							platformOrderId), trackingToken);
			return;
		}

		// 检查订单状态
		if (!GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT.equals(jpOrder.getStatus().getCurrentValue())) {
			
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理拒绝出票通知5->非待出票状态停止处理(%s)", trackingToken,
							platformOrderId), trackingToken);
			
			return;
		}

		jpOrder.supplierOutTicketFailure(refuseMemo);
		jpOrderDao.update(jpOrder);

		// 通知经销商
		GJOutTicketFailureEventMessage outTicketFailureMsg = new GJOutTicketFailureEventMessage();
		outTicketFailureMsg.setPlatformOrderId(platformOrderId);
		outTicketFailureMsg.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		outTicketFailureMsg.setRefuseMemo(refuseMemo);
		PublishEventRequest eventRequest = new PublishEventRequest(PublishEventRequest.EVENT_GJOutTicketFailure, JSON.toJSONString(outTicketFailureMsg));
		PlatformAmqpMessage platformMessage = new PlatformAmqpMessage(jpOrder.getBaseInfo().getFromDealer().getCode(), eventRequest);
		rabbitTemplate.convertAndSend("plfx.gjjp.platform.outTicketFailure", platformMessage);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理拒绝出票通知5->发布通知消息(%s)", trackingToken,
						JSON.toJSONString(platformMessage, true)), trackingToken);

		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "处理拒绝出票通知", SupplierCode.YEEXING_GJ,
				GJJPConstants.LOG_WORKER_TYPE_SUPPLIER, "处理拒绝出票通知完毕"));
		
	}

	/**
	 * @方法功能说明：处理出票成功通知3
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午5:01:04
	 * @修改内容：
	 * @参数：@param message
	 * @return:void
	 * @throws
	 */
	public void processOutTicketSuccessNotify(YXGJJPAmqpMessage message) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理出票成功通知3->processOutTicketSuccessNotify(%s)", trackingToken,
						JSON.toJSONString(message, true)), trackingToken);

		Map<String, String> map = message.getContent();
		String platformOrderId = map.get("extOrderId");

		// 乘客姓名 姓名之间用竖杠“|”分隔
		String[] passengerName = map.get("passengerName").split("\\^");
		// 票号 每个乘客对应的多个票号之间用“^”分隔，每组票号之间用竖杠“|”分隔，和乘客姓名对应
		String[] eticketNo = map.get("eticketNo").split("\\|");

		GJJPOrder jpOrder = jpOrderDao.get(platformOrderId);
		
		if (jpOrder == null) {
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理出票成功通知3->订单不存在(%s)", trackingToken,
							platformOrderId), trackingToken);
			return;
		}

		// 检查订单状态
		if (!GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT.equals(jpOrder.getStatus().getCurrentValue())) {
			
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->处理出票成功通知3->非待出票状态停止处理(%s)", trackingToken,
							platformOrderId), trackingToken);
			
			return;
		}

		Map<String, List<String>> passengerTicketMap = new HashMap<String, List<String>>();
		for (int i = 0; i < passengerName.length && i < eticketNo.length; i++)
			passengerTicketMap.put(passengerName[i], Arrays.asList(eticketNo[i].split("\\^")));

		jpOrder.supplierOutTicketSuccess(passengerTicketMap);
		jpOrderDao.update(jpOrder);

		// 通知经销商
		GJOutTicketSuccessEventMessage outTicketSuccessMsg = new GJOutTicketSuccessEventMessage();
		outTicketSuccessMsg.setPlatformOrderId(platformOrderId);
		outTicketSuccessMsg.setDealerOrderId(jpOrder.getBaseInfo().getDealerOrderId());
		outTicketSuccessMsg.setPassengerEticketNo(passengerTicketMap);
		PublishEventRequest eventRequest = new PublishEventRequest(PublishEventRequest.EVENT_GJOutTicketSuccess, JSON.toJSONString(outTicketSuccessMsg));
		PlatformAmqpMessage platformMessage = new PlatformAmqpMessage(jpOrder.getBaseInfo().getFromDealer().getCode(), eventRequest);
		rabbitTemplate.convertAndSend("plfx.gjjp.platform.outTicketSuccess", platformMessage);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->处理出票成功通知3->发布通知消息(%s)", trackingToken,
						JSON.toJSONString(platformMessage, true)), trackingToken);

		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "处理出票成功通知", SupplierCode.YEEXING_GJ,
				GJJPConstants.LOG_WORKER_TYPE_SUPPLIER, "处理出票成功通知完毕"));
		
		// 同步供应商订单
		jpOrderLocalService.syncSupplierOrder(platformOrderId);
	}
}
