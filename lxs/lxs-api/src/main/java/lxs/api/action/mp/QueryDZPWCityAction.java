package lxs.api.action.mp;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.RegionDTO;
import lxs.api.v1.response.mp.DZPWCityResponse;
import lxs.app.service.mp.DZPWCityService;
import lxs.domain.model.mp.DzpwCity;
import lxs.pojo.exception.mp.QueryDZPWCityException;
import lxs.pojo.qo.mp.DZPWCityQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryDZPWCityAction")
public class QueryDZPWCityAction implements LxsAction{

	@Autowired
	private DZPWCityService dzpwCityService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryDZPWCityAction】");
		DZPWCityQO dzpwCityQO = JSON.parseObject(apiRequest.getBody().getPayload(), DZPWCityQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QueryDZPWCityAction】【dzpwCityQO】"+JSON.toJSONString(dzpwCityQO));
		DZPWCityResponse dzpwCityResponse = new DZPWCityResponse();
		try{
			if(StringUtils.isBlank(dzpwCityQO.getCode())){
				throw new QueryDZPWCityException(QueryDZPWCityException.PARAMETER_ILLEGAL);
			}
			List<RegionDTO> regionDTOs = new ArrayList<RegionDTO>();
			List<DzpwCity> dzpwCities = dzpwCityService.queryList(dzpwCityQO);
			for (DzpwCity dzpwCity : dzpwCities) {
				RegionDTO regionDTO = JSON.parseObject(JSON.toJSONString(dzpwCity), RegionDTO.class);
				regionDTOs.add(regionDTO);
			}
			dzpwCityResponse.setRegionDTOs(regionDTOs);
			dzpwCityResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			String message = "";
			if(e instanceof QueryDZPWCityException){
				message=e.getMessage();
			}else{
				message="查询城市列表失败";
			}
			dzpwCityResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			dzpwCityResponse.setMessage(message);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryDZPWCityAction】【dzpwCityResponse】"+JSON.toJSONString(dzpwCityResponse));
		return JSON.toJSONString(dzpwCityResponse);
	}

}
