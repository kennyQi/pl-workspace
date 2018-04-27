package lxs.api.action;

import javax.annotation.Resource;

import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import hg.log.util.HgLogger;

@Component("defaultAction")
public class DefaultAction implements LxsAction {

	@Resource
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		ApiResponse response = new ApiResponse();
		response.setMessage("找不到Action");
		response.setResult(ApiResponse.RESULT_CODE_FAIL);
		
		hgLogger.info("zhangka", "DefaultAction->execute->response[默认Action]:" +JSON.toJSONString(response.getResult()));
		
		return JSON.toJSONString(response);
	}

}
