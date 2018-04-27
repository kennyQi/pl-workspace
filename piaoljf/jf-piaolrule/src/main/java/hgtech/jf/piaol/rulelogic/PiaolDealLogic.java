/**
 * @文件名称：TranscationLogic.java
 * @类路径：hgtech.jf.piaol.rulelogic
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年10月16日下午3:41:17
 */
package hgtech.jf.piaol.rulelogic;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * @类功能说明：用户在一个自然月内购物天数超过三天得到积分的规则
 * @类修改者：
 * @修改日期：2014年10月16日下午3:41:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月16日下午3:41:17
 * 
 */
public class PiaolDealLogic extends BaseSingleRule implements RuleLogic {

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @方法功能说明：用户在一个自然月内购物天数奖励积分规则
	 * @修改者名字：pengel
	 * @修改时间：2014年10月21日上午10:27:33
	 * @修改内容：
	 * @参数：@param args
	 * @return:CalResult
	 */
	public CalResult compute(Serializable flow) throws Exception {

		String str = "";

		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		if (!trade.TRADE_DEAL.trim().equals(trade.tradeName.trim())) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dealDate = sdf.parse(trade.getDate());
		Integer integral = 0;// 应该增加的积分
		Calendar ca = Calendar.getInstance();
		Date recentDate = null;// 上一次购物的日期
		int totleDays = Integer.parseInt(rule.parameters.get("totleDays"));//@REMARK@用户需要在一个自然月内连续消费的天数
		int integralIncrement = Integer.parseInt(rule.parameters.get("IntegralIncrement"));//@REMARK@满足购物天数后得到的积分值
		int countDays1 = 0;// 记录上个月内该用户的消费天数
		int countDays2 = 0;// 记录本月内该用户的消费天数
		List<Date> shoppingDate = null;// 存储最近两个月内的所有购物日期

		DataRow session = rule.session.get(trade.getUser());
		if (session == null) {// 无当前用户
			session = new DataRow();
			shoppingDate = new ArrayList<Date>();
			shoppingDate.add(dealDate);
			recentDate = dealDate;
			countDays2 = 1;
			ca.setTime(dealDate);
			str += ca.get(ca.YEAR) + "年" + (ca.get(ca.MONTH) + 1) + "月,消费天数："
					+ countDays2;
		} else {// 用户信息存在
			recentDate = (Date) session.get("recentDate");
			shoppingDate = (List) session.get("shoppingDate");
			if (shoppingDate.contains(dealDate)) {// 判读当前日期是否已经存在
				countDays2 = (Integer) session.get("countDays2");
				countDays1 = (Integer) session.get("countDays1");
				str += "在" + sdf.format(dealDate) + ",已经买过东西，不计入购物天数。";
			} else {
				ca.setTime(dealDate);
				int y2 = ca.get(ca.YEAR);
				int m2 = ca.get(ca.MONTH);
				ca.setTime(recentDate);
				int y1 = ca.get(ca.YEAR);
				int m1 = ca.get(ca.MONTH);
				if (y1 > y2) {
					if (m1 == 0 && m2 == 11) {
						shoppingDate.add(dealDate);
						countDays1 = (Integer) session.get("countDays1");
						countDays2 = (Integer) session.get("countDays2");
						countDays1++;
						str += y2 + "年" + (m2 + 1) + "月,消费天数：" + countDays1;
						if (countDays1 == totleDays)
							integral += integralIncrement;
					}
				}
				if (y1 < y2) {
					if (y2 - y1 == 1 && m1 == 11 && m2 == 0) {
						List<Date> delList = new ArrayList<Date>();
						for (Date d : shoppingDate) {
							ca.setTime(d);
							int m = ca.get(ca.MONTH);
							if (m1 != m)
								delList.add(d);
						}
						for (Date d : delList) {
							shoppingDate.remove(d);
						}
						shoppingDate.add(dealDate);
						recentDate = dealDate;
						countDays2 = (Integer) session.get("countDays2");
						countDays1 = countDays2;
						countDays2 = 1;
						str += y2 + "年" + (m2 + 1) + "月,消费天数：" + countDays2;
					} else {
						shoppingDate.clear();
						shoppingDate.add(dealDate);
						recentDate = dealDate;
						countDays1 = 0;
						countDays2 = 1;
						str += y2 + "年" + (m2 + 1) + "月,消费天数：" + countDays2;
					}
				}
				if (y1 == y2) {
					if (m1 == m2) {
						shoppingDate.add(dealDate);
						countDays2 = (Integer) session.get("countDays2");
						countDays1 = (Integer) session.get("countDays1");
						countDays2++;
						str += y2 + "年" + (m2 + 1) + "月,消费天数：" + countDays2;
						if (countDays2 == totleDays)
							integral += integralIncrement;
					} else if (m1 > m2) {// 默认订单在一个月之内处理完成
						shoppingDate.add(dealDate);
						countDays1 = (Integer) session.get("countDays1");
						countDays2 = (Integer) session.get("countDays2");
						countDays1++;
						str += y2 + "年" + (m2 + 1) + "月,消费天数：" + countDays1;
						if (countDays1 == totleDays)
							integral += integralIncrement;
					} else if (m2 - m1 == 1) {
						List<Date> delList = new ArrayList<Date>();
						for (Date d : shoppingDate) {
							ca.setTime(d);
							int m = ca.get(ca.MONTH);
							if (m1 != m)
								delList.add(d);
						}
						for (Date d : delList) {
							shoppingDate.remove(d);
						}
						shoppingDate.add(dealDate);
						recentDate = dealDate;
						countDays2 = (Integer) session.get("countDays2");
						countDays1 = countDays2;
						countDays2 = 1;
						str += y2 + "年" + (m2 + 1) + "月,消费天数：" + countDays2;
					} else {
						shoppingDate.clear();
						shoppingDate.add(dealDate);
						recentDate = dealDate;
						countDays1 = 0;
						countDays2 = 1;
					}
				}
			}
		}
		//@SESSIONREMARK@recentDate@当月第一次购物时间
		session.put("recentDate", recentDate);
		//@SESSIONREMARK@shoppingDate@购物日期列表
		session.put("shoppingDate", shoppingDate);
		//@SESSIONREMARK@countDays1@上次购物之前的一个月购物天数统计
		session.put("countDays1", countDays1);
		//@SESSIONREMARK@countDays2上次购物当月购物天数统计
		session.put("countDays2", countDays2);
		rule.session.put(trade.getUser(), session);
		result.out_jf = integral;
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.getUser();
		result.out_resultText = str;
		return result;
	}

}
