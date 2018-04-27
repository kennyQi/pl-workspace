package hsl.domain.model.dzp.ticket;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.dzpw.dealer.client.dto.ticket.TicketDTO;
import hsl.domain.model.M;
import hsl.domain.model.dzp.order.DZPTicketOrder;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import hsl.domain.model.dzp.policy.DZPTicketPolicySnapshot;

import javax.persistence.*;
import java.util.Date;

/**
 * 门票
 *
 * @author zhurz
 * @since 1.8
 */
@MappedSuperclass
@SuppressWarnings("serial")
public class DZPTicket extends BaseModel {

	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_ORDER_ID")
	private DZPTicketOrder ticketOrder;

	/**
	 * 有效期开始时间
	 */
	@Column(name = "USE_DATE_START", columnDefinition = M.DATE_COLUM)
	private Date useDateStart;

	/**
	 * 有效期结束时间
	 */
	@Column(name = "USE_DATE_END", columnDefinition = M.DATE_COLUM)
	private Date useDateEnd;

	/**
	 * 所属门票政策
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICY_ID")
	private DZPTicketPolicy ticketPolicy;

	/**
	 * 所属门票政策快照
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICY_SNAPSHOT_ID")
	private DZPTicketPolicySnapshot ticketPolicySnapshot;

	public DZPTicketOrder getTicketOrder() {
		return ticketOrder;
	}

	public void setTicketOrder(DZPTicketOrder ticketOrder) {
		this.ticketOrder = ticketOrder;
	}

	public Date getUseDateStart() {
		return useDateStart;
	}

	public void setUseDateStart(Date useDateStart) {
		this.useDateStart = useDateStart;
	}

	public Date getUseDateEnd() {
		return useDateEnd;
	}

	public void setUseDateEnd(Date useDateEnd) {
		this.useDateEnd = useDateEnd;
	}

	public DZPTicketPolicy getTicketPolicy() {
		return ticketPolicy;
	}

	public void setTicketPolicy(DZPTicketPolicy ticketPolicy) {
		this.ticketPolicy = ticketPolicy;
	}

	public DZPTicketPolicySnapshot getTicketPolicySnapshot() {
		return ticketPolicySnapshot;
	}

	public void setTicketPolicySnapshot(DZPTicketPolicySnapshot ticketPolicySnapshot) {
		this.ticketPolicySnapshot = ticketPolicySnapshot;
	}


	// --------------------------------------------------------------------

	private transient Manager manager;

	protected Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	protected class Manager {
		/**
		 * 通过电子票务返回的DTO创建门票
		 *
		 * @param ticketDTO   电子票务的门票DTO
		 * @param ticketOrder 所属订单
		 * @return
		 */
		protected DZPTicket create(TicketDTO ticketDTO, DZPTicketOrder ticketOrder) {
			setId(UUIDGenerator.getUUID());
			setTicketOrder(ticketOrder);
			setUseDateStart(ticketDTO.getUseDateStart());
			setUseDateEnd(ticketDTO.getUseDateEnd());
			return DZPTicket.this;
		}
	}
}