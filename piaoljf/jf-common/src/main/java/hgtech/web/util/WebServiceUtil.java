/**
 * @ServiceClientUtil.java Create on 2015年1月22日下午3:52:05
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.web.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;

import org.apache.commons.logging.LogFactory;

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
			URL url=new URL(surl);
			InputStream strm = url.openStream();
			//缓存数据
			byte[] b1=new byte[1024000];
			//收到数据
			byte[] b=new byte[1024000];
			int len=0;
			int index = 0;
			//把每次读到数据b1放到b中
			while((len=strm.read(b1))!=-1){
				for(int i=0;i<len;i++ ){
					b[i+index]=b1[i];
				}
				index= index+len;
			}
			len =index;
			strm.close();
			String string = new String(b,0,len,charset).trim();
			//<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:senMobileCodeResponse xmlns:ns2="http://hjf.webservice.remoting.bjrcb.com/"><return>{&quot;status&quot;:true,&quot;text&quot;:&quot;234679&quot;}</return></ns2:senMobileCodeResponse></soap:Body></soap:Envelope>
			//识别 soap
			if(string.startsWith("<soap") || string.startsWith("<SOAP")){
			    string = getSoapReturn(string);
			}
			//logger.info("callWebService return "+string);			
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
	
	public static void main(String[] args) throws UnsupportedEncodingException, SOAPException, IOException {
	    System.out.println(callWebService("https://xinglj:8443/hg-hjf-app/login", "utf-8"));
	    
	    
//		String s="http://192.168.2.226:9119/jf-admin/service/queryjf?pageNum=1&numPerPage=20&code=xiaoying&time=1418174498791&sign=11d16c360f87663f3914bc677963c58f";
////		System.out.println( callWebService(s, "utf-8"));
//		
//		String soap="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:senMobileCodeResponse xmlns:ns2=\"http://hjf.webservice.remoting.bjrcb.com/\"><return>{&quot;status&quot;:true,&quot;text&quot;:&quot;234679&quot;}</return></ns2:senMobileCodeResponse></soap:Body></soap:Envelope>";
//		 System.out.println(getSoapReturn(soap));   
	}
}
