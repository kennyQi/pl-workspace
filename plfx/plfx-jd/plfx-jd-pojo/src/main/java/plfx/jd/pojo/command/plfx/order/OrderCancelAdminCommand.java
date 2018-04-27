package plfx.jd.pojo.command.plfx.order;

import java.io.Serializable;

public class OrderCancelAdminCommand implements Serializable{
	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 供应商订单编号
	 */
	private String supplierOrderNo;
	/**
	 * 原因
	 */
	private String reason;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}
	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
