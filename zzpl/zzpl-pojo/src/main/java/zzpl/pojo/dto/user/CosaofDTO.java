package zzpl.pojo.dto.user;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class CosaofDTO extends BaseDTO {

	/***
	 * 乘机人姓名
	 */
	private String passengerName;
	
	/***
	 * 订票人姓名
	 */
	private String userName;

	/**
	 * 订单类型 1：国内机票 2：国际机票
	 */
	private String orderType;

	/**
	 * 订单ID
	 */
	private String orderID;
	/**
	 * 订单编号
	 */
	private String orderNO;

	/**
	 * 公司ID
	 */
	private String companyID;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 部门ID
	 */
	private String departmentID;

	/**
	 * 部门名称
	 */
	private String departmentName;

	/***
	 * PNR
	 */
	private String pnr;

	/***
	 * 航程信息
	 */
	private String voyage;
	
	/**
	 * 发生退款时退款金额
	 */
	private Double refundPrice;

	/**
	 * ---------------------------------真正购票成功后经销商返回----------------------------
	 * 机票成本价
	 */
	private Double totalPrice;
	
	/**
	 * 分销价加
	 */
	private Double platTotalPrice;

	/**
	 * 结算价格
	 */
	private Double casaofPrice;

	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	private String createTime;

	/**
	 * 票号
	 */
	private String airID;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	/**
	 * 订单状态
	 */
	private String oStatus;

	/**
	 * 结算订单状态
	 */
	private Integer cosaofStatus;
	
	/**
	 * 结算订单状态
	 */
	private String status;
	/**
	 * 成本中心
	 */
	private String costCenterName;
	
	/**
	 * 申请人价
	 */
	private Double userPayPrice;

	/**
	 * 申请人退款价
	 */
	private Double userRefundPrice;
	
	/**
	 * 申请分销价格失败
	 */
	private String failed;

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

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

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}

	public Double getCasaofPrice() {
		return casaofPrice;
	}

	public void setCasaofPrice(Double casaofPrice) {
		this.casaofPrice = casaofPrice;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getoStatus() {
		return oStatus;
	}

	public void setoStatus(String oStatus) {
		this.oStatus = oStatus;
	}

	public Integer getCosaofStatus() {
		return cosaofStatus;
	}

	public void setCosaofStatus(Integer cosaofStatus) {
		this.cosaofStatus = cosaofStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public Double getUserPayPrice() {
		return userPayPrice;
	}

	public void setUserPayPrice(Double userPayPrice) {
		this.userPayPrice = userPayPrice;
	}

	public Double getUserRefundPrice() {
		return userRefundPrice;
	}

	public void setUserRefundPrice(Double userRefundPrice) {
		this.userRefundPrice = userRefundPrice;
	}

	public String getFailed() {
		return failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}

}
