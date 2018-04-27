package plfx.jp.command.pay.balances;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：修改支付宝余额COMMAND
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月30日上午9:53:52
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class UpdatePayBalancesCommand extends BaseCommand{
	private String id;
	
	/**
	 * 预警类型
	 * 0:票量分销国内机票
	 */
	private Integer type;
	
	/**
	 * 余额
	 */
	private Double balances;
	
	/**
	 * 预警余额
	 */
	private Double warnBalances;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getBalances() {
		return balances;
	}

	public void setBalances(Double balances) {
		this.balances = balances;
	}

	public Double getWarnBalances() {
		return warnBalances;
	}

	public void setWarnBalances(Double warnBalances) {
		this.warnBalances = warnBalances;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
