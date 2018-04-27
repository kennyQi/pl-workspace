package hg.dzpw.app.common.util.alipay;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AlipayReflectUtil {
	/**
	 * 
	 * @描述： 反射获取属性map
	 * @author: guotx 
	 * @version: 2016-3-1 上午9:12:47
	 */
	public static Map<String, String> buildParameterMap(Object object){
		Map<String, String> parameterMap=new HashMap<String,String>();
		String parameterName = null;
		String parameterValue = null;
		Field[] parametersFields=object.getClass().getDeclaredFields();
		for (Field field : parametersFields) {
			parameterName=field.getName();
			try {
				field.setAccessible(true);
				if (field.get(object)!=null && !field.get(object).toString().equals("")) {
					parameterValue=field.get(object).toString();
					parameterMap.put(parameterName, parameterValue);
				}else {
					parameterValue=null;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		//父类属性
		Field[] fields=object.getClass().getSuperclass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String value = null;
			try {
				parameterName=fields[i].getName();
				if (fields[i].get(object)!=null && !fields[i].get(object).toString().equals("")) {
					value=fields[i].get(object).toString();
					parameterMap.put(parameterName, value);
				}else {
					parameterValue=null;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return parameterMap;
	}
}
