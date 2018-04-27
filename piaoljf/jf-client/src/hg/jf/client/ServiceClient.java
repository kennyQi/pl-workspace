/**
 * @ServiceClient.java Create on 2015年2月2日上午10:52:07
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf.client;

import hgtech.jf.security.JfSecurityUtil;
import hgtech.web.util.WebServiceUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @类功能说明：访问web service的客户端
 * @类修改者：
 * @修改日期：2015年2月2日上午10:52:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年2月2日上午10:52:07
 * @version：
 */
public class ServiceClient {
	static Log log=LogFactory.getLog(ServiceClient.class);
    private String md5Key = "";
    protected String charsetName = "utf-8";

    /**
     * @类名：ServiceClient.java Created on 2015年2月2日上午10:52:07
     * 
     * @Copyright (c) 2012 by www.hg365.com。
     */
    public ServiceClient() {
	super();
    }

    /**
     * @方法功能说明：以string方式调用web service，post方式发送数据。自动加上签名
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午4:21:54
     * @version：
     * @修改内容：
     * @参数：@param url
     * @param md5Data
     *            参与签名的数据,同时会post提交
     * @参数：@return
     * @参数：@throws MalformedURLException
     * @参数：@throws IOException
     * @参数：@throws UnsupportedEncodingException
     * @return:String
     * @throws
     */
    public String callWebServicePost(String url, String data) throws MalformedURLException, IOException,
	    UnsupportedEncodingException {
	        url = appendSign(url, data);
	        HttpClient client = new HttpClient();
	        client.getParams().setHttpElementCharset("UTF-8");
	        client.getParams().setContentCharset("UTF-8");
	    
	        PostMethod post = new PostMethod(url);
	        post.setParameter("data", data);
	        log.info(url);
	        log.info("data=" + data);
	        client.executeMethod(post);
	        return post.getResponseBodyAsString();
	    }

    /** abc
     * @方法功能说明：以http get方式调用web service，自动加上签名
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午4:21:54
     * @version：
     * @修改内容：
     * @参数：@param url 已经含有所有提交的数据
     * @param md5Data
     *            参与签名的数据
     * @参数：@return
     * @参数：@throws MalformedURLException
     * @参数：@throws IOException
     * @参数：@throws UnsupportedEncodingException
     * @return:String
     * @throws
     */
    public String callWebServiceGet(String url, String data) throws MalformedURLException, IOException,
	    UnsupportedEncodingException {
	        url = appendSign(url, data);
	        System.out.println(url);
	        String s= WebServiceUtil.callWebService(url, "utf-8");
	        return s;
	}

    /**
     * @方法功能说明：加上签名部分。如time=&sign=
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月27日下午8:19:16
     * @version：
     * @修改内容：
     * @参数：@param url
     * @参数：@param md5Data
     * @参数：@return
     * @return:String
     * @throws
     */
    public String appendSign(String url, String md5Data) {
    
        String time = System.currentTimeMillis() + "";
        return url + (url.contains("?") ? "&time=" : "?time=") + time + "&sign="
        	+ JfSecurityUtil.md5(md5Data, time, md5Key);
    }

    /**
     * @return the md5Key
     */
    public String getMd5Key() {
        return md5Key;
    }

    /**
     * @param md5Key
     *            the md5Key to set
     */
    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

 }