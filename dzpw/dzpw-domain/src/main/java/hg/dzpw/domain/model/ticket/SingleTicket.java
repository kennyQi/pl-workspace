package hg.dzpw.domain.model.ticket;

import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.tourist.Tourist;
import hg.dzpw.pojo.command.platform.ticket.singleTicket.PlatformModifySingleTicketCommand;
import hg.dzpw.pojo.exception.DZPWException;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @类功能说明：单票
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午3:09:14
 * @版本：V1.0
 */
@Entity
@Table(name = M.TABLE_PREFIX + "SINGLE_TICKET")
public class SingleTicket extends Ticket {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 游客
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOURIST_ID")
	private Tourist tourist;
	/**
	 * 所属门票
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_TICKET_ID")
	private GroupTicket groupTicket;

	/**
	 * 对应景区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private ScenicSpot scenicSpot;

	/**
	 * 结算信息
	 */
	@Embedded
	private SingleTicketSettlementInfo settlementInfo;
	
	/**
	 * SingleTicket状态（景区状态）
	 */
	@Embedded
	private SingleTicketStatus status;
	/**
	 * 初次入园时间
	 */
	@Column(name = "FIRST_TIME_USE_DATE", columnDefinition = M.DATE_COLUM)
	private Date firstTimeUseDate;
	
	/**
	 * 入园记录
	 */
	@Transient
	private List<UseRecord> useRecordList;

	/**
	 * @方法功能说明：创建单票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-20下午2:40:06
	 * @修改内容：
	 * @参数：@param ticketNo
	 * @参数：@param ticketOrder
	 * @参数：@param salePrice
	 * @参数：@param tourist
	 * @参数：@param ticketPolicy
	 * @参数：@param dateStandardPrice
	 * @参数：@param scenicSpot
	 * @参数：@throws DZPWException
	 * @return:void
	 * @throws
	 */
	public void create(String ticketNo, TicketOrder ticketOrder,
					   Double salePrice, Date travelDate, Tourist tourist,
					   TicketPolicy ticketPolicy, Double dateStandardPrice,
					   GroupTicket groupTicket, Integer singleTicketStatus) throws DZPWException {
		
		if (!(ticketPolicy.isSingleInGroup() || ticketPolicy.isSingleInSingle()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "门票政策不可用");
		
		super.create(ticketNo, ticketPolicy, travelDate, tourist, ticketOrder,
				salePrice, dateStandardPrice);
		
		setTourist(tourist);
		setScenicSpot(ticketPolicy.getScenicSpot());
		setGroupTicket(groupTicket);
		SingleTicketStatus status = new SingleTicketStatus();
		status.setCurrent(singleTicketStatus);
		setStatus(status);
	}

	public GroupTicket getGroupTicket() {
		return groupTicket;
	}

	public void setGroupTicket(GroupTicket groupTicket) {
		this.groupTicket = groupTicket;
	}

	public List<UseRecord> getUseRecordList() {
		return useRecordList;
	}

	public void setUseRecordList(List<UseRecord> useRecordList) {
		this.useRecordList = useRecordList;
	}

	public ScenicSpot getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(ScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public SingleTicketSettlementInfo getSettlementInfo() {
		if (settlementInfo == null)
			settlementInfo = new SingleTicketSettlementInfo();
		return settlementInfo;
	}

	public void setSettlementInfo(SingleTicketSettlementInfo settlementInfo) {
		this.settlementInfo = settlementInfo;
	}

	public Tourist getTourist() {
		return tourist;
	}

	public void setTourist(Tourist tourist) {
		this.tourist = tourist;
	}

	public SingleTicketStatus getStatus() {
		return status;
	}

	public void setStatus(SingleTicketStatus status) {
		this.status = status;
	}

	public void platformModifySingleTicket(PlatformModifySingleTicketCommand command) {
		
		getStatus().setCurrent(command.getStatus());
	}

	public Date getFirstTimeUseDate() {
		return firstTimeUseDate;
	}

	public void setFirstTimeUseDate(Date firstTimeUseDate) {
		this.firstTimeUseDate = firstTimeUseDate;
	}

}