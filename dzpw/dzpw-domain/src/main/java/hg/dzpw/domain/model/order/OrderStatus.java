package hg.dzpw.domain.model.order;

import hg.dzpw.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
@Embeddable
public class OrderStatus {
	
	/** 等待支付 */
	public final static Integer ORDER_STATUS_PAY_WAIT = 0;
	/** 支付成功  */
	public final static Integer ORDER_STATUS_PAY_SUCC = 1;
	/** 出票成功   (支付成功后状态直接变成出票成功) */
	public final static Integer ORDER_STATUS_OUT_SUCC = 2;
	/** 交易成功  */
	public final static Integer ORDER_STATUS_DEAL_SUCC = 3;
	/** 交易关闭 */
	public final static Integer ORDER_STATUS_DEAL_CLOSE = 4;
	
	
	/**
	 * 当前状态
	 */
	@Column(name = "CURRENT_VALUE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer currentValue = ORDER_STATUS_PAY_WAIT;

	public Integer getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}
}