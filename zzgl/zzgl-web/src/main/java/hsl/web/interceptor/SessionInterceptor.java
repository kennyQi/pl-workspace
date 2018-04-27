package hsl.web.interceptor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import hsl.pojo.dto.user.UserDTO;
import hsl.web.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

public class SessionInterceptor implements HandlerInterceptor  {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
        String url=request.getServletPath();
        
        @SuppressWarnings("unchecked")
		Enumeration<String> e = request.getParameterNames();
        String contextPath=ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
        UserDTO user=(UserDTO) request.getSession().getAttribute(BaseController.SESSION_USER_KEY);
        if(null==user){
        	StringBuffer sb=new StringBuffer();
        	sb.append("?");
        	while (e.hasMoreElements()) {
    			String name = (String) e.nextElement();
    			String value = request.getParameter(name).trim();
    			sb.append(name+"="+value+"&");
    		}
        	sb.delete(sb.length()-1,sb.length());
        	if(request.getHeader("x-requested-with") != null&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ 
        		//ajax请求特殊处理
        		Map<String,String> result=new HashMap<String, String>();
        		result.put("session", "no");
        		result.put("url", contextPath+"/user/login");
        		request.getSession().setAttribute("redirectUrl",url+sb.toString());
        		response.getWriter().write(JSON.toJSONString(result));
  	         }else{
  	        	//记录触发跳转事件的问题
  	        	request.getSession().setAttribute("redirectUrl",url+sb.toString());
  	        	response.sendRedirect(contextPath+"/user/login");
  	         }
            return false;   
        }    
        return true; 
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