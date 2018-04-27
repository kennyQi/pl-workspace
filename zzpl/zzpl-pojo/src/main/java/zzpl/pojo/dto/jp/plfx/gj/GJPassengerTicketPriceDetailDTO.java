package zzpl.pojo.dto.jp.plfx.gj;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class GJPassengerTicketPriceDetailDTO extends BaseDTO {
	/**
	 * 票面价
	 */
	private Double parValue;

	/**
	 * 税费
	 */
	private Double tax;

	/**
	 * 开票费
	 */
	private Double outTickMoney;

	/**
	 * 政策价格
	 */
	private Double platformPolicy;

	/**
	 * 支付总价
	 * 
	 * 支付总价=票面价+政策价格+税费+开票费
	 */
	private Double platformTotalPrice;

	public Double getParValue() {
		return parValue;
	}

	public void setParValue(Double parValue) {
		this.parValue = parValue;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getOutTickMoney() {
		return outTickMoney;
	}

	public void setOutTickMoney(Double outTickMoney) {
		this.outTickMoney = outTickMoney;
	}

	public Double getPlatformPolicy() {
		return platformPolicy;
	}

	public void setPlatformPolicy(Double platformPolicy) {
		this.platformPolicy = platformPolicy;
	}

	public Double getPlatformTotalPrice() {
		return platformTotalPrice;
	}

	public void setPlatformTotalPrice(Double platformTotalPrice) {
		this.platformTotalPrice = platformTotalPrice;
	}

}
