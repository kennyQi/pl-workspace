package plfx.yeexing.pojo.command.order;


@SuppressWarnings("serial")
public class JPPayNotifyCommand extends JPBaseCommand{
	/**
	 * 支付流水号
	 */
	private String payId;
	
	/**
	 * 总支付金额
	 */
	private Double totalPrice;
	
	/**
	 * 支付平台
	 * 1—支付宝 2—汇付  7—德付通
	 */
	private Integer payplatform;
	
	/**
	 * 支付方式
	 * 1：自动扣款，2：收银台支付
	 */
	private Integer payType;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getPayplatform() {
		return payplatform;
	}

	public void setPayplatform(Integer payplatform) {
		this.payplatform = payplatform;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
}
