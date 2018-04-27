package plfx.jp.pojo.dto.pay.balances;

import plfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：支付记录DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月16日下午3:17:50
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class PayBalancesDTO extends BaseJpDTO{
	/**
	 * 余额
	 */
	private Double balances;
	
	/**
	 * 预警余额
	 */
	private Double warnBalances;
	
	/**
	 * 预警类型
	 * 0:票量分销国内机票
	 */
	private Integer type;

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
}
