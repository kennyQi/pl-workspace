package zzpl.h5.util;


import hg.system.model.staff.Staff;

import java.util.HashMap;
import java.util.Map;

public class LoginUtil {
	
	/**
	 * 连续登录错误限制
	 */
	public static final Map<String, Staff> loginLimitMap = new HashMap<String, Staff>();

	public static Map<String, Staff> getLoginlimitmap() {
		return loginLimitMap;
	}
	
}

