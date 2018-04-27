package hsl.pojo.command.account;

import hg.common.component.BaseCommand;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;

@SuppressWarnings("serial")
public class RefundCommand extends BaseCommand{
	/**
	 * accountID
	 */
	private String accountId;
	/**
	 * 消费金额（消费订单记录 实际消费金额）
	 */
	private Double payPrice;
	/**
	 * 退款金额
	 */
	private Double refundMoney;
	/**
	 * 订单号
	 */
	private String orderID;
	/**
	 * 退费实际退费订单的id
	 */
	private String refundOrderId;
	/**
	 * 消费订单快照
	 */
	private String accountConsumeSnapshotId;
	private  AccountConsumeSnapshotDTO  consumeOrderSnapshot;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Double getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(Double refundMoney) {
		this.refundMoney = refundMoney;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public AccountConsumeSnapshotDTO getConsumeOrderSnapshot() {
		return consumeOrderSnapshot;
	}
	public void setConsumeOrderSnapshot(
			AccountConsumeSnapshotDTO consumeOrderSnapshot) {
		this.consumeOrderSnapshot = consumeOrderSnapshot;
	}
	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getAccountConsumeSnapshotId() {
		return accountConsumeSnapshotId;
	}

	public void setAccountConsumeSnapshotId(String accountConsumeSnapshotId) {
		this.accountConsumeSnapshotId = accountConsumeSnapshotId;
	}

	public Double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
}
