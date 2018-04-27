package hsl.domain.model.dzp.ticket;

import hg.dzpw.dealer.client.dto.ticket.SingleTicketDTO;
import hg.dzpw.dealer.client.dto.ticket.TicketDTO;
import hsl.domain.model.M;
import hsl.domain.model.dzp.order.DZPTicketOrder;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import hsl.domain.model.dzp.policy.DZPTicketPolicySnapshot;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpot;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * 单票
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "SINGLE_TICKET")
public class DZPSingleTicket extends DZPTicket {

	/**
	 * 所属门票
	 */
	@ManyToOne
	@JoinColumn(name = "GROUP_TICKET_ID")
	private DZPGroupTicket groupTicket;
	/**
	 * 对应景区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private DZPScenicSpot scenicSpot;
	/**
	 * 对应景区名称
	 */
	@Column(name = "SCENIC_NAME", length = 128)
	private String scenicName;
	/**
	 * 初次游玩时间
	 */
	@Column(name = "FIRST_TIME_USE_DATE", columnDefinition = M.DATE_COLUM)
	private Date firstTimeUseDate;

	/**
	 * 所属游客
	 */
	@OneToOne
	@JoinColumn(name = "TOURIST_ID")
	private DZPTourist tourist;

	/**
	 * 状态
	 */
	@Embedded
	private DZPSingleTicketStatus status;

	public DZPGroupTicket getGroupTicket() {
		return groupTicket;
	}

	public void setGroupTicket(DZPGroupTicket groupTicket) {
		this.groupTicket = groupTicket;
	}

	public DZPScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(DZPScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public Date getFirstTimeUseDate() {
		return firstTimeUseDate;
	}

	public void setFirstTimeUseDate(Date firstTimeUseDate) {
		this.firstTimeUseDate = firstTimeUseDate;
	}

	public DZPTourist getTourist() {
		return tourist;
	}

	public void setTourist(DZPTourist tourist) {
		this.tourist = tourist;
	}

	public DZPSingleTicketStatus getStatus() {
		if (status == null)
			status = new DZPSingleTicketStatus();
		return status;
	}

	public void setStatus(DZPSingleTicketStatus status) {
		this.status = status;
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
		 * 创建单票
		 *
		 * @param ticketDTO         电子票务的门票DTO
		 * @param ticketOrder       所属订单
		 * @param groupTicket       所属门票
		 * @param touristMap        游客MAP（身份证号：游客）
		 * @param policyMap         门票政策MAP（景区ID：政策）
		 * @param policySnapshotMap 门票政策快照MAP（景区ID：政策快照）
		 * @return
		 */
		public DZPSingleTicket create(SingleTicketDTO ticketDTO,
									  DZPTicketOrder ticketOrder,
									  DZPGroupTicket groupTicket,
									  Map<String, DZPTourist> touristMap,
									  Map<String, DZPTicketPolicy> policyMap,
									  Map<String, DZPTicketPolicySnapshot> policySnapshotMap) {

			super.create(ticketDTO, ticketOrder);

			// 设置政策与快照
			DZPTicketPolicy policy = policyMap.get(ticketDTO.getScenicSpotId());
			DZPTicketPolicySnapshot policySnapshot = policySnapshotMap.get(ticketDTO.getScenicSpotId());
			setTicketPolicy(policy);
			setTicketPolicySnapshot(policySnapshot);

			// 设置门票信息
			setGroupTicket(groupTicket);
			setScenicSpot(policy.getScenicSpot());
			setScenicName(policySnapshot.getScenicSpotName());

			// 设置游客
			DZPTourist tourist = touristMap.get(ticketDTO.getTourist().getIdNo());
			tourist.setTicket(getGroupTicket());
			setTourist(tourist);

			// 设置状态
			getStatus().setCurrent(DZPSingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE);

			return DZPSingleTicket.this;
		}
	}
}