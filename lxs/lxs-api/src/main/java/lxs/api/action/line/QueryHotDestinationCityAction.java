package lxs.api.action.line;

import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.v1.dto.line.HotDestinationCityDTO;
import lxs.api.v1.request.qo.line.HotDestinationCityQO;
import lxs.api.v1.response.line.HotDestinationCityResponse;
import lxs.app.service.line.LineService;
import lxs.domain.model.line.Line;
import lxs.pojo.qo.line.LineQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryHotDestinationCityAction")
public class QueryHotDestinationCityAction implements LxsAction {

	@Autowired
	private LineService lineService;
	@Autowired
	private CityService cityService;

	@Override
	public String execute(ApiRequest apiRequest) {
		HotDestinationCityQO hotDestinationCityQO = JSON.parseObject(apiRequest
				.getBody().getPayload(), HotDestinationCityQO.class);
		LineQO lineQO = new LineQO();
		lineQO.setType(hotDestinationCityQO.getType().toString());
		lineQO.setGetHotDestinationCity("get");
		List<Line> lines = lineService.queryList(lineQO);
		System.out.println(JSON.toJSON(lines));
		List<HotDestinationCityDTO> hotDestinationCityDTOs = new ArrayList<HotDestinationCityDTO>(); 
		for (int i =0 ;i<lines.size();i++) {
			String val = JSON.toJSONString(lines.get(i));
			val=val.replace("[\"", "");
			val=val.replace("\",", ":");
			val=val.replace("]", "");
			String[] vals = val.split(":");
			CityQo cityQo = new CityQo();
			cityQo.setId(vals[0]);
			City city = cityService.queryUnique(cityQo);
			if(city!=null){
				HotDestinationCityDTO hotDestinationCityDTO = new HotDestinationCityDTO();
				hotDestinationCityDTO.setDestinationCity(city.getId());
				hotDestinationCityDTO.setDestinationCityName(city.getName());
				hotDestinationCityDTO.setSum(Integer.parseInt(vals[1]));
				hotDestinationCityDTOs.add(hotDestinationCityDTO);
			}
		}
		Collections.sort(hotDestinationCityDTOs, new Comparator<HotDestinationCityDTO>() {
			@Override
			public int compare(HotDestinationCityDTO o1, HotDestinationCityDTO o2) {
				return o2.getSum().compareTo(o1.getSum());
			}
		});
		HotDestinationCityResponse hotDestinationCityResponse = new HotDestinationCityResponse();
		hotDestinationCityResponse.setHotDestinationCityDTOs(hotDestinationCityDTOs);
		HgLogger.getInstance().info("lxs_dev", "查询热门目的地城市"+JSON.toJSONString(hotDestinationCityResponse));
		return JSON.toJSONString(hotDestinationCityResponse);
	}
}
