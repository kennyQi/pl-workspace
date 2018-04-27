package lxs.api.action.mp;

import hg.common.page.Pagination;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.GroupTicketDTO;
import lxs.api.v1.request.qo.mp.GroupTicketQO;
import lxs.api.v1.response.mp.GroupTicketResponse;
import lxs.app.service.mp.GroupTicketService;
import lxs.domain.model.mp.GroupTicket;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryGroupTicketAction")
public class QueryGroupTicketAction implements LxsAction{

	@Autowired
	private GroupTicketService groupTicketService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryGroupTicketAction】");
		GroupTicketQO apigroupTicketQO = JSON.parseObject(apiRequest.getBody().getPayload(), GroupTicketQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QueryGroupTicketAction】【GroupTicketQO】"+JSON.toJSONString(apigroupTicketQO));
		GroupTicketResponse groupTicketResponse = new GroupTicketResponse();
		lxs.pojo.qo.mp.GroupTicketQO groupTicketQO = JSON.parseObject(apiRequest.getBody().getPayload(), lxs.pojo.qo.mp.GroupTicketQO.class);
		try{
			int pageNo = 1;
			int pageSize = 10;
			if(StringUtils.isNotBlank(apigroupTicketQO.getPageNO())){
				pageNo = Integer.valueOf(apigroupTicketQO.getPageNO());
			}
			if(StringUtils.isNotBlank(apigroupTicketQO.getPageSize())){
				pageSize = Integer.valueOf(apigroupTicketQO.getPageSize());
			}
			Pagination pagination = new Pagination();
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setCondition(groupTicketQO);
			pagination=groupTicketService.queryPagination(pagination);
			List<GroupTicket> groupTickets = (List<GroupTicket>) pagination.getList();
			List<GroupTicketDTO> groupTicketDTOs = new ArrayList<GroupTicketDTO>();
			for (GroupTicket groupTicket : groupTickets) {
				GroupTicketDTO groupTicketDTO = new GroupTicketDTO();
				groupTicketDTO.setTicketPolicyID(groupTicket.getId());
				if(groupTicket.getBaseInfo()!=null){
					if(StringUtils.isNotBlank(groupTicket.getBaseInfo().getName())){
						groupTicketDTO.setName(groupTicket.getBaseInfo().getName());
					}
					if(StringUtils.isNotBlank(groupTicket.getBaseInfo().getShortName())){
						groupTicketDTO.setShortName(groupTicket.getBaseInfo().getShortName());
					}
					if(StringUtils.isNotBlank(groupTicket.getBaseInfo().getScenicSpotNameStr())){
						groupTicketDTO.setScenicSpotNameStr(groupTicket.getBaseInfo().getScenicSpotNameStr());
					}
					if(groupTicket.getBaseInfo().getRackRate()!=null){
						groupTicketDTO.setRackRate(groupTicket.getBaseInfo().getRackRate());
					}
					if(groupTicket.getBaseInfo().getPlayPrice()!=null){
						groupTicketDTO.setPlayPrice(groupTicket.getBaseInfo().getPlayPrice());
					}
				}
				if(groupTicket.getUseInfo()!=null){
					groupTicketDTO.setValidDays(groupTicket.getUseInfo().getValidDays());
				}
				if(StringUtils.isNotBlank(groupTicket.getUrl())){
					groupTicketDTO.setUrl(groupTicket.getUrl());
				}
				if(StringUtils.isNotBlank(groupTicket.getScenicSpotID())){
					groupTicketDTO.setScenicSpotID(groupTicket.getScenicSpotID());
				}
				groupTicketDTOs.add(groupTicketDTO);
			}
			
			groupTicketResponse.setGroupTicketDTOs(groupTicketDTOs);
			groupTicketResponse.setPageNO(String.valueOf(pageNo));
			groupTicketResponse.setPageSize(String.valueOf(pageSize));
			double totalPage = Math.ceil(Double.valueOf(pagination.getTotalCount())/ Double.valueOf(pageSize));
			if(totalPage<=pageNo){
				if(totalPage<pageNo){
					groupTicketResponse.setGroupTicketDTOs(null);
				}
				groupTicketResponse.setIsTheLastPage("y");
			}else{
				groupTicketResponse.setIsTheLastPage("n");
			}
			groupTicketResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			groupTicketResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			groupTicketResponse.setMessage("查询联票列表失败");
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryGroupTicketAction】【groupTicketResponse】"+JSON.toJSONString(groupTicketResponse));
		return JSON.toJSONString(groupTicketResponse);
	}
}
