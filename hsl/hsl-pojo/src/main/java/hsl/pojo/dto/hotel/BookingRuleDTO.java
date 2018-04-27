package hsl.pojo.dto.hotel;

import hsl.pojo.dto.hotel.base.BaseBookingRuleDTO;

public class BookingRuleDTO extends BaseBookingRuleDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 预定规则id
	 */
	protected long bookingRuleId;

	public long getBookingRuleId() {
		return bookingRuleId;
	}

	public void setBookingRuleId(long value) {
		this.bookingRuleId = value;
	}

}
