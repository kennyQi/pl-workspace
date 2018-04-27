package zzpl.api.action.jp.gn;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.dto.jp.PassengerDTO;
import zzpl.api.client.dto.order.FlightOrderInfo;
import zzpl.api.client.request.order.FlightOrderInfoQO;
import zzpl.api.client.response.order.FlightOrderInfoResponse;
import zzpl.app.service.local.jp.PassengerService;
import zzpl.app.service.local.jp.gn.FlightOrderService;
import zzpl.app.service.local.user.CostCenterService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.CostCenter;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;

import com.alibaba.fastjson.JSON;

@Component("GetFlightOrderInfoAction")
public class GetFlightOrderInfoAction implements CommonAction {

	@Autowired
	private FlightOrderService flightOrderService;
	@Autowired
	private PassengerService passengerService;
	@Autowired
	private CostCenterService centerService;
	@Override
	public String execute(ApiRequest apiRequest) {
		FlightOrderInfoResponse flightOrderInfoResponse = new FlightOrderInfoResponse();
		try{
			FlightOrderInfoQO flightOrderInfoQO = JSON.parseObject(apiRequest.getBody().getPayload(), FlightOrderInfoQO.class);
			HgLogger.getInstance().info("cs", "【GetFlightOrderInfoAction】"+"flightOrderInfoQO:"+JSON.toJSONString(flightOrderInfoQO));
			if(StringUtils.isNotBlank(flightOrderInfoQO.getOrderID())){
				FlightOrder flightOrder = flightOrderService.get(flightOrderInfoQO.getOrderID());
				if(flightOrder!=null){
					FlightOrderInfo flightOrderInfo = new FlightOrderInfo();
					flightOrderInfo.setAirComp(flightOrder.getAirCompanyName());
					String airIDs = "";
					PassengerQO passengerQO =new PassengerQO() ;
					passengerQO.setFlightOrderID(flightOrder.getId());
					List<Passenger> passengers = passengerService.queryList(passengerQO);
					for (Passenger passenger : passengers) {
						if(passenger.getAirID()!=null&&StringUtils.isNotBlank(passenger.getAirID()))
							airIDs+=passenger.getAirID()+";";
					}
					if(StringUtils.isNotBlank(airIDs)){
						airIDs=airIDs.substring(0, airIDs.length()-1);
					}
					flightOrderInfo.setAirId(airIDs);
					flightOrderInfo.setCabinName(flightOrder.getCabinName());
					flightOrderInfo.setCabinCode(flightOrder.getCabinCode());
					flightOrderInfo.setArrivalAirport(flightOrder.getArrivalAirport());
					flightOrderInfo.setBuildFee(flightOrder.getBuildFee());
					flightOrderInfo.setDepartAirport(flightOrder.getDepartAirport());
					flightOrderInfo.setDstCity(flightOrder.getDstCity());
					flightOrderInfo.setDstCityName(flightOrder.getDstCityName());
					flightOrderInfo.setEndTime(flightOrder.getEndTime());
					flightOrderInfo.setFlightNO(flightOrder.getFlightNO());
					flightOrderInfo.setPlaneType(flightOrder.getPlaneType());
					flightOrderInfo.setLinkEmail(flightOrder.getLinkEmail());
					flightOrderInfo.setLinkName(flightOrder.getLinkName());
					flightOrderInfo.setLinkTelephone(flightOrder.getLinkTelephone());
					flightOrderInfo.setOilFee(flightOrder.getOilFee());
					flightOrderInfo.setOrderID(flightOrder.getId());
					flightOrderInfo.setOrderNO(flightOrder.getOrderNO());
					flightOrderInfo.setOrgCity(flightOrder.getOrgCity());
					flightOrderInfo.setOrgCityName(flightOrder.getOrgCityName());
//				flightOrderInfo.setPassengerList(JSON.parseArray(JSON.toJSONString(passengers), PassengerDTO.class));
					flightOrderInfo.setPassengerList(BeanMapperUtils.getMapper().mapAsList(passengers, PassengerDTO.class));
					flightOrderInfo.setStartTime(flightOrder.getStartTime());
					flightOrderInfo.setStatus(flightOrder.getStatus());
					flightOrderInfo.setIbePrice(flightOrder.getIbePrice());
					flightOrderInfo.setDepartTerm(flightOrder.getDepartTerm());
					flightOrderInfo.setArrivalTerm(flightOrder.getArrivalTerm());
					//为了不改接口 key 现在让手机订单展示 plattotalprice
					if(flightOrder.getPlatTotalPrice()!=null){
						flightOrderInfo.setTotalPrice(flightOrder.getPlatTotalPrice());
					}else{
						flightOrderInfo.setTotalPrice(flightOrder.getCommitPrice());
					}
					//
					flightOrderInfo.setTotalPrice(flightOrder.getPlatTotalPrice());
					flightOrderInfo.setRefundPrice(flightOrder.getRefundPrice());
					flightOrderInfo.setPayStatus(flightOrder.getPayStatus());
					flightOrderInfo.setPayType(flightOrder.getPayType());
					flightOrderInfo.setReplaceBuy(flightOrder.getReplaceBuy());
					CostCenter costCenter = centerService.get(flightOrder.getCostCenterID());
					if(costCenter!=null)
						flightOrderInfo.setCostCenterName(costCenter.getCostCenterName());
					flightOrderInfoResponse.setFlightOrderInfo(flightOrderInfo);
					flightOrderInfoResponse.setMessage("查询成功");
				}
			}else if(StringUtils.isNotBlank(flightOrderInfoQO.getOrderNO())){
				List<FlightOrder> flightOrders = new ArrayList<FlightOrder>();
				if(flightOrderInfoQO.getOrderNO().startsWith("UN_")){
					//如果是多订单合并付款
					FlightOrderQO flightOrderQO = new FlightOrderQO();
					flightOrderQO.setUnionOrderNO(flightOrderInfoQO.getOrderNO());
					flightOrders = flightOrderService.queryList(flightOrderQO);
				}else{
					//只是单独提交
					FlightOrderQO flightOrderQO = new FlightOrderQO();
					flightOrderQO.setOrderNO(flightOrderInfoQO.getOrderNO());
					FlightOrder flightOrder = flightOrderService.queryUnique(flightOrderQO);
					flightOrders.add(flightOrder);
				}
				Double sum = 0.0;
				for(FlightOrder flightOrder:flightOrders){
					sum=sum+flightOrder.getPlatTotalPrice();
				}
				FlightOrderInfo flightOrderInfo = new FlightOrderInfo();
				flightOrderInfo.setOrderNO(flightOrderInfoQO.getOrderNO());
				flightOrderInfo.setTotalPrice(sum);
				flightOrderInfoResponse.setFlightOrderInfo(flightOrderInfo);
				flightOrderInfoResponse.setMessage("查询成功");
			}
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【GetFlightOrderInfoAction】"+"异常，"+HgLogger.getStackTrace(e));
			flightOrderInfoResponse.setResult(GNFlightException.QUERY_FLIGHT_ORDER_INFO_FAILED_CODE);
			flightOrderInfoResponse.setMessage(GNFlightException.QUERY_FLIGHT_ORDER_INFO_FAILED_MESSAGE);
		}
		HgLogger.getInstance().info("cs", "【GetFlightOrderInfoAction】"+"flightOrderInfoResponse:"+JSON.toJSONString(flightOrderInfoResponse));
		return JSON.toJSONString(flightOrderInfoResponse);
	}

}
