
package hg.payment.app.pojo.qo.payorder;


import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class PayOrderLocalQO extends BaseQo{
	
	/**
	 * 客户端ID
	 */
	private String paymentClientID;
	
	/**
	 * 客户端信息是否延迟加载
	 */
	private Boolean clientLazy = false;
	
	/**
	 * 支付渠道类型
	 */
	private Integer payChannelType;
	
	/**
	 * 商城订单编号
	 */
	private String clientTradeNo;

	
	/**
	 * 支付单创建时间范围，起始时间
	 */
	private String beginDate;
	

	/**
	 * 支付单创建时间范围，截止时间
	 */
	private String endDate;
	
	/**
	 * 按创建时间倒序排列
	 */
	private Boolean createDateAsc = false;
	
	/**
	 * api密钥
	 */
	private String secretKey;
	
	/**
	 * 是否支付成功
	 */
	private Boolean paySuccess;
	
	private String id;


	public String getPaymentClientID() {
		return paymentClientID;
	}


	public void setPaymentClientID(String paymentClientID) {
		this.paymentClientID = paymentClientID;
	}


	public Integer getPayChannelType() {
		return payChannelType;
	}


	public void setPayChannelType(Integer payChannelType) {
		this.payChannelType = payChannelType;
	}


	public String getClientTradeNo() {
		return clientTradeNo;
	}


	public void setClientTradeNo(String clientTradeNo) {
		this.clientTradeNo = clientTradeNo;
	}

	public String getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}


	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}


	public Boolean getClientLazy() {
		return clientLazy;
	}


	public void setClientLazy(Boolean clientLazy) {
		this.clientLazy = clientLazy;
	}


	public String getSecretKey() {
		return secretKey;
	}


	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Boolean getPaySuccess() {
		return paySuccess;
	}


	public void setPaySuccess(Boolean paySuccess) {
		this.paySuccess = paySuccess;
	}
	
	

	

	
	
	

}
