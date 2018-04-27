package lxs.api.action.user;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.user.RegisterCommand;
import lxs.api.v1.response.user.RegisterResponse;
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

@Component("RegisterAction")
public class RegisterAction implements LxsAction {

	@Autowired
	private RegisterSagaService registerSagaServive;
	@Autowired
	private LxsUserService userService;
	@Autowired
	private HgLogger hgLogger;

	private final static int SMSValidCodeCorrect = 100;// 手机验证成功
	private final static int SMSValidCodePastDue = 101;// 验证码过期
	private final static int SMSValidCodeTooManyTimes = 102;// 验证次数超过最大验证次数
	private final static int SMSValidCodeWrong = 103;// 验证码输入错误
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",  "【RegisterAction】"+"进入action");
		RegisterCommand apiRegistercommand = JSON.parseObject(apiRequest
				.getBody().getPayload(), RegisterCommand.class);
		RegisterResponse registerResponse = new RegisterResponse();
		RegisterSagaQO registerQO = new RegisterSagaQO();
		registerQO.setSagaid(apiRegistercommand.getSagaId());
		HgLogger.getInstance().info("lxs_dev", "【RegisterAction】"+registerQO.getSagaid() + "注册查询流程开始");
		RegisterSaga registerSaga = registerSagaServive
				.queryBySagaId(registerQO);
		lxs.pojo.command.user.RegisterCommand registerCommand = new lxs.pojo.command.user.RegisterCommand();
		registerCommand.setValidCode(apiRegistercommand.getValidCode());
		registerCommand.setEncryptPassword(apiRegistercommand
				.getEncryptPassword());
		registerCommand.setSource(apiRequest.getHead().getClientKey());
		registerCommand.setNickName(apiRegistercommand.getNickName());
		try{
			if(registerSaga!=null){
				HgLogger.getInstance().info("lxs_dev", "【RegisterAction】"+registerQO.getSagaid() + "开始注册");
				switch (registerSagaServive.checkVaildCode(registerCommand,
						registerSaga)) {
						case SMSValidCodeCorrect:
							userService.register(registerCommand, registerSaga);
							if(apiRegistercommand.isQueryUserInfo()){
								//创建一个UserQO对象
								LxsUserQO userQO = new LxsUserQO();
								//设置查询条件
								userQO.setLoginName(registerSaga.getMobile());
								//查询
								LxsUser user = new LxsUser();
								user=userService.queryUnique(userQO);
								registerResponse.setUser(QueryUserAction.userToUserDTO(user));
							}
							registerResponse.setResult(ApiResponse.RESULT_CODE_OK);
							registerResponse.setMessage("注册成功");
							HgLogger.getInstance().info("lxs_dev", "【RegisterAction】"+registerQO.getSagaid() + "注册成功");
							break;
						case SMSValidCodePastDue:
							throw new UserException(UserException.VALIDCODE_OVERTIME, "验证码已过期");
						case SMSValidCodeTooManyTimes:
							throw new UserException(UserException.VALIDCODE_TOO_MANY_TIMES, "验证码输入次数过多");
						case SMSValidCodeWrong:
							throw new UserException(UserException.VALIDCODE_WRONG, "验证码输入错误");
				}
			}else{
				throw new UserException(UserException.RESULT_LOGINNAME_NOTFOUND, "注册流程不存在");
			}
		}catch(UserException e){
			HgLogger.getInstance().info("lxs_dev", "【RegisterAction】"+registerQO.getSagaid() + "注册失败，"+e.getMessage());
			registerResponse.setResult(e.getCode());
			registerResponse.setMessage(e.getMessage());
		}
			HgLogger.getInstance().info("lxs_dev", "【RegisterAction】"+registerQO.getSagaid() +"注册结果"+JSON.toJSONString(registerResponse));
			return JSON.toJSONString(registerResponse);
	}
}
