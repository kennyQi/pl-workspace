package hsl.web.interceptor;

import hsl.web.util.TokenHandler;
import hsl.web.util.TokenUrlMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!validToken(request)) {
			response.sendRedirect(request.getContextPath() + "/error");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

	private Boolean validToken(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String he = request.getContextPath();
		uri = uri.substring(he.length(), uri.length());
		HttpSession session = request.getSession();
		if (TokenUrlMapper.VMAPPER.contains(uri)) {
			// 开始校验token
			if (!TokenHandler.validToken(session, TokenHandler.getInputToken(request))) return false;
		}
		// 跳转的页面是需要生成token的则生成
		if (TokenUrlMapper.MAPPER.contains(uri)) {
			// 可以产生token
			TokenHandler.generateGUID(session);
		}
		return true;
	}

}
