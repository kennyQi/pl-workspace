package slfx.jp.command.client;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：易购退废票command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:06:28
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGApplyRefundCommand extends BaseCommand {
	
	/**
	 * 订单号    必填
	 */
	private String orderNo;
	
	/**
	 * 申请种类编号（根据平台不同，获取的种类也不同）
	 */
	private String actionType;
	
	/**
	 * 所退票号   必填，多个以|分割
	 */
	private String ticketNos;
	
	/**
	 * 所退航段，多个以|分割
	 */
	private String segment;
	
	/**
	 * 退废票原因
	 */
	private String reason;
	
	/**
	 * 退废种类：退票（T），废票（F）
	 */
	private String refundType;
	
	/**
	 * 退废票的通知地址
	 */
	private String noticeUrl="";

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getTicketNos() {
		return ticketNos;
	}

	public void setTicketNos(String ticketNos) {
		this.ticketNos = ticketNos;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}
	
}