package hsl.pojo.command;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class MPSyncOrderCommand extends BaseCommand {

	//	要同步的平台订单id
	private String orderId;
	
	private String platformOrderCode;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPlatformOrderCode() {
		return platformOrderCode;
	}

	public void setPlatformOrderCode(String platformOrderCode) {
		this.platformOrderCode = platformOrderCode;
	}

}
