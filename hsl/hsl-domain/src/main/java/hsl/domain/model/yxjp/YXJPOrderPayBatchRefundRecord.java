package hsl.domain.model.yxjp;

import hg.common.component.BaseModel;
import hg.common.util.DateUtil;
import hg.common.util.MoneyUtil;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.payment.alipay.pojo.PayRefundNotify;
import hsl.payment.alipay.pojo.PayRefundRequest;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.*;

/**
 * 易行机票订单支付批量退款记录
 *
 * @author zhurz
 * @since 1.7
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_YJ + "PAY_BATCH_REFUND_RECORD")
public class YXJPOrderPayBatchRefundRecord extends BaseModel {
	/**
	 * 退款批次号（格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）。）
	 */
	@Column(name = "BATCH_NO", length = 64)
	private String batchNo;
	/**
	 * 退款请求时间
	 */
	@Column(name = "REFUND_DATE", columnDefinition = M.DATE_COLUM)
	private Date refundDate;
	/**
	 * 退款成功时间
	 */
	@Column(name = "NOTIFY_DATE", columnDefinition = M.DATE_COLUM)
	private Date refundNotifyDate;
	/**
	 * 退款总笔数（支付宝最大支持1000笔）
	 */
	@Column(name = "BATCH_NUM", columnDefinition = M.NUM_COLUM)
	private Integer batchNum;
	/**
	 * 退款单项记录
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "payBatchRefundRecord")
	private List<YXJPOrderPayRefundRecord> payRefundRecords = new ArrayList<YXJPOrderPayRefundRecord>();

	/**
	 * 退款总计
	 *
	 * @return
	 */
	public Double totalRefundAmount() {
		Double totalRefundAmount = 0d;
		for (YXJPOrderPayRefundRecord refundRecord : getPayRefundRecords())
			totalRefundAmount = MoneyUtil.round(totalRefundAmount + refundRecord.getRefundAmount(), 2);
		return totalRefundAmount;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public Date getRefundNotifyDate() {
		return refundNotifyDate;
	}

	public void setRefundNotifyDate(Date refundNotifyDate) {
		this.refundNotifyDate = refundNotifyDate;
	}

	public Integer getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(Integer batchNum) {
		this.batchNum = batchNum;
	}

	public List<YXJPOrderPayRefundRecord> getPayRefundRecords() {
		if (payRefundRecords == null)
			payRefundRecords = new ArrayList<YXJPOrderPayRefundRecord>();
		return payRefundRecords;
	}

	public void setPayRefundRecords(List<YXJPOrderPayRefundRecord> payRefundRecords) {
		this.payRefundRecords = payRefundRecords;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {

		/**
		 * 将批量退款记录转为支付宝批量退款请求
		 *
		 * @param notifyUrl 支付宝通知地址
		 * @return
		 */
		public PayRefundRequest convertToPayRefundRequest(String notifyUrl) {
			PayRefundRequest request = new PayRefundRequest();
			request.setNotifyUrl(notifyUrl);
			request.setRefundDate(getRefundDate());
			request.setBatchNo(getBatchNo());
			request.setBatchNum(getBatchNum());
			StringBuilder detailData = new StringBuilder();
			for (YXJPOrderPayRefundRecord payRefundRecord : getPayRefundRecords()) {
				if (detailData.length() > 0)
					detailData.append('#');
				detailData.append(payRefundRecord.getPayRecord().getTradeNo());
				detailData.append('^');
				detailData.append(String.format("%.2f", payRefundRecord.getRefundAmount()));
				detailData.append('^');
				detailData.append(payRefundRecord.getReason());
			}
			request.setDetailData(detailData.toString());
			return request;
		}

		/**
		 * 构建退款批次号
		 *
		 * @param date 发起时间
		 */
		private void buildBatchNo(Date date) {
			StringBuilder sb = new StringBuilder();
			for (YXJPOrderPayRefundRecord payRefundRecord : getPayRefundRecords()) {
				sb.append(M.getModelObjectId(payRefundRecord.getPayRecord()));
				for (YXJPOrderPassenger passenger : payRefundRecord.getPassengers())
					sb.append(M.getModelObjectId(passenger));
			}
			BigInteger x = new BigInteger(DigestUtils.md5Hex(sb.toString()), 16);
			String no = x.toString(36);
			String nowDateStr = DateUtil.formatDateTime(date, "yyyyMMdd");
			setBatchNo(String.format("%s%24s", nowDateStr, no.length() <= 24 ? no : no.substring(0, 24)).replace(' ', '0'));
		}

		/**
		 * 创建退款记录
		 *
		 * @param passengers 退款乘客
		 * @param reason     退款理由
		 * @return
		 */
		public YXJPOrderPayBatchRefundRecord create(List<YXJPOrderPassenger> passengers, String reason) {

			// 按订单分类乘客
			Map<YXJPOrderPayRecord, List<YXJPOrderPassenger>> map = new LinkedHashMap<YXJPOrderPayRecord, List<YXJPOrderPassenger>>();
			for (YXJPOrderPassenger passenger : passengers) {
				YXJPOrderPayRecord paySuccessRecord = passenger.getPayInfo().getPaySuccessRecord();
				if (!map.containsKey(paySuccessRecord))
					map.put(paySuccessRecord, new ArrayList<YXJPOrderPassenger>());
				map.get(paySuccessRecord).add(passenger);
			}

			for (Map.Entry<YXJPOrderPayRecord, List<YXJPOrderPassenger>> payRecordListEntry : map.entrySet()) {
				YXJPOrderPayRefundRecord refundRecord = new YXJPOrderPayRefundRecord().manager().create(
						YXJPOrderPayBatchRefundRecord.this,
						payRecordListEntry.getKey(),
						payRecordListEntry.getValue(),
						reason);
				if (refundRecord.getRefundAmount() > 0d)
					getPayRefundRecords().add(refundRecord);
			}

			Date now = new Date();
			setId(UUIDGenerator.getUUID());
			setBatchNum(getPayRefundRecords().size());
			buildBatchNo(now);
			setRefundDate(now);

			return YXJPOrderPayBatchRefundRecord.this;
		}

		/**
		 * 处理批量退款结果
		 *
		 * @param notify 支付宝退款通知
		 * @return 处理的订单
		 */
		public List<YXJPOrder> processPayRefundNotify(PayRefundNotify notify) {

			// -- 设置退款成功时间 --
			setRefundNotifyDate(notify.getNotifyTime());

			// -- 筛选符合条件的订单 --
			Map<YXJPOrder, List<YXJPOrderPayRefundRecord>> orderRefundMap = new LinkedHashMap<YXJPOrder, List<YXJPOrderPayRefundRecord>>();
			for (YXJPOrderPayRefundRecord refundRecord : getPayRefundRecords()) {
				YXJPOrderPayRecord payRecord = refundRecord.getPayRecord();
				PayRefundNotify.ResultDetail resultDetail = notify.getResultDetailMap().get(payRecord.getTradeNo());
				// 通知里不包含的支付流水号将直接跳过
				if (resultDetail == null)
					continue;
				// 处理退款结果
				refundRecord.manager().processPayRefundDetail(resultDetail);
				YXJPOrder order = refundRecord.getFromOrder();
				if (!orderRefundMap.containsKey(order))
					orderRefundMap.put(order, new ArrayList<YXJPOrderPayRefundRecord>());
				orderRefundMap.get(order).add(refundRecord);
			}

			// -- 订单处理批量退款结果 --
			for (Map.Entry<YXJPOrder, List<YXJPOrderPayRefundRecord>> orderEntry : orderRefundMap.entrySet())
				orderEntry.getKey().manager().processPayRefundResult(orderEntry.getValue());

			return new ArrayList<YXJPOrder>(orderRefundMap.keySet());
		}

	}

}
