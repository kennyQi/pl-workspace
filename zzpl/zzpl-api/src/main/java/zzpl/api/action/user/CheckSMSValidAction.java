package zzpl.api.action.user;

import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.response.user.CheckSMSValidResponse;
import zzpl.app.service.local.saga.ResetPasswordSagaService;
import zzpl.pojo.command.saga.CheckSMSValidCommand;
import zzpl.pojo.dto.user.status.ResetPasswordSagaStatus;

@Component("CheckSMSValidAction")
public class CheckSMSValidAction implements CommonAction {

	@Autowired
	private ResetPasswordSagaService resetPasswordSagaService;

	@Override
	public String execute(ApiRequest apiRequest) {
		CheckSMSValidResponse checkSMSValidResponse = new CheckSMSValidResponse();
		try{
			CheckSMSValidCommand checkSMSValidCommand = JSON.parseObject(apiRequest.getBody().getPayload(), CheckSMSValidCommand.class);
			HgLogger.getInstance().info("cs", "【CheckSMSValidAction】"+"checkSMSValidCommand:"+JSON.toJSONString(checkSMSValidCommand));
			switch (resetPasswordSagaService.checkSMSValid(checkSMSValidCommand)) {
			case ResetPasswordSagaStatus.CODE_TOO_MANY_TIMES:
				checkSMSValidResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
				checkSMSValidResponse.setMessage(ResetPasswordSagaStatus.CODE_TOO_MANY_TIMES_MESSAGE);
				break;
			case ResetPasswordSagaStatus.CODE_PAST_DUE:
				checkSMSValidResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
				checkSMSValidResponse.setMessage(ResetPasswordSagaStatus.CODE_PAST_DUE_MESSAGE);
				break;
			case ResetPasswordSagaStatus.CODE_CORRECT:
				checkSMSValidResponse.setResult(ApiResponse.RESULT_CODE_OK);
				checkSMSValidResponse.setMessage(ResetPasswordSagaStatus.CODE_CORRECT_MESSAGE);
				break;
			case ResetPasswordSagaStatus.CODE_WRONG:
				checkSMSValidResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
				checkSMSValidResponse.setMessage(ResetPasswordSagaStatus.CODE_WRONG_MESSAGE);
				break;
			}
		}catch (Exception e){
			HgLogger.getInstance().info("cs", "【CheckSMSValidAction】"+"异常，"+HgLogger.getStackTrace(e));
			checkSMSValidResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			checkSMSValidResponse.setMessage("验证失败");
		}
		HgLogger.getInstance().info("cs", "【CheckSMSValidAction】"+"checkSMSValidResponse:"+JSON.toJSONString(checkSMSValidResponse));
		return JSON.toJSONString(checkSMSValidResponse);
	}

}
