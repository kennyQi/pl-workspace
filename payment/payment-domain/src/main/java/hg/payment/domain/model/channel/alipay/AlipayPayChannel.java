package hg.payment.domain.model.channel.alipay;

import hg.payment.domain.model.channel.PayChannel;

/**
 * 
 *@类功能说明：支付宝参数配置
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：lixx
 *@创建时间：2014年9月2日下午2:50:16
 *
 */
@SuppressWarnings("serial")
public class AlipayPayChannel extends PayChannel {
	
	/**
	 * 支付类型（默认值）：商品购买
	 */
	private String paymentType = "1";
	
	/**
	 * 支付宝异步回调地址
	 */
	private String notifyUrl;
	
	
	/**
	 * 支付宝同步回调地址
	 */
	private String returnUrl;
	
//	/**
//	 * 卖家支付宝账户（收款账号，必填）
//	 */
//	private String sellerEmail;
//	
//	
//	/**
//	 * 平台订单编号（必填）
//	 */
//	private String outTradeNo;
//	
//	/**
//	 * 订单名称（必填）
//	 */
//	private String subject;
//	
//	/**
//	 * 订单描述
//	 */
//	private String body;
//	
//	/**
//	 * 商品展示地址
//	 */
//	private String showUrl;
	
	/**
	 * 即时支付服务名称
	 */
	private String serviceName;
	
//	/**
//	 * 合作商户唯一ID
//	 */
//	private String partner;
//	
//	/**
//	 * 商户唯一密钥
//	 */
//	private String keys;
	
	/**
	 * 字符编码（默认：utf-8）
	 */
	private String inputCharset ;
	
	/**
	 * 签名方式
	 */
	private  String signType = "MD5";
	
	/**
	 * 日志文件夹路径
	 */
	private  String logPath ;
	
	/**
	 * 支付宝请求地址(发起)
	 */
	private String alipayUrl;
	
	/**
	 * 支付宝回调消息验证地址
	 */
	private String alipayVerifyUrl;
	
	//---------- 批量退款接口 ---------
	/**
	 * 批量退款接口名称
	 */
	private String refundServiceName;
	
	/**
	 * 批量退款服务器异步通知地址
	 */
	private String refundNotifyUrl;
	
	
	

//	public void setChannelParams(CreateAlipayPayOrderCommand command){
//		setSellerEmail(command.getPayOrderCreateDTO().getPayeeAccount());
//		setReturnUrl("http://my.csair.com:7001/payment-application/alipay/return");
//		setNotifyUrl("http://my.csair.com:7001/payment-application/alipay/notify");
//		setReturnUrl(AlipayProperties.getInstance().get("returnUrl"));
//		setNotifyUrl(AlipayProperties.getInstance().get("notifyUrl"));
//	}
	
	
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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


	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getAlipayUrl() {
		return alipayUrl;
	}

	public void setAlipayUrl(String alipayUrl) {
		this.alipayUrl = alipayUrl;
	}

	public String getAlipayVerifyUrl() {
		return alipayVerifyUrl;
	}

	public void setAlipayVerifyUrl(String alipayVerifyUrl) {
		this.alipayVerifyUrl = alipayVerifyUrl;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRefundServiceName() {
		return refundServiceName;
	}

	public void setRefundServiceName(String refundServiceName) {
		this.refundServiceName = refundServiceName;
	}

	public String getRefundNotifyUrl() {
		return refundNotifyUrl;
	}

	public void setRefundNotifyUrl(String refundNotifyUrl) {
		this.refundNotifyUrl = refundNotifyUrl;
	}




	
	
	
	
}
