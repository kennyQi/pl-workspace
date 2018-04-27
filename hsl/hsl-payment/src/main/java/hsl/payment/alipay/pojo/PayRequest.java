package hsl.payment.alipay.pojo;

/**
 * 支付请求
 *
 * @author zhurz
 * @since 1.7
 */
public class PayRequest {

	/**
	 * notify_Url
	 * 服务器异步通知路径
	 * 需http://格式的完整路径，不能加?id=123这类自定义参数
	 * 必填，不能修改
	 */
	private String notifyUrl;

	/**
	 * return_url
	 * 页面跳转同步通知页面路径
	 * 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
	 */
	private String returnUrl;

	/**
	 * out_trade_no
	 * 商户订单号
	 * 商户网站订单系统中唯一订单号，必填
	 */
	private String outTradeNo;

	/**
	 * subject
	 * 订单名称
	 * 必填
	 */
	private String subject;

	/**
	 * total_fee
	 * 付款金额
	 * 必填
	 */
	private Double totalFee;

	/**
	 * body
	 * 订单描述
	 */
	private String body;

	/**
	 * show_url
	 * 商品展示地址
	 * 需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
	 */
	private String showUrl;

	public PayRequest() {
	}

	public PayRequest(String notifyUrl, String returnUrl, String outTradeNo, String subject, Double totalFee, String body, String showUrl) {
		this.notifyUrl = notifyUrl;
		this.returnUrl = returnUrl;
		this.outTradeNo = outTradeNo;
		this.subject = subject;
		this.totalFee = totalFee;
		this.body = body;
		this.showUrl = showUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}
}
