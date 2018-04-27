package lxs.api.action.user;


import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.user.ResetPasswordCommand;
import lxs.api.v1.response.user.ResetPasswordResponse;
import lxs.app.service.user.LxsUserService;
import lxs.app.service.user.RegisterSagaService;
import lxs.domain.model.user.LxsUser;
import lxs.domain.model.user.saga.RegisterSaga;
import lxs.pojo.exception.user.UserException;
import lxs.pojo.qo.user.user.LxsUserQO;
import lxs.pojo.qo.user.user.RegisterSagaQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("ResetPasswordAction")
public class ResetPasswordAction implements LxsAction{

	@Autowired
	private RegisterSagaService registerSagaService; 
	@Autowired
	private LxsUserService userService;
	@Autowired
	private HgLogger hgLogger; 
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",  "【ResetPasswordAction】"+"进入action");
		ResetPasswordCommand apiResetPasswordCheckMobileCommand= JSON.parseObject(apiRequest.getBody().getPayload(), ResetPasswordCommand.class);
		lxs.pojo.command.user.ResetPasswordCommand command = new lxs.pojo.command.user.ResetPasswordCommand();
		command.setNewPassword(apiResetPasswordCheckMobileCommand.getEncryptPassword());
		RegisterSagaQO registerSagaQO = new RegisterSagaQO();
		registerSagaQO.setSagaid(apiResetPasswordCheckMobileCommand.getSagaId());
		RegisterSaga registerSaga = new RegisterSaga();
		HgLogger.getInstance().info("lxs_dev","【ResetPasswordAction】"+ registerSagaQO.getSagaid() + "重置密码查询流程开始");
		registerSaga=registerSagaService.queryUnique(registerSagaQO);
		ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
		try{
			if(registerSaga!=null){
				LxsUserQO userQO = new LxsUserQO();
				userQO.setLoginName(registerSaga.getMobile());
				LxsUser user= new LxsUser();
				HgLogger.getInstance().info("lxs_dev", "【ResetPasswordAction】"+userQO.getLoginName() + "查询用户开始");
				user=userService.queryUnique(userQO);
				if(user!=null){
					command.setUserId(user.getId());
					HgLogger.getInstance().info("lxs_dev", "【ResetPasswordAction】"+userQO.getLoginName() + "修改开始");
					userService.ResetPassword(command);
					resetPasswordResponse.setSagaId(apiResetPasswordCheckMobileCommand.getSagaId());
					resetPasswordResponse.setResult(ApiResponse.RESULT_CODE_OK);
					resetPasswordResponse.setMessage("修改成功");
					HgLogger.getInstance().info("lxs_dev", registerSagaQO.getSagaid()+ "修改成功");
				}else{
					throw new UserException(UserException.RESULT_LOGINNAME_NOTFOUND, "用户不存在");
				}
			}else{
				throw new UserException(UserException.RESULT_LOGINNAME_NOTFOUND, "注册流程不存在");
			}
		}catch(UserException e){
			HgLogger.getInstance().info("lxs_dev", "【ResetPasswordAction】"+registerSagaQO.getSagaid()+ "修改失败，"+e.getMessage());
			resetPasswordResponse.setResult(e.getCode());
			resetPasswordResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "【ResetPasswordAction】"+"重置密码结果"+JSON.toJSONString(resetPasswordResponse));
		return JSON.toJSONString(resetPasswordResponse);
	}


}
