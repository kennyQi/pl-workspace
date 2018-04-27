package hg.jxc.admin.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SensitiveCharsFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub

		boolean isSaveOrUpdate = false;

		HttpServletRequest request = (HttpServletRequest) arg0;
		String requestURI = request.getRequestURI();
		if (requestURI.indexOf("update") >= 0 || requestURI.indexOf("create") >= 0 || requestURI.indexOf("list") >= 0) {
			isSaveOrUpdate = true;
		}

		if (isSaveOrUpdate) {
			request = new MyRequestWrapper(request);
		}
		arg2.doFilter(request, arg1);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
