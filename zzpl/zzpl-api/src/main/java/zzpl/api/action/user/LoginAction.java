package zzpl.api.action.user;


import hg.common.util.Md5Util;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.request.user.LoginCommand;
import zzpl.api.client.response.user.LoginResponse;
import zzpl.app.service.local.push.PushService;
import zzpl.app.service.local.user.CompanyService;
import zzpl.app.service.local.user.UserService;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.User;
import zzpl.pojo.qo.user.UserQO;
import zzpl.pojo.util.UserSecurity;

import com.alibaba.fastjson.JSON;

@Component("LoginAction")
public class LoginAction implements CommonAction{
	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PushService pushService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		LoginResponse loginResponse = new LoginResponse();
		try{
			LoginCommand loginCommand = JSON.parseObject(apiRequest.getBody().getPayload(), LoginCommand.class);
			HgLogger.getInstance().info("cs", "【LoginAction】"+"loginCommand:"+JSON.toJSONString(loginCommand));
			UserQO userQO = new UserQO();
			if(loginCommand==null||loginCommand.getUserDTO()==null||loginCommand.getUserDTO().getUserNO()==null||loginCommand.getUserDTO().getLoginName()==null||loginCommand.getUserDTO().getPassword()==null||StringUtils.isBlank(loginCommand.getUserDTO().getUserNO())||StringUtils.isBlank(loginCommand.getUserDTO().getLoginName())||StringUtils.isBlank(loginCommand.getUserDTO().getPassword()))
				throw new Exception();
			userQO.setLoginName(Md5Util.MD5(loginCommand.getUserDTO().getLoginName()));
			userQO.setPassword(Md5Util.MD5(loginCommand.getUserDTO().getPassword()));
			List<User> users = userService.queryList(userQO);
			boolean flag = false;
			if(users==null){
				loginResponse.setMessage("账号信息或密码不正确");
				loginResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			}else{
				User user = new User();
				for (User user1 : users) {
					if(user1.getCompanyID()==null){
						loginResponse.setMessage("账号信息或密码不正确");
						loginResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
					}else{
						Company company = companyService.get(user1.getCompanyID());
						if(company!=null&&StringUtils.equals(company.getCompanyID(), loginCommand.getUserDTO().getUserNO())){
							user=user1;
							flag=true;
							break;
						}
					}
				}
				if(flag){
					String sessionID = UUIDGenerator.getUUID();
					String secretKey = UUIDGenerator.getUUID();
					UserSecurity userSecurity = new UserSecurity();
					userSecurity.setSecretKey(secretKey);
					userSecurity.setUserID(user.getId());
					Jedis jedis = null;
					jedis = jedisPool.getResource();
					String userStatus = jedis.get("ZZPL_"+user.getId());
					if(StringUtils.equals(userStatus,"online")){
						Map<String, String> map = new HashMap<String, String>();
						map.put("sessionID", sessionID);
						pushService.sendMessageAndroidByDeviceID(user.getId(), "ReLogin",map);
					}
					jedis.setex("ZZPL_"+sessionID,2592000,JSON.toJSONString(userSecurity));
					jedis.setex("ZZPL_"+user.getId(),2592000,"online");
					jedisPool.returnResource(jedis);
					loginResponse.setSessionID(sessionID);
					loginResponse.setSecretKey(secretKey);
					loginResponse.setUserID(user.getId());
					loginResponse.setMessage("登录成功");
					loginResponse.setResult(ApiResponse.RESULT_CODE_OK);
				}else{
					loginResponse.setMessage("账号信息或密码不正确");
					loginResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
				}
			}
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【LoginAction】"+"异常，"+HgLogger.getStackTrace(e));
			loginResponse.setMessage("登录失败");
			loginResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【LoginAction】"+"loginResponse:"+JSON.toJSONString(loginResponse));
		return JSON.toJSONString(loginResponse);
	}

}
