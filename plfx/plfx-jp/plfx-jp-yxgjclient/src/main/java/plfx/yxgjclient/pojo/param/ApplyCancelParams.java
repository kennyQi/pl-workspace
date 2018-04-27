package plfx.yxgjclient.pojo.param;
/**
 * 申请取消请求参数列表
 * @author guotx
 * 2015-07-08
 */
public class ApplyCancelParams extends BaseParam{
	/**
	 * 订单号，必填
	 */
	private String orderId;
	/**
	 * 合作伙伴生成订单号，非必填
	 */
	private String extOrderId;
	/**
	 * 取消通知地址，必填
	 */
	private String cancelNotifyUrl;
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
	public String getCancelNotifyUrl() {
		return cancelNotifyUrl;
	}
	public void setCancelNotifyUrl(String cancelNotifyUrl) {
		this.cancelNotifyUrl = cancelNotifyUrl;
	}
}
