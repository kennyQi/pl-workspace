package hg.dzpw.domain.model.order;

import hg.common.component.BaseModel;
import hg.dzpw.dealer.client.api.v1.request.CloseTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.PayToTicketOrderCommand;
import hg.dzpw.domain.model.M;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicySnapshot;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.domain.model.ticket.SingleTicket;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

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
@Entity
@DynamicUpdate
@Table(name = M.TABLE_PREFIX + "TICKET_ORDER")
public class TicketOrder extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 门票订单基本信息
	 */
	@Embedded
	private TicketOrderBaseInfo baseInfo;

	/**
	 * 门票订单状态
	 */
	@Embedded
	private OrderStatus status;

	/**
	 * 订单支付信息
	 */
	@Embedded
	private TicketOrderPayInfo payInfo;

	/**
	 * 订单下的所有单票
	 */
	@OneToMany(mappedBy = "ticketOrder")
	private List<SingleTicket> singleTickets;

	/**
	 * 订单下的所有独立门票
	 */
	@OneToMany(mappedBy = "ticketOrder", cascade = { CascadeType.ALL })
	private List<GroupTicket> groupTickets;

	/**
	 * 门票政策
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_POLICY_ID")
	private TicketPolicy ticketPolicy;

	/**
	 * 门票政策(快照)
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_POLICY_SNAPSHOT_ID")
	private TicketPolicySnapshot ticketPolicySnapshot;

	/**
	 * @方法功能说明：创建订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-19下午3:46:15
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param ticketPolicy
	 * @参数：@param dealer
	 * @参数：@param groupTickets
	 * @参数：@param ticketOrderNo
	 * @return:void
	 * @throws
	 */
	public void create(CreateTicketOrderCommand command,
					   TicketPolicy ticketPolicy, Dealer dealer,
					   List<GroupTicket> groupTickets, String ticketOrderNo,
					   Integer orderStatus) {
		
		setId(ticketOrderNo);
		
		getBaseInfo().setCreateDate(new Date());
		getBaseInfo().setDealerOrderId(command.getDealerOrderId());
		getBaseInfo().setTicketNo(command.getBuyNum());
		getBaseInfo().setLinkMan(command.getBookMan());
		getBaseInfo().setTelephone(command.getBookManMobile());
		getBaseInfo().setFromDealer(dealer);
		
		getPayInfo().setPrice(command.getPrice());
		getPayInfo().setPayDate(OrderStatus.ORDER_STATUS_PAY_WAIT==orderStatus?null:new Date());
		getPayInfo().setRefundAliPayAccount(command.getBookManAliPayAccount());
		// 订单经销商的手续费
		getPayInfo().setDealerSettlementFee(command.getBuyNum()*dealer.getAccountInfo().getSettlementFee());
		
		setTicketPolicy(ticketPolicy);
		setTicketPolicySnapshot(ticketPolicy.getLastSnapshot());
		
		setGroupTickets(groupTickets);
		getStatus().setCurrentValue(orderStatus);
	}

	/**
	 * @方法功能说明：为订单付款
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-27上午11:07:58
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return				0成功,1订单已支付,2订单已关闭
	 * @return:int				是否需要支付
	 * @throws
	 */
	public int payTo(PayToTicketOrderCommand command) {
		// 订单已支付
		if (OrderStatus.ORDER_STATUS_OUT_SUCC.equals(getStatus().getCurrentValue()) ||
				OrderStatus.ORDER_STATUS_PAY_SUCC.equals(getStatus().getCurrentValue()) ||
					OrderStatus.ORDER_STATUS_DEAL_SUCC.equals(getStatus().getCurrentValue()))
			return 1;
		
		// 订单已关闭
		if (OrderStatus.ORDER_STATUS_DEAL_CLOSE.equals(getStatus().getCurrentValue()))
			return 2;
		
//		List<GroupTicket> groupTickets = getGroupTickets();
//		for (GroupTicket groupTicket : groupTickets)
//			groupTicket.getStatus().setCurrent(GroupTicketStatus.GROUP_TICKET_STATUS_UNUSE);
		return 0;
	}
	
	/**
	 * @方法功能说明：关闭订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-27上午11:10:10
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:int	0成功,  1已支付,  2已关闭
	 * @throws
	 */
	public int close() {
		if (OrderStatus.ORDER_STATUS_PAY_SUCC == getStatus().getCurrentValue()
				|| OrderStatus.ORDER_STATUS_OUT_SUCC == getStatus().getCurrentValue()
					|| OrderStatus.ORDER_STATUS_DEAL_SUCC == getStatus().getCurrentValue())
			return 1;
		
		if (OrderStatus.ORDER_STATUS_DEAL_CLOSE == getStatus().getCurrentValue())
			return 2;
		
		return 0;
	}

	public TicketOrderBaseInfo getBaseInfo() {
		if (null == baseInfo)
			baseInfo = new TicketOrderBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(TicketOrderBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public OrderStatus getStatus() {
		if (null == status)
			status = new OrderStatus();
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<SingleTicket> getSingleTickets() {
		return singleTickets;
	}

	public void setSingleTickets(List<SingleTicket> singleTickets) {
		this.singleTickets = singleTickets;
	}

	public List<GroupTicket> getGroupTickets() {
		return groupTickets;
	}

	public void setGroupTickets(List<GroupTicket> groupTickets) {
		this.groupTickets = groupTickets;
	}

	public TicketOrderPayInfo getPayInfo() {
		if (null == payInfo)
			payInfo = new TicketOrderPayInfo();
		return payInfo;
	}

	public void setPayInfo(TicketOrderPayInfo payInfo) {
		this.payInfo = payInfo;
	}

	public TicketPolicy getTicketPolicy() {
		return ticketPolicy;
	}

	public void setTicketPolicy(TicketPolicy ticketPolicy) {
		this.ticketPolicy = ticketPolicy;
	}

	public TicketPolicySnapshot getTicketPolicySnapshot() {
		return ticketPolicySnapshot;
	}

	public void setTicketPolicySnapshot(
			TicketPolicySnapshot ticketPolicySnapshot) {
		this.ticketPolicySnapshot = ticketPolicySnapshot;
	}
	
}