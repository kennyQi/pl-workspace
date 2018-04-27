/**
 * @文件名称：PiaolConsumptionLogicTest.java
 * @类路径：hgtech.jf.piaol.rulelogic
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年10月21日上午10:27:33
 */
package hgtech.piaol;

import static org.junit.Assert.*;
import hg.common.util.JsonUtil;
import hgtech.jf.hg.rulelogic.HjfConsumptionLogic;
import hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.dao.imp.RuledSession_Mem;
import hgtech.jfcal.model.CalModel;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.Rule;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;
/**
 * @类功能说明：测试下单消费基本规则
 * @类修改者：
 * @修改日期：2014年10月21日上午10:27:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月21日上午10:27:33
 *
 */
public class PiaolConsumptionLogicTest {

	@Test
	public void testCompute() throws Exception {
		Class class1=PiaolConsumptionLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("jfRatio", "0.2");
		rule.parameters.put("birthdayRatio", "2");
		rule.parameters.put("privilegeRatio", "2");
		rule.parameters.put("wirelessRatio", "2");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		trade.birthday="20141020";
		trade.money=150.0;
		trade.level=trade.LEVEL_SILVER;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20141012");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.println(result.out_jf);
		System.out.println();
	}
	
	/**
	 * @类功能说明：测试用户在生日周内下单消费规则
	 * @类修改者：
	 * @修改日期：2014年10月21日上午10:27:33
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：pengel
	 * @创建时间：2014年10月21日上午10:27:33
	 *
	 */
	@Test
	public void test生日周() throws Exception {
		Class class1=PiaolConsumptionLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("jfRatio", "0.2");
		rule.parameters.put("birthdayRatio", "2");
		rule.parameters.put("privilegeRatio", "2");
		rule.parameters.put("wirelessRatio", "2");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		trade.birthday="20141020";
		trade.money=150.0;
		trade.level=trade.LEVEL_SILVER;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20141014");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员在非生日周购物 积分：");
		assertTrue(result.out_jf==30);
		System.out.println(result.out_jf);
		trade.setDate("20141021");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员在生日周购物 积分：");
		assertTrue(result.out_jf==60);
		System.out.println(result.out_jf);
		System.out.println();
	}
	
	@Test
	public void test生日周_赵启峰() throws Exception {
		Class class1=HjfConsumptionLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("jfRatio", "0.2");
		rule.parameters.put("birthdayRatio", "2");
		rule.parameters.put("privilegeRatio", "2");
		rule.parameters.put("wirelessRatio", "2");
		rule.session=new RuledSession_Mem(rule.code);
		cal.project.ruleSet.add(rule);
		
		PiaolTrade trade=new PiaolTrade();
		String js="{	\"@type\":\"hgtech.jf.piaol.trade.PiaolTrade\",	\"birthday\":\"11111117120000\",	\"date\":\"20151116051558\",	\"flowId\":\"d061b078cd904bf294c8aabdc45ae8cb\",	\"level\":\"diamond\",	\"merchandiseAmount\":0,	\"money\":-10D,	\"tradeName\":\"consumption\",	\"tradeWay\":\"computer\",	\"user\":\"18620318069\"}";
		trade = (PiaolTrade) hgtech.jf.entity.JsonUtil.toO(js);
		Collection<CalResult> r = cal.cal(trade);
		System.out.println(r);
		
	}	
	/**
	 * @类功能说明：测试用户在生日 内下单消费规则
	 * @类修改者：
	 * @修改日期：2014年10月21日上午10:27:33
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：xinglj
	 * @创建时间：2014年10月21日上午10:27:33
	 *
	 */
	@Test
	public void test生日() throws Exception {
		Class class1=PiaolConsumptionLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("jfRatio", "0.2");
		rule.parameters.put("birthdayRatio", "2");
		rule.parameters.put("privilegeRatio", "2");
		rule.parameters.put("wirelessRatio", "2");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		trade.tradeName=PiaolTrade.TRADE_CONSUMPTION;
		
		trade.setUser("pel");
		trade.birthday="20000111";
		trade.money=150.0;
		trade.level=trade.LEVEL_SILVER;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.setDate("20141014");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员在非生日购物 积分：");
		assertTrue(result.out_jf==30);
		System.out.println(result.out_jf);
		trade.setDate("20150111");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员在生日购物 积分：");
		System.out.println(result.out_jf);
		System.out.println();
		assertTrue(result.out_jf==60);
	}
	
	/**
	 * @类功能说明：测试钻石用户特权下单消费规则
	 * @类修改者：
	 * @修改日期：2014年10月21日上午10:27:33
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：pengel
	 * @创建时间：2014年10月21日上午10:27:33
	 *
	 */
	@Test
	public void test特权双倍() throws Exception {
		Class class1=PiaolConsumptionLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("jfRatio", "0.2");
		rule.parameters.put("birthdayRatio", "2");
		rule.parameters.put("privilegeRatio", "2");
		rule.parameters.put("wirelessRatio", "2");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		trade.birthday="20141020";
		trade.money=150.0;
		trade.level=trade.LEVEL_DIAMOND;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		
		trade.level=trade.LEVEL_NORMAL;
		trade.setDate("20141018");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("非钻石会员不能享受购物双倍票豆，票豆：");
		assertTrue(result.out_jf==30);
		System.out.println(result.out_jf);
		
		trade.level=trade.LEVEL_DIAMOND;
		trade.setDate("20141018");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("钻石会员享受购物双倍票豆，票豆：");
		assertTrue(result.out_jf==60);
		System.out.println(result.out_jf);
		System.out.println();
	}
	
	/**
	 * @类功能说明：测试钻石用户在生日周内特权下单消费规则
	 * @类修改者：
	 * @修改日期：2014年10月21日上午10:27:33
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：pengel
	 * @创建时间：2014年10月21日上午10:27:33
	 *
	 */
	
	@Test
	public void test生日周特权双倍() throws Exception {
		Class class1=PiaolConsumptionLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("jfRatio", "0.2");
		rule.parameters.put("birthdayRatio", "2");
		rule.parameters.put("privilegeRatio", "2");
		rule.parameters.put("wirelessRatio", "2");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		trade.birthday="20141020";
		trade.money=150.0;
		trade.tradeWay=trade.TRADE_WAY_COMPUTER;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.level=trade.LEVEL_SILVER;
		trade.setDate("20141018");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("非钻石会员在非生日周购物，票豆：");
		assertTrue(result.out_jf==30);
		System.out.println(result.out_jf);
		trade.level=trade.LEVEL_DIAMOND;
		trade.setDate("20141018");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("钻石会员在非生日周购物，票豆：");
		assertTrue(result.out_jf==60);
		System.out.println(result.out_jf);
		trade.level=trade.LEVEL_SILVER;
		trade.setDate("20141022");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("非钻石会员在生日周购物，票豆：");
		assertTrue(result.out_jf==60);
		System.out.println(result.out_jf);
		trade.level=trade.LEVEL_DIAMOND;
		trade.setDate("20141022");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("钻石会员在生日周购物，票豆：");
		assertTrue(result.out_jf==120);
		System.out.println(result.out_jf);
		System.out.println();
	}
	
	/**
	 * @类功能说明：测试用户手机下单消费规则
	 * @类修改者：
	 * @修改日期：2014年10月21日上午10:27:33
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：pengel
	 * @创建时间：2014年10月21日上午10:27:33
	 *
	 */
	@Test
	public void test手机下单() throws Exception {
		Class class1=PiaolConsumptionLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("jfRatio", "0.2");
		rule.parameters.put("birthdayRatio", "2");
		rule.parameters.put("privilegeRatio", "2");
		rule.parameters.put("wirelessRatio", "2");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		trade.birthday="20141020";
		trade.money=150.0;
		trade.level=trade.LEVEL_SILVER;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.tradeWay=trade.TRADE_WAY_COMPUTER;
		trade.setDate("20141018");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员不使用手机下单所得积分：");
		assertTrue(result.out_jf==30);
		System.out.println(result.out_jf);
		trade.tradeWay=trade.TRADE_WAY_WIRELESS;
		trade.setDate("20141018");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员使用手机下单所得积分：");
		assertTrue(result.out_jf==60);
		System.out.println(result.out_jf);
		System.out.println();
	}
	
	
	/**
	 * @类功能说明：测试用户在生日周内手机下单消费规则
	 * @类修改者：
	 * @修改日期：2014年10月21日上午10:27:33
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：pengel
	 * @创建时间：2014年10月21日上午10:27:33
	 *
	 */
	@Test
	public void test会员生日周手机下单() throws Exception {
		Class class1=PiaolConsumptionLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("jfRatio", "0.2");
		rule.parameters.put("birthdayRatio", "2");
		rule.parameters.put("privilegeRatio", "2");
		rule.parameters.put("wirelessRatio", "2");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		trade.birthday="20141020";
		trade.money=150.0;
		trade.level=trade.LEVEL_SILVER;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.tradeWay=trade.TRADE_WAY_COMPUTER;
		trade.setDate("20141015");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员在非生日周非手机下单所得票豆：");
		assertTrue(result.out_jf==30);
		System.out.println(result.out_jf);
		trade.tradeWay=trade.TRADE_WAY_WIRELESS;
		trade.setDate("20141018");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员在非生日周手机下单所得票豆：");
		assertTrue(result.out_jf==60);
		System.out.println(result.out_jf);
		trade.tradeWay=trade.TRADE_WAY_WIRELESS;
		trade.setDate("20141022");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员生日周手机下单所得票豆：");
		assertTrue(result.out_jf==120);
		System.out.println(result.out_jf);
		System.out.println();
	}
	
	/**
	 * @类功能说明：测试钻石会员在生日周内手机下单消费规则
	 * @类修改者：
	 * @修改日期：2014年10月21日上午10:27:33
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：pengel
	 * @创建时间：2014年10月21日上午10:27:33
	 *
	 */
	@Test
	public void test会员特权生日周手机下单() throws Exception {
		Class class1=PiaolConsumptionLogic.class;
		CalModel cal=new CalModel();
		Rule rule=new Rule();
		rule.code="PiaolTranscationLogic";
		rule.logicClass=class1.getName();
		rule.parameters.put("jfRatio", "0.2");
		rule.parameters.put("birthdayRatio", "2");
		rule.parameters.put("privilegeRatio", "2");
		rule.parameters.put("wirelessRatio", "2");
		rule.session=new RuledSession_Mem(rule.code);
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTrade trade=new PiaolTrade();
		
		trade.setUser("pel");
		trade.birthday="20141020";
		trade.money=150.0;
		cal.project.ruleSet.add(rule);
		
		CalResult  result=new CalResult();
		Collection<CalResult> results=null;
		
		Iterator it=null;
		trade.level=trade.LEVEL_DIAMOND;
		trade.tradeWay=trade.TRADE_WAY_WIRELESS;
		trade.setDate("20141012");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员特权在非生日周使用手机下单多的票豆：");
		assertTrue(result.out_jf==120);
		System.out.println(result.out_jf);
		trade.level=trade.LEVEL_DIAMOND;
		trade.tradeWay=trade.TRADE_WAY_COMPUTER;
		trade.setDate("20141022");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员特权生日周不使用手机下单多的票豆：");
		assertTrue(result.out_jf==120);
		System.out.println(result.out_jf);
		trade.level=trade.LEVEL_SILVER;
		trade.tradeWay=trade.TRADE_WAY_WIRELESS;
		trade.setDate("20141022");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("非会员特权生日周使用手机下单多的票豆：");
		assertTrue(result.out_jf==120);
		System.out.println(result.out_jf);
		trade.level=trade.LEVEL_DIAMOND;
		trade.tradeWay=trade.TRADE_WAY_WIRELESS;
		trade.setDate("20141022");
		results=cal.cal(trade);
		it=results.iterator();
		result=(CalResult) it.next();
		System.out.print("会员特权生日周使用手机下单多的票豆：");
		assertTrue(result.out_jf==240);
		System.out.println(result.out_jf);
		System.out.println();
	}

}
