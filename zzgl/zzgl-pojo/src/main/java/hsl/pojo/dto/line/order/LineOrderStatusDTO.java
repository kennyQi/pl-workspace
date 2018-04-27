package hsl.pojo.dto.line.order;

import hsl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class LineOrderStatusDTO extends BaseDTO{

	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 支付状态
	 */
	private Integer payStatus;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	

}
