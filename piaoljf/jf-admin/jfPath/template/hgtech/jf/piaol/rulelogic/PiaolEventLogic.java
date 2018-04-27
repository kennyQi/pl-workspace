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
public class PiaolEventLogic  extends BaseSingleRule implements RuleLogic{

	@Override
	public CalResult compute(Serializable flow) throws Exception {
		
		String str="";
		
		CalResult  result=new CalResult();
		PiaolTrade trade=(PiaolTrade)flow;
		if(!("phoneBinding".equals(trade.tradeName) || "perfectInfo".equals(trade.tradeName) ||"mailBinding".equals(trade.tradeName))){
			return null;
		}
		Integer integral=0;//应该增加的积分
		Integer integralIncrement=500;//每次操作积分的增量
		String tradeName=trade.tradeName;
		integralIncrement=Integer.parseInt(rule.parameters.get("integralIncrement"));//@REMARK@用户操作所得积分
		DataRow session=rule.session.get(trade.user);
		if(session==null){//每一个用户在每一个规则下的session是不能共享数据的！
			//用户信息不存在
			session=new DataRow();
			if(tradeName==trade.TRADE_PHONEBINDING){
				 session.put("phoneBinding", true);
				 str+="手机绑定";
			}
			if(tradeName==trade.TRADE_PERFECTINFO){
				session.put("perfectInfo", true);
				str+="完善资料";
			}
		    if(tradeName==trade.TRADE_MAILBINDING){
		    	session.put("mailBinding", true);
		    	str+="邮箱绑定";
		    }
		}else{
			if(tradeName==trade.TRADE_PHONEBINDING){//当前用户操作事件是手机绑定
				 Boolean b=(Boolean)session.get("phoneBinding");
				 if(b==null){
					 session.put("phoneBinding", true);
					 str+="手机绑定";
				 }else{
					 integralIncrement=0;
					 str+="已经进行过手机绑定，更换绑定的手机";
				 }
			}
			if(tradeName==trade.TRADE_PERFECTINFO){//当前用户操作事件是完善资料
				Boolean b=(Boolean)session.get("perfectInfo");
				if(b==null){
					 session.put("perfectInfo", true);
					 str+="完善资料";
				 }else{
					 integralIncrement=0;
					 str+="已经完善过资料，修改个人资料";
				 }
			}
		    if(tradeName==trade.TRADE_MAILBINDING){//当前用户操作事件是邮箱绑定
		    	Boolean b=(Boolean)session.get("mailBinding");
		    	if(b==null){
					 session.put("mailBinding", true);
					 str+="邮箱绑定";
				 }else{
					 integralIncrement=0;
					 str+="已经绑定过邮箱，修改绑定的邮箱";
				 }
		    }
		}
		
	    
	    integral+=integralIncrement;
		
		rule.session.put(trade.getUser(), session);
		result.out_jf=integral;
		result.in_rule=rule;
		result.in_tradeFlow=flow;
		result.in_userCode=trade.getUser();
		result.out_resultText=str;
		return result;
	}

}
