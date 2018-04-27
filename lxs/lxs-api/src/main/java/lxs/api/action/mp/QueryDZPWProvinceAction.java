package lxs.api.action.mp;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.RegionDTO;
import lxs.api.v1.response.mp.DZPWProvinceResponse;
import lxs.app.service.mp.DZPWProvinceService;
import lxs.domain.model.mp.DzpwProvince;
import lxs.pojo.qo.mp.DZPWProvinceQO;

@Component("QueryDZPWProvinceAction")
public class QueryDZPWProvinceAction implements LxsAction{

	@Autowired
	private  DZPWProvinceService dzpwProvinceService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryDZPWProvinceAction】");
		DZPWProvinceResponse dzpwProvinceResponse = new DZPWProvinceResponse();
		List<RegionDTO> regionDTOs = new ArrayList<RegionDTO>();
		try{
			List<DzpwProvince> dzpwProvinces = dzpwProvinceService.queryList(new DZPWProvinceQO());
			for (DzpwProvince dzpwProvince : dzpwProvinces) {
				RegionDTO regionDTO = JSON.parseObject(JSON.toJSONString(dzpwProvince), RegionDTO.class);
				regionDTOs.add(regionDTO);
			}
			dzpwProvinceResponse.setRegionDTOs(regionDTOs);
			dzpwProvinceResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			dzpwProvinceResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			dzpwProvinceResponse.setMessage("查询省份列表失败");
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryDZPWProvinceAction】【dzpwProvinceResponse】"+JSON.toJSONString(dzpwProvinceResponse));
		return JSON.toJSONString(dzpwProvinceResponse);
	}

}
