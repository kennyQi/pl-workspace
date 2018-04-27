package lxs.api.v1.response.order.mp;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.order.mp.TicketOrderInfoDTO;

public class TicketOrderInfoResponse extends ApiResponse {

	private TicketOrderInfoDTO ticketOrderInfoDTO;

	public TicketOrderInfoDTO getTicketOrderInfoDTO() {
		return ticketOrderInfoDTO;
	}

	public void setTicketOrderInfoDTO(TicketOrderInfoDTO ticketOrderInfoDTO) {
		this.ticketOrderInfoDTO = ticketOrderInfoDTO;
	}

}
