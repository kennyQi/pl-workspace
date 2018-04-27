package pl.h5.base.springmvc;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import pl.h5.base.utils.CachePool;
import pl.h5.base.utils.OpenidTracker;
import pl.h5.base.utils.WxUtil;
import pl.h5.exception.HslapiException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * @类功能说明：权限拦截器
 * @类修改者：
 * @修改日期：2015年3月12日下午1:59:57
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月12日下午1:59:57
 */
public class PowerInterceptor implements HandlerInterceptor {
	
	public static final String STATIS_BEGIN_TIME = "STATIS_BEGIN_TIME";
	
	// Action之前执行:
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute(STATIS_BEGIN_TIME, System.currentTimeMillis());
		String uri = request.getRequestURI();
		HgLogger.getInstance().info("chenxy", "PowerInterceptor>>获得当前请求的URL>>>:"+uri);
		// 判断是否是微信网关请求
		if (!isWCGateWay(uri)) {	
			String openid = getOpenidFromHttp(request);
			HgLogger.getInstance().info("chenxy", "PowerInterceptor>>获得openId为：>>>"+openid);
			// 访问者是微信浏览器且无openid则根据网页授权获取openid
			if(WxUtil.isWCBrowser(request) && StringUtils.isEmpty(openid)) {
				HgLogger.getInstance().info("chenxy", "PowerInterceptor>>访问者是微信浏览器且无openid>>>");
				/**
				 * 回调本地的weixin认证接口 WeiXinCtrl>>auth@
				 */
				String redirectUri = getWebAppPath(request) + "/weixin/auth";
		        redirectUri = URLEncoder.encode(redirectUri, "utf-8");
				String url = String.format(
						"https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect", 
						SysProperties.getInstance().get("wx_app_id"), redirectUri, getWCState(request));
				response.sendRedirect(url);	// 微信网页授权处理
				return false;
			}
			HgLogger.getInstance().info("chenxy", "PowerInterceptor>>获得当前请求的openid>>>:"+openid);
			OpenidTracker.set(openid);
			// 检验URL是否需要拦截
			if(isInterceptUri(uri)) {
				if (WxUtil.isWCBrowser(request)) {
					//微信浏览器时根据openid判断改用户时候注册以及绑定,跳转到注册页面
					if (!hasRegistered(openid)) {
						String url = getWebAppPath(request) + "/user/reg?redirectUrl=" + getRedirectUri(request);
						response.sendRedirect(url);
						return false;
					}
				} else {
					//不是微信浏览器的时候判断是否登录，跳转到登录页面
					if (!hasLogined(request)) {
						response.sendRedirect(getWebAppPath(request) + "/user/login?redirectUrl=" + getRedirectUri(request));
						return false;
					}
				}
			}
		}
		return true;
	
	}
	/**
	 * @方法功能说明：从Cookie中获得openId
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月13日上午8:55:23
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String getOpenidFromHttp(HttpServletRequest request) {
		String openid = request.getParameter("openid");
		HgLogger.getInstance().info("chenxy", "PowerInterceptor>>从request中获得openId为：>>>"+openid);
		if (StringUtils.isEmpty(openid)) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0 ; cookies != null && i < cookies.length; i++) {
				if ("openid".equals(cookies[i].getName())) {
					openid = cookies[i].getValue();
				}
			}
		}
		HgLogger.getInstance().info("chenxy", "PowerInterceptor>>从Cookie中获得openId为：>>>"+openid);
		return openid;
	}

	/**
	 * 判断是否是微信网关
	 * @param uri
	 * @return
	 */
	private boolean isWCGateWay(String uri) {
		return (uri.indexOf("/weixin/") > -1);
	}
	
	/**
	 * 判断是否要拦截URL
	 * @param request
	 * @return
	 */
	private boolean isInterceptUri(String uri) {
		return (uri.indexOf("/jpo/") > -1 ||
				uri.indexOf("/mpo/") > -1 
				);
	}
	
	/**
	 * 判断用户的会话时否已经丢失或失效
	 * @param request
	 * @return
	 */
	private boolean hasLogined(HttpServletRequest request) {
		if(request.getSession().getAttribute("user") != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断wx用户是否注册
	 * @param request
	 * @return
	 * @throws HslapiException 
	 */
	private boolean hasRegistered(String openid) throws HslapiException {
		return (CachePool.getUser(openid) != null);
	}
	
	/**
	 * 获取webapp路径
	 * @return
	 */
	private String getWebAppPath(HttpServletRequest request) {
		Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
        String proj = request.getContextPath();
        String port = SysProperties.getInstance().get("port");
        String system = "http://" + SysProperties.getInstance().get("host") + 
        		("80".equals(port) ? "" : ":" + port);
        if (!isRoot) {
        	system += proj;
        }
        return system;
	}
	
	/**
	 * 获取网页授权state参数
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getWCState(HttpServletRequest request) throws UnsupportedEncodingException {
		return getRedirectUri(request);
	}
	
	/**
	 * 获取重定向URI
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getRedirectUri(HttpServletRequest request) 
			throws UnsupportedEncodingException {
		String redirectUri = request.getServletPath();
		if (StringUtils.isNotBlank(request.getQueryString())) {
			redirectUri = (redirectUri + "?" + request.getQueryString());
		}
		return URLEncoder.encode(getWebAppPath(request) + redirectUri, "utf-8");
	}

	//生成视图之前执行
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	//最后执行，可用于释放资源
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Long beginTime = (Long) request.getAttribute(STATIS_BEGIN_TIME);
		if (beginTime != null) {
			long tl = System.currentTimeMillis() - beginTime;
			if(tl > 1000) {
			}
		}
	}
	
}