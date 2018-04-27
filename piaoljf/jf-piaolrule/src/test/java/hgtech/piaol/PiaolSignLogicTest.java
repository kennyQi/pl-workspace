package hgtech.piaol;

import static org.junit.Assert.assertTrue;
import hgtech.jf.piaol.rulelogic.PiaolSignLogic;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.dao.imp.RuledSession_Mem;
import hgtech.jfcal.model.CalModel;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.model.Rule;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

public class PiaolSignLogicTest {

	@Test
	public void testCompute() throws Exception {
		Class  class1 = PiaolSignLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();
		r1.parameters.put("norjf", "100");
		r1.parameters.put("siljf", "100");
		r1.parameters.put("goljf", "120");
		r1.parameters.put("ptjf", "140");
		r1.parameters.put("diajf", "160");
		r1.parameters.put("jfRatio1", "1.5");
		r1.parameters.put("jfRatio2", "2.0");
		r1.session=new RuledSession_Mem(r1.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		flow.level=flow.LEVLE_PT;
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		 
		flow.date="20000129";
	    results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    DataRow session=r1.session.get(flow.user);
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
	    flow.date="20000130";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000131";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000201";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println();
	    System.out.println("用户在新的月份继续签到！");
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000202";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000203";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000204";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000205";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000206";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000207";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000208";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000209";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000210";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000211";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000212";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000213";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000216";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println();
	    System.out.println("签到过程周有中断");
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000217";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000218";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000219";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000220";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000221";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000222";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000223";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000224";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000225";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000226";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
	    
		flow.date="20000227";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		
		flow.date="20010228";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
	    
	    
	}
	
	@Test
	public void test连续签到超8天() throws Exception {
		Class  class1 = PiaolSignLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();
		r1.parameters.put("norjf", "100");
		r1.parameters.put("siljf", "100");
		r1.parameters.put("goljf", "120");
		r1.parameters.put("ptjf", "140");
		r1.parameters.put("diajf", "160");
		r1.parameters.put("jfRatio1", "1.5");
		r1.parameters.put("jfRatio2", "2.0");
		r1.session=new RuledSession_Mem(r1.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		flow.level=flow.LEVEL_NORMAL;
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;

		flow.date="20000202";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    DataRow session=r1.session.get(flow.user);
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000203";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000204";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000205";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000206";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000207";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000208";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000209";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000210";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000211";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000212";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("当前的积分"+result.out_jf);
		flow.date="20000213";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
	    flow.date="20000214";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000215";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000216";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000217";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==200);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000218";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==200);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000219";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==200);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000220";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==200);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000221";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==200);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000222";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==200);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000223";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==200);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000224";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==200);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000225";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==200);
		System.out.println("当前用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	@Test
	public void test连续签到中间断开() throws Exception {
		Class  class1 = PiaolSignLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();
		r1.parameters.put("norjf", "100");
		r1.parameters.put("siljf", "100");
		r1.parameters.put("goljf", "120");
		r1.parameters.put("ptjf", "140");
		r1.parameters.put("diajf", "160");
		r1.parameters.put("jfRatio1", "1.5");
		r1.parameters.put("jfRatio2", "2.0");
		r1.session=new RuledSession_Mem(r1.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		flow.level=flow.LEVEL_NORMAL;
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		flow.date="20000208";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    DataRow session=r1.session.get(flow.user);
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000209";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000211";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000212";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000213";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
	    flow.date="20000214";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000215";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000216";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000217";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000218";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000219";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000221";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000222";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	@Test
	public void test重复签到() throws Exception {
		Class  class1 = PiaolSignLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();
		r1.parameters.put("norjf", "100");
		r1.parameters.put("siljf", "100");
		r1.parameters.put("goljf", "120");
		r1.parameters.put("ptjf", "140");
		r1.parameters.put("diajf", "160");
		r1.parameters.put("jfRatio1", "1.5");
		r1.parameters.put("jfRatio2", "2.0");
		r1.session=new RuledSession_Mem(r1.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		flow.level=flow.LEVEL_SILVER;
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		flow.date="20000208";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    DataRow session=r1.session.get(flow.user);
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000209";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
	    flow.date="20000209";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("重复签到不得积分！");
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==0);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000211";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000212";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
	    flow.date="20000212";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==0);
		System.out.println("当前用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	@Test
	public void test新的月份() throws Exception {
		Class  class1 = PiaolSignLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();
		r1.parameters.put("norjf", "100");
		r1.parameters.put("siljf", "100");
		r1.parameters.put("goljf", "120");
		r1.parameters.put("ptjf", "140");
		r1.parameters.put("diajf", "160");
		r1.parameters.put("jfRatio1", "1.5");
		r1.parameters.put("jfRatio2", "2.0");
		r1.session=new RuledSession_Mem(r1.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		flow.level=flow.LEVLE_PT;
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		flow.date="20000124";
	    results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    DataRow session=r1.session.get(flow.user);
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000125";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000126";
	    results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000127";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000128";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000129";
	    results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000130";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000131";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000201";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("新的月份："+flow.date);
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000202";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000203";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000204";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000205";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000206";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000207";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000208";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
	    flow.date="20000209";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==210);
		System.out.println("当前用户所得积分："+result.out_jf);
	    flow.date="20000301";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000302";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==140);
		System.out.println("当前用户所得积分："+result.out_jf);
		System.out.println();
	}
	
	@Test
	public void test积分等级() throws Exception {
		Class  class1 = PiaolSignLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();
		r1.parameters.put("norjf", "100");
		r1.parameters.put("siljf", "100");
		r1.parameters.put("goljf", "120");
		r1.parameters.put("ptjf", "140");
		r1.parameters.put("diajf", "160");
		r1.parameters.put("jfRatio1", "1.5");
		r1.parameters.put("jfRatio2", "2.0");
		r1.session=new RuledSession_Mem(r1.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		flow.level=flow.LEVEL_NORMAL;
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		flow.date="20000130";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    DataRow session=r1.session.get(flow.user);
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000131";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000201";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000202";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000203";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000204";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000205";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000206";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000207";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==100);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000208";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
	    flow.date="20000209";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==150);
		System.out.println("当前用户所得积分："+result.out_jf);
	    
	    flow.user="pel";
		flow.level=flow.LEVEL_GOLD;
		
		
		flow.date="20000130";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==120);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000131";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==120);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000201";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==120);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000202";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==120);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000203";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==120);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000204";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==120);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000205";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==120);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000206";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==120);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000207";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==120);
		System.out.println("当前用户所得积分："+result.out_jf);
		flow.date="20000208";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==180);
		System.out.println("当前用户所得积分："+result.out_jf);
	    flow.date="20000209";
		results=cal.cal(flow);
	    it=results.iterator();
	    if(it.hasNext()){
	    	result=(CalResult)it.next();
	    }
	    System.out.println("用户级别："+flow.level);
	    System.out.println("用户连续签到次数："+session.get("countDays"));
	    assertTrue(result.out_jf==180);
		System.out.println("当前用户所得积分："+result.out_jf);
		System.out.println();
	}
	
}
