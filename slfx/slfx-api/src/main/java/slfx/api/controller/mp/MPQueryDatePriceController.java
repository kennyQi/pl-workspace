package slfx.api.controller.mp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import slfx.api.base.ApiRequest;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.qo.mp.MPDatePriceQO;
import slfx.api.v1.response.mp.MPQueryDatePriceResponse;
import slfx.mp.spi.inter.api.ApiMPDatePriceService;

/**
 * 
 * @类功能说明：查询政策（门票）价格日历Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:54:12
 * @版本：V1.0
 *
 */
@Component("mpQueryDatePriceController")
public class MPQueryDatePriceController implements SLFXAction {
	
	@Autowired
	private ApiMPDatePriceService apiMPDatePriceService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		
		MPDatePriceQO qo = JSON.toJavaObject(jsonObject, MPDatePriceQO.class);
		return mpQueryDatePrice(qo);
	}
	
	/**
	 * @方法功能说明：查询政策（门票）价格日历
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-7上午10:35:49
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String mpQueryDatePrice(MPDatePriceQO qo) {
		MPQueryDatePriceResponse response = apiMPDatePriceService.apiQueryDateSalePrices(qo);
		return JSON.toJSONString(response);
	}

}
