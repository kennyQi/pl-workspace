package hsl.h5.alipay.entity;

import hsl.app.component.config.SysProperties;

import java.util.Date;

public class AlipayWapTradeCreateDirect {

	private String service = "alipay.wap.trade.create.direct";
	private String format;
	private String v;
	private String partner = SysProperties.getInstance().get("alipay_partner_id");
	private String reqId;
	private String secId = SysProperties.getInstance().get("alipay_sign_type");
	private String sign;
	private String subject;
	private String outTradeNo;
	private String totalFee;
	private String sellerAccountName;
	private String callBackUrl;
	private String notifyUrl;
	private String merchantUrl;
	private String outUser;
	private String payExpire;
	private String resSign;
	private String requestToken;
	private Date reqDate;
	private String resErrCode;
	private String resErrSubCode;
	private String resErrMsg;
	private String resErrDetail;
	private Date resDate;
	private Long times;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getSellerAccountName() {
		return sellerAccountName;
	}

	public void setSellerAccountName(String sellerAccountName) {
		this.sellerAccountName = sellerAccountName;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public String getOutUser() {
		return outUser;
	}

	public void setOutUser(String outUser) {
		this.outUser = outUser;
	}

	public String getPayExpire() {
		return payExpire;
	}

	public void setPayExpire(String payExpire) {
		this.payExpire = payExpire;
	}

	public String getResSign() {
		return resSign;
	}

	public void setResSign(String resSign) {
		this.resSign = resSign;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public String getResErrCode() {
		return resErrCode;
	}

	public void setResErrCode(String resErrCode) {
		this.resErrCode = resErrCode;
	}

	public String getResErrSubCode() {
		return resErrSubCode;
	}

	public void setResErrSubCode(String resErrSubCode) {
		this.resErrSubCode = resErrSubCode;
	}

	public String getResErrMsg() {
		return resErrMsg;
	}

	public void setResErrMsg(String resErrMsg) {
		this.resErrMsg = resErrMsg;
	}

	public String getResErrDetail() {
		return resErrDetail;
	}

	public void setResErrDetail(String resErrDetail) {
		this.resErrDetail = resErrDetail;
	}

	public Date getResDate() {
		return resDate;
	}

	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}
}
