package hsl.api.v1.request.qo.jp;

import hsl.api.base.ApiPayload;

public class JPFlightQO extends ApiPayload {

	/**
	 * 出发机场的三字代码(例如：PEK 北京)
	 */
	private String from;

	/**
	 * 目地机场三字代码
	 */
	private String arrive;

	/**
	 * 航班日期 (例如：2008-03-16)
	 */
	private String date;
	
	/**
	 * 航班 : CA8906
	 */
	private String flightNo;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

}