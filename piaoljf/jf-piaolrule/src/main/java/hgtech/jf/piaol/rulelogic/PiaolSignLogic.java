package hgtech.jf.piaol.rulelogic;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.LogFactory;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * 
 * @类功能说明：会员签到规则类
 * @类修改者：
 * @修改日期：2014年10月15日下午4:58:19
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月15日下午4:58:19
 * 
 */
public class PiaolSignLogic extends BaseSingleRule implements RuleLogic {

	/**
	 * @方法功能说明：用户签到规则实现
	 * @修改者名字：pengel
	 * @修改时间：2014年10月21日上午10:27:33
	 * @修改内容：
	 * @参数：@param args
	 * @return:CalResult
	 */
	@Override
	public CalResult compute(Serializable flow) throws Exception {

		String str = "";

		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		if (!trade.TRADE_SIGN.trim().equals(trade.tradeName)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date signDate = sdf.parse(trade.getDate());// 用户签到日期
		Integer integral = 0;// 会员签到之后得到积分
		int countDays = 0;// 用户连续签到的天数统计
		Integer integralIncrement = 100;// 每次签到积分的增量

		String level = trade.level;
		LogFactory.getLog(PiaolSignLogic.class).info("经典复古卷发");
		// 判断会员等级
		if (trade.LEVEL_SILVER.trim().equals(level.trim())) {
			LogFactory.getLog(PiaolSignLogic.class).info("白银会员");
			integralIncrement = Integer.parseInt(rule.parameters.get("siljf"));//@REMARK@白银用户签到积分
			str += "白银会员，";
		} else if (trade.LEVEL_GOLD.trim().equals(level.trim())) {
			integralIncrement = Integer.parseInt(rule.parameters.get("goljf"));//@REMARK@金牌用户签到积分
			LogFactory.getLog(PiaolSignLogic.class).info("金牌会员");
			str += "金牌会员，";
		} else if (trade.LEVLE_PT.trim().equals(level.trim())) {
			integralIncrement = Integer.parseInt(rule.parameters.get("ptjf"));//@REMARK@白金用户签到积分
			LogFactory.getLog(PiaolSignLogic.class).info("白金会员");
			str += "白金会员，";
		} else if (trade.LEVEL_DIAMOND.trim().equals(level.trim())) {
			integralIncrement = Integer.parseInt(rule.parameters.get("diajf"));//@REMARK@钻石用户签到积分
			LogFactory.getLog(PiaolSignLogic.class).info("钻石会员");
			str += "钻石会员，";
		} else {
			integralIncrement = Integer.parseInt(rule.parameters.get("norjf"));// @REMARK@普通用户签到积分
			LogFactory.getLog(PiaolSignLogic.class).info("普通会员");
			str += "普通会员，";
		}

		Double incrementRatio = 1.0;// 签到积分增量倍数

		DataRow session = rule.session.get(trade.user);
		if (session == null) {// 用户不存在
			session = new DataRow();
			countDays = 1;
			str += "签到日期"+signDate+"，本月连续签到天数：" + countDays;
		} else {// 用户存在
			Date lastDate = (Date) session.get("lastDate");// 用户上一次签到的日期
			if (lastDate.equals(signDate)) {// 用户是否是在同一天内的签到
				integralIncrement = 0;
				countDays = (Integer) session.get("countDays");
				str += signDate+"已经签到，本月连续签到天数：" + countDays;
			} else {// 用户不是在同意天内的签到
				Calendar ca = Calendar.getInstance();
				ca.setTime(lastDate);
				int m1 = ca.get(ca.MONTH);
				ca.clear();
				ca.setTime(signDate);
				int m2 = ca.get(ca.MONTH);
				if (m1 == m2) {// 签到日期和上次签到日期是在同一个月内
					ca.clear();
					ca.setTime(lastDate);
					ca.add(ca.DATE, 1);
					lastDate = ca.getTime();
					if (lastDate.equals(signDate)) {// 连续签到
						countDays = (Integer) session.get("countDays");
						countDays++;
						str += "签到日期"+signDate+"，本月连续签到天数：" + countDays;
					} else {
						countDays = 1;
						str += "签到日期"+signDate+"，本月连续签到天数：" + countDays;
					}
				} else {
					countDays = 1;
					str += "签到日期"+signDate+"，本月连续签到天数：" + countDays;
				}

			}

		}
		if (countDays < 8) {// 用户连续签到没有超过七天
			integral += Integer.parseInt(new java.text.DecimalFormat("0")
					.format(integralIncrement * incrementRatio));
		} else if (countDays >= 8 && countDays < 16) {// 用户连续签到超过七天但小于16天
			incrementRatio = Double
					.parseDouble(rule.parameters.get("jfRatio1"));//@REMARK@签到连续七天之后积分倍数
			integral += Integer.parseInt(new java.text.DecimalFormat("0")
					.format(integralIncrement * incrementRatio));
		} else if (countDays >= 16) {// 用户签到超过16天
			incrementRatio = Double
					.parseDouble(rule.parameters.get("jfRatio2"));//@REMARK@签到连续15天之后积分倍数
			integral += Integer.parseInt(new java.text.DecimalFormat("0")
					.format(integralIncrement * incrementRatio));
		}
		//@SESSIONREMARK@countDays@签到连续天数
		session.put("countDays", countDays);
		//@SESSIONREMARK@lastDate@上次签到日期
		session.put("lastDate", signDate);
		rule.session.put(trade.user, session);
		/*
		 * System.out.println("当前连续的第"+countDays+"天");
		 * System.out.println("目前的积分为："+integral);
		 */
		result.out_jf = integral;
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.user;
		result.out_resultText = str;
		return result;
	}

}
