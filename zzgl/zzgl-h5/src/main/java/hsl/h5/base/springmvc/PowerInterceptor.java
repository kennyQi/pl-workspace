package hsl.h5.base.springmvc;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hsl.api.base.ApiResponse;
import hsl.h5.base.result.api.ApiResult;
import hsl.h5.base.result.user.QueryUserResult;
import hsl.h5.base.utils.CachePool;
import hsl.h5.control.constant.Constants;
import hsl.h5.exception.HslapiException;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.user.HslUserBindAccountQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.user.UserService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限拦截 : wap + wechat
 * @author 胡永伟
 */
public class PowerInterceptor implements HandlerInterceptor {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
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
			//调试自定义菜单需要屏蔽
			/*// 访问者是微信浏览器且无openid则根据网页授权获取openid
			if(isWCBrowser(request) && StringUtils.isEmpty(openid)) {
				HgLogger.getInstance().info("chenxy", "PowerInterceptor>>访问者是微信浏览器且无openid>>>");
				*//**
				 * 回调本地的weixin认证接口 WeiXinCtrl>>auth@
				 *//*
				String redirectUri = getWebAppPath(request) + "/weixin/auth";
		        redirectUri = URLEncoder.encode(redirectUri, "utf-8");
				String url = String.format(
						"https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect", 
						SysProperties.getInstance().get("wx_app_id"), redirectUri, getWCState(request));
				response.sendRedirect(url);	// 微信网页授权处理
				return false;
			}
			HgLogger.getInstance().info("chenxy", "PowerInterceptor>>获得当前请求的openid>>>:"+openid);
			OpenidTracker.set(openid);*/
			// 检验URL是否需要拦截
			if(isInterceptUri(uri)) {
				//暂时不需要判断是否是微信浏览器
				/*if (isWCBrowser(request)) {
					//微信浏览器时根据openid判断改用户时候注册以及绑定,跳转到注册页面
					if (!hasRegistered(openid)) {
						//String url = getWebAppPath(request) + "/user/reg?redirectUrl=" + getRedirectUri(request);
						String url = getWebAppPath(request) + "/user/login?redirectUrl=" + getRedirectUri(request);
						response.sendRedirect(url);
						return false;
					}
				} else {
					//不是微信浏览器的时候判断是否登录，跳转到登录页面
					if (!hasLogined(request)) {
						response.sendRedirect(getWebAppPath(request) + "/user/login?redirectUrl=" + getRedirectUri(request));
						return false;
					}
				}*/
				//不是微信浏览器的时候判断是否登录，跳转到登录页面
				if (!hasLogined(request)) {
					response.sendRedirect(getWebAppPath(request) + "/user/login?redirectUrl=" + getRedirectUri(request));
					return false;
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
				uri.indexOf("/user/jpos") > -1 ||
				uri.indexOf("/user/repwdd") > -1 ||
				uri.indexOf("/user/userDetailInfo") > -1 ||
				uri.indexOf("/company") > -1 ||
				uri.indexOf("/user/balance") > -1 ||
				uri.indexOf("/file/imgUpload") > -1||
				uri.indexOf("/company/couponQuery") > -1||
				uri.indexOf("/hslH5/line/dateDetail") > -1||
				uri.indexOf("/hslH5/line/addPersonPage") > -1||
				uri.indexOf("/hslH5/line/detailToPayPage") > -1||
				uri.indexOf("/hslH5/line/userCouponList") > -1||
				uri.indexOf("/hslH5/line/payLineOrder") > -1||
				uri.indexOf("/hslH5/line/lineOrderListPage") > -1||
				uri.indexOf("/lineSalesPlan/lspOrderList") > -1||
				uri.indexOf("/lineSalesPlan/toFillOrders") > -1||
				uri.indexOf("/lineSalesPlan/addTravelerPage") > -1||
				uri.indexOf("/lineSalesPlan/payLSPOrder") > -1
				);
	}

	/**
	 * 判断是否是微信浏览器
	 * @param request
	 * @return
	 */
	private boolean isWCBrowser(HttpServletRequest request){
		String agent = request.getHeader("User-Agent").toLowerCase();
		if(agent.indexOf("micromessenger") > -1){
			return true;
		}
		return false;
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
	private boolean hasRegistered(
			String openid) throws HslapiException {
		return (CachePool.getUser(openid) != null) ||
				(getUserIdByOpenid(openid) != null);
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
			//isAvailable=true
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
				log.info(request.getRequestURI() + " 花费时间=" + tl);
			}
		}
	}
	
	/**
	 * 根据openid获取用户id
	 * @param openid
	 * @return
	 */
	private String getUserIdByOpenid(String openid) {
		HslUserBindAccountQO userBindAccountQO=new HslUserBindAccountQO();
		HslUserQO hslUserQO=new HslUserQO();
		userBindAccountQO.setAccountType(1);
		userBindAccountQO.setBindAccountId(openid);
		userBindAccountQO.setUserQO(hslUserQO);
		QueryUserResult queryUserResult = queryUser(userBindAccountQO);
		if (success(queryUserResult)&&null!=queryUserResult.getUserDTO()) {
			String userId = queryUserResult.getUserDTO().getId();
			CachePool.setUser(openid, userId);
			return userId;
		} else {
			return null;
		}
	}
	
	protected boolean success(ApiResult apiresult) {
		String result = apiresult.getResult();
		return "1".equals(result);
	}
	
	/**
	 * 查询用户
	 * @param userQO
	 * @return
	 */
	public QueryUserResult queryUser(HslUserBindAccountQO userBindAccountQO){
		QueryUserResult userResponse=new QueryUserResult();
		try {
			UserDTO userDTO=userService.queryUser(userBindAccountQO);
			userResponse.setUserDTO(userDTO);
			userResponse.setResult(ApiResponse.RESULT_CODE_OK);
			userResponse.setMessage("用户查询成功");
		} catch (UserException e) {
			userResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			userResponse.setMessage(e.getMessage());
		}
		return userResponse;
	}
	
}