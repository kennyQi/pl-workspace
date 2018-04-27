package hsl.api.v1.request.command.jp;

import hsl.api.base.ApiPayload;

/**
 * 取消机票
 * @author yuxx
 *
 */
public class JPCancelTicketCommand extends ApiPayload {

	/**
	 * 要取消的商城订单号
	 */
	private String orderId;

	/**
	 * 要取消的票号
	 */
	private String ticketNumbers;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTicketNumbers() {
		return ticketNumbers;
	}

	public void setTicketNumbers(String ticketNumbers) {
		this.ticketNumbers = ticketNumbers;
	}

}
