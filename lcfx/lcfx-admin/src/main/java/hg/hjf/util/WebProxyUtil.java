package hg.hjf.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 向另外网址代理请求，并对返回内容的地址做修改，改为发起方的ip和端口。因此对返回页面中的提交地址也要做代理
 * @author xinglj
 *
 */
public class WebProxyUtil {
	
	/**
	 * 直接代理。返回内容原样返回
	 * @param url
	 * @param request
	 * @param response
	 * @throws HttpException
	 * @throws IOException
	 */
	public static  void proxy(String url,HttpServletRequest request,HttpServletResponse response) throws HttpException, IOException{
		PostMethod m=doHttp(url, request);
		for(Header h:m.getResponseHeaders())
			response.setHeader(h.getName(),h.getValue());
		response.getOutputStream().write(m.getResponseBody());
	}

	/**
	 * 返回 响应的string
	 * @param url
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String getContent(String url, HttpServletRequest request)
			throws IOException, HttpException {
		PostMethod method = doHttp(url, request);
		String s=method.getResponseBodyAsString();
		return s;
	}

	private static PostMethod doHttp(String url, HttpServletRequest request)
			throws IOException, HttpException {
		HttpClient client = new HttpClient();
        client.getParams().setHttpElementCharset("UTF-8");
        client.getParams().setContentCharset("UTF-8");
    		
		PostMethod method= new PostMethod(url); 
		NameValuePair[] params = new NameValuePair[request.getParameterMap().size()];
		int i=0;
		for(Object k:request.getParameterMap().keySet()){
			params[i]=new NameValuePair();
			params[i].setName(k.toString());
			final Object v = request.getParameter(k.toString());
			if(v!=null)
			params[i].setValue(v.toString());
			i++;
		}
		method.setQueryString(params);
		client.executeMethod(method);
		return method;
	}

	 
}
