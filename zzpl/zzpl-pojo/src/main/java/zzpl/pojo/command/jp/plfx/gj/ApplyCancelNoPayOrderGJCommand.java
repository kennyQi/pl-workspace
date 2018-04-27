package zzpl.pojo.command.jp.plfx.gj;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ApplyCancelNoPayOrderGJCommand extends BaseCommand{
	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;

	/**
	 * 取消订单原因类型
	 * 
	 * 1:扣率过低 2:舱位过高 3:价格不正确 4:客人证件不齐 5:行程未确定 6:其他原因
	 */
	private Integer cancelReasonType;

	/**
	 * 取消订单其他原因具体内容
	 * 当cancelReasonType的值为6时，取消订单的具体原因为该字段的值
	 */
	private String cancelOtherReason;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public Integer getCancelReasonType() {
		return cancelReasonType;
	}

	public void setCancelReasonType(Integer cancelReasonType) {
		this.cancelReasonType = cancelReasonType;
	}

	public String getCancelOtherReason() {
		return cancelOtherReason;
	}

	public void setCancelOtherReason(String cancelOtherReason) {
		this.cancelOtherReason = cancelOtherReason;
	}

}
