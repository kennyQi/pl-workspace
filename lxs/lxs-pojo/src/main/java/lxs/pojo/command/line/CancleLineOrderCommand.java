package lxs.pojo.command.line;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CancleLineOrderCommand extends BaseCommand{

	/**
	 * 经销商订单号：dealerOrderNo
	 */
	private String lineOrderID;
	
	/**
	 * 要取消订单的游客ID
	 */
	private String[] travelerIDs;

	public String getLineOrderID() {
		return lineOrderID;
	}

	public void setLineOrderID(String lineOrderID) {
		this.lineOrderID = lineOrderID;
	}

	public String[] getTravelerIDs() {
		return travelerIDs;
	}

	public void setTravelerIDs(String[] travelerIDs) {
		this.travelerIDs = travelerIDs;
	}

	

}
