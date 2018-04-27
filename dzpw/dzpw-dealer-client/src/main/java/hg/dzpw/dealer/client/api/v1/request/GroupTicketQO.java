package hg.dzpw.dealer.client.api.v1.request;

import hg.dzpw.dealer.client.common.BaseClientQO;
import hg.dzpw.dealer.client.common.api.DealerApiAction;

/**
 * @类功能说明：门票查询对象
 * @类修改者：
 * @修改日期：2015-4-23上午11:22:27
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-23上午11:22:27
 */
@SuppressWarnings("serial")
@DealerApiAction(DealerApiAction.QueryGroupTicket)
public class GroupTicketQO extends BaseClientQO {

	/**
	 * 票号
	 */
	private String[] ticketNos;

	/**
	 * 订单id
	 */
	private String orderId;

	/**
	 * 是否查询游客信息[按QO条件决定是否查询]
	 */
	private Boolean touristFetch = false;
	
	/**
	 * 是否查询单票信息
	 */
	private Boolean singleTicketFetch = false;

	public String[] getTicketNos() {
		return ticketNos;
	}

	public void setTicketNos(String[] ticketNos) {
		this.ticketNos = ticketNos;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Boolean getTouristFetch() {
		return touristFetch;
	}

	public void setTouristFetch(Boolean touristFetch) {
		this.touristFetch = touristFetch;
	}

	public Boolean getSingleTicketFetch() {
		return singleTicketFetch;
	}

	public void setSingleTicketFetch(Boolean singleTicketFetch) {
		this.singleTicketFetch = singleTicketFetch;
	}

}
