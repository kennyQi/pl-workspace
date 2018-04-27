package hg.fx.domain;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @类功能说明：商户里程余额信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午7:38:40
 * @版本：V1.0
 *
 */
 @Entity
 @Table(name = O.FX_TABLE_PREFIX + "RESERVE_INFO")
public class ReserveInfo extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 总额里程，总额=冻结里程+可用里程
	 */
	@Column(name = "AMOUNT", columnDefinition = O.LONG_NUM_COLUM)
	private Long amount;
	
	/**
	 * 冻结里程
	 */
	@Column(name = "FREEZE_BALANCE", columnDefinition = O.LONG_NUM_COLUM)
	private Long freezeBalance;
	
	/**
	 * 可用里程
	 */
	@Column(name = "USABLE_BALANCE", columnDefinition = O.LONG_NUM_COLUM)
	private Long usableBalance;
	
	/**
	 * 可欠费里程
	 */
	@Column(name = "ARREARS_AMOUNT", columnDefinition = O.NUM_COLUM)
	private Integer arrearsAmount;
	
	/**
	 * 预警里程
	 */
	@Column(name = "WARN_VALUE", columnDefinition = O.NUM_COLUM)
	private Integer warnValue;

	/**
	 * 版本号，每次修改+1.spring jpa太强大了！只有此处标注，自动检查该自动，并每次更新累加
	 */
	@Column(name="VERSION", columnDefinition="NUMBER(7)  NOT NULL")
	@Version
	public int version =0;	
	
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
