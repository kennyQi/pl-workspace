package hsl.pojo.command.yxjp.order;


import hg.common.component.BaseCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * 取消未支付易行机票订单（管理员操作，必须附带fromAdminId）
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
public class CancelNoPayYXJPOrderCommand extends BaseCommand {

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 乘客ID
	 */
	private List<String> passengerIds;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<String> getPassengerIds() {
		if (passengerIds == null)
			passengerIds = new ArrayList<String>();
		return passengerIds;
	}

	public void setPassengerIds(List<String> passengerIds) {
		this.passengerIds = passengerIds;
	}
}
