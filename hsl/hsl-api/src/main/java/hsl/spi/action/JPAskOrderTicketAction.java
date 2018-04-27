package hsl.spi.action;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.response.jp.JPAskOrderTicketResponse;
import hsl.spi.inter.api.jp.JPService;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;

import com.alibaba.fastjson.JSON;

@Component("jpAskOrderTicketAction")
public class JPAskOrderTicketAction implements HSLAction {
	@Resource
	private JPService jpService;
	
	@Resource
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		JPAskOrderTicketCommand jpAskOrderTicketCommand = JSON.parseObject(apiRequest.getBody().getPayload(), JPAskOrderTicketCommand.class);
		
		HgLogger.getInstance().info("zhangka", "JPAskOrderTicketAction->execute->jpAskOrderTicketCommand[请求出票]:" + JSON.toJSONString(jpAskOrderTicketCommand));
		
		jpService.askOrderTicket(jpAskOrderTicketCommand);
		
		JPAskOrderTicketResponse response = new JPAskOrderTicketResponse();
		
		response.setMessage("成功！");
		response.setResult(JPAskOrderTicketResponse.RESULT_CODE_OK);
		
		HgLogger.getInstance().info("zhangka", "JPAskOrderTicketAction->execute->response[请求出票]:" + JSON.toJSONString(response.getResult()));
		
		return JSON.toJSONString(response.getResult());
	}

}
