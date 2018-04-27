package lxs.api.action.line;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.line.MinPriceActivityDTO;
import lxs.api.v1.dto.line.TravlerNOActivityDTO;
import lxs.api.v1.request.qo.line.LineActivityQO;
import lxs.api.v1.response.line.QueryLineActivityResponse;
import lxs.app.service.line.LineActivityService;
import lxs.app.service.line.LineService;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineActivity;
import lxs.domain.model.line.LineImage;
import lxs.pojo.qo.line.LineQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryLineActivityAction")
public class QueryLineActivityAction implements LxsAction{

	@Autowired
	private LineActivityService lineActivityService;
	@Autowired
	private CityService cityService;
	@Autowired
	private LineService lineService;
	
	private static final String MIN_PRICE_ACTIVITY="1";
	private static final String TRAVLER_NO_ACTIVITY="2";
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【QueryLineActivityAction】"+"进入action");
		LineActivityQO apiLineActivityQO = JSON.parseObject(apiRequest.getBody().getPayload(), LineActivityQO.class);
		QueryLineActivityResponse queryLineActivityResponse= new QueryLineActivityResponse();
		lxs.pojo.qo.line.LineActivityQO lineActivityQO = new lxs.pojo.qo.line.LineActivityQO();
		if(apiLineActivityQO!=null&&(StringUtils.isNotBlank(apiLineActivityQO.getStartingCity())||StringUtils.isNotBlank(apiLineActivityQO.getStartingProvince()))){
			//按城市查询
			lineActivityQO.setStartingCity(apiLineActivityQO.getStartingCity());
			List<String> strings = new ArrayList<String>();
			if(apiLineActivityQO.getStartingProvince()!=null&&StringUtils.isNotBlank(apiLineActivityQO.getStartingProvince())){
				CityQo cityQo = new CityQo();
				cityQo.setProvinceCode(apiLineActivityQO.getStartingProvince());
				List<City> cities = cityService.queryList(cityQo);
				for(City city:cities){
					strings.add(city.getCode());
				}
			}
			lineActivityQO.setStartingProvince(strings);
			lineActivityQO.setActivityType(MIN_PRICE_ACTIVITY);
			List<LineActivity> lineActivities= lineActivityService.queryList(lineActivityQO);
			List<MinPriceActivityDTO> minPriceActivityDTOs = new ArrayList<MinPriceActivityDTO>();
			for(LineActivity lineActivity:lineActivities){
				MinPriceActivityDTO minPriceActivityDTO = new MinPriceActivityDTO();
				LineQO lineQO = new LineQO();
				lineQO.setId(lineActivity.getLine().getId());
				Line line = lineService.queryUnique(lineQO);
				minPriceActivityDTO.setAdultUnitPrice(lineActivity.getAdultUnitPrice());
				minPriceActivityDTO.setChildUnitPrice(lineActivity.getChildUnitPrice());
				minPriceActivityDTO.setLineID(line.getId());
				minPriceActivityDTO.setLineNO(line.getBaseInfo().getNumber());
				minPriceActivityDTO.setLineName(line.getBaseInfo().getName());
				if(line.getBaseInfo().getStarting()!=null&&StringUtils.isNotBlank(line.getBaseInfo().getStarting())){
					minPriceActivityDTO.setStarting(cityService.get(line.getBaseInfo().getStarting()).getName());
				}
				minPriceActivityDTO.setType(line.getBaseInfo().getType());
				minPriceActivityDTO.setPictureUri("#");
				if(line.getLineImageList()!=null){
					for(LineImage lineImage:line.getLineImageList()){
						if(lineImage.getUrlMapsJSON()!=null){
							Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(), Map.class);
							if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType"))==null){
								if(lineMap.get("default")!=null){
									minPriceActivityDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
								}
							}else{
								minPriceActivityDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType")));
							}
							break;
						}
					}
				}
				minPriceActivityDTOs.add(minPriceActivityDTO);
			}
			queryLineActivityResponse.setMinPriceActivityDTO(minPriceActivityDTOs);
		}else{
			//查询所有活动
			//开始查询人数活动线路
			lineActivityQO.setActivityType(TRAVLER_NO_ACTIVITY);
			List<LineActivity> lineActivities= lineActivityService.queryList(lineActivityQO);
			List<TravlerNOActivityDTO> travlerNOActivityDTOs = new ArrayList<TravlerNOActivityDTO>();
			for(LineActivity lineActivity:lineActivities){
				TravlerNOActivityDTO travlerNOActivityDTO = new TravlerNOActivityDTO();
				LineQO lineQO = new LineQO();
				lineQO.setId(lineActivity.getLine().getId());
				Line line = lineService.queryUnique(lineQO);
				travlerNOActivityDTO.setLineID(line.getId());
				travlerNOActivityDTO.setLineNO(line.getBaseInfo().getNumber());
				travlerNOActivityDTO.setMinPrice(line.getMinPrice());
				travlerNOActivityDTO.setLineName(line.getBaseInfo().getName());
				if(line.getBaseInfo().getStarting()!=null&&StringUtils.isNotBlank(line.getBaseInfo().getStarting())){
					travlerNOActivityDTO.setStarting(cityService.get(line.getBaseInfo().getStarting()).getName());
				}
				travlerNOActivityDTO.setTravlerNO(lineActivity.getTravlerNO());
				travlerNOActivityDTO.setType(line.getBaseInfo().getType());
				travlerNOActivityDTO.setPictureUri("#");
				if(line.getLineImageList()!=null){
					for(LineImage lineImage:line.getLineImageList()){
						if(lineImage.getUrlMapsJSON()!=null){
							Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(), Map.class);
							if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType"))==null){
								if(lineMap.get("default")!=null){
									travlerNOActivityDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
								}
							}else{
								travlerNOActivityDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType")));
							}
							break;
						}
					}
				}
				travlerNOActivityDTOs.add(travlerNOActivityDTO);
			}
			queryLineActivityResponse.setTravlerNOActivityDTO(travlerNOActivityDTOs);
			//开始查询价格活动线路
			lineActivityQO.setActivityType(MIN_PRICE_ACTIVITY);
			lineActivities= lineActivityService.queryList(lineActivityQO);
			List<MinPriceActivityDTO> minPriceActivityDTOs = new ArrayList<MinPriceActivityDTO>();
			for(LineActivity lineActivity:lineActivities){
				MinPriceActivityDTO minPriceActivityDTO = new MinPriceActivityDTO();
				LineQO lineQO = new LineQO();
				lineQO.setId(lineActivity.getLine().getId());
				Line line = lineService.queryUnique(lineQO);
				minPriceActivityDTO.setAdultUnitPrice(lineActivity.getAdultUnitPrice());
				minPriceActivityDTO.setChildUnitPrice(lineActivity.getChildUnitPrice());
				minPriceActivityDTO.setLineID(line.getId());
				minPriceActivityDTO.setLineNO(line.getBaseInfo().getNumber());
				minPriceActivityDTO.setLineName(line.getBaseInfo().getName());
				if(line.getBaseInfo().getStarting()!=null&&StringUtils.isNotBlank(line.getBaseInfo().getStarting())){
					minPriceActivityDTO.setStarting(cityService.get(line.getBaseInfo().getStarting()).getName());
				}
				minPriceActivityDTO.setType(line.getBaseInfo().getType());
				minPriceActivityDTO.setPictureUri("#");
				if(line.getLineImageList()!=null){
					for(LineImage lineImage:line.getLineImageList()){
						if(lineImage.getUrlMapsJSON()!=null){
							Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(), Map.class);
							if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType"))==null){
								if(lineMap.get("default")!=null){
									minPriceActivityDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
								}
							}else{
								minPriceActivityDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType")));
							}
							break;
						}
					}
				}
				minPriceActivityDTOs.add(minPriceActivityDTO);
			}
			queryLineActivityResponse.setMinPriceActivityDTO(minPriceActivityDTOs);
		}
		queryLineActivityResponse.setMessage("查询成功");
		queryLineActivityResponse.setResult(ApiResponse.RESULT_CODE_OK);
		HgLogger.getInstance().info("lxs_dev", "【QueryLineAction】"+"查询活动结果"+JSON.toJSONString(queryLineActivityResponse));
		return JSON.toJSONString(queryLineActivityResponse);
	}

}
