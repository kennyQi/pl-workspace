package hsl.domain.model.jp;

import hg.common.component.BaseModel;

/**
 * 易购航班信息MODEL
 * @author renfeng
 *
 */
public class JPFlight extends BaseModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 航空公司代码
	 */
	private String carrier;
	
	/**
	 * 航班号
	 */
	private String flightNo;
	
	/**
	 * 离港机场代码
	 */
	private String boardPoint;
	
	/**
	 * 到港机场代码
	 */
	private String offPoint;
	
	/**
	 * 舱位代码
	 */
	private String classCode;
	
	/**
	 * 离港日期
	 */
	private String departureDate;

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getBoardPoint() {
		return boardPoint;
	}

	public void setBoardPoint(String boardPoint) {
		this.boardPoint = boardPoint;
	}

	public String getOffPoint() {
		return offPoint;
	}

	public void setOffPoint(String offPoint) {
		this.offPoint = offPoint;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	
	
}
