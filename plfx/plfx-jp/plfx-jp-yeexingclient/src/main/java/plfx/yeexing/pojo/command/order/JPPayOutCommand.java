package plfx.yeexing.pojo.command.order;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class JPPayOutCommand extends BaseCommand{
	
	/***
	 * 合作伙伴用户名
	 * 合作伙伴在易行天下的用户名
	 */
	private String userName;
	/**
	 * 订单号
	 * 易行天下订单号（易行天下系统中唯一）一次只能传一个易行订单
	 */
	private String orderid;
	/***
	 * 订单总支付价格
	 *
	 */
	private String totalPrice;
	/***
	 * 支付方式
	 * 1—支付宝 2—汇付 6—IPS   7—德付通
	 * 
	 */
	private Integer payPlatform;
	/***
	 * 扣款成功通知地址
	 * 扣款成功后异步通知url（合作伙伴自行开发）
	 * 
	 */
	private String pay_notify_url;
	/***
	 * 出票成功/拒绝出票通知地址
	 * 出票成功后异步通知url（合作伙伴自0行开发）
	 * 
	 */
	private String out_notify_url;
	/****
	 * 签名
	 * 所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	/***
	 * 密钥
	 */
	private String key;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}
	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}
	public String getPay_notify_url() {
		return pay_notify_url;
	}
	public void setPay_notify_url(String pay_notify_url) {
		this.pay_notify_url = pay_notify_url;
	}
	public String getOut_notify_url() {
		return out_notify_url;
	}
	public void setOut_notify_url(String out_notify_url) {
		this.out_notify_url = out_notify_url;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
}
