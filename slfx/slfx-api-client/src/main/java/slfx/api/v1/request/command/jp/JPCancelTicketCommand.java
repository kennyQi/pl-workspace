package slfx.api.v1.request.command.jp;

import slfx.api.base.ApiPayload;

/**
 * 
 * @类功能说明：取消机票COMMAND
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月1日下午5:07:14
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPCancelTicketCommand extends ApiPayload {

	/**
	 * 平台订单号
	 */
	private String orderId;
	/**
	 * 要取消的商城订单号
	 */
	private String dealerOrderCode;

	/**
	 * 要取消的票号 逗号分隔多张
	 */
	private String ticketNumbers;
	
	/**
	 * 操作人
	 */
	private String name;

	/**
	 * 退废种类：退票（T），废票（F）
	 */
	private String refundType;
	/** 退票政策 */
	private String backPolicy;
	/** 退票费用 */
	private String backAmount;
	/** 取消订单原因 */
	private String cancelReason;
	
	/** 退废原因编码 */
	private String actionType;
	
	/**
	 * 退废票完成通知地址
	 */
	private String notifyUrl;
	
	/**
	 * 退废原因
	 */
	private String reason;
	
	
	///////////////////
	
	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public String getTicketNumbers() {
		return ticketNumbers;
	}

	public void setTicketNumbers(String ticketNumbers) {
		this.ticketNumbers = ticketNumbers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getBackPolicy() {
		return backPolicy;
	}

	public void setBackPolicy(String backPolicy) {
		this.backPolicy = backPolicy;
	}

	public String getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(String backAmount) {
		this.backAmount = backAmount;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
