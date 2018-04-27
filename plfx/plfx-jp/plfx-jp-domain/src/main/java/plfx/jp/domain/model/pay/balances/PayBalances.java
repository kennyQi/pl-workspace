package plfx.jp.domain.model.pay.balances;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import plfx.jp.command.pay.balances.CreatePayBalancesCommand;
import plfx.jp.domain.model.J;

/**
 * 
 * @类功能说明：支付宝账户余额
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月25日上午10:25:07
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "PAY_BALANCES")
public class PayBalances extends BaseModel{
	public PayBalances() {
		super();
	}
	
	/**
	 * 预警类型
	 * 0:票量分销国内机票
	 */
	@Column(name = "TYPE", length = 2)
	private Integer type;
	
	/**
	 * 余额
	 */
	@Column(name = "BALANCES", columnDefinition = J.DOUBLE_COLUM)
	private Double balances;
	
	/**
	 * 预警余额
	 */
	@Column(name = "WARNBALANCES", columnDefinition = J.DOUBLE_COLUM)
	private Double warnBalances;

	/**
	 * 新增支付宝余额记录
	 * @param command
	 */
	public PayBalances(CreatePayBalancesCommand command) {
		this.setId(UUIDGenerator.getUUID());
		this.setType(command.getType());
		this.setBalances(command.getBalances());
		this.setWarnBalances(command.getWarnBalances());
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
