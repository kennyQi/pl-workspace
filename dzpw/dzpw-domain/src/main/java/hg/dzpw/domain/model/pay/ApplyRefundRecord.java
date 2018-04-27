package hg.dzpw.domain.model.pay;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.ticket.GroupTicket;

/**
 * @类功能说明：申请退款记录
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-3-17上午10:50:36
 * @版本：V1.0
 *
 */
@Entity
@Table(name = M.TABLE_PREFIX + "APPLY_REFUND_RECORD")
public class ApplyRefundRecord extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 申请退款门票
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_TICKET_ID")
	private GroupTicket groupTicket;
	
	/**
	 * 关联订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_ORDER_ID")
	private TicketOrder ticketOrder;
	
	/**
	 * 记录时间
	 */
	@Column(name = "RECORD_DATE", columnDefinition = M.DATE_COLUM)
	private Date recordDate;

	public GroupTicket getGroupTicket() {
		return groupTicket;
	}

	public void setGroupTicket(GroupTicket groupTicket) {
		this.groupTicket = groupTicket;
	}

	public TicketOrder getTicketOrder() {
		return ticketOrder;
	}

	public void setTicketOrder(TicketOrder ticketOrder) {
		this.ticketOrder = ticketOrder;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	
}
