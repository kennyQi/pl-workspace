/**
 * @JiuJiuUserController.java Create on 2015年1月22日下午4:23:12
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import hg.common.util.SMSUtils;
import hgtech.jf.JfProperty;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfadmin.dto.CheckSmsDto;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.File;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：
 * @类修改者：模拟久加久的用户管理
 * @修改日期：2015年1月22日下午4:23:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月22日下午4:23:12
 * @version：
 */
@Controller
@RequestMapping(value = "/service/user")
public class JiuJiuUserController {

    /**
     * @FieldsINT_30MIN:
     */
    private static final int INT_30MIN = 30 * 60000;
    @Resource
    private SMSUtils smsUtils;
    
    private static class User{
	String name,phone;

	/**
	 * @类名：JiuJiuUserController.java Created on 2015年2月12日下午2:16:47
	 * 
	 * @Copyright (c) 2012 by www.hg365.com。
	 */
	public User(String name, String phone) {
	    super();
	    this.name = name;
	    this.phone = phone;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	 User the=(User) obj;
	 return  name.equals(the.name) && phone.equals(the.phone);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	 return name +"/"+phone +"";
	}
    }
    
    static List<User> users = new ArrayList<>();
    static  String file = JfProperty.getProperties().getProperty("jfPath","/main/apps/jfPath")+"/9a9_user.txt";
    static {
	try {
	List<String> lines= FileUtils.readLines(new java.io.File(file),"utf-8");
	   for(String l:lines){
	       String []line=l.split("\\s");
	       if(line.length>=2)
	       users.add(new User(line[0], line[1]));
	   }
	   
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public JiuJiuUserController(){
	//启动移除过期消息
	new Thread(){
	    public void run() {
		try {
		    Thread.currentThread().sleep(60000);
		    removeTimedSms();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    };
	}.start();
    }
    
    // 暂时先用map存短消息  消息的清除
    HashMap<String, CheckSmsDto> smsMap = new HashMap<>();

    @ResponseBody
    @RequestMapping(value = "/sendSms")
    public String sendSms(HttpServletRequest request, HttpServletResponse response, Model model,
	    @ModelAttribute CheckSmsDto dto) throws Exception {
	// 生成验证码
	dto.code = "" + (int) (Math.random() * 10000);
	dto.createTime = System.currentTimeMillis();
	smsMap.put(dto.orderId, dto);

	JSONObject js = new JSONObject();
	boolean status = checkUserPhoneValid(dto);
	js.put("status", status);
	String value = status ? "你正在转出久加久积分，验证码发出 " : "您提供的用户与手机号不匹配 ";
	js.put("text", value);
	
	if(status){
	
        	String string = "【"+SetupAccountContext.sysDomain.name+"】你正在绑定积分账户或转出积分，验证码为 " + dto.code;
        	System.out.println(" 正要发送短信 " + dto.phone + " " + string);
		smsUtils.sendSms(dto.phone, string);
	}
	return js.toJSONString();
    }

    /**
     * @方法功能说明：
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年2月12日下午2:35:48
     * @version：
     * @修改内容：
     * @参数：@param dto
     * @参数：@return
     * @return:boolean
     * @throws
     */
   static boolean checkUserPhoneValid(CheckSmsDto dto) {
	
	User u=new User(dto.user,dto.phone);
	boolean contains = users.contains(u);
	if(!contains)
	{   System.out.println("该用户 与文件中的用户手机列表不符，不是久加久用户合法的用户、手机号码.\n请检查文件"+file);
		System.out.println(users);
	}
	return contains;
	
//	boolean status =dto.phone.startsWith("130")?false: true;
//	return status;
    }

    @ResponseBody
    @RequestMapping(value = "/checkSms")
    public String checkSms(HttpServletRequest request, HttpServletResponse response, Model model,
	    @ModelAttribute CheckSmsDto dto) {
	JSONObject js = new JSONObject();
	CheckSmsDto checkSms = smsMap.get(dto.orderId);
	System.out.println("map key:"+smsMap.keySet());
	System.out.println("key: "+dto.orderId);
	if(checkSms!=null){
        	boolean lt30Min = System.currentTimeMillis() - checkSms.createTime <= INT_30MIN;
        	boolean status = dto.code.equals(checkSms.code) && lt30Min;
        	js.put("status", status);
        	js.put("text", status ? "ok" : "验证码不对或超过30分钟");
	}else{
        	js.put("status", false);
        	js.put("text",   "验证码不对或超过30分钟");
	    
	}
	return js.toJSONString();
    }
    
    /**
     * 
     * @方法功能说明：30分钟后移除消息
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年1月31日下午4:13:36
     * @version：
     * @修改内容：
     * @参数：
     * @return:void
     * @throws
     */
    private void removeTimedSms(){
	int i=0;
	for(Entry<String, CheckSmsDto> en:smsMap.entrySet()){
	    if(System.currentTimeMillis() - en.getValue().createTime > INT_30MIN)
		{
			smsMap.remove(en.getKey());
			i++;
		}
	}
	System.out.println("移除过期消息条数 "+i);
    }

    public static void main(String[] args) {
	CheckSmsDto dto = new CheckSmsDto();
	dto.user="xlj";
	dto.phone="15305153869";
	System.out.println(checkUserPhoneValid(dto));
    }
}
