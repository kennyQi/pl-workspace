package lxs.api.action.app;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.app.IntroductionDTO;
import lxs.api.v1.response.app.QueryIntroductionResponse;
import lxs.app.service.app.IntroductionService;
import lxs.domain.model.app.Introduction;
import lxs.pojo.qo.app.IntroductionQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryIntroductionAction")
public class QueryIntroductionAction implements LxsAction {

	@Autowired
	private IntroductionService introductionservice;

	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev","【QueryIntroductionAction】" + "进入action");
		QueryIntroductionResponse introductionResponse = new QueryIntroductionResponse();
		try {
			List<IntroductionDTO> introductiondtos = new ArrayList<IntroductionDTO>();
			IntroductionQO introductionqo = new IntroductionQO();
			introductionqo.setIntroductionType(Introduction.JQJJ);
			List<Introduction> introductions = introductionservice	.queryList(introductionqo);
			if (introductions != null && introductions.size() > 0) {
				for (Introduction introduction : introductions) {
					IntroductionDTO introductiondto = new IntroductionDTO();
					introductiondto.setId(introduction.getId());
					introductiondto.setIntroductionContent(introduction	.getIntroductionContent());
					introductiondto.setIntroductionType(introduction.getIntroductionType());
					introductiondtos.add(introductiondto);
				}
			} 
			introductionResponse.setIntroductionDtos(introductiondtos);
			introductionResponse.setMessage("查询成功");
			introductionResponse.setResult(ApiResponse.RESULT_CODE_OK);
			HgLogger.getInstance().info("lxs_dev","【QueryIntroductionAction】" + "查询成功");
		} catch (Exception e) {
			introductionResponse.setMessage("查询失败");
			introductionResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryIntroductionAction】"+"introductionResponse:"+JSON.toJSONString(introductionResponse));
		return JSON.toJSONString(introductionResponse);
	}

}
