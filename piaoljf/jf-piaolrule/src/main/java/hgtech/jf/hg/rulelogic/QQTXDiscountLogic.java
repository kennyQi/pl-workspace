package hgtech.jf.hg.rulelogic;

import hgtech.jf.coupon.CouponInterface;
import hgtech.jf.coupon.QQTXCoupon;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.model.Rule;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * 
 * @类功能说明：权倾天下优惠券规则<br>
 * resultText ："折扣"或"失败原因"。<br>
 * @类修改者：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * 
 */
public class QQTXDiscountLogic extends BaseSingleRule implements RuleLogic {

	public static String key_sysuser = "__sys_";
	//优惠商品编号Map
	Map<String, String> productCodeMap = new HashMap<String, String>();
	//优惠转出编号
	String transferCode;
	//系统共支持多少商品编号
	public int MAX_ProudctCount = 10;
	//优惠券接口
	private CouponInterface coupon;
	@Override
	public void setRule(Rule r) {
		super.setRule(r);

		//init 优惠商品
		for(int i=1;i<=MAX_ProudctCount;i++){
			String proCode =   (String)rule.parameters.get("proCode"+i);
			// #：表示商品编号结束
			if("#".equals(proCode)){
				break;
			}else{
				//在商品编号map中增加商品编号
				productCodeMap.put(proCode, proCode);
			}
		}		
	}
	@SuppressWarnings("static-access")
	@Override
	public CalResult compute(Serializable flow) throws Exception {
		
		PiaolTrade trade = (PiaolTrade) flow;
		//如果不是优惠券交易则不做操作
		if (!trade.TRADE_QUERYDISCOUNTCODEVALID.trim().equals(trade.tradeName)&&
				!trade.TRADE_DODISCOUNTCODE.trim().equals(trade.tradeName)) {
			return null;
		}
		
		CalResult result = new CalResult();
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_userCode = trade.user;
		
		//活动范围内session
		DataRow sessionSysNotNull = rule.session.get(key_sysuser);
		if(sessionSysNotNull==null)
			sessionSysNotNull = new DataRow();
		//下为session处理
		
		int user_countLimit = Integer.parseInt(rule.parameters.get("userCountLimit"));//@REMARK@用户使用优惠券次数限制
		int quan_countLimit = Integer.parseInt(rule.parameters.get("quanCountLimit"));//@REMARK@总优惠券限制
		String proDiscount = rule.parameters.get("proDiscount");//@REMARK@商品折扣范围0-1
		String transferDiscount = rule.parameters.get("transferDiscount");//@REMARK@渠道转出折扣折扣范围0-1
		transferCode = rule.parameters.get("transferCode");//@REMARK@优惠转出渠道编码南航:cz__licheng
		rule.parameters.get("proCode1");//@REMARK@优惠产品编号1(#表示结束)
		rule.parameters.get("proCode2");//@REMARK@优惠产品编号2(#表示结束)
		rule.parameters.get("proCode3");//@REMARK@优惠产品编号3(#表示结束)
		rule.parameters.get("proCode4");//@REMARK@优惠产品编号4(#表示结束)
		rule.parameters.get("proCode5");//@REMARK@优惠产品编号5(#表示结束)
		rule.parameters.get("proCode6");//@REMARK@优惠产品编号6(#表示结束)
		rule.parameters.get("proCode7");//@REMARK@优惠产品编号7(#表示结束)
		rule.parameters.get("proCode8");//@REMARK@优惠产品编号8(#表示结束)
		rule.parameters.get("proCode9");//@REMARK@优惠产品编号9(#表示结束)
		rule.parameters.get("proCode10");//@REMARK@优惠产品编号10(#表示结束)
		//用户已使用优惠券次数
		String key_S_COUNT = "count";
		
		//用户session
		DataRow session = rule.session.get(trade.user);

		// @SESSIONREMARK@count@用户使用券次数
		// @SESSIONREMARK@nowCount@已使用优惠券总数
		
		//用户使用优惠券次数
		int session_count ;
		//抽过奖的日期
		if (session == null) {// 用户不存在
			session = new DataRow();
			session_count = 0;
		} else {// 用户存在
			session_count = ((Integer)session.get(key_S_COUNT)).intValue();
		}
		if(session_count >= user_countLimit){
			result.out_resultCode="N";
			result.out_resultText="您已超过优惠券使用次数";
			return result;
		}
		//优惠券总数检查
		Integer session_quanCount;
		session_quanCount = (Integer) sessionSysNotNull.get("nowCount");
		if(session_quanCount==null){
			session_quanCount=0;	
		}
		if(session_quanCount!=null&&session_quanCount >= quan_countLimit ){
			result.out_resultCode="N";
			result.out_resultText="活动已经结束！";
			return result;
		}
		//优惠折扣
		String discountCode = trade.discountCode;
		if(StringUtils.isBlank(discountCode)){
			result.out_resultCode="N";
			result.out_resultText="该券无效";
			return result;
		}
		//接口对象
		coupon  = new QQTXCoupon();
		Map<String, String> resultMap;
		//查询优惠券是否可用
		if(trade.TRADE_QUERYDISCOUNTCODEVALID.trim().equals(trade.tradeName)){
			//优惠券范围检查
			//表明是商品还是南航转出pro->商品;cz->南航
			String productFlag="pro";
			if(!productCodeMap.containsKey(trade.merchandise)&&!transferCode.equals(trade.merchandise)){
				result.out_resultCode="N";
				result.out_resultText="存在不能使用该券的商品，不能享受折扣";
				return result;
			}else{
				if(productCodeMap.containsKey(trade.merchandise)){
					productFlag="pro";
					result.out_resultText=proDiscount;
				}else{
					productFlag="cz";
					result.out_resultText=transferDiscount;
				}
			}
			//向券倾天下查询优惠码是否可用
			resultMap = coupon.checkCoupon(discountCode);
			if("N".equals(resultMap.get("out_resultCode"))){
				result.out_resultCode="N";
				result.out_resultText=resultMap.get("out_resultText");
				return result;
			}else{
				result.out_resultCode="Y";
				return result;
			}
		}else if(trade.TRADE_DODISCOUNTCODE.trim().equals(trade.tradeName)){//核销
			//向券倾天下核销 
			resultMap = coupon.userCoupon(discountCode);
			if("N".equals(resultMap.get("out_resultCode"))){
				result.out_resultCode="N";
				result.out_resultText=resultMap.get("out_resultText");
				return result;
			}
		}
		//优惠券总数控制
		session_quanCount = session_quanCount +1;
		//用户使用次数+1
		session_count+=1;
		//记录用户用券次数
		session.put(key_S_COUNT, session_count);
		//记录用户用券情况
		String usedDiscountCode = (String)session.get("usedDiscountCode");
		if(usedDiscountCode == null){
			usedDiscountCode="";
		}
		usedDiscountCode=usedDiscountCode+trade.discountCode+","+trade.merchandise+";";
		session.put("usedDiscountCode", usedDiscountCode);
		rule.session.put(trade.user, session);
		
		//记录使用的总券数
		sessionSysNotNull.put("nowCount", session_quanCount);
		rule.session.put(key_sysuser, sessionSysNotNull);
		//优惠券折扣
		result.out_resultText =trade.discountCode+"券码已经核销完成!";
		result.out_resultCode="Y";
		return result;
	}

}
