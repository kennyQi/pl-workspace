package pl.h5.base.utils;
/**
 * 微信Openid跟踪器
 * @author 胡永伟
 */
public class OpenidTracker {

	private static final ThreadLocal<String> 
				threadLocal= new ThreadLocal<String>();
	
	public static void set(String openid) {
		threadLocal.set(openid);
	}

	public static String get() {
		return threadLocal.get();
	}

	public static void remove() {
		threadLocal.remove();
	}
	
}
