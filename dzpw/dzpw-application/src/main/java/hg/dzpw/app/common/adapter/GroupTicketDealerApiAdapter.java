package hg.dzpw.app.common.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import hg.common.util.BeanMapperUtils;
import hg.dzpw.app.common.util.ModelUtils;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.dealer.client.api.v1.request.GroupTicketQO;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketStatusDTO;
import hg.dzpw.dealer.client.dto.ticket.SingleTicketDTO;
import hg.dzpw.dealer.client.dto.ticket.SingleTicketStatusDTO;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.domain.model.ticket.SingleTicket;

public class GroupTicketDealerApiAdapter {
	
	/**
	 * @方法功能说明：转换门票DTO
	 * @修改者名字：yangkang
	 * @修改时间：2015-4-27下午4:50:07
	 * @参数：@param groupTicket
	 * @参数：@param isFetchSingleTicket 是否查询单票信息
	 * @参数：@param isFetchTourist 是否查询游客信息
	 * @return:GroupTicketDTO
	 * @throws
	 */
	public GroupTicketDTO convertDTO(GroupTicket groupTicket,Boolean isFetchSingleTicket,Boolean isFetchTourist){
		
		GroupTicketDTO dto = new GroupTicketDTO();
		
		dto.setUseDateStart(groupTicket.getUseDateStart());
		dto.setUseDateEnd(groupTicket.getUseDateEnd());
		dto.setType(groupTicket.getType());
		dto.setTicketPolicyId(ModelUtils.getId(groupTicket.getTicketPolicy()));
		dto.setTicketOrderId(ModelUtils.getId(groupTicket.getTicketOrder()));
		dto.setTicketNo(groupTicket.getTicketNo());
		dto.setDealerSettlementFee(groupTicket.getDealerSettlementFee());
//		dto.setScenicSpotId(ModelUtils.getId(groupTicket.getScenicSpot()));
		dto.setPrice(groupTicket.getPrice());
		dto.setId(groupTicket.getId());
		// 未支付时 看不到二维码
		if (groupTicket.getStatus().getCurrent()!=GroupTicketStatus.GROUP_TICKET_CURRENT_PAY_WAIT){
			dto.setQrCodeUrl(groupTicket.getQrCodeUrl());
		}
		//组装单票信息
		if (isFetchSingleTicket) {
			List<SingleTicket>singleTickets=groupTicket.getSingleTickets();
			List<SingleTicketDTO> singleTicketDTOs=new ArrayList<SingleTicketDTO>();
			for (SingleTicket singleTicket : singleTickets) {
				SingleTicketDTO singleDto=new SingleTicketDTO();
				singleDto.setScenicSpotId(ModelUtils.getId(singleTicket.getScenicSpot()));
				singleDto.setScenicName(singleTicket.getScenicSpot().getBaseInfo().getName());
				singleDto.setFirstTimeUseDate(singleTicket.getFirstTimeUseDate());
				singleDto.setGroupTicketId(singleTicket.getGroupTicket().getId());
				singleDto.setId(singleTicket.getId());
				singleDto.setStatus(new SingleTicketStatusDTO());
				singleDto.getStatus().setCurrent(singleTicket.getStatus().getCurrent());
				
				if (isFetchTourist && singleTicket.getTourist()!=null) {
					TouristDTO tdto = new TouristDTO();
					Hibernate.initialize(singleTicket.getTourist());
					tdto.setId(singleTicket.getTourist().getId());
					tdto.setName(singleTicket.getTourist().getName());
					tdto.setIdType(singleTicket.getTourist().getIdType());
					tdto.setIdNo(singleTicket.getTourist().getIdNo());
					tdto.setGender(singleTicket.getTourist().getGender());
					tdto.setAge(singleTicket.getTourist().getAge());
					tdto.setName(singleTicket.getTourist().getName());
					tdto.setBirthday(singleTicket.getTourist().getBirthday());
					tdto.setTelephone(singleTicket.getTourist().getTelephone());
					tdto.setNation(singleTicket.getTourist().getNation());
					tdto.setAddress(singleTicket.getTourist().getAddress());
					singleDto.setTourist(tdto);
				}
				
				
				singleTicketDTOs.add(singleDto);
			}
			dto.setSingleTickets(singleTicketDTOs);
		}
		dto.setStatus(BeanMapperUtils.map(groupTicket.getStatus(),GroupTicketStatusDTO.class));
		
		return dto;
	}
	
	
	/**
	 * @方法功能说明：转换门票Qo
	 * @修改者名字：yangkang
	 * @修改时间：2015-4-27下午4:05:51
	 * @参数：@param QO
	 * @参数：@return
	 * @return:List<GroupTicketQo>
	 */
	public List<GroupTicketQo> convertQo(GroupTicketQO QO){
		
		List<GroupTicketQo> list = new ArrayList<GroupTicketQo>();
		
		//订单ID查询门
		if(StringUtils.isNotBlank(QO.getOrderId())){
			
			GroupTicketQo qo = new GroupTicketQo();
			TicketOrderQo tqo = new TicketOrderQo();
			
			tqo.setOrderId(QO.getOrderId());
			qo.setTicketOrdeQo(tqo);
			//关联当前经销商
			qo.getTicketOrdeQo().setDealerId(DealerApiAdapter.getRequestDealerId());
			
//			if (QO.getSingleTicketFetch()) {
//				qo.setSingleTicketQo(new SingleTicketQo());
//			}
//			if(QO.getSingleTicketFetch() && QO.getTouristFetch())
//				qo.getSingleTicketQo().setTourQo(new TouristQo());
//			
			list.add(qo);
		}
		//票号查询门票,订单编号不为空票号失效
		if(StringUtils.isBlank(QO.getOrderId()) && QO.getTicketNos()!=null && QO.getTicketNos().length>0){
			for(String ticketNo : QO.getTicketNos()){
				GroupTicketQo qo = new GroupTicketQo();
				qo.setTicketNo(ticketNo);
				qo.setTicketNoLike(false);
				//关联当前经销商
				qo.setTicketOrdeQo(new TicketOrderQo());
				qo.getTicketOrdeQo().setDealerId(DealerApiAdapter.getRequestDealerId());
//				if(QO.getTouristFetch())
//					qo.setTourQo(new TouristQo());
				
				list.add(qo);
			}
		}
		return list;
	}
	
}
