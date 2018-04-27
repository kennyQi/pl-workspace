package zzpl.pojo.dto.jp.plfx.gj;

import java.util.Date;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class GJFlightCabinDTO extends BaseDTO {

	/**
	 * 始发地
	 */
	private String orgCity;

	/**
	 * 始发地航站楼
	 */
	private String orgTerm;

	/**
	 * 目的地
	 */
	private String dstCity;

	/**
	 * 目的地航站楼
	 */
	private String dstTerm;

	/**
	 * 离港机场
	 */
	private String departAirport;

	/**
	 * 到港机场
	 */
	private String arrivalAirport;

	/**
	 * 市场方航空公司
	 */
	private String marketingAirline;

	/**
	 * 出票方航司
	 */
	private String ticketingCarrier;

	/**
	 * 航班号
	 */
	private String flightNo;

	/**
	 * 承运方航空公司
	 */
	private String carriageAirline;

	/**
	 * 承运方航班号
	 */
	private String carriageFlightno;

	/**
	 * 餐食代码
	 * 
	 * B:早餐；L：午餐；S：小吃
	 */
	private String mealCode;

	/**
	 * 舱位等级代码
	 */
	private String cabinCode;

	/**
	 * 剩余座位数
	 */
	private Integer cabinSales;

	/**
	 * 机型
	 */
	private String planeType;

	/**
	 * 出发时间
	 */
	private Date startTime;

	/**
	 * 到达时间
	 */
	private Date endTime;

	/**
	 * 是否共享
	 */
	private Boolean isShare;

	/**
	 * 飞行时间(包含经停时间,单位分钟)
	 */
	private Integer duration;

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getOrgTerm() {
		return orgTerm;
	}

	public void setOrgTerm(String orgTerm) {
		this.orgTerm = orgTerm;
	}

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}

	public String getDstTerm() {
		return dstTerm;
	}

	public void setDstTerm(String dstTerm) {
		this.dstTerm = dstTerm;
	}

	public String getDepartAirport() {
		return departAirport;
	}

	public void setDepartAirport(String departAirport) {
		this.departAirport = departAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public String getMarketingAirline() {
		return marketingAirline;
	}

	public void setMarketingAirline(String marketingAirline) {
		this.marketingAirline = marketingAirline;
	}

	public String getTicketingCarrier() {
		return ticketingCarrier;
	}

	public void setTicketingCarrier(String ticketingCarrier) {
		this.ticketingCarrier = ticketingCarrier;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getCarriageAirline() {
		return carriageAirline;
	}

	public void setCarriageAirline(String carriageAirline) {
		this.carriageAirline = carriageAirline;
	}

	public String getCarriageFlightno() {
		return carriageFlightno;
	}

	public void setCarriageFlightno(String carriageFlightno) {
		this.carriageFlightno = carriageFlightno;
	}

	public String getMealCode() {
		return mealCode;
	}

	public void setMealCode(String mealCode) {
		this.mealCode = mealCode;
	}

	public String getCabinCode() {
		return cabinCode;
	}

	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}

	public Integer getCabinSales() {
		return cabinSales;
	}

	public void setCabinSales(Integer cabinSales) {
		this.cabinSales = cabinSales;
	}

	public String getPlaneType() {
		return planeType;
	}

	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
