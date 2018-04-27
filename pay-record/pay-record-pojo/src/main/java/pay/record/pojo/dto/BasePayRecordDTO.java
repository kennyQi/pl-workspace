package pay.record.pojo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @类功能说明：支付记录基础DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月20日下午4:30:08
 * @版本：V1.0
 * 
 */
public class BasePayRecordDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public String id;

	/**
	 * 调用接口名
	 * 
	 * @FieldsinterfaceName:TODO
	 */
	public String interfaceName;

	/**
	 * 调用时间
	 * 
	 * @FieldscallTime:TODO
	 */
	public Date callTime;

	/**
	 * 错误代码 0 表示成功 999999 自定义错误代码
	 * 
	 * @FieldserrorCode:TODO
	 */
	public String errorCode;

	/**
	 * 错误消息、描述
	 * 
	 * @FieldserrorMsg:TODO
	 */
	public String errorMsg;

	/**
	 * 支付平台 1—支付宝 2—汇付 6—IPS 7—德付通
	 */
	private Integer payPlatform;

	/**
	 * 付款账号
	 */
	private String payAccountNo;

	/**
	 * 乘机人，多个乘机人之间用"|"分割
	 */
	private String passengers;

	/**
	 * 来源项目标识 票量分销生产、中智票量测试
	 */
	private Integer fromProjectCode;

	/**
	 * 来源项目IP
	 */
	private String fromProjectIP;

	/**
	 * 订购人
	 */
	private String booker;

	/**
	 * 分销平台订单号
	 */
	private String platOrderNo;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderNo;

	/**
	 * 供应商订单号
	 */
	private String supplierNo;

	/**
	 * 支付流水号
	 */
	private String paySerialNumber;

	/**
	 * 出发地
	 */
	private String startCity;

	/**
	 * 目的地
	 */
	private String destCity;

	/**
	 * 支付方式
	 */
	private String payType;

	/**
	 * 收入金额
	 */
	private Double incomeMoney;

	/**
	 * 支出金额
	 */
	private Double payMoney;

	/**
	 * 返点/折扣
	 */
	public Double rebate;

	/**
	 * 佣金
	 */
	private Double brokerage;

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
	 * 记录类型 1：用户->直销 2：分销->供应商 3:供应商->分销 4:直销->用户 5:直销->分销 6:分销->直销
	 */
	private Integer recordType;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 订票平台退款金额
	 */
	private Double platRefunceMoney;

	/***
	 * 订单金额
	 */
	public Double orderPrice;
	
	/**
	 * 退给客户金额
	 */
	public Double toUserRefundMoney;
	
	/***
	 * 实退金额
	 */
	public Double realRefundMoney;
	
	/**
	 * 来源客户端类型
	 */
	public Integer fromClientType;
	
	
	public Integer getFromClientType() {
		return fromClientType;
	}

	public void setFromClientType(Integer fromClientType) {
		this.fromClientType = fromClientType;
	}

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

	public Double getPlatRefunceMoney() {
		return platRefunceMoney;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public void setPlatRefunceMoney(Double platRefunceMoney) {
		this.platRefunceMoney = platRefunceMoney;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}

	public Integer getFromProjectCode() {
		return fromProjectCode;
	}

	public void setFromProjectCode(Integer fromProjectCode) {
		this.fromProjectCode = fromProjectCode;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

}
