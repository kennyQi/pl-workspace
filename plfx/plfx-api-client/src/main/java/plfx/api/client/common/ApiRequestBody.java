package plfx.api.client.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @类功能说明：API请求体
 * @类修改者：
 * @修改日期：2014-11-18下午2:17:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午2:17:25
 */
public abstract class ApiRequestBody implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 来自经销商的ID 客户端调用时无需带上
	 */
	private String fromDealerId;

	/**
	 * 来自经销商的CODE 客户端调用时无需带上
	 */
	private String fromDealerCode;

	/**
	 * 来源客户端类型 0来自移动端，1来自pc端
	 */
	private Integer fromClientType;
	
	/**
	 * 来源经销商的ip地址 客户端调用时无需带上
	 */
	private String fromDealerIp;
	//-----------------------------------------

	/**
	 * 付款账号
	 */
	private String payAccountNo;
	/**
	 * 支付流水号
	 */
	private String paySerialNumber;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderNo;

	/**
	 * 收入金额
	 */
	private Double incomeMoney;

	/**
	 * 支出金额
	 */
	private Double payMoney;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 支付状态
	 */
	private String payStatus;

	
	/**
	 * 支付时间
	 */
	private Date payTime;
	
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 来源项目标识 票量分销生产、中智票量测试
	 */
	private Integer fromProjectCode;

	/**
	 * 支付平台 1—支付宝 2—汇付 6—IPS 7—德付通
	 */
	private Integer payPlatform;

	/**
	 * 乘机人，多个乘机人之间用"|"分割
	 */
	private String passengers;

	/**
	 * 来源项目IP
	 */
	private String fromProjectIP;

	/**
	 * 订购人
	 */
	private String booker;

	/**
	 * 出发地
	 */
	private String startCity;

	/**
	 * 目的地
	 */
	private String destCity;

	/**
	 * 返点/折扣
	 */
	private Double rebate;

	/**
	 * 佣金
	 */
	private Double brokerage;
	
	/**
	 * 记录类型 1：用户->直销 2：分销->供应商 3:供应商->分销 4:分销->用户 5:直销->分销 6:分销->直销
	 */
	private Integer recordType;

	/**
	 * 分销平台订单号
	 */
	private String platOrderNo;

	/**
	 * 供应商订单号
	 */
	private String supplierNo;
	
	/***
	 * 订单金额
	 */
	public Double orderPrice;
	
	/**
	 * 退给客户金额
	 */
	public Double toUserRefundMoney;
	
	/***
	 * 实退金额(客户)
	 */
	public Double realRefundMoney;
	
	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Double getToUserRefundMoney() {
		return toUserRefundMoney;
	}

	public void setToUserRefundMoney(Double toUserRefundMoney) {
		this.toUserRefundMoney = toUserRefundMoney;
	}

	public Double getRealRefundMoney() {
		return realRefundMoney;
	}

	public void setRealRefundMoney(Double realRefundMoney) {
		this.realRefundMoney = realRefundMoney;
	}

	public String getPayAccountNo() {
		return payAccountNo;
	}

	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}

	public String getPaySerialNumber() {
		return paySerialNumber;
	}

	public void setPaySerialNumber(String paySerialNumber) {
		this.paySerialNumber = paySerialNumber;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public Double getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(Double incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
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

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getFromProjectCode() {
		return fromProjectCode;
	}

	public void setFromProjectCode(Integer fromProjectCode) {
		this.fromProjectCode = fromProjectCode;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}

	public String getPassengers() {
		return passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	public String getFromProjectIP() {
		return fromProjectIP;
	}

	public void setFromProjectIP(String fromProjectIP) {
		this.fromProjectIP = fromProjectIP;
	}

	public String getBooker() {
		return booker;
	}

	public void setBooker(String booker) {
		this.booker = booker;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Double brokerage) {
		this.brokerage = brokerage;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	public String getPlatOrderNo() {
		return platOrderNo;
	}

	public void setPlatOrderNo(String platOrderNo) {
		this.platOrderNo = platOrderNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getFromDealerId() {
		return fromDealerId;
	}

	public void setFromDealerId(String fromDealerId) {
		this.fromDealerId = fromDealerId;
	}

	public String getFromDealerCode() {
		return fromDealerCode;
	}

	public void setFromDealerCode(String fromDealerCode) {
		this.fromDealerCode = fromDealerCode;
	}

	public Integer getFromClientType() {
		return fromClientType;
	}

	public void setFromClientType(Integer fromClientType) {
		this.fromClientType = fromClientType;
	}

	public String getFromDealerIp() {
		return fromDealerIp;
	}

	public void setFromDealerIp(String fromDealerIp) {
		this.fromDealerIp = fromDealerIp;
	}

}
