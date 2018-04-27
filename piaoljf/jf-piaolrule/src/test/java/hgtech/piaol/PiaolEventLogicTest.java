package hgtech.piaol;

import static org.junit.Assert.assertTrue;
import hgtech.jf.piaol.rulelogic.PiaolEventLogic;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.dao.imp.RuledSession_Mem;
import hgtech.jfcal.model.CalModel;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.model.Rule;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class PiaolEventLogicTest {

	@Test
	public void test手机绑定() throws Exception {
		
		Class  class1 = PiaolEventLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.parameters.put("integralIncrement", "500");
		r1.logicClass=class1.getName();
		r1.session=new RuledSession_Mem(r1.code);
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;

		flow.tradeName=flow.TRADE_PHONEBINDING;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==500);
		System.out.println("用户第一次绑定手机，用户所得积分："+result.out_jf);
		
		flow.tradeName=flow.TRADE_PHONEBINDING;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户更换绑定的手机，用户所得积分："+result.out_jf);
		System.out.println();
		
	}
	
	@Test
	public void test邮箱绑定() throws Exception {
		
		Class  class1 = PiaolEventLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.parameters.put("integralIncrement", "500");
		r1.logicClass=class1.getName();
		r1.session=new RuledSession_Mem(r1.code);
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;

		flow.tradeName=flow.TRADE_MAILBINDING;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==500);
		System.out.println("用户绑定的邮箱，用户所得积分："+result.out_jf);
		
		flow.tradeName=flow.TRADE_MAILBINDING;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户更换绑定的邮箱，用户所得积分："+result.out_jf);
		System.out.println();
		
	}
	
	@Test
	public void test完善资料() throws Exception {
		
		Class  class1 = PiaolEventLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.parameters.put("integralIncrement", "500");
		r1.logicClass=class1.getName();
		r1.session=new RuledSession_Mem(r1.code);
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;

		flow.tradeName=flow.TRADE_PERFECTINFO;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==500);
		System.out.println("用户完善资料，用户所得积分："+result.out_jf);
		flow.tradeName=flow.TRADE_PERFECTINFO;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户更改资料，用户所得积分："+result.out_jf);
		System.out.println();
		
	}
	
	
	@Test
	public void test整个业务() throws Exception {
		
		Class  class1 = PiaolEventLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.parameters.put("integralIncrement", "500");
		r1.logicClass=class1.getName();
		r1.session=new RuledSession_Mem(r1.code);
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;

		flow.tradeName=flow.TRADE_PERFECTINFO;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==500);
		System.out.println("用户完善资料，用户所得积分："+result.out_jf);
		
		flow.tradeName=flow.TRADE_MAILBINDING;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==500);
		System.out.println("用户绑定邮箱，用户所得积分："+result.out_jf);
		
		flow.tradeName=flow.TRADE_MAILBINDING;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户更改绑定的邮箱，用户所得积分："+result.out_jf);
		
		flow.tradeName=flow.TRADE_PERFECTINFO;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户修改个人资料，用户所得积分："+result.out_jf);
 
		flow.tradeName=flow.TRADE_MAILBINDING;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==0);
		System.out.println("用户更改邮箱，用户所得积分："+result.out_jf);
		
		flow.tradeName=flow.TRADE_PHONEBINDING;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==500);
		System.out.println("用户绑定手机，用户所得积分："+result.out_jf);
		System.out.println();
		
		flow.tradeName=flow.TRADE_CONSUMPTION;
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		assertTrue(result.out_jf==500);
		System.out.println("首次交易，用户所得积分："+result.out_jf);
		System.out.println();		
	}


}
