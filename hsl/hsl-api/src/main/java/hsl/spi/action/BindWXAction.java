package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.command.user.BindWXCommand;
import hsl.api.v1.response.user.BindWXResponse;
import hsl.pojo.dto.user.UserBindAccountDTO;
import hsl.pojo.exception.UserException;
import hsl.spi.action.constant.Constants;
import hsl.spi.inter.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("bindWXAction")
public class BindWXAction implements HSLAction {
	@Autowired
	private UserService userService;
	@Autowired
	private HgLogger hgLogger;
	@Override
	public String execute(ApiRequest apiRequest) {
		BindWXCommand bindWXCommand = JSON.parseObject(apiRequest.getBody()
				.getPayload(), BindWXCommand.class);
		hgLogger.info("liujz", "绑定微信请求"+JSON.toJSONString(bindWXCommand));
		return bindWX(bindWXCommand);
	}

	/**
	 * 绑定微信
	 * 
	 * @param bindWXCommand
	 * @return
	 */
	private String bindWX(BindWXCommand bindWXCommand) {
		BindWXResponse bindWXResponse = new BindWXResponse();
		hsl.pojo.command.BindWXCommand command=BeanMapperUtils.map(bindWXCommand, hsl.pojo.command.BindWXCommand.class);
		try {
			UserBindAccountDTO userBindAccountDTO = userService.bindWXCommand(command);
			bindWXResponse.setUserBindAccountDTO(userBindAccountDTO);
			bindWXResponse.setResult(ApiResponse.RESULT_CODE_OK);
			bindWXResponse.setMessage("绑定成功");
		} catch (UserException e) {
			bindWXResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			bindWXResponse.setMessage(e.getMessage());
		}
		hgLogger.info("liujz", "绑定微信"+JSON.toJSONString(bindWXResponse));
		return JSON.toJSONString(bindWXResponse);
	}

}
