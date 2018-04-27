package slfx.api.controller.jp;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.qo.jp.JPPolicyQO;
import slfx.api.v1.response.jp.JPQueryPolicyResponse;
import slfx.jp.pojo.dto.flight.SlfxFlightPolicyDTO;
import slfx.jp.pojo.exception.JPException;
import slfx.jp.qo.api.JPPolicySpiQO;
import slfx.jp.spi.inter.FlightPolicyService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：查询航班政策Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:55:56
 * @版本：V1.0
 *
 */
@Component("jpQueryFlightPolicyController")
public class JPQueryFlightPolicyController implements SLFXAction {
	
	@Autowired
	private FlightPolicyService flightPolicyService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JPPolicyQO qo = JSON.toJavaObject(jsonObject, JPPolicyQO.class);
		
		return jpQueryFlightPolicy(qo);
	}
	
	/**
	 * 
	 * @方法功能说明：查询航班政策
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月6日上午10:27:49
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String jpQueryFlightPolicy(JPPolicyQO apiQO){
		HgLogger.getInstance().info("tandeng","JPQueryFlightPolicyController->[查询航班政策开始]:"+ JSON.toJSONString(apiQO));
		SlfxFlightPolicyDTO policys = null;
		JPPolicySpiQO jpPolicySpiQO = new JPPolicySpiQO();
		BeanUtils.copyProperties(apiQO,jpPolicySpiQO);
		String message = "失败";
		try{
			policys = flightPolicyService.queryPlatformPolicy(jpPolicySpiQO);
		}catch(JPException e){
			message = e.getMessage();
			HgLogger.getInstance().error("tandeng", "JPQueryFlightPolicyController->[查询航班政策失败]"+HgLogger.getStackTrace(e));
		}
		
		JPQueryPolicyResponse queryPolicyResponse = null;
		
		if(policys != null){
			queryPolicyResponse = new JPQueryPolicyResponse();
			queryPolicyResponse.setMessage("成功");
			queryPolicyResponse.setResult(ApiResponse.RESULT_CODE_OK);
			queryPolicyResponse.setSlfxFlightPolicyDTO(policys);
		}else{
			queryPolicyResponse = new JPQueryPolicyResponse();
			queryPolicyResponse.setMessage(message);
			queryPolicyResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
			queryPolicyResponse.setSlfxFlightPolicyDTO(null);
		}
		HgLogger.getInstance().info("tandeng","JPQueryFlightPolicyController->[查询航班政策结束]:"+ JSON.toJSONString(queryPolicyResponse.getResult()));
		return JSON.toJSONString(queryPolicyResponse);
	}

}
