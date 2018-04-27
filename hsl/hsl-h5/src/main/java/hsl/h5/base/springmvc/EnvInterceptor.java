package hsl.h5.base.springmvc;

import hsl.app.component.config.SysProperties;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 环境参数拦截器（设置常用参数到session中）
 *
 * @author zhurz
 */
public class EnvInterceptor implements HandlerInterceptor {

	/**
	 * 检查是否不需要页面缓存
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean checkUriNoCache(HttpServletRequest request, HttpServletResponse response) {
		if (request.getRequestURI().indexOf(request.getContextPath() + "/hslH5/line") == 0) {
			response.setHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			return true;
		}
		return false;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 基础图片地址
		request.getSession().setAttribute("fileUploadPath", SysProperties.getInstance().get("fileUploadPath"));
		request.getSession().setAttribute("imageBaseUrl", SysProperties.getInstance().get("fileUploadPath"));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		checkUriNoCache(request, response);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
