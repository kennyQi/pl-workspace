package hsl.h5.base.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Http访问跟踪器
 * @author 胡永伟
 */
public final class HttpTracker {

	private static final ThreadLocal<HttpServletRequest> 
						tl = new ThreadLocal<HttpServletRequest>();

	public static void setRequest(HttpServletRequest request) {
		tl.set(request);
	}

	public static HttpServletRequest getRequest() {
		return tl.get();
	}

	public static void removeRequest() {
		tl.remove();
	}

}
