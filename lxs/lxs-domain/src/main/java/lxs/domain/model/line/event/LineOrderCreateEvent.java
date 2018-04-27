package lxs.domain.model.line.event;

import hg.common.component.BaseEvent;

public class LineOrderCreateEvent extends BaseEvent {
	private static final long serialVersionUID = 1L;
	/**
	 * 线路订单号
	 */
	private String dealerOrderNo;
	/**
	 * 销售总额
	 */
	private Double salePrice;

	/**
	 * 销售定金
	 */
	private Double bargainMoney;

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getBargainMoney() {
		return bargainMoney;
	}

	public void setBargainMoney(Double bargainMoney) {
		this.bargainMoney = bargainMoney;
	}
}