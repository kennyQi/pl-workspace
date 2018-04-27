package hg.fx.command.reserveInfo;

import hg.framework.common.base.BaseSPICommand;

/**
 * 
 * @author Caihuan
 * @date   2016年6月2日
 */
@SuppressWarnings("serial")
public class CreateReserveInfoCommand extends BaseSPICommand{

	/**
	 * 总额
	 */
	private Long amount;
	
	/**
	 * 冻结余额
	 */
	private Long freezeBalance;
	
	/**
	 * 可用余额
	 */
	private Long usableBalance;
	
	/**
	 * 可欠费里程
	 */
	private Integer arrearsAmount;
	
	/**
	 * 预警里程
	 */
	private Integer warnValue;

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(Long freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	public Long getUsableBalance() {
		return usableBalance;
	}

	public void setUsableBalance(Long usableBalance) {
		this.usableBalance = usableBalance;
	}

	public Integer getArrearsAmount() {
		return arrearsAmount;
	}

	public void setArrearsAmount(Integer arrearsAmount) {
		this.arrearsAmount = arrearsAmount;
	}

	public Integer getWarnValue() {
		return warnValue;
	}

	public void setWarnValue(Integer warnValue) {
		this.warnValue = warnValue;
	}
	
	
}
