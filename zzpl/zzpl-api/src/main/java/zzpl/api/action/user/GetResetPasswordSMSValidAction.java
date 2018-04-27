package zzpl.api.action.user;

import hg.common.util.Md5Util;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.request.user.GetResetPasswordSMSValidCommand;
import zzpl.api.client.response.user.GetResetPasswordSMSValidResponse;
import zzpl.app.service.local.saga.ResetPasswordSagaService;
import zzpl.app.service.local.user.CompanyService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.saga.ResetPasswordCommand;
import zzpl.pojo.qo.user.CompanyQO;
import zzpl.pojo.qo.user.UserQO;

import com.alibaba.fastjson.JSON;

@Component("GetResetPasswordSMSValidAction")
public class GetResetPasswordSMSValidAction implements CommonAction{

	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ResetPasswordSagaService resetPasswordSagaService;
	@Autowired
	private SMSUtils smsUtils;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		GetResetPasswordSMSValidResponse getResetPasswordSMSValidResponse = new GetResetPasswordSMSValidResponse();
		try {
			GetResetPasswordSMSValidCommand getResetPasswordSMSValidCommand = JSON.parseObject(apiRequest.getBody().getPayload(), GetResetPasswordSMSValidCommand.class);
			HgLogger.getInstance().info("cs", "【GetResetPasswordSMSValidAction】"+"getResetPasswordSMSValidCommand:"+JSON.toJSONString(getResetPasswordSMSValidCommand));
			CompanyQO companyQO = new CompanyQO();
			companyQO.setCompanyID(getResetPasswordSMSValidCommand.getCompanyID());
			List<Company> companies = companyService.queryList(companyQO);
			User user = new User();
			boolean flag = false;
			if(companies==null){
				throw new Exception("公司不存在");
			}else{
				for (Company company : companies) {
					UserQO userQO = new UserQO();
					userQO.setLoginName(Md5Util.MD5(getResetPasswordSMSValidCommand.getLoginName()));
					userQO.setCompanyID(company.getId());
					User user1 = userService.queryUnique(userQO);
					if(user1==null){
						throw new Exception("用户不存在");		
					}else{
						user=user1;
						flag=true;
						break;
					}
				}
			}
			if(flag&&user!=null&&user.getLinkMobile()!=null){
				ResetPasswordCommand resetPasswordCommand = new ResetPasswordCommand();
				resetPasswordCommand.setMobile(user.getLinkMobile());
				resetPasswordCommand.setUserID(user.getId());
				String sagaID_smsValid = resetPasswordSagaService.getResetPasswordSMSValid(resetPasswordCommand);
				final String text = "【"+SysProperties.getInstance().get("smsSign")+"】"+"您的验证码为："+sagaID_smsValid.split(":")[1]+"，三十分钟内有效。如非您本人操作，可忽略本消息";
				final String linkinMobile = user.getLinkMobile();
				HgLogger.getInstance().info("cs", "【GetResetPasswordSMSValidAction】"+"发送短信内容，电话"+user.getLinkMobile());
				HgLogger.getInstance().info("cs", "【GetResetPasswordSMSValidAction】"+"发送短信内容，内容"+text);
				new Thread(){
					public void run(){
						try {
							smsUtils.sendSms(linkinMobile, text);
						} catch (Exception e) {
							HgLogger.getInstance().info("cs", "【GetResetPasswordSMSValidAction】异常，"+HgLogger.getStackTrace(e));
						}
					}
				}.start();
				getResetPasswordSMSValidResponse.setLinkMobile(user.getLinkMobile());
				getResetPasswordSMSValidResponse.setSagaID(sagaID_smsValid.split(":")[0]);
			}else{
				throw new Exception("用户不存在");
			}
		} catch (Exception e) {
			HgLogger.getInstance().info("cs", "【GetResetPasswordSMSValidAction】"+"异常，"+HgLogger.getStackTrace(e));
			getResetPasswordSMSValidResponse.setMessage("获取验证码失败");
			getResetPasswordSMSValidResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【GetResetPasswordSMSValidAction】"+"getResetPasswordSMSValidResponse:"+JSON.toJSONString(getResetPasswordSMSValidResponse));
		return JSON.toJSONString(getResetPasswordSMSValidResponse);
	}
}
