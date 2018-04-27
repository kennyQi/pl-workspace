package hgtech.jfcal.rulelogic.demo;

import java.util.Map;

import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.Rule;
import hgtech.jfcal.model.TradeFlow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.ConflictLogic;

/**
 * 
 * @类功能说明：
 * 一个示例的解决规则冲突的逻辑。取几个规则计算出来的大者作为最后积分。<br>
 * 交易6次积1分。
 * @类修改者：
 * @修改日期：2014-9-3下午1:43:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-3下午1:43:46
 * <br>
 *
 */
public class 取积分大者Logic  extends BaseSingleRule implements ConflictLogic {
	 

	/* (non-Javadoc)
	 * @see hgtech.jfcal.rulelogic.RuleSetLogic#compute(java.util.Map)
	 */
	@Override
	public CalResult compute(Map<String, CalResult> results) {
		
		String 冲突的规则号=rule.parameters.get("冲突的规则号");
		
		CalResult result = new CalResult();
		int max=0, total=0;
		for(String ruleid:冲突的规则号.split(",")){
			int jf =((Number) results.get(ruleid).out_jf).intValue();
			total +=jf;
			if(jf>max)
				max=jf;
		}
		
		//如分别积分了100，200，那么应该最后是积分200，所以这里得到-100的积分来调整。
		int 因冲突调整积分 = max-total;
		result.out_jf= 因冲突调整积分;
		return result;
	}	
}
