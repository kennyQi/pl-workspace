package hg.dzpw.app.service.api.dealer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.dzpw.app.common.adapter.DealerApiAdapter;
import hg.dzpw.app.common.adapter.ScenicSpotDealerApiAdapter;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.UseRecordQo;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.UseRecordLocalService;
import hg.dzpw.dealer.client.api.v1.request.UseRecordQO;
import hg.dzpw.dealer.client.api.v1.response.UseRecordResponse;
import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.common.api.DealerApiAction;
import hg.dzpw.dealer.client.common.api.DealerApiService;
import hg.dzpw.dealer.client.dto.useRecord.UseRecordDTO;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.UseRecord;


/**
 * @类功能说明：经销商接口--入园记录查询
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-5-25上午10:20:17
 * @版本：V1.0
 *
 */
@Service
public class UseRecordDealerApiService implements DealerApiService{
	
	@Autowired
	private UseRecordLocalService useRecordLocalService;
	
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	
	
	@Transactional(readOnly = true)
	@DealerApiAction(DealerApiAction.QueryUseRecord)
	public UseRecordResponse queryUseRecord(ApiRequest<UseRecordQO> request){
		
		UseRecordResponse resp = new UseRecordResponse();
		UseRecordQO urQo = request.getBody();
		
		try {
			// 查询对应的经销商ID
			String dealerId = DealerApiAdapter.getDealerId(request.getHeader().getDealerKey());
			
			if (StringUtils.isBlank(dealerId)) {
				resp.setMessage("无效经销商代码");
				resp.setResult(UseRecordResponse.RESULT_DEALER_KEY_ERROR);
				return resp;
			}
			
			if(StringUtils.isBlank(urQo.getTicketNo())){
				resp.setMessage("缺少必填参数[ticketNo]");
				resp.setResult(UseRecordResponse.RESULT_REQUIRED_PARAM);
				return resp;
			}
			
			GroupTicketQo gtQo = new GroupTicketQo();
			gtQo.setTicketNo(urQo.getTicketNo());
			gtQo.setTicketNoLike(false);
			GroupTicket gt = groupTicketLocalService.queryUnique(gtQo);
			
			if(gt == null){
				resp.setMessage("票号["+urQo.getTicketNo()+"]不存在");
				resp.setResult(UseRecordResponse.RESULT_TICKET_NO_NOT_EXISTS);
				return resp;
			}
			
			UseRecordQo qo = new UseRecordQo();
			qo.setGroupTicketId(gt.getId());
			
			List<UseRecord> list = this.useRecordLocalService.queryList(qo);
			
			if(list!=null && list.size()>0){
				resp.setUseRecords(copyToUseRecordDTO(list, urQo.getTicketNo()));
			}
			resp.setResult(UseRecordResponse.RESULT_SUCCESS);
			resp.setMessage("查询成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResult(UseRecordResponse.RESULT_ERROR);
			resp.setMessage("系统异常");
		}
		
		return resp;
	}
	
	
	private List<UseRecordDTO> copyToUseRecordDTO(List<UseRecord> list, String ticketNo){
		
		List<UseRecordDTO> l = new ArrayList<UseRecordDTO>();
		List<String> ids = new ArrayList<String>();
		
		for(UseRecord ur1 : list)
			ids.add(ur1.getScenicSpotId());
			
		ScenicSpotQo qo = new ScenicSpotQo();
		qo.setIds(ids);
		List<ScenicSpot> ls = scenicSpotLocalService.queryList(qo);
		
		for(UseRecord ur : list){
			UseRecordDTO dto = new UseRecordDTO();
			dto.setCheckType(ur.getCheckType());
			dto.setGroupTicketId(ur.getGroupTicketId());
			dto.setScenicSpotId(ur.getScenicSpotId());
			dto.setSingleTicketId(ur.getSingleTicketId());
			dto.setTicketNo(ticketNo);
			dto.setUseDate(ur.getUseDate());
			for(ScenicSpot ss : ls){
				if(ur.getScenicSpotId().equals(ss.getId())){
//					dto.setScenicSpot(ScenicSpotDealerApiAdapter.convertDTO(ss));
					dto.setScenicName(ss.getBaseInfo().getName());
				}
			}
			l.add(dto);
		}
		
		return l;
	}
	
}
