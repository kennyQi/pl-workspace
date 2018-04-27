package com.huigou.common.util;

import hg.common.util.MobileUtil;
import hg.common.util.MobileUtil.Result;
import junit.framework.TestCase;

public class MobileTest extends TestCase {

	public void testMobile(){
		String numbers[]={"13323262335","17098541253","15466523566",
				"1302562355","56232562","12235262335","17856265658",
				"15866523511","17236541258","17145852365","15326584924"};
		Result result;
		for (int i = 0; i < numbers.length; i++) {
			result=MobileUtil.checkMobile(numbers[i]);
			System.out.println(numbers[i]+":"+result.getCode());
		}
		
		//正确
		assertTrue(MobileUtil.checkMobile("15305153869").getCode()!=-1);
		//错误号码
		assertTrue(MobileUtil.checkMobile("25305153869").getCode()==-1);
	}
}
