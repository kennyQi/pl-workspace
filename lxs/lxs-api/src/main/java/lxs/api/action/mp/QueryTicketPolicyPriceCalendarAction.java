package lxs.api.action.mp;

import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDatePriceDTO;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.qo.mp.TicketPolicyPriceCalendarQO;
import lxs.api.v1.response.mp.TicketPolicyPriceCalendarResponse;
import lxs.app.service.mp.DZPWService;
import lxs.pojo.exception.mp.DZPWException;
import lxs.pojo.exception.mp.QueryTicketOrderInfoException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryTicketPolicyPriceCalendarAction")
public class QueryTicketPolicyPriceCalendarAction implements LxsAction{

	@Autowired
	private DZPWService dzpwService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryTicketPolicyPriceCalendarAction】");
		TicketPolicyPriceCalendarQO ticketPolicyPriceCalendarQO = JSON.parseObject(apiRequest.getBody().getPayload(),TicketPolicyPriceCalendarQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QueryTicketPolicyPriceCalendarAction】【ticketPolicyPriceCalendarQO】"+JSON.toJSONString(ticketPolicyPriceCalendarQO));
		TicketPolicyPriceCalendarResponse ticketPolicyPriceCalendarResponse = new TicketPolicyPriceCalendarResponse();
		try{
			if(StringUtils.isBlank(ticketPolicyPriceCalendarQO.getTicketPolicyID())){
				throw new QueryTicketOrderInfoException(QueryTicketOrderInfoException.PARAMETER_ILLEGAL);
			}
			String[] ticketPolicyIds = {ticketPolicyPriceCalendarQO.getTicketPolicyID()};
			TicketPolicyQO ticketPolicyQO = new TicketPolicyQO();
			ticketPolicyQO.setCalendarFetch(true);
			ticketPolicyQO.setTicketPolicyIds(ticketPolicyIds);
			TicketPolicyResponse ticketPolicyResponse = dzpwService.queryTicketPolicy(ticketPolicyQO);
			List<lxs.api.v1.dto.mp.TicketPolicyDatePriceDTO> ticketPolicyDatePriceDTOs= new ArrayList<lxs.api.v1.dto.mp.TicketPolicyDatePriceDTO>();
			if(ticketPolicyResponse!=null&&ticketPolicyResponse.getTicketPolicies().size()!=0&&ticketPolicyResponse.getTicketPolicies().get(0)!=null&&ticketPolicyResponse.getTicketPolicies().get(0).getCalendar()!=null&&ticketPolicyResponse.getTicketPolicies().get(0).getCalendar().getPrices()!=null){
				List<TicketPolicyDatePriceDTO> dzpwTicketPolicyDatePriceDTOs =  ticketPolicyResponse.getTicketPolicies().get(0).getCalendar().getPrices();
				for (TicketPolicyDatePriceDTO dzpwTicketPolicyDatePriceDTO : dzpwTicketPolicyDatePriceDTOs) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
					String today = simpleDateFormat.format(new Date());
					if(Integer.valueOf(dzpwTicketPolicyDatePriceDTO.getDate())>=Integer.valueOf(today)){
						lxs.api.v1.dto.mp.TicketPolicyDatePriceDTO  ticketPolicyDatePriceDTO = JSON.parseObject(JSON.toJSONString(dzpwTicketPolicyDatePriceDTO),lxs.api.v1.dto.mp.TicketPolicyDatePriceDTO.class);
						ticketPolicyDatePriceDTOs.add(ticketPolicyDatePriceDTO);
					}
				}
			}
			ticketPolicyPriceCalendarResponse.setTicketPolicyDatePriceDTOs(ticketPolicyDatePriceDTOs);
			ticketPolicyPriceCalendarResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			String message = "";
			if(e instanceof DZPWException||e instanceof QueryTicketOrderInfoException){
				message=e.getMessage();
			}else{
				message="查询门票价格日历失败";
			}
			ticketPolicyPriceCalendarResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			ticketPolicyPriceCalendarResponse.setMessage(message);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryTicketPolicyPriceCalendarAction】【ticketPolicyPriceCalendarResponse】"+JSON.toJSONString(ticketPolicyPriceCalendarResponse));
		return JSON.toJSONString(ticketPolicyPriceCalendarResponse);
	}

}
