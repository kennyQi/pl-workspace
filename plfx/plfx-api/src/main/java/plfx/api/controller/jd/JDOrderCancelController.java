package plfx.api.controller.jd;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.jd.request.command.JDOrderCancelApiCommand;
import plfx.api.client.api.v1.jd.response.JDOrderCancelResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.jd.pojo.command.plfx.ylclient.JDOrderCancelCommand;
import plfx.jd.pojo.dto.ylclient.order.OrderCancelResultDTO;
import plfx.jd.spi.inter.HotelOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：酒店订单取消api
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年2月11日下午2:03:25
 * @版本：V1.0
 *
 */
@Component("jdOrderCancelController")
public class JDOrderCancelController implements SLFXAction{

	@Autowired
	private HotelOrderService hotelOrderService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JDOrderCancelApiCommand apiCommand = JSON.toJavaObject(jsonObject, JDOrderCancelApiCommand.class);
		
		return JDOrderCancel(apiCommand);
	}
	/**
	 * 
	 * @方法功能说明：酒店订单取消
	 * 当订单提交成功后返回的结果中CancelTime属性，
	 * 当时间在CancelTime的时间之前，可以通过本接口取消该订单；
	 * 如果是担保订单，请遵循担保规则中关于取消的条款。
                     须使用https访问本接口。
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月11日下午2:02:59
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String JDOrderCancel(JDOrderCancelApiCommand apiCommand){
		
		HgLogger.getInstance().info("yaosanfeng", "JDOrderCancelController->[取消酒店订单开始]:" + JSON.toJSONString(apiCommand));
		OrderCancelResultDTO result = null;
		String message = "取消订单失败";
		try{
			
			//"apiCommand"转换"分销Command"
			JDOrderCancelCommand command = new JDOrderCancelCommand();
			BeanUtils.copyProperties(apiCommand,command);
			
			result = hotelOrderService.orderCancel(command);
			
		}catch(Exception e){
			HgLogger.getInstance().info("yaosanfeng", "JDOrderCancelController->[取消酒店订单异常]:" + HgLogger.getStackTrace(e));
		}
		
		
		JDOrderCancelResponse jdOrderCancelResponse = new JDOrderCancelResponse();
		if(result != null && result.isSuccesss()){
			jdOrderCancelResponse.setMessage("取消订单成功");
			jdOrderCancelResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			jdOrderCancelResponse.setMessage(message);
			jdOrderCancelResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("yaosanfeng", "JDOrderCancelController->[取消酒店订单结束]:" + JSON.toJSONString(jdOrderCancelResponse));
		return JSON.toJSONString(jdOrderCancelResponse);
	}
	
	
	

}
