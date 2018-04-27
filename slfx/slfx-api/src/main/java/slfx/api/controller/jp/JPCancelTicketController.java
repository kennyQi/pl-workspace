package slfx.api.controller.jp;

import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
import slfx.api.v1.response.jp.JPCancelOrderTicketResponse;
import slfx.jp.command.admin.jp.JPCancelTicketSpiCommand;
import slfx.jp.pojo.exception.JPOrderException;
import slfx.jp.spi.inter.JPPlatformOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：订单取消Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:57:24
 * @版本：V1.0
 *
 */
@Component("jpCancelTicketController")
public class JPCancelTicketController implements SLFXAction {
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		
		JPCancelTicketCommand command = JSON.toJavaObject(jsonObject, JPCancelTicketCommand.class);
		return jpCancelOrderTicket(command);
	}

	/**
	 * 
	 * @方法功能说明：订单取消
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月6日上午10:31:40
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String jpCancelOrderTicket(JPCancelTicketCommand command){
		HgLogger.getInstance().info("tandeng","JPCancelTicketController->[订单取消开始]:"+ JSON.toJSONString(command));
		boolean bool = false;
		String ticketNumbers = command.getTicketNumbers();
		
		JPCancelTicketSpiCommand spiCommand = new JPCancelTicketSpiCommand();
		
		String message = "失败";
		try{
			BeanUtils.copyProperties(command, spiCommand);
			if (StringUtils.isBlank(ticketNumbers)) {//不传票号  取消订单
				bool = jpPlatformOrderService.shopCancelOrder(spiCommand);
			} else {//传票号，是退废票(根据废票时间自行判断)
				bool = jpPlatformOrderService.shopRefundOrder(spiCommand);
			}
		}catch(JPOrderException e){
			message = e.getMessage();
			HgLogger.getInstance().error("tandeng", "JPCancelTicketController->[订单取消失败]:"+HgLogger.getStackTrace(e));
		}
		
		JPCancelOrderTicketResponse jpCancelOrderTicketResponse = null;
		
		if(bool){
			jpCancelOrderTicketResponse = new JPCancelOrderTicketResponse();
			jpCancelOrderTicketResponse.setMessage("成功");
			jpCancelOrderTicketResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			jpCancelOrderTicketResponse = new JPCancelOrderTicketResponse();
			jpCancelOrderTicketResponse.setMessage(message);
			jpCancelOrderTicketResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		HgLogger.getInstance().info("tandeng","JPCancelTicketController->[订单取消结束]:"+ JSON.toJSONString(jpCancelOrderTicketResponse));
		return JSON.toJSONString(jpCancelOrderTicketResponse);
		
	}
}
