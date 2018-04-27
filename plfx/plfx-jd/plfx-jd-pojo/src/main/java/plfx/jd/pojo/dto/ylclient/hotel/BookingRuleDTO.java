package plfx.jd.pojo.dto.ylclient.hotel;
@SuppressWarnings("serial")
public class BookingRuleDTO extends BaseBookingRuleDTO {
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
