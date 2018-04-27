package hgtech.jf.hg.rulelogic;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：注册模版类(汇购)
 * @类修改者：
 * @修改日期：2015年08月31日下午3:41:17
 * @修改说明：
 * @公司名称：
 * @作者：zhaoqf
 * @创建时间：2015年08月31日下午3:41:17
 * 
 */
public class HjfRegisterActivityLogic extends BaseSingleRule implements RuleLogic {

	@SuppressWarnings("static-access")
	@Override
	public CalResult compute(Serializable flow) throws Exception {
		String str = "";
		String event = null;

		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		String tradeName = trade.tradeName;
		if (!(trade.TRADE_REGISTER.trim().equals(tradeName.trim()))) {
			return null;
		}
		Map<String, Integer> intergralMap =  new HashMap<String, Integer>();
		intergralMap.put("pc", Integer.parseInt(rule.parameters.get("pcIncrement")));//@REMARK@pc端注册用户所得积分
		intergralMap.put("app", Integer.parseInt(rule.parameters.get("appIncrement")));//@REMARK@app端注册用户所得积分
		intergralMap.put("h5", Integer.parseInt(rule.parameters.get("h5Increment")));//@REMARK@h5端注册用户所得积分
		intergralMap.put("wp", Integer.parseInt(rule.parameters.get("wpIncrement")));//@REMARK@公众平台注册用户所得积分
		Integer integral = 0;// 应该增加的积分
		Integer integralIncrement = 0;
		Integer registerIncrement =intergralMap.get(trade.tradeWay);
		DataRow session = rule.session.get(trade.user);
		if (session == null) {// 每一个用户在每一个规则下的session是不能共享数据的！
			// 用户信息不存在
			session = new DataRow();
			if (trade.TRADE_REGISTER.trim().equals(tradeName.trim())) {
				integralIncrement = registerIncrement;
				event = "register";
				str += "用户完成注册";
			}
		} else {
			if (trade.TRADE_REGISTER.trim().equals(tradeName.trim())) {// 当前用户操作事件是用户注册
				Boolean b = (Boolean) session.get("register");
				if (b == null) {
					integralIncrement = registerIncrement;
					event = "register";
					str += "用户注册成功";
				} else {
					integralIncrement = 0;
					str += "用户已经完成注册";
				}
			}
		}

		integral += integralIncrement;

		if ("register".equals(event)) {
			// @SESSIONREMARK@register@用户注册
			session.put("register", true);
			// @SESSIONREMARK@registerWay@注册来源
			session.put("registerWay", trade.tradeWay);
		}

		rule.session.put(trade.getUser(), session);
		result.out_jf = integral;
		/*try {
			if(!StringUtils.isBlank(invalidDate)){
				Long day = System.currentTimeMillis()+Integer.parseInt(invalidDate)*1000*3600*24;
				result.out_invalidDay=new Date(day);
			}
		} catch (Exception e) {
			System.out.println("无效日期");
			e.printStackTrace();
		}*/
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.getUser();
		result.out_resultText = str;
		System.out.println(str);
		return result;
	}

}
