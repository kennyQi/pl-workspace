package hsl.domain.model.yxjp;

import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * 航班和舱位基本信息
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings("serial")
public class YXJPOrderFlightBaseInfo implements Serializable {

	/**
	 * 离港机场名称（例：上海虹桥国际机场）
	 */
	@Column(name = "DEPART_AIRPORT", length = 64)
	private String departAirport;

	/**
	 * 到港机场名称（例：广州白云国际机场）
	 */
	@Column(name = "ARRIVAL_AIRPORT", length = 64)
	private String arrivalAirport;

	/**
	 * 始发地三字码（例：SHA）
	 */
	@Column(name = "ORG_CITY", length = 8)
	private String orgCity;

	/**
	 * 始发地名称（例：上海）
	 */
	@Column(name = "ORG_CITY_NAME", length = 64)
	private String orgCityName;

	/**
	 * 目的地三字码（例：CAN）
	 */
	@Column(name = "DST_CITY", length = 8)
	private String dstCity;

	/**
	 * 目的地名称（例：广州）
	 */
	@Column(name = "DST_CITY_NAME", length = 64)
	private String dstCityName;

	/**
	 * 始发地航站楼（例：T2）
	 */
	@Column(name = "DEPART_TERM", length = 8)
	private String departTerm;

	/**
	 * 目的地航站楼（例：T1）
	 */
	@Column(name = "ARRIVAL_TERM", length = 8)
	private String arrivalTerm;

	/**
	 * 航空公司代码（例：CA）
	 */
	@Column(name = "AIR_COMP", length = 8)
	private String airComp;

	/**
	 * 航空公司名称（例：中国国际航空公司）
	 */
	@Column(name = "AIR_COMP_NAME", length = 64)
	private String airCompanyName;

	/**
	 * 航空公司简称（例：国航）
	 */
	@Column(name = "AIR_COMP_SHORT_NAME", length = 64)
	private String airCompanyShortName;

	/**
	 * 航班号（例：CA1837）
	 */
	@Column(name = "FLIGHT_NO", length = 16)
	private String flightNo;

	/**
	 * 机型（例：33A）
	 */
	@Column(name = "PLANE_TYPE", length = 16)
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
	 * 舱位折扣（80即八折）
	 */
	@Column(name = "CABIN_DISCOUNT", columnDefinition = M.NUM_COLUM)
	private Integer cabinDiscount;

	/**
	 * 舱位代码（例：M）
	 */
	@Column(name = "CABIN_CODE", length = 8)
	private String cabinCode;

	/**
	 * 舱位名称（例：经济舱）
	 */
	@Column(name = "CABIN_NAME", length = 16)
	private String cabinName;

	/**
	 * 仓位类型
	 * 0：普通；1：特殊
	 */
	@Column(name = "CABIN_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer cabinType;

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

	public String getAirCompanyShortName() {
		return airCompanyShortName;
	}

	public void setAirCompanyShortName(String airCompanyShortName) {
		this.airCompanyShortName = airCompanyShortName;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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

	public Integer getCabinDiscount() {
		return cabinDiscount;
	}

	public void setCabinDiscount(Integer cabinDiscount) {
		this.cabinDiscount = cabinDiscount;
	}

	public String getCabinCode() {
		return cabinCode;
	}

	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}

	public String getCabinName() {
		return cabinName;
	}

	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}

	public Integer getCabinType() {
		return cabinType;
	}

	public void setCabinType(Integer cabinType) {
		this.cabinType = cabinType;
	}
}
