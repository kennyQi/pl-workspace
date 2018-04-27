package plfx.yxgjclient.pojo.param;
/**
 * 申请取消订单返回结果参数列表
 * @author guotx
 * 2015-07-08
 */
public class ApplyCancelResult {
	/**
	 * 易行订单号
	 */
	private String orderId;
	/**
	 * 合作伙伴的外部订单号
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
