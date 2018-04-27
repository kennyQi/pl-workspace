package hgtech.jf.hg.rulelogic;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.model.Rule;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;
import hgtech.util.RandomPrize2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @类功能说明：南航抽奖规则<br>
 * 抽奖概率  -1：表示奖项结束， 表示 不设此奖项和随后奖项<br>
 * resultText ："谢谢参与"或prizeLevel+"等奖"。<br>
 * 抽奖时间不是当时时间，要在交易中转入
 * @类修改者：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * 
 */
public class CZPrizeLogic extends BaseSingleRule implements RuleLogic {

	//没有抽中奖项的标识
	public static String NO_PRIZE="-1";
	//系统共支持多少奖项
	public int MAX_prizeLevel = 10;
	//实际规则设置了多少奖项。概率的基数0表示 不设此级别和后面的奖项
	private int real_prizeLevel;
	
	public static String key_sysuser = "__sys_";
	
	RandomPrize2 randomPrize2;
	@Override
	public void setRule(Rule r) {
		super.setRule(r);

		//init
		for(int i=1;i<=MAX_prizeLevel;i++){
			final float gv =   Float.parseFloat(rule.parameters.get("gv"+i));
			// -1：表示奖项结束
			if(gv==-1f){
				real_prizeLevel=i-1;
				break;
			}
		}		
		String gv[]=new String[real_prizeLevel];
		for(int i=1;i<=real_prizeLevel;i++){
			 gv[i-1] =  (rule.parameters.get("gv"+i));
		}
		randomPrize2 = new RandomPrize2(gv);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public CalResult compute(Serializable flow) throws Exception {
		
		//本次计算结果,这里是奖项级别名，1，2，3，4，5，6
		String prizeLevel = NO_PRIZE;
		int integral=0;
		
		
		
		PiaolTrade trade = (PiaolTrade) flow;
		if (!trade.TRADE_CHOUJIAGN.trim().equals(trade.tradeName)) {
			return null;
		}
		
		CalResult result = new CalResult();
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.user;
		
		//抽奖过程...
		Float.parseFloat(rule.parameters.get("gv1"));//@REMARK@一等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv2");//@REMARK@二等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv3");//@REMARK@三等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv4");//@REMARK@四等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv5");//@REMARK@五等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv6");//@REMARK@六等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv7");//@REMARK@七等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv8");//@REMARK@八等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv9");//@REMARK@九等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv10");//@REMARK@十等奖概率（-1：表示奖项结束）

		rule.parameters.get("jf1");//@REMARK@一等奖积分
		rule.parameters.get("jf2");//@REMARK@二等奖积分
		rule.parameters.get("jf3");//@REMARK@三等奖积分
		rule.parameters.get("jf4");//@REMARK@四等奖积分
		rule.parameters.get("jf5");//@REMARK@五等奖积分
		rule.parameters.get("jf6");//@REMARK@六等奖积分
		rule.parameters.get("jf7");//@REMARK@七等奖积分
		rule.parameters.get("jf8");//@REMARK@八等奖积分
		rule.parameters.get("jf9");//@REMARK@九等奖积分
		rule.parameters.get("jf10");//@REMARK@十等奖积分
		
		//奖项对应的积分
		Map<String,Integer> jfMap = new HashMap<>();
		jfMap.put( (NO_PRIZE), 0);
		for(int i=1;i<=real_prizeLevel;i++){
			jfMap.put(i+"", Integer.parseInt(rule.parameters.get("jf"+i)));
		}

		//参数，奖品数量控制参数
		rule.parameters.get("count1");//@REMARK@一等奖数量
		rule.parameters.get("count2");//@REMARK@二等奖数量
		rule.parameters.get("count3");//@REMARK@三等奖数量
		rule.parameters.get("count4");//@REMARK@四等奖数量
		rule.parameters.get("count5");//@REMARK@五等奖数量
		rule.parameters.get("count6");//@REMARK@六等奖数量
		rule.parameters.get("count7");//@REMARK@七等奖数量
		rule.parameters.get("count8");//@REMARK@八等奖数量
		rule.parameters.get("count9");//@REMARK@九等奖数量
		rule.parameters.get("count10");//@REMARK@十等奖数量
		//奖项个数限制
		Map<String,Integer> prizeCountLimit = new HashMap<>();
		for(int i=1;i<=real_prizeLevel;i++)
			prizeCountLimit.put(i+"", Integer.parseInt(rule.parameters.get("count"+i)));
		//活动范围内session
		DataRow sessionSysNotNull = rule.session.get(key_sysuser);
		if(sessionSysNotNull==null)
			sessionSysNotNull = new DataRow();
		
		//抽
		Integer chou=randomPrize2.chouj();
		if(chou!=null ){
			Integer sesPrizeCount = (Integer) sessionSysNotNull.get(chou+"");
			//奖品够
			final boolean prizeAvailable =sesPrizeCount==null || sesPrizeCount.intValue()<prizeCountLimit.get(chou+"");
			if(prizeAvailable)
				prizeLevel = chou+"";
		}

		//下为session处理
		
		int para_countLimit = Integer.parseInt(rule.parameters.get("actionCount"));//@REMARK@用户抽奖次数限制
		int para_dayCountLimit = Integer.parseInt(rule.parameters.get("dayActionCount"));//@REMARK@用户一天内抽奖次数限制
		
		
		String key_S_COUNT = "count";
		String key_S_DAYCOUNT = "daycount";
		String key_S_LASTDATE = "lastDate";
		
		//用户session
		DataRow session = rule.session.get(trade.user);

		// @SESSIONREMARK@count@抽奖次数
		// @SESSIONREMARK@lastDate@上次抽奖日期
		// @SESSIONREMARK@1@系统一等奖个数
		// @SESSIONREMARK@2@系统二等奖个数		
		// @SESSIONREMARK@3@系统三等奖个数		
		// @SESSIONREMARK@4@系统四等奖个数		
		// @SESSIONREMARK@5@系统五等奖个数		
		// @SESSIONREMARK@6@系统六等奖个数
		// @SESSIONREMARK@7@系统七等奖个数
		// @SESSIONREMARK@8@系统八等奖个数
		// @SESSIONREMARK@9@系统九等奖个数
		// @SESSIONREMARK@10@系统十等奖个数
		
		//抽奖次数
		int session_count ;
		//当日抽奖次数
		int session_daycount ;
		//抽过奖的日期
		String session_lastDate  ;
		String today = trade.date; //new SimpleDateFormat("yyyyMMdd").format(new Date());
		if (session == null) {// 用户不存在
			session = new DataRow();
			session_count = 1;
			session_daycount =1;
			
		} else {// 用户存在
			session_count = ((Integer)session.get(key_S_COUNT)).intValue() +1;
			session_daycount = ((Integer)session.get(key_S_DAYCOUNT)).intValue();
			if(session.get(key_S_LASTDATE).toString().equals(today))
				session_daycount +=1;
			else
				session_daycount =1;
		}
		//抽奖总次数检查
		if(session_count> para_countLimit){
			result.out_resultCode="N";
			result.out_resultText="您已抽奖。等下次活动哦!";
			return result;
		}
		if(session_daycount> para_dayCountLimit ){
			result.out_resultCode="N";
			result.out_resultText="您本日已抽奖。改日再来哦！";
			return result;
		}
		
		
		//该奖项数量控制
		Integer session_prizeCount;
		{
			session_prizeCount = (Integer) sessionSysNotNull.get(prizeLevel);
			if(session_prizeCount==null)
				session_prizeCount=1;
			else
				session_prizeCount = session_prizeCount +1;
		}
		
		//记录用户抽奖次数，日期等
		session.put(key_S_COUNT, session_count);
		session.put(key_S_LASTDATE, today);
		session.put(key_S_DAYCOUNT, session_daycount);
		rule.session.put(trade.user, session);
		
		
		sessionSysNotNull.put(prizeLevel, session_prizeCount);
		rule.session.put(key_sysuser, sessionSysNotNull);
		
		result.out_jf = jfMap.get(prizeLevel);
		result.out_resultText = prizeLevel.equals(NO_PRIZE)?"谢谢参与":prizeLevel+"等奖";
		result.out_resultCode="Y";
		return result;
	}

}
