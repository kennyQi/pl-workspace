/**
 * @ServiceClientUtil.java Create on 2015年1月22日下午3:52:05
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.common.util;

import hg.common.util.web.WebClientWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;

import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * @类功能说明：call service
 * @类修改者：
 * @修改日期：2015年1月22日下午3:52:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月22日下午3:52:05
 * @version：
 */
public class WebServiceUtil {
    	static org.apache.commons.logging.Log logger = LogFactory.getLog(WebServiceUtil.class);
	/**
	 * 支持返回类型为json或soap格式
	 * @方法功能说明：call web service.（假定返回内容在1m以内）
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月22日下午4:01:22
	 * @version：
	 * @修改内容：
	 * @参数：@param surl
	 * @参数：@param charset
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String callWebService(String surl, String charset){
	    	logger.info("callWebService "+surl);
		try {
		    	String string="" ;
		    	if(surl.startsWith("https") || surl.startsWith("HTTPS")){
		    	    HttpClient client = WebClientWrapper.getClient();
		    	    HttpGet get = new HttpGet(surl);
		    	    HttpResponse resp = client.execute(get);
		    	    string=EntityUtils.toString(resp.getEntity(),charset);
		    	}else{
		    	    	//普通访问
        			URL url=new URL(surl);
        			InputStream strm = url.openStream();
        			byte[] b=new byte[1024000];
        			int len=strm.read(b);
        			strm.close();
        			string = new String(b,0,len,charset).trim();
		    	}
			//<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:senMobileCodeResponse xmlns:ns2="http://hjf.webservice.remoting.bjrcb.com/"><return>{&quot;status&quot;:true,&quot;text&quot;:&quot;234679&quot;}</return></ns2:senMobileCodeResponse></soap:Body></soap:Envelope>
			//识别 soap
			if(string.startsWith("<soap") || string.startsWith("<SOAP")){
			    string = getSoapReturn(string);
			}
			logger.info("callWebService return "+string);			
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年5月14日下午3:26:05
	 * @version：
	 * @修改内容：
	 * @参数：@param string
	 * @参数：@return
	 * @参数：@throws UnsupportedEncodingException
	 * @参数：@throws SOAPException
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	static String getSoapReturn(String string) throws UnsupportedEncodingException, SOAPException, IOException {
	    InputStream ins = new ByteArrayInputStream(string.getBytes("utf-8")) ;
	    javax.xml.soap.SOAPMessage createMessage = MessageFactory.newInstance().createMessage(new MimeHeaders(), ins);
	    string =  createMessage.getSOAPBody().getElementsByTagName("return").item(0).getTextContent();
	    return string;
	}
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2016年4月26日上午11:35:24
	 * @version：
	 * @修改内容：
	 * @参数：@param url
	 * @参数：@param para
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String callWebServiceGet(String url, Map para){
	    boolean urlWithPara = url.contains("?");
	    int i=0;
	    for(Object k:para.keySet()){
		if(i==0 && !urlWithPara)
		    url +="?";
		if(i>0)
		    url +="&";
		try {
		    url += k +"=" +  URLEncoder.encode(para.get(k).toString(),"utf-8");
		} catch (UnsupportedEncodingException e) {
		    throw new RuntimeException(e);
		}
		
		i++;
	    }
	    return callWebService(url, "utf-8");
	}
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2016年4月26日上午11:35:30
	 * @version：
	 * @修改内容：
	 * @参数：@param url
	 * @参数：@param para
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String callWebServicePost(String surl, Map para){
	    		String charset="utf-8";
			try {
			    	String string="" ;
			    	HttpClient client;
			    	    client = WebClientWrapper.getClient();
			    	    HttpPost post = new HttpPost(surl);
			    	   List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
			    	   for(Object k:para.keySet()){
			    	       nvps.add(new BasicNameValuePair(k.toString(), para.get(k).toString()));  
			    	   }
			           post.setEntity(new UrlEncodedFormEntity(nvps));  
			           
			    	    HttpResponse resp = client.execute(post);
			    	    string=EntityUtils.toString(resp.getEntity(),charset);
				//<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:senMobileCodeResponse xmlns:ns2="http://hjf.webservice.remoting.bjrcb.com/"><return>{&quot;status&quot;:true,&quot;text&quot;:&quot;234679&quot;}</return></ns2:senMobileCodeResponse></soap:Body></soap:Envelope>
				//识别 soap
				if(string.startsWith("<soap") || string.startsWith("<SOAP")){
				    string = getSoapReturn(string);
				}
				logger.info("callWebService return "+string);			
				return string;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} 	    
	}
	 
}
