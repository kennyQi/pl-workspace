package zzpl.pojo.dto.jp.plfx.gj;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class ApplyCancelNoPayOrderGJDTO extends BaseDTO {
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
