package hgtech.jfcal.model;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.ws.ServiceMode;

import hgtech.jfcal.rulelogic.RuleLogic;
import hgtech.jfcal.rulelogic.ConflictLogic;
import hgtech.jfcal.rulelogic.SingleRule;
import hgtech.jfcal.rulelogic.demo.AmtRuleLogic;

/**
 * 
 * @类功能说明：规则计算的功能类
 * @类修改者：
 * @修改日期：2014年10月21日下午1:39:01
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月21日下午1:39:01
 *
 */
public class CalModel {
	public Project project=new Project();
	private Map<String, SingleRule> cachedLogic=new HashMap<>();
//	public CalListener listener;
	/**
	 * 
	 * @方法功能说明：应用活动的每条规则，计算这条交易流水的积分
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-22下午1:46:26
	 * @修改内容：
	 * @参数：@param tradeFlow 待积分流水
	 * @参数：@return 
	 * @参数：@throws Exception
	 * @return:Collection<CalResult> 在这个活动内算出来的积分流水
	 * @throws
	 */
	public Collection<CalResult> cal(Serializable tradeFlow)throws Exception{
		Map<String,CalResult> results=new LinkedHashMap<String,CalResult>();
		for(Rule r:project.ruleSet){
			//logic.setRule(r);
			
			CalResult result ;
			try {
				SingleRule logic = getCachedRuleLogic(r);
				if(logic instanceof RuleLogic)
					result=((RuleLogic) logic).compute(tradeFlow);
				else
					result =((ConflictLogic)logic).compute(results);
				if(result!=null ){
					if(!"N".equalsIgnoreCase(result.out_resultCode)){
						result.out_resultCode="Y";
					}
					result.calTime=new Date();
					result.in_rule=r;
					result.in_tradeFlow=tradeFlow;
					//result.out_resultText=""+r.name;
				}

			} catch (Throwable e) {
				System.out.println(r.getLogicClass()+"##############################");
				result=new CalResult();
				result.calTime=new Date();
				result.in_rule=r;
				result.in_tradeFlow=tradeFlow;
				result.out_resultCode="N";
				StringWriter out = new StringWriter();
				e.printStackTrace(new PrintWriter(out));
				String string = out.toString();
				result.out_resultText=string.substring(0,string.length()>512?512:string.length());
				e.printStackTrace();
				
			}
			if(result!=null)
				results.put(r.code, result);
		}
		return results.values();
		
	}

	/**
	 * @方法功能说明：缓存的方式获取一个实例
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月29日上午8:55:24
	 * @修改内容：
	 * @参数：@param r
	 * @参数：@return
	 * @参数：@throws InstantiationException
	 * @参数：@throws IllegalAccessException
	 * @参数：@throws ClassNotFoundException
	 * @return:SingleRule
	 * @throws
	 */
	public SingleRule getCachedRuleLogic(Rule r) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		
		SingleRule logic=cachedLogic.get(r.code);
		if(logic==null)
		{	
			//获取class。如果规则有，直接使用，没有则从currentLoader 中加载
			if(r.logic==null)
			{	logic= (SingleRule) Class.forName(r.logicClass).newInstance();
				System.out.println("加载类完毕："+r.logicClass);
			}else
			{	
				logic=(SingleRule) r.logic.newInstance();
			}
			logic.setRule(r);
			
			cachedLogic.put(r.code, logic);
		}
		return logic;
	}
	
	public static void main(String[] args) throws Exception {
		CalModel cal = new CalModel();
		Rule r1=new Rule();
		r1.code="1";
		r1.logicClass=AmtRuleLogic.class.getName();
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
}
