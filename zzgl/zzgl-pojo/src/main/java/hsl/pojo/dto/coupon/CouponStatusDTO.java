package hsl.pojo.dto.coupon;

import hsl.pojo.dto.BaseDTO;

/**
 * @类功能说明：卡券DTO
 * @类修改者：
 * @修改日期：2014年10月15日下午1:51:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：liusong
 * @创建时间：2014年10月15日下午1:51:58
 */
@SuppressWarnings("serial")
public class CouponStatusDTO extends BaseDTO {
	/**
	 * 卡券状态
	 */
	private int currentValue;

	/**
	 * 卡券已赠送次数
	 */
	private Integer alreadySendTimes;

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	public Integer getAlreadySendTimes() {
		return alreadySendTimes;
	}

	public void setAlreadySendTimes(Integer alreadySendTimes) {
		this.alreadySendTimes = alreadySendTimes;
	}
}
