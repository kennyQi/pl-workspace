package hsl.domain.model.yxjp;

import hg.common.component.BaseModel;
import hg.common.util.MoneyUtil;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.payment.alipay.pojo.PayRefundNotify;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 支付退款记录
 *
 * @author zhurz
 * @since 1.7
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_YJ + "PAY_REFUND_RECORD")
public class YXJPOrderPayRefundRecord extends BaseModel {

	/**
	 * 所属批量退款
	 */
	@ManyToOne
	@JoinColumn(name = "PAY_BATCH_REFUND_RECORD_ID")
	private YXJPOrderPayBatchRefundRecord payBatchRefundRecord;
	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private YXJPOrder fromOrder;
	/**
	 * 所属支付记录
	 */
	@ManyToOne
	@JoinColumn(name = "PAY_RECORD_ID")
	private YXJPOrderPayRecord payRecord;
	/**
	 * 包含的退款乘客
	 */
	@ManyToMany
	private List<YXJPOrderPassenger> passengers;
	/**
	 * 退款金额
	 */
	@Column(name = "REFUND_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double refundAmount = 0d;
	/**
	 * 退款理由
	 */
	@Column(name = "REASON", length = 512)
	private String reason;
	/**
	 * 退款是否成功
	 */
	@Type(type = "yes_no")
	@Column(name = "REFUND_SUCCESS")
	private Boolean refundSuccess = false;

	public YXJPOrderPayBatchRefundRecord getPayBatchRefundRecord() {
		return payBatchRefundRecord;
	}

	public void setPayBatchRefundRecord(YXJPOrderPayBatchRefundRecord payBatchRefundRecord) {
		this.payBatchRefundRecord = payBatchRefundRecord;
	}

	public YXJPOrder getFromOrder() {
		return fromOrder;
	}

	public void setFromOrder(YXJPOrder fromOrder) {
		this.fromOrder = fromOrder;
	}

	public YXJPOrderPayRecord getPayRecord() {
		return payRecord;
	}

	public void setPayRecord(YXJPOrderPayRecord payRecord) {
		this.payRecord = payRecord;
	}

	public List<YXJPOrderPassenger> getPassengers() {
		if (passengers == null)
			passengers = new ArrayList<YXJPOrderPassenger>();
		return passengers;
	}

	public void setPassengers(List<YXJPOrderPassenger> passengers) {
		this.passengers = passengers;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getRefundSuccess() {
		return refundSuccess;
	}

	public void setRefundSuccess(Boolean refundSuccess) {
		this.refundSuccess = refundSuccess;
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
		 * 创建退款记录
		 *
		 * @param payBatchRefundRecord 批量支付退款记录
		 * @param payRecord            支付记录
		 * @param passengers           涉及乘客
		 * @param reason               理由
		 * @return
		 */
		YXJPOrderPayRefundRecord create(YXJPOrderPayBatchRefundRecord payBatchRefundRecord,
										YXJPOrderPayRecord payRecord, List<YXJPOrderPassenger> passengers, String reason) {

			setId(UUIDGenerator.getUUID());
			setPayBatchRefundRecord(payBatchRefundRecord);
			setFromOrder(payRecord.getFromOrder());
			setPayRecord(payRecord);
			getPassengers().addAll(passengers);
			setReason(reason);

			// 计算退款金额
			Double refundAmount = 0d;
			for (YXJPOrderPassenger passenger : passengers)
				refundAmount = MoneyUtil.round(refundAmount + passenger.getPayInfo().getNeedRefundAmount(), 2);
			setRefundAmount(refundAmount);

			return YXJPOrderPayRefundRecord.this;
		}

		/**
		 * 处理退款结果
		 *
		 * @param notify 支付宝退款通知
		 */
		void processPayRefundDetail(PayRefundNotify.ResultDetail resultDetail) {
			setRefundSuccess(resultDetail.isSuccess());
		}
	}


}
