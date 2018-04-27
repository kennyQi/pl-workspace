package hg.dzpw.app.service.api.dealer;

import hg.dzpw.app.common.adapter.DealerApiAdapter;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.dealer.client.api.v1.request.GroupTicketQO;
import hg.dzpw.dealer.client.api.v1.response.GroupTicketResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketOrderResponse;
import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.common.api.DealerApiAction;
import hg.dzpw.dealer.client.common.api.DealerApiService;
import hg.dzpw.dealer.client.dto.ticket.GroupTicketDTO;
import hg.dzpw.domain.model.ticket.GroupTicket;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：门票服务
 * @类修改者：
 * @修改日期：2015-4-23下午6:13:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-23下午6:13:03
 */
@Service
public class GroupTicketDealerApiService implements DealerApiService {
	
	@Autowired
	private GroupTicketLocalService groupTicketService;
	
	/**
	 * @方法功能说明：查询门票
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-23下午6:12:46
	 * @参数：@param request
	 * @参数：@return
	 * @return:GroupTicketResponse
	 * @throws
	 */
	@DealerApiAction(DealerApiAction.QueryGroupTicket)
	@Transactional(readOnly=true)
	public GroupTicketResponse queryGroupTickets(ApiRequest<GroupTicketQO> request){
		
		GroupTicketResponse response = new GroupTicketResponse();
		try {
			
			// 查询对应的经销商ID
			String dealerId = DealerApiAdapter.getDealerId(request.getHeader().getDealerKey());
			if (StringUtils.isBlank(dealerId)) {
				response.setMessage("无效经销商代码");
				response.setResult(TicketOrderResponse.RESULT_DEALER_KEY_ERROR);
				return response;
			}
			
			GroupTicketQO QO = request.getBody();
			List<GroupTicketQo> qoList = DealerApiAdapter.groupTicket.convertQo(QO);
			List<GroupTicket> ticketList = new ArrayList<GroupTicket>();
			List<GroupTicketDTO> dtoList = new ArrayList<GroupTicketDTO>();
			
			for(GroupTicketQo qo : qoList){
				List<GroupTicket> l = this.groupTicketService.queryList(qo);
				for(GroupTicket gt : l){
					ticketList.add(gt);
				}
			}
			
			for(GroupTicket gt : ticketList){
				dtoList.add(DealerApiAdapter.groupTicket.convertDTO(gt,QO.getSingleTicketFetch(),QO.getTouristFetch()));
			}
			
			response.setGroupTickets(dtoList);
			response.setMessage("成功");
			response.setResult(GroupTicketResponse.RESULT_SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("系统异常");
			response.setResult(GroupTicketResponse.RESULT_ERROR);
		}
		return response;
	}
	
}
