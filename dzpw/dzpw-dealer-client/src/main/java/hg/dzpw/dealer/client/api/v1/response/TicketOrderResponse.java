package hg.dzpw.dealer.client.api.v1.response;

import java.util.List;
import hg.dzpw.dealer.client.common.ApiPageResponse;
import hg.dzpw.dealer.client.dto.order.TicketOrderDTO;

/**
 * @类功能说明：门票订单查询结果
 * @类修改者：
 * @修改日期：2014-11-24上午10:58:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24上午10:58:32
 */
@SuppressWarnings("serial")
public class TicketOrderResponse extends ApiPageResponse {

	/**
	 * 门票订单列表
	 */
	private List<TicketOrderDTO> ticketOrders;

	public List<TicketOrderDTO> getTicketOrders() {
		return ticketOrders;
	}

	public void setTicketOrders(List<TicketOrderDTO> ticketOrders) {
		this.ticketOrders = ticketOrders;
	}
}