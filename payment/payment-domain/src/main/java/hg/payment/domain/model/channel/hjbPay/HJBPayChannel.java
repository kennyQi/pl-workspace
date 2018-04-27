package hg.payment.domain.model.channel.hjbPay;

import hg.payment.domain.model.channel.PayChannel;

/**
 * 
 * 
 *@类功能说明：汇金宝支付渠道
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月17日上午9:13:14
 *
 */
public class HJBPayChannel extends PayChannel{

	private static final long serialVersionUID = 1L;

	/**
	 * 接口版本号
	 */
	private String version;
	
	/**
	 * 参数签名算法
	 */
	private String signatureAlgorithm;
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 同步通知地址
	 */
	private String returnUrl;
	
	/**
	 * 异步通知地址
	 */
	private String notifyAddr;
	
	/**
	 * 汇金宝支付请求地址
	 */
	private String hjbUrl;
	
	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNotifyAddr() {
		return notifyAddr;
	}

	public void setNotifyAddr(String notifyAddr) {
		this.notifyAddr = notifyAddr;
	}


	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getHjbUrl() {
		return hjbUrl;
	}

	public void setHjbUrl(String hjbUrl) {
		this.hjbUrl = hjbUrl;
	}
	
	
	
	
	
	
	
}
