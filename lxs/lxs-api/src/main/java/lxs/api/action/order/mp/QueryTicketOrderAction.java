package lxs.api.action.order.mp;


import hg.common.page.Pagination;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.order.mp.TicketOrderDTO;
import lxs.api.v1.request.qo.order.mp.TicketOrderQO;
import lxs.api.v1.response.order.mp.TicketOrderResponse;
import lxs.app.service.mp.TicketOrderService;
import lxs.domain.model.mp.TicketOrder;
import lxs.pojo.exception.mp.QueryTicketOrderException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryTicketOrderAction")
public class QueryTicketOrderAction implements LxsAction{

	@Autowired
	private TicketOrderService ticketOrderService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryTicketOrderAction】");
		TicketOrderQO apiTicketOrderQO = JSON.parseObject(apiRequest.getBody().getPayload(), TicketOrderQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QueryTicketOrderAction】【apiTicketOrderQO】"+JSON.toJSONString(apiTicketOrderQO));
		lxs.pojo.qo.mp.TicketOrderQO ticketOrderQO = JSON.parseObject(apiRequest.getBody().getPayload(), lxs.pojo.qo.mp.TicketOrderQO.class);
		TicketOrderResponse ticketOrderResponse = new TicketOrderResponse();
		try{
			if(StringUtils.isBlank(apiTicketOrderQO.getUserID())){
				throw new QueryTicketOrderException(QueryTicketOrderException.PARAMETER_ILLEGAL);
			}
			int pageNo = 1;
			int pageSize = 10;
			if(StringUtils.isNotBlank(apiTicketOrderQO.getPageNO())){
				pageNo = Integer.valueOf(apiTicketOrderQO.getPageNO());
			}
			if(StringUtils.isNotBlank(apiTicketOrderQO.getPageSize())){
				pageSize = Integer.valueOf(apiTicketOrderQO.getPageSize());
			}
			Pagination pagination = new Pagination();
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setCondition(ticketOrderQO);
			pagination=ticketOrderService.queryPagination(pagination);
			List<TicketOrder> ticketOrders = (List<TicketOrder>) pagination.getList();
			List<TicketOrderDTO> ticketOrderDTOs = new ArrayList<TicketOrderDTO>();
			for (TicketOrder ticketOrder : ticketOrders) {
				TicketOrderDTO ticketOrderDTO = JSON.parseObject(JSON.toJSONString(ticketOrder),TicketOrderDTO.class);
				ticketOrderDTO.setOrderID(ticketOrder.getId());
				ticketOrderDTO.setId(null);
				ticketOrderDTOs.add(ticketOrderDTO);
			}
			ticketOrderResponse.setTicketOrderDTOs(ticketOrderDTOs);
			ticketOrderResponse.setPageNO(String.valueOf(pageNo));
			ticketOrderResponse.setPageSize(String.valueOf(pageSize));
			double totalPage = Math.ceil(Double.valueOf(pagination.getTotalCount())/ Double.valueOf(pageSize));
			if(totalPage<=pageNo){
				if(totalPage<pageNo){
					ticketOrderResponse.setTicketOrderDTOs(null);
				}
				ticketOrderResponse.setIsTheLastPage("y");
			}else{
				ticketOrderResponse.setIsTheLastPage("n");
			}
			ticketOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			String message = "";
			if(e instanceof QueryTicketOrderException){
				message=e.getMessage();
			}else{
				message="查询门票订单列表失败";
			}
			ticketOrderResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			ticketOrderResponse.setMessage(message);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryTicketOrderAction】【ticketOrderResponse】"+JSON.toJSONString(ticketOrderResponse));
		return JSON.toJSONString(ticketOrderResponse);
	}

}
