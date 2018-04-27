package hsl.h5.base.springmvc;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
        if(!validToken(request,response)){
        	response.sendRedirect(request.getContextPath()+"/error");
        	return false;
        }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private Boolean validToken(HttpServletRequest request,HttpServletResponse response){
		String uri = request.getRequestURI();
		String he = request.getContextPath();
		uri = uri.substring(he.length(),uri.length());
		HttpSession session = request.getSession();
		if(TokenUrlMapper.VMAPPER.contains(uri)){
//			System.out.println("开始校验token");
			if(!TokenHandler.validToken(session, TokenHandler.getInputToken(request))){
				return false;
			}
		}
		//跳转的页面是需要生成token的则生成
		if(TokenUrlMapper.MAPPER.contains(uri)){
			TokenHandler.generateGUID(session);
			checkUriNoCache(request,response,uri);
		}
		return true;
	}
	/**
	 * 检查是否不需要页面缓存
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean checkUriNoCache(HttpServletRequest request, HttpServletResponse response,String uri) {
		if(TokenUrlMapper.MAPPER.contains(uri)){
			response.setHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			return true;
		}
		return false;
	}
}
