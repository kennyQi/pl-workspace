package hsl.spi.action;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.response.jp.JPQueryPolicyResponse;
import hsl.pojo.dto.jp.FlightPolicyDTO;
import hsl.spi.inter.api.jp.JPService;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import slfx.api.v1.request.qo.jp.JPPolicyQO;

import com.alibaba.fastjson.JSON;

@Component("jpQueryFlightPolicyAction")
public class JPQueryFlightPolicyAction implements HSLAction {
	@Resource
	private JPService jpService;
	
	@Resource
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		JPPolicyQO jpPolicyQO = JSON.parseObject(apiRequest.getBody().getPayload(), JPPolicyQO.class);
		
		HgLogger.getInstance().info("zhangka", "JPQueryFlightPolicyAction->execute->jpPolicyQO[航班政策查询]:" + JSON.toJSONString(jpPolicyQO));
		
		FlightPolicyDTO flightPolicyDTO = jpService.queryFlightPolicy(jpPolicyQO);
		
		JPQueryPolicyResponse response = new JPQueryPolicyResponse();
		
		if (flightPolicyDTO == null) {
			response.setMessage("失败！");
			response.setResult(JPQueryPolicyResponse.RESULT_CODE_FAIL);
		} else {
			response.setMessage("成功");
			response.setFlightPolicyDTO(flightPolicyDTO);
		}
		
		HgLogger.getInstance().info("zhangka", "JPQueryFlightPolicyAction->execute->response[航班政策查询]:" +JSON.toJSONString(response.getResult()));
		
		return JSON.toJSONString(response);
	}

}
