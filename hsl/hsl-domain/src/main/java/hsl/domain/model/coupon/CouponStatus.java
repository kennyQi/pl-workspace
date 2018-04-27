package hsl.domain.model.coupon;

import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 卡券状态
 *
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午8:48:15
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:48:15
 */
@Embeddable
public class CouponStatus {
	/**
	 * 未使用
	 */
	public final static int TYPE_NOUSED = 1;
	/**
	 * 已使用
	 */
	public final static int TYPE_ISUSED = 2;
	/**
	 * 过期
	 */
	public final static int TYPE_OVERDUE = 3;
	/**
	 * 废弃
	 */
	public final static int TYPE_CANCEL = 4;
	/**
	 * 卡券 状态
	 * 1.未使用
	 * 2.已使用
	 * 3.过期
	 * 4.废弃
	 */
	@Column(name = "STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer currentValue;
	/**
	 * 卡券已赠送次数
	 */
	@Column(name = "ALREADYSENDTIMES", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer alreadySendTimes;

	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

	public Integer getAlreadySendTimes() {
		if (alreadySendTimes == null)
			alreadySendTimes = 0;
		return alreadySendTimes;
	}

	public void setAlreadySendTimes(Integer alreadySendTimes) {
		this.alreadySendTimes = alreadySendTimes;
	}
}