package plfx.yxgjclient.pojo.param;
/**
 * 申请取消未支付订单结果参数列表
 * @author guotx
 * 2015-07-07
 */
public class ApplyCancelNoPayResult {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 外部订单号
	 */
	private String extOrderId;
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
}
