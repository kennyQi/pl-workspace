package zzpl.api.action.jp.gn;

import hg.common.page.Pagination;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.order.FlightOrderList;
import zzpl.api.client.request.order.FlightOrderListQO;
import zzpl.api.client.response.order.FlightOrderListResponse;
import zzpl.app.service.local.jp.gn.FlightOrderService;
import zzpl.app.service.local.push.PushService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.qo.jp.FlightOrderQO;

import com.alibaba.fastjson.JSON;
@Component("GetFlightOrderListAction")
public class GetFlightOrderListAction implements CommonAction{

	@Autowired
	private FlightOrderService flightOrderService;
	@Autowired
	private PushService pushService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		FlightOrderListResponse flightOrderListResponse = new FlightOrderListResponse();
		try{
			FlightOrderListQO flightOrderListQO = JSON.parseObject(apiRequest.getBody().getPayload(),FlightOrderListQO.class);
			HgLogger.getInstance().info("cs", "【GetFlightOrderInfoAction】"+"flightOrderListQO:"+JSON.toJSONString(flightOrderListQO));
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setUserID(flightOrderListQO.getUserID());
			flightOrderQO.setOrderByCreatTime("desc");
			if(flightOrderListQO.getStatus()!=null){
				flightOrderQO.setStatus(flightOrderListQO.getStatus());
			}
			Pagination pagination = new Pagination();
			pagination.setCondition(flightOrderQO);
			pagination.setPageNo(flightOrderListQO.getPageNO());
			pagination.setPageSize(flightOrderListQO.getPageSize());
			pagination = flightOrderService.queryPagination(pagination);
			if(flightOrderListQO.getPageNO()<=pagination.getTotalPage()&&pagination!=null&&pagination.getList()!=null){
				List<FlightOrder> flightOrders = (List<FlightOrder>) pagination.getList();
				List<FlightOrderList> flightOrderLists = new ArrayList<FlightOrderList>();
				for(FlightOrder flightOrder:flightOrders){
					FlightOrderList flightOrderList = new FlightOrderList();
					if(StringUtils.isNotBlank(flightOrder.getAirCompanyName())){
						flightOrderList.setAirComp(flightOrder.getAirCompanyName());
					}
					flightOrderList.setArrivalAirport(flightOrder.getArrivalAirport());
					flightOrderList.setArrivalTerm(flightOrder.getArrivalTerm());
					flightOrderList.setDepartAirport(flightOrder.getDepartAirport());
					flightOrderList.setDepartTerm(flightOrder.getDepartTerm());
					flightOrderList.setDstCity(flightOrder.getDstCity());
					flightOrderList.setDstCityName(flightOrder.getDstCityName());
					flightOrderList.setEndTime(flightOrder.getEndTime());
					flightOrderList.setFlightNO(flightOrder.getFlightNO());
					flightOrderList.setOrderID(flightOrder.getId());
					flightOrderList.setOrderNO(flightOrder.getOrderNO());
					flightOrderList.setOrgCity(flightOrder.getOrgCity());
					flightOrderList.setOrgCityName(flightOrder.getOrgCityName());
					flightOrderList.setPlaneType(flightOrder.getPlaneType());
					flightOrderList.setStartTime(flightOrder.getStartTime());
					flightOrderList.setStatus(flightOrder.getStatus());
					//为了不改接口 key 现在让手机订单展示 plattotalprice
					if(flightOrder.getPlatTotalPrice()!=null){
						flightOrderList.setTotalPrice(flightOrder.getPlatTotalPrice());
					}else{
						flightOrderList.setTotalPrice(flightOrder.getCommitPrice());
					}
					flightOrderList.setPayStatus(flightOrder.getPayStatus());
					flightOrderList.setPayType(flightOrder.getPayType());
					flightOrderList.setIbePrice(flightOrder.getCommitPrice().toString());
					flightOrderList.setReplaceBuy(flightOrder.getReplaceBuy());
					flightOrderLists.add(flightOrderList);
				}
				if(flightOrderListQO.getPageNO()==pagination.getTotalPage()){
					flightOrderListResponse.setIsTheLastPage(1);
				}else {
					flightOrderListResponse.setIsTheLastPage(0);
				}
				flightOrderListResponse.setFlightOrderLists(flightOrderLists);
				flightOrderListResponse.setResult(ApiResponse.RESULT_CODE_OK);
				flightOrderListResponse.setMessage("查询成功");
			}
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【GetFlightOrderListAction】"+"异常，"+HgLogger.getStackTrace(e));
			flightOrderListResponse.setResult(GNFlightException.QUERY_FLIGHT_ORDER_LIST_FAILED_CODE);
			flightOrderListResponse.setMessage(GNFlightException.QUERY_FLIGHT_ORDER_LIST_FAILED_MESSAGE);
		}
		HgLogger.getInstance().info("cs", "【GetFlightOrderInfoAction】"+"flightOrderListResponse:"+JSON.toJSONString(flightOrderListResponse));
		return JSON.toJSONString(flightOrderListResponse);
	}

}