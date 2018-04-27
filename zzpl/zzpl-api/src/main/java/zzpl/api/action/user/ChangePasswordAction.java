package zzpl.api.action.user;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hg.system.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.request.user.ChangePasswordCommand;
import zzpl.api.client.response.user.ChangePasswordResponse;
import zzpl.app.service.local.user.UserService;

import com.alibaba.fastjson.JSON;

@Component("ChangePasswordAction")
public class ChangePasswordAction implements CommonAction{

	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserService userService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		ChangePasswordResponse changePasswordResponse = new ChangePasswordResponse();
		try{
			ChangePasswordCommand changePasswordCommand = JSON.parseObject(apiRequest.getBody().getPayload(),ChangePasswordCommand.class);
			HgLogger.getInstance().info("cs", "【ChangePasswordAction】"+"changePasswordCommand:"+JSON.toJSONString(changePasswordCommand));
			if(userService.changePassword(BeanMapperUtils.getMapper().map(changePasswordCommand, zzpl.pojo.command.user.ChangePasswordCommand.class))){
				securityService.updateUserPassword(changePasswordCommand.getUserID(), changePasswordCommand.getPassword(), changePasswordCommand.getNewPassword());
				changePasswordResponse.setMessage("修改密码成功");
				changePasswordResponse.setResult(ApiResponse.RESULT_CODE_OK);
			}else{
				changePasswordResponse.setMessage("原密码不正确");
				changePasswordResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			}
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【ChangePasswordAction】"+"异常，"+HgLogger.getStackTrace(e));
			changePasswordResponse.setMessage("修改密码失败");
			changePasswordResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【ChangePasswordAction】"+"changePasswordResponse:"+JSON.toJSONString(changePasswordResponse));
		return JSON.toJSONString(changePasswordResponse);
	}

}
