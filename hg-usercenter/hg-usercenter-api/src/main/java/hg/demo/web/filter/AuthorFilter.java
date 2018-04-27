package hg.demo.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import hg.demo.member.common.common.DateUtils;

/**
 * 
 * @author xuww
 *
 */
public class AuthorFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorFilter.class);


	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getRequestURL().toString();
		if(url.endsWith(".ico")){
			return;
		}
		if(url.endsWith("/genSign")){
			chain.doFilter(request, response);
			return ;
		}
		String sign = request.getParameter("sign");
		String time = request.getParameter("time");
		String appId = request.getParameter("appId");
//		String token = request.getParameter("token");//appId对应一个token,暂时不用
		if (StringUtils.isBlank(sign)) {
			LOGGER.info("sign is null!!!");
			resp.sendError(HttpStatus.UNAUTHORIZED.value());
			return;
		}
		if (StringUtils.isBlank(appId)) {
			LOGGER.info("appId is null!!!");
			resp.sendError(HttpStatus.UNAUTHORIZED.value());
			return;
		}
		if (StringUtils.isBlank(time)) {
			LOGGER.info("time is null!!!");
			resp.sendError(HttpStatus.UNAUTHORIZED.value());
			return;
		}else{
			Date date = DateUtils.format(time);
			if(date == null){
				LOGGER.info("time format error!!!");
				resp.sendError(HttpStatus.UNAUTHORIZED.value());
				return;
			}
			long longtime = DateUtils.longtime(date);
			if(longtime > 1800000){//超过前後30分钟
				LOGGER.info("timeout!!!");
				resp.sendError(HttpStatus.UNAUTHORIZED.value());
				return;
			}
		}
		
		HGMessageDigest hgDigest = HGMessageDigest.getInstance();
		boolean bValid = hgDigest.validate(sign, time, appId);		
		if (!bValid) {
			LOGGER.info("sign is error!!!");
			resp.sendError(HttpStatus.UNAUTHORIZED.value());
			return;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}


}
