package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.qo.mp.MPPolicyQO;
import hsl.api.v1.response.mp.MPQueryPolicyResponse;
import hsl.pojo.dto.mp.PolicyDTO;
import hsl.pojo.qo.mp.HslMPPolicyQO;
import hsl.spi.inter.mp.MPScenicSpotService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("mpQueryPolicyAction")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MPQueryPolicyAction implements HSLAction {
	@Autowired
	private MPScenicSpotService scenicSpotService;
	@Autowired
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		MPPolicyQO mpPolicyQO = JSON.parseObject(apiRequest.getBody().getPayload(), MPPolicyQO.class);
		HslMPPolicyQO hslMPPolicyQO=BeanMapperUtils.map(mpPolicyQO, HslMPPolicyQO.class);
		
		hgLogger.info("liujz", "查询景点政策请求"+JSON.toJSONString(mpPolicyQO));
		MPQueryPolicyResponse policyResponse = new MPQueryPolicyResponse();
		Map policyMap = scenicSpotService.queryScenicPolicy(hslMPPolicyQO);
		policyResponse.setPolicies((List<PolicyDTO>)policyMap.get("dto"));
		policyResponse.setTotalCount(Integer.parseInt(policyMap.get("count").toString()));
		hgLogger.info("liujz", "景点政策查询结果"+JSON.toJSONString(policyResponse));
		return JSON.toJSONString(policyResponse);
	}
	
}
