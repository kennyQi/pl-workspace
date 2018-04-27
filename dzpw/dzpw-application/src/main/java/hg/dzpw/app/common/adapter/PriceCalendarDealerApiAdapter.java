package hg.dzpw.app.common.adapter;

import hg.common.util.BeanMapperUtils;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDatePriceDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyPriceCalendarDTO;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;

/**
 * @类功能说明：价格日历经销商接口适配器
 * @类修改者：
 * @修改日期：2015-4-27下午4:46:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-27下午4:46:22
 */
public class PriceCalendarDealerApiAdapter {

	/**
	 * @方法功能说明：将价格日历转为DTO
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午4:51:46
	 * @修改内容：
	 * @参数：@param calendar
	 * @参数：@return
	 * @return:TicketPolicyPriceCalendarDTO
	 * @throws
	 */
	public TicketPolicyPriceCalendarDTO convertDTO(TicketPolicyPriceCalendar calendar) {
	
		if (calendar == null)
			return null;
		
		TicketPolicyPriceCalendarDTO dto = new TicketPolicyPriceCalendarDTO();
		dto.setId(calendar.getId());
		dto.setPrices(BeanMapperUtils.getMapper().mapAsList(
				calendar.getPrices(), TicketPolicyDatePriceDTO.class));
		
		return dto;
	}

}
