package hg.dzpw.dealer.client.api.v1.response;

import java.util.List;

import hg.dzpw.dealer.client.common.ApiResponse;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;

/**
 * @类功能说明：门票查询结果
 * @类修改者：
 * @修改日期：2015-4-23下午4:24:41
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-23下午4:24:41
 */
@SuppressWarnings("serial")
public class GroupTicketResponse extends ApiResponse {

	/**
	 * 门票列表
	 */
	private List<GroupTicketDTO> groupTickets;

	public List<GroupTicketDTO> getGroupTickets() {
		return groupTickets;
	}

	public void setGroupTickets(List<GroupTicketDTO> groupTickets) {
		this.groupTickets = groupTickets;
	}

}
