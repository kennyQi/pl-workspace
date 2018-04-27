package hg.dzpw.pojo.api.hjb;

import java.io.Serializable;

public class HJBTransferRequestDto implements Serializable {

	private static final long serialVersionUID = -1346042284780811753L;
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
	 * 企业用户或者商户在汇金宝平台唯一标识
	 */
	private String payCstNo;
	/**
	 * 企业用户或者商户在汇金宝平台唯一标识
	 */
	private String rcvCstNo;
	/**
	 * 交易金额(单位分)
	 */
	private String trxAmount;
	/**
	 * 企业用户在汇金宝平台的操作员编号,可空
	 */
	private String userId;
	/**
	 * 备注，可空
	 */
	private String remark;
	/**
	 * 接入商城订单号
	 */
	private String originalOrderNo;
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
	public String getPayCstNo() {
		return payCstNo;
	}
	public void setPayCstNo(String payCstNo) {
		this.payCstNo = payCstNo;
	}
	public String getRcvCstNo() {
		return rcvCstNo;
	}
	public void setRcvCstNo(String rcvCstNo) {
		this.rcvCstNo = rcvCstNo;
	}
	public String getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(String trxAmount) {
		this.trxAmount = trxAmount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOriginalOrderNo() {
		return originalOrderNo;
	}
	public void setOriginalOrderNo(String originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}
	
	
}
