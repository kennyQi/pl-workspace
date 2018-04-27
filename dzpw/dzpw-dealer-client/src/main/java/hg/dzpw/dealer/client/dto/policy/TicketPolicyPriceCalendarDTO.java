package hg.dzpw.dealer.client.dto.policy;

import hg.dzpw.dealer.client.common.BaseDTO;

import java.util.List;

/**
 * @类功能说明：门票政策价格日历
 * @类修改者：
 * @修改日期：2015-3-3下午4:03:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-3下午4:03:12
 */
@SuppressWarnings("serial")
public class TicketPolicyPriceCalendarDTO extends BaseDTO {

	/**
	 * 所属门票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * 每日价格
	 */
	private List<TicketPolicyDatePriceDTO> prices;

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public List<TicketPolicyDatePriceDTO> getPrices() {
		return prices;
	}

	public void setPrices(List<TicketPolicyDatePriceDTO> prices) {
		this.prices = prices;
	}

}
