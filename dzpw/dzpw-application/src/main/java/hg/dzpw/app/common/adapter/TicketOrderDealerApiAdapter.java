package hg.dzpw.app.common.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hg.common.util.BeanMapperUtils;
import hg.dzpw.app.common.util.ModelUtils;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.dealer.client.api.v1.request.TicketOrderQO;
import hg.dzpw.dealer.client.dto.order.OrderStatusDTO;
import hg.dzpw.dealer.client.dto.order.TicketOrderBaseInfoDTO;
import hg.dzpw.dealer.client.dto.order.TicketOrderDTO;
import hg.dzpw.dealer.client.dto.order.TicketOrderPayInfoDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicySnapshotDTO;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketStatusDTO;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.log.util.HgLogger;

/**
 * @类功能说明：门票订单经销商接口适配器
 * @类修改者：
 * @修改日期：2015-4-24下午4:51:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-24下午4:51:21
 */
public class TicketOrderDealerApiAdapter {

	/**
	 * @方法功能说明：将门票订单转换为DTO
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-27下午3:25:39
	 * @修改内容：
	 * @参数：@param ticketOrder
	 * @参数：@param QO
	 * @参数：@return
	 * @return:TicketOrderDTO
	 * @throws
	 */
	public TicketOrderDTO convertDTO(TicketOrder ticketOrder, TicketOrderQO QO) {
		
		if (ticketOrder == null)
			return null;
		
		TicketOrderDTO dto = new TicketOrderDTO();
		dto.setId(ticketOrder.getId());
		dto.setBaseInfo(BeanMapperUtils.map(ticketOrder.getBaseInfo(), TicketOrderBaseInfoDTO.class));
		dto.getBaseInfo().setDealerName(ticketOrder.getBaseInfo().getFromDealer().getBaseInfo().getName());
		dto.getBaseInfo().setDealerId(ticketOrder.getBaseInfo().getFromDealer().getId());
		dto.setStatus(BeanMapperUtils.map(ticketOrder.getStatus(), OrderStatusDTO.class));
		dto.setPayInfo(BeanMapperUtils.map(ticketOrder.getPayInfo(), TicketOrderPayInfoDTO.class));
		dto.setTicketPolicyId(ModelUtils.getId(ticketOrder.getTicketPolicy()));
		dto.setTicketPolicyVersion(ticketOrder.getTicketPolicySnapshot() == null ? 1
				: ticketOrder.getTicketPolicySnapshot().getVersion());
		dto.setTicketPolicySnapshot(BeanMapperUtils.map(ticketOrder.getTicketPolicySnapshot(), TicketPolicySnapshotDTO.class));
		
		// 类型转换
		List<GroupTicket> groupTickets = ticketOrder.getGroupTickets();
		if (groupTickets != null && groupTickets.size() > 0) {
			List<GroupTicketDTO> groupTicketDTOs = new ArrayList<GroupTicketDTO>();
			double dealerSettlementFee=0d;
			for (GroupTicket groupTicket : groupTickets){
				dealerSettlementFee += groupTicket.getDealerSettlementFee();
//				groupTicketDTOs.add(DealerApiAdapter.groupTicket.convertDTO(groupTicket, 
//																				QO==null?false:QO.getSingleTicketsFetch(),
//																						QO==null?false:QO.getTouristFetch()));
				groupTicketDTOs.add(DealerApiAdapter.groupTicket.convertDTO(groupTicket, true, true));
			}
				dto.getPayInfo().setDealerSettlementFee(dealerSettlementFee);
				dto.setGroupTickets(groupTicketDTOs);
		}
		//更新订单和联票状态
		updateGroupStatusAndOrderStatus(ticketOrder,QO,dto);
//		if (QO == null || (QO.getGroupTicketsFetch() != null && QO.getGroupTicketsFetch()==false)) {
//			dto.setGroupTickets(null);
//		}
		return dto;
	}
/**
 * 
 * @描述： 经销商端状态不一致情况下修改联票状态和订单状态
 * @author: guotx 
 * @version: 2016-2-17 上午10:49:04
 */
	private void updateGroupStatusAndOrderStatus(TicketOrder ticketOrder,
			TicketOrderQO qO, TicketOrderDTO dto) {
		List<GroupTicket> groupTickets = ticketOrder.getGroupTickets();
		//修改联票状态
		for (GroupTicket groupTicket : groupTickets) {
			List<SingleTicket> singleTickets=groupTicket.getSingleTickets();
			
			Map<Integer, SingleTicket> singleStatusMap=new HashMap<Integer, SingleTicket>();
			for (SingleTicket singleTicket : singleTickets) 
				singleStatusMap.put(singleTicket.getStatus().getCurrent(),singleTicket);
			
			List<GroupTicketDTO> gps=dto.getGroupTickets();
			switch (singleStatusMap.size()) {
			case 1:
				//全部已游玩
				if (singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_USED)!=null) {
					HgLogger.getInstance().info("guotx", "订单【"+ticketOrder.getId()+"】中联票【"+groupTicket.getId()+"】下已全部游玩，修改联票状态为交易成功");
					for (GroupTicketDTO groupTicketDTO : gps) {
						if(groupTicketDTO.getId().equals(groupTicket.getId())){
							groupTicketDTO.getStatus().setCurrent(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_DEAL_SUCC);
							break;
						}
					}
				}
				break;
			case 2:
				//(已游玩、无需退款)||(已游玩、已退款)||(已游玩、已结算)
				if ((singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_USED)!=null
					&& singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_NO_REFUND)!=null)
					||(singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_USED)!=null
							&& singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_REFUNDED)!=null)
					||(singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_USED)!=null
							&& singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_SETTLED)!=null)
					) {
					HgLogger.getInstance().info("guotx", "订单【"+ticketOrder.getId()+"】中联票【"+groupTicket.getId()+"】下单票状态为【(已游玩、无需退款)||(已游玩、已退款)||(已游玩、已结算)】，修改联票状态为交易成功");
					for (GroupTicketDTO groupTicketDTO : gps) {
						if(groupTicketDTO.getId().equals(groupTicket.getId())){
							groupTicketDTO.getStatus().setCurrent(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_DEAL_SUCC);
							break;
						}
					}
				}
				
				break;
			case 3:
				//已游玩、已退款、已结算
				if (singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_USED)!=null
						&& singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_REFUNDED)!=null
						&& singleStatusMap.get(SingleTicketStatus.SINGLE_TICKET_CURRENT_SETTLED)!=null) {
					HgLogger.getInstance().info("guotx", "订单【"+ticketOrder.getId()+"】中联票【"+groupTicket.getId()+"】下单票状态为【已游玩、已退款、已结算】，修改联票状态为交易成功");
					for (GroupTicketDTO groupTicketDTO : gps) {
						if(groupTicketDTO.getId().equals(groupTicket.getId())){
							groupTicketDTO.getStatus().setCurrent(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_DEAL_SUCC);
							break;
						}
					}
				}
				break;
			}	
				
			
		}
		//修改订单状态
		Map<Integer, GroupTicketDTO> gpdtoMap=new HashMap<Integer, GroupTicketDTO>();
		for(GroupTicketDTO gpDto:dto.getGroupTickets()){
			gpdtoMap.put(gpDto.getStatus().getCurrent(), gpDto);
			
		}
		switch (gpdtoMap.size()) {
		case 1:
			//都是交易成功
			if (gpdtoMap.get(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_DEAL_SUCC)!=null) {
				HgLogger.getInstance().info("guotx", "订单【"+ticketOrder.getId()+"】中联票状态全部为交易成功，修改订单状态为交易成功");
				dto.getStatus().setCurrentValue(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_DEAL_SUCC);
			}
			break;

		case 2:
			//交易成功、出票成功||交易成功、订单关闭
			if ((gpdtoMap.get(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_DEAL_SUCC)!=null
					&& gpdtoMap.get(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_OUT_SUCC)!=null)
				|| (gpdtoMap.get(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_DEAL_SUCC)!=null
					&& gpdtoMap.get(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_DEAL_CLOSE)!=null)) {
				HgLogger.getInstance().info("guotx", "订单【"+ticketOrder.getId()+"】中联票状态全部为【交易成功、出票成功||交易成功、订单关闭】，修改订单状态为交易成功");
				dto.getStatus().setCurrentValue(GroupTicketStatusDTO.GROUP_TICKET_CURRENT_DEAL_SUCC);
			}
			break;
		}
	}

	/**
	 * @方法功能说明：转换门票订单Qo
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-27下午3:28:00
	 * @修改内容：
	 * @参数：@param QO
	 * @参数：@param dealerId
	 * @参数：@return
	 * @return:TicketOrderQo
	 * @throws
	 */
	public TicketOrderQo convertQo(TicketOrderQO QO) {
		TicketOrderQo qo = new TicketOrderQo();
		qo.setOrderId(QO.getOrderId());
		qo.setCreateBeginDate(QO.getCreateDateBegin());
		qo.setCreateEndDate(QO.getCreateDateEnd());
		qo.setStatus(QO.getStatus());
		qo.setDealerId(DealerApiAdapter.getRequestDealerId());
		// asc
		qo.setCreateDateSort(1);
		return qo;
	}
}
