package lxs.api.action.line;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.line.DayRouteDTO;
import lxs.api.v1.dto.line.HotelDTO;
import lxs.api.v1.request.qo.line.DayRouteQO;
import lxs.api.v1.response.line.QueryDayRouteResponse;
import lxs.app.service.line.DayRouteService;
import lxs.domain.model.line.DayRoute;
import lxs.pojo.exception.line.LineException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryDayRouteAction")
public class QueryDayRouteAction implements LxsAction {

	@Autowired
	private DayRouteService dayRouteService;

	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入action");
		DayRouteQO apidayRouteQO = JSON.parseObject(apiRequest.getBody().getPayload(),DayRouteQO.class);
		lxs.pojo.qo.line.DayRouteQO dayRouteQO = new lxs.pojo.qo.line.DayRouteQO();
		QueryDayRouteResponse queryDayRouteResponse = new QueryDayRouteResponse();
		try{
			if(apidayRouteQO.getLineId()==null){
				throw new LineException(LineException.RESULT_NO_LINE, "线路为空");
			}
			dayRouteQO.setLineID(apidayRouteQO.getLineId());
			List<DayRoute> dayRoutsList = dayRouteService.queryList(dayRouteQO);
			List<DayRouteDTO> dayRouteDTOs = new ArrayList<DayRouteDTO>();
			for(DayRoute dayRoute:dayRoutsList){
				DayRouteDTO dayRouteDTO = new DayRouteDTO();
				dayRouteDTO.setDays(dayRoute.getDays());
				dayRouteDTO.setDestination(dayRoute.getDestination());
				List<HotelDTO> hotelInfoList = new ArrayList<HotelDTO>();
				if(StringUtils.isNotBlank(dayRoute.getHotelInfoJson())){
					hotelInfoList = JSON.parseArray(dayRoute.getHotelInfoJson(), HotelDTO.class);
				}
				dayRouteDTO.setHotel(hotelInfoList);
				dayRouteDTO.setIncludeBreakfast(dayRoute.getIncludeBreakfast());
				dayRouteDTO.setIncludeDinner(dayRoute.getIncludeDinner());
				dayRouteDTO.setIncludeLunch(dayRoute.getIncludeLunch());
				dayRouteDTO.setStarting(dayRoute.getStarting());
				dayRouteDTO.setSchedulingDescription(dayRoute.getSchedulingDescription());
				dayRouteDTO.setStayLevel(dayRoute.getStayLevel());
				dayRouteDTO.setVehicle(dayRoute.getVehicle());
				dayRouteDTOs.add(dayRouteDTO);
			}
			queryDayRouteResponse.setDayRouteList(dayRouteDTOs);
			queryDayRouteResponse.setResult(ApiResponse.RESULT_CODE_OK);
			queryDayRouteResponse.setMessage("查询成功");
		}catch(LineException e){
			HgLogger.getInstance().info("lxs_dev", "【QueryDayRouteAction】"+"异常，"+HgLogger.getStackTrace(e));
			queryDayRouteResponse.setResult(e.getCode());
			queryDayRouteResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "查询行程结果"+JSON.toJSONString(queryDayRouteResponse));
		return JSON.toJSONString(queryDayRouteResponse);
	}

}
