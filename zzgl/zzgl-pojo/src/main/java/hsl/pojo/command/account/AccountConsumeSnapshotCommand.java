package hsl.pojo.command.account;


import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class AccountConsumeSnapshotCommand extends BaseCommand{
	private AccountCommand account;
	/**
	 * 订单类型 
	 */
	private Integer orderType;
	/**
	 * 订单ID
	 */
    private String orderId;
    /**
	 * 此次消费余额价格
	 */
    private Double payPrice;
    /**
   	 * 详细
   	 */
    private String detail;
    /**
   	 * 状态
   	 */
    private Integer status;
	public AccountCommand getAccount() {
		return account;
	}
	public void setAccount(AccountCommand account) {
		this.account = account;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    
}
