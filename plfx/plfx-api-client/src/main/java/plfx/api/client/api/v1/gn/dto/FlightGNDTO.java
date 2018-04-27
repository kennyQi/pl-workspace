package plfx.api.client.api.v1.gn.dto;

import java.util.Date;
import java.util.List;

import plfx.api.client.common.BaseDTO;
@SuppressWarnings("serial")
public class FlightGNDTO extends BaseDTO{
	/**
	 * 始发地
	 */
	private String orgCity;
	/**
	 * 始发地,名称
	 */
	private String orgCityName;
	/**
	 * 始发地航站楼
	 */
	private String departTerm;
	/**
	 * 航空公司名称 
	 * 中国国际航空公司
	 */
	private String airCompanyName;
	/**
	 * 航空公司名称 简称
	 * 国航
	 */
	private String airCompanyShotName;
	/**
	 * 目的地
	 */
	private String dstCity;
	/**
	 * 目的地,名称
	 */
	private String dstCityName;
	/**
	 * 目的地航站楼
	 */
	private String arrivalTerm;
	/**
	 * 航空公司
	 */
	private String airComp;
	/**
	 *航班 
	 */
	private String flightno;
	/**
	 * 机型
	 */
	private String planeType;
	/**
	 * 出发时间
	 */
//	private String startTime;
	private Date startTime;
	/**
	 * 到达时间
	 */
//	private String endTime;
	private Date endTime;
	/**
	 * 经停次数
	 */
	private String stopNumber;
	/**
	 * 餐食代码
	 */
	private String mealCode;
	/**
	 * 舱位信息
	 */
	private List<CabinGNDTO> cabinList;
	
	public List<CabinGNDTO> getCabinList() {
		return cabinList;
	}
	public void setCabinList(List<CabinGNDTO> cabinList) {
		this.cabinList = cabinList;
	}
	public String getOrgCity() {
		return orgCity;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}
	public String getDepartTerm() {
		return departTerm;
	}
	public void setDepartTerm(String departTerm) {
		this.departTerm = departTerm;
	}
	public String getDstCity() {
		return dstCity;
	}
	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}
	public String getArrivalTerm() {
		return arrivalTerm;
	}
	public void setArrivalTerm(String arrivalTerm) {
		this.arrivalTerm = arrivalTerm;
	}
	public String getAirComp() {
		return airComp;
	}
	public void setAirComp(String airComp) {
		this.airComp = airComp;
	}
	public String getFlightno() {
		return flightno;
	}
	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}
	public String getPlaneType() {
		return planeType;
	}
	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}
//	public String getStartTime() {
//		return startTime;
//	}
//	public void setStartTime(String startTime) {
//		this.startTime = startTime;
//	}
//	public String getEndTime() {
//		return endTime;
//	}
//	public void setEndTime(String endTime) {
//		this.endTime = endTime;
//	}
	
	public String getStopNumber() {
		return stopNumber;
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
	public void setStopNumber(String stopNumber) {
		this.stopNumber = stopNumber;
	}
	public String getMealCode() {
		return mealCode;
	}
	public void setMealCode(String mealCode) {
		this.mealCode = mealCode;
	}
	public String getAirCompanyName() {
		return airCompanyName;
	}
	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}
	public String getOrgCityName() {
		return orgCityName;
	}
	public void setOrgCityName(String orgCityName) {
		this.orgCityName = orgCityName;
	}
	public String getDstCityName() {
		return dstCityName;
	}
	public void setDstCityName(String dstCityName) {
		this.dstCityName = dstCityName;
	}
	public String getAirCompanyShotName() {
		return airCompanyShotName;
	}
	public void setAirCompanyShotName(String airCompanyShotName) {
		this.airCompanyShotName = airCompanyShotName;
	}
}
