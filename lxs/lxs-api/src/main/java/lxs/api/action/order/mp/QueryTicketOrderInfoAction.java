package lxs.api.action.order.mp;

import hg.dzpw.dealer.client.api.v1.request.UseRecordQO;
import hg.dzpw.dealer.client.api.v1.response.UseRecordResponse;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.TouristDTO;
import lxs.api.v1.dto.mp.UseRecordDTO;
import lxs.api.v1.dto.order.mp.TicketOrderInfoDTO;
import lxs.api.v1.request.qo.order.mp.TicketOrderInfoQO;
import lxs.api.v1.response.order.mp.TicketOrderInfoResponse;
import lxs.app.service.mp.DZPWService;
import lxs.app.service.mp.TicketOrderService;
import lxs.app.service.mp.TouristService;
import lxs.domain.model.mp.TicketOrder;
import lxs.domain.model.mp.Tourist;
import lxs.pojo.exception.mp.QueryTicketOrderInfoException;
import lxs.pojo.qo.mp.TouristQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryTicketOrderInfoAction")
public class QueryTicketOrderInfoAction implements LxsAction{

	@Autowired
	private TicketOrderService ticketOrderService;
	@Autowired
	private TouristService touristService;
	@Autowired
	private DZPWService dzpwService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryTicketOrderInfoAction】");
		TicketOrderInfoQO ticketOrderInfoQO = JSON.parseObject(apiRequest.getBody().getPayload(), TicketOrderInfoQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QueryTicketOrderInfoAction】【ticketOrderInfoQO】"+JSON.toJSONString(ticketOrderInfoQO));
		TicketOrderInfoResponse ticketOrderInfoResponse = new TicketOrderInfoResponse();
		try{
			if(StringUtils.isBlank(ticketOrderInfoQO.getOrderID())){
				throw new QueryTicketOrderInfoException(QueryTicketOrderInfoException.PARAMETER_ILLEGAL);
			}
			TicketOrder ticketOrder = ticketOrderService.get(ticketOrderInfoQO.getOrderID());
			TicketOrderInfoDTO ticketOrderInfoDTO = new TicketOrderInfoDTO();
			if(ticketOrder!=null){
				ticketOrderInfoDTO = JSON.parseObject(JSON.toJSONString(ticketOrder),TicketOrderInfoDTO.class);
				ticketOrderInfoDTO.setOrderID(ticketOrder.getId());
				TouristQO touristQO = new TouristQO();
				touristQO.setOrderID(ticketOrder.getId());
				List<Tourist> tourists = touristService.queryList(touristQO);
				List<TouristDTO> touristDTOs = new ArrayList<TouristDTO>();
				for (Tourist tourist : tourists) {
					TouristDTO touristDTO = JSON.parseObject(JSON.toJSONString(tourist),TouristDTO.class);
					List<UseRecordDTO> useRecordDTOs = new ArrayList<UseRecordDTO>();
					if(StringUtils.isNotBlank(tourist.getTicketNo())){
						UseRecordQO useRecordQO = new UseRecordQO();
						useRecordQO.setTicketNo(tourist.getTicketNo());
						UseRecordResponse useRecordResponse = dzpwService.queryUseRecord(useRecordQO);
						if(useRecordResponse!=null&&useRecordResponse.getUseRecords()!=null&&useRecordResponse.getUseRecords().size()!=0){
							for (hg.dzpw.dealer.client.dto.useRecord.UseRecordDTO dzpwUseRecordDTO : useRecordResponse.getUseRecords()) {
								UseRecordDTO useRecordDTO = JSON.parseObject(JSON.toJSONString(dzpwUseRecordDTO),UseRecordDTO.class);
								useRecordDTOs.add(useRecordDTO);
							}
						}
					}
					touristDTO.setUseRecordDTOs(useRecordDTOs);
					touristDTOs.add(touristDTO);
				}
				ticketOrderInfoDTO.setTouristDTOs(touristDTOs);
			}
			ticketOrderInfoResponse.setTicketOrderInfoDTO(ticketOrderInfoDTO);
			ticketOrderInfoResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			String message = "";
			if(e instanceof QueryTicketOrderInfoException){
				message=e.getMessage();
			}else{
				message="查询门票订单详情失败";
			}
			ticketOrderInfoResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			ticketOrderInfoResponse.setMessage(message);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryTicketOrderInfoAction】【ticketOrderInfoResponse】"+JSON.toJSONString(ticketOrderInfoResponse));
		return JSON.toJSONString(ticketOrderInfoResponse);
	}

}
