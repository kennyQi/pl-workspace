package hg.jxc.admin.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javassist.expr.NewArray;

public class IpCountUtil {
	private static Map<String, Integer> ipCountMp = new HashMap<String, Integer>();

	public static void addIpCount(String ip) {
		Integer count = ipCountMp.get(ip);
		if (count == null) {
			ipCountMp.put(ip, 1);
		} else {
			count++;
			ipCountMp.put(ip, count);
		}
	}

	public static Integer getIpCount(String ip) {
		Integer count = ipCountMp.get(ip);
		if (count == null) {
			return 1;

		}
		return count;
	}
	
	static public void reset() {
		ipCountMp = new HashMap<String, Integer>();
	}

	public static String getRequestIp(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Forwarded-For");
		// 如果通过多级反向代理，X-Forwarded-For的值不止一个，而是一串用逗号分隔的IP值，此时取X-Forwarded-For中第一个非unknown的有效IP字符串
		if (isEffective(remoteAddr) && (remoteAddr.indexOf(",") > -1)) {
			String[] array = remoteAddr.split(",");
			for (String element : array) {
				if (isEffective(element)) {
					remoteAddr = element;
					break;
				}
			}
		}
		if (!isEffective(remoteAddr)) {
			remoteAddr = request.getHeader("X-Real-IP");
		}
		if (!isEffective(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		}
		return remoteAddr;
		// return "192.168.92.128";
	}

	private static boolean isEffective(final String remoteAddr) {
		boolean isEffective = false;
		if ((null != remoteAddr) && (!"".equals(remoteAddr.trim())) && (!"unknown".equalsIgnoreCase(remoteAddr.trim()))) {
			isEffective = true;
		}
		return isEffective;
	}

}
