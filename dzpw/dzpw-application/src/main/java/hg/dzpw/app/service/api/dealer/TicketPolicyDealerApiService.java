package hg.dzpw.app.service.api.dealer;

import hg.common.page.Pagination;
import hg.dzpw.app.common.adapter.DealerApiAdapter;
import hg.dzpw.app.component.manager.DealerCacheManager;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.TicketPolicyPriceCalendarLocalService;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.common.api.DealerApiAction;
import hg.dzpw.dealer.client.common.api.DealerApiService;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDTO;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：面向经销商的门票政策服务
 * @类修改者：
 * @修改日期：2014-12-5下午4:00:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzx
 * @创建时间：2014-12-5下午4:00:07
 */
@Service
public class TicketPolicyDealerApiService implements DealerApiService{
	
	@Autowired
	private TicketPolicyLocalService ticketPolicyLocalService;
	@Autowired
	private DealerCacheManager dealerCacheManager;
	@Autowired
	private TicketPolicyPriceCalendarLocalService calendarLocalService;
	
	/**
	 * @方法功能说明：门票政策查询
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午4:51:06
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:TicketPolicyResponse
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@DealerApiAction(DealerApiAction.QueryTicketPolicy)
	public TicketPolicyResponse queryTicketPolicys(ApiRequest<TicketPolicyQO> request) {
		
		//返回实体
		TicketPolicyResponse response = new TicketPolicyResponse();
		//请求参数
		TicketPolicyQO QO = request.getBody();
		
		//查询对应的经销商ID
		String dealerId = dealerCacheManager.getDealerId(request.getHeader().getDealerKey());

		if (StringUtils.isBlank(dealerId)) {
			response.setMessage("无效经销商代码");
			response.setResult(TicketPolicyResponse.RESULT_DEALER_KEY_ERROR);
			return response;
		}

		if (QO.getType() != null && (QO.getType() > 2 || QO.getType() < 1)) {
			response.setMessage("[参数type]未通过校验");
			response.setResult(TicketPolicyResponse.RESULT_CHECK_FAIL);
			return response;
		}
		
		// 分页查询
//		Pagination pagination = ticketPolicyLocalService.queryPagination(DealerApiAdapter.convertPaginationCondition(QO));
		Pagination pagination = ticketPolicyLocalService.queryPaginationApi(DealerApiAdapter.convertPaginationCondition(QO));

		// 门票政策列表
		List<TicketPolicy> list = pagination.getTotalCount() > 0 ? (List<TicketPolicy>) pagination.getList() : new ArrayList<TicketPolicy>();

		// 价格政策DTO列表
		List<TicketPolicyDTO> ticketPolicies = new ArrayList<TicketPolicyDTO>();
		
		// 设置价格日历
		if (QO.getCalendarFetch() != null && QO.getCalendarFetch() && pagination.getTotalCount() > 0) {
			// 价格日历MAP
			String[] ticketPolicyIds = new String[list.size()];
			for (int i = 0; i < list.size(); i++)
				ticketPolicyIds[i] = list.get(i).getId();
			Map<String, TicketPolicyPriceCalendar> calendarMap = calendarLocalService.getTicketPolicyPriceCalendarMap(dealerId, ticketPolicyIds);
			for (TicketPolicy ticketPolicy : list)
				ticketPolicy.setPriceCalendar(calendarMap.get(ticketPolicy.getId()));
		}

		// 转换成DTO
		for (TicketPolicy ticketPolicy : list)
			ticketPolicies.add(DealerApiAdapter.ticketPolicy.convertDTO(ticketPolicy, QO));

		response.setResult(TicketPolicyResponse.RESULT_SUCCESS);
		response.setTicketPolicies(ticketPolicies);
		response.setPageNo(pagination.getPageNo());
		response.setPageSize(pagination.getPageSize());
		response.setTotalCount(pagination.getTotalCount());
		
		return response;
	}
	
}
