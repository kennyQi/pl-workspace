package slfx.mp.command;

/**
 * 改变订单状态
 * 
 * @author zhurz
 */
public class ModifyMPOrderStatusCommand {

	/**
	 * 是否可以取消
	 */
	private Boolean cancelAble;

	/**
	 * 订单状态
	 * 
	 * @see slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum
	 */
	private Integer orderStatus;

	public Boolean getCancelAble() {
		return cancelAble;
	}

	public void setCancelAble(Boolean cancelAble) {
		this.cancelAble = cancelAble;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

}
