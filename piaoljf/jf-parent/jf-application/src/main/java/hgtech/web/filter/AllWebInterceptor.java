package hgtech.web.filter;

import java.util.Enumeration;

import hg.hjf.app.service.sys.SysControlService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AllWebInterceptor   implements HandlerInterceptor{

	@Autowired
	SysControlService sysControlService;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//check day end
		sysControlService.checkDayEnd();
		
		//check nextUrl
//		String nextUrl = request.getParameter("nextUrl");
//		if(nextUrl !=null && nextUrl.length()>0){
//			if(isAttack(nextUrl)){
//				response.getWriter().write("Are you attacking me?");
//				return false;
//			}
//		}
		
		// 过滤非法字符，2016.3,xinglj
		boolean isCheckChar=! request.getRequestURI().contains("/service/"); // “/service/”不检查
		if(isCheckChar){
			@SuppressWarnings("unchecked")
			Enumeration<String> e = request.getParameterNames();
	    	while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				if(isAttack(name)){
					response.getWriter().write("Are you attacking me?");
					return false;
				}
				String value = request.getParameter(name);
				if(isAttack(value)){
					response.getWriter().write("Are you attacking me?");
					return false;
				}
			}		
		}
		
		return true;
	}

	public boolean isAttack(String name) {
		boolean is = false;
		for (int i = 0; i < name.length(); i++) {
			final char c = name.charAt(i);
			if (c == '<' || c == '{' || c == '(' || c=='\'' || c=='"')  {
				is = true;
				break;
			}
		}
			
		return is;
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
	
}
