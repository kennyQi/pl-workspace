/**
 * 
 */
package com.yeexing.iat.services.basic.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 签名工具类
 * 
 * @author gpwei
 * 
 */
public class SignUtils {

	private static final String IS_SUCCESS = "isSuccess";

	private static final String ERROR_CODE = "errorCode";

	private static final String ERROR_MESSAGE = "errorMessage";

	private static final String SIGN = "sign";

	/**
	 * 根据要签名的bean对象获取签名
	 * 
	 * @param object
	 *            要签名的bean对象
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getSign(Object object, String key) throws Exception {
		Map<Object, Object> params = new HashMap<Object, Object>();

		Method[] methods = object.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			if (methodName.startsWith("get") && !methodName.equalsIgnoreCase("getClass")) {
				String name = getParameterName(methods[i].getName());
				Object value = methods[i].invoke(object, new Object[] {});

				params.put(name, value);
			}
		}

		String sign = MD5Sign(params, key);

		return sign;
	}

	/**
	 * 验证签名
	 * 
	 * @param object
	 *            要签名的bean对象
	 * @param key
	 *            密钥
	 * @param sign
	 *            从参数中获取的签名
	 * @return
	 * @throws Exception
	 */
	public static boolean checkSign(Object object, String key, String sign) throws Exception {
		Map<Object, Object> params = new HashMap<Object, Object>();

		Method[] methods = object.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			if (methodName.startsWith("get") && !methodName.equalsIgnoreCase("getClass")) {
				String name = getParameterName(methods[i].getName());
				Object value = methods[i].invoke(object, new Object[] {});

				params.put(name, value);
			}
		}

		String mySign = MD5Sign(params, key);

		if (mySign.equals(sign)) {
			return true;
		}

		return false;
	}

	/**
	 * 将参数进行MD5签名
	 * 
	 * @param params
	 *            签名Map集合
	 * @param privateKey
	 *            签名Key
	 * @return
	 */
	public static String MD5Sign(Map<Object, Object> params, String key) {
		if (!BaseUtil.mapNotNull(params) || !BaseUtil.stringNotNull(key)) {
			return null;
		}
		//移除空值
		params = processMap(params);

		Properties properties = new Properties();

		Iterator<Object> iter = params.keySet().iterator();

		while (iter.hasNext()) {
			String name = (String) iter.next();
			Object value = params.get(name);

			if (name == null || name.equalsIgnoreCase(IS_SUCCESS) || name.equalsIgnoreCase(ERROR_CODE)
					|| name.equalsIgnoreCase(ERROR_MESSAGE) || name.equalsIgnoreCase(SIGN)) {
				continue;
			}

			properties.setProperty(name, value.toString());

		}

		String content = getSignatureContent(properties) + key;
		return DigestUtils.md5Message(content);
	}

	/**
	 * 将参数排序
	 * 
	 * @param properties
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static String getSignatureContent(Properties properties) {
		StringBuffer content = new StringBuffer();
		List keys = new ArrayList(properties.keySet());
		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = properties.getProperty(key);

			content.append((i == 0 ? "" : "&") + key + "=" + value);
		}

		return content.toString();
	}

	/**
	 * 将map中value为空的移除
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private static Map<Object, Object> processMap(Map<Object, Object> map) {
		Map<Object, Object> returnMap = null;
		if (BaseUtil.mapNotNull(map)) {
			returnMap = new HashMap<Object, Object>();
			for (Object key : map.keySet()) {
				if (map.get(key) != null) {
					if (map.get(key) instanceof String) {
						String str = (String) map.get(key);
						if (!str.equals("") && str != null && !str.equals("null")) {
							returnMap.put(key, map.get(key));
						}
					} else if (null != map.get(key)) {
						returnMap.put(key, map.get(key));
					}
				}
			}
		}
		return returnMap;
	}

	/**
	 * 根据method名称获取属性名称
	 * 
	 * @param methodName
	 * @return
	 */
	private static String getParameterName(String methodName) {
		String parameterName = methodName.substring(3);
		parameterName = parameterName.substring(0, 1).toLowerCase() + parameterName.substring(1);
		return parameterName;
	}
}
