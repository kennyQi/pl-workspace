package zzpl.h5.util.alipay.entity;
import hg.common.util.SysProperties;
public class AlipayWapTradeCreateDirect {
	
	private java.lang.String service = "alipay.wap.trade.create.direct";
	private java.lang.String format; 
	private java.lang.String v; 
	private java.lang.String partner = SysProperties.getInstance().get("alipay_partner_id"); 
	private java.lang.String reqId; 
	private java.lang.String secId = SysProperties.getInstance().get("alipay_sign_type"); 
	private java.lang.String sign; 
	private java.lang.String subject; 
	private java.lang.String outTradeNo; 
	private java.lang.String totalFee; 
	private java.lang.String sellerAccountName; 
	private java.lang.String callBackUrl; 
	private java.lang.String notifyUrl; 
	private java.lang.String merchantUrl; 
	private java.lang.String outUser; 
	private java.lang.String payExpire; 
	private java.lang.String resSign; 
	private java.lang.String requestToken; 
	private java.util.Date reqDate; 
	private java.lang.String resErrCode; 
	private java.lang.String resErrSubCode; 
	private java.lang.String resErrMsg; 
	private java.lang.String resErrDetail; 
	private java.util.Date resDate; 
	private java.lang.Long times;
	
	public java.lang.String getService(){
		return service;
	}
	public void setService(java.lang.String service){
		this.service = service;
	}
	public java.lang.String getFormat(){
		return format;
	}
	public void setFormat(java.lang.String format){
		this.format = format;
	}
	public java.lang.String getV(){
		return v;
	}
	public void setV(java.lang.String v){
		this.v = v;
	}
	public java.lang.String getPartner(){
		return partner;
	}
	public void setPartner(java.lang.String partner){
		this.partner = partner;
	}
	public java.lang.String getReqId(){
		return reqId;
	}
	public void setReqId(java.lang.String reqId){
		this.reqId = reqId;
	}
	public java.lang.String getSecId(){
		return secId;
	}
	public void setSecId(java.lang.String secId){
		this.secId = secId;
	}
	public java.lang.String getSign(){
		return sign;
	}
	public void setSign(java.lang.String sign){
		this.sign = sign;
	}
	public java.lang.String getSubject(){
		return subject;
	}
	public void setSubject(java.lang.String subject){
		this.subject = subject;
	}
	public java.lang.String getOutTradeNo(){
		return outTradeNo;
	}
	public void setOutTradeNo(java.lang.String outTradeNo){
		this.outTradeNo = outTradeNo;
	}
	public java.lang.String getTotalFee(){
		return totalFee;
	}
	public void setTotalFee(java.lang.String totalFee){
		this.totalFee = totalFee;
	}
	public java.lang.String getSellerAccountName(){
		return sellerAccountName;
	}
	public void setSellerAccountName(java.lang.String sellerAccountName){
		this.sellerAccountName = sellerAccountName;
	}
	public java.lang.String getCallBackUrl(){
		return callBackUrl;
	}
	public void setCallBackUrl(java.lang.String callBackUrl){
		this.callBackUrl = callBackUrl;
	}
	public java.lang.String getNotifyUrl(){
		return notifyUrl;
	}
	public void setNotifyUrl(java.lang.String notifyUrl){
		this.notifyUrl = notifyUrl;
	}
	public java.lang.String getMerchantUrl(){
		return merchantUrl;
	}
	public void setMerchantUrl(java.lang.String merchantUrl){
		this.merchantUrl = merchantUrl;
	}
	public java.lang.String getOutUser(){
		return outUser;
	}
	public void setOutUser(java.lang.String outUser){
		this.outUser = outUser;
	}
	public java.lang.String getPayExpire(){
		return payExpire;
	}
	public void setPayExpire(java.lang.String payExpire){
		this.payExpire = payExpire;
	}
	public java.lang.String getResSign(){
		return resSign;
	}
	public void setResSign(java.lang.String resSign){
		this.resSign = resSign;
	}
	public java.lang.String getRequestToken(){
		return requestToken;
	}
	public void setRequestToken(java.lang.String requestToken){
		this.requestToken = requestToken;
	}
	public java.util.Date getReqDate(){
		return reqDate;
	}
	public void setReqDate(java.util.Date reqDate){
		this.reqDate = reqDate;
	}
	public java.lang.String getResErrCode(){
		return resErrCode;
	}
	public void setResErrCode(java.lang.String resErrCode){
		this.resErrCode = resErrCode;
	}
	public java.lang.String getResErrSubCode(){
		return resErrSubCode;
	}
	public void setResErrSubCode(java.lang.String resErrSubCode){
		this.resErrSubCode = resErrSubCode;
	}
	public java.lang.String getResErrMsg(){
		return resErrMsg;
	}
	public void setResErrMsg(java.lang.String resErrMsg){
		this.resErrMsg = resErrMsg;
	}
	public java.lang.String getResErrDetail(){
		return resErrDetail;
	}
	public void setResErrDetail(java.lang.String resErrDetail){
		this.resErrDetail = resErrDetail;
	}
	public java.util.Date getResDate(){
		return resDate;
	}
	public void setResDate(java.util.Date resDate){
		this.resDate = resDate;
	}
	public java.lang.Long getTimes(){
		return times;
	}
	public void setTimes(java.lang.Long times){
		this.times = times;
	}
}
