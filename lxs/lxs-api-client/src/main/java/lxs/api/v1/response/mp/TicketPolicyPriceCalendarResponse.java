package lxs.api.v1.response.mp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.TicketPolicyDatePriceDTO;

public class TicketPolicyPriceCalendarResponse extends ApiResponse {

	private List<TicketPolicyDatePriceDTO> ticketPolicyDatePriceDTOs;

	public List<TicketPolicyDatePriceDTO> getTicketPolicyDatePriceDTOs() {
		return ticketPolicyDatePriceDTOs;
	}

	public void setTicketPolicyDatePriceDTOs(
			List<TicketPolicyDatePriceDTO> ticketPolicyDatePriceDTOs) {
		this.ticketPolicyDatePriceDTOs = ticketPolicyDatePriceDTOs;
	}
	
	public static void main(String[] args) {
		TicketPolicyPriceCalendarResponse ticketPolicyPriceCalendarResponse = new TicketPolicyPriceCalendarResponse();
		List<TicketPolicyDatePriceDTO> ticketPolicyDatePriceDTOs = new ArrayList<TicketPolicyDatePriceDTO>();
		TicketPolicyDatePriceDTO ticketPolicyDatePriceDTO = new TicketPolicyDatePriceDTO();
		ticketPolicyDatePriceDTO.setDate(new Date().toString());
		ticketPolicyDatePriceDTO.setPrice(0.1);
		ticketPolicyDatePriceDTOs.add(ticketPolicyDatePriceDTO);
		
		TicketPolicyDatePriceDTO ticketPolicyDatePriceDTO1 = new TicketPolicyDatePriceDTO();
		ticketPolicyDatePriceDTO1.setDate(new Date().toString());
		ticketPolicyDatePriceDTO1.setPrice(0.1);
		ticketPolicyDatePriceDTOs.add(ticketPolicyDatePriceDTO1);
		
		TicketPolicyDatePriceDTO ticketPolicyDatePriceDTO2 = new TicketPolicyDatePriceDTO();
		ticketPolicyDatePriceDTO2.setDate(new Date().toString());
		ticketPolicyDatePriceDTO2.setPrice(0.1);
		ticketPolicyDatePriceDTOs.add(ticketPolicyDatePriceDTO2);
		
		ticketPolicyPriceCalendarResponse.setTicketPolicyDatePriceDTOs(ticketPolicyDatePriceDTOs);
		System.out.println(JSON.toJSONString(ticketPolicyPriceCalendarResponse));
	}
}
