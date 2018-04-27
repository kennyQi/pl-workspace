package hgfx.web.interceptor;

import java.util.ArrayList;
import java.util.List;

import hg.framework.common.util.UUIDGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 防止csrf攻击的token匹配
 * @author Caihuan
 * @date   2016年6月20日
 */
public class AvoidCSRFInterceptor implements HandlerInterceptor{

	/**
	 * 菜单页面，产生token，其余操作要匹配token
	 */
	private static final List<String> menuList = new ArrayList<String>();
	static
	{
		menuList.add("/index");
		menuList.add("/orderManage/orderList");
		menuList.add("/mileOrder/newOrderView");
		menuList.add("/importHistory/importHistoryList");
		menuList.add("/account/accountManageOperate");
		menuList.add("/userManager/staffList");
		menuList.add("/bill/list");
		
	}
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String contnetPath = request.getContextPath();
		String url = request.getRequestURI();
		String subContentPath = url.substring(contnetPath.indexOf(contnetPath)+contnetPath.length());
		//菜单
		if(menuList.contains(subContentPath))
		{
			request.getSession().setAttribute("token", UUIDGenerator.getUUID());
			return true;
		}
		else
		{
			String reqToken = (String) request.getParameter("token");
			String sessionToken = (String) request.getSession().getAttribute("token");
			if(reqToken!=null&&reqToken.equals(sessionToken))
			{
				return true;
			}
		}
		
		
		response.getWriter().println("error");
		return false;
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
