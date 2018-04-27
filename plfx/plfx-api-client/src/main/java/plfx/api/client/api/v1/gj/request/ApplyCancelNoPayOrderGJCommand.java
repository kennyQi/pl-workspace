package plfx.api.client.api.v1.gj.request;

import plfx.api.client.common.BaseClientCommand;
import plfx.api.client.common.api.PlfxApiAction;

/**
 * @类功能说明：申请取消未付款的订单
 * @类修改者：
 * @修改日期：2015-7-7下午3:16:38
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午3:16:38
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GJ_ApplyCancelNoPayOrder)
public class ApplyCancelNoPayOrderGJCommand extends BaseClientCommand {

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
