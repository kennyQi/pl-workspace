package lxs.api.action.user;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.user.LoginCommand;
import lxs.api.v1.response.user.LoginResponse;


import lxs.app.service.user.LxsUserService;
import lxs.domain.model.user.LxsUser;
import lxs.pojo.exception.user.UserException;
import lxs.pojo.qo.user.user.LxsUserQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("LoginAction")
public class LoginAction implements LxsAction{

	@Autowired
	private LxsUserService userService;
	@Autowired
	private HgLogger hgLogger;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",  "【LoginAction】"+"进入action");
		LoginCommand apiLoginCommand = JSON.parseObject(apiRequest.getBody().getPayload(), LoginCommand.class);
		LoginResponse loginResponse = new LoginResponse();
		try{
			if(apiLoginCommand.getLoginName()!=null&&StringUtils.isNotBlank(apiLoginCommand.getLoginName())&&apiLoginCommand.getPassword()!=null&&StringUtils.isNotBlank(apiLoginCommand.getPassword())){
				LxsUserQO userQO = new LxsUserQO();
				userQO.setLoginName(apiLoginCommand.getLoginName());
				userQO.setPassword(apiLoginCommand.getPassword());
				HgLogger.getInstance().info("lxs_dev", "【LoginAction】"+userQO.getLoginName() + "登录开始");
				LxsUser user = new LxsUser();
				user=userService.queryUnique(userQO);
				try{
					if(user!=null){
						//登录成功
						userService.Login(user);
						user=userService.queryUnique(userQO);
						if(apiLoginCommand.getQueryUserInfo()){
							//返回UserDTO
							loginResponse.setUser(QueryUserAction.userToUserDTO(user));
						}
						loginResponse.setMessage("登录成功");
						loginResponse.setResult(ApiResponse.RESULT_CODE_OK);
						HgLogger.getInstance().info("lxs_dev", userQO.getLoginName() + "登录成功");
					}else{
						throw new UserException(UserException.RESULT_AUTH_FAIL, "用户名或密码错误");
					}
				}catch(UserException e){
					HgLogger.getInstance().info("lxs_dev", "【LoginAction】"+userQO.getLoginName() + "登录失败，"+e.getMessage());
					loginResponse.setMessage(e.getMessage());
					loginResponse.setResult(e.getCode());
				}
			}else{
				throw new UserException(UserException.RESULT_USERNAME_OR_PASSWORD_IS_ENPTY, "用户名或密码不能为空");
			}
		}catch(UserException e){
			loginResponse.setMessage(e.getMessage());
			loginResponse.setResult(e.getCode());
		}
		HgLogger.getInstance().info("lxs_dev","【LoginAction】"+"登录结果"+JSON.toJSONString(loginResponse));
		return JSON.toJSONString(loginResponse);
	}

}
