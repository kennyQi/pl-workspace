package zzpl.api.client.dto.jp;

import java.util.Date;

public class GNFlightListDTO {

	/**
	 * 航班ID
	 */
	private String flightID;

	/**
	 * 离港机场
	 */
	private String departAirport;

	/**
	 * 到港机场
	 */
	private String arrivalAirport;

	/**
	 * 始发地航站楼
	 */
	private String departTerm;

	/**
	 * 目的地航站楼
	 */
	private String arrivalTerm;

	/**
	 * 始发地
	 */
	private String orgCity;

	/**
	 * 始发地
	 */
	private String orgCityName;

	/**
	 * 目的地
	 */
	private String dstCity;

	/**
	 * 目的地
	 */
	private String dstCityName;

	/**
	 * 航空公司
	 */
	private String airComp;

	/**
	 * 航空公司名称 深圳航空
	 */
	private String airCompanyName;

	/**
	 * 航班
	 */
	private String flightNO;

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
	 * 舱位折扣
	 */
	private String cabinDiscount;

	/**
	 * 舱位名称
	 */
	private String cabinName;

	/**
	 * 舱位
	 */
	private String cabinCode;

	/**
	 * 舱位加密字符串
	 */
	private String encryptString;

	/**
	 * 参考机场建设费
	 */
	private Double buildFee;
	/**
	 * 参考燃油费
	 */
	private Double oilFee;

	/**
	 * 票面价
	 */
	private String ibePrice;

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
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

	public String getDepartTerm() {
		return departTerm;
	}

	public void setDepartTerm(String departTerm) {
		this.departTerm = departTerm;
	}

	public String getArrivalTerm() {
		return arrivalTerm;
	}

	public void setArrivalTerm(String arrivalTerm) {
		this.arrivalTerm = arrivalTerm;
	}

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getOrgCityName() {
		return orgCityName;
	}

	public void setOrgCityName(String orgCityName) {
		this.orgCityName = orgCityName;
	}

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}

	public String getDstCityName() {
		return dstCityName;
	}

	public void setDstCityName(String dstCityName) {
		this.dstCityName = dstCityName;
	}

	public String getAirComp() {
		return airComp;
	}

	public void setAirComp(String airComp) {
		this.airComp = airComp;
	}

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public String getFlightNO() {
		return flightNO;
	}

	public void setFlightNO(String flightNO) {
		this.flightNO = flightNO;
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

	public String getCabinDiscount() {
		return cabinDiscount;
	}

	public void setCabinDiscount(String cabinDiscount) {
		this.cabinDiscount = cabinDiscount;
	}

	public String getCabinName() {
		return cabinName;
	}

	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}

	public String getCabinCode() {
		return cabinCode;
	}

	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	public Double getBuildFee() {
		return buildFee;
	}

	public void setBuildFee(Double buildFee) {
		this.buildFee = buildFee;
	}

	public Double getOilFee() {
		return oilFee;
	}

	public void setOilFee(Double oilFee) {
		this.oilFee = oilFee;
	}

	public String getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(String ibePrice) {
		this.ibePrice = ibePrice;
	}

}
