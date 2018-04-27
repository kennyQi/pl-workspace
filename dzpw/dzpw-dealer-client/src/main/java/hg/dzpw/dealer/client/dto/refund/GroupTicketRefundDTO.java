package hg.dzpw.dealer.client.dto.refund;

import hg.dzpw.dealer.client.common.BaseDTO;

public class GroupTicketRefundDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 异常票号
	 **/
	private String ticketNo;
	
	/**
	 * 异常描述
	 * 描述可用 RefundResponse 错误代码描述
	 **/
	private String reason;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
