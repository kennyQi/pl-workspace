package hsl.domain.model.yxjp;

import hsl.domain.model.M;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付信息
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings("serial")
public class YXJPOrderPayInfo implements Serializable {

	/**
	 * 订单总价=乘客需要支付的价格总和（这个值下单完成后就不变）
	 */
	@Column(name = "TOTAL_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double totalPrice = 0d;

	/**
	 * 订单已取消的总价=未支付已取消的乘客需要支付的价格总和
	 */
	@Column(name = "TOTAL_CANCEL_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double totalCancelPrice = 0d;

	/**
	 * 用户已支付总额（不含卡券金额）
	 */
	@Column(name = "TOTAL_PAY_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double totalPayAmount = 0d;

	/**
	 * 已退款总额（不含卡券金额）
	 */
	@Column(name = "TOTAL_REFUND_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double totalRefundAmount = 0d;

	/**
	 * 供应商已退款手续费总额
	 */
	@Column(name = "TOTAL_SUPPLIER_PROD_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double totalSupplierProceduresAmount = 0d;

	/**
	 * 供应商已退款总额
	 */
	@Column(name = "TOTAL_SUPPLIER_REFUND_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double totalSupplierRefundAmount = 0d;

	/**
	 * 用户已使用卡券的实际总额
	 */
	@Column(name = "TOTAL_COUPON_USED_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double totalCouponUsedAmount = 0d;

	/**
	 * 用户已使用的卡券总额
	 */
	@Column(name = "TOTAL_COUPON_AMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double totalCouponAmount = 0d;

	/**
	 * 用户支付记录
	 * KEY为支付订单号
	 */
	@MapKey(name = "payOrderNo")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fromOrder")
	private Map<String, YXJPOrderPayRecord> payRecordMap;

	/**
	 * 支付退款记录
	 */
	@OrderBy("createDate")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fromOrder")
	private List<YXJPOrderPayRefundRecord> payRefundRecords;

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getTotalPayAmount() {
		return totalPayAmount;
	}

	public void setTotalPayAmount(Double totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}

	public Double getTotalRefundAmount() {
		return totalRefundAmount;
	}

	public void setTotalRefundAmount(Double totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}

	public Double getTotalSupplierProceduresAmount() {
		return totalSupplierProceduresAmount;
	}

	public void setTotalSupplierProceduresAmount(Double totalSupplierProceduresAmount) {
		this.totalSupplierProceduresAmount = totalSupplierProceduresAmount;
	}

	public Double getTotalSupplierRefundAmount() {
		return totalSupplierRefundAmount;
	}

	public void setTotalSupplierRefundAmount(Double totalSupplierRefundAmount) {
		this.totalSupplierRefundAmount = totalSupplierRefundAmount;
	}

	public Double getTotalCouponUsedAmount() {
		return totalCouponUsedAmount;
	}

	public void setTotalCouponUsedAmount(Double totalCouponUsedAmount) {
		this.totalCouponUsedAmount = totalCouponUsedAmount;
	}

	public Double getTotalCouponAmount() {
		return totalCouponAmount;
	}

	public void setTotalCouponAmount(Double totalCouponAmount) {
		this.totalCouponAmount = totalCouponAmount;
	}

	public Map<String, YXJPOrderPayRecord> getPayRecordMap() {
		if (payRecordMap == null)
			payRecordMap = new LinkedHashMap<String, YXJPOrderPayRecord>();
		return payRecordMap;
	}

	public void setPayRecordMap(Map<String, YXJPOrderPayRecord> payRecordMap) {
		this.payRecordMap = payRecordMap;
	}

	public List<YXJPOrderPayRefundRecord> getPayRefundRecords() {
		return payRefundRecords;
	}

	public void setPayRefundRecords(List<YXJPOrderPayRefundRecord> payRefundRecords) {
		this.payRefundRecords = payRefundRecords;
	}

	public Double getTotalCancelPrice() {
		if (totalCancelPrice == null)
			totalCancelPrice = 0d;
		return totalCancelPrice;
	}

	public void setTotalCancelPrice(Double totalCancelPrice) {
		this.totalCancelPrice = totalCancelPrice;
	}
}
