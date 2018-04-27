package zzpl.api.controller.api;

import hg.common.util.Md5Util;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.controller.ActionContext;
import zzpl.api.controller.BaseController;
import zzpl.pojo.util.UserSecurity;

import com.alibaba.fastjson.JSON;

@Controller
public class ApiController extends BaseController {

	@Resource
	private ActionContext actionContext;

	@Autowired
	private JedisPool jedisPool;
	
	@Resource
	private SMSUtils smsUtils;

	@ResponseBody
	@RequestMapping(value = "/api")
	public String api(HttpServletRequest request) {
		ApiResponse response = null;
		try {
			String sign = request.getParameter("sign");
			String headmsg = request.getParameter("headmsg");
			HgLogger.getInstance().info("cs", "客户端传入headmsg:" + headmsg);
			String bodymsg = (String) request.getParameter("bodymsg");
			HgLogger.getInstance().info("cs", "客户端传入bodymsg:" + bodymsg);
			System.out.println(JSON.toJSON(request.getParameterMap()));
			String msg = "{\"body\":" + bodymsg + ",\"head\":" + headmsg + "}";
			// 请求信息转ApiRequest类
			ApiRequest apiRequest = JSON.parseObject(msg, ApiRequest.class);
			if (checkSign(apiRequest, headmsg, bodymsg, sign)) {
				String actionName = apiRequest.getHead().getActionName();
				HgLogger.getInstance().info("cs", "密匙验证成功，开始转向对应【"+actionName+"】action");
				CommonAction action = actionContext.get(actionName);
				return action.execute(apiRequest);
			} else {
				response = new ApiResponse();
				response.setMessage("非法请求，请检查你的客户端密钥！");
				response.setResult(ApiResponse.RESULT_CODE_FAIL);
			}
		} catch (Exception e) {
			HgLogger.getInstance().info("cs", "【ApiController】"+"异常，"+HgLogger.getStackTrace(e));
			response.setMessage("请求失败");
			response.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		return JSON.toJSONString(response);
	}

	/**
	 * 检验sign是否正确
	 * 
	 * @param apiRequest
	 * @return
	 */
	private boolean checkSign(ApiRequest apiRequest, String headmsg,
			String bodymsg, String clientSign) {

		// 客户端key
		String clientKey = apiRequest.getHead().getClientKey();
		String secret_key ="";
		if(StringUtils.equals(SysProperties.getInstance().get("H5ClientKey"), apiRequest.getHead().getClientKey())){
			secret_key = SysProperties.getInstance().get("H5SecretKey");
		}else{
			String[] apiNames = SysProperties.getInstance().get("apiNames").split(":");
			boolean flag = true;
			for (int i = 0; i < apiNames.length; i++) {
				if(StringUtils.equals(apiRequest.getHead().getActionName(),apiNames[i])){
					secret_key= SysProperties.getInstance().get(clientKey);
					flag = false;
					break;
				}
			}
			if(flag){
				Jedis jedis = null;
				jedis = jedisPool.getResource();
				UserSecurity userSecurity = JSON.parseObject(jedis.get("ZZPL_"+apiRequest.getHead().getSessionID()),UserSecurity.class);
				jedisPool.returnResource(jedis);
				secret_key=  userSecurity.getSecretKey();
			}
		}
		String sign = Md5Util.MD5(clientKey + secret_key + headmsg + bodymsg);
		return sign.equalsIgnoreCase(clientSign) ? true : false;

	}

}
