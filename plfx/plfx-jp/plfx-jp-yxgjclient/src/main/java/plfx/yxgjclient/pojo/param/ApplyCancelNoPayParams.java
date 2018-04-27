package plfx.yxgjclient.pojo.param;
/**
 * 申请取消未支付订单请求参数
 * @author guotx
 * 2015-07-06
 */
public class ApplyCancelNoPayParams extends BaseParam{
	/**
	 * 订单号，必填
	 */
	private String orderId;
	/**
	 * 外部订单号，非必填
	 */
	private String extOrderId;
	/**
	 * 取消订单原因类型，必填
	 */
	private String cancelReasonType;
	/**
	 * 取消订单其他原因，非必填
	 */
	private String cancelOtherReason;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getExtOrderId() {
		return extOrderId;
	}
	public void setExtOrderId(String extOrderId) {
		this.extOrderId = extOrderId;
	}
	public String getCancelReasonType() {
		return cancelReasonType;
	}
	public void setCancelReasonType(String cancelReasonType) {
		this.cancelReasonType = cancelReasonType;
	}
	public String getCancelOtherReason() {
		return cancelOtherReason;
	}
	public void setCancelOtherReason(String cancelOtherReason) {
		this.cancelOtherReason = cancelOtherReason;
	}
	
}
