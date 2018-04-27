package plfx.api.controller;

import hg.log.util.HgLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

import plfx.api.client.common.ApiRequest;
import plfx.api.client.common.ApiRequestBody;
import plfx.api.client.common.ApiRequestHead;
import plfx.api.client.common.ApiResponse;
import plfx.api.client.common.api.PlfxApiAction;
import plfx.api.client.common.api.PlfxApiService;
import plfx.api.client.common.exception.PlfxApiException;
import plfx.api.exception.MsgCheckException;
import plfx.jp.app.component.cache.DealerCacheManager;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.util.NetworkUtil;

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
public class PlfxApiController {

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
	private Map<String, PlfxApiService> methodServiceMap = new HashMap<String, PlfxApiService>();

	/**
	 * 实现经销商接口的服务
	 */
	@Autowired
	private Map<String, PlfxApiService> serviceMap;

	/**
	 * 经销商缓存
	 */
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

		for (Entry<String, PlfxApiService> entry : serviceMap.entrySet()) {

			PlfxApiService service = entry.getValue();
			Class<?> clazz = service.getClass();

			while (clazz != Object.class) {

				// 获取当前类所有声明的方法
				Method[] methods = clazz.getDeclaredMethods();

				for (Method method : methods) {

					Class<?>[] paramType = method.getParameterTypes();
					Class<?> returnType = method.getReturnType();

					if (returnType == null
							|| paramType.length != 1
							|| !plfx.api.client.common.ApiRequestBody.class.isAssignableFrom(paramType[0])
							|| !plfx.api.client.common.ApiResponse.class.isAssignableFrom(returnType))
						continue;

					PlfxApiAction action = method.getAnnotation(PlfxApiAction.class);
					
					if (action != null) {
						actionMap.put(action.value(), method);
						Type[] types = method.getGenericParameterTypes();
//						ParameterizedType pType = (ParameterizedType) types[0];
//						bodyClassMap.put(action.value(), (Class<?>) pType.getActualTypeArguments()[0]);
						bodyClassMap.put(action.value(), (Class<?>) types[0]);
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

		String dealerKey = (String) header.get("dealerKey");//获取经销商代码
		String actionName = (String) header.get("actionName");

		if (dealerKey == null || dealerKey.trim().length() == 0)
			throw new MsgCheckException("经销商Key不能为空");

		if (actionName == null || actionName.trim().length() == 0)
			throw new MsgCheckException("未指定接口方法名称");

		Dealer dealer = dealerCacheManager.getDealer(dealerKey);

		if (dealer == null)
			throw new MsgCheckException("无效经销商代码");

		if (!StringUtils.equalsIgnoreCase(sign, DigestUtils.md5Hex(dealer.getSecretKey() + msg)))
			throw new MsgCheckException("签名认证未通过");

		request.setAttribute("actionName", actionName);
//		request.setAttribute("dealerId", dealer.getId());
		//避免修改经销商代码，code和id不一致导致的问题
		request.setAttribute("dealerCode", dealer.getCode());
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
	private ApiResponse executeAction(String actionName, String requestJson, String dealerCode, String ip)
			throws InvocationTargetException {

		Method method = actionMap.get(actionName);
		Class<ApiRequestBody> bodyClass = (Class<ApiRequestBody>) bodyClassMap.get(actionName);

		if (method == null)
			return ApiResponse.error("接口方法不存在");

		PlfxApiService service = methodServiceMap.get(actionName);

		if (service == null)
			return ApiResponse.error("接口不存在");

		try {
			ApiRequest<ApiRequestBody> apiRequest = ApiRequest.parseRequest(requestJson, bodyClass);
			ApiRequestBody body = apiRequest.getBody();
			ApiRequestHead head = apiRequest.getHeader();
			// 设置经销商ID
			body.setFromDealerId(dealerCode);
			body.setFromDealerCode(head.getDealerKey());
			body.setFromDealerIp(ip);
			ApiResponse response=(ApiResponse) method.invoke(service, body);
			return response;
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
	public String api(HttpServletRequest request,
			@RequestParam("msg") String msg, 
			@RequestParam("sign") String sign) {
		NetworkUtil networkUtil = new NetworkUtil();
		String ip = networkUtil.getIpAddr(request);
		HgLogger.getInstance().info("yuqz", "调用分销请求来源IP="+ip);
		//System.out.println("msg>>>>>>>>" + msg);
		//System.out.println("sign>>>>>>>" + sign);

		hgLogger.debug("plfx-api", String.format(
				"接收参数 sessionId(%s) msg(%s) sign(%s)", request.getSession().getId(), msg, sign));

		ApiResponse apiResponse = null;
		try {
			checkMsg(request, msg, sign);
			try {
				apiResponse = executeAction(
						(String) request.getAttribute("actionName"), msg,
						(String) request.getAttribute("dealerCode"), ip);
			} catch (InvocationTargetException e) {
				throw (Exception) e.getTargetException();
			}
		} catch (PlfxApiException apiException) {
			apiResponse = ApiResponse.fail(apiException.getCode(),
					apiException.getMessage());
		} catch (MsgCheckException e) {
			apiResponse = ApiResponse.checkFail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = ApiResponse.error("系统异常");
		}

		String resultJson = JSON.toJSONString(apiResponse);

		hgLogger.debug("plfx-api", String.format(
				"返回结果 sessionId(%s) result(%s)", request.getSession().getId(), resultJson));

		return resultJson;
	}
	
}