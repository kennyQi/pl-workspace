package hgtech.jfcal.rulelogic.piaol;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.model.Rule;
import hgtech.jfcal.model.TradeFlow;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

/**
 * 
 * @类功能说明：
 * 一个示例的按交易金额积分的规则逻辑。<br>
 * 1000元积1分，某些商户不积分，生日当天双倍积分。
 * @类修改者：
 * @修改日期：2014-9-3下午1:43:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-3下午1:43:46
 * <br>
 *
 */
public class PiaolAmtRuleLogicDemo  extends BaseSingleRule implements RuleLogic{
	public CalResult compute(Serializable o) throws Exception{
		PiaolTradeDemo flow=(PiaolTradeDemo) o;
		//交易数据
		String 交易pos=flow.pos;
		Number 交易金额= flow.amt;
		String 客户生日 = flow.birthday;
		String 交易日期=flow.tradeDate;
		SimpleDateFormat form=new SimpleDateFormat("yyyyMMdd");
		Date d交易日期 = form.parse(flow.tradeDate);
		
		
//		规则参数
		String 不积分商户=rule.parameters.get("不积分商户");//@不积分商户@每个商户号后跟一英文逗号，如pos01,pos02,
		int 多少积1分 = Integer.parseInt( rule.parameters.get("多少积1分"));//
		
//		计算过程
		CalResult result = new CalResult();
		if(不积分商户!=null && 不积分商户.contains(交易pos+",")){
			System.out.println(String.format("商户 %s 在不积分商户列表 %s 中，不积分", 交易pos,不积分商户)  );
		}else{
			int 倍数=1;
			
			//生日倍数
			if(客户生日.equals(交易日期))
				倍数=2;
			
//			连续第七日双倍
			DataRow session = rule.session.get(flow.user);
			int continuedays;
			Date lastdate;
			if(session==null){
				session = new DataRow();
				//首次交易
				continuedays=1;
			}else{
				lastdate=(Date) session.get("lastdate");
				continuedays=  (Integer) session.get("continue");
				if(d交易日期.getTime()- (lastdate).getTime()==24*60*60*1000)
				{
					continuedays +=1;
				}else
				{
					continuedays=1;
				}
			}
			lastdate=d交易日期;
			System.out.println("连续日子："+continuedays);
			if(continuedays==7)
			{	
				System.out.println("双倍");
				倍数*=2;
			}
			
			//保存session
			session.put("lastdate", lastdate);
			session.put("continue", continuedays);
			rule.session.put(flow.user,session);
			
			int 积分= (交易金额.intValue()/多少积1分) * 倍数;
			
			//写计算结果
			result.out_jf= 积分;
			result.in_userCode=flow.user;
			result.in_rule=rule;
			result.in_tradeFlow=o;		
		}
		return result;
	}
}
