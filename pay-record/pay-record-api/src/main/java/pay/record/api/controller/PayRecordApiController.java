package pay.record.api.controller;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pay.record.api.client.common.ApiRequest;
import pay.record.api.client.common.ApiRequestBody;
import pay.record.api.client.common.ApiResponse;
import pay.record.api.client.common.api.PayRecordApiAction;
import pay.record.api.client.common.api.PayReocrdApiService;
import pay.record.api.client.common.exception.PayRecordApiException;
import pay.record.api.client.common.util.NetworkUtil;
import pay.record.api.client.common.util.RSAUtils;
import pay.record.api.exception.MsgCheckException;
import pay.record.app.component.cache.AuthIPCacheManager;
import pay.record.domain.model.authip.AuthIP;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：分销对支付记录接口
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月23日上午10:41:18
 * @版本：V1.0
 *
 */
@Controller
public class PayRecordApiController {

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
	private Map<String, PayReocrdApiService> methodServiceMap = new HashMap<String, PayReocrdApiService>();

	/**
	 * 实现支付记录接口的服务
	 */
	@Autowired
	private Map<String, PayReocrdApiService> serviceMap;
	
	/**
	 * 请求授权ip缓存
	 */
	@Autowired
	private AuthIPCacheManager authIPCacheManager;


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

