package lxs.api.action.app;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.app.RecommendDTO;
import lxs.api.v1.response.app.QueryRecommendResponse;
import lxs.app.service.app.RecommendService;
import lxs.domain.model.app.Recommend;
import lxs.pojo.qo.app.RecommendQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryRecommendAction")
public class QueryRecommendAction implements LxsAction {

	@Autowired
	private RecommendService recommendService;

	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev","【QueryRecommendAction】" + "进入action");
		QueryRecommendResponse recommendResponse = new QueryRecommendResponse();
		try {
			List<RecommendDTO> recommendDTOs = new ArrayList<RecommendDTO>();
			RecommendQO recommendQO = new RecommendQO();
			recommendQO.setStatus(Recommend.ON);
			List<Recommend> recommends = recommendService.queryList(recommendQO);
			HgLogger.getInstance().info("lxs_dev","【QueryRecommendAction】【recommends长度】" + recommends.size());
			if (recommends != null && recommends.size() > 0) {
				for (Recommend recommend : recommends) {
					RecommendDTO recommendDTO = new RecommendDTO();
					recommendDTO.setCreateDate(recommend.getCreateDate());
					recommendDTO.setImageURL(SysProperties.getInstance().get("fileUploadPath")+recommend.getImageURL());
					recommendDTO.setNote(recommend.getNote());
					recommendDTO.setRecommendAction(recommend.getRecommendAction());
					recommendDTO.setRecommendName(recommend.getRecommendName());
					recommendDTO.setRecommendType(recommend.getRecommendType());
					recommendDTOs.add(recommendDTO);
				}
				recommendResponse.setRecommendDTOs(recommendDTOs);
				recommendResponse.setMessage("查询成功");
				recommendResponse.setResult(ApiResponse.RESULT_CODE_OK);
				HgLogger.getInstance().info("lxs_dev","【QueryRecommendAction】【recommendDTOs长度】"+ recommendDTOs.size());
				HgLogger.getInstance().info("lxs_dev","【QueryRecommendAction】" + "查询推荐成功");
			} else {
				recommendResponse.setMessage("查询失败");
				recommendResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
				HgLogger.getInstance().info("lxs_dev","【QueryRecommendAction】" + "查询推荐失败");
			}
		} catch (Exception e) {
			recommendResponse.setMessage("查询失败");
			recommendResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryRecommendAction】"+"recommendResponse:"+JSON.toJSONString(recommendResponse));
		return JSON.toJSONString(recommendResponse);
	}

}
