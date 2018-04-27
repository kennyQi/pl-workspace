package zzpl.api.action.user;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.CostCenterDTO;
import zzpl.api.client.response.user.QueryCostCenterResponse;
import zzpl.app.service.local.user.CostCenterService;
import zzpl.domain.model.user.CostCenter;
import zzpl.pojo.dto.user.status.CostCenterStatus;
import zzpl.pojo.qo.user.CostCenterQO;

@Component("QueryCostCenterAction")
public class QueryCostCenterAction implements CommonAction{
	
	@Autowired
	private CostCenterService costCenterService;

	@Override
	public String execute(ApiRequest apiRequest) {
		QueryCostCenterResponse queryCostCenterResponse = new QueryCostCenterResponse();
		try{
			CostCenterQO costCenterQO = JSON.parseObject(apiRequest.getBody().getPayload(), CostCenterQO.class);
			HgLogger.getInstance().info("cs", "【QueryCostCenterAction】"+"costCenterQO:"+JSON.toJSONString(costCenterQO));
			costCenterQO.setStatus(CostCenterStatus.NORMAL);
			List<CostCenter> costCenters = costCenterService.queryList(costCenterQO);
			List<CostCenterDTO> costCenterDTOs = BeanMapperUtils.getMapper().mapAsList(costCenters, CostCenterDTO.class);
			queryCostCenterResponse.setCostCenterDTOs(costCenterDTOs);
			queryCostCenterResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【QueryCostCenterAction】"+"异常，"+HgLogger.getStackTrace(e));
			queryCostCenterResponse.setMessage("成本中心查询失败");
			queryCostCenterResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【QueryCostCenterAction】"+"queryCostCenterResponse:"+JSON.toJSONString(queryCostCenterResponse));
		return JSON.toJSONString(queryCostCenterResponse);
	}

}
