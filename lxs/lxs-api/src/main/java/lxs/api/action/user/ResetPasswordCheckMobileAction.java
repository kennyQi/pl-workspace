package lxs.api.action.user;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.user.ResetPasswordCheckMobileCommand;
import lxs.api.v1.response.user.ResetPasswordCheckMobileResponse;
import lxs.app.service.user.RegisterSagaService;
import lxs.domain.model.user.saga.RegisterSaga;
import lxs.pojo.command.user.RegisterCommand;
import lxs.pojo.exception.user.UserException;
import lxs.pojo.qo.user.user.RegisterSagaQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("ResetPasswordCheckMobileAction")
public class ResetPasswordCheckMobileAction implements LxsAction{

	private final static int SMSValidCodeCorrect = 100;// 手机验证成功
	private final static int SMSValidCodePastDue = 101;// 验证码过期
	private final static int SMSValidCodeTooManyTimes = 102;// 验证次数超过最大验证次数
	private final static int SMSValidCodeWrong = 103;// 验证码输入错误
	@Autowired
	private RegisterSagaService registerSagaServive;
	@Autowired
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",  "【ResetPasswordCheckMobileAction】"+"进入action");
		ResetPasswordCheckMobileCommand apiResetPasswordCheckMobileCommand = JSON.parseObject(apiRequest.getBody().getPayload(), ResetPasswordCheckMobileCommand.class);
		RegisterCommand command = new RegisterCommand();
		command.setValidCode(apiResetPasswordCheckMobileCommand.getValidCode());
		RegisterSagaQO registerQO = new RegisterSagaQO();
		registerQO.setSagaid(apiResetPasswordCheckMobileCommand.getSagaId());
		HgLogger.getInstance().info("lxs_dev", "【ResetPasswordCheckMobileAction】"+registerQO.getSagaid() + "重置密码验证手机号查询流程开始");
		RegisterSaga registerSaga = registerSagaServive	.queryBySagaId(registerQO);
		ResetPasswordCheckMobileResponse resetPasswordCheckMobileResponse = new ResetPasswordCheckMobileResponse();
		try{
			if(registerSaga!=null){
				HgLogger.getInstance().info("lxs_dev","【ResetPasswordCheckMobileAction】"+ registerQO.getSagaid()+ "开始验证");
				switch (registerSagaServive.checkVaildCode(command,registerSaga)) {
				case SMSValidCodeCorrect:
					resetPasswordCheckMobileResponse.setSagaId(apiResetPasswordCheckMobileCommand.getSagaId());
					resetPasswordCheckMobileResponse.setResult(ApiResponse.RESULT_CODE_OK);
					resetPasswordCheckMobileResponse.setMessage("验证成功");
					HgLogger.getInstance().info("lxs_dev", "【ResetPasswordCheckMobileAction】"+registerQO.getSagaid()+ "验证成功");
					break;
				case SMSValidCodePastDue:
					throw new UserException(UserException.VALIDCODE_OVERTIME, "验证码已过期");
				case SMSValidCodeTooManyTimes:
					throw new UserException(UserException.VALIDCODE_TOO_MANY_TIMES, "验证码输入次数过多");
				case SMSValidCodeWrong:
					throw new UserException(UserException.VALIDCODE_WRONG, "验证码输入错误");
				}
			}else{
				throw new UserException(UserException.RESULT_LOGINNAME_NOTFOUND, "重置密码流程不存在");
			}
		}catch(UserException e){
			HgLogger.getInstance().info("lxs_dev", "【ResetPasswordCheckMobileAction】"+registerQO.getSagaid() + "验证失败，"+e.getMessage());
			resetPasswordCheckMobileResponse.setResult(e.getCode());
			resetPasswordCheckMobileResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "【ResetPasswordCheckMobileAction】"+"重置密码校验手机号结果"+JSON.toJSONString(resetPasswordCheckMobileResponse));
		return JSON.toJSONString(resetPasswordCheckMobileResponse);
	}

}
