package plfx.api.client.api.v1.gj.dto;

import java.util.List;

import plfx.api.client.common.PlfxApiConstants.GJ;

/**
 * @类功能说明：国际航班组合政策
 * @类修改者：
 * @修改日期：2015-7-8下午5:37:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午5:37:03
 */
public class GJFlightPolicyDTO {
	/**
	 * 航班组合和对应政策token
	 */
	private String flightAndPolicyToken;

	/**
	 * 退改签规则
	 */
	private String airRules;

	/**
	 * 是否换编码出票
	 */
	private Boolean isChangePnr;

	/**
	 * 开票费
	 */
	private Double outTicketMoney;

	/**
	 * 购票限制
	 */
	private String saleLimit;

	/**
	 * 适用乘客类型
	 * 
	 * @see GJ#PASSENGER_TYPE_MAP
	 */
	private List<Integer> passengerType;

	/**
	 * 成人票面价
	 */
	private Double price;

	/**
	 * 儿童票面价
	 */
	private Double childPrice;

	/**
	 * 婴儿票面价
	 */
	private Double babyPrice;

	/**
	 * 成人税
	 */
	private Double tax;

	/**
	 * 儿童税
	 */
	private Double childTax;

	/**
	 * 婴儿税
	 */
	private Double babyTax;

	/**
	 * 成人政策价格
	 */
	private Double policyPrice;

	/**
	 * 儿童政策价格
	 */
	private Double childPolicyPrice;

	/**
	 * 婴儿政策价格
	 */
	private Double babyPolicyPrice;

	/**
	 * 成人最终结算价=成人票面价+成人政策价格+成人税+开票费
	 */
	private Double ordFinalPirce;

	/**
	 * 儿童最终结算价=儿童票面价+儿童政策价格+儿童税+开票费
	 */
	private Double childFinalPirce;

	/**
	 * 婴儿最终结算价=婴儿票面价+婴儿政策价格+婴儿税+开票费
	 */
	private Double babyFinalPirce;

	/**
	 * 营业时间(格式HH:mm-HH:mm)
	 */
	private String openTime;

	/**
	 * 退票时间(格式HH:mm-HH:mm)
	 */
	private String refundTime;

	public String getFlightAndPolicyToken() {
		return flightAndPolicyToken;
	}

	public void setFlightAndPolicyToken(String flightAndPolicyToken) {
		this.flightAndPolicyToken = flightAndPolicyToken;
	}

	public String getAirRules() {
		return airRules;
	}

	public void setAirRules(String airRules) {
		this.airRules = airRules;
	}

	public Boolean getIsChangePnr() {
		return isChangePnr;
	}

	public void setIsChangePnr(Boolean isChangePnr) {
		this.isChangePnr = isChangePnr;
	}

	public Double getOutTicketMoney() {
		return outTicketMoney;
	}

	public void setOutTicketMoney(Double outTicketMoney) {
		this.outTicketMoney = outTicketMoney;
	}

	public String getSaleLimit() {
		return saleLimit;
	}

	public void setSaleLimit(String saleLimit) {
		this.saleLimit = saleLimit;
	}

	public List<Integer> getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(List<Integer> passengerType) {
		this.passengerType = passengerType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}

	public Double getBabyPrice() {
		return babyPrice;
	}

	public void setBabyPrice(Double babyPrice) {
		this.babyPrice = babyPrice;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getChildTax() {
		return childTax;
	}

	public void setChildTax(Double childTax) {
		this.childTax = childTax;
	}

	public Double getBabyTax() {
		return babyTax;
	}

	public void setBabyTax(Double babyTax) {
		this.babyTax = babyTax;
	}

	public Double getPolicyPrice() {
		return policyPrice;
	}

	public void setPolicyPrice(Double policyPrice) {
		this.policyPrice = policyPrice;
	}

	public Double getChildPolicyPrice() {
		return childPolicyPrice;
	}

	public void setChildPolicyPrice(Double childPolicyPrice) {
		this.childPolicyPrice = childPolicyPrice;
	}

	public Double getBabyPolicyPrice() {
		return babyPolicyPrice;
	}

	public void setBabyPolicyPrice(Double babyPolicyPrice) {
		this.babyPolicyPrice = babyPolicyPrice;
	}

	public Double getOrdFinalPirce() {
		return ordFinalPirce;
	}

	public void setOrdFinalPirce(Double ordFinalPirce) {
		this.ordFinalPirce = ordFinalPirce;
	}

	public Double getChildFinalPirce() {
		return childFinalPirce;
	}

	public void setChildFinalPirce(Double childFinalPirce) {
		this.childFinalPirce = childFinalPirce;
	}

	public Double getBabyFinalPirce() {
		return babyFinalPirce;
	}

	public void setBabyFinalPirce(Double babyFinalPirce) {
		this.babyFinalPirce = babyFinalPirce;
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

}
