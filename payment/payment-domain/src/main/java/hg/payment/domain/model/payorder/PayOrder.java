package hg.payment.domain.model.payorder;

import hg.common.component.BaseModel;
import hg.payment.domain.Pay;
import hg.payment.domain.model.channel.PayChannel;
import hg.payment.domain.model.client.PaymentClient;
import hg.payment.pojo.command.dto.PayOrderRequestDTO;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @类功能说明：支付单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月22日下午3:35:26
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Pay.TABLE_PREFIX + "PAY_ORDER")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PayOrder extends BaseModel {

	/**
	 * 来源客户端
	 */
	@ManyToOne
	@JoinColumn(name="PAYMENTCLIENT_ID")
	private PaymentClient paymentClient;

	/**
	 * 支付用户信息
	 */
	private PayerInfo payerInfo;
	
	/**
	 * 收款方信息
	 */
	private PayeeInfo payeeInfo;
	
	/**
	 * 支付渠道类型
	 */
	@Column(name="PAYCHANNEL_TYPE")
	private Integer payChannelType;
	
	/**
	 * 支付渠道
	 */
	@Transient
	private PayChannel payChannel;
	
	/**
	 * 第三方交易帐号
	 */
	@Column(name = "PAYEE_TRADE_NO")
	private String thirdPartyTradeNo;
	

	/**
	 * 商城订单编号
	 */
	@Column(name="CLIENT_TRADE_NO")
	private String clientTradeNo;

//	/**
//	 * 收款方的帐号
//	 */
//	@Column(name="PAYEE_ACCOUNT")
//	private String payeeAccount;
//
//	/**
//	 * 收款户名
//	 */
//	@Column(name="PAYEE_NAME")
//	private String payeeName;

	/**
	 * 金额
	 */
	@Column(name="AMOUNT")
	private Double amount;

	/**
	 * 支付单创建时间，以支付平台服务端时间为准
	 */
	@Column(name="CREATEDATE")
	private Date createDate;

	/**
	 * 平台备注信息
	 */
	@Column(name="ORDER_REMARK")
	private String orderRemark;
	
	/**
	 * 订单状态
	 */
	private PayOrderProcessor payOrderProcessor;
	

	/**
	 * 商城请求创建支付订单，设置支付订单基本参数
	 */
	public void createPayOrderCommand(PayOrderRequestDTO payOrderRequestDTO){
		
		setId(payOrderRequestDTO.getId());
		PayerInfo payer = new PayerInfo();
		payer.setPayerClientUserId(payOrderRequestDTO.getPayerClientUserId());
		payer.setName(payOrderRequestDTO.getName());
		payer.setIdCardNo(payOrderRequestDTO.getIdCardNo());
		payer.setMobile(payOrderRequestDTO.getMobile());
		payerInfo = payer;
		PayeeInfo payee = new PayeeInfo();
		payee.setPayeeAccount(payOrderRequestDTO.getPayeeAccount());
		payee.setPayeeName(payOrderRequestDTO.getPayeeName());
		payeeInfo = payee;
		payChannelType = payOrderRequestDTO.getPayChannelType();
		clientTradeNo = payOrderRequestDTO.getClientTradeNo();
//		payeeAccount = payOrderRequestDTO.getPayeeAccount();
//		payeeName = payOrderRequestDTO.getPayeeName();
		amount = payOrderRequestDTO.getAmount();
		createDate = new Date();
//		orderRemark = payOrderRequestDTO.getPayerRemark();
		
		payOrderProcessor = new PayOrderProcessor();
		
	}
	
	
	/**
	 * 组装各渠道请求地址
	 */
	abstract public String buildRequestParam();
	
	/**
	 * 组装商城通知参数
	 * @return
	 */
	abstract public String notifyClientParam();
	
	
	public PaymentClient getPaymentClient() {
		return paymentClient;
	}

	public void setPaymentClient(PaymentClient paymentClient) {
		this.paymentClient = paymentClient;
	}

	public PayerInfo getPayerInfo() {
		return payerInfo;
	}

	public void setPayerInfo(PayerInfo payerInfo) {
		this.payerInfo = payerInfo;
	}

	public PayeeInfo getPayeeInfo() {
		return payeeInfo;
	}

	public void setPayeeInfo(PayeeInfo payeeInfo) {
		this.payeeInfo = payeeInfo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public PayChannel getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(PayChannel payChannel) {
		this.payChannel = payChannel;
	}

	public String getClientTradeNo() {
		return clientTradeNo;
	}

	public void setClientTradeNo(String clientTradeNo) {
		this.clientTradeNo = clientTradeNo;
	}

	public Integer getPayChannelType() {
		return payChannelType;
	}

	public void setPayChannelType(Integer payChannelType) {
		this.payChannelType = payChannelType;
	}

	public PayOrderProcessor getPayOrderProcessor() {
		return payOrderProcessor;
	}

	public void setPayOrderProcessor(PayOrderProcessor payOrderProcessor) {
		this.payOrderProcessor = payOrderProcessor;
	}

	public String getThirdPartyTradeNo() {
		return thirdPartyTradeNo;
	}

	public void setThirdPartyTradeNo(String thirdPartyTradeNo) {
		this.thirdPartyTradeNo = thirdPartyTradeNo;
	}

	

	
	

	
}
