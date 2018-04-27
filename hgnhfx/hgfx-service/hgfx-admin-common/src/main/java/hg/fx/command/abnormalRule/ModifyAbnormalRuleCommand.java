package hg.fx.command.abnormalRule;

import hg.framework.common.base.BaseSPICommand;

/**
 * 修改异常规则COMMAND
 * */
@SuppressWarnings("serial")
public class ModifyAbnormalRuleCommand  extends BaseSPICommand{
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 单笔订单里程上限
	 */
	private Long orderUnitMax;
	
	/**
	 * 同账号每日订单上限次数
	 */
	private Integer dayMax;
	
	/**
	 * 同账号每月订单上限次数
	 * */
	private Integer mouthMax;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getOrderUnitMax() {
		return orderUnitMax;
	}

	public void setOrderUnitMax(Long orderUnitMax) {
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
