package hg.common.util;

import hg.common.model.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class HttpUtil {
	
	public static HttpResponse reqForPost(String postUrl, String context, Integer timeout) {
		HttpResponse res = new HttpResponse();
		Date sendTime = new Date();
		res.setSendTime(sendTime);
		try {
			URL url = new URL(postUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true，默认是false
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			//	设定请求的方法为"post"，默认为"get"
			httpURLConnection.setRequestMethod("POST");
			if (timeout == null) {
				timeout = 30000;
			}
			httpURLConnection.setConnectTimeout(timeout);
			OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
			writer.write(context);
			writer.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			writer.close();
			in.close();	
			Date handOverTime = new Date();
			Long responseTime = handOverTime.getTime()-sendTime.getTime();
			res.setRespoinsCode(httpURLConnection.getResponseCode());
			if(responseTime>timeout){
				res.setHandOverTime(new Date());
				res.setResult("请求连接超时！");
				res.setResponseTime(0L);
				res.setResponseState(3);	
			}else{			
				res.setHandOverTime(handOverTime);
				if (sb.length() <= 0) {
					res.setResult(httpURLConnection.getResponseMessage());
				} else {
					res.setResult(sb.toString());
				}
				res.setResponseTime(handOverTime.getTime()-sendTime.getTime());
				res.setResponseState(1);	
			}			
		} catch (Exception e) {
			res.setHandOverTime(new Date());
			res.setResponseTime(0L);
			res.setResponseState(3);
			res.setResult("响应超时!");
			res.setRespoinsCode(500);
		}
		return res;
	}
	
	public static HttpResponse reqForGet(String getUrl, Integer timeout) {
		HttpResponse res = new HttpResponse();
		Date sendTime = new Date();
		res.setSendTime(sendTime);
		try {
			URL url = new URL(getUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 默认是false
			httpURLConnection.setDoOutput(false);
			//	设置是否从httpUrlConnection读入，默认是true
			httpURLConnection.setDoInput(true);
			//	设定请求的方法为"post"，默认为"get"
			httpURLConnection.setRequestMethod("GET");
			if (timeout == null) {
				timeout = 30000;
			}
			httpURLConnection.setConnectTimeout(timeout);
			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}		
			in.close();
			Date handOverTime = new Date();
			Long responseTime = handOverTime.getTime()-sendTime.getTime();
			res.setRespoinsCode(httpURLConnection.getResponseCode());
			if(responseTime>timeout){
				res.setHandOverTime(new Date());
				res.setResult("请求连接超时！");
				res.setResponseTime(0L);
				res.setResponseState(3);	
			}else{			
				res.setHandOverTime(handOverTime);
				if (sb.length() <= 0) {
					res.setResult(httpURLConnection.getResponseMessage());
				} else {
					res.setResult(sb.toString());
				}
				res.setResponseTime(handOverTime.getTime()-sendTime.getTime());
				res.setResponseState(1);	
			}
		} catch (Exception e) {
			res.setHandOverTime(new Date());
			res.setResult("请求连接超时！");
			res.setResponseTime(0L);
			res.setResponseState(3);	
			res.setRespoinsCode(500);
		}
		return res;
	}
	
	/**
	 * 获取cokie信息
	 * @param request
	 * @param key
	 * @param domain
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String key, String domain) {
		if (StringUtils.isNotBlank(key)) {
			// 获取cookie[]组
			Cookie[] cookies = request.getCookies();
			// 遍历cookie[]组，获取匹配cookie
			if (cookies != null) {
				for (Cookie cookie:cookies) {
					if (key.equals(cookie.getName())) {
						return cookie.getValue();
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 设置cokie信息
	 * @param request
	 * @param key
	 * @param domain
	 * @return
	 */
	public static void setCookie(HttpServletResponse response, String key, String value, String domain, Integer maxAge) {
		Cookie c = new Cookie(key, value);
		if (StringUtils.isNotEmpty(domain)) {
			c.setDomain(domain);
		}
		if (maxAge != null) {
			c.setMaxAge(maxAge);
		}
		c.setPath("/");
		response.addCookie(c);
	}
	
	/**
	 * 获取用户IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip=request.getHeader("Proxy-Client-IP");
		}
		if (ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip=request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip=request.getRemoteAddr();
		}
		return ip;
	}
}
