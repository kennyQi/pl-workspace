package hg.dzpw.pojo.api.hjb;

import java.io.Serializable;

public class HJBSignQueryRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1541944266144792901L;
	/**
	 * 接口版本号
	 */
	private String version;
	/**
	 * 票务平台唯一标识
	 */
	private String merchantId;
	/**
	 * 调用端IP，可空
	 */
	private String callerIp;
	/**
	 * 商户订单编号
	 */
	private String orderNo;
	/**
	 * 原商户订单编号
	 */
	//private String originalOrderNo;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getCallerIp() {
		return callerIp;
	}
	public void setCallerIp(String callerIp) {
		this.callerIp = callerIp;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
