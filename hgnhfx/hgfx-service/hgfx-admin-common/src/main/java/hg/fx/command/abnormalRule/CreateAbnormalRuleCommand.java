package hg.fx.command.abnormalRule;

import hg.framework.common.base.BaseSPICommand;

/**
 * 创建异常规则COMMAND
 * */
@SuppressWarnings("serial")
public class CreateAbnormalRuleCommand  extends BaseSPICommand{
	
	/**
	 * 单笔订单里程上限
	 */
	private Integer orderUnitMax;
	
	/**
	 * 同账号每日订单上限次数
	 */
	private Integer dayMax;
	
	/**
	 * 同账号每月订单上限次数
	 * */
	private Integer mouthMax;

	public Integer getOrderUnitMax() {
		return orderUnitMax;
	}

	public void setOrderUnitMax(Integer orderUnitMax) {
		this.orderUnitMax = orderUnitMax;
	}

	public Integer getDayMax() {
		return dayMax;
	}

	public void setDayMax(Integer dayMax) {
		this.dayMax = dayMax;
	}

	public Integer getMouthMax() {
		return mouthMax;
	}

	public void setMouthMax(Integer mouthMax) {
		this.mouthMax = mouthMax;
	}
	
}
