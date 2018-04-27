package zzpl.pojo.dto.jp;

import zzpl.pojo.dto.BaseDTO;


@SuppressWarnings("serial")
public class FlightClassDTO extends BaseDTO {

	/**
	 * 舱位代码
	 */
	private String classCode;

	/**
	 * 舱位类型
	 */
	private String classType;

	/**
	 * 票面价
	 */
	private Double originalPrice;

	/**
	 * 折扣
	 */
	private Integer discount;

	/**
	 * 代理费
	 */
	private Double proxyCost;

	/**
	 * 奖励
	 */
	private Double awardCost;

	/**
	 * 结算
	 */
	private Double settleAccounts;

	/**
	 * 剩余座位
	 */
	private String lastSeat;

	/**
	 * 退改签政策KEY
	 */
	private String tgNoteKey;

	/**
	 * 是否允许儿童预订
	 */
	private Boolean allowChildren;

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Double getProxyCost() {
		return proxyCost;
	}

	public void setProxyCost(Double proxyCost) {
		this.proxyCost = proxyCost;
	}

	public Double getAwardCost() {
		return awardCost;
	}

	public void setAwardCost(Double awardCost) {
		this.awardCost = awardCost;
	}

	public String getLastSeat() {
		return lastSeat;
	}

	public void setLastSeat(String lastSeat) {
		this.lastSeat = lastSeat;
	}

	public Boolean getAllowChildren() {
		return allowChildren;
	}

	public void setAllowChildren(Boolean allowChildren) {
		this.allowChildren = allowChildren;
	}

	public Double getSettleAccounts() {
		return settleAccounts;
	}

	public void setSettleAccounts(Double settleAccounts) {
		this.settleAccounts = settleAccounts;
	}

	public String getTgNoteKey() {
		return tgNoteKey;
	}

	public void setTgNoteKey(String tgNoteKey) {
		this.tgNoteKey = tgNoteKey;
	}

}
