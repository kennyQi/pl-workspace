package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.command.user.SendValidCodeCommand;
import hsl.api.v1.response.user.SendValidCodeResponse;
import hsl.pojo.exception.UserException;
import hsl.spi.action.constant.Constants;
import hsl.spi.inter.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("sendValidCodeAction")
public class SendValidCodeAction implements HSLAction {
	
	@Autowired
	private UserService userService;

	@Autowired
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		SendValidCodeCommand sendValidCodeCommand = JSON.parseObject(apiRequest
				.getBody().getPayload(), SendValidCodeCommand.class);
		hgLogger.info("liujz", "发送短信验证码请求"+JSON.toJSONString(sendValidCodeCommand));
		return sendValidCode(sendValidCodeCommand);
	}

	/**
	 * 发送验证码
	 * 
	 * @param command
	 * @return
	 */
	private String sendValidCode(SendValidCodeCommand command) {
		//hsl.api.v1.request.command.user.SendValidCodeCommand转成hsl.spi.command.SendValidCodeCommand
		hsl.pojo.command.SendValidCodeCommand sendValidCodeCommand=BeanMapperUtils.map(command, hsl.pojo.command.SendValidCodeCommand.class);
		
		SendValidCodeResponse sendValidCodeResponse = new SendValidCodeResponse();
		try {
			String token = userService.sendValidCode(sendValidCodeCommand);
			sendValidCodeResponse.setToken(token);
			sendValidCodeResponse.setResult(ApiResponse.RESULT_CODE_OK);
			hgLogger.info("chenxy", "短信验证码发送成功");
			sendValidCodeResponse.setMessage("验证码已发送");
		} catch (UserException e) {
			hgLogger.info("chenxy", "短信验证码发送失败"+e.getMessage());
			sendValidCodeResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			sendValidCodeResponse.setMessage(e.getMessage());
		}
		hgLogger.info("liujz", "短信验证码发送结果"+ JSON.toJSONString(sendValidCodeResponse));
		return JSON.toJSONString(sendValidCodeResponse);
	}

}
