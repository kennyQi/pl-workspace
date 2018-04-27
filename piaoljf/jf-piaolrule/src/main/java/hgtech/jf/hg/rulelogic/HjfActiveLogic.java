package hgtech.jf.hg.rulelogic;

import java.io.Serializable;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * @类功能说明：激活模版类(汇购)
 * @类修改者：
 * @修改日期：2015年08月31日下午3:41:17
 * @修改说明：
 * @公司名称：
 * @作者：zhaoqf
 * @创建时间：2015年08月31日下午3:41:17
 * 
 */
public class HjfActiveLogic extends BaseSingleRule implements RuleLogic {

	@SuppressWarnings("static-access")
	@Override
	public CalResult compute(Serializable flow) throws Exception {
		String str = "";
		String event = null;

		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		String tradeName = trade.tradeName;
		if (!(trade.TRADE_ACTIVATE.trim().equals(tradeName.trim()))) {
			return null;
		}
		Integer integral = 0;// 应该增加的积分
		Integer integralIncrement = 0;
		Integer activeIncrement = Integer.parseInt(rule.parameters.get("activeIncrement"));// @REMARK@用户激活所得积分
		DataRow session = rule.session.get(trade.user);
		if (session == null) {// 每一个用户在每一个规则下的session是不能共享数据的！
			// 用户信息不存在
			session = new DataRow();
			if (trade.TRADE_ACTIVATE.trim().equals(tradeName.trim())) {
				integralIncrement = activeIncrement;
				event = "active";
				str += "用户完成激活";
			}
		} else {
			if (trade.TRADE_ACTIVATE.trim().equals(tradeName.trim())) {// 当前用户操作事件是用户激活
				Boolean b = (Boolean) session.get("active");
				if (b == null) {
					integralIncrement = activeIncrement;
					event = "active";
					str += "用户激活成功";
				} else {
					integralIncrement = 0;
					str += "用户已经完成激活";
				}
			}
		}

		integral += integralIncrement;

		if ("active".equals(event)) {
			// @SESSIONREMARK@active@用户激活
			session.put("active", true);
		}

		rule.session.put(trade.getUser(), session);
		result.out_jf = integral;
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.getUser();
		result.out_resultText = str;
		return result;
	}

}
