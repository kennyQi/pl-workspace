package lxs.api.action.order.line;

import hg.common.util.SysProperties;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.v1.response.line.GetInsuranceResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("GetInsuranceAction")
public class GetInsuranceAction implements LxsAction {

	@Override
	public String execute(ApiRequest apiRequest) {
		GetInsuranceResponse getInsuranceResponse = new GetInsuranceResponse();
		getInsuranceResponse.setInsurance(SysProperties.getInstance().get("insurance"));
		return JSON.toJSONString(getInsuranceResponse);
	}

}
