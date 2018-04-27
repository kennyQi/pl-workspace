package slfx.api.controller.jp;

import hg.log.util.HgLogger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;
import slfx.api.v1.response.jp.JPAskOrderTicketResponse;
import slfx.jp.command.admin.jp.JPAskOrderTicketSpiCommand;
import slfx.jp.pojo.exception.JPOrderException;
import slfx.jp.spi.inter.JPPlatformOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：请求出票Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午5:22:07
 * @版本：V1.0
 *
 */
@Component("jpAskOrderTicketController")
public class JPAskOrderTicketController implements SLFXAction {
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		
		JPAskOrderTicketCommand command = JSON.toJavaObject(jsonObject, JPAskOrderTicketCommand.class);
		return jpAskOrderTicket(command);
	}
	
	/**
	 * 
	 * @方法功能说明：请求出票
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月6日上午10:30:58
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String jpAskOrderTicket(JPAskOrderTicketCommand command){
		HgLogger.getInstance().info("tandeng","JPAskOrderTicketController->[请求出票开始]:"+ JSON.toJSONString(command));
		boolean bool = false;
		JPAskOrderTicketSpiCommand spiCommand = new JPAskOrderTicketSpiCommand();
		String message = "失败";
		try{
			BeanUtils.copyProperties(command, spiCommand);
			bool = jpPlatformOrderService.shopAskOrderTicket(spiCommand);
		}catch(JPOrderException e){
			message = e.getMessage();
			HgLogger.getInstance().error("tandeng", "JPAskOrderTicketController->[请求出票失败]:"+HgLogger.getStackTrace(e));
		} 
		
		JPAskOrderTicketResponse jpAskOrderTicketResponse = null;
		
		if(bool){
			jpAskOrderTicketResponse = new JPAskOrderTicketResponse();
			jpAskOrderTicketResponse.setMessage("成功");
			jpAskOrderTicketResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}else{
			jpAskOrderTicketResponse = new JPAskOrderTicketResponse();
			jpAskOrderTicketResponse.setMessage(message);
			jpAskOrderTicketResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		HgLogger.getInstance().info("tandeng","JPAskOrderTicketController->[请求出票结束]:"+ JSON.toJSONString(jpAskOrderTicketResponse));
		return JSON.toJSONString(jpAskOrderTicketResponse);
		
	}

}
