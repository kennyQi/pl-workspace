package lxs.api.action.mp;

import hg.common.page.Pagination;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.ScenicSpotDTO;
import lxs.api.v1.request.qo.mp.ScenicSpotSelectiveQO;
import lxs.api.v1.response.mp.ScenicSpotResponse;
import lxs.app.service.app.SubjectService;
import lxs.app.service.mp.DZPWCityService;
import lxs.app.service.mp.ScenicSpotPicService;
import lxs.app.service.mp.ScenicSpotSelectiveService;
import lxs.app.service.mp.ScenicSpotService;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.ScenicSpotPic;
import lxs.domain.model.mp.ScenicSpotSelective;
import lxs.pojo.qo.mp.ScenicSpotPicQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryScenicSpotSelectiveAction")
public class QueryScenicSpotSelectiveAction implements LxsAction{

	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ScenicSpotPicService scenicSpotPicService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ScenicSpotSelectiveService scenicSpotSelectiveService;
	@Autowired
	private DZPWCityService dzpwCityService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryScenicSpotSelectiveAction】");
		ScenicSpotSelectiveQO apiScenicSpotSelectiveQO= JSON.parseObject(apiRequest.getBody().getPayload(), ScenicSpotSelectiveQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QueryScenicSpotSelectiveAction】【apiScenicSpotSelectiveQO】"+JSON.toJSONString(apiScenicSpotSelectiveQO));
		ScenicSpotResponse scenicSpotResponse = new ScenicSpotResponse();
		lxs.pojo.qo.mp.ScenicSpotSelectiveQO scenicSpotSelectiveQO = new lxs.pojo.qo.mp.ScenicSpotSelectiveQO();
		try{
			int pageNo = 1;
			int pageSize = 10;
			if(StringUtils.isNotBlank(apiScenicSpotSelectiveQO.getPageNO())){
				pageNo = Integer.valueOf(apiScenicSpotSelectiveQO.getPageNO());
			}
			if(StringUtils.isNotBlank(apiScenicSpotSelectiveQO.getPageSize())){
				pageSize = Integer.valueOf(apiScenicSpotSelectiveQO.getPageSize());
			}
			Pagination pagination = new Pagination();
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setCondition(scenicSpotSelectiveQO);
			pagination=scenicSpotSelectiveService.queryPagination(pagination);
			List<ScenicSpotDTO> scenicSpotDTOs = new ArrayList<ScenicSpotDTO>();
			List<ScenicSpotSelective> scenicSpotSelectives = (List<ScenicSpotSelective>) pagination.getList();
			for (ScenicSpotSelective scenicSpotSelective : scenicSpotSelectives) {
				ScenicSpot scenicSpot = scenicSpotService.get(scenicSpotSelective.getScenicSpotID());
				if(scenicSpot!=null){
					ScenicSpotDTO scenicSpotDTO =new ScenicSpotDTO();
					scenicSpotDTO.setScenicSpotID(scenicSpot.getId());
					if(scenicSpot.getBaseInfo()!=null){
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getName())){
							scenicSpotDTO.setName(scenicSpot.getBaseInfo().getName());
						}
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getCityId())){
							scenicSpotDTO.setCityName(dzpwCityService.get(scenicSpot.getBaseInfo().getCityId()).getName());
						}
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getShortName())){
							scenicSpotDTO.setShortName(scenicSpot.getBaseInfo().getShortName());
						}
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getFeature())){
							scenicSpotDTO.setIntro(scenicSpot.getBaseInfo().getFeature());
						}
						if(StringUtils.isNotBlank(scenicSpot.getBaseInfo().getLevel())){
							scenicSpotDTO.setLevel(scenicSpot.getBaseInfo().getLevel());
						}
					}
					scenicSpotDTO.setPlayPrice(scenicSpot.getPlayPrice());
					scenicSpotDTO.setRackRate(scenicSpot.getRackRate());
					ScenicSpotPicQO scenicSpotPicQO = new ScenicSpotPicQO();
					scenicSpotPicQO.setScenicSpotID(scenicSpot.getId());
					List<ScenicSpotPic> scenicSpotPics = scenicSpotPicService.queryList(scenicSpotPicQO);
					if(scenicSpotPics.size()!=0&&StringUtils.isNotBlank(scenicSpotPics.get(0).getUrl())){
						scenicSpotDTO.setUrl(scenicSpotPics.get(0).getUrl());
					}
					scenicSpotDTOs.add(scenicSpotDTO);
					
				}
			}
			scenicSpotResponse.setScenicSpotDTOs(scenicSpotDTOs);
			scenicSpotResponse.setPageNO(String.valueOf(pageNo));
			scenicSpotResponse.setPageSize(String.valueOf(pageSize));
			double totalPage = Math.ceil(Double.valueOf(pagination.getTotalCount())/ Double.valueOf(pageSize));
			if(totalPage<=pageNo){
				if(totalPage<pageNo){
					scenicSpotResponse.setScenicSpotDTOs(null);
				}
				scenicSpotResponse.setIsTheLastPage("y");
			}else{
				scenicSpotResponse.setIsTheLastPage("n");
			}
			scenicSpotResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().error("lxs_dev", HgLogger.getStackTrace(e));
			scenicSpotResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			scenicSpotResponse.setMessage("查询精选景区列表失败");
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryScenicSpotSelectiveAction】【scenicSpotResponse】"+JSON.toJSONString(scenicSpotResponse));
		return JSON.toJSONString(scenicSpotResponse);
	}

}
