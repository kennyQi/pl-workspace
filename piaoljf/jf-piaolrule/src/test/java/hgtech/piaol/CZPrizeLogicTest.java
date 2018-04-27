package hgtech.piaol;

import hgtech.jf.hg.rulelogic.CZPrizeLogic;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.dao.imp.RuledSession_Mem;
import hgtech.jfcal.model.CalModel;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.Rule;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

public class CZPrizeLogicTest {
	@Test
	public void test抽奖() throws Exception {
		Class  class1 = CZPrizeLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();
		
		//单个用户的抽奖次数
		r1.parameters.put("actionCount", "30000");
		r1.parameters.put("dayActionCount", "30000");
		
		r1.parameters.put("gv1", "0.0");// 1/100
		r1.parameters.put("gv2", "0.001");// 1/100
		r1.parameters.put("gv3", "0.005");// 1/50
		r1.parameters.put("gv4", "0.15");//  1/10
		r1.parameters.put("gv5", "0.5");// 
		r1.parameters.put("gv6", "-1");// 
		r1.parameters.put("gv6", "-1");// 
		r1.parameters.put("gv7", "-1");// 
		r1.parameters.put("gv8", "-1");// 
		r1.parameters.put("gv9", "-1");// 
		r1.parameters.put("gv10", "-1");// 
		
		r1.parameters.put("jf1", "1000");
		r1.parameters.put("jf2", "200");
		r1.parameters.put("jf3", "300");
		r1.parameters.put("jf4", "400");
		r1.parameters.put("jf5", "500");
		
		r1.parameters.put("count1", "0");
		r1.parameters.put("count2", "1000");
		r1.parameters.put("count3", "3000");
		r1.parameters.put("count4", "6000");
		r1.parameters.put("count5", "15000");
		r1.parameters.put("count6", "0");
		r1.parameters.put("count7", "0");
		r1.parameters.put("count8", "0");
		r1.parameters.put("count9", "0");
		r1.parameters.put("count10", "0");
		
		r1.session=new RuledSession_Mem(r1.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade flow=new PiaolTrade();
		flow.user="TOM";
		flow.level=flow.LEVEL_SILVER;
		cal.project.ruleSet.add(r1);
		
		cal(cal, r1, flow);
		
//		flow.user="mary";
//		cal(cal, r1, flow);
	}

	private void cal(CalModel cal, Rule r1, PiaolTrade flow) throws Exception {
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		flow.date="20000208";
		flow.tradeName=PiaolTrade.TRADE_CHOUJIAGN;
		Map<String, Integer> prize=new HashMap<String, Integer>();
		for(int i=0;i<30000;i++){
			CalResult result1 = result;
			Collection<CalResult> results1;
			Iterator it1;
			results1=cal.cal(flow);
			it1=results1.iterator();
			if(it1.hasNext()){
				result1=(CalResult)it1.next();
			}

			if(result1.out_resultCode.equals("Y"))
				System.out.println("奖项："+result1.out_resultText);
			else
				System.out.println("系统信息为："+result1.out_resultText);
			System.out.println(result1);
			
			result = result1;
			if(result.out_resultCode.equals("Y")){
				if(!prize.containsKey(result.out_resultText))
					prize.put(result.out_resultText, 0);
				
				prize.put(result.out_resultText, prize.get(result.out_resultText)+1);
			}
		}
		
//		flow.date="20000209";
//		flow.tradeName=PiaolTrade.TRADE_CHOUJIAGN;
//		 for(int i=0;i<15;i++){
//			CalResult result1 = result;
//			Collection<CalResult> results1;
//			Iterator it1;
//			results1=cal.cal(flow);
//			it1=results1.iterator();
//			if(it1.hasNext()){
//				result1=(CalResult)it1.next();
//			}
//
//			if(result1.out_resultCode.equals("Y"))
//				System.out.println("奖项："+result1.out_resultText);
//			else
//				System.out.println("系统信息为："+result1.out_resultText);
//			
//			result = result1;
//			if(result.out_resultCode.equals("Y")){
//				if(!prize.containsKey(result.out_resultText))
//					prize.put(result.out_resultText, 0);
//				
//				prize.put(result.out_resultText, prize.get(result.out_resultText)+1);
//			}
//		}		
		 System.out.println("用户获奖 "+prize);
		System.out.println("用户 session "+r1.session.get(flow.user));
		System.out.println("系统session "+r1.session.get(CZPrizeLogic.key_sysuser));		
	}

	 
}
