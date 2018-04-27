package hg.dzpw.domain.model.ticket;

import hg.dzpw.domain.model.M;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明: 套票状态
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @创建时间：2014-11-12 上午10:30:19 
 * @版本：V1.0
 */
@Embeddable
public class GroupTicketStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	//********以下1.4版本开始弃用
	/** 未激活 */
	public final static Integer GROUP_TICKET_STATUS_UNACTIVE = -1;
	/** 未游玩 */
	public final static Integer GROUP_TICKET_STATUS_UNUSE = 0;
	/** 部分游玩 */
	public final static Integer GROUP_TICKET_STATUS_PARTUSE = 1;
	/** 全部游玩 */
	public final static Integer GROUP_TICKET_STATUS_USED = 2;
	/** 部分失效 */
	public final static Integer GROUP_TICKET_STATUS_PARTINVALID = 3;
	/** 已全部失效 */
	public final static Integer GROUP_TICKET_STATUS_INVALID = 4;
	//*************/
	
	
	
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
	 * groupTicket当前状态
	 */
	@Column(name = "CURRENT", columnDefinition = M.NUM_COLUM)
	private Integer current = GROUP_TICKET_CURRENT_PAY_WAIT;
	
	/**
	 * groupTicket售后/退款状态
	 */
	@Column(name = "REFUND_CURRENT", columnDefinition = M.NUM_COLUM)
	private Integer refundCurrent;
	/**
	 * 退款时间
	 */
	@Column(name = "REFUND_DATE", columnDefinition = M.DATE_COLUM)
	private Date refundDate;
	/**
	 * 订单关闭时间 （超时为支付 或者 经销商取消订单）
	 */
	@Column(name = "CLOSE_DATE", columnDefinition = M.DATE_COLUM)
	private Date closeDate;
	
	
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public Integer getRefundCurrent() {
		return refundCurrent;
	}
	public void setRefundCurrent(Integer refundCurrent) {
		this.refundCurrent = refundCurrent;
	}
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
}