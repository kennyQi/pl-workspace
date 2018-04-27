package hg.dzpw.api.controller;

import hg.dzpw.api.exception.MsgCheckException;
import hg.dzpw.app.component.manager.DealerCacheManager;
import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.common.ApiRequestBody;
import hg.dzpw.dealer.client.common.ApiResponse;
import hg.dzpw.dealer.client.common.api.DealerApiAction;
import hg.dzpw.dealer.client.common.api.DealerApiService;
import hg.dzpw.dealer.client.common.exception.DZPWDealerApiException;
import hg.log.util.HgLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
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
 * @类功能说明：电子票务对经销商接口
 * @类修改者：
 * @修改日期：2014-12-17下午3:15:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-17下午3:15:18
 */
@Controller
public class DZPWDealerApiController2 {

	@Autowired
	private HgLogger hgLogger;

	/**
	 * Api接口(接口名称:接口方法)
	 */
	private Map<String, Method> actionMap = new HashMap<String, Method>();
	/**
	 * 请求体类型(接口名称:请求体类型)
	 */
	private Map<String, Class<?>> bodyClassMap = new HashMap<String, Class<?>>();
	/**
	 * 方法对应的服务(接口名称:对应服务的对象)
	 */
	private Map<String, DealerApiService> methodServiceMap = new HashMap<String, DealerApiService>();

	/**
	 * 实现经销商接口的服务
	 */
	@Autowired
	private Map<String, DealerApiService> serviceMap;
	
	@Autowired
	private DealerCacheManager dealerCacheManager;

	/**
	 * @方法功能说明：初始化
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-17下午4:12:33
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	@PostConstruct
	public void init() {

		for (Entry<String, DealerApiService> entry : serviceMap.entrySet()) {

			DealerApiService service = entry.getValue();
			Class<?> clazz = service.getClass();
			
			while (clazz != Object.class) {
				
				// 获取当前类所有声明的方法
				Method[] methods = clazz.getDeclaredMethods();
				
				for (Method method : methods) {
					
					Class<?>[] paramType = method.getParameterTypes();
					Class<?> returnType = method.getReturnType();
					
					if (returnType == null
							|| paramType.length != 1
							|| !hg.dzpw.dealer.client.common.ApiRequest.class.isAssignableFrom(paramType[0])
							|| !hg.dzpw.dealer.client.common.ApiResponse.class.isAssignableFrom(returnType))
						continue;

					DealerApiAction action = method.getAnnotation(DealerApiAction.class);
					if (action != null) {
						actionMap.put(action.value(), method);
						Type[] types = method.getGenericParameterTypes();
						ParameterizedType pType = (ParameterizedType) types[0];
						bodyClassMap.put(action.value(), (Class<?>) pType.getActualTypeArguments()[0]);
						methodServiceMap.put(action.value(), service);
					}
				}

				clazz = clazz.getSuperclass();
			}
		}
	}

	/**
	 * @方法功能说明：消息校验
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-17下午3:51:34
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param msg
	 * @参数：@param sign
	 * @return:void
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
		
		String dealerKey = (String) header.get("dealerKey");
		String actionName = (String) header.get("actionName");
		
		if (dealerKey == null || dealerKey.trim().length() == 0)
			throw new MsgCheckException("经销商Key不能为空");
		
		if (actionName == null || actionName.trim().length() == 0)
			throw new MsgCheckException("未指定接口方法名称");

		String secretKey = dealerCacheManager.getSecretKey(dealerKey);
		
		if (secretKey == null)
//			throw new MsgCheckException("未授权的经销商接口请求");
			throw new MsgCheckException("无效经销商代码");

		System.out.println("---------Md5加密："+DigestUtils.md5Hex(secretKey + msg));
		if (!StringUtils.equalsIgnoreCase(sign, DigestUtils.md5Hex(secretKey + msg)))
			throw new MsgCheckException("签名认证未通过");
		
		request.setAttribute("actionName", actionName);
	}
	
	/**
	 * @方法功能说明：执行接口方法
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-17下午4:00:51
	 * @修改内容：
	 * @参数：@param actionName
	 * @参数：@param requestJson
	 * @参数：@return
	 * @参数：@throws InvocationTargetException
	 * @return:ApiResponse
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private ApiResponse executeAction(String actionName, String requestJson) throws InvocationTargetException {

		Method method = actionMap.get(actionName);
		Class<ApiRequestBody> bodyClass = (Class<ApiRequestBody>) bodyClassMap.get(actionName);

		if (method == null)
			return ApiResponse.error("接口方法不存在");

		DealerApiService service = methodServiceMap.get(actionName);
		
		if (service == null)
			return ApiResponse.error("接口不存在");
		
		try {
			return (ApiResponse) method.invoke(service, ApiRequest.parseRequest(requestJson, bodyClass));
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
	@RequestMapping(value = "/api")
	@ResponseBody
	public String api(
			HttpServletRequest request,
			@RequestParam("msg") String msg, 
			@RequestParam("sign") String sign) {

		System.out.println("msg>>>>>>>>" + msg);
		System.out.println("sign>>>>>>>" + sign);

		hgLogger.debug("dealer-api", String.format(
				"接收参数 sessionId(%s) msg(%s) sign(%s)", request.getSession().getId(), msg, sign));

		ApiResponse apiResponse = null;
		try {
			
			checkMsg(request, msg, sign);
			try {
				apiResponse = executeAction((String) request.getAttribute("actionName"), msg);
			} catch (InvocationTargetException e) {
				throw (Exception) e.getTargetException();
			}
		} catch (DZPWDealerApiException apiException) {
			apiResponse = ApiResponse.fail(apiException.getCode(), apiException.getMessage());
		} catch (MsgCheckException e) {
			apiResponse = ApiResponse.checkFail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = ApiResponse.error("系统异常");
		}

		String resultJson = JSON.toJSONString(apiResponse);
		
		hgLogger.debug("dealer-api", String.format(
				"返回结果 sessionId(%s) result(%s)", request.getSession().getId(), resultJson));
		
		return resultJson;
	}

}