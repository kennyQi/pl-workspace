package plfx.api.client.api.v1.gj.dto;

import plfx.api.client.common.PlfxApiConstants.GJ;

public class GJPassengerTicketStatusDTO {

	/**
	 * 机票状态
	 * 
	 * @see GJ#TICKET_STATUS_MAP
	 */
	private Integer currentValue;

	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

}
