package hg.dzpw.admin.component.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.DelegatingFilterProxy;

public class MyDelegatingFilterProxy extends DelegatingFilterProxy {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			if (req.getRequestURI().indexOf(req.getContextPath() + "/resources/") == 0) {
				filterChain.doFilter(request, response);
				return;
			}
		}
		super.doFilter(request, response, filterChain);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	
}
