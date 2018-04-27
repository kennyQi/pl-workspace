package hgtech.jfcal.rulelogic.demo;

import java.io.Serializable;

import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.Rule;
import hgtech.jfcal.model.TradeFlow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * 
 * @类功能说明：
 * 一个示例的按交易金额积分的规则逻辑。<br>
 * 1000元积1分，某些商户不积分，生日当天双倍积分。
 * @类修改者：
 * @修改日期：2014-9-3下午1:43:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-3下午1:43:46
 * <br>
 *
 */
public class AmtRuleLogic  extends BaseSingleRule implements RuleLogic{
	 
	public CalResult compute(Serializable o){
		TradeFlow flow=(TradeFlow) o;
		//交易数据
		String 交易pos=(String) flow.get("pos");
		Number 交易金额=(Number) flow.get("amt");
		String 客户生日 =(String) flow.get("birthday");
		String 交易日期=(String) flow.get("date");
		
//		规则参数
		String 不积分商户=rule.parameters.get("不积分商户");//@REMARK@每个商户号后跟一英文逗号，如pos01,pos02,
		int 多少积1分 = Integer.parseInt( rule.parameters.get("多少积1分"));// @REMARK@demo
		
		CalResult result = new CalResult();
		if(不积分商户!=null && 不积分商户.contains(交易pos+",")){
			System.out.println(String.format("商户 %s 在不积分商户列表 %s 中，不积分", 交易pos,不积分商户)  );
		}else{
			int 倍数=1;
			if(客户生日.equals(交易日期))
				倍数=2;
			int 积分= (交易金额.intValue()/多少积1分) * 倍数;
			
			result.out_jf=积分;
		}
		return result;
	}
}
