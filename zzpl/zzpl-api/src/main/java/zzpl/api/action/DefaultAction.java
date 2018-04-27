package zzpl.api.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;

import com.alibaba.fastjson.JSON;

import hg.log.util.HgLogger;

@Component("DefaultAction")
public class DefaultAction implements CommonAction {

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
