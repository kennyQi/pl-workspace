package hsl.pojo.command.account;
import hg.common.component.BaseCommand;
import hsl.pojo.dto.user.UserDTO;

import java.util.Date;

@SuppressWarnings("serial")
public class AccountCommand extends BaseCommand{
	/**
	 * 账户余额
	 */
	private Double balance;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/** 
	 * 上次消费日期
	 */
	private Date lastConsumeDate;
	/**
	 * 金额来源
	 */
	private String moneyOrigin;
	/**
	 * 持用用户快照
	 */
	private UserDTO user;
	/**
	 * 本次消费金额
	 */
	private Double currentMoney;
	/**
	 * 是否消费余额
	 */
	private Boolean consumption;
	/**
	 * 消费订单快照
	 */
	private AccountConsumeSnapshotCommand accountConsumeSnapshotCommand;
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastConsumeDate() {
		return lastConsumeDate;
	}
	public void setLastConsumeDate(Date lastConsumeDate) {
		this.lastConsumeDate = lastConsumeDate;
	}
	public String getMoneyOrigin() {
		return moneyOrigin;
	}
	public void setMoneyOrigin(String moneyOrigin) {
		this.moneyOrigin = moneyOrigin;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public Double getCurrentMoney() {
		return currentMoney;
	}
	public void setCurrentMoney(Double currentMoney) {
		this.currentMoney = currentMoney;
	}
	public AccountConsumeSnapshotCommand getAccountConsumeSnapshotCommand() {
		return accountConsumeSnapshotCommand;
	}
	public void setAccountConsumeSnapshotCommand(
			AccountConsumeSnapshotCommand accountConsumeSnapshotCommand) {
		this.accountConsumeSnapshotCommand = accountConsumeSnapshotCommand;
	}
	public Boolean getConsumption() {
		return consumption;
	}
	public void setConsumption(Boolean consumption) {
		this.consumption = consumption;
	}

}
