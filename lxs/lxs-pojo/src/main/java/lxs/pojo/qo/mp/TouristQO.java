package lxs.pojo.qo.mp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class TouristQO extends BaseQo {

	/**
	 * 订单ID
	 */
	private String orderID;

	/**
	 * 票号
	 */
	private String ticketNo;
	
	/**
	 * 票号
	 */
	private Integer localStatus;

	public Integer getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(Integer localStatus) {
		this.localStatus = localStatus;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

}
