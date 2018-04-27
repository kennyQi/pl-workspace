package zzpl.pojo.qo.jp;

import java.util.Date;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class FlightOrderQO extends BaseQo {

	/**
	 * 按照创建订单时间倒序
	 * 
	 */
	private String orderByCreatTime;

	public String getOrderByCreatTime() {
		return orderByCreatTime;
	}

	public void setOrderByCreatTime(String orderByCreatTime) {
		this.orderByCreatTime = orderByCreatTime;
	}

	/**
	 * 订单编号
	 */
	private String orderNO;

	/**
	 * 合并付款订单号 用于多人购票支付时给支付宝用的订单号 规则为 'UN_'+一个新的订单号（并不是每一张订单中的orderNO）
	 */
	private String unionOrderNO;

	/**
	 * 用户ID
	 */
	private String userID;
	/**
	 * 订单状态
	 */
	private Integer status;

	/**
	 * 公司ID
	 */
	private String companyID;
	
	private String companyName;

	private String roleID;
	/**
	 * 乘机人
	 */
	private String passengerName;

	/**
	 * 下单时间
	 */
	private Date startCreateTime;
	/**
	 * 下单时间
	 */
	private Date endCreateTime;
	
	/**
	 * 下单时间
	 */
	private String startDate;
	/**
	 * 下单时间
	 */
	private String endDate;

	/**
	 * 出发时间
	 */
	private Date startTime;

	/**
	 * 出发时间
	 */
	private Date endTime;

	/**
	 * 出发时间
	 */
	private String inTime;

	/**
	 * 出发时间
	 */
	private String toTime;

	/**
	 * 支付宝交易号
	 */
	private String tradeno;
	
	/**
	 * 航班号
	 */
	private String flightNO;
	
	private String airID;
	
	private String replacePerson;
	
	public String getUnionOrderNO() {
		return unionOrderNO;
	}

	public void setUnionOrderNO(String unionOrderNO) {
		this.unionOrderNO = unionOrderNO;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFlightNO() {
		return flightNO;
	}

	public void setFlightNO(String flightNO) {
		this.flightNO = flightNO;
	}

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public String getReplacePerson() {
		return replacePerson;
	}

	public void setReplacePerson(String replacePerson) {
		this.replacePerson = replacePerson;
	}

}
