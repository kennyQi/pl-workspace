package hg.fx.command.reserveInfo;

import hg.framework.common.base.BaseSPICommand;

@SuppressWarnings("serial")
public class ModifyReserveInfoCommand extends BaseSPICommand{
	
	private String id;

	/**
	 * 总额
	 */
	private Integer amount;
	
	/**
	 * 冻结余额
	 */
	private Integer freezeBalance;
	
	/**
	 * 可用余额
	 */
	private Integer usableBalance;
	
	/**
	 * 可欠费里程
	 */
	private Integer arrearsAmount;
	
	/**
	 * 预警里程
	 */
	private Integer warnValue;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(Integer freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	public Integer getUsableBalance() {
		return usableBalance;
	}

	public void setUsableBalance(Integer usableBalance) {
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