		for (Entry<String, PayReocrdApiService> entry : serviceMap.entrySet()) {

			PayReocrdApiService service = entry.getValue();
			Class<?> clazz = service.getClass();

			while (clazz != Object.class) {

				// 获取当前类所有声明的方法
				Method[] methods = clazz.getDeclaredMethods();

				for (Method method : methods) {

					Class<?>[] paramType = method.getParameterTypes();
					Class<?> returnType = method.getReturnType();

					if (returnType == null
							|| paramType.length != 1
							//isAssignableFrom 是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口
							|| !ApiRequestBody.class.isAssignableFrom(paramType[0])
							|| !ApiResponse.class.isAssignableFrom(returnType))
						continue;

					PayRecordApiAction action = method.getAnnotation(PayRecordApiAction.class);
					
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
	private void checkMsg(HttpServletRequest request, String msg, String ip) {
		
		JSONObject jsonObject = null;
		
		AuthIP authip = authIPCacheManager.getAuthIP(ip);
		if(authip == null){
			throw new MsgCheckException(ip + "未授权");
		}

		try {
			jsonObject = JSONObject.parseObject(msg);
		} catch (Exception e) {
			throw new MsgCheckException("非法的请求体");
		}

		if (jsonObject == null)
			throw new MsgCheckException("非法的请求体");

		JSONObject header = (JSONObject) jsonObject.get("header");
		JSONObject body = (JSONObject) jsonObject.get("body");

		if (header == null)
			throw new MsgCheckException("请求体头部不能为空");

		if (body == null)
			throw new MsgCheckException("请求体不能为空");

		String actionName = (String) header.get("actionName");

		if (actionName == null || actionName.trim().length() == 0)
			throw new MsgCheckException("未指定接口方法名称");
		request.setAttribute("actionName", actionName);
	}

	/**
	 * 
	 * @方法功能说明：执行接口方法
	 * @修改者名字：yuqz
	 * @修改时间：2015年11月27日下午5:19:24
	 * @修改内容：
	 * @参数：@param actionName
	 * @参数：@param requestJson
	 * @参数：@param dealerCode
	 * @参数：@param ip
	 * @参数：@return
	 * @参数：@throws InvocationTargetException
	 * @return:ApiResponse
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private ApiResponse executeAction(String actionName, String requestJson, String ip)
			throws InvocationTargetException {

		Method method = actionMap.get(actionName);
		Class<ApiRequestBody> bodyClass = (Class<ApiRequestBody>) bodyClassMap.get(actionName);
		
		if (method == null)
			return ApiResponse.error("接口方法不存在");

		PayReocrdApiService service = methodServiceMap.get(actionName);

		if (service == null)
			return ApiResponse.error("接口不存在");

		try {
//			String modulus = "124772110688646963986080649555964103539422188358187828168937575305950354245761868431506632387775213795975959903801685687213725513485979867335122668713621985468566123166884763277431607358336804344573792493151032125487645367798247528849276077786351681288665334101843452187400145922866688656369859774560299619129";
//			String exponent = "118028701376972832633734682228695158443726117571232348251114408366150300005584738901860837006273164461014840994189320600253519587539303322938812119061003609985063485309620896171524508208987613625106181312013237805487057044390321140277632554689148254374021763278669836738436087423294847492331709108303246053233";
			String modulus = SysProperties.getInstance().get("modulus");
			String exponent = SysProperties.getInstance().get("exponent");
			RSAPrivateKey privateKey = RSAUtils.getPrivateKey(modulus, exponent);
			ApiRequest<ApiRequestBody> apiRequest = ApiRequest.parseRequest(requestJson, bodyClass);
			ApiRequestBody body = apiRequest.getBody();
			String ciphertext = body.getCiphertext();
			String msg = RSAUtils.decryptByPrivateKey(ciphertext, privateKey);
			if(StringUtils.isBlank(msg)){
				return ApiResponse.error("解密异常");
			}
			JSONObject jsonObject = JSON.parseObject(msg);
			body = body.getClass().newInstance();
			body = JSON.toJavaObject(jsonObject, body.getClass());
			//设置请求来源IP
			body.setFromProjectIP(ip);
			
//			boolean bool = checkMsgBody(body);
//			if(!bool){
//				return ApiResponse.error("请求参数不完整或者有误");
//			}
			ApiResponse response=(ApiResponse) method.invoke(service, body);
			return response;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			return ApiResponse.error("解密异常");
		}

		return ApiResponse.error("未知异常");
	}
	
	/**
	 * 
	 * @方法功能说明：验证必填参数是否都满足要求
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月8日下午4:29:42
	 * @修改内容：
	 * @参数：@param body
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	private boolean checkMsgBody(ApiRequestBody body) {
		boolean bool = true;
//		if(body.getPayAccountNo() == null || body.getPaySerialNumber() == null ||
//				body.getPayPlatform() == null || body.getFromProjectCode() == null || body.getRecordType() == null ||
//				StringUtils.isBlank(BasePayRecordConstants.PAY_PLATFORM_MAP.get(body.getPayPlatform().toString())) ||
//				StringUtils.isBlank(BasePayRecordConstants.FROM_PROJECT_CODE_MAP.get(body.getFromProjectCode().toString())) ||
//				StringUtils.isBlank(BasePayRecordConstants.RECORD_TYEP_MAP.get(body.getRecordType().toString()))){
//			bool = false;
//		}
		return bool;
	}
	
	/**
	 * @方法功能说明: api
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api")
	@ResponseBody
	public String api(HttpServletRequest request,
			@RequestParam("msg") String msg) {
		ApiResponse apiResponse = null;
		NetworkUtil networkUtil = new NetworkUtil();
		String ip = networkUtil.getIpAddr(request);
		HgLogger.getInstance().info("yuqz", "调用只对记录请求来源IP="+ip);
		hgLogger.debug("pay-record-api", String.format(
				"接收参数 sessionId(%s) msg(%s)", request.getSession().getId(), msg));
		
		try {
			checkMsg(request, msg, ip);
			try {
				apiResponse = executeAction(
						(String) request.getAttribute("actionName"), msg, ip);
			} catch (InvocationTargetException e) {
				throw (Exception) e.getTargetException();
			}
		} catch (PayRecordApiException apiException) {
			apiResponse = ApiResponse.fail(apiException.getCode(),
					apiException.getMessage());
		} catch (MsgCheckException e) {
			apiResponse = ApiResponse.checkFail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = ApiResponse.error("系统异常");
		}

		String resultJson = JSON.toJSONString(apiResponse);

		hgLogger.debug("pay-record-api", String.format(
				"返回结果 sessionId(%s) result(%s)", request.getSession().getId(), resultJson));

		return resultJson;
	}
	
}