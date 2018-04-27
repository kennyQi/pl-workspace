package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.qo.mp.MPScenicSpotsQO;
import hsl.api.v1.response.mp.MPQueryScenicSpotsResponse;
import hsl.pojo.dto.mp.PCScenicSpotDTO;
import hsl.pojo.dto.mp.ScenicSpotDTO;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.spi.action.constant.Constants;
import hsl.spi.inter.mp.MPScenicSpotService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("mpQueryScenicSpotsAction")
@SuppressWarnings({"rawtypes","unchecked"})
public class MPQueryScenicSpotsAction implements HSLAction {
	@Autowired
	private MPScenicSpotService scenicSpotService;
	@Autowired
	private HgLogger hgLogger;
	
	
	@Override
	public String execute(ApiRequest apiRequest) {
		MPScenicSpotsQO mpScenicSpotsQO = JSON.parseObject(apiRequest.getBody().getPayload(), MPScenicSpotsQO.class);
		
		HslMPScenicSpotQO hslMPScenicSpotQO=BeanMapperUtils.map(mpScenicSpotsQO, HslMPScenicSpotQO.class);
		
		hgLogger.info("liujz", "查询景区请求"+JSON.toJSONString(mpScenicSpotsQO));
		MPQueryScenicSpotsResponse scenicSpotsResponse = new MPQueryScenicSpotsResponse();

		Map scenicSpotMap=new HashMap();
		try {
			scenicSpotMap = scenicSpotService.queryScenicSpot(hslMPScenicSpotQO);
			List<ScenicSpotDTO> scenicSpotDTOList;
			//是热门景点的话需要修改id
			if(mpScenicSpotsQO.getHot()){
				List<HotScenicSpotDTO> hotlist = (List<HotScenicSpotDTO>)scenicSpotMap.get("dto");
				scenicSpotDTOList = new ArrayList<ScenicSpotDTO>();
				if(hotlist!=null&&hotlist.size()>0){
					ScenicSpotDTO scenicSpotDTO;
					for(HotScenicSpotDTO hot: hotlist){
						scenicSpotDTO = new ScenicSpotDTO();
						scenicSpotDTO.setId(hot.getScenicSpotId());
						scenicSpotDTO.setScenicSpotBaseInfo(hot.getScenicSpotBaseInfo());
						scenicSpotDTOList.add(scenicSpotDTO);
					}
				}
			}else{
				List<PCScenicSpotDTO> pcScenicSpotDTOList = (List<PCScenicSpotDTO>) scenicSpotMap.get("dto");
				scenicSpotDTOList =BeanMapperUtils.getMapper().mapAsList(pcScenicSpotDTOList, ScenicSpotDTO.class);
			}
			if(scenicSpotDTOList != null && scenicSpotDTOList.size() > 0){
				scenicSpotsResponse.setScenicSpots(scenicSpotDTOList);
				scenicSpotsResponse.setTotalCount(Integer.parseInt(scenicSpotMap.get("count").toString()));				
			}else{
				scenicSpotsResponse.setScenicSpots(null);
				scenicSpotsResponse.setTotalCount(0);
				scenicSpotsResponse.setMessage("没有数据");
			}
		} catch (MPException e) {
			scenicSpotsResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			scenicSpotsResponse.setMessage(e.getMessage());
		}
		hgLogger.info("liujz", "查询景区结果"+JSON.toJSONString(scenicSpotsResponse));
		return JSON.toJSONString(scenicSpotsResponse);
	}

}
