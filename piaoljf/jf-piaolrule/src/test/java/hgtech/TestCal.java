/**
 * @文件名称：TestCal.java
 * @类路径：hgtech
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-15下午4:05:56
 */
package hgtech;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import hgtech.jf.ClassUtil;
import hgtech.jf.FileClassLoader;
import hgtech.jfcal.dao.imp.RuledSession_Mem;
import hgtech.jfcal.model.CalModel;
import hgtech.jfcal.model.Rule;
import hgtech.jfcal.model.TradeFlow;
import hgtech.jfcal.rulelogic.FindParameters;
import hgtech.jfcal.rulelogic.demo.AmtRuleLogic;
import hgtech.jfcal.rulelogic.piaol.PiaolAmtRuleLogicDemo;
import hgtech.jfcal.rulelogic.piaol.PiaolTradeDemo;

import org.junit.Test;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014-9-15下午4:05:56
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-15下午4:05:56
 *
 */
public class TestCal {
	
	//演示从规则类,自动抽取规则参数
	@Test
	public void testFindParameters() throws IOException {
		System.out.println();
		Class<AmtRuleLogic> class1 = AmtRuleLogic.class;
		String file ="../jf-cal/calSrc/"+class1.getName().replace('.', '/')+".java";
		InputStream stream = new FileInputStream(new File(file));
		System.out.println("从文件 "+file +"中抽取的参数 :");
		for(FindParameters.Parameters p: FindParameters.findParameters(stream))
			System.out.println("name:"+p.name+" remark:"+p.remark);
		
	}

//	计算框架演示
	@Test
	public void testCal() throws Exception{
		System.out.println();
		
		//这是一个演示的规则类。参考这个类编写业务规则
		Class<AmtRuleLogic> class1 = AmtRuleLogic.class;

		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();	
		r1.parameters.put("不积分商户", "FANG,CHE");
		r1.parameters.put("多少积1分","100");
		
		cal.project.ruleSet.add(r1);
		
		TradeFlow flow=new TradeFlow();
		flow.put("pos", "FANG");
		flow.put("amt", 150);
		flow.put("birthday","20000102");
		flow.put("date", "20000101");
		
		System.out.println(cal.cal(flow));
	}
	

	@Test
	public void testCal_动态装载逻辑类() throws Exception{
		System.out.println();
		
		//这是一个演示的规则类。参考这个类编写业务规则
		String dir ="..\\jf-cal\\bin";// "D:/javawork/testprj/bin/hgtech/jfcal/rulelogic/piaol";
		String className = "hgtech.jfcal.rulelogic.demo.AmtRuleLogic";

		Class<?> class1 = //AmtRuleLogic.class;
			ClassUtil.loadFileClass(dir, className);
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();	
		r1.logic=class1;
		
		r1.parameters.put("不积分商户", "FANG,CHE");
		r1.parameters.put("多少积1分","100");
		
		cal.project.ruleSet.add(r1);
		
		TradeFlow flow=new TradeFlow();
		flow.put("pos", "FANG");
		flow.put("amt", 150);
		flow.put("birthday","20000102");
		flow.put("date", "20000101");
		
		System.out.println(cal.cal(flow));
	}

	@Test
	public void test票量网规则() throws Exception{
		System.out.println();
		
		//这是一个演示的规则类。参考这个类编写业务规则
		Class  class1 = PiaolAmtRuleLogicDemo.class;

		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();	
		r1.parameters.put("不积分商户", "FANG,CHE");
		r1.parameters.put("多少积1分","100");
		
		cal.project.ruleSet.add(r1);
		
		PiaolTradeDemo flow=new PiaolTradeDemo();
		flow.pos= "FANG";
		flow.amt=150;
		flow.birthday="20000102";
		flow.tradeDate="20000101";
		
		System.out.println(cal.cal(flow));
	}
	
	@Test
	public void test票量网7日规则() throws Exception{
		System.out.println();
		
		//这是一个演示的规则类。参考这个类编写业务规则
		Class  class1 = PiaolAmtRuleLogicDemo.class;

		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=class1.getName();
		r1.parameters.put("不积分商户", "FANG,CHE");
		r1.parameters.put("多少积1分","100");
		r1.session=new RuledSession_Mem(r1.code);
		
		cal.project.ruleSet.add(r1);
		
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		PiaolTradeDemo flow=new PiaolTradeDemo();
//		flow.pos= "FANG";
		flow.amt=150;
		flow.birthday= "20000102";
		flow.user="TOM";
		
		flow.tradeDate="20000101";
		System.out.println(cal.cal(flow));
		flow.tradeDate="20000102";
		System.out.println(cal.cal(flow));		
		flow.tradeDate="20000103";
		System.out.println(cal.cal(flow));		
		flow.tradeDate="20000104";
		System.out.println(cal.cal(flow));		
		flow.tradeDate="20000105";
		System.out.println(cal.cal(flow));		
		flow.tradeDate="20000106";
		System.out.println(cal.cal(flow));		
//		flow.tradeDate="20000107";
//		System.out.println(cal.cal(flow));	
		
		flow.tradeDate="20000111";
		System.out.println(cal.cal(flow));
		flow.tradeDate="20000112";
		System.out.println(cal.cal(flow));		
		flow.tradeDate="20000113";
		System.out.println(cal.cal(flow));		
		flow.tradeDate="20000114";
		System.out.println(cal.cal(flow));		
		flow.tradeDate="20000115";
		System.out.println(cal.cal(flow));		
		flow.tradeDate="20000116";
		System.out.println(cal.cal(flow));		
		flow.tradeDate="20000117";
		System.out.println(cal.cal(flow));	
		
	}

	@Test
	public   void testFileClassLoader() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		String dir = "D:/javawork/testprj/bin/hgtech/jfcal/rulelogic/piaol";
		String name = "hgtech.jfcal.rulelogic.piaol.PiaolAmtRuleLogicDemo";
		FileClassLoader loader = new FileClassLoader(dir);
		Class<?> c=loader.loadClass(name);
		hgtech.jfcal.rulelogic.piaol.PiaolAmtRuleLogicDemo d=(PiaolAmtRuleLogicDemo) c.newInstance();
		System.out.println(d);
	}
}
