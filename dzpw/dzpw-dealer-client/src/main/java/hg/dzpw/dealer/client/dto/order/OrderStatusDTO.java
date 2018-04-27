package hg.dzpw.dealer.client.dto.order;

import hg.dzpw.dealer.client.common.EmbeddDTO;

/**
 * @类功能说明：订单状态
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午3:03:58
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class OrderStatusDTO extends EmbeddDTO {
	
	/** 等待支付 */
	public final static Integer ORDER_STATUS_PAY_WAIT = 0;
	/** 支付成功  */
	public final static Integer ORDER_STATUS_PAY_SUCC = 1;
	/** 出票成功  */
	public final static Integer ORDER_STATUS_OUT_SUCC = 2;
	/** 交易成功  */
	public final static Integer ORDER_STATUS_DEAL_SUCC = 3;
	/** 交易关闭 */
	public final static Integer ORDER_STATUS_DEAL_CLOSE = 4;

	/**
	 * 当前状态
	 */
	private Integer currentValue;

	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}
}