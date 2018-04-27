package lxs.api.action.user;

import hg.common.util.JsonUtil;
import hg.common.util.Md5Util;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.user.GetSMSValidCodeCommand;
import lxs.api.v1.response.user.GetSMSValidCodeResponse;
import lxs.app.service.user.LxsUserService;
import lxs.app.service.user.RegisterSagaService;
import lxs.domain.model.user.saga.RegisterSaga;
import lxs.pojo.exception.user.UserException;
import lxs.pojo.qo.user.user.LxsUserQO;
import lxs.pojo.qo.user.user.RegisterSagaQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("GetSMSValidCodeAction")
public class GetSMSValidCodeAction implements LxsAction {
	private final static Integer SCENE_TYPE_FIND_PASSWORD = 1; // 找回密码
	private final static Integer SCENE_TYPE_REGISTER = 2; // 注册
	private final static int STATUS_ACTIVED = 2; // 已填写密码激活
	
	@Autowired
	private RegisterSagaService registerSagaService;
	@Autowired
	private SMSUtils smsUtil;
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private LxsUserService lxsUserService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",  "【GetSMSValidCodeAction】"+"进入action");
		final GetSMSValidCodeCommand apiGetSMSValidCodeCommand = JSON.parseObject(apiRequest.getBody().getPayload(),GetSMSValidCodeCommand.class);
		GetSMSValidCodeResponse getSMSValidCodeResponse = new GetSMSValidCodeResponse();
		try{
			// 验证电话号码是否合法
			// 电话号码合法
			if (isPhone(apiGetSMSValidCodeCommand.getMobile())) {
				HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+apiGetSMSValidCodeCommand.getMobile() + "验证手机号码格式成功");
				//判断业务场景
				if (apiGetSMSValidCodeCommand.getSceneType() == SCENE_TYPE_REGISTER) {
					HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+apiGetSMSValidCodeCommand.getMobile() + "开始注册流程");
					final RegisterSagaQO registerSagaQO = new RegisterSagaQO();
					registerSagaQO.setMobile(apiGetSMSValidCodeCommand.getMobile());
					LxsUserQO lxsUserQO = new LxsUserQO();
					lxsUserQO.setLoginName(apiGetSMSValidCodeCommand.getMobile());
					//电话号码未激活
					if(lxsUserService.queryCount(lxsUserQO)==0){
						//注册流程获取验证码，插入流程信息				
						//将Request 的command转化为lxs.user.pojo.command.GetSMSValidCodeCommand
						HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+apiGetSMSValidCodeCommand.getMobile() + "创建注册流程");
						lxs.pojo.command.user.GetSMSValidCodeCommand getSMSValidCodeCommand = new lxs.pojo.command.user.GetSMSValidCodeCommand();
						getSMSValidCodeCommand.setMobile(apiGetSMSValidCodeCommand.getMobile());
						getSMSValidCodeCommand.setSceneType(apiGetSMSValidCodeCommand.getSceneType());
						getSMSValidCodeCommand.setToken(getToken(apiRequest));
						//流程信息插入数据库
						registerSagaService.createRegisterSaga(getSMSValidCodeCommand);
						//发短信
						HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+apiGetSMSValidCodeCommand.getMobile() + "开始发送短信");
						try {
							//发送成功
							Thread t=new Thread(){
							    public void run(){
							    	try {
										smsUtil.sendSms(apiGetSMSValidCodeCommand.getMobile(),"【"+SysProperties.getInstance().get("smsSign")+"】您的验证码为:"+registerSagaService.queryByMoble(registerSagaQO).getValidCode()+",三分钟内有效.如非您本人操作,可忽略本消息.");
									} catch (Exception e) {
										HgLogger.getInstance().info("lxs_dev", "发短信"+"异常，"+HgLogger.getStackTrace(e));
									}
							   }
							};
							t.start();
							getSMSValidCodeResponse.setSagaId(registerSagaService.queryByMoble(registerSagaQO).getSagaid());
							getSMSValidCodeResponse.setResult(ApiResponse.RESULT_CODE_OK);
							getSMSValidCodeResponse.setMessage("短信发送成功");
							HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+apiGetSMSValidCodeCommand.getMobile() + "发送短信成功");
						} catch (Exception e) {
							//发送失败
							throw new UserException(UserException.RESULT_SEND_SMS_FAIL, "短信发送失败");
						}
					}else{
						//该号码已注册过
						throw new UserException(UserException.RESULT_MOBILE_BIND, "该号码已注册");
					}
				}
				if (apiGetSMSValidCodeCommand.getSceneType() == SCENE_TYPE_FIND_PASSWORD) {
					HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+apiGetSMSValidCodeCommand.getMobile() + "开始找回密码流程");
					// 找回密码
					final RegisterSagaQO registerSagaQO = new RegisterSagaQO();
					registerSagaQO.setMobile(apiGetSMSValidCodeCommand.getMobile());
					//电话号码已激活
					if(!notActived(registerSagaService.queryList(registerSagaQO))){
						//注册流程获取验证码，插入流程信息				
						//将Request 的command转化为lxs.user.pojo.command.GetSMSValidCodeCommand
						HgLogger.getInstance().info("lxs_dev","【GetSMSValidCodeAction】"+ apiGetSMSValidCodeCommand.getMobile() + "创建找回密码流程");
						lxs.pojo.command.user.GetSMSValidCodeCommand getSMSValidCodeCommand = new lxs.pojo.command.user.GetSMSValidCodeCommand();
						getSMSValidCodeCommand.setMobile(apiGetSMSValidCodeCommand.getMobile());
						getSMSValidCodeCommand.setSceneType(apiGetSMSValidCodeCommand.getSceneType());
						getSMSValidCodeCommand.setToken(getToken(apiRequest));
						//流程信息插入数据库
						registerSagaService.createRegisterSaga(getSMSValidCodeCommand);
						//发短信
						HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+ apiGetSMSValidCodeCommand.getMobile() + "开始发送短信");
						try {
							//发送成功
							//发送成功
							Thread t=new Thread(){
							    public void run(){
							    	try {
										smsUtil.sendSms(apiGetSMSValidCodeCommand.getMobile(),"【"+SysProperties.getInstance().get("smsSign")+"】您的验证码为:"+registerSagaService.queryByMoble(registerSagaQO).getValidCode()+",三分钟内有效.如非您本人操作,可忽略本消息.");
									} catch (Exception e) {
										HgLogger.getInstance().info("lxs_dev", "发短信"+"异常，"+HgLogger.getStackTrace(e));
									}
							   }
							};
							t.start();
							getSMSValidCodeResponse.setSagaId(registerSagaService.queryByMoble(registerSagaQO).getSagaid());
							getSMSValidCodeResponse.setResult(ApiResponse.RESULT_CODE_OK);
							getSMSValidCodeResponse.setMessage("短信发送成功");
							HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+apiGetSMSValidCodeCommand.getMobile() + "发送短信成功");
						} catch (Exception e) {
							//发送失败
							throw new UserException(UserException.RESULT_SEND_SMS_FAIL, "短信发送失败");
						}
					}else{
						//该号码已注册过
						throw new UserException(UserException.RESULT_MOBILE_UNBIND, "该号码未注册");
					}
				}
			} else {
				// 电话号码不合法
				throw new UserException(UserException.RESULT_MOBILE_WRONG, "手机号码格式错误");
			}
		}catch(UserException e){
			HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+apiGetSMSValidCodeCommand.getMobile() + "发送短信失败，"+e.getMessage());
			getSMSValidCodeResponse.setResult(e.getCode());
			getSMSValidCodeResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "【GetSMSValidCodeAction】"+apiGetSMSValidCodeCommand.getMobile() +"发送短信结果"+JSON.toJSONString(getSMSValidCodeResponse));
		return JSON.toJSONString(getSMSValidCodeResponse);
	}

	/**
	 * 检验电话号码是否正确
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	/**
	 * 获取token
	 * @param apiRequest
	 * @return token
	 */
	public static String getToken(ApiRequest apiRequest) {
		// 客户端key
		String clientKey = apiRequest.getHead().getClientKey();
		String secret_key = SysProperties.getInstance().get(clientKey);
		
		String msg = JsonUtil.parseObject(apiRequest, false);
		
		String sign = Md5Util.MD5(clientKey + secret_key + msg);
		return sign;
	}
	
	/**
	 * 验证号码是否激活
	 * @param registerSagaList
	 * @return 
	 */
	public static boolean notActived(List<RegisterSaga> registerSagaList) {
		boolean flag = true;
		for(int i=0;i<registerSagaList.size();i++){
			if(registerSagaList.get(i).getCurrentStatus()==STATUS_ACTIVED){
				flag=false;break;
			}
		}
		return flag;
	}
	
}
