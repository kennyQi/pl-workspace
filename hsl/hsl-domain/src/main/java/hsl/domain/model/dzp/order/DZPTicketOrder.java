package hsl.domain.model.dzp.order;

import hg.common.component.BaseModel;
import hg.common.util.DateUtil;
import hg.common.util.MoneyUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.dto.order.TicketOrderDTO;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;
import hsl.domain.model.M;
import hsl.domain.model.dzp.policy.DZPTicketPolicy;
import hsl.domain.model.dzp.policy.DZPTicketPolicyDatePrice;
import hsl.domain.model.dzp.policy.DZPTicketPolicySnapshot;
import hsl.domain.model.dzp.ticket.DZPGroupTicket;
import hsl.domain.model.dzp.ticket.DZPTourist;
import hsl.pojo.command.dzp.order.CreateDZPTicketOrderCommand;
import hsl.pojo.exception.ShowMessageException;

import javax.persistence.*;
import java.util.*;

/**
 * 门票订单
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_DZP + "TICKET_ORDER")
@SuppressWarnings("serial")
public class DZPTicketOrder extends BaseModel {

	/**
	 * 门票订单基本信息
	 */
	@Embedded
	private DZPTicketOrderBaseInfo baseInfo;

	/**
	 * 门票订单状态
	 */
	@Embedded
	private DZPTicketOrderStatus status;

	/**
	 * 订单支付信息
	 */
	@Embedded
	private DZPTicketOrderPayInfo payInfo;

	/**
	 * 门票政策
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_POLICY_ID")
	private DZPTicketPolicy ticketPolicy;

	/**
	 * 订单对应门票政策的快照
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_POLICY_SNAPSHOT_ID")
	private DZPTicketPolicySnapshot ticketPolicySnapshot;

	/**
	 * 门票列表
	 */
	@OneToMany(mappedBy = "ticketOrder")
	private List<DZPGroupTicket> groupTickets;

	/**
	 * 游客列表
	 */
	@OrderBy("seq")
	@OneToMany(mappedBy = "ticketOrder", cascade = CascadeType.ALL)
	private List<DZPTourist> tourists;

	public DZPTicketOrderBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new DZPTicketOrderBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(DZPTicketOrderBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public DZPTicketOrderStatus getStatus() {
		if (status == null)
			status = new DZPTicketOrderStatus();
		return status;
	}

	public void setStatus(DZPTicketOrderStatus status) {
		this.status = status;
	}

	public DZPTicketOrderPayInfo getPayInfo() {
		if (payInfo == null)
			payInfo = new DZPTicketOrderPayInfo();
		return payInfo;
	}

	public void setPayInfo(DZPTicketOrderPayInfo payInfo) {
		this.payInfo = payInfo;
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

	public List<DZPGroupTicket> getGroupTickets() {
		if (groupTickets == null)
			groupTickets = new ArrayList<DZPGroupTicket>();
		return groupTickets;
	}

	public void setGroupTickets(List<DZPGroupTicket> groupTickets) {
		this.groupTickets = groupTickets;
	}

	public List<DZPTourist> getTourists() {
		if (tourists == null)
			tourists = new ArrayList<DZPTourist>();
		return tourists;
	}

	public void setTourists(List<DZPTourist> tourists) {
		this.tourists = tourists;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {

		/**
		 * 创建电子票订单
		 *
		 * @param command 下单命令
		 * @param orderNo 订单号
		 * @param policy  门票政策
		 * @return
		 */
		public DZPTicketOrder createOrder(CreateDZPTicketOrderCommand command, String orderNo, DZPTicketPolicy policy) {

			setId(UUIDGenerator.getUUID());

			// 基本信息设置
			getBaseInfo().setOrderNo(orderNo);
			getBaseInfo().setFromType(command.getFromType());
			getBaseInfo().setTicketNum(command.getTourists().size());
			getBaseInfo().setOrderDate(new Date());
			getBaseInfo().setLinkMan(command.getLinkMan());
			getBaseInfo().setLinkMobile(command.getLinkMobile());
			getBaseInfo().setTravelDate(command.getTravelDate());

			// 等待用户支付
			getStatus().setCurrent(DZPTicketOrderStatus.ORDER_STATUS_USER_PAY_WAIT);

			// 设置支付信息
			double price = 0d;
			if (DZPTicketPolicy.TICKET_POLICY_TYPE_GROUP.equals(policy.getType())) {
				// 联票没有价格日历
				price = policy.getBaseInfo().getPlayPrice();
			} else {
				DZPTicketPolicyDatePrice datePrice = policy.getPrice().get(DateUtil.formatDateTime(command.getTravelDate(), "yyyyMMdd"));
				if (datePrice != null)
					price = datePrice.getPrice();
			}
			if (price == 0d)
				throw new ShowMessageException("不在预售期");
			// 游客数
			int count = getBaseInfo().getTicketNum();
			// 计算订单金额
			double dealerPrice = MoneyUtil.round(price * count, 2);
			getPayInfo().setDealerPrice(dealerPrice);
			getPayInfo().setUserPrice(dealerPrice);
			getPayInfo().setRefundAlipayAccount(command.getAlipayAccount());

			setTicketPolicy(policy);
			setTicketPolicySnapshot(policy.getLastSnapshot());

			// 设置游客
			for (int i = 0; i < command.getTourists().size(); i++) {
				getTourists().add(new DZPTourist().manager().create(command.getTourists().get(i), DZPTicketOrder.this, i + 1));
			}

			return DZPTicketOrder.this;
		}

		/**
		 * 构建电子票务下单command
		 *
		 * @return
		 */
		public CreateTicketOrderCommand buildDZPWCreateTicketOrderCommand() {

			CreateTicketOrderCommand command = new CreateTicketOrderCommand();
			command.setDealerOrderId(getBaseInfo().getOrderNo());
			command.setTicketPolicyId(getTicketPolicySnapshot().getTicketPolicyId());
			command.setTicketPolicyVersion(getTicketPolicySnapshot().getVersion());
			command.setTravelDate(getBaseInfo().getTravelDate());
			command.setBuyNum(getBaseInfo().getTicketNum());
			command.setPrice(getPayInfo().getDealerPrice());
			command.setBookMan(getBaseInfo().getLinkMan());
			command.setBookManMobile(getBaseInfo().getLinkMobile());
			command.setBookManAliPayAccount(getPayInfo().getRefundAlipayAccount());
			command.setTourists(new ArrayList<TouristDTO>());

			for (DZPTourist dzpTourist : getTourists())
				command.getTourists().add(dzpTourist.manager().convertToDZPWTouristDTO());

			return command;
		}

		/**
		 * 处理电子票务下单返回结果
		 *
		 * @param orderDTO
		 * @return
		 */
		public DZPTicketOrder processDZPWCreateOrderResult(TicketOrderDTO orderDTO) {

			// 更新基本信息
			getBaseInfo().setDzpwOrderNo(orderDTO.getId());
			getBaseInfo().setCreateDate(orderDTO.getBaseInfo().getCreateDate());

			// 更新支付信息
			getPayInfo().setDealerSettlementFee(orderDTO.getPayInfo().getDealerSettlementFee());

			// 更新门票信息
			Map<String, DZPTourist> touristMap = new HashMap<String, DZPTourist>();
			for (DZPTourist tourist : getTourists())
				touristMap.put(tourist.getIdNo(), tourist);
			for (GroupTicketDTO groupTicketDTO : orderDTO.getGroupTickets())
				getGroupTickets().add(new DZPGroupTicket().manager().create(groupTicketDTO, DZPTicketOrder.this, touristMap));

			return DZPTicketOrder.this;
		}

	}
}