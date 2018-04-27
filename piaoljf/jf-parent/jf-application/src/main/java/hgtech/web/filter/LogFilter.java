package hgtech.web.filter;

import hg.hjf.domain.model.log.OperationLog;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.tools.JavaCompiler;

public class LogFilter implements javax.servlet.Filter{

	/**
	 * 系统入口。
	 */
	String entry=OperationLog.entryAdmin;
	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain arg2) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url=request.getRequestURL().toString();
		AuthUser user = (AuthUser)request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		if(user!=null){
			
			//线程上记录操作员，以便service层获取。
        	OperationLog log = new OperationLog();
        	log.setIp(request.getRemoteAddr());
        	log.setUri(request.getRequestURI());
        	log.setEntry(getEntry());
        	log.setLoginName(user.getLoginName());
			OperationLog.threadLocal.set(log);    
			
		}
		arg2.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
