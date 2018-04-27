package hsl.domain.model.yxjp;

import hsl.domain.model.M;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 乘客支付信息
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings("serial")
public class YXJPOrderPassengerPayInfo implements Serializable {

	/**
	 * 需要用户支付的价格
	 *
	 * @see YXJPOrderFlightPolicyInfo#payPrice
	 */
	@Column(name = "PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price = 0d;

	/**
	 * 用户实际支付的金额
	 */
	@Column(name = "PAY_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double payAmount = 0d;

	/**
	 * 直销实际退款给用户的金额
	 */
	@Column(name = "REFUND_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double refundAmount = 0d;

	/**
	 * 直销需要退款给用户的金额=用户实际支付的金额-供应商退款给分销的手续费，若小于0则取0。
	 */
	@Column(name = "NEED_REFUND_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double needRefundAmount = 0d;

	/**
	 * 供应商退款给分销的金额
	 */
	@Column(name = "SUPPLIER_REFUND_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double supplierRefundAmount = 0d;

	/**
	 * 供应商退款给分销的手续费
	 */
	@Column(name = "SUPPLIER_PROCEDURES_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double supplierProceduresAmount = 0d;

	/**
	 * 用户成功的付款记录
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAY_SUCCESS_RECORD_ID")
	private YXJPOrderPayRecord paySuccessRecord;

	/**
	 * 支付退款成功的记录
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAY_REFUND_SUCCESS_RECORD_ID")
	private YXJPOrderPayRefundRecord payRefundSuccessRecord;

	/**
	 * 成功的退废票结果
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REFUND_SUCCESS_RESULT_ID")
	private YXJPOrderRefundResult refundSuccessResult;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPayAmount() {
		if (payAmount == null)
			payAmount = 0d;
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Double getSupplierRefundAmount() {
		return supplierRefundAmount;
	}

	public void setSupplierRefundAmount(Double supplierRefundAmount) {
		this.supplierRefundAmount = supplierRefundAmount;
	}

	public Double getSupplierProceduresAmount() {
		if (supplierProceduresAmount == null)
			supplierProceduresAmount = 0d;
		return supplierProceduresAmount;
	}

	public void setSupplierProceduresAmount(Double supplierProceduresAmount) {
		this.supplierProceduresAmount = supplierProceduresAmount;
	}

	public YXJPOrderPayRecord getPaySuccessRecord() {
		return paySuccessRecord;
	}

	public void setPaySuccessRecord(YXJPOrderPayRecord paySuccessRecord) {
		this.paySuccessRecord = paySuccessRecord;
	}

	public YXJPOrderPayRefundRecord getPayRefundSuccessRecord() {
		return payRefundSuccessRecord;
	}

	public void setPayRefundSuccessRecord(YXJPOrderPayRefundRecord payRefundSuccessRecord) {
		this.payRefundSuccessRecord = payRefundSuccessRecord;
	}

	public YXJPOrderRefundResult getRefundSuccessResult() {
		return refundSuccessResult;
	}

	public void setRefundSuccessResult(YXJPOrderRefundResult refundSuccessResult) {
		this.refundSuccessResult = refundSuccessResult;
	}

	public Double getNeedRefundAmount() {
		if (needRefundAmount == null)
			needRefundAmount = 0d;
		return needRefundAmount;
	}

	public void setNeedRefundAmount(Double needRefundAmount) {
		this.needRefundAmount = needRefundAmount;
	}
}
