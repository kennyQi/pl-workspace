package plfx.api.controller.mp;

import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.mp.request.command.MPOrderCancelCommand;
import plfx.api.client.api.v1.mp.response.MPOrderCancelResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.controller.base.SLFXAction;
import plfx.mp.spi.inter.api.ApiMPOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：取消门票Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:54:27
 * @版本：V1.0
 *
 */
@Component("mpOrderCancelController")
public class MPOrderCancelController implements SLFXAction {
	
	@Autowired
	private ApiMPOrderService apiMPOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		
		MPOrderCancelCommand command = JSON.toJavaObject(jsonObject, MPOrderCancelCommand.class);
		return mpOrderCancel(command);
	}
	
	/**
	 * @方法功能说明：取消门票
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-27下午1:27:25
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String mpOrderCancel(MPOrderCancelCommand command){
		HgLogger.getInstance().info("tandeng","SLFX-API(取消门票)->SlfxApiController->mpOrderCancel->开始执行");
		MPOrderCancelResponse response = apiMPOrderService.apiCancelOrder(command);
		HgLogger.getInstance().info("tandeng","SLFX-API(取消门票)->SlfxApiController->mpOrderCancel->开始结束");
		return JSON.toJSONString(response);
	}

}
