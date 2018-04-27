/**
 * @TestWebService.java Create on 2016年4月25日下午6:31:52
 * Copyright (c) 2012 by www.hg365.com。
 */
package com.huigou.common.util;

import java.util.HashMap;
import java.util.Map;

import hg.common.util.WebServiceUtil;

import org.junit.Test;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016年4月25日下午6:31:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2016年4月25日下午6:31:52
 * @version：
 */
public class TestWebService {

    @Test
    public void testGet(){
	
	//https test
	String s="https://admin.51hjf.com/service/queryjf?pageNum=1&numPerPage=20&code=xiaoying&time=1418174498791&sign=11d16c360f87663f3914bc677963c58f";
	System.out.println( WebServiceUtil.callWebService(s, "utf-8"));
	
	//with parameters
	System.out.println("=============================");
	HashMap para = new HashMap();//code=xiaoying&time=1418174498791&sign=11d16c360f87663f3914bc677963c58f
	para.put("code", "15305153869");
	para.put("time", "1418174498791");
	para.put("sign", "11d16c360f87663f3914bc677963c58f");
	para.put("pageNum", "1");
	
	System.out.println(WebServiceUtil.callWebServiceGet("https://admin.51hjf.com/service/queryjf", para));

	//http
	System.out.println("-----------------------");
	s=WebServiceUtil.callWebServiceGet("http://baidu.com",para);
	System.out.println(s);
	
	//testPost();
    }

    /**
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2016年4月26日下午1:47:11
     * @version：
     * @修改内容：
     * @参数：
     * @return:void
     * @throws
     */
    @Test
    public void testPost() {
	//https post
	Map para=new HashMap();
	para.put("mobile", "15305153870");
	para.put("imgValidCode", "123456a");
	para.put("token", "8ad938f701bd42caad31f9c9017add9a");
	System.out.println(WebServiceUtil.callWebServicePost("https://www.51hjf.com/register/submit_mobile", para));
	
	//http
	System.out.println(WebServiceUtil.callWebServicePost("http://sms.hg365.com/hgsms/send", para));
    }
}
