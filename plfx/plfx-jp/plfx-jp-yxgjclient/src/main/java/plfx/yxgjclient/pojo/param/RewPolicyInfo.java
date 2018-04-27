package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 有奖励政策信息
 * @author guotx
 * 2015-07-06
 */
@XStreamAlias("rewPolicyInfo")
public class RewPolicyInfo {
	/**
	 * 儿童税
	 */
	private String childTax;
	/**
	 * 儿童最终结算价格
	 */
	private String childFinalPrice;
	/**
	 * 婴儿最终结算价格
	 */
	private String babyFinalPrice;

	/**
	 * 是否换编码出票
	 */
	private String isChangePnr;
	/**
	 * 出票类型
	 */
	private String outTicketType;
	/**
	 * 基础返点
	 */
	private String saleBasicDisc;
	/**
	 * 奖励返点
	 */
	private String saleExtraDisc;
	/**
	 * 开票费
	 */
	private String outTicketMoney;
	/**
	 * 购票限制
	 */
	private String saleLimit;
	/**
	 * 乘客类型
	 */
	private String passengerType;
	/**
	 * 政策ID
	 */
	private String policyId;
	/**
	 * 出票速度
	 */
	private String gsqAvrOutTime;
	/**
	 * 可支付类型
	 */
	private String payplatform;
	/**
	 * 修改时间
	 */
	private String modiTime;
	/**
	 * 中转段奖励，1无，2有
	 */
	private String noTransferAirline;
	/**
	 * 成人票面价
	 */
	private String ordPrice;
	/**
	 * 成人税
	 */
	private String ordTax;
	/**
	 * 儿童票面价
	 */
	private String childPrice;
	/**
	 * 婴儿票面价
	 */
	private String babyPrice;
	/**
	 * 婴儿税
	 */
	private String babyTax;
	/**
	 * 成人最终结算价
	 */
	private String ordFinalPirce;
	/**
	 * 儿童最终结算价
	 */
	private String childFinalPirce;
	/**
	 * 婴儿最终结算价
	 */
	private String babyFinalPirce;
	/**
	 * 营业时间
	 */
	private String openTime;
	/**
	 * 退票时间
	 */
	private String refundTime;
	/**
	 * 加密串
	 */
	private String encryptString;
	
	// ----------------- 缓存用 -----------------
	
	/**
	 * 航班舱位组合token
	 */
	private String flightCabinGroupToken;

	/**
	 * 退改签规则
	 */
	private String airRules;

	public String getIsChangePnr() {
		return isChangePnr;
	}

	public void setIsChangePnr(String isChangePnr) {
		this.isChangePnr = isChangePnr;
	}

	public String getOutTicketType() {
		return outTicketType;
	}

	public void setOutTicketType(String outTicketType) {
		this.outTicketType = outTicketType;
	}

	public String getSaleBasicDisc() {
		return saleBasicDisc;
	}

	public void setSaleBasicDisc(String saleBasicDisc) {
		this.saleBasicDisc = saleBasicDisc;
	}

	public String getSaleExtraDisc() {
		return saleExtraDisc;
	}

	public void setSaleExtraDisc(String saleExtraDisc) {
		this.saleExtraDisc = saleExtraDisc;
	}

	public String getOutTicketMoney() {
		return outTicketMoney;
	}

	public void setOutTicketMoney(String outTicketMoney) {
		this.outTicketMoney = outTicketMoney;
	}

	public String getSaleLimit() {
		return saleLimit;
	}

	public void setSaleLimit(String saleLimit) {
		this.saleLimit = saleLimit;
	}

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getGsqAvrOutTime() {
		return gsqAvrOutTime;
	}

	public void setGsqAvrOutTime(String gsqAvrOutTime) {
		this.gsqAvrOutTime = gsqAvrOutTime;
	}

	public String getPayplatform() {
		return payplatform;
	}

	public void setPayplatform(String payplatform) {
		this.payplatform = payplatform;
	}

	public String getModiTime() {
		return modiTime;
	}

	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}

	public String getNoTransferAirline() {
		return noTransferAirline;
	}

	public void setNoTransferAirline(String noTransferAirline) {
		this.noTransferAirline = noTransferAirline;
	}

	public String getOrdPrice() {
		return ordPrice;
	}

	public void setOrdPrice(String ordPrice) {
		this.ordPrice = ordPrice;
	}

	public String getOrdTax() {
		return ordTax;
	}

	public void setOrdTax(String ordTax) {
		this.ordTax = ordTax;
	}

	public String getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(String childPrice) {
		this.childPrice = childPrice;
	}

	public String getChildTax() {
		return childTax;
	}

	public void setChildTax(String childTax) {
		this.childTax = childTax;
	}

	public String getBabyPrice() {
		return babyPrice;
	}

	public void setBabyPrice(String babyPrice) {
		this.babyPrice = babyPrice;
	}

	public String getBabyTax() {
		return babyTax;
	}

	public void setBabyTax(String babyTax) {
		this.babyTax = babyTax;
	}

	public String getOrdFinalPirce() {
		return ordFinalPirce;
	}

	public void setOrdFinalPirce(String ordFinalPirce) {
		this.ordFinalPirce = ordFinalPirce;
	}

	public String getChildFinalPrice() {
		return childFinalPrice;
	}

	public void setChildFinalPrice(String childFinalPrice) {
		this.childFinalPrice = childFinalPrice;
	}

	public String getBabyFinalPrice() {
		return babyFinalPrice;
	}

	public void setBabyFinalPrice(String babyFinalPrice) {
		this.babyFinalPrice = babyFinalPrice;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	public String getChildFinalPirce() {
		return childFinalPirce;
	}

	public void setChildFinalPirce(String childFinalPirce) {
		this.childFinalPirce = childFinalPirce;
	}

	public String getBabyFinalPirce() {
		return babyFinalPirce;
	}

	public void setBabyFinalPirce(String babyFinalPirce) {
		this.babyFinalPirce = babyFinalPirce;
	}

	public String getFlightCabinGroupToken() {
		return flightCabinGroupToken;
	}

	public void setFlightCabinGroupToken(String flightCabinGroupToken) {
		this.flightCabinGroupToken = flightCabinGroupToken;
	}

	public String getAirRules() {
		return airRules;
	}

	public void setAirRules(String airRules) {
		this.airRules = airRules;
	}
	
}
