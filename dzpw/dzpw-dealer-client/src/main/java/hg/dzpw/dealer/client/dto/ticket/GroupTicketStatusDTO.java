package hg.dzpw.dealer.client.dto.ticket;

import java.util.Date;

import hg.dzpw.dealer.client.common.EmbeddDTO;

/**
 * @类功能说明: 套票状态
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @创建时间：2014-11-12 上午10:30:19
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class GroupTicketStatusDTO extends EmbeddDTO {


	/** 等待支付 */
	public final static Integer GROUP_TICKET_CURRENT_PAY_WAIT = 0;
	/** 支付成功  */
	public final static Integer GROUP_TICKET_CURRENT_PAY_SUCC = 1;
	/** 出票成功 (支付成功后状态直接变成出票成功) */
	public final static Integer GROUP_TICKET_CURRENT_OUT_SUCC = 2;
	/** 交易成功  */
	public final static Integer GROUP_TICKET_CURRENT_DEAL_SUCC = 3;
	/** 交易关闭  
	 *  1.由于票务在规定之间内未支付或者有经销商自主发起的取消订单操作。取消订单为同一订单号所有票务统一取消
	 *  2.当退款状态为“退款成功”，标记退款状态为交易关闭
	 **/
	public final static Integer GROUP_TICKET_CURRENT_DEAL_CLOSE= 4;
	
	/**
	 * 可退款
	 */
	public final static Integer GROUP_TICKET_REFUND_CURRENT_CAN = 0;
	/**
	 * 退款成功(当票务中所有景区的状态为已退款)
	 */
	public final static Integer GROUP_TICKET_REFUND_CURRENT_ALL_SUCC = 1;
	/**
	 * 部分退款成功(当票务中所有景区的状态为已退款和已结算)
	 */
	public final static Integer GROUP_TICKET_REFUND_CURRENT_SOME_SUCC = 2;
	/**
	 * 退款待处理(：当景区状态中存在待退款状态时显示)
	 */
	public final static Integer GROUP_TICKET_REFUND_CURRENT_WAIT_HANDLE = 3;
	
	
	/**
	 * 当前状态
	 */
	private Integer current;
	/**
	 * 退款状态
	 */
	private Integer refundCurrent;
	/**
	 * 退款时间
	 */
	private Date refundDate;
	/**
	 * 订单关闭时间 （超时为支付 或者 经销商取消订单）
	 */
	private Date closeDate;
	

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getRefundCurrent() {
		return refundCurrent;
	}

	public void setRefundCurrent(Integer refundCurrent) {
		this.refundCurrent = refundCurrent;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

}