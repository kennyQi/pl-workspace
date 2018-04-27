package plfx.api.client.api.v1.gj.dto;

import plfx.api.client.common.PlfxApiConstants.GJ;

/**
 * @类功能说明：国际机票供应商订单状态
 * @类修改者：
 * @修改日期：2015-7-7下午4:40:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午4:40:35
 */
public class GJJPOrderStatusDTO {

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
