package hgtech.jfcal.rulelogic.demo;

import java.io.Serializable;

import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.TradeFlow;
import hgtech.jfcal.model.Rule;
import hgtech.jfcal.model.TradeFlow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * 
 * @类功能说明：
 * 一个示例的按合计交易次数积分的规则逻辑，用到了中间数据（这里是次数）。<br>
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
public class 按次数积分RuleLogic extends BaseSingleRule implements RuleLogic {
 
	@Override
	public CalResult compute(Serializable flow) throws Exception{
		String flowPos=(String) ((TradeFlow) flow).get("pos");
		Number flowAmt=(Number) ((TradeFlow) flow).get("amt");
		String flowBirthday =(String) ((TradeFlow) flow).get("birthday");
		String flowTransdate=(String) ((TradeFlow) flow).get("date");
		String flowCardNo = (String) ((TradeFlow) flow).get("cardNo");
		
		String 不积分商户=rule.parameters.get("不积分商户");
		int 多少次积1分 = Integer.parseInt( rule.parameters.get("多少次积1分"));
		
		CalResult result = new CalResult();
		if(不积分商户.contains(flowPos)){
			System.out.println(String.format("商户 %s 在不积分商户列表 %s 中，不积分", flowPos,不积分商户)  );
		}else{
			int 已交易次数= ((Integer)rule.session.get(flowCardNo).get("已交易次数")).intValue()+1;
			if(已交易次数==多少次积1分)
			{	result.out_jf= 1;
				rule.session.get(flowCardNo).put("已交易次数", 0);
			}else{
				rule.session.get(flowCardNo).put("已交易次数", 已交易次数);
			}
				
		}
		return result;
	}
}
