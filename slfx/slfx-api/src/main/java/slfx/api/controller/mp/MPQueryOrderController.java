package slfx.api.controller.mp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import slfx.api.base.ApiRequest;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.qo.mp.MPOrderQO;
import slfx.api.v1.response.mp.MPQueryOrderResponse;
import slfx.mp.spi.inter.api.ApiMPOrderService;

/**
 * 
 * @类功能说明：门票订单查询Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:53:50
 * @版本：V1.0
 *
 */
@Component("mpQueryOrderController")
public class MPQueryOrderController implements SLFXAction {
	@Autowired
	private ApiMPOrderService apiMPOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		
		MPOrderQO qo = JSON.toJavaObject(jsonObject, MPOrderQO.class);
		return mpQueryOrder(qo);
	}
	
	/**
	 * @方法功能说明：订单查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-7上午10:36:50
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String mpQueryOrder(MPOrderQO qo) {
		MPQueryOrderResponse response = apiMPOrderService.apiQueryOrder(qo);
		return JSON.toJSONString(response);
	}

}
