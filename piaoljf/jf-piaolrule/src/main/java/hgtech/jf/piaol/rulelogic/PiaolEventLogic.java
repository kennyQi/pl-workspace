package hgtech.jf.piaol.rulelogic;

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
public class PiaolEventLogic extends BaseSingleRule implements RuleLogic {

	/**
     * @FieldsFIRST_TRADE:TODO
     */
    private static final String FIRST_TRADE = "firstTrade";

	@Override
	public CalResult compute(Serializable flow) throws Exception {

		String str = "";
		String event = null;

		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		String tradeName = trade.tradeName;
		if (!(trade.TRADE_REGISTER.trim().equals(tradeName.trim())
				|| trade.TRADE_PHONEBINDING.trim().equals(tradeName.trim())
				|| trade.TRADE_PERFECTINFO.trim().equals(tradeName.trim())
				|| trade.TRADE_CONSUMPTION.trim().equals(tradeName.trim())
				|| trade.TRADE_MAILBINDING.trim().equals(tradeName.trim()))) {
			return null;
		}
		Integer integral = 0;// 应该增加的积分
		Integer integralIncrement = 0;
		Integer phBindingIncrement = Integer.parseInt(rule.parameters.get("phBindingIncrement"));//@REMARK@用户手机绑定所得积分
		Integer perfectInfoIncrement = Integer.parseInt(rule.parameters.get("perfectInfoIncrement"));//@REMARK@用户完善资料所得积分
		Integer mailBindingIncrement = Integer.parseInt(rule.parameters.get("mailBindingIncrement"));//@REMARK@用户邮箱绑定所得积分
		Integer registerIncrement = Integer.parseInt(rule.parameters.get("registerIncrement"));//@REMARK@用户注册所得积分
		Integer firstTradeIncrement = Integer.parseInt(rule.parameters.get("firstTradeIncrement"));//@REMARK@用户首次交易所得积分
		DataRow session = rule.session.get(trade.user);
		if (session == null) {// 每一个用户在每一个规则下的session是不能共享数据的！
			// 用户信息不存在
			session = new DataRow();
			if (trade.TRADE_PHONEBINDING.trim().equals(tradeName.trim())) {
				integralIncrement = phBindingIncrement;
				event = "phoneBinding";
				// session.put("phoneBinding", true);
				str += "手机绑定";
			}
			if (trade.TRADE_PERFECTINFO.trim().equals(tradeName.trim())) {
				integralIncrement = perfectInfoIncrement;
				event = "perfectInfo";
				// session.put("perfectInfo", true);
				str += "完善资料";
			}
			if (trade.TRADE_MAILBINDING.trim().equals(tradeName.trim())) {
				integralIncrement = mailBindingIncrement;
				event = "mailBinding";
				// session.put("mailBinding", true);
				str += "邮箱绑定";
			}
			if (trade.TRADE_REGISTER.trim().equals(tradeName.trim())) {
				integralIncrement = registerIncrement;
				event = "register";
				// session.put("register", true);
				str += "用户完成注册";
			}
			if (trade.TRADE_CONSUMPTION.trim().equals(tradeName.trim())) {
				integralIncrement = firstTradeIncrement;
				event = trade.TRADE_CONSUMPTION;
				// session.put("register", true);
				str += "首次交易";
			}
		} else {
			if (trade.TRADE_PHONEBINDING.trim().equals(tradeName.trim())) {// 当前用户操作事件是手机绑定
				Boolean b = (Boolean) session.get("phoneBinding");
				if (b == null) {
					integralIncrement = phBindingIncrement;
					event = "phoneBinding";
					// session.put("phoneBinding", true);
					str += "手机绑定";
				} else {
					integralIncrement = 0;
					str += "更换手机";
				}
			}
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
			if (trade.TRADE_REGISTER.trim().equals(tradeName.trim())) {// 当前用户操作事件是完善资料
				Boolean b = (Boolean) session.get("register");
				if (b == null) {
					integralIncrement = registerIncrement;
					event = "register";
					// session.put("register", true);
					str += "用户注册成功";
				} else {
					integralIncrement = 0;
					str += "用户已经完成注册";
				}
			}
			if (trade.TRADE_MAILBINDING.trim().equals(tradeName.trim())) {// 当前用户操作事件是邮箱绑定
				Boolean b = (Boolean) session.get("mailBinding");
				if (b == null) {
					integralIncrement = mailBindingIncrement;
					event = "mailBinding";
					// session.put("mailBinding", true);
					str += "邮箱绑定";
				} else {
					integralIncrement = 0;
					str += "更换邮箱";
				}
			}
			if (trade.TRADE_CONSUMPTION.trim().equals(tradeName.trim())) {// 当前用户操作事件是邮箱绑定
				Boolean b = (Boolean) session.get(FIRST_TRADE);
				if (b == null) {
					integralIncrement = firstTradeIncrement;
					event = FIRST_TRADE;
					// session.put("mailBinding", true);
					str += "首次交易";
				} else {
					integralIncrement = 0;
					str += "非首次交易";
				}
			}
		}

		integral += integralIncrement;

		if ("mailBinding".equals(event)) {
			//@SESSIONREMARK@mailBinding@邮箱绑定
			session.put("mailBinding", true);
		} else if ("register".equals(event)) {
			//@SESSIONREMARK@register@用户注册
			session.put("register", true);
		} else if ("perfectInfo".equals(event)) {
			//@SESSIONREMARK@perfectInfo@完善资料
			session.put("perfectInfo", true);
		} else if ("phoneBinding".equals(event)) {
			//@SESSIONREMARK@phoneBinding@手机绑定
			session.put("phoneBinding", true);
		} else if (FIRST_TRADE.equals(event)) {
			//@SESSIONREMARK@firstTrade@首次交易
			session.put(FIRST_TRADE, true);
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
