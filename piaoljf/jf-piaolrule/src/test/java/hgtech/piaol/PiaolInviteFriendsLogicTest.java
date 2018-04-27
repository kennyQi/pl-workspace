package hgtech.piaol;

import static org.junit.Assert.*;
import hgtech.jf.piaol.rulelogic.PiaolInviteFriendsLogic;
import hgtech.jf.piaol.rulelogic.PiaolSignLogic;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.dao.imp.RuledSession_Mem;
import hgtech.jfcal.model.CalModel;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.Rule;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

public class PiaolInviteFriendsLogicTest {

	@Test
	public void testCompute() throws Exception {
		
		Class  class1 = PiaolInviteFriendsLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.parameters.put("integralIncrement", "300");
		r1.logicClass=class1.getName();
		r1.session=new RuledSession_Mem(r1.code);
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		cal.project.ruleSet.add(r1);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;

		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		System.out.print("新邀请一位好友所得积分：");
		System.out.println(result.out_jf);
		results=cal.cal(flow);
		it=results.iterator();
		if(it.hasNext())
			result=(CalResult) it.next();
		System.out.print("新邀请一位好友所得积分：");
		System.out.println(result.out_jf);
		
	}

}
