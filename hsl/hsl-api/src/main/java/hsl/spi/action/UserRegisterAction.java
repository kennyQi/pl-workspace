package hsl.spi.action;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.base.ApiResponse;
import hsl.api.v1.request.command.user.UserRegisterCommand;
import hsl.api.v1.response.user.UserRegisterResponse;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.spi.action.constant.Constants;

import hsl.spi.inter.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("userRegisterAction")
public class UserRegisterAction implements HSLAction {
	@Autowired
	private UserService userService;
	@Autowired
	private HgLogger hgLogger;
	@Override
	public String execute(ApiRequest apiRequest) {
		UserRegisterCommand command = JSON.parseObject(apiRequest.getBody().getPayload(), UserRegisterCommand.class);
		hgLogger.info("liujz", "用户注册请求"+JSON.toJSONString(command));
		return register(command,apiRequest.getHead().getClientKey());
	}
	
	/**
	 * 注册
	 * @param command
	 * @return
	 */
	private String register(UserRegisterCommand command,String clientKey){
		UserRegisterResponse userRegisterResponse =new UserRegisterResponse();
		if(command.getType() == null){
			command.setType(1);
		}
		if(command.getType() == 1){
			//hsl.api.v1.request.command.user.UserRegisterCommand转hsl.spi.command.UserRegisterCommand
			hsl.pojo.command.UserRegisterCommand userRegisterCommand=BeanMapperUtils.map(command, hsl.pojo.command.UserRegisterCommand.class);
		
			try{
				UserDTO userDTO=userService.register(userRegisterCommand,clientKey);
				userRegisterResponse.setUserDTO(userDTO);
				userRegisterResponse.setResult(ApiResponse.RESULT_CODE_OK);
				userRegisterResponse.setMessage("注册成功");
				hgLogger.info("chenxy",userDTO.getAuthInfo().getLoginName()+"用户注册成功");
			}catch (UserException e){
				userRegisterResponse.setResult(Constants.exceptionMap.get(e.getCode()));
				userRegisterResponse.setMessage(e.getMessage());
				hgLogger.error("chenxy",command.getLoginName()+"用户注册失败>>"+e.getMessage());
			}
			hgLogger.info("liujz", "用户注册结果"+JSON.toJSONString(userRegisterResponse));
			return JSON.toJSONString(userRegisterResponse);
		}else{
			userRegisterResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			userRegisterResponse.setMessage("不允许注册企业用户");
			return JSON.toJSONString(userRegisterResponse);
		}
	}
	
}
