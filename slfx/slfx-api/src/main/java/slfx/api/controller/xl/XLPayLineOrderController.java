package slfx.api.controller.xl;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.command.xl.XLPayLineOrderApiCommand;
import slfx.api.v1.response.xl.XLPayLineOrderResponse;
import slfx.xl.pojo.command.pay.BatchPayLineOrderCommand;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.spi.inter.LineOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：api接口支付线路订单Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月29日上午9:48:53
 * @版本：V1.0
 *
 */
@Component("xlPayLineOrderController")
public class XLPayLineOrderController implements SLFXAction{

	@Autowired
	private LineOrderService lineOrderService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		
		XLPayLineOrderApiCommand apiCommand = JSON.toJavaObject(jsonObject, XLPayLineOrderApiCommand.class);
		return xlPayLineOrder(apiCommand);
		
	}
	
	public String xlPayLineOrder(XLPayLineOrderApiCommand apiCommand){
		
		HgLogger.getInstance().info("luoyun", "XLPayLineOrderController->[支付线路订单开始]:" + JSON.toJSONString(apiCommand));
		Boolean payResult = false;
		String message = "支付订单失败";
		try{
			
			//"apiCommand"转换"分销Command"
			BatchPayLineOrderCommand command = new BatchPayLineOrderCommand();
			BeanUtils.copyProperties(apiCommand,command);
			
			payResult = lineOrderService.shopPayLineOrder(command);
			
		}catch(SlfxXlException e){
			message = e.getMessage();
			HgLogger.getInstance().info("luoyun", "XLCancleLineOrderController->[支付线路订单异常]:" + HgLogger.getStackTrace(e));
		}catch(Exception e){
			HgLogger.getInstance().info("luoyun", "XLCancleLineOrderController->[支付线路订单异常]:" + HgLogger.getStackTrace(e));
		}
		
		XLPayLineOrderResponse xlPayLineOrderResponse = new XLPayLineOrderResponse();
		if(payResult){
			xlPayLineOrderResponse.setMessage("支付订单成功");
			xlPayLineOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			xlPayLineOrderResponse.setMessage(message);
			xlPayLineOrderResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("luoyun", "XLPayLineOrderController->[支付线路订单结束]:" + JSON.toJSONString(xlPayLineOrderResponse));
		return JSON.toJSONString(xlPayLineOrderResponse);
	}

}
