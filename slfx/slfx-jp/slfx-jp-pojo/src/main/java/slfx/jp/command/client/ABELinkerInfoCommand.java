package slfx.jp.command.client;

/**
 * 
 * @类功能说明：ABE订单联系信息command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:04:41
 * @版本：V1.0
 *
 */
public class ABELinkerInfoCommand {
	
	/**
	 * 是否电子机票  Y或N 必填 设为Y
	 */
	private String isETiket;
	
	/**
	 * 支付方式   不设置
	 */
	private String payType;
	
	/**
	 * 地址  不设置
	 */
	private String address;
	
	/**
	 * 联系人     必填
	 */
	private String linkerName;
	
	/**
	 * 邮编    不设置
	 */
	private String zip;
	
	/**
	 * 电话    不设置
	 */
	private String telphone="";
	
	/**
	 * 手机
	 */
	private String mobilePhone;

	/**
	 * 送票时间   不设置
	 */
	private String sendTime;
	
	/**
	 * 联系人邮箱  不设置
	 */
	private String  linkerEmail;
	
	/**
	 * 是否需要发票 Y/N   不设置
	 */
	private String needInvoices;
	
	/**
	 * 发票发送方式 A邮寄 B配送    不设置
	 */
	private String invoicesSendType;
	
	/**
	 * 备注  不设置
	 */
	private String remark;
	
	/**
	 * 订单配送方式（By：不配送 ZQ:自取 SP:送票 YJ:邮寄 BZ:不制单）
	 * 不设置
	 */
	private String sendTktsTypeCode;
	
	/**
	 * 是否需要行程单   Y/N  必填
	 */
	private String isPrintSerial;

	public String getIsETiket() {
		return isETiket;
	}

	public void setIsETiket(String isETiket) {
		this.isETiket = isETiket;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLinkerName() {
		return linkerName;
	}

	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getLinkerEmail() {
		return linkerEmail;
	}

	public void setLinkerEmail(String linkerEmail) {
		this.linkerEmail = linkerEmail;
	}

	public String getNeedInvoices() {
		return needInvoices;
	}

	public void setNeedInvoices(String needInvoices) {
		this.needInvoices = needInvoices;
	}

	public String getInvoicesSendType() {
		return invoicesSendType;
	}

	public void setInvoicesSendType(String invoicesSendType) {
		this.invoicesSendType = invoicesSendType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSendTktsTypeCode() {
		return sendTktsTypeCode;
	}

	public void setSendTktsTypeCode(String sendTktsTypeCode) {
		this.sendTktsTypeCode = sendTktsTypeCode;
	}

	public String getIsPrintSerial() {
		return isPrintSerial;
	}

	public void setIsPrintSerial(String isPrintSerial) {
		this.isPrintSerial = isPrintSerial;
	}
	
}
