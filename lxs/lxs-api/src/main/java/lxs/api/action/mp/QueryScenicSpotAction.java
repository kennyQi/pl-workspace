package lxs.api.action.mp;

import hg.common.page.Pagination;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.mp.ScenicSpotDTO;
import lxs.api.v1.request.qo.mp.ScenicSpotQO;
import lxs.api.v1.response.mp.ScenicSpotResponse;
import lxs.app.service.app.SubjectService;
import lxs.app.service.mp.DZPWCityService;
import lxs.app.service.mp.ScenicSpotPicService;
import lxs.app.service.mp.ScenicSpotService;
import lxs.domain.model.app.Subject;
import lxs.domain.model.mp.ScenicSpot;
import lxs.domain.model.mp.ScenicSpotPic;
import lxs.pojo.qo.mp.ScenicSpotPicQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryScenicSpotAction")
public class QueryScenicSpotAction implements LxsAction{

	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ScenicSpotPicService scenicSpotPicService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private DZPWCityService dzpwCityService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "进入【QueryScenicSpotAction】");
		ScenicSpotQO apiScenicSpotQO = JSON.parseObject(apiRequest.getBody().getPayload(), ScenicSpotQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QueryScenicSpotAction】【apiScenicSpotQO】"+JSON.toJSONString(apiScenicSpotQO));
		ScenicSpotResponse scenicSpotResponse = new ScenicSpotResponse();
		lxs.pojo.qo.mp.ScenicSpotQO scenicSpotQO = JSON.parseObject(apiRequest.getBody().getPayload(), lxs.pojo.qo.mp.ScenicSpotQO.class);
		try{
			int pageNo = 1;
			int pageSize = 10;
			if(StringUtils.isNotBlank(apiScenicSpotQO.getPageNO())){
				pageNo = Integer.valueOf(apiScenicSpotQO.getPageNO());
			}
			if(StringUtils.isNotBlank(apiScenicSpotQO.getPageSize())){
				pageSize = Integer.valueOf(apiScenicSpotQO.getPageSize());
			}
			Pagination pagination = new Pagination();
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			scenicSpotQO.setLocalStatus(ScenicSpot.SHOW);
			if(StringUtils.isNotBlank(apiScenicSpotQO.getSubjectID())){
				Subject subject = subjectService.get(apiScenicSpotQO.getSubjectID());
				if(subject!=null){
					scenicSpotQO.setTheme(subject.getSubjectName());
				}
			}
			pagination.setCondition(scenicSpotQO);
			pagination=scenicSpotService.queryPagination(pagination);
			List<ScenicSpot> scenicSpots = (List<ScenicSpot>) pagination.getList();
			List<ScenicSpotDTO> scenicSpotDTOs = new ArrayList<ScenicSpotDTO>();
			for (ScenicSpot scenicSpot : scenicSpots) {
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
			scenicSpotResponse.setMessage("查询景区列表失败");
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryScenicSpotAction】【scenicSpotResponse】"+JSON.toJSONString(scenicSpotResponse));
		return JSON.toJSONString(scenicSpotResponse);
	}

}
