package slfx.api.controller.xl;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.command.xl.XLCancelLineOrderApiCommand;
import slfx.api.v1.response.xl.XLCancelLineOrderResponse;
import slfx.xl.pojo.command.order.CancleLineOrderCommand;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.spi.inter.LineOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：api接口取消线路订单Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月26日下午3:56:17
 * @版本：V1.0
 *
 */
@Component("xlCancleLineOrderController")
public class XLCancleLineOrderController implements SLFXAction{

	@Autowired
	private LineOrderService lineOrderService;

	
	@Override
	public String execute(ApiRequest apiRequest) {
		
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		XLCancelLineOrderApiCommand apiCommand = JSON.toJavaObject(jsonObject, XLCancelLineOrderApiCommand.class);
		
		return XLCancelLineOrder(apiCommand);
	}
	
	/**
	 * 
	 * @方法功能说明：取消线路订单
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月26日下午4:00:01
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String XLCancelLineOrder(XLCancelLineOrderApiCommand apiCommand){
		
		HgLogger.getInstance().info("luoyun", "XLCancleLineOrderController->[取消线路订单开始]:" + JSON.toJSONString(apiCommand));
		Boolean cancleResult = false;
		String message = "取消订单失败";
		try{
			
			//"apiCommand"转换"分销Command"
			CancleLineOrderCommand command = new CancleLineOrderCommand();
			BeanUtils.copyProperties(apiCommand,command);
			
			cancleResult = lineOrderService.shopCancleLineOrder(command);
			
		}catch(SlfxXlException e){
			message = e.getMessage();
			HgLogger.getInstance().info("luoyun", "XLCancleLineOrderController->[取消线路订单异常]:" + HgLogger.getStackTrace(e));
			
		}catch(Exception e){
			HgLogger.getInstance().info("luoyun", "XLCancleLineOrderController->[取消线路订单异常]:" + HgLogger.getStackTrace(e));
		}
		
		
		XLCancelLineOrderResponse xlCancelLineOrderResponse = new XLCancelLineOrderResponse();
		if(cancleResult){
			xlCancelLineOrderResponse.setMessage("取消订单成功");
			xlCancelLineOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			xlCancelLineOrderResponse.setMessage(message);
			xlCancelLineOrderResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		
		HgLogger.getInstance().info("luoyun", "XLCancleLineOrderController->[取消线路订单结束]:" + JSON.toJSONString(xlCancelLineOrderResponse));
		return JSON.toJSONString(xlCancelLineOrderResponse);
	}
	
	
	

}
