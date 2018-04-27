package hsl.admin.interceptor;

import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionInterceptor implements HandlerInterceptor  {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//      log.info("==============执行顺序: 1、preHandle================");  
		String url=request.getRequestURL().toString();
		if(url.contains("/login")||url.contains("/resources/")||url.contains("/timer")||url.contains("/lspTimer")||url.contains("/api")){return true;}
		AuthUser user = (AuthUser)request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		if(user!=null){
			return true;
		}else{
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//        log.info("==============执行顺序: 2、postHandle================");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//        log.info("==============执行顺序: 3、afterCompletion================"); 
	}  
}