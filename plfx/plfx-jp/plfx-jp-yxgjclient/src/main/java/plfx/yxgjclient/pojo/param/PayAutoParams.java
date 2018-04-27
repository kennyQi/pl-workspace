package plfx.yxgjclient.pojo.param;
/**
 * 自动扣款接口参数
 * @author guotx
 * 2015-07-07
 */
public class PayAutoParams extends BaseParam{
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 外部订单号
	 */
	private String extOrderId;
	/**
	 * 订单总支付价格
	 */
	private String totalPrice;
	/**
	 * 支付平台
	 */
	private String payPlatform;
	/**
	 * 支付通知地址
	 */
	private String payNotifyUrl;
	/**
	 * 出票成功通知地址
	 */
	private String outNotifyUrl;
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
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getPayPlatform() {
		return payPlatform;
	}
	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}
	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}
	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}
	public String getOutNotifyUrl() {
		return outNotifyUrl;
	}
	public void setOutNotifyUrl(String outNotifyUrl) {
		this.outNotifyUrl = outNotifyUrl;
	}
	
}
