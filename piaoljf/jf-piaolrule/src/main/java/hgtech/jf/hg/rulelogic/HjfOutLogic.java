/**
 * @文件名称：HjfOutLogic.java
 * @类路径：hgtech.jf.rulelogic
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年10月21日上午10:27:33
 */
package hgtech.jf.hg.rulelogic;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.rulelogic.BaseSingleRule;
import hgtech.jfcal.rulelogic.RuleLogic;

public class HjfOutLogic extends BaseSingleRule implements RuleLogic {

	/**
	 * @方法功能说明：积分转出增长规则
	 * @修改者名字：pengel
	 * @修改时间：2014年10月21日上午10:27:33
	 * @修改内容：
	 * @参数：@param args
	 * @return:CalResult
	 */
	@SuppressWarnings({ "unused", "static-access" })
	@Override
	public CalResult compute(Serializable flow) throws Exception {
		String str = "";
		CalResult result = new CalResult();
		PiaolTrade trade = (PiaolTrade) flow;
		if (!trade.TRADE_TRANSFEROUT.trim().equals(trade.tradeName.trim()) || trade.TRADE_SIGN.trim().equals(trade.tradeName.trim())) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dealDate = sdf.parse(trade.date);
		Double money = trade.getMoney();
		Integer integral = 0;// 会员转出后积分增长量
		Integer integralIncrement = 100;// 每次转出应该得到的积分的增量
		Double jfRatio = 0.2;// 每次转出会员所得积分占成交积分的比重

		jfRatio = Double.parseDouble(rule.parameters.get("jfRatio"));// @REMARK@会员积分转出，所得积分占转出积分的比重
		integralIncrement = Integer.parseInt(new java.text.DecimalFormat("0").format(money * jfRatio));

		str += "转出：" + trade.money;
		integral += integralIncrement;
		result.out_jf = integral * -1;
		result.in_rule = rule;
		result.in_tradeFlow = flow;
		result.in_tradeFlowId = trade.getFlowId();
		result.in_userCode = trade.user;
		result.out_resultText = str;
		return result;
	}

}
