package hgtech.jf.piaol.rulelogic;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

public class PiaolInviteFriendsLogic extends BaseSingleRule implements
		RuleLogic {

	@Override
	public CalResult compute(Serializable flow) throws Exception {
		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		if (!trade.TRADE_INVITEFRIENDS.trim().equals(trade.tradeName.trim())) {
			return null;
		}
		Integer integral = 0;
		Integer integralIncrement = 300;// 邀请一个好友积分的增量

		DataRow session = rule.session.get(trade.user);
		if (session == null) {
			session = new DataRow();
		}
		integralIncrement = Integer.parseInt(rule.parameters
				.get("integralIncrement"));//@REMARK@用户邀请好友得到的积分数量
		integral += integralIncrement;

		result.out_jf = integral;
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.user;
		result.out_resultText = "邀请了一位好友";
		return result;
	}

}
