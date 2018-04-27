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
 * @类功能说明：手机充值抽奖规则<br>
 * 抽奖概率  -1：表示奖项结束， 表示 不设此奖项和随后奖项<br>
 * resultText ："谢谢参与"或prizeLevel+"等奖"。<br>
 * 抽奖时间不是当时时间，要在交易中转入
 * @类修改者：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * 
 */
public class HFPrizeLogic extends BaseSingleRule implements RuleLogic {

	//没有抽中奖项的标识
	public static String NO_PRIZE="*";
	//系统共支持多少奖项
	public int MAX_prizeLevel = 10;
	//实际规则设置了多少奖项。概率的基数0表示 不设此级别和后面的奖项
	private int real_prizeLevel;
	
	public static String key_sysuser = "sys_HFCJ";
	
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
		
		
		
		PiaolTrade trade = (PiaolTrade) flow;
		if (!trade.TRADE_HFCHOUJIAGN.trim().equals(trade.tradeName)) {
			return null;
		}
		
		CalResult result = new CalResult();
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.user;
		
		//抽奖过程...
		Float.parseFloat(rule.parameters.get("gv1"));//@REMARK@一等奖概率（-1：表示奖项结束,谢谢参与也需配置概率）
		rule.parameters.get("gv2");//@REMARK@二等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv3");//@REMARK@三等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv4");//@REMARK@四等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv5");//@REMARK@五等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv6");//@REMARK@六等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv7");//@REMARK@七等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv8");//@REMARK@八等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv9");//@REMARK@九等奖概率（-1：表示奖项结束）
		rule.parameters.get("gv10");//@REMARK@十等奖概率（-1：表示奖项结束）

		rule.parameters.get("jf1");//@REMARK@一等奖积分(谢谢参与请配置成0积分)
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
		rule.parameters.get("count1");//@REMARK@一等奖数量(谢谢参与奖品数可配制成0或-1)
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
		
		//下为session处理
		
		int para_countLimit = Integer.parseInt(rule.parameters.get("actionCount"));//@REMARK@用户中奖奖次数限制
		
		//用户已抽奖次数
		String key_U_COUNT = "count";
		//用户已中奖数
		String key_U_ENCOUNT="encount";
		//用户session
		DataRow session = rule.session.get(trade.user);

		// @SESSIONREMARK@count@用户已抽奖次数
		// @SESSIONREMARK@encount@用户已中奖数
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
		
		//已抽奖次数
		int session_count ;
		//已中奖次数
		int session_encount;
		if (session == null) {// 用户不存在
			session = new DataRow();
			session_count = 0;
			session_encount = 0;
		}else{
			session_count = ((Integer)session.get(key_U_COUNT)).intValue();
			session_encount = ((Integer)session.get(key_U_ENCOUNT)).intValue();
		}
		//抽奖次数检查
		if(session_count >= trade.enPrizeCount){
			result.out_resultCode="N";
			result.out_resultText="抽奖次数不足!";
			return result;
		}
		session_count = session_count +1;
		//中奖次数检查
		if(session_encount >= para_countLimit){
			prizeLevel = NO_PRIZE;
			System.out.println("*");
		}else{
			//抽奖
			Integer chou=randomPrize2.chouj();
			if(chou!=null ){
				Integer sesPrizeCount = (Integer) sessionSysNotNull.get(chou+"");
				//奖品够
				final boolean prizeAvailable =sesPrizeCount==null || sesPrizeCount.intValue()<prizeCountLimit.get(chou+"");
				if(prizeAvailable){
					prizeLevel = chou+"";
				}else{
					System.out.println("#"+chou);
				}
			}
		}
		
		//该奖项数量控制
		Integer session_prizeCount;
		{
			session_prizeCount = (Integer) sessionSysNotNull.get(prizeLevel);
			if(session_prizeCount==null)
				session_prizeCount=1;
			else
				session_prizeCount = session_prizeCount +1;
			if(jfMap.get(prizeLevel)==0){
				session_prizeCount=-1;
				prizeLevel = NO_PRIZE;
			}
			
		}
		if(!NO_PRIZE.equals(prizeLevel)&&jfMap.get(prizeLevel)!=0){
			session_encount += 1;
		}
		//记录用户抽奖次数，日期等
		session.put(key_U_COUNT, session_count);
		session.put(key_U_ENCOUNT, session_encount);
		rule.session.put(trade.user, session);
		
		
		sessionSysNotNull.put(prizeLevel, session_prizeCount);
		rule.session.put(key_sysuser, sessionSysNotNull);
		
		result.out_jf = jfMap.get(prizeLevel);
		result.out_resultText = prizeLevel.equals(NO_PRIZE)?"谢谢参与":prizeLevel+"等奖";
		result.out_resultCode="Y";
		return result;
	}

}
