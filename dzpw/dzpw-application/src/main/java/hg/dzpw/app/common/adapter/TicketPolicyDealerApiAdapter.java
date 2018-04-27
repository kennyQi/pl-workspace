package hg.dzpw.app.common.adapter;

import hg.common.util.BeanMapperUtils;
import hg.dzpw.app.common.util.ModelUtils;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyBaseInfoDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicySellInfoDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyStatusDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyUseConditionDTO;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：门票政策经销商接口适配器
 * @类修改者：
 * @修改日期：2015-4-24下午4:51:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-24下午4:51:21
 */
public class TicketPolicyDealerApiAdapter {

	/**
	 * @方法功能说明：将门票政策转为DTO
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午4:51:30
	 * @修改内容：
	 * @参数：@param ticketPolicy
	 * @参数：@param QO
	 * @参数：@return
	 * @return:TicketPolicyDTO
	 * @throws
	 */
	public TicketPolicyDTO convertDTO(TicketPolicy ticketPolicy, TicketPolicyQO QO) {
		
		if (ticketPolicy == null)
			return null;
		
		TicketPolicyDTO dto = new TicketPolicyDTO();
		dto.setId(ticketPolicy.getId());
		dto.setBaseInfo(BeanMapperUtils.map(ticketPolicy.getBaseInfo(), TicketPolicyBaseInfoDTO.class));
		dto.setSellInfo(BeanMapperUtils.map(ticketPolicy.getSellInfo(), TicketPolicySellInfoDTO.class));
		dto.setUseInfo(BeanMapperUtils.map(ticketPolicy.getUseInfo(), TicketPolicyUseConditionDTO.class));
		dto.setStatus(BeanMapperUtils.map(ticketPolicy.getStatus(), TicketPolicyStatusDTO.class));
		dto.setParentId(ModelUtils.getId(ticketPolicy.getParent()));
		dto.setScenicSpotId(ModelUtils.getId(ticketPolicy.getScenicSpot()));
		dto.setType(ticketPolicy.getType());
		dto.setVersion(ticketPolicy.getVersion());
		
		// 查询价格日历
		if (QO.getCalendarFetch() != null && QO.getCalendarFetch()) {
			dto.setCalendar(DealerApiAdapter.priceCalendar.convertDTO(ticketPolicy.getPriceCalendar()));
		}
		
		// 查询单票政策
		if (QO.getSingleTicketPoliciesFetch() != null
				&& QO.getSingleTicketPoliciesFetch()) {
			
			List<TicketPolicy> list = ticketPolicy.getSingleTicketPolicies();
			List<TicketPolicyDTO> dtolist = new ArrayList<TicketPolicyDTO>();
			
			if (list != null)
				for (TicketPolicy tp : list)
					dtolist.add(DealerApiAdapter.ticketPolicy.convertDTO(tp, QO));
			
			dto.setSingleTicketPolicies(dtolist);
		}
		
		return dto;
	}

	/**
	 * @方法功能说明：转换门票政策Qo
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午4:52:01
	 * @修改内容：
	 * @参数：@param QO
	 * @参数：@return
	 * @return:TicketPolicyQo
	 * @throws
	 */
	public TicketPolicyQo convertQo(TicketPolicyQO QO) {

		TicketPolicyQo qo = new TicketPolicyQo();

		if (QO == null)
			return qo;

		// 门票政策ID
		if (QO.getTicketPolicyIds() != null && QO.getTicketPolicyIds().length > 0)
			qo.setIds(Arrays.asList(QO.getTicketPolicyIds()));

		// 景区ID
		if (StringUtils.isNotBlank(QO.getScenicSpotId())) {
			ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
			scenicSpotQo.setId(QO.getScenicSpotId());
			qo.setScenicSpotQo(scenicSpotQo);
		}

		// 门票政策类型 1独立单票 2联票
		if (QO.getType() != null)
			qo.setType(QO.getType());

		// 修改时间开始
		qo.setModifyDateBegin(QO.getModifyDateBegin());

		// 修改时间截止
		qo.setModifyDateEnd(QO.getModifyDateEnd());

		// 是否查询门票政策下的单个景区门票政策
		if (QO.getSingleTicketPoliciesFetch() != null)
			qo.setSingleTicketPoliciesFetchAble(QO.getSingleTicketPoliciesFetch());

		qo.setRemoved(false);
		qo.setStatus(TicketPolicyStatus.TICKET_POLICY_STATUS_ISSUE);

		
		// asc
		qo.setModifyDateSort(1);

		return qo;
	}

}
