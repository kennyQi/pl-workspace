package zzpl.api.action.jp.gj;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.dto.jp.GJCabinInfoDTO;
import zzpl.api.client.dto.jp.GJCabinListDTO;
import zzpl.api.client.request.jp.GJCabinQO;
import zzpl.api.client.response.jp.GJCabinResponse;
import zzpl.app.service.local.jp.gj.GJFlightService;
import zzpl.pojo.dto.jp.plfx.gj.FlightMoreCabinsGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.GJAvailableFlightGroupDTO;
import zzpl.pojo.dto.jp.plfx.gj.GJFlightCabinDTO;
import zzpl.pojo.exception.jp.GJFlightException;
import zzpl.pojo.qo.jp.plfx.gj.JPMoreCabinsGJQO;

import com.alibaba.fastjson.JSON;

@Component("QueryGJCabinAction")
public class QueryGJCabinAction implements CommonAction{

	@Autowired
	private GJFlightService gjFlightService;
	
	/**
	 * 成人舱位
	 */
	public final static String ADT = "ADT";
	
	@Override
	public String execute(ApiRequest apiRequest) {
		GJCabinResponse gjCabinResponse = new GJCabinResponse();
		try{
			GJCabinQO gjCabinQO = JSON.parseObject(apiRequest.getBody().getPayload(), GJCabinQO.class);
			HgLogger.getInstance().info("cs", "【QueryGJCabinAction】"+"gjCabinQO:"+JSON.toJSONString(gjCabinQO));
			JPMoreCabinsGJQO jpMoreCabinsGJQO = new JPMoreCabinsGJQO();
			jpMoreCabinsGJQO.setFlightCabinGroupToken(gjCabinQO.getFlightID());
			FlightMoreCabinsGJDTO flightMoreCabinsGJDTO = gjFlightService.queryGJMoreCabins(jpMoreCabinsGJQO);
			HgLogger.getInstance().info("cs", "【QueryGJCabinAction】"+"flightMoreCabinsGJDTO:"+JSON.toJSONString(flightMoreCabinsGJDTO));
			//舱位组合列表
			List<GJCabinListDTO> cabinListDTOs = new ArrayList<GJCabinListDTO>();
			for (GJAvailableFlightGroupDTO gjAvailableFlightGroupDTO : flightMoreCabinsGJDTO.getAvailableFlightGroups()) {
				GJCabinListDTO gjCabinListDTO = new GJCabinListDTO();
				//航班加密字符串
				gjCabinListDTO.setEncryptString(gjAvailableFlightGroupDTO.getFlightCabinGroupToken());
				//航班航班相关金额
				gjCabinListDTO.setPriceInfoMap(JSON.parseObject(JSON.toJSONString(gjAvailableFlightGroupDTO.getPriceInfoMap()),Map.class));
				//票面价
				gjCabinListDTO.setIbePrice(gjAvailableFlightGroupDTO.getPriceInfoMap().get(ADT).getTotalPrice().toString());
				//去程航班舱位信息
				List<GJCabinInfoDTO> takeoffGJCabinInfoDTOs = new ArrayList<GJCabinInfoDTO>();
				for(GJFlightCabinDTO gjFlightCabinDTO:gjAvailableFlightGroupDTO.getTakeoffFlights()){
					GJCabinInfoDTO takeoffCabinInfo = new GJCabinInfoDTO();
					//舱位类型
					takeoffCabinInfo.setCabinCode(gjFlightCabinDTO.getCabinCode());
					//剩余数量
					takeoffCabinInfo.setCabinSales(gjFlightCabinDTO.getCabinSales().toString());
					takeoffGJCabinInfoDTOs.add(takeoffCabinInfo);
				}
				//返程航班舱位信息
				List<GJCabinInfoDTO> backGJCabinInfoDTOs = new ArrayList<GJCabinInfoDTO>();
				for(GJFlightCabinDTO gjFlightCabinDTO:gjAvailableFlightGroupDTO.getBackFlights()){
					GJCabinInfoDTO backCabinInfo = new GJCabinInfoDTO();
					//舱位类型
					backCabinInfo.setCabinCode(gjFlightCabinDTO.getCabinCode());
					//剩余数量
					backCabinInfo.setCabinSales(gjFlightCabinDTO.getCabinSales().toString());
					backGJCabinInfoDTOs.add(backCabinInfo);
				}				
				gjCabinListDTO.setTakeoffCabinInfo(takeoffGJCabinInfoDTOs);
				gjCabinListDTO.setBackCabinInfo(backGJCabinInfoDTOs);
				cabinListDTOs.add(gjCabinListDTO);
			}
			gjCabinResponse.setCabinListDTOs(cabinListDTOs);
		}catch (GJFlightException e) {
			HgLogger.getInstance().info("cs", "【QueryGJCabinAction】"+"国际航班舱位查询失败，"+HgLogger.getStackTrace(e));
			gjCabinResponse.setResult(e.getCode());
			gjCabinResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("cs", "【QueryGJCabinAction】"+"gjCabinResponse:"+JSON.toJSONString(gjCabinResponse));
		return JSON.toJSONString(gjCabinResponse);
	}

}
