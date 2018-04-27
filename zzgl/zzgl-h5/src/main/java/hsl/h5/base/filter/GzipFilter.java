package hsl.h5.base.filter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.zip.GZIPOutputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 数据压缩过滤器
 * @author 胡永伟
 */
public class GzipFilter implements Filter {
	
	private static final String CHARSET = "utf-8";
	
	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		
		String exclude = filterConfig.getInitParameter("exclude");
		if (exclude != null) {
			for (String exc : exclude.split(";")) {
				if (request.getRequestURI().indexOf(exc) > -1) {
					chain.doFilter(request, response);
					return;
				}
			}
		}
		
		final ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream();
		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(byteArrOut, CHARSET), true);
		// 动态代理解决数据压缩
		chain.doFilter(request, (ServletResponse) Proxy.newProxyInstance(GzipFilter.class.getClassLoader(), response.getClass().getInterfaces(), new InvocationHandler() {
					public Object invoke(Object proxy,  Method method, Object[] args) throws Throwable {

						// 将流中的数据写到ByteArrayOutputStream中
						if ("getWriter".equalsIgnoreCase(method.getName())) {
							return pw;
						} else if ("getOutputStream".equalsIgnoreCase(method.getName())) {
							return new ServletOutputStream() {
								public void write(int b) throws IOException {
									byteArrOut.write(b);
								}
							};
						} else {
							return method.invoke(response, args);
						}
					}
		}));

		if (pw != null) {
			pw.close();
		}

		if (byteArrOut != null) {
			byteArrOut.flush();
		}

		// 将ByteArrayOutputStream中的数据压缩后存在字节数组中
		byte[] buffer = byteArrOut.toByteArray();
		ByteArrayOutputStream baOut = new ByteArrayOutputStream();
		GZIPOutputStream gzipOut = new GZIPOutputStream(baOut);
		gzipOut.write(buffer);
		gzipOut.close();

		byte[] gzip = baOut.toByteArray();
		response.setHeader("content-encoding", "gzip");
		response.setContentLength(gzip.length);
		response.getOutputStream().write(gzip);

	}

	public void destroy() {}

}
