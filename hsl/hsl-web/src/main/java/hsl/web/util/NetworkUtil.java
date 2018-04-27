package hsl.web.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 
 * @类功能说明：常用获取客户端信息的工具
 * @公司名称：浙江汇购科�?有限公司
 * @部门：技术部
 * @作�?�：yuqz
 * @创建时间�?2015�?11�?16日上�?9:51:29
 * @版本：V1.0
 * 
 */
public class NetworkUtil {
	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */

	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;

	}
	
	public static void main(String[] args) {
		
	}
}
