package hgtech.piaol;

import hgtech.jf.hg.rulelogic.HLiChengLogic;
import hgtech.jf.hg.rulelogic.QQTXDiscountLogic;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.dao.imp.RuledSession_Mem;
import hgtech.jfcal.model.CalModel;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.model.Rule;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

public class QQTXDiscountLogicTest {
	//@Test
	public void test抽奖() throws Exception {
		Class  class1 = QQTXDiscountLogic.class;
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();
		
		//用户使用优惠券限制
		r1.parameters.put("userCountLimit", "1");
		//券的总数限制
		r1.parameters.put("quanCountLimit", "3");
		r1.parameters.put("transferCode", "cz");
		r1.parameters.put("proCode1", "123123");
		r1.parameters.put("proCode2", "321321");
		r1.parameters.put("proCode3", "#321321");
		r1.parameters.put("proCode4", "#");
		r1.parameters.put("proDiscount", "8.8");
		r1.parameters.put("transferDiscount", "9");
		
		r1.session=new RuledSession_Mem(r1.code);
		cal.project.ruleSet.add(r1);
		
		PiaolTrade flow=new PiaolTrade();String a = PiaolTrade.TRADE_QUERYDISCOUNTCODEVALID;
		flow.user="TOM";
		DataRow session = r1.session.get(flow.user);
		if (session == null) {// 用户不存在
			session = new DataRow();
			session.put("count", 1);
			r1.session.put(flow.user, session);
		} else {// 用户存在
			
		}
		
		cal(cal, r1, flow);
		
		
		flow=new PiaolTrade();
		flow.user="TOM1";
		session = r1.session.get(flow.user);
		if (session == null) {// 用户不存在
			session = new DataRow();
			session.put("count", 1);
			r1.session.put(flow.user, session);
		} else {// 用户存在
			
		}
		flow.level=flow.LEVEL_SILVER;
		
		cal(cal, r1, flow);
		
		flow=new PiaolTrade();
		flow.user="TOM2";
		session = r1.session.get(flow.user);
		if (session == null) {// 用户不存在
			session = new DataRow();
			session.put("count", 1);
			r1.session.put(flow.user, session);
		} else {// 用户存在
			
		}
		flow.level=flow.LEVEL_SILVER;
		
		cal(cal, r1, flow);
		
		
		flow=new PiaolTrade();
		flow.user="TOM3";
		session = r1.session.get(flow.user);
		if (session == null) {// 用户不存在
			session = new DataRow();
			session.put("count", 1);
			r1.session.put(flow.user, session);
		} else {// 用户存在
			
		}
		flow.level=flow.LEVEL_SILVER;
		
		cal(cal, r1, flow);
//		flow.user="mary";
//		cal(cal, r1, flow);
	}

	private void cal(CalModel cal, Rule r1, PiaolTrade flow) throws Exception {
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		flow.date="20000208";
		flow.tradeName=PiaolTrade.TRADE_DODISCOUNTCODE;
		for(int i=0;i<3;i++){
			CalResult result1 = result;
			Collection<CalResult> results1;
			Iterator it1;
			flow.discountCode="1234567";
			/*if(i==0){
				flow.merchandise="1231231";
			}else if(i==1){
				flow.merchandise="cz";
			}*/
			results1=cal.cal(flow);
			it1=results1.iterator();
			if(it1.hasNext()){
				result1=(CalResult)it1.next();
			}
			System.out.println(result1+" "+result1.out_resultText);
		}
		
	}

	@Test
	public void TestHonghao(){
		Class  templateClass = HLiChengLogic.class;
		CalModel cal = new CalModel();
		Rule huiRule=new Rule();
		huiRule.code="1";
		huiRule.logicClass=templateClass.getName();
		//设置参数
		huiRule.parameters.put("userCountLimit", "1");
		huiRule.parameters.put("jf_account", "1200");
		huiRule.parameters.put("hb_name", "里程红包");
		huiRule.parameters.put("start_jf", "0");
		huiRule.parameters.put("totalCount", "5");
		huiRule.parameters.put("valid_startTime", "2016-01-13");
		huiRule.parameters.put("valid_endTime", "2016-01-31");
		huiRule.parameters.put("platform1", "tt&1");
		huiRule.parameters.put("platform2", "9a9&2");
		huiRule.parameters.put("platform3", "hjf&2");
		huiRule.parameters.put("platform4", "#&#");
		//设置用户数据
		PiaolTrade trade = new PiaolTrade();
		trade.user="18268207061";
		trade.merchant="tt";
		trade.tradeName="huihongbao";
		//执行规则
		huiRule.session=new RuledSession_Mem(huiRule.code);
		cal.project.ruleSet.add(huiRule);
		System.out.println(huiRule.session.get("__sys_hb"));
		try {
			calSimple(cal, huiRule, trade);
			trade = new PiaolTrade();
			trade.user="18268207062";
			trade.merchant="9a9";
			trade.tradeName="huihangbao";
			calSimple(cal, huiRule, trade);
			System.out.println(huiRule.session.get("__sys_hb"));
			trade = new PiaolTrade();
			trade.user="18268207063";
			trade.merchant="9a9";
			trade.tradeName="huihangbao";
			calSimple(cal, huiRule, trade);
			trade = new PiaolTrade();
			trade.user="18268207064";
			trade.merchant="hjf";
			trade.tradeName="huihangbao";
			calSimple(cal, huiRule, trade);
			trade = new PiaolTrade();
			trade.user="18268207065";
			trade.merchant="hjf";
			trade.tradeName="huihangbao";
			calSimple(cal, huiRule, trade);
			trade = new PiaolTrade();
			trade.user="18268207062";
			trade.merchant="hjf";
			trade.tradeName="huihangbao";
			calSimple(cal, huiRule, trade);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(huiRule.session.get("__sys_hb"));
		System.out.println(huiRule.session.get("18268207065"));
	}
	 
	private void calSimple(CalModel cal, Rule rule, PiaolTrade flow) throws Exception {
		CalResult  result=new CalResult();
		Collection<CalResult> results;
		Iterator<CalResult> iterator;
		results=cal.cal(flow);
		iterator=results.iterator();
		if(iterator.hasNext()){
			result=(CalResult)iterator.next();
		}
		System.out.println(result.out_resultCode+" "+result.out_resultText);
	}
}
