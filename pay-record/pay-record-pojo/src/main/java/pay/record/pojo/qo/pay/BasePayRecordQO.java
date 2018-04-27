package pay.record.pojo.qo.pay;

import hg.common.component.BaseQo;

import java.util.Date;

/**
 * 
 * @类功能说明：支付记录基础QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月8日上午11:07:07
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class BasePayRecordQO extends BaseQo{
	/**
	 * 支付平台
	 * 0—支付宝 2—汇付 6—IPS 7—德付通
	 */
	public Integer payPlatform;
	
	/** 
	 * 付款账号 
	 */
	public String payAccountNo;
	
	/**
	 * 乘机人，多个乘机人之间用"|"分割
	 */
	public String passengers;
	
	/**
	 * 来源项目标识
	 * 票量分销生产、中智票量测试
	 */
	public Integer fromProjectCode;
	
	/**
	 * 来源项目IP
	 */
	public String fromProjectIP;
	
	/**
	 * 订购人
	 */
	public String booker;
	
	/**
	 * 分销平台订单号
	 */
	public String platOrderNo;
	
	/**
	 * 经销商订单号
	 */
	public String dealerOrderNo;
	
	/**
	 * 供应商订单号
	 */
	public String supplierNo;
	
	/**
	 * 支付流水号
	 */
	public String paySerialNumber;
	
	/**
	 * 订单状态
	 */
	public String orderStatus;
	
	/**
	 * 支付状态
	 */

	public String payStatus;
	
	/**
	 * 支付时间
	 */
	public Date payTime;
	
	/**
	 * 记录类型
	 * 1：用户->直销
	 * 2：分销->供应商
	 * 3:供应商->分销
	 * 4:直销->用户
	 * 5:直销->分销
	 * 6:分销->直销
	 */
	public Integer recordType;
	
	/**
	 * 创建时间
	 */
	public Date createTime;
	
	/**
	 * 支付开始时间(用于时间段查询订单)
	 */
	public Date payDateFrom;
	
	/**
	 * 支付结束时间 (用于时间段查询订单)
	 */
	public Date payDateTo;

	/****
	 * 排序条件
	 */
	public Boolean createDateAsc;

	public Integer getFromProjectCode() {
		return fromProjectCode;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}

	public void setFromProjectCode(Integer fromProjectCode) {
		this.fromProjectCode = fromProjectCode;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	public String getPayAccountNo() {
		return payAccountNo;
	}

	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
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

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getPaySerialNumber() {
		return paySerialNumber;
	}

	public void setPaySerialNumber(String paySerialNumber) {
		this.paySerialNumber = paySerialNumber;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayDateFrom() {
		return payDateFrom;
	}

	public void setPayDateFrom(Date payDateFrom) {
		this.payDateFrom = payDateFrom;
	}

	public Date getPayDateTo() {
		return payDateTo;
	}

	public void setPayDateTo(Date payDateTo) {
		this.payDateTo = payDateTo;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}
}
