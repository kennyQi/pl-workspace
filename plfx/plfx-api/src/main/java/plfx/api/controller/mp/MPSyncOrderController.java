package plfx.api.controller.mp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.mp.request.command.MPSyncOrderCommand;
import plfx.api.client.api.v1.mp.response.MPSyncOrderResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.controller.base.SLFXAction;
import plfx.mp.spi.inter.api.ApiMPOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：同步订单状态Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:53:03
 * @版本：V1.0
 *
 */
@Component("mpSyncOrderController")
public class MPSyncOrderController implements SLFXAction {
	
	@Autowired
	private ApiMPOrderService apiMPOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		
		MPSyncOrderCommand command = JSON.toJavaObject(jsonObject, MPSyncOrderCommand.class);
		return syncMPOrder(command);
	}
	
	/**
	 * @方法功能说明：同步订单状态
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-26下午4:31:43
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String syncMPOrder(MPSyncOrderCommand command){
		MPSyncOrderResponse response = apiMPOrderService.syncOrder(command);
		return JSON.toJSONString(response);
	}

}
