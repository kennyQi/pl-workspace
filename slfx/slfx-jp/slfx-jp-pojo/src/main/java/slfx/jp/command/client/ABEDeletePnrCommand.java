package slfx.jp.command.client;

import hg.common.component.BaseCommand;

public class ABEDeletePnrCommand extends BaseCommand {

	private static final long serialVersionUID = -4648060151853056755L;
	
	/**
	 * PNR  必填
	 */
	private String pnr;
	
	/**
	 * 订单编号  非必填
	 */
	private String orderNo;

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
