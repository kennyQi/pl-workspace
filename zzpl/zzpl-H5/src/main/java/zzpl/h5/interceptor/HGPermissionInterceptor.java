package zzpl.h5.interceptor;

import hg.common.util.DwzJsonResultUtil;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import zzpl.h5.manager.HGSessionUserManager;

public class HGPermissionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 登录检查
		if (!HGSessionUserManager.isLogin(request)) {

			if ("XMLHttpRequest".equalsIgnoreCase(request
					.getHeader("X-Requested-With"))
					|| request.getParameter("ajax") != null) {

				PrintWriter out = response.getWriter();
				out.print(DwzJsonResultUtil.createJsonString("301",
						"登陆超时！请重新登陆！", null, null, null,
						request.getContextPath() + "/login", null));
				return false;
			} else {
				response.sendRedirect(response.encodeRedirectURL(request
						.getContextPath() + "/login"));
				return false;
			}

		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
