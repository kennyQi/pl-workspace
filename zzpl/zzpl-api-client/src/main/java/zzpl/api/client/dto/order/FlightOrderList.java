package zzpl.api.client.dto.order;

import java.util.Date;

public class FlightOrderList {
	/**
	 * 订单编号
	 */
	private String orderID;

	/**
	 * 订单编号
	 */
	private String orderNO;
	
	/**
	 * 离港机场
	 */
	private String departAirport;

	/**
	 * 到港机场
	 */
	private String arrivalAirport;

	/**
	 * 始发地
	 */
	private String orgCity;

	private String orgCityName;

	/**
	 * 目的地
	 */
	private String dstCity;

	private String dstCityName;

	/**
	 * 始发地航站楼
	 */
	private String departTerm;

	/**
	 * 目的地航站楼
	 */
	private String arrivalTerm;

	/**
	 * 航空公司
	 */
	private String airComp;

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
	 * 总支付金额
	 */
	private Double totalPrice;

	/**
	 * 票面价
	 */
	private String ibePrice;
	
	/**
	 * 订单状态
	 */
	private Integer status;

	/**
	 * 支付类型
	 */
	private String payType;

	/**
	 * 支付状态
	 */
	private Integer payStatus;

	private String replaceBuy;
	
	/**
	 * 代购
	 */
	public static final String REPLACE_BUY_OK = "1";
	/**
	 * 非代购
	 */
	public static final String REPLACE_BUY_NOT = "2";
	
	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(String ibePrice) {
		this.ibePrice = ibePrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getReplaceBuy() {
		return replaceBuy;
	}

	public void setReplaceBuy(String replaceBuy) {
		this.replaceBuy = replaceBuy;
	}


	
}
