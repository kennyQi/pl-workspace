/**
 * @TestXml.java Create on 2016年4月25日下午5:12:06
 * Copyright (c) 2012 by www.hg365.com。
 */
package com.huigou.common.util;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.annotation.XmlRootElement;

import junit.framework.Assert;
import hg.common.util.XmlUtil;

import org.junit.Test;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016年4月25日下午5:12:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2016年4月25日下午5:12:06
 * @version：
 */

public class TestXml {
    
    @XmlRootElement
    private static class Student{
	String id,name;
	int age;
	/**
	 * @return the id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
	    return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
	    return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
	    this.age = age;
	}
    }
    
    @Test
    public void testXml(){
	Student st = new Student();
	st.age=30;
	st.id="sdfdsfsf";
	st.name="邢立军";
	
	 
	String xml=XmlUtil.beanToXML(st );
	System.out.println(xml);
	
	Student st2=(Student) XmlUtil.XMLStringToBean(Student.class, xml);
	System.out.println(st2);
	Assert.assertTrue(st2.getId().equals(st.getId()));
    }
    
}
