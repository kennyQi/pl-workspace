package hgtech.jf.hg.rulelogic;

import java.io.Serializable;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * @类功能说明：积分卡绑定模版类
 * @类修改者：
 * @修改日期：2014年10月16日下午3:41:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月16日下午3:41:17
 * 
 */
public class HjfJfCardLogic extends BaseSingleRule implements RuleLogic {

	@SuppressWarnings("static-access")
	@Override
	public CalResult compute(Serializable flow) throws Exception {
		String str = "";
		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		String tradeName = trade.tradeName;
		if (!(trade.TRADE_JFCARDBINDING.trim().equals(tradeName.trim())) || trade.TRADE_SIGN.trim().equals(tradeName.trim())) {
			return null;
		}
		Integer integral = 0;// 应该增加的积分
		Integer jfCardBindingIncrement = Integer.parseInt(rule.parameters.get("jfCardBindingIncrement"));// @REMARK@用户积分卡绑定所得积分
		integral += jfCardBindingIncrement;

		str += "积分卡绑定:" + integral;
		result.out_jf = integral;
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.getUser();
		result.out_resultText = str;
		return result;
	}

}
