package zzpl.api.action.jp.gj;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.dto.jp.GJFlightDTO;
import zzpl.api.client.dto.jp.GJFlightListDTO;
import zzpl.api.client.request.jp.GJFlightQO;
import zzpl.api.client.response.jp.GJFlightResponse;
import zzpl.app.service.local.jp.gj.GJFlightService;
import zzpl.pojo.dto.jp.plfx.gj.FlightGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.GJAvailableFlightGroupDTO;
import zzpl.pojo.dto.jp.plfx.gj.GJFlightCabinDTO;
import zzpl.pojo.exception.jp.GJFlightException;
import zzpl.pojo.qo.jp.plfx.gj.JPFlightGJQO;

import com.alibaba.fastjson.JSON;

@Component("QueryGJFlightAction")
public class QueryGJFlightAction implements CommonAction{

	@Autowired
	private GJFlightService gjFlightService;
	
	/**
	 * 成人舱位
	 */
	public final static String ADT = "ADT";
	
	@Override
	public String execute(ApiRequest apiRequest) {
		GJFlightResponse gjFlightResponse = new GJFlightResponse();
		try {
			GJFlightQO gjFlightQO = JSON.parseObject(apiRequest.getBody().getPayload(), GJFlightQO.class);
			HgLogger.getInstance().info("cs", "【QueryGJFlightAction】"+"gjFlightQO:"+JSON.toJSONString(gjFlightQO));
			JPFlightGJQO jpFlightGJQO = JSON.parseObject(JSON.toJSONString(gjFlightQO), JPFlightGJQO.class);
			FlightGJDTO flightGJDTO = gjFlightService.queryGJFlight(jpFlightGJQO);
			HgLogger.getInstance().info("cs", "【QueryGJFlightAction】"+"flightGJDTO:"+JSON.toJSONString(flightGJDTO));
			List<GJAvailableFlightGroupDTO> availableFlightGroups = flightGJDTO.getAvailableFlightGroups();
			List<GJFlightListDTO> gjFlightListDTOs = new ArrayList<GJFlightListDTO>();
			for(GJAvailableFlightGroupDTO gjAvailableFlightGroupDTO:availableFlightGroups){
				GJFlightListDTO gjFlightListDTO = new GJFlightListDTO();
				//航班加密字符串
				gjFlightListDTO.setFlightID(gjAvailableFlightGroupDTO.getFlightCabinGroupToken());
				//航班航班相关金额
				gjFlightListDTO.setPriceInfoMap(JSON.parseObject(JSON.toJSONString(gjAvailableFlightGroupDTO.getPriceInfoMap()),Map.class));
				//票面价
				gjFlightListDTO.setIbePrice(gjAvailableFlightGroupDTO.getPriceInfoMap().get(ADT).getTotalPrice().toString());
				
				//出发航班
				List<GJFlightDTO> takeoffFlights = new ArrayList<GJFlightDTO>();
				for(GJFlightCabinDTO gjFlightCabinDTO:gjAvailableFlightGroupDTO.getBackFlights()){
					//去程航班
					GJFlightDTO takeoffFlight = new GJFlightDTO();
					//航空公司
					takeoffFlight.setAirComp(gjFlightCabinDTO.getCarriageAirline());
					//到达机场
					takeoffFlight.setArrivalAirport("");
					//到达航站楼
					takeoffFlight.setArrivalTerm(gjFlightCabinDTO.getDstTerm());
					//离港机场
					takeoffFlight.setDepartAirport("");
					//离港航站楼
					takeoffFlight.setDepartTerm(gjFlightCabinDTO.getOrgTerm());
					//目的地
					takeoffFlight.setDstCity(gjFlightCabinDTO.getDstCity());
					//落地时间
					takeoffFlight.setEndTime(gjFlightCabinDTO.getEndTime());
					//航班号
					takeoffFlight.setFlightNO(gjFlightCabinDTO.getCarriageFlightno());
					//出发城市
					takeoffFlight.setOrgCity(gjFlightCabinDTO.getOrgCity());
					//机型
					takeoffFlight.setPlaneType(gjFlightCabinDTO.getCarriageFlightno());
					//起飞时间
					takeoffFlight.setStartTime(gjFlightCabinDTO.getStartTime());
					//飞行时长
					takeoffFlight.setDuration(gjFlightCabinDTO.getDuration());
					//餐食代码
					takeoffFlight.setMealCode(gjFlightCabinDTO.getMealCode());
					takeoffFlights.add(takeoffFlight);
				}
				//回程信息不为空，说明是往返
				List<GJFlightDTO> backFlights = new ArrayList<GJFlightDTO>();
				for(GJFlightCabinDTO gjFlightCabinDTO:gjAvailableFlightGroupDTO.getBackFlights()){
					//返程航班
					GJFlightDTO backFlight = new GJFlightDTO();
					backFlight.setAirComp(gjFlightCabinDTO.getCarriageAirline());
					backFlight.setArrivalAirport("");
					backFlight.setArrivalTerm(gjFlightCabinDTO.getDstTerm());
					backFlight.setDepartAirport("");
					backFlight.setDepartTerm(gjFlightCabinDTO.getOrgTerm());
					backFlight.setDstCity(gjFlightCabinDTO.getDstCity());
					backFlight.setEndTime(gjFlightCabinDTO.getEndTime());
					backFlight.setFlightNO(gjFlightCabinDTO.getCarriageFlightno());
					backFlight.setOrgCity(gjFlightCabinDTO.getOrgCity());
					backFlight.setPlaneType(gjFlightCabinDTO.getCarriageFlightno());
					backFlight.setStartTime(gjFlightCabinDTO.getStartTime());
					backFlight.setDuration(gjFlightCabinDTO.getDuration());
					backFlight.setMealCode(gjFlightCabinDTO.getMealCode());
					gjFlightListDTOs.add(gjFlightListDTO);
				}
				gjFlightListDTO.setTakeoffFlights(takeoffFlights);
				gjFlightListDTO.setBackFlights(backFlights);
				gjFlightListDTOs.add(gjFlightListDTO);
			}
			gjFlightResponse.setFlightGNListDTOs(gjFlightListDTOs);
			//按出发起飞时间
			if(StringUtils.isNotBlank(gjFlightQO.getOrderBy())){
				if(StringUtils.equals(gjFlightQO.getOrderBy(), String.valueOf(GJFlightQO.TAKEOFF_START_TIME))){
					if(StringUtils.isNotBlank(gjFlightQO.getOrderType())){
						if(StringUtils.equals(gjFlightQO.getOrderType(), String.valueOf(GJFlightQO.ASC))){
							Collections.sort(gjFlightListDTOs, new Comparator<GJFlightListDTO>() {
								@Override
								public int compare(GJFlightListDTO o1,GJFlightListDTO o2) {
									return o1.getTakeoffFlights().get(0).getStartTime().compareTo(o2.getTakeoffFlights().get(0).getStartTime());
								}
							});
						}
						if(StringUtils.equals(gjFlightQO.getOrderType(), String.valueOf(GJFlightQO.DESC))){
							Collections.sort(gjFlightListDTOs, new Comparator<GJFlightListDTO>() {
								@Override
								public int compare(GJFlightListDTO o1,GJFlightListDTO o2) {
									return o2.getTakeoffFlights().get(0).getStartTime().compareTo(o1.getTakeoffFlights().get(0).getStartTime());
								}
							});
						}
					}
				}
				//按返回起飞时间
				if(StringUtils.equals(gjFlightQO.getOrderBy(), String.valueOf(GJFlightQO.BACK_START_TIME))){
					if(StringUtils.isNotBlank(gjFlightQO.getOrderType())){
						if(StringUtils.equals(gjFlightQO.getOrderType(), String.valueOf(GJFlightQO.ASC))){
							Collections.sort(gjFlightListDTOs, new Comparator<GJFlightListDTO>() {
								@Override
								public int compare(GJFlightListDTO o1,GJFlightListDTO o2) {
									return o1.getBackFlights().get(0).getStartTime().compareTo(o2.getBackFlights().get(0).getStartTime());
								}
							});
						}
						if(StringUtils.equals(gjFlightQO.getOrderType(), String.valueOf(GJFlightQO.DESC))){
							Collections.sort(gjFlightListDTOs, new Comparator<GJFlightListDTO>() {
								@Override
								public int compare(GJFlightListDTO o1,GJFlightListDTO o2) {
									return o2.getBackFlights().get(0).getStartTime().compareTo(o1.getBackFlights().get(0).getStartTime());
								}
							});
						}
					}
				}
				//按票面价
				if(StringUtils.equals(gjFlightQO.getOrderBy(), String.valueOf(GJFlightQO.IBE_PRICE))){
					if(StringUtils.isNotBlank(gjFlightQO.getOrderType())){
						if(StringUtils.equals(gjFlightQO.getOrderType(), String.valueOf(GJFlightQO.ASC))){
							Collections.sort(gjFlightListDTOs, new Comparator<GJFlightListDTO>() {
								@Override
								public int compare(GJFlightListDTO o1,GJFlightListDTO o2) {
									return o1.getIbePrice().compareTo(o2.getIbePrice());
								}
							});
						}
						if(StringUtils.equals(gjFlightQO.getOrderType(), String.valueOf(GJFlightQO.DESC))){
							Collections.sort(gjFlightListDTOs, new Comparator<GJFlightListDTO>() {
								@Override
								public int compare(GJFlightListDTO o1,GJFlightListDTO o2) {
									return o2.getIbePrice().compareTo(o1.getIbePrice());
								}
							});
						}
					}
				}
			}
		} catch (GJFlightException e) {
			HgLogger.getInstance().info("cs", "【QueryGJFlightAction】"+"国际航班查询失败，"+HgLogger.getStackTrace(e));
			gjFlightResponse.setResult(e.getCode());
			gjFlightResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("cs", "【QueryGJFlightAction】"+"gjFlightResponse:"+JSON.toJSONString(gjFlightResponse));
		return JSON.toJSONString(gjFlightResponse);
	}

}
