package zzpl.pojo.dto.jp;


import java.util.Date;
import java.util.Set;

import zzpl.pojo.dto.BaseDTO;
@SuppressWarnings("serial")
public class JPOrderDTO extends BaseDTO{

	/** 平台订单号 */
	private String orderCode;

	/** 经销商订单号 */
	private String dealerOrderCode;
	
	/**
	 * 支付金额
	 */
	private Double payAmount;
	/**
	 * 退废金额
	 */
	private Double backPrice;

	/**
	 * 实际退款金额
	 */
	private Double returnedPrice;
	

	/**
	 * 单人票面价
	 */
	private Double ticketPrice;
	
	/**
	 * 单张机票税款（基建+燃油）
	 */
	private Double singleTaxAmount;
	
	/**
	 * 商家信息
	 */
	private String agencyName = "汇购商旅平台";

	/** 下单时间 */
	private Date createDate;

	/** PNR */
	private String pnr;

	/**
	 * 乘机人
	 */
	private Set<FlightPassangerDTO> passangers;

	/** 订单状态 */
	private Integer status;
	
	/** 支付状态 */
	private Integer payStatus;

	/** 支付类型 */
	private String supplierPayType;

	/**
	 * 订单对应航班信息
	 */
	private FlightDTO flightDTO;
	
	/**
	 * 订单航班舱位
	 */
	private String classCode;
	
	/**
	 * 机票订单人
	 */
	private JPOrderUserDTO orderUser;
	
	/**第三方支付单号 */
	private String payTradeNo;
	
	/** 付款支付宝账号 */
	private String buyerEmail;

	/**
	 * 工作时间
	 */
	private String workTime;
	
	/**
	 * 退票时间
	 */
	private String refundWorkTime;
	
	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getRefundWorkTime() {
		return refundWorkTime;
	}

	public void setRefundWorkTime(String refundWorkTime) {
		this.refundWorkTime = refundWorkTime;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getBackPrice() {
		return backPrice;
	}

	public void setBackPrice(Double backPrice) {
		this.backPrice = backPrice;
	}

	public Double getReturnedPrice() {
		return returnedPrice;
	}

	public void setReturnedPrice(Double returnedPrice) {
		this.returnedPrice = returnedPrice;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Double getSingleTaxAmount() {
		return singleTaxAmount;
	}

	public void setSingleTaxAmount(Double singleTaxAmount) {
		this.singleTaxAmount = singleTaxAmount;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public Set<FlightPassangerDTO> getPassangers() {
		return passangers;
	}

	public void setPassangers(Set<FlightPassangerDTO> passangers) {
		this.passangers = passangers;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getSupplierPayType() {
		return supplierPayType;
	}

	public void setSupplierPayType(String supplierPayType) {
		this.supplierPayType = supplierPayType;
	}

	public FlightDTO getFlightDTO() {
		return flightDTO;
	}

	public void setFlightDTO(FlightDTO flightDTO) {
		this.flightDTO = flightDTO;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public JPOrderUserDTO getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(JPOrderUserDTO orderUser) {
		this.orderUser = orderUser;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	
}
