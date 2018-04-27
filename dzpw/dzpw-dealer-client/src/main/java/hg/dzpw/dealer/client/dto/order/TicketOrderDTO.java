package hg.dzpw.dealer.client.dto.order;

import hg.dzpw.dealer.client.common.BaseDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicySnapshotDTO;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;

import java.util.List;

/**
 * @类功能说明：门票订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午2:56:06
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class TicketOrderDTO extends BaseDTO {

	/**
	 * 门票订单基本信息
	 */
	private TicketOrderBaseInfoDTO baseInfo;

	/**
	 * 门票订单状态
	 */
	private OrderStatusDTO status;

	/**
	 * 订单支付信息
	 */
	private TicketOrderPayInfoDTO payInfo;

	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * 门票政策版本
	 */
	private Integer ticketPolicyVersion;
	
	/**
	 * 门票列表[按QO条件决定是否查询]
	 */
	private List<GroupTicketDTO> groupTickets;
	
	/**
	 * 订单对应门票政策的快照
	 */
	private TicketPolicySnapshotDTO ticketPolicySnapshot;

	public TicketOrderBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(TicketOrderBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public OrderStatusDTO getStatus() {
		return status;
	}

	public void setStatus(OrderStatusDTO status) {
		this.status = status;
	}

	public TicketOrderPayInfoDTO getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(TicketOrderPayInfoDTO payInfo) {
		this.payInfo = payInfo;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public Integer getTicketPolicyVersion() {
		return ticketPolicyVersion;
	}

	public void setTicketPolicyVersion(Integer ticketPolicyVersion) {
		this.ticketPolicyVersion = ticketPolicyVersion;
	}

	public List<GroupTicketDTO> getGroupTickets() {
		return groupTickets;
	}

	public void setGroupTickets(List<GroupTicketDTO> groupTickets) {
		this.groupTickets = groupTickets;
	}

	public TicketPolicySnapshotDTO getTicketPolicySnapshot() {
		return ticketPolicySnapshot;
	}

	public void setTicketPolicySnapshot(TicketPolicySnapshotDTO ticketPolicySnapshot) {
		this.ticketPolicySnapshot = ticketPolicySnapshot;
	}

}