package plfx.api.client.api.v1.gj.dto;

import java.util.Date;

public class GJPassengerTicketRefundDetailDTO {

	/**
	 * 退票类型
	 * 
	 * 0 废票1 退票
	 */
	private Integer refundType;

	/**
	 * 申请退票时间
	 */
	private Date reqRtnTime;

	/**
	 * 退票完成时间
	 */
	private Date rtnCompleteTime;

	/**
	 * 实退金额
	 */
	private Double refundPrice;

	/**
	 * 退款手续费
	 */
	private Double refundFee;

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
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
