package plfx.gjjp.domain.model;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plfx.jp.domain.model.J;

/**
 * @类功能说明：国际机票
 * @类修改者：
 * @修改日期：2015-7-13上午10:02:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-13上午10:02:25
 */
@Entity
@SuppressWarnings("serial")
@Table(name = J.TABLE_PREFIX_GJ + "JP_TICKET")
public class GJJPTicket extends BaseModel {

	/**
	 * 机票票号
	 */
	@Column(name = "TICKET_NO", length = 64)
	private String ticketNo;

	/**
	 * 对应乘客
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PASSENGER_ID")
	private GJPassenger passenger;

	/**
	 * 对应航班，为null时则为订单中的全部航班
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FLIGHT_ID")
	private GJFlightCabin flight;

	public void create(String ticketNo, GJPassenger passenger, GJFlightCabin flight) {
		setId(UUIDGenerator.getUUID());
		setPassenger(passenger);
		setFlight(flight);
		setTicketNo(ticketNo);
	}

	public GJPassenger getPassenger() {
		return passenger;
	}

	public void setPassenger(GJPassenger passenger) {
		this.passenger = passenger;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public GJFlightCabin getFlight() {
		return flight;
	}

	public void setFlight(GJFlightCabin flight) {
		this.flight = flight;
	}

	@Override
	public String toString() {
		return ticketNo;
	}

}
