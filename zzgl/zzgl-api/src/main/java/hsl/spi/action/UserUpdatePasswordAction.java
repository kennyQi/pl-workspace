package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.command.user.UserUpdatePasswordCommand;
import hsl.api.v1.response.user.UserUpdatePasswordResponse;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.spi.action.constant.Constants;
import hsl.spi.inter.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("userUpdatePasswordAction")
public class UserUpdatePasswordAction implements HSLAction {
	@Autowired
	private UserService userService;
	@Autowired
	private HgLogger hgLogger;
	@Override
	public String execute(ApiRequest apiRequest) {
		UserUpdatePasswordCommand userUpdatePasswordCommand = JSON.parseObject(
				apiRequest.getBody().getPayload(),
				UserUpdatePasswordCommand.class);
		hgLogger.info("liujz", "用户更新密码请求"+JSON.toJSONString(userUpdatePasswordCommand));
		return userUpdatePassword(userUpdatePasswordCommand);
	}

	/**
	 * 用户更新密码
	 * 
	 * @param userUpdatePasswordCommand
	 * @return
	 */
	public String userUpdatePassword(UserUpdatePasswordCommand userUpdatePasswordCommand) {
		
		hsl.pojo.command.UserUpdatePasswordCommand command=BeanMapperUtils.map(userUpdatePasswordCommand, hsl.pojo.command.UserUpdatePasswordCommand.class);
		UserUpdatePasswordResponse userUpdatePasswordResponse = new UserUpdatePasswordResponse();
		try {
			UserDTO userDTO = userService.updatePassword(command);
			userUpdatePasswordResponse.setUserDTO(userDTO);
			userUpdatePasswordResponse.setResult(ApiResponse.RESULT_CODE_OK);
			userUpdatePasswordResponse.setMessage("密码修改成功");
			hgLogger.info("chenxy", "密码修改成功");
		} catch (UserException e) {
			hgLogger.error("chenxy", "密码修改失败"+e.getMessage());
			userUpdatePasswordResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			userUpdatePasswordResponse.setMessage(e.getMessage());
		}
		hgLogger.info("liujz", "用户更新密码结果"+JSON.toJSONString(userUpdatePasswordResponse));
		return JSON.toJSONString(userUpdatePasswordResponse);
	}

}
