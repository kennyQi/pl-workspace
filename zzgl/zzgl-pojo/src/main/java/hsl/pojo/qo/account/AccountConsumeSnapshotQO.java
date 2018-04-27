package hsl.pojo.qo.account;


import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class AccountConsumeSnapshotQO extends BaseQo{
	/**
	 * 订单类型 
	 */
	private Integer orderType;
	/**
	 * 订单ID
	 */
    private String accountId;
	 /**
   	 * 订单状态
   	 */
    private Integer status;
	/**
	 * 退款订单的实体ID
	 */
	private String refundOrderId;
	 /**
   	 * 消费快照状态
   	 */
    private Integer accountstatus;
    /**
	 * 订单ID
	 */
    private String orderId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}
	public Integer getAccountstatus() {
		return accountstatus;
	}
	public void setAccountstatus(Integer accountstatus) {
		this.accountstatus = accountstatus;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}
