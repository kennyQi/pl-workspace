package plfx.api.controller.jd;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plfx.api.client.api.v1.jd.request.qo.JDOrderApiQO;
import plfx.api.client.api.v1.jd.response.JDOrderDetailResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.controller.base.SLFXAction;
import plfx.jd.pojo.dto.ylclient.order.OrderDetailResultDTO;
import plfx.jd.pojo.qo.ylclient.JDOrderQO;
import plfx.jd.spi.inter.HotelOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：酒店订单详情api
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
@Component("jdOrderDetailController")
public class JDOrderDetailController implements SLFXAction{

	@Autowired
	private HotelOrderService hotelOrderService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		JDOrderApiQO apiQo = JSON.toJavaObject(jsonObject, JDOrderApiQO.class);
		
		return JDOrderDetail(apiQo);
	}
	/**
	 * 
	 * @方法功能说明：酒店订单查询
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月11日下午2:02:59
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String JDOrderDetail(JDOrderApiQO apiQo){
		
		HgLogger.getInstance().info("caizhenghao", "JDOrderDetailController->[查询酒店订单开始]:" + JSON.toJSONString(apiQo));
		OrderDetailResultDTO result = null;
		String message = "查询订单失败";
		try{
			
			//"apiCommand"转换"分销Command"
			JDOrderQO qo = new JDOrderQO();
			BeanUtils.copyProperties(apiQo,qo);
			
			result = hotelOrderService.queryOrder(qo);
			
		}catch(Exception e){
			HgLogger.getInstance().info("caizhenghao", "JDOrderDetailController->[查询订单异常]:" + HgLogger.getStackTrace(e));
		}
		
		
		JDOrderDetailResponse jdOrderDetailResponse = new JDOrderDetailResponse();
		if(result != null){
			jdOrderDetailResponse.setMessage("查询订单成功");
			jdOrderDetailResponse.setOrderDetailResultDTO(result);
			jdOrderDetailResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			jdOrderDetailResponse.setMessage(message);
			jdOrderDetailResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("caizhenghao", "JDOrderDetailController->[查询订单结束]:" + JSON.toJSONString(jdOrderDetailResponse));
		return JSON.toJSONString(jdOrderDetailResponse);
	}
	
	
	

}
