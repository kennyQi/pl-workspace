package hsl.pojo.command;


import hg.common.component.BaseCommand;
import hsl.pojo.dto.mp.MPOrderStatusDTO;

@SuppressWarnings("serial")
public class UpdateSyncOrderStatusCommand extends BaseCommand {

	private String orderId;

	/**
	 * 门票订单状态
	 */
	private MPOrderStatusDTO status;

	public MPOrderStatusDTO getStatus() {
		return status;
	}

	public void setStatus(MPOrderStatusDTO status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
