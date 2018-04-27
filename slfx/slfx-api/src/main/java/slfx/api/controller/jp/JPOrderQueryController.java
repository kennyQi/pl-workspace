package slfx.api.controller.jp;

import hg.common.page.Pagination;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.qo.jp.JPOrderQO;
import slfx.api.v1.response.jp.JPQueryOrderResponse;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.exception.JPOrderException;
import slfx.jp.qo.api.JPOrderSpiQO;
import slfx.jp.spi.inter.JPPlatformOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：机票订单查询Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:56:57
 * @版本：V1.0
 *
 */
@Component("jpOrderQueryController")
public class JPOrderQueryController implements SLFXAction {
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JPOrderQO qo = JSON.toJavaObject(jsonObject, JPOrderQO.class);
		
		return jpOrderQuery(qo);
	}
	
	/**
	 * 
	 * @方法功能说明：机票订单查询
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月6日上午10:29:23
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private String jpOrderQuery(JPOrderQO qo){
		HgLogger.getInstance().info("tandeng","JPOrderQueryController->[机票订单查询开始]:"+ JSON.toJSONString(qo));
		Pagination pagination = null;
		JPOrderSpiQO spiQO = new JPOrderSpiQO();
		String message = "失败";
		try{
			BeanUtils.copyProperties(qo, spiQO);
			pagination = jpPlatformOrderService.shopQueryOrderList(spiQO);
			HgLogger.getInstance().info("tandeng","slfx-api机票订单查询成功");
		}catch(JPOrderException e){
			message = e.getMessage();
			HgLogger.getInstance().error("tandeng", "JPOrderQueryController->[机票订单查询失败]:"+HgLogger.getStackTrace(e));
		}
		
		JPQueryOrderResponse jpQueryOrderResponse = null;
		
		if(pagination != null && pagination.getList() != null 
				&& pagination.getList().size() > 0){
			jpQueryOrderResponse = new JPQueryOrderResponse();
			jpQueryOrderResponse.setMessage("成功");
			jpQueryOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
			jpQueryOrderResponse.setJpOrders((List<JPOrderDTO>) pagination.getList());
			jpQueryOrderResponse.setTotalCount(pagination.getList().size());
		}else{
			jpQueryOrderResponse = new JPQueryOrderResponse();
			jpQueryOrderResponse.setMessage(message);
			jpQueryOrderResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
			jpQueryOrderResponse.setJpOrders(null);
			jpQueryOrderResponse.setTotalCount(null);
		}
		HgLogger.getInstance().info("tandeng","JPOrderQueryController->[机票订单查询结束]:"+ JSON.toJSONString(jpQueryOrderResponse));
		return JSON.toJSONString(jpQueryOrderResponse);
	}

}
