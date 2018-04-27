package lxs.api.action.user;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.user.ChangePasswordCommand;
import lxs.api.v1.response.user.ChangePasswordResponse;
import lxs.app.service.user.LxsUserService;
import lxs.domain.model.user.LxsUser;
import lxs.pojo.exception.user.UserException;
import lxs.pojo.qo.user.user.LxsUserQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("ChangePasswordAction")
public class ChangePasswordAction implements LxsAction {
	@Autowired
	private LxsUserService userService;
	@Autowired
	private HgLogger hgLogger;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",  "【ChangePasswordAction】"+"进入action");
		ChangePasswordCommand apiChangePasswordCommand = JSON.parseObject(apiRequest.getBody().getPayload(), ChangePasswordCommand.class);
		lxs.pojo.command.user.ChangePasswordCommand command = new lxs.pojo.command.user.ChangePasswordCommand();
		command.setNewPassWord(apiChangePasswordCommand.getNewPassWord());
		LxsUserQO userQO = new LxsUserQO();
		userQO.setId(apiChangePasswordCommand.getUserId());
		LxsUser user = new LxsUser();
		user=userService.queryUnique(userQO);
		ChangePasswordResponse changePasswordResponse = new ChangePasswordResponse();
		try{
			if(user!=null){
				HgLogger.getInstance().info("lxs_dev", "【ChangePasswordAction】"+userQO.getId() + "修改密码开始");
				if(user.getAuthInfo().getPassword().endsWith(apiChangePasswordCommand.getOldPassWord())){
					userService.ChangePassword(command,user);
					changePasswordResponse.setResult(ApiResponse.RESULT_CODE_OK);
					changePasswordResponse.setMessage("修改密码成功");
					HgLogger.getInstance().info("lxs_dev", "【ChangePasswordAction】"+userQO.getId() + "修改密码成功");
				}else{
					throw new UserException(UserException.OLD_PASSWORD_WRONG, "原密码不正确");
				}
			}else{
				throw new UserException(UserException.USER_NOT_FOUND, "用户不存在");
			}
		}catch(UserException e){
			HgLogger.getInstance().info("lxs_dev", "【ChangePasswordAction】"+userQO.getId() + "修改密码失败，"+e.getMessage());
			changePasswordResponse.setResult(e.getCode());
			changePasswordResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "【ChangePasswordAction】"+"修改密码结果"+JSON.toJSONString(changePasswordResponse));
		return JSON.toJSONString(changePasswordResponse);
	}
}
