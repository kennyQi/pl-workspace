package plfx.api.client.api.v1.gn.request;

import plfx.api.client.common.BaseClientCommand;

@SuppressWarnings("serial")
public class JPCreatePayRecordCommand extends BaseClientCommand {
	
	/**
	 * 航空公司
	 */
	private String airCompName;

	/**
	 * 航班号 string ZH9814
	 */
	private String flightNo;

	/**
	 * 舱位代码
	 */
	private String classCode;

	/**
	 * 舱位名称
	 */
	private String cabinName;

	/**
	 * 订单pnr
	 */
	private String pnr;

	public String getAirCompName() {
		return airCompName;
	}

	public void setAirCompName(String airCompName) {
		this.airCompName = airCompName;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getCabinName() {
		return cabinName;
	}

	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

}
