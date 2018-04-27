package lxs.domain.model.line;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：线路支付信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午1:49:12
 */
@Embeddable
public class LinePayInfo{
	/**
	 * 订金支付比例
	 */
	@Column(name = "DOWN_PAYMENT")
	private Double downPayment;

	/**
	 * 需付全款提前天数
	 */
	@Column(name = "PAY_TOTAL_DAYS_BEFORE_START")
	private Integer payTotalDaysBeforeStart;
	
	/**
	 * 最晚付款时间出发日期前
	 */
	@Column(name = "LAST_PAY_TOTAL_DAYS_BEFORE_START")
	private Integer lastPayTotalDaysBeforeStart;

	public Double getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(Double downPayment) {
		this.downPayment = downPayment;
	}

	public Integer getPayTotalDaysBeforeStart() {
		return payTotalDaysBeforeStart;
	}

	public void setPayTotalDaysBeforeStart(Integer payTotalDaysBeforeStart) {
		this.payTotalDaysBeforeStart = payTotalDaysBeforeStart;
	}

	public Integer getLastPayTotalDaysBeforeStart() {
		return lastPayTotalDaysBeforeStart;
	}

	public void setLastPayTotalDaysBeforeStart(Integer lastPayTotalDaysBeforeStart) {
		this.lastPayTotalDaysBeforeStart = lastPayTotalDaysBeforeStart;
	}
}