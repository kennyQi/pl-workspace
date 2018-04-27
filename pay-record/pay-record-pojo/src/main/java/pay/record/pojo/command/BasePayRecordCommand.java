package pay.record.pojo.command;

import hg.common.component.BaseCommand;

import java.util.Date;

@SuppressWarnings("serial")
public class BasePayRecordCommand extends BaseCommand{
	/**
	 * 来源项目标识
	 * 0:票量直销
	 * 1：商旅分销
	 * 2：中智票量
	 * 3：票量分销
	 * 4：智行
	 * 5：旅行社
	 */
	private Integer fromProjectCode;
	
	/**
	 * 来源项目环境
	 * 0：开发
	 * 1：测试
	 * 2：生产
	 */
	private Integer fromProjectEnv;
	
	/**
	 * 订购人
	 */
	private String booker;
	
	/**
	 * 经销商订单创建时间
	 */
	private Date dealerOrderCreateDate;
	
	/**
	 * 平台订单创建时间
	 */
	private Date platOrderCreateDate;
	
	/**
	 * 供应商订单号
	 */
	private String supplierOrderNo;
	
	/**
	 * 平台订单号
	 */
	private String platOrderNo;
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderNo;
	
	/**
	 * 乘机人，多个乘机人之间用"|"分割
	 */
	private String passengers;
	
	/**
	 * 出发地
	 */
	private String startPlace;
	
	/**
	 * 目的地
	 */
	private String destination;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 支付状态
	 */
	private String payStatus;
	
	/**
	 * 支付平台
	 * 0—支付宝
	 */
	private String payPlatform;
	
	/**
	 * 用户支付总价
	 * 包括卡券，现金
	 */
	private Double userPayAmount;
	
	/**
	 * 用户支付的现金
	 * 实际入公司支付宝账户的钱
	 */
	private Double userPayCash;
	
	/**
	 * 用户支付的优惠券金额
	 */
	private Double userPayCoupon;
	
	/**
	 * 用户支付的余额金额
	 */
	private Double userPayBalances;
	
	/** 
	 * 客户付款账号 
	 */
	private String userPayAccountNo;
	
	/**
	 * 客户业务流水号
	 */
	private String userBusinessSerialNumber;
	
	/**
	 * 供应商业务流水号
	 */
	private String supplierBusinessSerialNumber;
	
	/**
	 * 来源项目IP
	 */
	private String fromProjectIP;
	
	/**
	 * 支付时间
	 */
	private Date payTime;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 记录类型
	 * 1：用户->直销
	 * 2：分销->供应商
	 * 3:供应商->分销
	 * 4:直销->用户
	 * 5:直销->分销
	 * 6:分销->直销
	 */
	private String recordType;
	
	/**
	 * 来源客户端类型 
	 * 0：移动端，1：pc端
	 */
	private String fromClientType;

	public Integer getFromProjectCode() {
		return fromProjectCode;
	}

	public void setFromProjectCode(Integer fromProjectCode) {
		this.fromProjectCode = fromProjectCode;
	}

	public Integer getFromProjectEnv() {
		return fromProjectEnv;
	}

	public void setFromProjectEnv(Integer fromProjectEnv) {
		this.fromProjectEnv = fromProjectEnv;
	}

	public String getBooker() {
		return booker;
	}

	public void setBooker(String booker) {
		this.booker = booker;
	}

	public Date getDealerOrderCreateDate() {
		return dealerOrderCreateDate;
	}

	public void setDealerOrderCreateDate(Date dealerOrderCreateDate) {
		this.dealerOrderCreateDate = dealerOrderCreateDate;
	}

	public Date getPlatOrderCreateDate() {
		return platOrderCreateDate;
	}

	public void setPlatOrderCreateDate(Date platOrderCreateDate) {
		this.platOrderCreateDate = platOrderCreateDate;
	}

	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}

	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}

	public String getPlatOrderNo() {
		return platOrderNo;
	}

	public void setPlatOrderNo(String platOrderNo) {
		this.platOrderNo = platOrderNo;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public String getPassengers() {
		return passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}

	public Double getUserPayAmount() {
		return userPayAmount;
	}

	public void setUserPayAmount(Double userPayAmount) {
		this.userPayAmount = userPayAmount;
	}

	public Double getUserPayCash() {
		return userPayCash;
	}

	public void setUserPayCash(Double userPayCash) {
		this.userPayCash = userPayCash;
	}

	public Double getUserPayCoupon() {
		return userPayCoupon;
	}

	public void setUserPayCoupon(Double userPayCoupon) {
		this.userPayCoupon = userPayCoupon;
	}

	public Double getUserPayBalances() {
		return userPayBalances;
	}

	public void setUserPayBalances(Double userPayBalances) {
		this.userPayBalances = userPayBalances;
	}

	public String getUserPayAccountNo() {
		return userPayAccountNo;
	}

	public void setUserPayAccountNo(String userPayAccountNo) {
		this.userPayAccountNo = userPayAccountNo;
	}

	public String getUserBusinessSerialNumber() {
		return userBusinessSerialNumber;
	}

	public void setUserBusinessSerialNumber(String userBusinessSerialNumber) {
		this.userBusinessSerialNumber = userBusinessSerialNumber;
	}

	public String getSupplierBusinessSerialNumber() {
		return supplierBusinessSerialNumber;
	}

	public void setSupplierBusinessSerialNumber(String supplierBusinessSerialNumber) {
		this.supplierBusinessSerialNumber = supplierBusinessSerialNumber;
	}

	public String getFromProjectIP() {
		return fromProjectIP;
	}

	public void setFromProjectIP(String fromProjectIP) {
		this.fromProjectIP = fromProjectIP;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getFromClientType() {
		return fromClientType;
	}

	public void setFromClientType(String fromClientType) {
		this.fromClientType = fromClientType;
	}
}
