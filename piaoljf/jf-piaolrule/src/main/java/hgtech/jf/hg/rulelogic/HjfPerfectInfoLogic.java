package hgtech.jf.hg.rulelogic;

import java.io.Serializable;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * @类功能说明：完善信息模版类
 * @类修改者：
 * @修改日期：2014年10月16日下午3:41:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月16日下午3:41:17
 * 
 */
public class HjfPerfectInfoLogic extends BaseSingleRule implements RuleLogic {

	@SuppressWarnings("static-access")
	@Override
	public CalResult compute(Serializable flow) throws Exception {

		String str = "";
		String event = null;

		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		String tradeName = trade.tradeName;
		if (!(trade.TRADE_PERFECTINFO.trim().equals(tradeName.trim()))) {
			return null;
		}
		Integer integral = 0;// 应该增加的积分
		Integer integralIncrement = 0;
		Integer perfectInfoIncrement = Integer.parseInt(rule.parameters.get("perfectInfoIncrement"));// @REMARK@用户完善资料所得积分
		DataRow session = rule.session.get(trade.user);
		if (session == null) {// 每一个用户在每一个规则下的session是不能共享数据的！
			// 用户信息不存在
			session = new DataRow();
			if (trade.TRADE_PERFECTINFO.trim().equals(tradeName.trim())) {
				integralIncrement = perfectInfoIncrement;
				event = "perfectInfo";
				// session.put("perfectInfo", true);
				str += "完善资料";
			}
		} else {
			if (trade.TRADE_PERFECTINFO.trim().equals(tradeName.trim())) {// 当前用户操作事件是完善资料
				Boolean b = (Boolean) session.get("perfectInfo");
				if (b == null) {
					integralIncrement = perfectInfoIncrement;
					event = "perfectInfo";
					// session.put("perfectInfo", true);
					str += "完善资料";
				} else {
					integralIncrement = 0;
					str += "修改个人资料";
				}
			}
		}

		integral += integralIncrement;

		if ("perfectInfo".equals(event)) {
			// @SESSIONREMARK@perfectInfo@完善资料
			session.put("perfectInfo", true);
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
