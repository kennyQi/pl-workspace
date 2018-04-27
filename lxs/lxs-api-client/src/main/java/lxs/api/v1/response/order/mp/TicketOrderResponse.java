package lxs.api.v1.response.order.mp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.order.mp.TicketOrderDTO;

public class TicketOrderResponse extends ApiResponse {

	/**
	 * 每页数量
	 */
	private String pageSize;

	/**
	 * 页码
	 */
	private String pageNO;

	/**
	 * 是否为最后一页
	 */
	private String isTheLastPage;

	private List<TicketOrderDTO> ticketOrderDTOs;

	public List<TicketOrderDTO> getTicketOrderDTOs() {
		return ticketOrderDTOs;
	}

	public void setTicketOrderDTOs(List<TicketOrderDTO> ticketOrderDTOs) {
		this.ticketOrderDTOs = ticketOrderDTOs;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNO() {
		return pageNO;
	}

	public void setPageNO(String pageNO) {
		this.pageNO = pageNO;
	}

	public String getIsTheLastPage() {
		return isTheLastPage;
	}

	public void setIsTheLastPage(String isTheLastPage) {
		this.isTheLastPage = isTheLastPage;
	}

	public static void main(String[] args) {
		TicketOrderResponse ticketOrderResponse = new TicketOrderResponse();
		List<TicketOrderDTO> ticketOrderDTOs= new ArrayList<TicketOrderDTO>();
		TicketOrderDTO ticketOrderDTO = new TicketOrderDTO();
		ticketOrderDTO.setCreatDate(new Date());
		ticketOrderDTO.setCurrentValue(1);
		ticketOrderDTO.setName("景区名称");
		ticketOrderDTO.setOrderID("订单ID");
		ticketOrderDTO.setPlayPriceSUM(0.0);
		ticketOrderDTO.setTravelDate(new Date());
		ticketOrderDTO.setType(1);
		ticketOrderDTO.setValidDays(1);
		ticketOrderDTOs.add(ticketOrderDTO);
		
		TicketOrderDTO ticketOrderDTO1 = new TicketOrderDTO();
		ticketOrderDTO1.setCreatDate(new Date());
		ticketOrderDTO1.setCurrentValue(1);
		ticketOrderDTO1.setName("景区名称");
		ticketOrderDTO1.setOrderID("订单ID");
		ticketOrderDTO1.setPlayPriceSUM(0.0);
		ticketOrderDTO1.setTravelDate(new Date());
		ticketOrderDTO1.setType(1);
		ticketOrderDTO1.setValidDays(1);
		ticketOrderDTOs.add(ticketOrderDTO1);
		ticketOrderResponse.setTicketOrderDTOs(ticketOrderDTOs);
		ticketOrderResponse.setPageNO("1");
		ticketOrderResponse.setPageSize("20");
		ticketOrderResponse.setIsTheLastPage("y");
		System.out.println(JSON.toJSONString(ticketOrderResponse));
	}
}
