package hgtech.jf.hg.rulelogic;

import java.io.Serializable;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * @类功能说明：事件模版类
 * @类修改者：
 * @修改日期：2014年10月16日下午3:41:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月16日下午3:41:17
 * 
 */
public class HjfLevelLogic extends BaseSingleRule implements RuleLogic {

	@SuppressWarnings("static-access")
	@Override
	public CalResult compute(Serializable flow) throws Exception {

		String str = "";
		String event = null;

		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		String tradeName = trade.tradeName;
		if (!(trade.LEVEL_NORMAL.trim().equals(tradeName.trim()) || trade.LEVEL_SILVER.trim().equals(tradeName.trim()) || trade.LEVEL_GOLD.trim().equals(tradeName.trim())
				|| trade.LEVLE_PT.trim().equals(tradeName.trim()) || trade.LEVEL_DIAMOND.equals(tradeName.trim()))) {
			return null;
		}
		Integer integral = 0;// 应该增加的积分
		Integer integralIncrement = 0;
		Integer normalIncrement = Integer.parseInt(rule.parameters.get("normalIncrement"));// @REMARK@普通会员所得积分
		Integer silverIncrement = Integer.parseInt(rule.parameters.get("silverIncrement"));// @REMARK@白银会员所得积分
		Integer goldIncrement = Integer.parseInt(rule.parameters.get("goldIncrement"));// @REMARK@金牌会员所得积分
		Integer ptIncrement = Integer.parseInt(rule.parameters.get("ptIncrement"));// @REMARK@白金会员所得积分
		Integer diamondIncrement = Integer.parseInt(rule.parameters.get("diamondIncrement"));// @REMARK@钻石会员所得积分
		DataRow session = rule.session.get(trade.user);
		if (session == null) {// 每一个用户在每一个规则下的session是不能共享数据的！
			// 用户信息不存在
			session = new DataRow();
			if (trade.LEVEL_NORMAL.trim().equals(tradeName.trim())) {
				integralIncrement = normalIncrement;
				event = "normal";
				str += "普通会员";
			}
			if (trade.LEVEL_SILVER.trim().equals(tradeName.trim())) {
				integralIncrement = silverIncrement;
				event = "silver";
				str += "白银会员";
			}
			if (trade.LEVEL_GOLD.trim().equals(tradeName.trim())) {
				integralIncrement = goldIncrement;
				event = "gold";
				str += "金牌会员";
			}
			if (trade.LEVLE_PT.trim().equals(tradeName.trim())) {
				integralIncrement = ptIncrement;
				event = "pt";
				str += "白金会员";
			}
			if (trade.LEVEL_DIAMOND.trim().equals(tradeName.trim())) {
				integralIncrement = diamondIncrement;
				event = "diamond";
				str += "钻石会员";
			}
		} else {
			if (trade.LEVEL_NORMAL.trim().equals(tradeName.trim())) {
				Boolean b = (Boolean) session.get("normal");
				if (b == null) {
					integralIncrement = normalIncrement;
					event = "normal";
					str += "普通会员";
				} else {
					integralIncrement = 0;
					str += "用户已成为普通会员";
				}
			}
			if (trade.LEVEL_SILVER.trim().equals(tradeName.trim())) {
				Boolean b = (Boolean) session.get("silver");
				if (b == null) {
					integralIncrement = silverIncrement;
					event = "silver";
					str += "白银会员";
				} else {
					integralIncrement = 0;
					str += "用户已成为白银会员";
				}
			}
			if (trade.LEVEL_GOLD.trim().equals(tradeName.trim())) {
				Boolean b = (Boolean) session.get("gold");
				if (b == null) {
					integralIncrement = goldIncrement;
					event = "gold";
					str += "金牌会员";
				} else {
					integralIncrement = 0;
					str += "用户已成为金牌会员";
				}
			}
			if (trade.LEVLE_PT.trim().equals(tradeName.trim())) {
				Boolean b = (Boolean) session.get("pt");
				if (b == null) {
					integralIncrement = ptIncrement;
					event = "pt";
					str += "白金会员";
				} else {
					integralIncrement = 0;
					str += "用户已成为白金会员";
				}
			}
			if (trade.LEVEL_DIAMOND.trim().equals(tradeName.trim())) {
				Boolean b = (Boolean) session.get("diamond");
				if (b == null) {
					integralIncrement = diamondIncrement;
					event = "diamond";
					str += "钻石会员";
				} else {
					integralIncrement = 0;
					str += "用户已成为钻石会员";
				}
			}
		}

		integral += integralIncrement;

		if ("normal".equals(event)) {
			// @SESSIONREMARK@normal@用户成为普通会员所得积分
			session.put("normal", true);
		} else if ("silver".equals(event)) {
			// @SESSIONREMARK@silver@用户成为白银会员所得积分
			session.put("silver", true);
		} else if ("gold".equals(event)) {
			// @SESSIONREMARK@gold@用户成为金牌会员所得积分
			session.put("gold", true);
		} else if ("pt".equals(event)) {
			// @SESSIONREMARK@pt@用户成为白金会员所得积分
			session.put("pt", true);
		} else if ("diamond".equals(event)) {
			// @SESSIONREMARK@diamond@用户成为钻石会员所得积分
			session.put("diamond", true);
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
