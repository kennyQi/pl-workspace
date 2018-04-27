package zzpl.pojo.dto.jp.plfx.gj;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;


@SuppressWarnings("serial")
public class GJFlightPolicyDTO extends BaseDTO{
	/**
	 * 航班组合和对应政策token
	 */
	private String flightAndPolicyToken;

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
	 * 成人最终结算价
	 */
	private Double ordFinalPirce;

	/**
	 * 儿童最终结算价
	 */
	private Double childFinalPirce;

	/**
	 * 婴儿最终结算价
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