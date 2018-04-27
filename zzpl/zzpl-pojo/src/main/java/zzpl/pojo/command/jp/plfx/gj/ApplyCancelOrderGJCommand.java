package zzpl.pojo.command.jp.plfx.gj;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ApplyCancelOrderGJCommand extends BaseCommand{
	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

}