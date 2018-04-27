/**
 * @文件名称：PiaolConsumptionLogic.java
 * @类路径：hgtech.jf.piaol.rulelogic
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年10月21日上午10:27:33
 */
package hgtech.jf.piaol.rulelogic;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.JfChangeApply;
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
public class PiaolConsumptionLogic  extends BaseSingleRule implements RuleLogic{

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
		
		String str="";
		
		JfChangeApply r;
		CalResult  result=new CalResult();
		PiaolTrade trade=(PiaolTrade)flow;
		if(!"consumption".equals(trade.tradeName)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		
		Date dealDate=sdf.parse(trade.date);
		Integer integral=0;//会员下单消费后积分增长量
		Integer integralIncrement=100;//每次购物应该得到的积分的增量
		Double jfRatio=0.2;//每次购物会员所得积分占成交金额的比重
		Double wirelessRatio=1.0;//手机下单享受电脑下单所得积分的倍数
		Double birthdayRatio=1.0;//生日周消费所得积分的倍数
		Double privilegeRatio=1.0;//会员特权（只有钻石会员拥有该特权）
		
		//判断购物日期是不是和生日在同意个周内
		
		String tradeName=trade.getTradeName();
		Double money=trade.getMoney();
		if(trade.tradeWay==trade.TRADE_WAY_WIRELESS){//判读下单方式是不是手机下单
			wirelessRatio=Double.parseDouble(rule.parameters.get("wirelessRatio"));//@REMARK@会员使用手机下单，所得积分的倍数
			str+="手机下单，";
		}
		if(trade.level==trade.LEVEL_DIAMOND){//判断用户级别是不是钻石会员，如果是钻石会员则享受下单特权
			privilegeRatio=Double.parseDouble(rule.parameters.get("privilegeRatio"));//@REMARK@会员特权（只有钻石会员拥有该特权）
			str+="钻石会员享受下单特权，";
		}
		
		jfRatio=Double.parseDouble(rule.parameters.get("jfRatio"));//@REMARK@会员下单消费，所得积分（票豆）占消费金额的比重
		
		if(!"".equals(trade.getBirthday())&&trade.getBirthday()!=null){
			Date birthday=sdf.parse(trade.getBirthday());			
			if(sameWeek(birthday, dealDate)){//判断两个日期是不是在同一个周内
				birthdayRatio=Double.parseDouble(rule.parameters.get("birthdayRatio"));//@REMARK@会员在生日周下单消费，所得积分的倍数
				str+="生日周购物，";
			}
			if(trade.level==trade.LEVEL_DIAMOND && sameWeek(birthday, dealDate)){
				integralIncrement=Integer.parseInt(new java.text.DecimalFormat("0").format(money*jfRatio*wirelessRatio*birthdayRatio + money*jfRatio*wirelessRatio*privilegeRatio));
			}else{
				integralIncrement=Integer.parseInt(new java.text.DecimalFormat("0").format(money*jfRatio*wirelessRatio*birthdayRatio*privilegeRatio));
			}
		}else{
			integralIncrement=Integer.parseInt(new java.text.DecimalFormat("0").format(money*jfRatio*wirelessRatio*birthdayRatio*privilegeRatio));
		}
		  
		integral+=integralIncrement;
		
		result.out_jf=integral;
		result.in_rule=rule;
		result.in_tradeFlow=flow;
		result.in_userCode=trade.user;
		result.out_resultText=str;
		return result;
	}
	/**
	 * @方法功能说明：判断连个日期是不是在同一个周内
	 * @修改者名字：pengel
	 * @修改时间：2014年10月21日上午10:27:33
	 * @修改内容：
	 * @参数：@param args
	 * @return:CalResult
	 */
	
	public Boolean sameWeek(Date date1,Date date2){
		Boolean result=false;
		Calendar ca=Calendar.getInstance();
		ca.setTime(date1);
		Integer i=ca.get(ca.DAY_OF_WEEK);
		Integer week1=ca.get(ca.WEEK_OF_YEAR);
		ca.setTime(date2);
		Integer week2=ca.get(ca.WEEK_OF_YEAR);
        Integer j=ca.get(ca.DAY_OF_WEEK);
		if(week1==week2 && j==i){
			result=true;
		}else if(week1==week2 && j!=1 && i!=1){
			result=true;
		}else if(j==1 || i==1){
			if(j==1 && week2-week1==1 && i!=1){
				result=true;
			}else if(i==1 && week1-week2==1 && j!=1){
				result=true;
			}
		}
		
		return result;
	}

}
