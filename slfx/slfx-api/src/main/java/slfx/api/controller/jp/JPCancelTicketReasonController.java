package slfx.api.controller.jp;

import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.qo.jp.JPCancleOrderTicketReasonQO;
import slfx.api.v1.response.jp.JPCancelOrderTicketReasonResponse;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
import slfx.jp.spi.inter.JPPlatformOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：退废票原因查询Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:38:11
 * @版本：V1.0
 *
 */
@Component("jpCancelTicketReasonController")
public class JPCancelTicketReasonController implements SLFXAction {
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JPCancleOrderTicketReasonQO qo = JSON.toJavaObject(jsonObject, JPCancleOrderTicketReasonQO.class);
		
		return jPCancelTicketReason(qo.getPlatCode());
	}
	
	/**
	 * 
	 * @方法功能说明：退废票原因查询
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月22日下午5:37:38
	 * @修改内容：
	 * @参数：@param platCode
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String jPCancelTicketReason(String platCode) {
		HgLogger.getInstance().info("tandeng","JPCancelTicketReasonController->[查询机票颓废票原因]:"+platCode);
		YGRefundActionTypesDTO dto = null;
		try{
			dto = jpPlatformOrderService.getRefundActionType(platCode);
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng", "JPCancelTicketReasonController->[查询机票颓废票原因失败]:"+HgLogger.getStackTrace(e));
		}
		JPCancelOrderTicketReasonResponse response = new JPCancelOrderTicketReasonResponse();
		
		if(dto != null && dto.getActionTypeList()!=null){
			response.setMessage("成功");
			response.setResult(ApiResponse.RESULT_CODE_OK);
			response.setActionTypeList(dto.getActionTypeList());
		}else{
			response.setMessage("失败");
			response.setResult(ApiResponse.RESULT_CODE_FAILE);
			response.setActionTypeList(null);
		}
		HgLogger.getInstance().info("tandeng","SlfxApiController->jpQueryCityAirCode->JPCityAirCodeResponse[查询机票颓废票原因结果]:"+ JSON.toJSONString(response.getResult()));
		return JSON.toJSONString(response);
	}

}
