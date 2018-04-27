package hg.dzpw.app.service.local;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import hg.common.component.BaseServiceImpl;
import hg.common.util.MoneyUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.component.event.DealerApiEventPublisher;
import hg.dzpw.app.component.manager.DealerRefundNotifyCacheManager;
import hg.dzpw.app.dao.ApplyRefundRecordDao;
import hg.dzpw.app.pojo.qo.AliPayTransferRecordQo;
import hg.dzpw.app.pojo.qo.ApplyRefundRecordQo;
import hg.dzpw.app.service.api.alipay.AliPayRefundFastService;
import hg.dzpw.dealer.client.common.publish.PublishEventRequest;
import hg.dzpw.domain.model.dealer.DealerRefundNotifyRecord;
import hg.dzpw.domain.model.pay.AliPayTransferRecord;
import hg.dzpw.domain.model.pay.ApplyRefundRecord;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.dzpw.pojo.api.alipay.RefundDetailData;
import hg.dzpw.pojo.api.alipay.RefundDetailData.MainTrade;
import hg.dzpw.pojo.api.alipay.RefundFastParameter;
import hg.dzpw.pojo.api.alipay.RefundFastResponse;
import hg.log.util.HgLogger;

/**
 * 
 * @类功能说明：经销商退款申请处理服务
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-18下午5:00:24
 * @版本：
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApplyRefundRecordLocalService
		extends
		BaseServiceImpl<ApplyRefundRecord, ApplyRefundRecordQo, ApplyRefundRecordDao> {
	@Autowired
	private ApplyRefundRecordDao dao;
	@Autowired
	private AliPayRefundFastService refundFastService;
	@Autowired
	private AliPayTransferRecordLocalService transferRecordLocalService;
	@Autowired
	private DealerRefundNotifyCacheManager refundNotifyCacheManager;
	// 全局退款理由
	private String refundReason = "门票过期，钱款退还";
	private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");

	@Override
	protected ApplyRefundRecordDao getDao() {

		return dao;
	}

	/**
	 * 
	 * @描述：退款申请处理
	 * @author: guotx
	 * @version: 2016-3-22 下午2:21:22
	 */
	public void dealRefund() {
		ApplyRefundRecordQo refundRecordQo = new ApplyRefundRecordQo();
		Calendar calendar = Calendar.getInstance();
		// 退款详细信息
		List<RefundDetailData> refundDetailDatas = null;
		RefundFastParameter refundFastParameter = null;
		// 三天以前的申请
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		Date endDate = calendar.getTime();
		refundRecordQo.setRecordDateEnd(endDate);
		List<ApplyRefundRecord> queryList = getDao().queryList(refundRecordQo);

		for (ApplyRefundRecord applyRefundRecord : queryList) {
			refundDetailDatas = new ArrayList<RefundDetailData>();

			RefundDetailData refundDetailData = buildRefundData(applyRefundRecord);

			refundDetailDatas.add(refundDetailData);
			refundFastParameter = new RefundFastParameter();
			// 设置退款批次号
			refundFastParameter.setBatch_no(dateFormat.format(new Date())
					+ applyRefundRecord.getGroupTicket().getTicketNo());
			// 写入退款处理记录
			AliPayTransferRecord refundRecord = recordAliPayRefundRecord(
					refundFastParameter.getBatch_no(), refundDetailData,
					applyRefundRecord);
			// 调用接口
			RefundFastResponse refundFastResponse = refundFastService
					.refundFastRequest(refundFastParameter, refundDetailDatas);
			if (refundFastResponse.isSuccess()) {
				// 接口调用成功
//				HgLogger.getInstance().info("guotx", "申请支付宝退款成功，票号："+refundRecord.getTradeNo()+"退款批次号："+refundFastParameter.getBatch_no());
			} else {
				// 接口调用失败，更新AliPayTransferRecord
				HgLogger.getInstance().info("guotx", "申请支付宝退款失败，票号："+refundRecord.getTradeNo()+"退款批次号："+refundFastParameter.getBatch_no());
				refundRecord.setResponseDataJson(JSON
						.toJSONString(refundFastResponse));
				refundRecord.setIsSuccess(false);
				refundRecord.setHasResponse(true);
				refundRecord.setErrorCode(refundFastResponse.getMessage());
			}
			refundRecord.setRequestDataJson(JSON
					.toJSONString(refundFastParameter));
			transferRecordLocalService.update(refundRecord);
			// 处理完删除申请记录
			getDao().delete(applyRefundRecord);
		}
	}

	/**
	 * 
	 * @描述： 写入退款处理记录
	 * @author: guotx
	 * @version: 2016-3-22 下午5:05:18
	 * @return
	 */
	private AliPayTransferRecord recordAliPayRefundRecord(String batchNo,
			RefundDetailData refundDetailData,
			ApplyRefundRecord applyRefundRecord) {
		AliPayTransferRecord transferRecord = new AliPayTransferRecord();
		transferRecord.setId(UUIDGenerator.getUUID());
		transferRecord.setType(AliPayTransferRecord.TYPE_DZPW_TO_DEALER);
		transferRecord.setRefundBatchNo(batchNo);
		transferRecord.setTradeNo(refundDetailData.getMainTrade().getTradeNo());
		transferRecord.setTrxAmount(refundDetailData.getMainTrade()
				.getRefundAmount());
		transferRecord.setRecordDate(new Date());
		transferRecord.setTicketOrderId(applyRefundRecord.getTicketOrder()
				.getId());
		transferRecord.setGroupTicketId(applyRefundRecord.getGroupTicket()
				.getId());

		return transferRecordLocalService.save(transferRecord);
	}

	/**
	 * 
	 * @描述： 构建退款数据
	 * @author: guotx
	 * @version: 2016-3-22 下午4:36:30
	 */
	private RefundDetailData buildRefundData(ApplyRefundRecord applyRefundRecord) {
		RefundDetailData refundDetailData = new RefundDetailData();
		refundDetailData.setMainTrade(new MainTrade());
		// 获取原交易号
		String tradeNo = null;
		AliPayTransferRecordQo transferRecordQo = new AliPayTransferRecordQo();
		transferRecordQo.setTicketOrderId(applyRefundRecord.getTicketOrder()
				.getId());
		AliPayTransferRecord transferRecord = transferRecordLocalService
				.queryUnique(transferRecordQo);
		tradeNo = transferRecord.getTradeNo();
		refundDetailData.getMainTrade().setTradeNo(tradeNo);
		// 获取退款金额
		double refundAmount = 0;
		GroupTicket groupTicket = applyRefundRecord.getGroupTicket();
		refundAmount = groupTicket.getPrice();
		List<SingleTicket> singleTickets = groupTicket.getSingleTickets();
		for (SingleTicket singleTicket : singleTickets) {
			if (singleTicket.getStatus().getCurrent() == SingleTicketStatus.SINGLE_TICKET_CURRENT_USED) {
				// 减去已游玩单票价格
				refundAmount = MoneyUtil.sub(refundAmount, singleTicket.getPrice());
			}
		}
		// 退款金额
		refundDetailData.getMainTrade().setRefundAmount(refundAmount);
		// 退款理由
		refundDetailData.getMainTrade().setRefundReason(refundReason);
		return refundDetailData;
	}

	/**
	 * 
	 * @描述： 调用经销商api发布信息接口，推送退款成功消息，按照经销商分组进行推送
	 * @author: guotx
	 * @version: 2016-3-24 下午12:19:44
	 */
	public void publishRefundEvent() {
		List<String> dealerKeys = refundNotifyCacheManager.getAllDealerKeys();
		if (dealerKeys != null && dealerKeys.size() > 0){
			Calendar calendar=Calendar.getInstance();
			for (int i = 0; i < dealerKeys.size(); i++) {
				String dealerKey = dealerKeys.get(i);
				Map<String, DealerRefundNotifyRecord> records = refundNotifyCacheManager
						.getRecordByDealer(dealerKey);
				for (Entry<String, DealerRefundNotifyRecord> entry : records
						.entrySet()) {
					DealerRefundNotifyRecord record = entry.getValue();
					calendar.setTime(record.getNotifyDate());
					// 过了该通知的时间，就通知
					if (Calendar.getInstance().after(calendar)) {
						String ticketNo = record.getTicketNo();
						DealerApiEventPublisher
								.publish(new PublishEventRequest(
										PublishEventRequest.EVENT_TICKET_REFUND_SUCCESS,
										ticketNo, dealerKey));
					}
				}
			}
	}
	}

}
