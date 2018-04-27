package lxs.api.action.mp;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.RegionDTO;
import lxs.api.v1.response.mp.DZPWAreaResponse;
import lxs.app.service.mp.DZPWAreaService;
import lxs.domain.model.mp.DzpwArea;
import lxs.pojo.exception.mp.QueryDZPWAreaException;
import lxs.pojo.qo.mp.DZPWAreaQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryDZPWAreaAction")
public class QueryDZPWAreaAction implements LxsAction{

	@Autowired
	private DZPWAreaService dzpwAreaService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryDZPWAreaAction】");
		DZPWAreaQO dzpwAreaQO = JSON.parseObject(apiRequest.getBody().getPayload(), DZPWAreaQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QueryDZPWAreaAction】【dzpwAreaQO】"+JSON.toJSONString(dzpwAreaQO));
		DZPWAreaResponse dzpwAreaResponse = new DZPWAreaResponse();
		try{
			if(StringUtils.isBlank(dzpwAreaQO.getCode())){
				throw new QueryDZPWAreaException(QueryDZPWAreaException.PARAMETER_ILLEGAL);
			}
			List<RegionDTO> regionDTOs = new ArrayList<RegionDTO>();
			List<DzpwArea> dzpwCities = dzpwAreaService.queryList(dzpwAreaQO);
			for (DzpwArea dzpwArea : dzpwCities) {
				RegionDTO regionDTO = JSON.parseObject(JSON.toJSONString(dzpwArea), RegionDTO.class);
				regionDTOs.add(regionDTO);
			}
			dzpwAreaResponse.setRegionDTOs(regionDTOs);
			dzpwAreaResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			String message = "";
			if(e instanceof QueryDZPWAreaException){
				message=e.getMessage();
			}else{
				message="查询城市列表失败";
			}
			dzpwAreaResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			dzpwAreaResponse.setMessage(message);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryDZPWAreaAction】【dzpwAreaResponse】"+JSON.toJSONString(dzpwAreaResponse));
		return JSON.toJSONString(dzpwAreaResponse);
	}

}
