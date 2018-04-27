package lxs.api.v1.dto.line;

import java.util.Date;



import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class DateSalePriceDTO extends BaseDTO {
	/**
	 * 属于哪天
	 */
	private Date saleDate;

	/**
	 * 成人价
	 */
	private Double adultPrice;

	/**
	 * 儿童价
	 */
	private Double childPrice;

	/**
	 * 剩余人数
	 */
	private Integer number;

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}