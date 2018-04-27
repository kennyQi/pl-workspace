package zzpl.pojo.dto.jp.plfx.gj;

import zzpl.pojo.dto.BaseDTO;


@SuppressWarnings("serial")
public class GJJPOrderStatusDTO extends BaseDTO{
	/**
	 * 订单状态
	 * 
	 * @see GJ#ORDER_STATUS_MAP
	 */
	private Integer currentValue;

	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

}