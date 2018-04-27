package com.huigou.common.util;

import hg.common.util.DateUtil;

import java.util.Date;

import junit.framework.TestCase;

public class DateUtilTest extends TestCase {

	/**
		20160422
		2016-04-22
		2016-04-22 15:09:00
		2016-04-22 15:09:00.946
		20160422150900
		2016-04-22 15:09
		2016/04/22 15:09
		150900
		2016-04-22 15:09:00
		2016-04-113 03:09:946
	 */
	public void testFormat(){
		Date date=new Date();
		System.out.println(DateUtil.formatDate1(date));
		System.out.println(DateUtil.formatDate2(date));
		System.out.println(DateUtil.formatDateTime1(date));
		System.out.println(DateUtil.formatDateTime2(date));
		System.out.println(DateUtil.formatDateTime3(date));
		System.out.println(DateUtil.formatDateTime4(date));
		System.out.println(DateUtil.formatDateTime5(date));
		System.out.println(DateUtil.formatTime(date));
		System.out.println(DateUtil.formatDateTime(date, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateUtil.formatDateTime(date, "yyyy-MM-DD hh:mm:SS"));
	}
	
	public void testParse(){
		Date date1=DateUtil.parseDate1("20160322");
		Date date2=DateUtil.parseDate2("2016-04-21");
		Date date3=DateUtil.parseDateTime1("2016-04-22 11:29:00");
		//格式错误
		Date date33=DateUtil.parseDateTime1("2016/04/22 11:29:00");
		System.out.println(date33);//值为null
		System.out.println(DateUtil.parseDateTime1(null));
		Date date4=DateUtil.parseDateTime2("2016-04-22 15:09:00.946");
		Date date5=DateUtil.parseDateTime3("20160422150900");
		Date date6=DateUtil.parseDateTime4("2016-04-22 15:09");
		Date date7=DateUtil.parseDateTime5("2016/04/22 15:09");
		System.out.println(DateUtil.checkDate("2016-04-22 15:09:00", "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateUtil.checkDate("2016-04-22 15:09:00", "yyyy-MM-dd HH:ss"));
		System.out.println(DateUtil.dateCompare(date3, date4));
		System.out.println(DateUtil.dateCompare(date3, date2));
		System.out.println(DateUtil.diff(date3, date4, DateUtil.field_day));
		System.out.println(DateUtil.diff(date3, date4, DateUtil.field_hour));
		System.out.println(DateUtil.add(date7, DateUtil.field_day, 25));
	}
}
