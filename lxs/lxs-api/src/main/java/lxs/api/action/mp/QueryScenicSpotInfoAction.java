package lxs.api.action.mp;

import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDTO;
import hg.dzpw.dealer.client.dto.policy.TicketPolicyDatePriceDTO;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.ScenicSpotPicDTO;
import lxs.api.v1.request.qo.mp.ScenicSpotInfoQO;
import lxs.api.v1.response.mp.ScenicSpotInfoResponse;
import lxs.app.service.mp.DZPWService;
import lxs.app.service.mp.ScenicSpotPicService;
import lxs.app.service.mp.ScenicSpotService;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.ScenicSpotPic;
import lxs.pojo.exception.mp.DZPWException;
import lxs.pojo.exception.mp.QueryScenicSpotInfoException;
import lxs.pojo.qo.mp.ScenicSpotPicQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryScenicSpotInfoAction")
public class QueryScenicSpotInfoAction implements LxsAction{

	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ScenicSpotPicService scenicSpotPicService;
	@Autowired
	private DZPWService dzpwService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryScenicSpotInfoAction】");
		ScenicSpotInfoQO scenicSpotInfoQO = JSON.parseObject(apiRequest.getBody().getPayload(), ScenicSpotInfoQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QueryScenicSpotInfoAction】【scenicSpotInfoQO】"+JSON.toJSONString(scenicSpotInfoQO));
		ScenicSpotInfoResponse scenicSpotInfoResponse = new ScenicSpotInfoResponse();
		try{
			if(StringUtils.isBlank(scenicSpotInfoQO.getScenicSpotID())){
				throw new QueryScenicSpotInfoException(QueryScenicSpotInfoException.PARAMETER_ILLEGAL);
			}
			ScenicSpot scenicSpot = scenicSpotService.get(scenicSpotInfoQO.getScenicSpotID());
			if(scenicSpot!=null){
				scenicSpotInfoResponse.setScenicSpotID(scenicSpot.getId());
				if(scenicSpot.getBaseInfo()!=null){
					if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getName())){
						scenicSpotInfoResponse.setName(scenicSpot.getBaseInfo().getName());
					}
					if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getShortName())){
						scenicSpotInfoResponse.setShortName(scenicSpot.getBaseInfo().getShortName());
					}
					if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getOpenTime())){
						scenicSpotInfoResponse.setOpenTime(scenicSpot.getBaseInfo().getOpenTime());
					}
					if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getFeature())){
						scenicSpotInfoResponse.setIntro(scenicSpot.getBaseInfo().getFeature());
					}
					//查询景区图片
					ScenicSpotPicQO scenicSpotPicQO = new ScenicSpotPicQO();
					scenicSpotPicQO.setScenicSpotID(scenicSpot.getId());
					List<ScenicSpotPic> scenicSpotPics = scenicSpotPicService.queryList(scenicSpotPicQO);
					List<ScenicSpotPicDTO> scenicSpotPicDTOs = new ArrayList<ScenicSpotPicDTO>();
					for (ScenicSpotPic scenicSpotPic : scenicSpotPics) {
						ScenicSpotPicDTO scenicSpotPicDTO = JSON.parseObject(JSON.toJSONString(scenicSpotPic), ScenicSpotPicDTO.class);
						scenicSpotPicDTOs.add(scenicSpotPicDTO);
					}
					scenicSpotInfoResponse.setScenicSpotPicDTOs(scenicSpotPicDTOs);
					//查询政策
					TicketPolicyQO ticketPolicyQO = new TicketPolicyQO();
					ticketPolicyQO.setCalendarFetch(true);
					ticketPolicyQO.setScenicSpotId(scenicSpot.getId());
					TicketPolicyResponse ticketPolicyResponse = dzpwService.queryTicketPolicy(ticketPolicyQO);
					List<TicketPolicyDTO> dzpwTicketPolicyDTOs = ticketPolicyResponse.getTicketPolicies();
					List<lxs.api.v1.dto.mp.TicketPolicyDTO>  ticketPolicyDTOs = new ArrayList<lxs.api.v1.dto.mp.TicketPolicyDTO>();
					for (TicketPolicyDTO dzpwTicketPolicyDTO : dzpwTicketPolicyDTOs) {
						lxs.api.v1.dto.mp.TicketPolicyDTO ticketPolicyDTO = new lxs.api.v1.dto.mp.TicketPolicyDTO();
						ticketPolicyDTO.setTicketPolicyID(dzpwTicketPolicyDTO.getId());
						ticketPolicyDTO.setType(dzpwTicketPolicyDTO.getType());
						ticketPolicyDTO.setVersion(dzpwTicketPolicyDTO.getVersion());
						if(dzpwTicketPolicyDTO.getBaseInfo()!=null){
							if(StringUtils.isNotBlank(dzpwTicketPolicyDTO.getBaseInfo().getName())){
								ticketPolicyDTO.setName(dzpwTicketPolicyDTO.getBaseInfo().getName());
							}
							if(dzpwTicketPolicyDTO.getBaseInfo().getRackRate()!=null){
								ticketPolicyDTO.setRackRate(dzpwTicketPolicyDTO.getBaseInfo().getRackRate());
							}
							if(dzpwTicketPolicyDTO.getBaseInfo().getScenicSpotNameStr()!=null){
								ticketPolicyDTO.setScenicSpotNameStr(dzpwTicketPolicyDTO.getBaseInfo().getScenicSpotNameStr());
							}
							if(dzpwTicketPolicyDTO.getCalendar()!=null&&dzpwTicketPolicyDTO.getCalendar().getPrices()!=null){
								double playPrice = 0.0;
								if(ticketPolicyDTO.getType()==TicketPolicyDTO.TICKET_POLICY_TYPE_SINGLE){
									//如果是单票 查询价格日历
									List<TicketPolicyDatePriceDTO> ticketPolicyDatePriceDTOs = dzpwTicketPolicyDTO.getCalendar().getPrices();
									double theFirstMinPlayPrice = 0.0;
									boolean flag = true;
									for (TicketPolicyDatePriceDTO ticketPolicyDatePriceDTO : ticketPolicyDatePriceDTOs) {
										if(flag){
											theFirstMinPlayPrice=ticketPolicyDatePriceDTO.getPrice();
											flag=false;
										}else if(ticketPolicyDatePriceDTO.getPrice()<theFirstMinPlayPrice){
											theFirstMinPlayPrice=ticketPolicyDatePriceDTO.getPrice();
										}
									}
									if(theFirstMinPlayPrice!=0.0){
										playPrice=theFirstMinPlayPrice;
									}
								}else{
									//如果是联票 直接从政策里获取
									if(dzpwTicketPolicyDTO.getBaseInfo().getPlayPrice()!=null){
										playPrice=dzpwTicketPolicyDTO.getBaseInfo().getPlayPrice();
									}
								}
								ticketPolicyDTO.setPlayPrice(playPrice);
							}
							if(StringUtils.isNotBlank(dzpwTicketPolicyDTO.getBaseInfo().getIntro())){
								ticketPolicyDTO.setIntro(dzpwTicketPolicyDTO.getBaseInfo().getIntro());
							}
							if(StringUtils.isNotBlank(dzpwTicketPolicyDTO.getBaseInfo().getNotice())){
								ticketPolicyDTO.setNotice(dzpwTicketPolicyDTO.getBaseInfo().getNotice());
							}
							if(StringUtils.isNotBlank(dzpwTicketPolicyDTO.getBaseInfo().getScenicSpotNameStr())){
								scenicSpotInfoResponse.setScenicSpotNameStr(dzpwTicketPolicyDTO.getBaseInfo().getScenicSpotNameStr());
								
							}
						}
						if(dzpwTicketPolicyDTO.getSellInfo()!=null){
							if(dzpwTicketPolicyDTO.getSellInfo().getReturnCost()!=null){
								ticketPolicyDTO.setReturnCost(dzpwTicketPolicyDTO.getSellInfo().getReturnCost());
							}
							if(dzpwTicketPolicyDTO.getSellInfo().getReturnRule()!=null){
								ticketPolicyDTO.setReturnRule(dzpwTicketPolicyDTO.getSellInfo().getReturnRule());
							}
							if(dzpwTicketPolicyDTO.getSellInfo().getAutoMaticRefund()!=null){
								ticketPolicyDTO.setAutoMaticRefund(dzpwTicketPolicyDTO.getSellInfo().getAutoMaticRefund());
							}
						}
						if(dzpwTicketPolicyDTO.getUseInfo()!=null&&dzpwTicketPolicyDTO.getUseInfo().getValidDays()!=null){
							ticketPolicyDTO.setValidDays(dzpwTicketPolicyDTO.getUseInfo().getValidDays());
						}
						ticketPolicyDTOs.add(ticketPolicyDTO);
					}
					scenicSpotInfoResponse.setTicketPolicyDTOs(ticketPolicyDTOs);
				}
			}
			scenicSpotInfoResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			String message = "";
			if(e instanceof QueryScenicSpotInfoException||e instanceof DZPWException){
				message=e.getMessage();
			}else{
				message="查询景区详情失败";
			}
			scenicSpotInfoResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			scenicSpotInfoResponse.setMessage(message);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryScenicSpotInfoAction】【scenicSpotInfoResponse】"+JSON.toJSONString(scenicSpotInfoResponse));
		return JSON.toJSONString(scenicSpotInfoResponse);
	}

}
