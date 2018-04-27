package hsl.pojo.dto.jp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class FlightBaseInfoDTO {

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
	 * 始发地
	 */
	@Column(name = "ORY_CITY", length = 512)
	private String orgCity;

	/**
	 * 目的地
	 */
	@Column(name = "DST_CITY", length = 512)
	private String dstCity;

	/**
	 * 始发地航站楼
	 */
	@Column(name = "DEPART_TERM", length = 512)
	private String departTerm;

	/**
	 * 目的地航站楼
	 */
	@Column(name = "ARRIVAL_TERM", length = 512)
	private String arrivalTerm;

	/**
	 * 航空公司
	 */
	@Column(name = "AIR_COMP", length = 512)
	private String airComp;

	/**
	 * 航班
	 */
	@Column(name = "FIGHT_NO", length = 512)
	private String flightNO;

	/**
	 * 机型
	 */
	@Column(name = "PLANE_TYPE", length = 512)
	private String planeType;

	/**
	 * 出发时间
	 */
	@Column(name = "START_TIME", length = 512)
	private Date startTime;

	/**
	 * 到达时间
	 */
	@Column(name = "END_TIME", length = 512)
	private Date endTime;

	/**
	 * 舱位折扣
	 */
	@Column(name = "CABIN_DISCOUNT", length = 512)
	private String cabinDiscount;

	/**
	 * 舱位名称
	 */
	@Column(name = "CABIN_NAME", length = 512)
	private String cabinName;
	/**
	 * 航空公司名称 深圳航空
	 */
	private String airCompanyName;
	/**
	 * 舱位加密字符串
	 */
	@Column(name = "ENCRYPT_STRING", length = 512)
	private String encryptString;
	/**
	 * 中转次数
	 */
	private Integer stopNumber;
	/**
	 * 支持的支付方式     
	 * 1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267
	 */
	
	private Integer  payPlatform;
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

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
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

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public Integer getStopNumber() {
		return stopNumber;
	}

	public void setStopNumber(Integer stopNumber) {
		this.stopNumber = stopNumber;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}




}
