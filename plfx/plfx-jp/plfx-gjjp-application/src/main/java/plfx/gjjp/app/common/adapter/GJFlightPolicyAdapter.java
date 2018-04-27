package plfx.gjjp.app.common.adapter;

import hg.common.util.MoneyUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import plfx.api.client.api.v1.gj.dto.GJFlightPolicyDTO;
import plfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import plfx.yxgjclient.pojo.param.RewPolicyInfo;

/**
 * @类功能说明：API政策适配器
 * @类修改者：
 * @修改日期：2015-7-24下午5:17:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-24下午5:17:52
 */
public class GJFlightPolicyAdapter {

	/**
	 * @方法功能说明：转换为API的政策DTO
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-24下午5:18:08
	 * @修改内容：
	 * @参数：@param availableJourney
	 * @参数：@param rewPolicyInfo
	 * @参数：@param policySnapshot
	 * @参数：@return
	 * @return:GJFlightPolicyDTO
	 * @throws
	 */
	public GJFlightPolicyDTO convertDTO(RewPolicyInfo rewPolicyInfo, JPPlatformPolicySnapshot policySnapshot) {

		List<Integer> passengerType = new ArrayList<Integer>();
		String[] passengerTypeStrs = rewPolicyInfo.getPassengerType().split("\\^");
		for (String str : passengerTypeStrs)
			passengerType.add(Integer.valueOf(str));

		double outTicketMoney = Double.valueOf(rewPolicyInfo.getOutTicketMoney());

		double price = Double.valueOf(rewPolicyInfo.getOrdPrice()); 				// 成人票面价
		double childPrice = Double.valueOf(rewPolicyInfo.getChildPrice());			// 儿童票面价
		double babyPrice = Double.valueOf(rewPolicyInfo.getBabyPrice());;			// 婴儿票面价

		double tax = Double.valueOf(rewPolicyInfo.getOrdTax()); 					// 成人税
		double childTax = Double.valueOf(rewPolicyInfo.getChildTax());				// 儿童税
		double babyTax = Double.valueOf(rewPolicyInfo.getBabyTax());				// 婴儿税

		double policyPrice = 0d; 													// 成人平台政策价格
		double childPolicyPrice = 0d;												// 儿童平台政策价格
		double babyPolicyPrice = 0d;												// 婴儿平台政策价格

		double ordFinalPirce = 0d;													// 成人最终结算价
		double childFinalPirce = 0d;												// 儿童最终结算价
		double babyFinalPirce = 0d;													// 婴儿最终结算价

		if (policySnapshot != null) {
			double percentPolicy = policySnapshot.getPercentPolicy();
			double pricePolicy = policySnapshot.getPricePolicy();
			policyPrice = MoneyUtil.round(price * percentPolicy + pricePolicy, 2);
			childPolicyPrice = MoneyUtil.round(childPrice * percentPolicy + pricePolicy, 2);
			babyPolicyPrice = MoneyUtil.round(babyPrice * percentPolicy + pricePolicy, 2);
		}
		
		ordFinalPirce = MoneyUtil.round(price + policyPrice + tax + outTicketMoney, 2);
		childFinalPirce = MoneyUtil.round(childPrice + childPolicyPrice + childTax + outTicketMoney, 2);
		babyFinalPirce = MoneyUtil.round(babyPrice + babyPolicyPrice + babyTax + outTicketMoney, 2);

		GJFlightPolicyDTO policyDTO = new GJFlightPolicyDTO();
		policyDTO.setFlightAndPolicyToken(DigestUtils.md5Hex(rewPolicyInfo.getEncryptString()));
		policyDTO.setAirRules(rewPolicyInfo.getAirRules());
		policyDTO.setIsChangePnr("1".equals(rewPolicyInfo.getIsChangePnr()) ? true : false);
		policyDTO.setOutTicketMoney(Double.valueOf(rewPolicyInfo.getOutTicketMoney()));
		policyDTO.setSaleLimit(rewPolicyInfo.getSaleLimit());
		policyDTO.setPassengerType(passengerType);
		policyDTO.setPolicyPrice(policyPrice);
		policyDTO.setChildPolicyPrice(childPolicyPrice);
		policyDTO.setBabyPolicyPrice(babyPolicyPrice);
		policyDTO.setOrdFinalPirce(ordFinalPirce);
		policyDTO.setChildFinalPirce(childFinalPirce);
		policyDTO.setBabyFinalPirce(babyFinalPirce);
		policyDTO.setPrice(price);
		policyDTO.setChildPrice(childPrice);
		policyDTO.setBabyPrice(babyPrice);
		policyDTO.setTax(tax);
		policyDTO.setChildTax(childTax);
		policyDTO.setBabyTax(babyTax);
		policyDTO.setOpenTime(rewPolicyInfo.getOpenTime());
		policyDTO.setRefundTime(rewPolicyInfo.getRefundTime());

		return policyDTO;
	}

}
