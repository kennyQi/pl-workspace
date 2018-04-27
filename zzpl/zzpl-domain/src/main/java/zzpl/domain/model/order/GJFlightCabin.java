package zzpl.domain.model.order;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_ORDER + "FLIGHT_CABIN_GJ")
public class GJFlightCabin extends BaseModel {

	/**
	 * 始发地
	 */
	@Column(name = "ORG_CITY", length = 512)
	private String orgCity;

	/**
	 * 始发地航站楼
	 */
	@Column(name = "ORG_TERM", length = 512)
	private String orgTerm;

	/**
	 * 目的地
	 */
	@Column(name = "DST_CITY", length = 512)
	private String dstCity;

	/**
	 * 目的地航站楼
	 */
	@Column(name = "DST_TERM", length = 512)
	private String dstTerm;

	/**
	 * 离港机场
	 */
	@Column(name = "DEPART_AIRPORT", length = 512)
	private String departAirport;

	/**
	 * 到港机场
	 */
	@Column(name = "ARRIVAL_AIRPORT", length = 512)
	private String arrivalAirport;
	
	/**
	 * 承运方航空公司
	 */
	@Column(name = "ACRRIAGE_AIRLINE", length = 512)
	private String carriageAirline;

	/**
	 * 承运方航班号
	 */
	@Column(name = "CARRIAGE_FLIGHTNO", length = 512)
	private String carriageFlightno;

	/**
	 * 餐食代码
	 * 
	 * B:早餐；L：午餐；S：小吃
	 */
	@Column(name = "MEAL_CODE", length = 512)
	private String mealCode;

	/**
	 * 舱位等级代码
	 */
	@Column(name = "CABIN_CODE", length = 512)
	private String cabinCode;

	/**
	 * 机型
	 */
	@Column(name = "PLANE_TYPE", length = 512)
	private String planeType;

	/**
	 * 出发时间
	 */
	@Column(name = "START_TIME", columnDefinition = M.DATE_COLUM)
	private Date startTime;

	/**
	 * 到达时间
	 */
	@Column(name = "END_TIME", columnDefinition = M.DATE_COLUM)
	private Date endTime;

	/**
	 * 飞行时间(包含经停时间,单位分钟)
	 */
	@Column(name = "DUTATION", columnDefinition = M.NUM_COLUM)
	private Integer duration;

	/**
	 * 关联航班
	 */
	@ManyToOne
	@JoinColumn(name="FLIGHT_ORDER_GJ_ID")
	private GJFlightOrder gjFlightOrder;

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

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public GJFlightOrder getGjFlightOrder() {
		return gjFlightOrder;
	}

	public void setGjFlightOrder(GJFlightOrder gjFlightOrder) {
		this.gjFlightOrder = gjFlightOrder;
	}
	
}
