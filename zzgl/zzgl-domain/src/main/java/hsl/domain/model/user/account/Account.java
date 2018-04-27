package hsl.domain.model.user.account;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.user.User;
import hsl.pojo.command.account.AccountCommand;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * @类功能说明：账户
 * @类修改者：
 * @修改日期：2015年7月18日上午9:38:25
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年7月18日上午9:38:25
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_ACCOUNT+"ACCOUNT")
public class Account extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 账户余额
	 */
	@Column(name="BALANCE",columnDefinition=M.DOUBLE_COLUM)
    private Double balance;
//	/**
//	 * 当前占用余额
//	 */
//	@Column(name="OCCUPY_BALANCE",columnDefinition = M.DOUBLE_COLUM)
//	private Double occupyBalance;
    /**
     * 创建日期
     */
	@Column(name="CREATEDATE",columnDefinition=M.DATE_COLUM)
    private Date createDate;
	/** 
	 * 上次消费日期
	 */
	@Column(name="LASTCONSUMEDATE",columnDefinition=M.DATE_COLUM)
	private Date lastConsumeDate;
    /**
     * 金额来源
     */
	@Column(name="MONEYORIGIN",length = 64)
    private String moneyOrigin;
    /**
     * 持用用户快照
     */
 	@OneToOne(fetch=FetchType.EAGER)
 	@JoinColumn(name = "USER_ID")
    private User user;
 	/**
 	 * 消费订单快照
 	 */
 	@OneToMany(fetch = FetchType.LAZY, mappedBy="account",cascade={CascadeType.REMOVE})
 	private List<AccountConsumeSnapshot> consumeOrderSnapshots;
 	
 	/**
 	 * @方法功能说明：保存账户信息
 	 * @创建者名字：zhaows
 	 * @创建时间：2015-9-2下午2:08:15
 	 * @参数：@return
 	 * @return:Double
 	 * @throws
 	 */
 	public void create(AccountCommand command){
 		this.setBalance(command.getBalance());
 		this.setCreateDate(new Date());
 		this.setId(UUIDGenerator.getUUID());
//		this.setOccupyBalance(0.00);
 		if(command.getLastConsumeDate()==null){
 			this.setLastConsumeDate(new Date());
 		}else{
 			this.setLastConsumeDate(command.getLastConsumeDate());	
 		}
 		this.setMoneyOrigin(command.getMoneyOrigin());
 	}
	public void occupy(AccountCommand command){
//		this.setOccupyBalance(command.getCurrentMoney());
	}
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
	public String getMoneyOrigin() {
		return moneyOrigin;
	}
	public void setMoneyOrigin(String moneyOrigin) {
		this.moneyOrigin = moneyOrigin;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<AccountConsumeSnapshot> getConsumeOrderSnapshots() {
		return consumeOrderSnapshots;
	}
	public void setConsumeOrderSnapshots(
			List<AccountConsumeSnapshot> consumeOrderSnapshots) {
		this.consumeOrderSnapshots = consumeOrderSnapshots;
	}
	public Date getLastConsumeDate() {
		return lastConsumeDate;
	}
	public void setLastConsumeDate(Date lastConsumeDate) {
		this.lastConsumeDate = lastConsumeDate;
	}

//	public Double getOccupyBalance() {
//		return occupyBalance;
//	}
//
//	public void setOccupyBalance(Double occupyBalance) {
//		this.occupyBalance = occupyBalance;
//	}
}