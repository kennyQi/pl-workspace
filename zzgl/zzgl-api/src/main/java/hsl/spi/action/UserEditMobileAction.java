package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.command.user.UserEditCommand;
import hsl.api.v1.response.user.UserEditMobileResponse;
import hsl.api.v1.response.user.UserEditResponse;
import hsl.pojo.command.UpdateBindMobileCommand;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("userEditMobileAction")
public class UserEditMobileAction implements HSLAction{
	@Autowired
	private UserService userService;
	@Autowired
	private HgLogger hgLogger;
	@Override
	public String execute(ApiRequest apiRequest) {
		UpdateBindMobileCommand command = JSON.parseObject(
				apiRequest.getBody().getPayload(),
				UpdateBindMobileCommand.class);
		hgLogger.info("yuqz", "用户修改手机请求"+JSON.toJSONString(command));
		return userEditMobile(command);
	}
	
	
	private String userEditMobile(UpdateBindMobileCommand command) {
		UserEditMobileResponse userEditMobileResponse = new UserEditMobileResponse();
		if(command.getUserId() == null){
			userEditMobileResponse.setResult(userEditMobileResponse.RESULT_USERID_NULL);
			userEditMobileResponse.setMessage("用户id不存在");
			return JSON.toJSONString(userEditMobileResponse);
		}
		if(command.getMobile() == null){
			userEditMobileResponse.setResult(userEditMobileResponse.RESULT_MOBILE_NULL);
			userEditMobileResponse.setMessage("手机号码不存在");
			return JSON.toJSONString(userEditMobileResponse);
		}
		HslUserQO qo = new HslUserQO();
		qo.setId(command.getUserId());
		UserDTO user = userService.queryUnique(qo);
		if(user == null){
			userEditMobileResponse.setResult(userEditMobileResponse.RESULT_USER_NOTEXIST);
			userEditMobileResponse.setMessage("用户不存在");
			return JSON.toJSONString(userEditMobileResponse);
		}
		
		hsl.pojo.command.UpdateBindMobileCommand updateBindMobileCommand = BeanMapperUtils.map(command, hsl.pojo.command.UpdateBindMobileCommand.class);
		UserDTO userDTO = new UserDTO();
		userDTO = userService.updateBindMobile(updateBindMobileCommand);
		userEditMobileResponse.setUserDTO(userDTO);
		userEditMobileResponse.setResult(userEditMobileResponse.RESULT_CODE_OK);
		userEditMobileResponse.setMessage("修改手机成功");
		
		return JSON.toJSONString(userEditMobileResponse);
	}

}
