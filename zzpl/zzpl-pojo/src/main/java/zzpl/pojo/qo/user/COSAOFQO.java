package zzpl.pojo.qo.user;

import java.util.Date;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class COSAOFQO extends BaseQo{
	
	/***
	 * 申请人姓名
	 */
	private String passengerName;

	/**
	 * 公司ID
	 */
	private String companyID;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 部门ID
	 */
	private String departmentID;
	/**
	 * 成本中心ID
	 */
	private String costCenterID;
	
	/**
	 * 订单ID
	 */
	private String orderID;
	
	private String roleID;
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
	 * 按照创建订单时间倒序
	 * 
	 */
	private String orderByCreatTime;

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getCostCenterID() {
		return costCenterID;
	}

	public void setCostCenterID(String costCenterID) {
		this.costCenterID = costCenterID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
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

	public String getOrderByCreatTime() {
		return orderByCreatTime;
	}

	public void setOrderByCreatTime(String orderByCreatTime) {
		this.orderByCreatTime = orderByCreatTime;
	}
	
}
