package hg.payment.pojo.command.dto;

import hg.payment.pojo.dto.BaseDTO;

/**
 * 
 * @类功能说明：支付请求通用参数
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年9月3日下午2:50:37
 * 
 */
@SuppressWarnings("serial")
public class PayOrderRequestDTO extends BaseDTO {

	/**
	 *  客户端ID
	 */
	private String paymentClientId;

	/**
	 * 客户端用户id
	 */
	private String payerClientUserId;
	
	/**
	 * 客户端KEY
	 */
	private String paymentClienKeys;

	/**
	 * 商户订单编号（必填）
	 */
	private String clientTradeNo;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 用户身份证号
	 */
	private String idCardNo;

	/**
	 * 用户手机号
	 */
	private String mobile;

	/**
	 * 选择的支付渠道ID 1-支付宝
	 */
	private Integer payChannelType;

	/**
	 * 收款方的帐号
	 */
	private String payeeAccount;

	/**
	 * 收款方名称
	 */
	private String payeeName; 
	

	/**
	 * 金额
	 */
	private Double amount;

	/**
	 * 支付者备注信息
	 */
	private String payerRemark; //弃用
	
	
	
	

	public String getPaymentClientId() {
		return paymentClientId;
	}

	public void setPaymentClientId(String paymentClientId) {
		this.paymentClientId = paymentClientId;
	}

	public String getPayerClientUserId() {
		return payerClientUserId;
	}

	public void setPayerClientUserId(String payerClientUserId) {
		this.payerClientUserId = payerClientUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getPayChannelType() {
		return payChannelType;
	}

	public void setPayChannelType(Integer payChannelType) {
		this.payChannelType = payChannelType;
	}

	public String getPayeeAccount() {
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getClientTradeNo() {
		return clientTradeNo;
	}

	public void setClientTradeNo(String clientTradeNo) {
		this.clientTradeNo = clientTradeNo;
	}

	public String getPaymentClienKeys() {
		return paymentClienKeys;
	}

	public void setPaymentClienKeys(String paymentClienKeys) {
		this.paymentClienKeys = paymentClienKeys;
	}

	public String getPayerRemark() {
		return payerRemark;
	}

	public void setPayerRemark(String payerRemark) {
		this.payerRemark = payerRemark;
	}

	
	

}
