package hsl.h5.base.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 中文乱码过滤器
 * @author 胡永伟
 */
public class CharacterFilter implements Filter {

	private static final String CHARSET = "utf-8";
	
	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String charset = filterConfig.getInitParameter("charset");
		if (charset == null || "".equals(charset)) {
			charset = CHARSET;
		}
		
		// 解决POST乱码问题
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		response.setContentType("text/html;charset=" + charset);

		// 动态代理解决GET乱码问题
		chain.doFilter((ServletRequest) Proxy.newProxyInstance(
				CharacterFilter.class.getClassLoader(), request.getClass()
						.getInterfaces(), new InvocationHandler() {

					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {

						// 代理getParameter方法
						if ("getParameter".equalsIgnoreCase(method.getName())) {
							String value = (String) method
									.invoke(request, args);
							if (value == null) {
								return null;
							}
							if (!"GET".equalsIgnoreCase(request.getMethod())) {
								return value;
							}
							return new String(value.getBytes("ISO-8859-1"),
									request.getCharacterEncoding());
						}
						return method.invoke(request, args);
					}

				}), response);

	}

	public void destroy() {}

}
