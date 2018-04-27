package zzpl.api.action.user;

import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.response.user.ResetPasswordResponse;
import zzpl.app.service.local.user.UserService;
import zzpl.pojo.command.user.ResetPasswordCommand;

@Component("ResetPasswordAction")
public class ResetPasswordAction implements CommonAction{

	@Autowired
	private UserService userService;
	@Override
	public String execute(ApiRequest apiRequest) {
		ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
		try{
			ResetPasswordCommand resetPasswordCommand = JSON.parseObject(apiRequest.getBody().getPayload(), ResetPasswordCommand.class);
			HgLogger.getInstance().info("cs", "【ResetPasswordAction】"+"resetPasswordCommand:"+JSON.toJSONString(resetPasswordCommand));
			userService.resetPassword(resetPasswordCommand);
			resetPasswordResponse.setResult(ApiResponse.RESULT_CODE_OK);
			resetPasswordResponse.setMessage("找回密码成功");
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【ResetPasswordAction】"+"异常，"+HgLogger.getStackTrace(e));
			resetPasswordResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			resetPasswordResponse.setMessage("找回密码失败");
		}
		HgLogger.getInstance().info("cs", "【ResetPasswordAction】"+"resetPasswordResponse:"+JSON.toJSONString(resetPasswordResponse));
		return JSON.toJSONString(resetPasswordResponse);
	}

}
