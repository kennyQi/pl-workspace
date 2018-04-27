package lxs.pojo.command.mp;

public class AlipayCommand {

	/**
	 * 订单号
	 */
	private String dealerOrderNo;

	/**
	 * 付款金额
	 */
	private Double price;

	private String serialNumber;

	private String paymentAccount;

	private String requestType;
	
	public static final String LOCAL="local";
	public static final String ALIPAY="alipay";
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
