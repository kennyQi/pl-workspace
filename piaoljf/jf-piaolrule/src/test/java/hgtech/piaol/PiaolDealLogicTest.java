package hgtech.piaol;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import hgtech.jf.piaol.rulelogic.PiaolDealLogic;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.dao.imp.RuledSession_Mem;
import hgtech.jfcal.model.CalModel;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.model.Rule;

import org.junit.Test;

public class PiaolDealLogicTest {

	@Test
	public void testCompute() throws Exception {
		
		Class class1=PiaolDealLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolDealLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("IntegralIncrement", "1000");
		rule.parameters.put("totleDays", "3");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		trade.setUser("pel");
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		
		assertTrue(result.out_jf==0);
		DataRow session=rule.session.get(trade.user);
		
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140311");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140311");
		
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("");
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140312");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140314");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140402");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140302");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		System.out.println();
		
	}
	
	@Test
	public void test连续三天() throws Exception {
		
		Class class1=PiaolDealLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("IntegralIncrement", "1000");
		rule.parameters.put("totleDays", "3");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
        DataRow session=rule.session.get(trade.user);
		
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140311");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140312");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	@Test
	public void test连续多于三天() throws Exception {
		
		Class class1=PiaolDealLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("IntegralIncrement", "1000");
		rule.parameters.put("totleDays", "3");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		Iterator it=null;
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		DataRow session=rule.session.get(trade.user);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140317");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140313");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140316");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
	}
	
	@Test
	public void test同一天连续多次购物() throws Exception {
		
		Class class1=PiaolDealLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("IntegralIncrement", "1000");
		rule.parameters.put("totleDays", "3");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		DataRow session=rule.session.get(trade.user);
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140313");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140316");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	@Test
	public void test本月才对上个月的交易确认收货() throws Exception {
		
		Class class1=PiaolDealLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("IntegralIncrement", "1000");
		rule.parameters.put("totleDays", "3");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		DataRow session=rule.session.get(trade.user);
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140313");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140416");
		results=cal.cal(trade);
		it=results.iterator(); 
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140313");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140413");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140315");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140416");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140411");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	@Test
	public void test跨年处理的订单() throws Exception {
		
		Class class1=PiaolDealLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("IntegralIncrement", "1000");
		rule.parameters.put("totleDays", "3");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20141210");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		DataRow session=rule.session.get(trade.user);
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20141213");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20150116");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20141213");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20141220");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20150116");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20150111");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20150110");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	@Test
	public void test跨年处理的订单2() throws Exception {
		
		Class class1=PiaolDealLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("IntegralIncrement", "1000");
		rule.parameters.put("totleDays", "3");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20141210");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		DataRow session=rule.session.get(trade.user);
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20141120");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20141129");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20141122");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20141213");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20150116");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20141213");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20141220");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20150116");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20150111");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20150110");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	
	
	@Test
	public void test多个月的情况() throws Exception {
		
		Class class1=PiaolDealLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("IntegralIncrement", "1000");
		rule.parameters.put("totleDays", "3");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		DataRow session=rule.session.get(trade.user);
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140313");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140416");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140313");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140413");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140315");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140416");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140411");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		
		trade.setDate("20140513");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140515");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140416");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140511");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	
	@Test
	public void test多个月没有购物() throws Exception {
		
		Class class1=PiaolDealLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("IntegralIncrement", "1000");
		rule.parameters.put("totleDays", "3");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20140310");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		DataRow session=rule.session.get(trade.user);
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140313");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140416");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140313");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140413");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140315");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140416");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140411");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==1000);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		
		trade.setDate("20140513");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140515");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20140416");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		trade.setDate("20150111");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("有一段时间没有购物");
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		System.out.println();
		trade.setDate("20150113");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户上月购物天数："+session.get("countDays1"));
		System.out.println("用户本月购物天数："+session.get("countDays2"));
		System.out.println("用户所得积分："+result.out_jf);
		System.out.println();
	}

}
