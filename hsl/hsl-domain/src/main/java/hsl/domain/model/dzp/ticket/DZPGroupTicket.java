package hsl.domain.model.dzp.ticket;

import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;
import hg.dzpw.dealer.client.dto.ticket.SingleTicketDTO;
import hsl.domain.model.M;
import hsl.domain.model.dzp.order.DZPTicketOrder;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import hsl.domain.model.dzp.policy.DZPTicketPolicySnapshot;
import hsl.pojo.util.HSLConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门票
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_DZP + "GROUP_TICKET")
@SuppressWarnings("serial")
public class DZPGroupTicket extends DZPTicket implements HSLConstants.DZPGroupTicketType {

	/**
	 * 票号
	 */
	@Column(name = "TICKET_NO", length = 64)
	private String ticketNo;

	/**
	 * 套票类型
	 *
	 * @see HSLConstants.DZPGroupTicketType
	 */
	@Column(name = "TICKET_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer type;

	/**
	 * 单票列表
	 */
	@OneToMany(mappedBy = "groupTicket", cascade = CascadeType.ALL)
	private List<DZPSingleTicket> singleTickets;

	/**
	 * 实际价格
	 */
	@Column(name = "PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price = 0d;

	/**
	 * 经销商手续费（元/张）
	 */
	@Column(name = "DEALER_SETTLEMENT_FEE", columnDefinition = M.MONEY_COLUM)
	private Double dealerSettlementFee = 0d;

	/**
	 * 套票状态
	 */
	@Embedded
	private DZPGroupTicketStatus status;

	/**
	 * 入园记录
	 */
	@OrderBy("useDate")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fromGroupTicket")
	private List<DZPUseRecord> useRecords;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<DZPSingleTicket> getSingleTickets() {
		if (singleTickets == null)
			singleTickets = new ArrayList<DZPSingleTicket>();
		return singleTickets;
	}

	public void setSingleTickets(List<DZPSingleTicket> singleTickets) {
		this.singleTickets = singleTickets;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDealerSettlementFee() {
		return dealerSettlementFee;
	}

	public void setDealerSettlementFee(Double dealerSettlementFee) {
		this.dealerSettlementFee = dealerSettlementFee;
	}

	public DZPGroupTicketStatus getStatus() {
		if (status == null)
			status = new DZPGroupTicketStatus();
		return status;
	}

	public void setStatus(DZPGroupTicketStatus status) {
		this.status = status;
	}

	public List<DZPUseRecord> getUseRecords() {
		if (useRecords == null)
			useRecords = new ArrayList<DZPUseRecord>();
		return useRecords;
	}

	public void setUseRecords(List<DZPUseRecord> useRecords) {
		this.useRecords = useRecords;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager extends DZPTicket.Manager {
		/**
		 * 创建门票
		 *
		 * @param ticketDTO   电子票务的门票DTO
		 * @param ticketOrder 所属订单
		 * @param touristMap  游客MAP（身份证号：游客）
		 * @return
		 */
		public DZPGroupTicket create(GroupTicketDTO ticketDTO, DZPTicketOrder ticketOrder,
									 Map<String, DZPTourist> touristMap) {

			super.create(ticketDTO, ticketOrder);

			// 设置政策信息
			DZPTicketPolicy policy = ticketOrder.getTicketPolicy();
			DZPTicketPolicySnapshot policySnapshot = ticketOrder.getTicketPolicySnapshot();
			Map<String, DZPTicketPolicy> policyMap = new HashMap<String, DZPTicketPolicy>();
			Map<String, DZPTicketPolicySnapshot> policySnapshotMap = new HashMap<String, DZPTicketPolicySnapshot>();
			for (DZPTicketPolicy singleTicketPolicy : policy.getSingleTicketPolicies())
				policyMap.put(M.getModelObjectId(singleTicketPolicy.getScenicSpot()), singleTicketPolicy);
			for (DZPTicketPolicySnapshot singleTicketPolicySnapshot : policySnapshot.getSingleTicketPolicies())
				policySnapshotMap.put(singleTicketPolicySnapshot.getScenicSpotId(), singleTicketPolicySnapshot);
			setTicketPolicy(policy);
			setTicketPolicySnapshot(policySnapshot);

			// 设置状态
			getStatus().setCurrent(DZPGroupTicketStatus.GROUP_TICKET_CURRENT_USER_PAY_WAIT);
			getStatus().setRefundCurrent(DZPGroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_ALL_NONE);

			// 设置基本信息
			setType(ticketDTO.getType());
			setDealerSettlementFee(ticketDTO.getDealerSettlementFee());
			setTicketNo(ticketDTO.getTicketNo());
			setPrice(ticketDTO.getPrice());

			// 设置门票下的单票列表
			for (SingleTicketDTO singleTicketDTO : ticketDTO.getSingleTickets())
				getSingleTickets().add(new DZPSingleTicket().manager().create(singleTicketDTO, ticketOrder,
						DZPGroupTicket.this, touristMap, policyMap, policySnapshotMap));

			return DZPGroupTicket.this;
		}
	}
}