package hg.dzpw.api.controller;

import hg.common.util.MD5HashUtil;
import hg.dzpw.api.exception.MsgCheckException;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.component.manager.ScenicSpotCacheManager;
import hg.dzpw.app.service.api.app.AppApiService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.pojo.annotation.AppApiAction;
import hg.dzpw.pojo.api.appv1.base.ApiRequest;
import hg.dzpw.pojo.api.appv1.base.ApiRequestBody;
import hg.dzpw.pojo.api.appv1.base.ApiResponse;
import hg.dzpw.pojo.api.appv1.exception.DZPWAppApiException;
import hg.dzpw.pojo.api.appv1.request.LoginRequestBody;
import hg.dzpw.pojo.api.appv1.request.ValiTicketByTicketNoRequestBody;
import hg.log.util.HgLogger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：电子票务APP接口
 * @类修改者：
 * @修改日期：2014-11-27下午2:48:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-27下午2:48:37
 */
@Controller
public class DZPWAppApiController {
	
	@Autowired
	private HgLogger hgLogger;
	
	@Autowired
	private ScenicSpotCacheManager cacheManager;
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	
	/**
	 * Api接口
	 */
	private Map<String, Method> actionMap = new HashMap<String, Method>();
	private Map<String, Class<?>> bodyClassMap = new HashMap<String, Class<?>>();
	
	@Autowired
	private AppApiService appApiService;
	

	public DZPWAppApiController() {
		// 获取当前类所有声明的方法
		Method[] methods = AppApiService.class.getDeclaredMethods();
		for (Method method : methods) {
			Class<?>[] paramType = method.getParameterTypes();
			Class<?> returnType = method.getReturnType();
			if (returnType == null || paramType.length != 1
					|| !ApiRequest.class.isAssignableFrom(paramType[0])
					|| !ApiResponse.class.isAssignableFrom(returnType))
				continue;

			AppApiAction action = method.getAnnotation(AppApiAction.class);
			if (action != null) {
				actionMap.put(action.value(), method);
				Type[] types = method.getGenericParameterTypes();
				ParameterizedType pType = (ParameterizedType) types[0];
				bodyClassMap.put(action.value(), (Class<?>) pType.getActualTypeArguments()[0]);
			}
		}
	}
	
