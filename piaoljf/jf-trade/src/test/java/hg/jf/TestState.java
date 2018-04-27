/**
 * @TestState.java Create on 2015年1月15日下午3:47:19
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf;

import hg.jf.state.TradeState;
import hg.jf.state.TradeStateAction;
import hg.state.State;
import hg.state.StateDao;
import hg.state.StatePath;

import org.junit.Assert;
import org.junit.Test;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015年1月15日下午3:47:19
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月15日下午3:47:19
 * @version：
 */
public class TestState {

	public static TradeStateAction faOrder=new FackeOrder();
	@Test
	public void teststate(){
		TradeState order=TradeState.getInstance();
		State stInited = order.stInited;
		System.out.println(stInited.getAllPathes());
		System.out.println(stInited.getAvailablePathes(faOrder));
		System.out.println(
		StateDao.instance.pathes);
		
		//go path
		StatePath firstPath = stInited.getAvailablePathes(faOrder).getFirst();
		State st= stInited.goPath(faOrder, firstPath);
		System.out.println(st);
		System.out.println(st.getAvailablePathes(faOrder));
		if(st.state==400)
			st=st.nextState(st.getAllPathes().iterator().next());
		System.out.println(st);
	}
	
}
