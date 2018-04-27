package zzpl.pojo.dto.jp.plfx.gj;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class PayToJPOrderGJDTO extends BaseDTO{
	/**
	 * 订单总支付价格
	 */
	private Double totalPrice;

	/**
	 * 支付是否成功
	 */
	private Boolean payStatus;

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Boolean getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Boolean payStatus) {
		this.payStatus = payStatus;
	}

}