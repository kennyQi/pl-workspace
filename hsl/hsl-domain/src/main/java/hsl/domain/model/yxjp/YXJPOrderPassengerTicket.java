package hsl.domain.model.yxjp;

import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 易行机票信息
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings("serial")
public class YXJPOrderPassengerTicket implements Serializable, HSLConstants.YXJPOrderPassengerTicket {


	/**
	 * 机票票号
	 */
	@Column(name = "TICKET_NO", length = 64)
	private String ticketNo;

	/**
	 * 航班信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FLIGHT_ID")
	private YXJPOrderFlight flight;

	/**
	 * 机票订单状态
	 *
	 * @see HSLConstants.YXJPOrderPassengerTicket
	 */
	@Column(name = "STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer status;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public YXJPOrderFlight getFlight() {
		return flight;
	}

	public void setFlight(YXJPOrderFlight flight) {
		this.flight = flight;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
