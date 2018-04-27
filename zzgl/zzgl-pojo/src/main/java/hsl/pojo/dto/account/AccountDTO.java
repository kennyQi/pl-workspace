package hsl.pojo.dto.account;
import hsl.pojo.dto.BaseDTO;
import hsl.pojo.dto.user.UserDTO;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class AccountDTO extends BaseDTO{
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
 	 * 消费订单快照
 	 */
 	private List<AccountConsumeSnapshotDTO> consumeOrderSnapshots;
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
	public List<AccountConsumeSnapshotDTO> getConsumeOrderSnapshots() {
		return consumeOrderSnapshots;
	}
	public void setConsumeOrderSnapshots(
			List<AccountConsumeSnapshotDTO> consumeOrderSnapshots) {
		this.consumeOrderSnapshots = consumeOrderSnapshots;
	}
 	
}
