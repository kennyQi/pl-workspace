package zzpl.pojo.dto.jp.plfx.gj;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;


@SuppressWarnings("serial")
public class GJPassengerDTO extends BaseDTO {
	/**
	 * 机票票号
	 */
	private List<String> tickets;

	/**
	 * 乘客基本信息
	 */
	private GJPassengerBaseInfoDTO baseInfo;

	/**
	 * 机票状态
	 */
	private GJPassengerTicketStatusDTO status;

	/**
	 * 价格明细
	 */
	private GJPassengerTicketPriceDetailDTO ticketPriceDetail;

	/**
	 * 退票详情(退票才有)
	 */
	private GJPassengerTicketRefundDetailDTO ticketRefundDetail;

	public List<String> getTickets() {
		return tickets;
	}

	public void setTickets(List<String> tickets) {
		this.tickets = tickets;
	}

	public GJPassengerBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(GJPassengerBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public GJPassengerTicketStatusDTO getStatus() {
		return status;
	}

	public void setStatus(GJPassengerTicketStatusDTO status) {
		this.status = status;
	}

	public GJPassengerTicketPriceDetailDTO getTicketPriceDetail() {
		return ticketPriceDetail;
	}

	public void setTicketPriceDetail(
			GJPassengerTicketPriceDetailDTO ticketPriceDetail) {
		this.ticketPriceDetail = ticketPriceDetail;
	}

	public GJPassengerTicketRefundDetailDTO getTicketRefundDetail() {
		return ticketRefundDetail;
	}

	public void setTicketRefundDetail(
			GJPassengerTicketRefundDetailDTO ticketRefundDetail) {
		this.ticketRefundDetail = ticketRefundDetail;
	}

}