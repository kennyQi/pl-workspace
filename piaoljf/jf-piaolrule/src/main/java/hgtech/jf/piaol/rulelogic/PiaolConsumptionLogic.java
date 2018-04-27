/**
 * @文件名称：PiaolConsumptionLogic.java
 * @类路径：hgtech.jf.piaol.rulelogic
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年10月21日上午10:27:33
 */
package hgtech.jf.piaol.rulelogic;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * @类功能说明：下单消费规则
 * @类修改者：
 * @修改日期：2014年10月21日上午10:27:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月21日上午10:27:33
 * 
 */
public class PiaolConsumptionLogic extends BaseSingleRule implements RuleLogic {

	/**
	 * @方法功能说明：下单消费积分增长规则
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
		if (!trade.TRADE_CONSUMPTION.trim().equals(trade.tradeName.trim())) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Date dealDate = sdf.parse(trade.date);
		Integer integral = 0;// 会员下单消费后积分增长量
		Integer integralIncrement = 100;// 每次购物应该得到的积分的增量
		Double jfRatio = 0.2;// 每次购物会员所得积分占成交金额的比重
		Double wirelessRatio = 1.0;// 手机下单享受电脑下单所得积分的倍数
		Double birthdayRatio = 1.0;// 生日周消费所得积分的倍数
		Double privilegeRatio = 1.0;// 会员特权（只有钻石会员拥有该特权）

		// 判断购物日期是不是和生日在同意个周内

		String tradeName = trade.getTradeName();
		Double money = trade.getMoney();
		if (trade.TRADE_WAY_WIRELESS.trim().equals(trade.tradeWay.trim())) {// 判读下单方式是不是手机下单
			wirelessRatio = Double.parseDouble(rule.parameters.get("wirelessRatio"));//@REMARK@会员使用手机下单，所得积分的倍数
			str += "手机下单，";
		}
		if (trade.LEVEL_DIAMOND.trim().equals(trade.level.trim())) {// 判断用户级别是不是钻石会员，如果是钻石会员则享受下单特权
			privilegeRatio = Double.parseDouble(rule.parameters.get("privilegeRatio"));//@REMARK@钻石会员下单消费(钻石会员享受会员特权)，所得积分倍数
			str += "钻石会员享受下单特权，";
		}

		jfRatio = Double.parseDouble(rule.parameters.get("jfRatio"));//@REMARK@会员下单消费，所得积分（票豆）占消费金额的比重

		if (!"".equals(trade.getBirthday()) && trade.getBirthday() != null) {
			String  birthday =  (trade.getBirthday());
			if (inBirthdayWeek(dealDate, birthday))  {// 判断两个日期是不是在同一个周内
				birthdayRatio = Double.parseDouble(rule.parameters.get("birthdayRatio"));//@REMARK@会员在生日周下单消费，所得积分的倍数
				str += "生日周购物，";
			}
			if (trade.LEVEL_DIAMOND.trim().equals(trade.level.trim())
					&& inBirthdayWeek(dealDate, birthday)) {
				integralIncrement = Integer
						.parseInt(new java.text.DecimalFormat("0").format(money
								* jfRatio * wirelessRatio * birthdayRatio
								+ money * jfRatio * wirelessRatio
								* privilegeRatio));
			} else {
				integralIncrement = Integer
						.parseInt(new java.text.DecimalFormat("0").format(money
								* jfRatio * wirelessRatio * birthdayRatio
								* privilegeRatio));
			}
		} else {
			integralIncrement = Integer.parseInt(new java.text.DecimalFormat(
					"0").format(money * jfRatio * wirelessRatio * birthdayRatio
					* privilegeRatio));
		}
		str += "下单消费：" + trade.money;
		integral += integralIncrement;

		result.out_jf = integral;
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.user;
		result.out_resultText = str;
		return result;
	}
 
	
	/**
	 * 
	 * @方法功能说明：是否在生日周内，一周指的周一到周日
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月9日下午3:10:06
	 * @version：
	 * @修改内容：
	 * @参数：@param dealday 交易日
	 * @参数：@param birthday 生日（年月日）yyyyMMdd
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public static boolean inBirthdayWeek(Date dealday, String birthday){
		Calendar birth = Calendar.getInstance();
		//设置周一为一周的开始
		birth.setFirstDayOfWeek(Calendar.MONDAY);
		int month=Integer.parseInt(birthday.substring(4, 6));
		int day=Integer.parseInt(birthday.substring(6));
		birth.set(Calendar.MONTH, month-1);
		birth.set(Calendar.DAY_OF_MONTH, day);
		
		Calendar deal=Calendar.getInstance();
		//设置周一为一周的开始
		deal.setFirstDayOfWeek(Calendar.MONDAY);
		deal.setTime(dealday);
		
		return birth.get(Calendar.WEEK_OF_YEAR)==deal.get(Calendar.WEEK_OF_YEAR);
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		//true
		System.out.println( inBirthdayWeek(sf.parse("20150111"),  ("20000111")));
		System.out.println( inBirthdayWeek(sf.parse("20150105"),  ("20000111")));
		//false
		System.out.println( inBirthdayWeek(sf.parse("20150112"),  ("20000111")));

		//true
		System.out.println( inBirthdayWeek(sf.parse("20150112"),  ("20000112")));
		System.out.println( inBirthdayWeek(sf.parse("20150118"),  ("20000112")));

		//false
		System.out.println( inBirthdayWeek(sf.parse("20150111"),  ("20000112")));
		System.out.println( inBirthdayWeek(sf.parse("20150119"),  ("20000112")));
		
	}
}
