package pl.h5.base.utils;

import java.util.HashMap;
import java.util.Map;

public class CachePool {
	
	private CachePool() {}

	private static Map<String, String> users = new HashMap<String, String>();

	public static String getUser(String openid) {
		return users.get(openid);
	}

	public static void setUser(String openid, String userId) {
		users.put(openid, userId);
	}

}