	/**
	 * @方法功能说明：消息校验
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-27下午6:20:10
	 * @修改内容：
	 * @修改者名字：chenys
	 * @修改时间：2014-11-27下午8:20:10
	 * @修改内容：
	 * @参数：@param msg
	 * @参数：@param sign
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	private void checkMsg(HttpServletRequest request, String msg, String sign) {
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.parseObject(msg);
		} catch (Exception e) {
			throw new MsgCheckException("非法的请求体");
		}
		if (jsonObject == null)
			throw new MsgCheckException("非法的请求体");
		if (sign == null)
			throw new MsgCheckException("非法的请求体");
		
		JSONObject header = (JSONObject) jsonObject.get("header");
		JSONObject body = (JSONObject) jsonObject.get("body");
		if (header == null)
			throw new MsgCheckException("请求体头部不能为空");
		if (body == null)
			throw new MsgCheckException("请求体不能为空");
//		String clientId = (String) header.get("deviceId");
		
		String actionName = (String) header.get("actionName");
//		if (clientId == null || clientId.trim().length() == 0)
//			throw new MsgCheckException("设备ID不能为空");
		if (actionName == null || actionName.trim().length() == 0)
			throw new MsgCheckException("未指定接口方法名称");
		
		if(actionMap.get(actionName)!=null && actionName.equals("Login")){
			//登录接口使用公共密钥验签
			System.out.println("登录接口--本地sign>>>"+MD5HashUtil.toMD5(SystemConfig.scenicSpotPublicKey + msg));
			
			if(!sign.equals(MD5HashUtil.toMD5(SystemConfig.scenicSpotPublicKey + msg)))
				throw new MsgCheckException("签名错误");
		}else{
			
			String scenicSpotId = (String) body.get("scenicSpotId");
			if (scenicSpotId == null || scenicSpotId.trim().length() == 0)
			throw new MsgCheckException("景区ID不能为空");
			
			
			String secretKey = cacheManager.getSecretKeyByScenicSpotId(scenicSpotId);
			//从缓存获取不到从数据库中查询
			if (secretKey == null || secretKey.trim().length() == 0){
				ScenicSpot scenicSpot=scenicSpotLocalService.get(scenicSpotId);
				if (scenicSpot!=null && StringUtils.isNotBlank(scenicSpot.getSuperAdmin().getSecretKey())) {
					secretKey=scenicSpot.getSuperAdmin().getSecretKey();
				}
			}
			if (secretKey == null || secretKey.trim().length() == 0)
				throw new MsgCheckException("secretKey不能为空");
			System.out.println(actionName+"接口--本地sign>>>"+MD5HashUtil.toMD5(secretKey + msg));
			
				if(!sign.equals(MD5HashUtil.toMD5(secretKey + msg)))
					throw new MsgCheckException("签名错误");
		}
		request.setAttribute("actionName", actionName);
	}
	
	/**
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @方法功能说明：执行接口方法
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-27下午3:13:27
	 * @修改内容：
	 * @参数：@param actionName
	 * @参数：@param requestJson
	 * @参数：@return
	 * @return:ApiResponse
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private ApiResponse executeAction(String actionName, String requestJson) throws InvocationTargetException {
		
		Method method = actionMap.get(actionName);
		Class<ApiRequestBody> bodyClass = (Class<ApiRequestBody>) bodyClassMap.get(actionName);

		if (method == null)
			return ApiResponse.error("接口方法不存在");

		try {
			return (ApiResponse) method.invoke(appApiService, ApiRequest.parseRequest(requestJson, bodyClass));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return ApiResponse.error("未知异常");
	}
	
	/**
	 * @方法功能说明: api 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/api")
	public String api(
			HttpServletRequest request, 
			@RequestParam("msg") String msg, 
			@RequestParam("sign") String sign ) {
		System.out.println("msg>>>>>>>>"+msg);
		System.out.println("sign>>>>"+sign);
		hgLogger.debug("app-api", String.format(
				"接收参数 sessionId(%s) msg(%s) sign(%s)", request.getSession().getId(), msg, sign));
		
		ApiResponse apiResponse = null;
		try {
			checkMsg(request, msg, sign);
			try {
				apiResponse = executeAction((String) request.getAttribute("actionName"), msg);
			} catch (InvocationTargetException e) {
				throw (Exception) e.getTargetException();
			}
		} catch (DZPWAppApiException apiException) {
			apiResponse = ApiResponse.fail(apiException.getCode(), apiException.getMessage());
		} catch (MsgCheckException e) {
			apiResponse = ApiResponse.checkFail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = ApiResponse.error("未知异常");
		}
		
		String resultJson = JSON.toJSONString(apiResponse);
		hgLogger.debug("app-api", String.format(
				"返回结果 sessionId(%s) result(%s)", request.getSession().getId(), resultJson));
		return resultJson;
	}
	
	
	public static void main(String[] args) {
		ApiRequest<ValiTicketByTicketNoRequestBody> request = new ApiRequest<ValiTicketByTicketNoRequestBody>();
		request.getHeader().setActionName("ValiTicketByTicketNo");
		request.getHeader().setTimestamp(System.currentTimeMillis());
		request.getHeader().setDeviceId("1");
		ValiTicketByTicketNoRequestBody body = new ValiTicketByTicketNoRequestBody();
		request.setBody(body);
		body.setTicketNo("122");

		ApiRequest<LoginRequestBody> request2 = new ApiRequest<LoginRequestBody>();
		request2.getHeader().setDeviceId("ddd");
		request2.getHeader().setActionName("Login");
		request2.getHeader().setTimestamp(System.currentTimeMillis());
		LoginRequestBody body2 = new LoginRequestBody();
		request2.setBody(body2);
		body2.setLoginName("admin");
		body2.setPassword("123456");
		
		System.out.println(JSON.toJSONString(request2, true));
		
	}
}