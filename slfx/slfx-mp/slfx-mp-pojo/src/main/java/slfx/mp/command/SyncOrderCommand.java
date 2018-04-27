package slfx.mp.command;

import hg.common.component.BaseCommand;

/**
 * 同步单个订单状态
 * 
 * @author zhurz
 */
public class SyncOrderCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分销平台订单号
	 */
	private String orderId;

	public SyncOrderCommand() {
	}

	public SyncOrderCommand(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
