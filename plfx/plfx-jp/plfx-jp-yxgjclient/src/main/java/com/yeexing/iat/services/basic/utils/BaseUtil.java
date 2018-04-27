package com.yeexing.iat.services.basic.utils;

import java.util.Map;

public class BaseUtil {

	public static boolean mapNotNull(Map<Object, Object> map) {
		return map==null?false:true;
	}

	public static boolean stringNotNull(String key) {
		return (key==null||"".equals(key))?false:true;
	}


}
