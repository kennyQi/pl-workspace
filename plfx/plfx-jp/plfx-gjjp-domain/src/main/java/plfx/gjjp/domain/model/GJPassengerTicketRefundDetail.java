package plfx.gjjp.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import plfx.jp.domain.model.J;

/**
 * @类功能说明：乘客退款明细
 * @类修改者：
 * @修改日期：2015-7-14上午10:54:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-14上午10:54:53
 */
@Embeddable
@SuppressWarnings("serial")
public class GJPassengerTicketRefundDetail implements Serializable {

	/**
	 * 退票类型
	 * 
	 * 0 废票1 退票
	 */
	@Column(name = "REFUND_TYPE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer refundType;

	/**
	 * 退票理由
	 */
	@Column(name = "REFUND_REASON", length = 1024)
	private String refundReason;
	
	/**
	 * 拒绝退废票理由
	 */
	@Column(name = "REFUSE_REFUND_REASON", length = 1024)
	private String refuseRefundReason;
	
	// ------------------- 供应商退票信息 -------------------

	/**
	 * 供应商申请退票时间
	 */
	@Column(name = "SUPPLIER_REQ_RTN_TIME", columnDefinition = J.DATE_COLUM)
	private Date supplierReqRtnTime;

	/**
	 * 供应商退票完成时间
	 */
	@Column(name = "SUPPLIER_RTN_COMPLETE_TIME", columnDefinition = J.DATE_COLUM)
	private Date supplierRtnCompleteTime;

	/**
	 * 供应商实退金额
	 */
	@Column(name = "SUPPLIER_REFUND_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double supplierRefundPrice;

	/**
	 * 供应商退款手续费
	 */
	@Column(name = "SUPPLIER_REFUND_FEE", columnDefinition = J.MONEY_COLUM)
	private Double supplierRefundFee;

	// ------------------- 平台退票信息 -------------------

	/**
	 * 平台申请退票时间
	 */
	@Column(name = "REQ_RTN_TIME", columnDefinition = J.DATE_COLUM)
	private Date reqRtnTime;

	/**
	 * 平台退票完成时间
	 */
	@Column(name = "RTN_COMPLETE_TIME", columnDefinition = J.DATE_COLUM)
	private Date rtnCompleteTime;

	/**
	 * 平台实退金额
	 */
	@Column(name = "REFUND_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double refundPrice;

	/**
	 * 平台退款手续费
	 */
	@Column(name = "REFUND_FEE", columnDefinition = J.MONEY_COLUM)
	private Double refundFee;

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefuseRefundReason() {
		return refuseRefundReason;
	}

	public void setRefuseRefundReason(String refuseRefundReason) {
		this.refuseRefundReason = refuseRefundReason;
	}

	public Date getSupplierReqRtnTime() {
		return supplierReqRtnTime;
	}

	public void setSupplierReqRtnTime(Date supplierReqRtnTime) {
		this.supplierReqRtnTime = supplierReqRtnTime;
	}

	public Date getSupplierRtnCompleteTime() {
		return supplierRtnCompleteTime;
	}

	public void setSupplierRtnCompleteTime(Date supplierRtnCompleteTime) {
		this.supplierRtnCompleteTime = supplierRtnCompleteTime;
	}

	public Double getSupplierRefundPrice() {
		return supplierRefundPrice;
	}

	public void setSupplierRefundPrice(Double supplierRefundPrice) {
		this.supplierRefundPrice = supplierRefundPrice;
	}

	public Double getSupplierRefundFee() {
		return supplierRefundFee;
	}

	public void setSupplierRefundFee(Double supplierRefundFee) {
		this.supplierRefundFee = supplierRefundFee;
	}

	public Date getReqRtnTime() {
		return reqRtnTime;
	}

	public void setReqRtnTime(Date reqRtnTime) {
		this.reqRtnTime = reqRtnTime;
	}

	public Date getRtnCompleteTime() {
		return rtnCompleteTime;
	}

	public void setRtnCompleteTime(Date rtnCompleteTime) {
		this.rtnCompleteTime = rtnCompleteTime;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Double getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}

}
