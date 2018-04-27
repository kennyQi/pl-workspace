package lxs.api.v1.request.qo.mp;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class TicketPolicyPriceCalendarQO extends ApiPayload {

	/**
	 * 政策ID
	 */
	private String ticketPolicyID;

	public String getTicketPolicyID() {
		return ticketPolicyID;
	}

	public void setTicketPolicyID(String ticketPolicyID) {
		this.ticketPolicyID = ticketPolicyID;
	}
	public static void main(String[] args) {
		TicketPolicyPriceCalendarQO ticketPolicyPriceCalendarQO = new TicketPolicyPriceCalendarQO();
		ticketPolicyPriceCalendarQO.setTicketPolicyID("政策ID");
		System.out.println(JSON.toJSONString(ticketPolicyPriceCalendarQO));
	}
}
