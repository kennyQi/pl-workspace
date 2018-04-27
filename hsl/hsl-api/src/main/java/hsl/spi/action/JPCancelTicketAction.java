package hsl.spi.action;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.response.jp.JPCancelOrderTicketResponse;
import hsl.pojo.dto.jp.JPOrderDTO;
import hsl.spi.inter.api.jp.JPService;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import slfx.api.v1.request.command.jp.JPCancelTicketCommand;

import com.alibaba.fastjson.JSON;

@Component("jpCancelTicketAction")
public class JPCancelTicketAction implements HSLAction {
	@Resource
	private JPService jpService;
	
	@Resource
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		JPCancelTicketCommand jpCancelTicketCommand = JSON.parseObject(apiRequest.getBody().getPayload(), JPCancelTicketCommand.class);
		
		HgLogger.getInstance().info("zhangka", "JPCancelTicketAction->execute->jpCancelTicketCommand[取消订单]:" + JSON.toJSONString(jpCancelTicketCommand));
		
		JPOrderDTO jpOrderDTO = jpService.cancelTicket(jpCancelTicketCommand);
		
		JPCancelOrderTicketResponse response = new JPCancelOrderTicketResponse();
		if (jpOrderDTO == null) {
			response.setResult(JPCancelOrderTicketResponse.RESULT_CODE_OK);
			response.setMessage("失败");
		} else {
			response.setResult(JPCancelOrderTicketResponse.RESULT_CODE_FAIL);
			response.setMessage("成功");
			response.setStatus(jpOrderDTO.getStatus());
		}
		
		HgLogger.getInstance().info("zhangka", "JPCancelTicketAction->execute->response[取消订单]:" +JSON.toJSONString(response.getResult()));
		return JSON.toJSONString(response);
	}

}
