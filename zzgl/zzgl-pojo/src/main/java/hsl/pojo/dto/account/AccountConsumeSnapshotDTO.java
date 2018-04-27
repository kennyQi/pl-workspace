package hsl.pojo.dto.account;


import java.util.Date;

import javax.persistence.Column;

import hsl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class AccountConsumeSnapshotDTO extends BaseDTO{
	private AccountDTO account;
	/**
	 * 订单类型 
	 */
	private Integer orderType;
	/**
	 * 订单ID
	 */
    private String orderId;
    /**
	 * 消费价格
	 */
    private Double payPrice;
    /**
   	 * 详细
   	 */
    private String detail;
    /**
   	 * 状态
   	 */
    private Integer status;
    /**
   	 * 退款金额
   	 */
    private Double refundPrice;
    /**
     * 创建日期
     */
    private Date createDate;
	public AccountDTO getAccount() {
		return account;
	}
	public void setAccount(AccountDTO account) {
		this.account = account;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getRefundPrice() {
		return refundPrice;
	}
	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
}
