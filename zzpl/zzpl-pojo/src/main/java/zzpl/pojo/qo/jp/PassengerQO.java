package zzpl.pojo.qo.jp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class PassengerQO extends BaseQo {

	private String passengerName;

	private String airID;

	private String flightOrderID;

	private String gjFlightOrderID;
	/**
	 * 订单类型 1：国内机票 2：国际机票
	 */
	private String orderType;

	public String getPassengerName() {
		return passengerName;
	}

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public String getFlightOrderID() {
		return flightOrderID;
	}

	public void setFlightOrderID(String flightOrderID) {
		this.flightOrderID = flightOrderID;
	}

	public String getGjFlightOrderID() {
		return gjFlightOrderID;
	}

	public void setGjFlightOrderID(String gjFlightOrderID) {
		this.gjFlightOrderID = gjFlightOrderID;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
