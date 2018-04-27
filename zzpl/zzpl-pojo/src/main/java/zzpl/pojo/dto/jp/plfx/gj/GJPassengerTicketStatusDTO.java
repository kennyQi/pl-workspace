package zzpl.pojo.dto.jp.plfx.gj;

import zzpl.pojo.dto.BaseDTO;


@SuppressWarnings("serial")
public class GJPassengerTicketStatusDTO extends BaseDTO {
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
