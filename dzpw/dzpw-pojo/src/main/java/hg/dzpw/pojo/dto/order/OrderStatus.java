package hg.dzpw.pojo.dto.order;

import hg.dzpw.pojo.common.BaseDTO;

/**
 * @类功能说明: 订单状态DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-11 下午4:54:19 
 * @版本：V1.0
 */
public class OrderStatus extends BaseDTO {
	private static final long serialVersionUID = 1L;
	
	/** 等待支付 */
	public final static Integer TICKET_ORDER_CURRENT_PAY_WAIT = 0;
	/** 支付成功  */
	public final static Integer TICKET_ORDER_CURRENT_PAY_SUCC = 1;
	/** 出票成功  */
	public final static Integer TICKET_ORDER_CURRENT_OUT_SUCC = 2;
	/** 交易成功  */
	public final static Integer TICKET_ORDER_CURRENT_DEAL_SUCC = 3;
	/** 交易关闭  
	 *  1.由于票务在规定之间内未支付或者有经销商自主发起的取消订单操作。取消订单为同一订单号所有票务统一取消
	 *  2.当退款状态为“退款成功”，标记退款状态为交易关闭
	 **/
	public final static Integer TICKET_ORDER_CURRENT_DEAL_CLOSE= 4;
	
	
	/**
	 * 当前状态(0,未出票；1,已出票；2,交易关闭)
	 */
	private int currentValue;

	public int getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}
}